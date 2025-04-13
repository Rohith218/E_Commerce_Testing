package pages;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import io.qameta.allure.Allure;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.Assertion;

import Resources.ExcelReader;
import setup.Setup;

public class Login_Page extends Setup {

	public static HashMap<String, String> hmap;
	public static WebElement user,pw,login,logbut,inc,homepage,logout;
	public static Logger logs = LogManager.getLogger(Login_Page.class);

//	public Login_Page(String string) {
//		// TODO Auto-generated constructor stub
//		string = "locators";
//	}

	public void initialize() throws IOException {
//		Setup s = new Setup("Envconfig");
//		setup();
		setProp();
		ExcelReader xc = new ExcelReader(locators,Sheet);
		hmap = xc.readExcel();
		if (driver == null) {
			throw new IllegalStateException("driver not initialized");
		} else {
			System.out.println("Driver Initialized successfully");
		}
		Actions a = new Actions(driver);
		user = driver.findElement(By.id(hmap.get("user")));
		pw = driver.findElement(By.id(hmap.get("pw")));
		login = driver.findElement(By.className(hmap.get("login")));
		logout = driver.findElement(By.className(hmap.get("logout")));
		logbut = driver.findElement(By.className(hmap.get("login_button")));
	}

	public boolean enterCorrectCred() {
		user.clear();
		user.sendKeys(hmap.get("UserName"));
		logs.info("User name entered");
		pw.clear();
		pw.sendKeys(hmap.get("password"));
		logs.info("pw entered");

		login();
		boolean signoutdisplay = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			inc = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(hmap.get("incorrect_login"))));
			logs.fatal("Login failed: " + inc.getText());
		} catch (Exception e) {
			// TODO: handle exception
			logs.info("Page loading with correct pw");
			System.out.println("Page loading with correct pw");
			signoutdisplay = true;
		}
		return signoutdisplay;
	}

	public boolean isLogoutDisplayed() {
		boolean x = false;
		try
		{
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));
			logout = wait.until(ExpectedConditions.visibilityOf(logout));
			logs.info("logout  displayed");
			x = true;
		}catch(Exception e) {
			logs.info("logout is not displayed");
		}
		
		return x;
	}
	
	public boolean isLogInDisplayed() {
		logout.click();
		boolean x = false;
		try
		{
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));
			logbut = wait.until(ExpectedConditions.visibilityOf(logbut));
			String s = logbut.getText();
			System.out.println(s+"*******************************************************");
			if(s.equalsIgnoreCase("login")) {
				logs.info("login  displayed");
				x = true;
			}
		}catch(Exception e) {
			logs.info("login is not displayed");
		}
		
		return x;
	}
	
	
	public boolean enterInCorrectCred() {
		user.clear();
		user.sendKeys(hmap.get("Incorrect_user"));
		logs.info("User name entered");
		pw.clear();
		pw.sendKeys(hmap.get("Incorrect_pw"));
		logs.info("pw entered");
		boolean signoutdisplay = true;
		login();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			inc = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(hmap.get("incorrect_login"))));
			logs.info("Login failed: " + inc.getText());
		} catch (Exception e) {
			// TODO: handle exception
			logs.fatal("Page loading with incorrect pw");
			System.out.println("Page loading with incorrect pw");
		}
		
		try
		{
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));
			logout = wait.until(ExpectedConditions.visibilityOf(logout));
			logs.info("logout  displayed");
			signoutdisplay = false;
		}catch(Exception e) {
			logs.info("logout is not displayed");
		}
		return signoutdisplay;
	}

	public boolean enterEmptyCred() throws InterruptedException {
		user.clear();
		logs.info("User name cleared");
		pw.clear();
		logs.info("pw cleared");
		boolean signoutdisplay = false;
		login();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			inc = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(hmap.get("incorrect_login"))));
			logs.info("Login failed: " + inc.getText());
		} catch (Exception e) {
			// TODO: handle exception
			signoutdisplay = true;
			logs.fatal("Page loading with incorrect pw");
		}
		
		return signoutdisplay;
	}
	public static void login() {
		login.click();
		logs.info("logged in");
	}

	public static void main(String Args[]) throws IOException {
//		initialize();
//		Assertion a = new Assertion();
//		a.assertEquals(enterInCorrectCred(), true);
//		Allure.description("After giving valid credintials of the user, user will be able to successfully login " +
//                "and after login logout button will be displayed");
//		System.out.println(inc.getText());;
	}
} 