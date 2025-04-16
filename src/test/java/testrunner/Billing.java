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

import com.google.protobuf.Method;

import Resources.ExcelReader;
import Resources.Screenshot;
import pages.Book_Selection;
import pages.Login_Page;
import setup.Setup;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

public class Billing extends Setup {


	Login_Page loginPage;
	Book_Selection bs;
    SoftAssert a;
    Screenshot s;
	
	@BeforeMethod
	public void setup() throws IOException {
		logs.info("========== Starting Test: " + Method.getDescriptor() + " ==========");
        super.setup();
        loginPage = new Login_Page();
        bs = new Book_Selection();
        a = new SoftAssert();
        s = new Screenshot();
        loginPage.initialize();
		loginPage.enterCorrectCred();
		bs.initialize();
	}
	@Test(priority = 1,description = "Sample")
	public void Selection() throws IOException, InterruptedException, AWTException {
		bs.selection();
		bs.cart();
		bs.displayTotal();
		bs.checkOut();
		a.assertTrue(bs.payment(), "Checkout not done");
		s.takeScreenshot("Checkout page");
	}
	@Test(priority = 2,description = "Sample2formultipleScenarioInstance")
	public void Selection2() throws IOException, InterruptedException, AWTException {
		bs.selection();
		bs.cart();
		bs.displayTotal();
		bs.checkOut();
		a.assertTrue(bs.payment(), "Checkout not done");
		s.takeScreenshot("Checkout page");
	}
	
	@AfterMethod
	public void tearDown() throws IOException, InterruptedException {
		logs.info("========== Starting Test: " + Method.getDescriptor() + " ==========");
        super.closeWeb();
	}
}
