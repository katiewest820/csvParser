package com.cloudability.csv_parser;


import com.opencsv.bean.CsvBindByName;

public class DbrFiles {

    @CsvBindByName(column = "UsageStartDate")
    private String date;

    @CsvBindByName(column = "UnblendedCost")
    private String unblendedCost;

    @CsvBindByName(column = "RecordType")
    private String recordType;

    @CsvBindByName(column = "LinkedAccountId")
    private String usageAccountId;

    @Override
    public String toString() {
        return "DbrFiles{" +
                "date='" + date + '\'' +
                ", unblendedCost='" + unblendedCost + '\'' +
                ", recordType='" + recordType + '\'' +
                ", usageAccountId='" + usageAccountId + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public String getUnblendedCost() {
        return unblendedCost;
    }

    public String getRecordType() {
        return recordType;
    }

    public String getUsageAccountId() {
        return usageAccountId;
    }
}
