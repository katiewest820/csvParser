package com.cloudability.csv_parser;

import com.opencsv.bean.CsvBindByName;

public class CurFiles {

    @CsvBindByName(column = "lineItem/UsageAccountId", required = true)
    private String usageAccountId;

    @CsvBindByName(column = "lineItem/UnblendedCost", required = true)
    private String unblendedCost;

    @CsvBindByName(column = "lineItem/UsageStartDate", required = true)
    private String date;


    @Override
    public String toString() {
        return "CurFiles{" +
                "usageAccountId='" + usageAccountId + '\'' +
                ", unblendedCost='" + unblendedCost + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getUsageAccountId() {
        return usageAccountId;
    }

    public String getUnblendedCost() {
        return unblendedCost;
    }

    public String getDate() {
        return date;
    }
}
