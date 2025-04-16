package Resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import setup.Setup;

import java.io.File;

public class Screenshot extends Setup{
	
	public Logger logs = LogManager.getLogger(Screenshot.class);
    public void takeScreenshot( String fileName) {
        try {
            TakesScreenshot ss = (TakesScreenshot) driver;
            
            File src = ss.getScreenshotAs(OutputType.FILE);
            
            File des = new File("./screenshot/"+ fileName + ".png");
            
            FileHandler.copy(src, des);
            
            logs.info("Screenshot saved: " + des.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

