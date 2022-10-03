package com.salesforce.test.utility;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.salesforce.test.basetest.BaseTest1;

public class GenerateReports{

	ExtentHtmlReporter htmlreporter;
	public ExtentReports extent;
	public ExtentTest logger;
	private static GenerateReports ob;
	private GenerateReports()
	{
		
	}
	public static GenerateReports getInstance()
	{
		if(ob==null)
			ob=new GenerateReports();
		return ob;
	}
	public void startExtentReport()
	{
		htmlreporter =new ExtentHtmlReporter(Constants.GENERATE_REPORT_PATH);
		extent=new ExtentReports();
		extent.attachReporter(htmlreporter);
		
		extent.setSystemInfo("Host Name", "salesforce");
		extent.setSystemInfo("Environment", "Automation Testing");
		extent.setSystemInfo("User Name", "Sudha");
		
		htmlreporter.config().setDocumentTitle("Test Execution REport");
		htmlreporter.config().setReportName("Salesforce Regression Tests");
		//htmlreporter.config().setTestViewChartLocation(ChartLocation.top);
		htmlreporter.config().setTheme(Theme.STANDARD);
		
	}
	public void startSingleTestReport(String testname)
	{
		logger=extent.createTest(testname);
	}
	public void logTestinfo(String message)
	{
		logger.log(Status.INFO, message);
	}
	public void logTestPassed(String testname)
	{
		var filePath = BaseTest1.captureSS(testname);
		//logger.log(Status.PASS, MarkupHelper.createLabel(testname+" is Passed ", ExtentColor.GREEN));
		try {
			logger.log(Status.PASS, testname+" is Passed ", MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void logTestFailed(String testname)
	{
		var filePath = BaseTest1.captureSS(testname);
		//logger.log(Status.PASS, MarkupHelper.createLabel(testname+" is Passed ", ExtentColor.GREEN));
		try {
			logger.log(Status.FAIL, testname+" is FAiled ", MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void logTestFailedWithException(Exception e)
	{
		logger.log(Status.ERROR, e);
	}
	public void logTestSkipped(String testname)
	{
		logger.log(Status.SKIP, MarkupHelper.createLabel(testname+" is FAiled ", ExtentColor.BLUE));
	}
	public void endReport()
	{
		extent.flush();
	}
}
