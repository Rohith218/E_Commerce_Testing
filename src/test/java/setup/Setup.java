package setup;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;

import Resources.ExcelReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import jdk.internal.org.jline.utils.Log;

public class Setup {

	public static WebDriver driver;
	public static Logger logs = LogManager.getLogger(Setup.class);
	public static Properties prop = new Properties();
	public static String WorkBook, Sheet, locators;

	public static void setProp() throws IOException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\java\\utils\\prop.properties");
		prop.load(fis);
		WorkBook = prop.getProperty("workbook");
		Sheet = prop.getProperty("sheet");
		locators = prop.getProperty("locators");
	}

	@SuppressWarnings("null")
	public void setup() throws IOException {
		setProp();
		ExcelReader xc = new ExcelReader(WorkBook, Sheet);
		HashMap<String, String> map = xc.readExcel();
		if (map.get("browser").equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (map.get("browser").equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else {
			logs.info("Enter Valid Browser from chrome/edge");
		}

		PropertyConfigurator.configure(map.get("log"));
		driver.get(map.get("URL"));
		driver.manage().window().maximize();
		logs.info("Driver Opened");
	}

	public void closeWeb() throws InterruptedException {
		if (driver != null) {
			Thread.sleep(5000);
			driver.quit(); // Use quit() to close all browser windows & end WebDriver session
			logs.info("Browser closed successfully.");
		} else {
			logs.warn("WebDriver was not initialized, so no browser to close.");
		}
	}
}