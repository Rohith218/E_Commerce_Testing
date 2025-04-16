package Resources;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import setup.Setup;

import java.io.File;

public class Screenshot extends Setup{
    public void takeScreenshot( String fileName) {
        try {
            // Convert WebDriver object to TakesScreenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;

            // Capture screenshot as a File
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

            // Define destination path
            File destFile = new File("./screenshots/" + fileName + ".png");

            // Copy file to destination
            FileHandler.copy(srcFile, destFile);

            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

