package com.qspider.atlassian.genericutility;

import java.time.LocalDateTime;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportForTest {
	private static ExtentReports extent;
	
	/**
	 * this method is responsible to create a report for the test
	 * @return ExtentReports object
	 */
	public static ExtentReports getReporterObject() {
		//ExtentSparkReporter, //ExtentReports
		//create object for ExtentSparkReporter pass the file path
		//file path where the all the report should be reported
		ExtentSparkReporter reporter = new ExtentSparkReporter("./testReport/index"+LocalDateTime.now().toString().replace(':', '-')+".html");
		//using reporter class methods we can set the report name title and many things 
		reporter.config().setReportName("Automation Test Reporter");
		reporter.config().setDocumentTitle("Test Results");
		reporter.config().setTheme(Theme.STANDARD);
		//Create object for ExtentReports class 
		extent = new ExtentReports();
		//attach the configured ExtentSparkReporter  object set the config to extent report
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Vinay");
		return extent;
		
	}

}
