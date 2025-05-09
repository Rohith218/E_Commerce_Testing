package testrunner;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.protobuf.Method;

import Resources.ExcelReader;
import Resources.Screenshot;
import pages.Login_Page;
import setup.Setup;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

public class LoginPage_Runner extends Setup {


	Login_Page loginPage;
    SoftAssert a;
    Screenshot s;
    ExtentReports extent;
    
    ExtentTest test;
	
	@BeforeMethod
	public void setup() throws IOException {
		logs.info("========== Starting Test: " + Method.getDescriptor() + " ==========");
        super.setup();
        loginPage = new Login_Page();
        a = new SoftAssert();
        s = new Screenshot();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Tester", "Rohith");
	}
	
	@Test(priority = 1, description = "User gives valid credentials and login is successful")
	public void testLoginWithValidCreds() throws IOException, InterruptedException {
		loginPage.initialize();
		boolean isLoggedIn = loginPage.enterCorrectCred();
		a.assertTrue( isLoggedIn,"Login Successful.");
		if(isLoggedIn) {
			logs.info("Login Success");
			s.takeScreenshot("SuccessfullLogin");
		}else {
			logs.fatal("Login Failed");
			s.takeScreenshot("Login Failed");
		}
		a.assertAll();
	}

	@Test(priority = 3, description = "logout button displayed")
	public void testLogOutButton() throws IOException, InterruptedException {
		loginPage.initialize();
		boolean isLoggedIn = loginPage.enterCorrectCred();
		a.assertTrue( isLoggedIn,"Login failed! Logout button not displayed.");
		a.assertTrue(isLoggedIn, "Not logged in");
//		Actions ab = new Actions(driver);
//		ab.keyDown(Keys.ARROW_DOWN).keyUp(Keys.ARROW_DOWN).perform();
		
		boolean logoutButtonDisplay = loginPage.isLogoutDisplayed();
		if (logoutButtonDisplay) {
			logs.info("logout button available");
			s.takeScreenshot("logout button available");
		} else {
			logs.fatal("logout notbutton available");
			s.takeScreenshot("logout notbutton available");
		}
		a.assertTrue( logoutButtonDisplay,"logout Button Displayed");
		a.assertAll();
	}

	@Test(priority = 2, description = "User gives incorrect credentials and login fails")
	public void testLoginWithInvalidCreds() throws IOException, InterruptedException {
		loginPage.initialize();
		boolean isLoggedIn = loginPage.enterInCorrectCred();
		System.out.println(isLoggedIn + " loged in or not");
		a.assertFalse( isLoggedIn,"Login should fail with incorrect credentials.");
		if(!isLoggedIn) {
			logs.info("Login should fail with incorrect credentials...");
			s.takeScreenshot("Login should fail with incorrect credentials...");
			
		}else {
			logs.fatal("Login sucess");
			s.takeScreenshot("Login sucess");
		}
		a.assertAll();

	}

	@Test(priority = 4, description = "User able to logout")
	public void testLogOut() throws IOException, InterruptedException {
		loginPage.initialize();
		boolean isLoggedIn = loginPage.enterCorrectCred();
		a.assertTrue( isLoggedIn,"Login failed! Logout button not displayed.");
		boolean isLogInDisplayed = loginPage.isLogInDisplayed();
		a.assertTrue( isLogInDisplayed,"Login Button Displayed after successful Logout!");
		
		if (isLogInDisplayed) {
			logs.info("Login Button Displayed after successful Logout!");
			s.takeScreenshot("Login Button Displayed after successful Logout");
		} else {
			logs.fatal("login notbutton available");
			s.takeScreenshot("login notbutton available");
		}
		a.assertAll();
	}

	@Test(priority = 5, description = "empty user cred")
	public void testEmptyUserCred() throws IOException, InterruptedException {
		loginPage.initialize();
		boolean isLoggedIn = loginPage.enterEmptyCred();
		a.assertFalse( isLoggedIn,"Login should fail with empty credentials!");
		System.out.println(isLoggedIn);
		if(isLoggedIn) {
			logs.fatal("Login sucess with empty cred");
			s.takeScreenshot("Login sucess with empty cred");
		}else {
			logs.info("Login should fail with empty credentials.");
			s.takeScreenshot("Login should fail with empty credentials.");
		}
		a.assertAll();

	}
	
	@AfterMethod
	public void closeWeb() throws InterruptedException {
		super.closeWeb();
	}
}