package com.p3solutions.archon_report_utility;

import com.p3solutions.archon_report_utility.reports.GenerateReport;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GenerateReport generateReport = new GenerateReport();
        generateReport.reportGeneration();

    }
}