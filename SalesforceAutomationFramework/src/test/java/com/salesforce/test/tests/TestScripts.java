package com.salesforce.test.tests;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bouncycastle.util.Properties;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import com.salesforce.test.basetest.BaseTest1;
import com.salesforce.test.utility.CommonUtilities;

@Listeners(com.salesforce.test.tests.ListenersTest.class)
public class TestScripts extends BaseTest1 
{
	
	@Test
	public static void LogoutSFDC() throws InterruptedException
	{
		driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
		WebElement usermenu=findElement("//*[@id=\"userNavLabel\"]","xpath");
		if(WaitCheck(driver, usermenu, "UserMenu"))
			System.out.println("\nUserMenu element found ");
		else System.out.println("\nUserMenu element not found ");
		clickOn(driver, usermenu, Duration.ofSeconds(10),"USerMenu Label");
		WebElement logout = findElement("//*[@id=\"userNav-menuItems\"]/a[5]","xpath");
		if(WaitCheck(driver, logout, "LogOut"))
			System.out.println("\nLogOut element found ");
		else System.out.println("\nLogOut element not found ");
		clickOn(driver, logout, Duration.ofSeconds(10),"Logout Label");
		driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
		String expected="Login | Salesforce";
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(expected, driver.getTitle(), "Titles doesnt match");
			report.logTestinfo(" TestCase passed - Logout successful");
	}
	@Test
	
	public static void LoginSFDC_TC02(WebElement username,String username1,WebElement password,String pswd) throws InterruptedException, IOException
	{
		sendKeys(driver, username, Duration.ofSeconds(10), username1,"Username Textbox");
		sendKeys(driver, password, Duration.ofSeconds(10), pswd,"Username Textbox");
		WebElement login=driver.findElement(By.xpath("//*[@id=\"Login\"]"));
		clickOn(driver, login, Duration.ofSeconds(10),"Login Button");
		String expected="Home Page ~ Salesforce - Developer Edition";
		String actual=driver.getTitle();
		System.out.println(actual);
		if(actual.equals(expected))
			System.out.println("\n TestCase passed 02- Login successful");
		//driver.close();
	}
	
	@Test
	
	public static void TC01() throws InterruptedException, IOException
	{
		//setUp("chrome");
		CommonUtilities CU = new CommonUtilities();
		var applicationPropertiesFile = CU.loadFile("data");

		String url = CU.getApplicationProperty("myurl", applicationPropertiesFile);
		driver.get(url);
		//launchSalesforce();
		WebElement username=driver.findElement(By.xpath("//*[@id=\"username\"]"));
		String usrname = CU.getApplicationProperty("myusername", applicationPropertiesFile);
		sendKeys(driver, username, Duration.ofSeconds(10), usrname,"Username Textbox");
		WebElement pswd=driver.findElement(By.name("pw"));
		pswd.clear();
		WebElement login=driver.findElement(By.xpath("//*[@id=\"Login\"]"));
		clickOn(driver, login, Duration.ofSeconds(10),"Login Button");
		waitUntilPageLoads();
		WebElement errormsg1=driver.findElement(By.xpath("//*[@id=\"error\"]"));
		//if(errormsg1.isDisplayed())
			//System.out.println("\n Test Case Passed 01 - Error message displayed ");
		String act="Please enter your password.";
		String exp=readText(errormsg1,"Error Msg");
		Assert.assertEquals(exp, act, "Error messages are not the same");
		report.logTestinfo("Login Error Msg displayed");
		
		
	}
	@Test
	public static void TC02()
	{
		loginToSalesforce();
	}
	@Test
	
	public static void TC03() throws InterruptedException, IOException
	{
		String p_username=readPropertyData("myusername");
		String p_pswd=readPropertyData("mypswd");
		WebElement username=driver.findElement(By.xpath("//*[@id=\"username\"]"));
		WebElement pswd=driver.findElement(By.name("pw"));
		WebElement checkbox1=driver.findElement(By.xpath("//*[@id=\"login_form\"]/div[3]/label"));
		clickOn(driver, checkbox1, Duration.ofSeconds(10),"Remember Me Checkbox");
		LoginSFDC_TC02(username,p_username, pswd,p_pswd);
		driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
		LogoutSFDC();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		WebElement id1=driver.findElement(By.xpath("//*[@id=\"idcard-identity\"]"));
		if(WaitCheck(driver, id1, "Username field"))
			System.out.println("\n Element found ");
		if(id1.isDisplayed())
			report.logTestinfo("Test Case 03 passed - Username displayed in the username checkbox ");
		
	}
	@Test
	public static void TC_4A() throws InterruptedException, IOException
	{
		WebElement forgotpswd=driver.findElement(By.xpath("//*[@id=\"forgot_password_link\"]"));
		clickOn(driver, forgotpswd, Duration.ofSeconds(10),"Forgot PAassword Link");
		String expected="Forgot Your Passport | Salesforce";
		String str=driver.getTitle();
		if(str.equals(expected))
			System.out.println("\n Forgot Your Password page loaded successfully ");
		String p_username=readPropertyData("myusername");
		WebElement username1=driver.findElement(By.xpath("//*[@id=\"un\"]"));
		sendKeys(driver, username1, Duration.ofSeconds(10), p_username,"Username Textbox");
		WebElement button1=driver.findElement(By.xpath("//*[@id=\"continue\"]"));
		clickOn(driver, button1, Duration.ofSeconds(10),"Continue button");
		WebElement msg1=driver.findElement(By.xpath("//*[@id=\"forgotPassForm\"]/div/p[1]"));
		if(msg1.isDisplayed())
			System.out.println("\n Test Case 4A passed - Password Reset message displayed successfully");
		
	}
	@Test
	public static void TC_4B() throws InterruptedException, IOException
	{
		//loginToSalesforce();
		//launchSalesforce();
		String p_username=readPropertyData("invalidusername");
		String p_pswd=readPropertyData("invalidpswd");
		WebElement username=driver.findElement(By.xpath("//*[@id=\"username\"]"));
		username.clear();
		WebElement pswd=driver.findElement(By.name("pw"));
		pswd.clear();
		LoginSFDC_TC02(username,p_username, pswd,p_pswd);
		WebElement errormsg2=driver.findElement(By.xpath("//*[@id=\"error\"]"));
		if(errormsg2.isDisplayed())
			System.out.println("\n Test Case 4B passed - Error message 'Login Attempt Failed' displayed successfully ");
		
	}
	@Test
	public static void TC05() throws IOException, InterruptedException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
		WebElement usermenu=findElement("//span[@id='userNavLabel']","xpath");
		if(WaitCheck(driver, usermenu, "UserMenu"))
			System.out.println("\nUserMenu element found ");
		else System.out.println("\nUserMenu element not found ");
		clickOn(driver, usermenu, Duration.ofSeconds(10),"User Menu Label");
		String[] expectedUserValues = {"My Profile","My Settings", "Developer Console","Switch to Lightning Experience","Logout"};
		for(int i=0;i<expectedUserValues.length;i++)
		{
			String actualValue=driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a"+"["+(i+1)+"]")).getText();
			if(actualValue.equals(expectedUserValues[i]))
			{
				System.out.println(expectedUserValues[i]+" is verified successfully ");
			}
		}
	}
	@Test
	
	public static void TC06() throws IOException, InterruptedException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
		WebElement usermenu=findElement("//span[@id='userNavLabel']","xpath");
		if(WaitCheck(driver, usermenu, "UserMenu"))
			System.out.println("\nUserMenu element found ");
		else System.out.println("\nUserMenu element not found ");
		clickOn(driver, usermenu, Duration.ofSeconds(10),"User Menu Label");
		String[] expectedUserValues = {"My Profile","My Settings", "Developer Console","Switch to Lightning Experience","Logout"};
		for(int i=0;i<expectedUserValues.length;i++)
		{
			String actualValue=driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a"+"["+(i+1)+"]")).getText();
			if(actualValue.equals(expectedUserValues[i]))
			{
				System.out.println(expectedUserValues[i]+" is verified successfully ");
			}
		}
		WebElement Myprofile=driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[1]"));
		clickOn(driver, Myprofile, Duration.ofSeconds(30),"My Profile Label");
		//driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		
		Thread.sleep(10000);
		WebElement Editprofilelink=driver.findElement(By.xpath("//a[@class='contactInfoLaunch editLink']//img[@title='Edit Profile']"));
		WaitCheck(driver, Editprofilelink, "Edit Profile Link");
		clickOn(driver, Editprofilelink, Duration.ofSeconds(40),"Edit Profile Link");
		Thread.sleep(4000);
		driver.switchTo().frame("contactInfoContentId");
		WebElement about_tab=driver.findElement(By.xpath("//*[@id=\"aboutTab\"]/a"));
		clickOn(driver, about_tab, Duration.ofSeconds(20),"About Tab");
		WebElement abouttab=driver.findElement(By.xpath("//*[@id=\"aboutTab\"]/a"));
		clickOn(driver, abouttab, Duration.ofSeconds(40),"About Tab");
		WebElement lastnamefield=driver.findElement(By.xpath("//*[@id=\"lastName\"]"));
		lastnamefield.clear();
		sendKeys(driver, lastnamefield, Duration.ofSeconds(40), "Athipatala", "Lastname Field");
		WebElement saveall=driver.findElement(By.xpath("//*[@id=\"TabPanel\"]/div/div[2]/form/div/input[1]"));
		clickOn(driver, saveall, Duration.ofSeconds(40), "SaveAll");
		driver.switchTo().defaultContent();
		Thread.sleep(5000);
		
		WebElement postlink=driver.findElement(By.xpath("//*[@id=\"publisherAttachTextPost\"]/span[1]"));
		clickOn(driver, postlink, Duration.ofSeconds(40), "Post Link");
		Thread.sleep(5000);
		WebElement frame1=driver.findElement(By.xpath("//iframe[@title='Rich Text Editor, publisherRichTextEditor']"));
		//Thread.sleep(5000);
		driver.switchTo().frame(frame1);
		
		WebElement textarea=driver.findElement(By.xpath("/html/body"));
		WaitCheck(driver, textarea, "text area");
		sendKeys(driver, textarea, Duration.ofSeconds(40),"Hello Everyone", "Text entered in textbox");
		driver.switchTo().defaultContent();
		WebElement sharebutton=driver.findElement(By.xpath("//*[@id=\"publishersharebutton\"]"));
		clickOn(driver, sharebutton, Duration.ofSeconds(40), "Share Button");
		
		//File upload-
		Thread.sleep(5000);
		WebElement filelink=driver.findElement(By.xpath("//a[@id='publisherAttachContentPost']"));
		clickOn(driver, filelink, Duration.ofSeconds(40), "File Link");
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("#chatterUploadFileAction")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("#chatterFile")).sendKeys("C:\\Users\\athip\\OneDrive\\Desktop\\assignments\\Day3.pdf");
		driver.findElement(By.cssSelector("#publishersharebutton")).click();
		Thread.sleep(10000);
			
		WebElement profilephoto=driver.findElement(By.xpath("//*[@id=\"photoSection\"]/span[2]"));
		Actions act=new Actions(driver);
		act.moveToElement(profilephoto).build().perform();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement uploadphoto=driver.findElement(By.id("uploadLink"));
		clickOn(driver, uploadphoto, Duration.ofSeconds(40), "Upload Photo");
		WebElement imageframe=driver.findElement(By.id("uploadPhotoContentId"));
		driver.switchTo().frame(imageframe);
		Thread.sleep(4000);
		WebElement choosefile=driver.findElement(By.xpath("//*[@id=\"j_id0:uploadFileForm:uploadInputFile\"]"));
		choosefile.sendKeys("C:\\Users\\athip\\OneDrive\\Pictures\\Camera Roll\\20140215_113005_Android.jpg");
		WebElement save=driver.findElement(By.xpath("//*[@id=\"j_id0:uploadFileForm:uploadBtn\"]"));
		clickOn(driver, save, Duration.ofSeconds(40),"Save Photo");
		Thread.sleep(10000);
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,500)");
		WebElement save1=driver.findElement(By.xpath("//*[@id=\"j_id0:j_id7:save\"]"));
		clickOn(driver, save1, Duration.ofSeconds(40), "Save Cropped Image");
		//driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		Thread.sleep(5000);
	}
	@Test
	public static void TC07() throws IOException, InterruptedException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
		WebElement usermenu=findElement("//span[@id='userNavLabel']","xpath");
		if(WaitCheck(driver, usermenu, "UserMenu"))
			System.out.println("\nUserMenu element found ");
		else System.out.println("\nUserMenu element not found ");
		clickOn(driver, usermenu, Duration.ofSeconds(10),"User Menu Label");
		String[] expectedUserValues = {"My Profile","My Settings", "Developer Console","Switch to Lightning Experience","Logout"};
		for(int i=0;i<expectedUserValues.length;i++)
		{
			String actualValue=driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a"+"["+(i+1)+"]")).getText();
			if(actualValue.equals(expectedUserValues[i]))
			{
				System.out.println(expectedUserValues[i]+" is verified successfully ");
			}
		}
		WebElement MySettings=driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[2]"));
		clickOn(driver, MySettings, Duration.ofSeconds(30),"My Settings");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		
		//loginhistorylink
		WebElement personal=driver.findElement(By.xpath("//tbody/tr[1]/td[1]/div[1]/div[4]/div[2]/a[1]"));
		clickOn(driver, personal,  Duration.ofSeconds(30), "Personal Tab");
		WebElement loginhistorylink=driver.findElement(By.xpath("//a[@id='LoginHistory_font']"));
		clickOn(driver, loginhistorylink, Duration.ofSeconds(30),"Login History Link");
		WebElement downloadlinl=driver.findElement(By.xpath("//a[contains(text(),'Download login history for last six months, includ')]"));
		clickOn(driver, downloadlinl, Duration.ofSeconds(30), "Download Login History");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String path=readPropertyData("defaultdownloadpath");
		boolean result=isFileDownloaded_Ext("C:\\Users\\athip\\Downloads",".csv");
		if(result)
			System.out.println("\n File downloaded in .CSV format ");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		
		//displayandlayout
		WebElement displayandlayout=driver.findElement(By.xpath("//span[@id='DisplayAndLayout_font']"));
		clickOn(driver, displayandlayout, Duration.ofSeconds(30), "Display and Layout Link");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement customisetabs=driver.findElement(By.xpath("//a[@id='CustomizeTabs_font']"));
		clickOn(driver, customisetabs, Duration.ofSeconds(30), "Customise My Tabs");
		WebElement select=driver.findElement(By.xpath("//select[@id='p4']"));
		Select op1=new Select(select);
		List<WebElement> dpdnvalues1=op1.getOptions();
		op1.selectByVisibleText("Salesforce Chatter");
		WebElement isselected=op1.getFirstSelectedOption();
		System.out.println("\n THe selected otpion is "+isselected.getText());
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		WebElement selecttab=driver.findElement(By.xpath("//select[@id='duel_select_1']"));
		Select op2=new Select(selecttab);
		List<WebElement> dpdnvalues2=op2.getOptions();
		op2.selectByVisibleText("Reports");
		
		WebElement save=driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
		clickOn(driver, save, Duration.ofSeconds(30), "save");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		
		/*WebElement toolbar=driver.findElement(By.xpath("//ul[@id='tabBar']"));
		Select op3=new Select(toolbar);
		List<WebElement> dpdnvalues3=op3.getOptions();
		verifyfromList(dpdnvalues3,"Reports");*/
		
		//email
		WebElement email=driver.findElement(By.xpath("//tbody/tr[1]/td[1]/div[1]/div[4]/div[4]/a[1]"));
		clickOn(driver, email, Duration.ofSeconds(30), "Email Link");
		WebElement myemailsettings=driver.findElement(By.xpath("//a[@id='EmailSettings_font']"));
		clickOn(driver, myemailsettings, Duration.ofSeconds(30), "My Email Settings");
		WebElement ename=driver.findElement(By.xpath("//input[@id='sender_name']"));
		ename.clear();
		sendKeys(driver, ename, Duration.ofSeconds(30), "Sudha Athipatala", "Email Name");
		WebElement eaddress=driver.findElement(By.xpath("//input[@id='sender_email']"));
		eaddress.clear();
		sendKeys(driver, eaddress, Duration.ofSeconds(30), "sathipatala@gmail.com", "Email Addrss");
		WebElement autobcc=driver.findElement(By.xpath("//input[@id='auto_bcc1']"));
		clickOn(driver, autobcc, Duration.ofSeconds(30), "Auto BCC : Yes");
		WebElement save2=driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
		clickOn(driver, save2, Duration.ofSeconds(30), "Save");
		String exp="Your settings have been successfully saved.";
		WebElement verify=driver.findElement(By.xpath("//div[contains(text(),'Your settings have been successfully saved.')]"));
		Assert.assertEquals(verify.getText(), exp,"Settings not saved");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		
		//calenders and reminders
		WebElement calandrem=driver.findElement(By.xpath("//span[@id='CalendarAndReminders_font']"));
		clickOn(driver, calandrem, Duration.ofSeconds(30), "Calenders and Reminders");
		WebElement actrem=driver.findElement(By.xpath("//a[@id='Reminders_font']"));
		clickOn(driver, actrem, Duration.ofSeconds(30), "Activity Reminders");
		WebElement testreminder=driver.findElement(By.xpath("//input[@id='testbtn']"));
		clickOn(driver, testreminder, Duration.ofSeconds(30), "Test Reminder Button");
		//handlePopup();
		//WebElement dismissall=driver.findElement(By.xpath("//*[@id=\"dismiss_all\"]"));
		//clickOn(driver, dismissall, Duration.ofSeconds(30), "Dismiss All");
		//dismisAlert();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		WebElement save3=driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
		clickOn(driver, save3, Duration.ofSeconds(30), "saVe");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	@Test
	public static void TC08() throws IOException, InterruptedException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
		WebElement usermenu=findElement("//span[@id='userNavLabel']","xpath");
		if(WaitCheck(driver, usermenu, "UserMenu"))
			System.out.println("\nUserMenu element found ");
		else System.out.println("\nUserMenu element not found ");
		clickOn(driver, usermenu, Duration.ofSeconds(10),"User Menu Label");
		String[] expectedUserValues = {"My Profile","My Settings", "Developer Console","Switch to Lightning Experience","Logout"};
		for(int i=0;i<expectedUserValues.length;i++)
		{
			String actualValue=driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a"+"["+(i+1)+"]")).getText();
			if(actualValue.equals(expectedUserValues[i]))
			{
				System.out.println(expectedUserValues[i]+" is verified successfully ");
			}
		}
		driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
		String parenthandle=driver.getWindowHandle();
		System.out.println("\nParent Window handle :"+parenthandle);
		WebElement devconsole=driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[3]"));
		WaitCheck(driver, devconsole, "Developer Console Link");
		clickOn(driver, devconsole, Duration.ofSeconds(40),"Developer Console Link");
		for(String handle : driver.getWindowHandles())
		{
			driver.switchTo().window(handle);
		}
		String title=driver.getTitle();
		System.out.println("\n The window title is :"+title);
		driver.close();
		driver.switchTo().window(parenthandle);
		String title1=driver.getTitle();
		System.out.println("\n The window title is :"+title1);
		
	}
	@Test
	public static void TC09() throws InterruptedException
	{
		LogoutSFDC();
	}
	@Test
	public static void TC10() throws InterruptedException, IOException
	{
		loginToSalesforce();
		WebElement accountstab=driver.findElement(By.xpath("//*[@id=\"Account_Tab\"]/a"));
		clickOn(driver, accountstab, Duration.ofSeconds(40),"Account Tab Link");
		
		handlePopup();
		
		WebElement accountshome=driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[1]/div[1]/h2"));
		WaitCheck(driver, accountshome, "Accounts page");
		WebElement newaccount=driver.findElement(By.xpath("//*[@id=\"hotlist\"]/table/tbody/tr/td[2]/input"));
		clickOn(driver, newaccount, Duration.ofSeconds(40),"Create New Account Link");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		WebElement accname=driver.findElement(By.id("acc2"));
		sendKeys(driver, accname, Duration.ofSeconds(10), "Auto1","Username Textbox");
		WebElement select=driver.findElement(By.xpath("//*[@id=\"acc6\"]/option[7]"));
		clickOn(driver, select, Duration.ofSeconds(40),"Technology Partner option");
		WebElement priority=driver.findElement(By.xpath("//*[@id=\"00N4x00000Wg9oN\"]/option[2]"));
		clickOn(driver, priority, Duration.ofSeconds(40),"Priority -High");
		WebElement save=driver.findElement(By.xpath("//*[@id=\"bottomButtonRow\"]/input[1]"));
		clickOn(driver, save, Duration.ofSeconds(40),"Save option");
		
	}
	@Test
	public static void TC11() throws InterruptedException, IOException
	{
		loginToSalesforce();
		WebElement accountstab=driver.findElement(By.xpath("//*[@id=\"Account_Tab\"]/a"));
		clickOn(driver, accountstab, Duration.ofSeconds(40),"Account Tab Link");
		handlePopup();
		WebElement newview=driver.findElement(By.xpath("//a[text()='Create New View']"));
		clickOn(driver, newview, Duration.ofSeconds(20),"Create New View");
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(40));
		WebElement viewname=driver.findElement(By.xpath("//*[@id=\"fname\"]"));
		sendKeys(driver, viewname, Duration.ofSeconds(20), "View1", "New View");
		WebElement viewUname=driver.findElement(By.xpath("//*[@id=\"devname\"]"));
		sendKeys(driver, viewUname, Duration.ofSeconds(20), "UniqueView1", "View Unique Name");
		WebElement save=driver.findElement(By.xpath("//input[@value=' Save ']"));
		clickOn(driver, save, Duration.ofSeconds(20), "Save Button");
	}
	@Test
	public static void TC12() throws InterruptedException, IOException
	{
		loginToSalesforce();
		WebElement accountstab=driver.findElement(By.xpath("//*[@id=\"Account_Tab\"]/a"));
		clickOn(driver, accountstab, Duration.ofSeconds(40),"Account Tab Link");
		//Waiting for the Popup to appear
		handlePopup();
		WebElement dropdown=driver.findElement(By.xpath("//*[@id=\"fcf\"]/option[8]"));
		clickOn(driver, dropdown, Duration.ofSeconds(20), "Dropdown Menu");
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		WebElement editoption=driver.findElement(By.xpath("//a[text()='Edit']"));
		clickOn(driver, editoption, Duration.ofSeconds(20), "Edit Button");
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		WebElement viewname=driver.findElement(By.xpath("//*[@id=\"fname\"]"));
		sendKeys(driver, viewname, Duration.ofSeconds(20), "View2", "New View");
		WebElement filter1=driver.findElement(By.xpath("//*[@id=\"fcol1\"]/option[2]"));
		clickOn(driver, filter1,Duration.ofSeconds(20), "Filter1");
		WebElement filter2=driver.findElement(By.xpath("//*[@id=\"fop1\"]/option[4]"));
		clickOn(driver, filter2,Duration.ofSeconds(20), "Filter2");
		WebElement filter3=driver.findElement(By.xpath("//*[@id=\"fval1\"]"));
		sendKeys(driver, filter3, Duration.ofSeconds(20), "a", "Filter3");
		WebElement save=driver.findElement(By.xpath("//*[@id=\"editPage\"]/div[3]/table/tbody/tr/td[2]/input[1]"));
		clickOn(driver, save, Duration.ofSeconds(20), "Save Button");
	}
	
	
	@Test
	public static void TC13() throws InterruptedException, IOException
	{
		loginToSalesforce();
		WebElement accountstab=driver.findElement(By.xpath("//*[@id=\"Account_Tab\"]/a"));
		clickOn(driver, accountstab, Duration.ofSeconds(40),"Account Tab Link");
		//Waiting for the Popup to appear
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
		WebElement merge=driver.findElement(By.xpath("//a[contains(text(),'Merge Accounts')]"));
		clickOn(driver, merge, Duration.ofSeconds(20), "Merge Accounts Link");
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(40));
		WebElement searchbox=driver.findElement(By.xpath("//input[@id='srch']"));
		sendKeys(driver, searchbox, Duration.ofSeconds(40), "Auto", "Search accounts with Auto");
		WebElement search=driver.findElement(By.xpath("//tbody/tr[1]/td[2]/form[1]/div[1]/div[2]/div[4]/input[2]"));
		clickOn(driver, search, Duration.ofSeconds(20), "Search Accounts button");
		WebElement cb1=driver.findElement(By.xpath("//input[@id='cid0']"));
		isSelected(cb1, "Result1");
		WebElement cb2=driver.findElement(By.xpath("//input[@id='cid1']"));
		isSelected(cb2, "Result2");
		WebElement next=driver.findElement(By.xpath("//tbody/tr[1]/td[2]/form[1]/div[1]/div[2]/div[1]/div[1]/input[1]"));
		clickOn(driver, next, Duration.ofSeconds(20), "Next button");
		WebElement merge1=driver.findElement(By.xpath("//input[@value=' Merge ']"));
		clickOn(driver, merge1, Duration.ofSeconds(20), "Merge Button");
		Alert alert = driver.switchTo().alert();
		driver.switchTo().alert();
		alert.accept();
		
	}
	
	public static void TC15() throws InterruptedException, IOException
	{
		loginToSalesforce();
		WebElement opp=driver.findElement(By.xpath("//*[@id=\"Opportunity_Tab\"]/a"));
		clickOn(driver, opp, Duration.ofSeconds(20), "Oppurtunity Link");
		
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
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
		String[] expectedUserValues = {"All Opportunities","Closing Next Month", "Closing This Month","My Opportunities",
				"New Last Week","New This Week","Opportunity Pipeline","Private","Recently Viewed Opportunities","Won"};
		WebElement menu=driver.findElement(By.cssSelector("#fcf"));
		clickOn(driver, menu, Duration.ofSeconds(20), "Oppoutunity Menu");
		Thread.sleep(5000);
		for(int i=0;i<expectedUserValues.length;i++)
		{
			String actualValue=driver.findElement(By.xpath("//*[@id=\"fcf\"]/option"+"["+(i+1)+"]")).getText();
			if(actualValue.equals(expectedUserValues[i]))
			{
				System.out.println(expectedUserValues[i]+" is verified successfully ");
			}
		}
		
	}
	@Test
	public static void TC16() throws InterruptedException, IOException
	{
		loginToSalesforce();
		WebElement opp=driver.findElement(By.xpath("//*[@id=\"Opportunity_Tab\"]/a"));
		clickOn(driver, opp, Duration.ofSeconds(20), "Oppurtunity Link");
		handlePopup();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
		WebElement newbutton=driver.findElement(By.xpath("//input[@title='New']"));
		clickOn(driver, newbutton, Duration.ofSeconds(20),"Create New OPP");
		//WebDriverWait wait1 = new WebDriverWait(driver,Duration.ofSeconds(40));
		Thread.sleep(5000);
		WebElement oppname=driver.findElement(By.xpath("//input[@id='opp3']"));
		sendKeys(driver, oppname, Duration.ofSeconds(20), "OPP1", "Opp Name");
		WebElement accname=driver.findElement(By.xpath("//input[@id='opp4']"));
		sendKeys(driver, accname, Duration.ofSeconds(20), "test1", "Account Name");
		WebElement leadsource=driver.findElement(By.xpath("//*[@id=\"opp6\"]/option[3]"));
		leadsource.click();
		WebElement closedate=driver.findElement(By.xpath("//input[@id='opp9']"));
		sendKeys(driver, closedate, Duration.ofSeconds(20), "09/25/2022", "Choose close date");
		//closedate.click();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		//driver.findElement(By.xpath("//a[contains(text(),'Today')]")).click();
		
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		WebElement stage=driver.findElement(By.xpath("//*[@id=\"opp11\"]/option[9]"));
		stage.click();
		//sendKeys(driver, stage, Duration.ofSeconds(20), "OPP1", "Stage");
		WebElement prop=driver.findElement(By.xpath("//*[@id=\"opp12\"]"));
		sendKeys(driver, prop, Duration.ofSeconds(20),"95%", "Propability");
		WebElement leadsrc=driver.findElement(By.xpath("//*[@id=\"opp6\"]/option[3]"));
		clickOn(driver, leadsrc, Duration.ofSeconds(20), "Lead Source");
		WebElement psrc=driver.findElement(By.xpath("//*[@id=\"opp17\"]"));
		sendKeys(driver, psrc, Duration.ofSeconds(20), "Web","Primary Campaign Source");
		WebElement save=driver.findElement(By.xpath("//*[@id=\"topButtonRow\"]/input[1]"));
		clickOn(driver, save, Duration.ofSeconds(20), "SAve Bttn");
		
	}
	@Test
	
	public static void TC17() throws InterruptedException, IOException
	{
		loginToSalesforce();
		WebElement opp=driver.findElement(By.xpath("//*[@id=\"Opportunity_Tab\"]/a"));
		clickOn(driver, opp, Duration.ofSeconds(20), "Oppurtunity Link");
		String str1=driver.getTitle();
		System.out.println("\n The page title is : "+str1);
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
		driver.manage().timeouts().pageLoadTimeout(4, TimeUnit.SECONDS);
		WebElement opppipeline=driver.findElement(By.xpath("//a[contains(text(),'Opportunity Pipeline')]"));
		clickOn(driver, opppipeline, Duration.ofSeconds(20), "Opportunity Pipeline Link");
		String str=driver.getTitle();
		System.out.println("\n The page title is : "+str);
	}
	@Test
	public static void TC18() throws InterruptedException, IOException
	{
		loginToSalesforce();
		WebElement opp=driver.findElement(By.xpath("//*[@id=\"Opportunity_Tab\"]/a"));
		clickOn(driver, opp, Duration.ofSeconds(20), "Oppurtunity Link");
		String str1=driver.getTitle();
		System.out.println("\n The page title is : "+str1);
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
		driver.manage().timeouts().pageLoadTimeout(4, TimeUnit.SECONDS);
		WebElement stuckopp=driver.findElement(By.xpath("//a[contains(text(),'Stuck Opportunities')]"));
		clickOn(driver, stuckopp, Duration.ofSeconds(20), "Stuck Opp Link");
		String str=driver.getTitle();
		System.out.println("\n The page title is : "+str);
	}
	@Test
	public static void loadOpportunities() throws InterruptedException, IOException
	{
		loginToSalesforce();
		WebElement opp=driver.findElement(By.xpath("//*[@id=\"Opportunity_Tab\"]/a"));
		clickOn(driver, opp, Duration.ofSeconds(20), "Oppurtunity Link");
		String str1=driver.getTitle();
		System.out.println("\n The page title is : "+str1);
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
		driver.manage().timeouts().pageLoadTimeout(4, TimeUnit.SECONDS);
	}
	@Test
	public static void TC19() throws InterruptedException, IOException
	{
		loginToSalesforce();
		WebElement opp=driver.findElement(By.xpath("//*[@id=\"Opportunity_Tab\"]/a"));
		clickOn(driver, opp, Duration.ofSeconds(20), "Oppurtunity Link");
		String str1=driver.getTitle();
		System.out.println("\n The page title is : "+str1);
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
		driver.manage().timeouts().pageLoadTimeout(4, TimeUnit.SECONDS);
		WebElement menu1=driver.findElement(By.xpath("//select[@id='quarter_q']"));
		Select op1=new Select(menu1);
		List<WebElement> dpdnvalues1=op1.getOptions();
		WebElement menu2=driver.findElement(By.xpath("//select[@id='open']"));
		Select op2=new Select(menu2);
		List<WebElement> dpdnvalues2=op2.getOptions();
		
		String value1=dpdnvalues1.get(1).getText();
		String value2=dpdnvalues2.get(1).getText();
		System.out.println("\n First option to select :"+value1);
		System.out.println("\n Next option to select :"+value2);
		//System.out.println("\n The values of the dropdown from menu 1 are :"+value);
		op1.selectByVisibleText(value1);
		op2.selectByVisibleText(value2);
		WebElement runreport=driver.findElement(By.xpath("//tbody/tr[3]/td[1]/input[1]"));
		clickOn(driver, runreport, Duration.ofSeconds(20), "Run Report");
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		//loadOpportunities();
	}
	@Test
	public static void TC20() throws InterruptedException, IOException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		WebElement leadslink=driver.findElement(By.linkText("Leads"));
		clickOn(driver, leadslink, Duration.ofSeconds(20), "Leads Link");
		String str=driver.getTitle();
		System.out.println("\n THe title is :"+str);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		handlePopup();
	}
	@Test
	public static void TC21() throws InterruptedException, IOException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		WebElement leadslink=driver.findElement(By.linkText("Leads"));
		clickOn(driver, leadslink, Duration.ofSeconds(20), "Leads Link");
		String str=driver.getTitle();
		System.out.println("\n THe title is :"+str);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		handlePopup();
		WebElement dropdown=driver.findElement(By.cssSelector("#fcf"));
		clickOn(driver, dropdown, Duration.ofSeconds(10),"Leads Dropdown");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		String[] expectedUserValues = {"All Open Leads","My Unread Leads", "Recently Viewed Leads","Today's Leads","View - Custom 1","View - Custom 2"};
		for(int i=0;i<expectedUserValues.length;i++)
		{
			String actualValue=driver.findElement(By.xpath("//*[@id=\"fcf\"]/option"+"["+(i+1)+"]")).getText();
			if(actualValue.equals(expectedUserValues[i]))
			{
				System.out.println(expectedUserValues[i]+" is verified successfully ");
			}
		}
	}
	@Test
	public static void TC23() throws InterruptedException, IOException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		WebElement leadslink=driver.findElement(By.linkText("Leads"));
		clickOn(driver, leadslink, Duration.ofSeconds(20), "Leads Link");
		String str=driver.getTitle();
		System.out.println("\n THe title is :"+str);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		handlePopup();
		WebElement dropdown=driver.findElement(By.cssSelector("#fcf"));
		clickOn(driver, dropdown, Duration.ofSeconds(10),"Leads Dropdown");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		WebElement todayleads=driver.findElement(By.xpath("//*[@id=\"fcf\"]/option[4]"));
		clickOn(driver, todayleads, Duration.ofSeconds(10), "Todays Leads");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		//String str=driver.getTitle();
		//System.out.println("\n The title of the page is : "+str);
		String Expected="Today's Leads";
		Assert.assertEquals(Expected, todayleads.getText());
		System.out.println("\n Todays Leads successfully displayed");
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}
	@Test
	public static void TC22() throws InterruptedException, IOException
	{
		//TC23();
		//driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		WebElement leadslink=driver.findElement(By.linkText("Leads"));
		clickOn(driver, leadslink, Duration.ofSeconds(20), "Leads Link");
		String str=driver.getTitle();
		System.out.println("\n THe title is :"+str);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		handlePopup();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		WebElement dropdown=driver.findElement(By.cssSelector("#fcf"));
		clickOn(driver, dropdown, Duration.ofSeconds(10),"Leads Dropdown");
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		WebElement todayleads=driver.findElement(By.xpath("//*[@id=\"fcf\"]/option[4]"));
		clickOn(driver, todayleads, Duration.ofSeconds(10), "Todays Leads");
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		//String str=driver.getTitle();
		//System.out.println("\n The title of the page is : "+str);
		String Expected="Today's Leads";
		Assert.assertEquals(Expected, todayleads.getText());
		System.out.println("\n Todays Leads successfully displayed");
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		
		WebElement op1=driver.findElement(By.xpath("//option[contains(text(),'My Unread Leads')]"));
		clickOn(driver, op1, Duration.ofSeconds(10), "My Unread Leads");
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		LogoutSFDC();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		TC20();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		WebElement go=driver.findElement(By.xpath("//*[@id=\"filter_element\"]/div/span/span[1]/input"));
		clickOn(driver, go, Duration.ofSeconds(10), "Go Button");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		String exp="My Unread Leads";
		String actual=driver.findElement(By.xpath("//select[@id='fcf']")).getText();
		System.out.println("\n Actual is :"+actual);
		Assert.assertEquals(exp,actual);
		
	}
	@Test
	public static void TC24() throws InterruptedException, IOException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		WebElement leadslink=driver.findElement(By.linkText("Leads"));
		clickOn(driver, leadslink, Duration.ofSeconds(20), "Leads Link");
		String str=driver.getTitle();
		System.out.println("\n THe title is :"+str);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		handlePopup();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		WebElement newbttn=driver.findElement(By.xpath("//*[@id=\"hotlist\"]/table/tbody/tr/td[2]/input"));
		clickOn(driver, newbttn,Duration.ofSeconds(10), "New Button");
		WebElement lastname=driver.findElement(By.xpath("//input[@id='name_lastlea2']"));
		sendKeys(driver, lastname, Duration.ofSeconds(10), "ABCD", "LastName");
		WebElement companyname=driver.findElement(By.xpath("//input[@id='lea3']"));
		sendKeys(driver, companyname, Duration.ofSeconds(10), "ABCD", "CompanyName");
		WebElement save=driver.findElement(By.xpath("//body[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[2]/form[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[2]/input[1]"));
		clickOn(driver, save,  Duration.ofSeconds(10), "Save Button");
		
		
	}
	@Test
	public static void TC25() throws InterruptedException, IOException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement contactlink=driver.findElement(By.xpath("//a[contains(text(),'Contacts')]"));
		clickOn(driver, contactlink, Duration.ofSeconds(10), "Contacts Link");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		handlePopup();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		WebElement newbttn=driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
		clickOn(driver, newbttn, Duration.ofSeconds(10),"New Button");
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement lastname=driver.findElement(By.xpath("//input[@id='name_lastcon2']"));
		sendKeys(driver, lastname, Duration.ofSeconds(10), "Athipatala", "Last Name");
		WebElement accname=driver.findElement(By.xpath("//input[@id='con4']"));
		sendKeys(driver, accname, Duration.ofSeconds(10), "sudha", "Account Name");
		WebElement save=driver.findElement(By.xpath("//body[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[2]/form[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[2]/input[1]"));
		clickOn(driver, save, Duration.ofSeconds(10), "SaveButton");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
	}
	@Test
	
	public static void TC26() throws InterruptedException, IOException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement contactlink=driver.findElement(By.xpath("//a[contains(text(),'Contacts')]"));
		clickOn(driver, contactlink, Duration.ofSeconds(10), "Contacts Link");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		handlePopup();
		WebElement createnewview=driver.findElement(By.xpath("//a[contains(text(),'Create New View')]"));
		clickOn(driver, createnewview, Duration.ofSeconds(10),"Create New View");
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		WebElement viewname=driver.findElement(By.xpath("//*[@id=\"fname\"]"));
		String exp="TestView6";
		sendKeys(driver, viewname, Duration.ofSeconds(10), exp, "View Name");
		WebElement Uviewname=driver.findElement(By.xpath("//*[@id=\"devname\"]"));
		sendKeys(driver, Uviewname, Duration.ofSeconds(10), exp, "Unique View Name");
		WebElement save=driver.findElement(By.xpath("//*[@id=\"editPage\"]/div[1]/table/tbody/tr/td[2]/input[1]"));
		clickOn(driver, save, Duration.ofSeconds(10), "Save Button");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Thread.sleep(2000);
		Select select=new Select(driver.findElement(By.xpath("//select[@id='fcf']")));
		WebElement op=select.getFirstSelectedOption();
		String act=op.getText();
		System.out.println("\n The selected value is :"+act);
		//String act=driver.findElement(By.xpath("//*[@id=\"00B4x00000IQmm5_listSelect\"]")).getText();
		//Assert.assertEquals(act, exp, "Xhecking");
	}
	
	@Test
	public static void TC27() throws InterruptedException, IOException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement contactlink=driver.findElement(By.xpath("//a[contains(text(),'Contacts')]"));
		clickOn(driver, contactlink, Duration.ofSeconds(10), "Contacts Link");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		handlePopup();
		WebElement recentlycr=driver.findElement(By.xpath("//option[contains(text(),'Recently Created')]"));
		clickOn(driver, recentlycr, Duration.ofSeconds(10),"Recently Created");
		
	}
	@Test
	public static void TC28() throws InterruptedException
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement contactlink=driver.findElement(By.xpath("//a[contains(text(),'Contacts')]"));
		clickOn(driver, contactlink, Duration.ofSeconds(10), "Contacts Link");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		handlePopup();
		WebElement mycontacts=driver.findElement(By.xpath("//option[contains(text(),'My Contacts')]"));
		clickOn(driver, mycontacts, Duration.ofSeconds(10), "My Contacts Link");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		report.logTestinfo("Test Case Passed");
		//Thread.sleep(4000);
		//Select sel=new Select(driver.findElement(By.xpath("//select[@class='title']")));
		//String CV=sel.getFirstSelectedOption().getText();
		//WebElement op=driver.findElement(By.xpath("//select[@id='00B4x00000GllVg_listSelect']"));
		//String act=op.getText();
		//System.out.println("\n The selected value is :"+CV);
	}
	public static void TC29()
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement contactlink=driver.findElement(By.xpath("//a[contains(text(),'Contacts')]"));
		clickOn(driver, contactlink, Duration.ofSeconds(10), "Contacts Link");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		handlePopup();
	}
	@Test
	public static void TC30()
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement contactlink=driver.findElement(By.xpath("//a[contains(text(),'Contacts')]"));
		clickOn(driver, contactlink, Duration.ofSeconds(10), "Contacts Link");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		handlePopup();
		WebElement createnewview=driver.findElement(By.xpath("//*[@id=\"filter_element\"]/div/span/span[2]/a[2]"));
		clickOn(driver, createnewview, Duration.ofSeconds(10),"Create New View");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		WebElement viewUname=driver.findElement(By.xpath("//*[@id=\"devname\"]"));
		sendKeys(driver, viewUname, Duration.ofSeconds(10), "EFGH", "View Unique Name");
		WebElement save=driver.findElement(By.xpath("//*[@id=\"editPage\"]/div[1]/table/tbody/tr/td[2]/input[1]"));
		clickOn(driver, save, Duration.ofSeconds(10), "Save");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		WebElement errormsg=driver.findElement((By.xpath("//*[@id=\"editPage\"]/div[2]/div[1]/div[2]/table/tbody/tr[1]/td[2]/div/div[2]")));
		String str=errormsg.getText();
		System.out.println("\n The error msg is "+str);
		String exp="Error: You must enter a value";
		Assert.assertEquals(str, exp,"Error messages are not the same");
		report.logTestPassed(" TestCase passed ");
	}
	@Test
	public static void TC31()
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement contactlink=driver.findElement(By.xpath("//a[contains(text(),'Contacts')]"));
		clickOn(driver, contactlink, Duration.ofSeconds(10), "Contacts Link");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		handlePopup();
		WebElement createnewview=driver.findElement(By.xpath("//*[@id=\"filter_element\"]/div/span/span[2]/a[2]"));
		clickOn(driver, createnewview, Duration.ofSeconds(10),"Create New View");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		WebElement viewname=driver.findElement(By.xpath("//*[@id=\"fname\"]"));
		sendKeys(driver, viewname,Duration.ofSeconds(10),"ABCD", "View Name");
		WebElement viewUname=driver.findElement(By.xpath("//*[@id=\"devname\"]"));
		sendKeys(driver, viewUname, Duration.ofSeconds(10), "EFGH", "View Unique Name");
		WebElement cancel=driver.findElement(By.xpath("//*[@id=\"editPage\"]/div[1]/table/tbody/tr/td[2]/input[2]"));
		clickOn(driver, cancel, Duration.ofSeconds(10), "Cancel");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		WebElement verifyview=driver.findElement(By.xpath("//*[@id=\"fcf\"]"));
		
		Select op1=new Select(verifyview);
		List<WebElement> dpdnvalues1=op1.getOptions();
		//op1.selectByVisibleText("Salesforce Chatter");
		WebElement isselected=op1.getFirstSelectedOption();
		report.logTestinfo("\n THe selected view is "+isselected.getText());
		report.logTestPassed(" TestCase passed ");
	}
	
	
	@Test
	public static void TC32()
	{
		loginToSalesforce();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement contactlink=driver.findElement(By.xpath("//a[contains(text(),'Contacts')]"));
		clickOn(driver, contactlink, Duration.ofSeconds(10), "Contacts Link");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		handlePopup();
		WebElement newcontact=driver.findElement(By.xpath("//*[@id=\"hotlist\"]/table/tbody/tr/td[2]/input"));
		clickOn(driver, newcontact, Duration.ofSeconds(10), "New Contact");
		WebElement lastname=driver.findElement(By.xpath("//*[@id=\"name_lastcon2\"]"));
		sendKeys(driver, lastname, Duration.ofSeconds(10), "Indian", "Last Name");
		WebElement accname=driver.findElement(By.xpath("//*[@id=\"con4\"]"));
		sendKeys(driver, lastname, Duration.ofSeconds(10), "Global Media", "Accont Name");
		WebElement saveNew=driver.findElement(By.xpath("//*[@id=\"topButtonRow\"]/input[2]"));
		clickOn(driver, saveNew, Duration.ofSeconds(10), "SAve and New");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}
	@Test
	public static void TC33()
	{
		loginToSalesforce();
		WebElement hometab=driver.findElement(By.xpath("//*[@id=\"home_Tab\"]/a"));
		clickOn(driver, hometab, Duration.ofSeconds(10), "Home Tab");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement userprofilepage=driver.findElement(By.linkText("su Athipatala"));
		if(userprofilepage.isDisplayed())
			report.logTestinfo("User profile name displayed as a link");
		clickOn(driver, userprofilepage, Duration.ofSeconds(10), "User Profile Link");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		String str=driver.getTitle();
		System.out.println("THe title is "+str);
	}
	@Test
	public static void TC34() throws InterruptedException
	{
		loginToSalesforce();
		WebElement hometab=driver.findElement(By.xpath("//*[@id=\"home_Tab\"]/a"));
		clickOn(driver, hometab, Duration.ofSeconds(10), "Home Tab");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		handlePopup();
		Thread.sleep(5000);
		WebElement userprofilepage=driver.findElement(By.xpath("//tbody/tr[1]/td[2]/div[1]/div[1]/div[1]/div[2]/span[1]/h1[1]/a[1]"));
		if(userprofilepage.isDisplayed())
			report.logTestinfo("User profile name displayed as a link");
		clickOn(driver, userprofilepage, Duration.ofSeconds(10), "User Profile Link");
		//driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		Thread.sleep(5000);
		
		WebElement editprofile=driver.findElement(By.xpath("//*[@id=\"chatterTab\"]/div[2]/div[2]/div[1]/h3/div/div/a/img"));
		clickOn(driver, editprofile, Duration.ofSeconds(10), "Edit Profile Link");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.switchTo().frame("contactInfoContentId");
		WebElement about_tab=driver.findElement(By.xpath("//*[@id=\"aboutTab\"]/a"));
		clickOn(driver, about_tab, Duration.ofSeconds(20),"About Tab");
		WebElement abouttab=driver.findElement(By.xpath("//*[@id=\"aboutTab\"]/a"));
		clickOn(driver, abouttab, Duration.ofSeconds(40),"About Tab");
		WebElement lastnamefield=driver.findElement(By.xpath("//*[@id=\"lastName\"]"));
		lastnamefield.clear();
		sendKeys(driver, lastnamefield, Duration.ofSeconds(40), "ABCD", "Lastname Field");
		WebElement saveall=driver.findElement(By.xpath("//*[@id=\"TabPanel\"]/div/div[2]/form/div/input[1]"));
		clickOn(driver, saveall, Duration.ofSeconds(40), "SaveAll");
		driver.switchTo().defaultContent();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}
	
}
