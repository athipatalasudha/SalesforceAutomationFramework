package com.salesforce.test.tests;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.salesforce.test.utility.*;
public class ListenersTest implements ITestListener
{
	GenerateReports report=GenerateReports.getInstance();
	@Override
	public void onTestStart(ITestResult result) {
		report.logTestinfo("Test Execution Started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		report.logTestPassed("Test case Passed: " + result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		report.logTestFailed("Test Case FAiled"+result.getName());
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
	}

}
