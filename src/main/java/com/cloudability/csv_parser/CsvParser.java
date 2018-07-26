package com.cloudability.csv_parser;

import com.opencsv.bean.CsvToBeanBuilder;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

public class CsvParser {
    private static Map<String, Double> accountIds = new TreeMap<>();
    private static Map<String, Double> dates = new TreeMap<>();
    private static double totalCost = 0;


    public static void main(String[] args) throws IOException {
        if (args.length <= 0) {
            System.out.println("You fool! Add at least one csv file");
        } else {
            File mainFolder = new File(args[0]);
            getFiles(mainFolder);
        }
        returnValues();
    }

    private static void unZipFile(File path) throws IOException {
        String sourceFile = path.getPath();
        if (sourceFile.endsWith(".gz")) {
            String targetFile = sourceFile.substring(0, sourceFile.length() - 3);
            System.out.println(targetFile);
            FileInputStream fis = new FileInputStream(sourceFile);
            GZIPInputStream gzis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(targetFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = gzis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.close();
            gzis.close();
            fis.close();
            File unzippedFile = new File(targetFile);
            readCurFiles(unzippedFile);
        } else if (sourceFile.endsWith(".zip")) {
            String targetFile = sourceFile.substring(0, sourceFile.length() - 4);
            String pathForUnzippedFile = path.getParentFile().getAbsolutePath();
            System.out.println(pathForUnzippedFile);
            try {
                ZipFile zipFile = new ZipFile(sourceFile);
                zipFile.extractAll(pathForUnzippedFile);
            } catch (ZipException e) {
                e.printStackTrace();
            }
            File unzippedFile = new File(targetFile);
            readDbrFiles(unzippedFile);
        } else if (sourceFile.contains("detailed")) {
            System.out.println(sourceFile);
            readDbrFiles(path);
        } else {
            readCurFiles(path);
        }
    }

    private static void getFiles(File f) throws IOException {
        File files[];
        if (f.isFile()) {
            System.out.println(f.getAbsolutePath());
            unZipFile(f);
        } else {
            files = f.listFiles();
            for (File file : files) {
                getFiles(file);
            }
        }
    }

    private static void readDbrFiles(File dbrFile) throws IOException {
        double cost = 0;
        String formattedDate = "";
        List<DbrFiles> beans = new CsvToBeanBuilder<DbrFiles>(new FileReader(dbrFile))
                .withType(DbrFiles.class).build().parse();
        for (int i = 0; i < beans.size(); i++) {
            DbrFiles bean = beans.get(i);
            String recordType = bean.getRecordType();
            if (recordType.equals("LineItem")) {
                try {
                    System.out.println("yes!!!");
                    cost = Double.parseDouble(bean.getUnblendedCost());
                    String tempDate = bean.getDate();
                    formattedDate = tempDate.substring(0, 10);
                    totalCost = totalCost + cost;
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
                if (accountIds.containsKey(bean.getUsageAccountId())) {
                    String myVal = String.valueOf(accountIds.get(bean.getUsageAccountId()));
                    double secondCost = Double.parseDouble(myVal);
                    accountIds.put(bean.getUsageAccountId(), cost + secondCost);
                } else {
                    accountIds.put(bean.getUsageAccountId(), cost);
                }
                if (dates.containsKey(formattedDate)) {
                    String myVal = String.valueOf(dates.get(formattedDate));
                    double secondCost = Double.parseDouble(myVal);
                    dates.put(formattedDate, cost + secondCost);
                } else {
                    dates.put(formattedDate, cost);
                }
            }
        }
    }

    private static void readCurFiles(File curFile) throws IOException {
        double cost = 0;
        String formattedDate = "";
        List<CurFiles> beans = new CsvToBeanBuilder<CurFiles>(new FileReader(curFile))
                .withType(CurFiles.class).build().parse();
        for (CurFiles bean : beans) {
            try {
                cost = Double.parseDouble(bean.getUnblendedCost());
                String tempDate = bean.getDate();
                formattedDate = tempDate.substring(0, 10);
                totalCost = totalCost + cost;

            } catch (NumberFormatException e) {
                System.out.println(e);
            }

            if (accountIds.containsKey(bean.getUsageAccountId())) {
                String myVal = String.valueOf(accountIds.get(bean.getUsageAccountId()));
                double secondCost = Double.parseDouble(myVal);
                accountIds.put(bean.getUsageAccountId(), cost + secondCost);
            } else {
                accountIds.put(bean.getUsageAccountId(), cost);
            }
            if (dates.containsKey(formattedDate)) {
                String myVal = String.valueOf(dates.get(formattedDate));
                double secondCost = Double.parseDouble(myVal);
                dates.put(formattedDate, cost + secondCost);
            } else {
                dates.put(formattedDate, cost);
            }
        }
    }

    private static void returnValues() {
        System.out.println("     Account ID | Un-blended Cost");
        System.out.println("-------------------------------------");
        for (Map.Entry<String, Double> entry : accountIds.entrySet()) {
            DecimalFormat df = new DecimalFormat("0.00");
            System.out.println("   " + entry.getKey() + " | " + df.format(entry.getValue()));
        }
        System.out.println("-------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println("     Date    | Un-blended Cost");
        System.out.println("---------------------------------");
        for (Map.Entry<String, Double> entry : dates.entrySet()) {
            DecimalFormat df = new DecimalFormat("0.00");
            System.out.println("  " + entry.getKey() + " | " + df.format(entry.getValue()));
        }
        System.out.println("---------------------------------");
        System.out.println();
        System.out.println();
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Total Cost: " + df.format(totalCost));
    }
}
