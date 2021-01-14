package com.northwind.handlers;

import java.time.YearMonth;

public class ReportRequest {
    private String month;
    private String year;

    public YearMonth getYearMonth() {
        return YearMonth.parse(year + "-" + month);
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}