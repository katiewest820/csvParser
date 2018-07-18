package com.cloudability.csv_parser;


import com.opencsv.bean.CsvBindByName;

public class DbrFiles {

    @CsvBindByName(column = "UsageStartDate")
    private String date;

    @CsvBindByName(column = "Cost")
    private String unblendedCost;

    @CsvBindByName(column = "LinkedAccountId")
    private String usageAccountId;

    @Override
    public String toString() {
        return "DbrFiles{" +
                "date='" + date + '\'' +
                ", unblendedCost='" + unblendedCost + '\'' +
                ", usageAccountId='" + usageAccountId + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public String getUnblendedCost() {
        return unblendedCost;
    }

    public String getUsageAccountId() {
        return usageAccountId;
    }
}
