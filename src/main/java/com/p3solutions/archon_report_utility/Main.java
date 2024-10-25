package com.p3solutions.archon_report_utility;

import com.p3solutions.archon_report_utility.reports.Executable_Class;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Executable_Class executableClass = new Executable_Class();
        executableClass.reportProcessInitiated();

    }
}