package com.salesforce.test.basetest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import com.salesforce.test.utility.CommonUtilities;
import com.salesforce.test.utility.Constants;
import com.salesforce.test.utility.GenerateReports;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest1 extends CommonUtilities
{
	public static WebDriver driver = null;
	public static WebDriverWait wait = null;
	public static GenerateReports report=null;
	//public static Logger logger=LogManager.getLogger(BaseTest1.class);
	
	@BeforeTest
	public static void setupBeforeTest()
	{
		report=GenerateReports.getInstance();
		report.startExtentReport();
	}
	
	
	@Parameters({"browsername"})
	@Test
	@BeforeMethod
	public static void setUp(String browsername,Method m ) {
		System.out.println("before method execution started");
		report.startSingleTestReport(m.getName());
		setDriver(browsername);
		CommonUtilities CU = new CommonUtilities();
		Properties applicationPropertiesFile = CU.loadFile("data");
		String url = CU.getApplicationProperty("myurl", applicationPropertiesFile);
		driver.get(url);
		driver.manage().window().maximize();
		waitUntilPageLoads();
	}
	
	@AfterMethod
	public static void tearDown(Method m) {
		//logger.info("after method execution is started");
		//captureSS(m.getName());
		closeBrowser();
	}
	@AfterTest
	public static void teardownAfterTest()
	{
		report.endReport();
	}
	public static void setDriver(String browser) {

		switch (browser) {
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		default:
			break;

		}

	}
	
	@Test
	public static void loginToSalesforce() {
		
		CommonUtilities CU = new CommonUtilities();
		Properties applicationPropertiesFile = CU.loadFile("data");

		//String url = CU.getApplicationProperty("myurl", applicationPropertiesFile);
		
		String usrname = CU.getApplicationProperty("myusername", applicationPropertiesFile);
		String passwrd = CU.getApplicationProperty("mypswd", applicationPropertiesFile);
		WebElement username=driver.findElement(By.xpath("//*[@id=\"username\"]"));
		WebElement pswd=driver.findElement(By.name("pw"));
		sendKeys(driver, username, Duration.ofSeconds(10), usrname,"Username Textbox");
		sendKeys(driver, pswd, Duration.ofSeconds(10), passwrd,"Username Textbox");
		WebElement login=driver.findElement(By.xpath("//*[@id=\"Login\"]"));
		clickOn(driver, login, Duration.ofSeconds(10),"Login Button");
		waitUntilPageLoads();
		String expected="Home Page ~ Salesforce - Developer Edition";
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(expected, driver.getTitle());
		System.out.println("\n Home page successfully displayed");
		waitUntilPageLoads();

	}
	public static void waitUntilPageLoads() {
		//driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
	}

	
public static void clearElement(WebElement element,String objname )
{
	if(element.isDisplayed())
	{
		element.clear();
		System.out.println("pass : "+objname+" element is cleared ");
	
	}
	else
		System.out.println("faile : "+objname+" element is not displayed ");
}
public static WebDriver getDriverInstance()
{
	return driver;
}
public static void gotoURL(String url)
{
	driver.get(url);
}
public static void closeBrowser()
{
	driver.close();
}
public static void closeAllBrowser()
{
	driver.quit();
}
public static void waitUntilVisible(WebElement element, String objName) {
	wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	wait.until(ExpectedConditions.visibilityOf(element));
}

public static void sendKeys(WebDriver driver1, WebElement element, Duration timeout, String value,String objname)
{
	new WebDriverWait(driver1, timeout).until(ExpectedConditions.visibilityOf(element));
	element.sendKeys(value);
	System.out.println("text entered in : "+objname+" field ");
}
public static void enterText(WebElement element, String text, String objname)
{
	if(element.isDisplayed())
	{
		clearElement(element,objname);
		element.sendKeys(text);
		System.out.println("text entered in : "+objname+" field ");
	}
	else
		System.out.println("faile : "+objname+" element is not displayed ");
}
public static void clickOn(WebDriver driver1, WebElement element, Duration timeout,String objname)
{
	new WebDriverWait(driver1, timeout).until(ExpectedConditions.elementToBeClickable(element));
	element.click();
	System.out.println("\n Click successfully performed on :"+objname);
}
public static void clickElement(WebElement element,String objname)
{
	if(!(element.isDisplayed()|| element.isSelected()))
	{
		element.click();
		System.out.println("pass : "+objname+" clicked ");
	}
	else
		System.out.println("failed : "+objname+" element is not displayed or already selected ");
}
public static void isSelected(WebElement element,String objname)
{
	if(element.isSelected())
		System.out.println("\n Element already selected");
	else
	{
		element.click();
		System.out.println("pass : "+objname+" selected ");
	}
}
public static boolean WaitCheck(WebDriver drv, WebElement element, String str)
{
	WebDriverWait wait=new WebDriverWait(drv, Duration.ofSeconds(50));
	wait.until(ExpectedConditions.visibilityOf(element));
	System.out.println("\n Wait check for "+str);
	return element.isDisplayed();
	
}
public static void handlePopup()
{
	try
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"tryLexDialog\"]")));
		//clicking on the close
		driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]")).click();
		System.out.println("\n Popup closed ");
	}catch (Exception e) {
		// TODO: handle exception
		System.out.println("\n No popup present");
	}
	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
}
public static By xpath(String xpath)
{
	return By.xpath(xpath);
}
public static By css(String css)
{
	return By.cssSelector(css);
}
public static WebElement findElement(String text,String type)
{
	WebElement element=null;
	switch(type)
	{
	case "xpath" : element =driver.findElement(By.xpath(text));
					break;
	case "css" : element=driver.findElement(By.cssSelector(text));
					break;
	}
	return element;
}

public static String captureSS(String ssname)
{
	try
	{
		Date d=new Date();
		String Timestamp=d.toString().replace(":", "-").replace(" ", "-");
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		String path="C:\\Users\\athip\\eclipse-workspace\\eclipsenewAugbatch\\SalesforceAutomationFramework\\ScreenShots\\"+ssname+ "_"+Timestamp+".png";
		FileHandler.copy(source, new File(path));
		System.out.println("\n Screenshot taken");
		return path;
	}catch(Exception e)
	{
		System.out.println("\n Error in capturing ss"+e.getMessage());
		return null;
	}
}
public static String readText(WebElement element, String objectName) {
	waitUntilVisible(element, objectName);
	String text = element.getText();
	return text;
}
protected static boolean isFileDownloaded_Ext(String dirPath, String ext)
{
	boolean flag=false;
    File dir = new File(dirPath);
    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
        flag = false;
    }
    
    for (int i = 1; i < files.length; i++) {
    	if(files[i].getName().contains(ext)) {
    		flag=true;
    	}
    }
    return flag;
}

}
