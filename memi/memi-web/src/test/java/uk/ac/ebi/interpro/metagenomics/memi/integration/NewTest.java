package uk.ac.ebi.interpro.metagenomics.memi.integration;
import static junit.framework.Assert.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NewTest {
    private WebDriver driver;
    private ScreenshotHelper screenshotHelper;
    private boolean takeScreenShot = true;
    private WebDriverWait wait;
    private String memiURL= "http://localhost:8082/metagenomics/";
    private final static Log log = LogFactory.getLog(NewTest.class);

    @Test
    public void testHomePage() {
        String title = driver.getTitle();
        assertTrue("The title should has EBI", title.contains("EBI metagenomics"));

//      Test the existence of the jumbo header and the option to hide it
        WebElement jumbo = driver.findElement(By.cssSelector(".jumbo-header"));
        assertTrue(jumbo.isDisplayed());
        driver.findElement(By.cssSelector(".show_tooltip")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".show_tooltip")));
        jumbo = driver.findElement(By.cssSelector(".jumbo-header"));
        assertFalse(jumbo.isDisplayed());


        takeScreenShot = false;
    }
    @Before
    public void beforeTest() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 3);
        driver.get(memiURL);
        wait.until(ExpectedConditions.titleContains("EBI"));
        screenshotHelper = new ScreenshotHelper();
        takeScreenShot = true;
    }
    @After
    public void afterTest() throws IOException {
        if (takeScreenShot) {
            String uuid = "target/screenshot_"+UUID.randomUUID().toString()+".png";
            log.error("Trying to create a screenshot for the failed test: ["+uuid+"]");
            try {
                screenshotHelper.saveScreenshot(uuid);
            } catch (Exception e){
                log.error("Screenshot not saved: "+e);
            }
        }
        driver.quit();
    }
    private class ScreenshotHelper {

        public void saveScreenshot(String screenshotFileName) throws IOException {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(screenshotFileName));
        }
    }
}