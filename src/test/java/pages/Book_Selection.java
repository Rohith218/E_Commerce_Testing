package pages;

import java.util.HashMap;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import io.qameta.allure.Allure;
import setup.Setup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.Assertion;

import Resources.ExcelReader;


public class Book_Selection extends Setup{
	
	public static WebElement book1,book2,cart,co,amt,tym;
	public static HashMap<String,String> hmap;
	public static Login_Page lp;
//	public Logger logs = LogManager.getLogger(Book_Selection.class);
	
	public void initialize() throws IOException {
//		setup();
//		setProp();
		ExcelReader xc = new ExcelReader(locators,Sheet);
		hmap = xc.readExcel();
		if (driver == null) {
			throw new IllegalStateException("driver not initialized");
		} else {
			System.out.println("Driver Initialized successfully");
		}
		Actions a = new Actions(driver);
		book1 = driver.findElement(By.xpath(hmap.get("ToKillAMockBird")));
//		book2 = driver.findElement(By.xpath(hmap.get("WarAndPeace")));
		cart = driver.findElement(By.xpath(hmap.get("Cart")));
		System.out.println(hmap.get("ToKillAMockBird"));
		System.out.println(hmap.get("WarAndPeace"));
	}

	public void selection() throws IOException, InterruptedException, AWTException {
		Actions a = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)", "");
		Thread.sleep(3000);
		book1.click();
		book1 = driver.findElement(By.xpath(hmap.get("ToKillAMockBird")));
		book1.click();
		book2 = driver.findElement(By.xpath(hmap.get("WarAndPeace")));
		book2.click();
		a.keyDown(Keys.HOME).keyDown(Keys.HOME).perform();
	}
	
	public void cart() {
		cart.click();
	}
	public void checkOut() {
		co = driver.findElement(By.xpath(hmap.get("co")));
		co.click();
	}
	public void displayTotal() {
		amt = driver.findElement(By.cssSelector(hmap.get("amt")));
		String amt1 = amt.getText();
		System.out.println(amt1);
	}
	
	public boolean payment() {
		boolean pay = false;
		tym = driver.findElement(By.xpath(hmap.get("ty")));
		String s = tym.getText();
		if(s.equalsIgnoreCase("Thank you For Shopping With Us")) {
			pay = true;
			logs.info("Payment done as expected");
		}
		return pay;
	}

}
