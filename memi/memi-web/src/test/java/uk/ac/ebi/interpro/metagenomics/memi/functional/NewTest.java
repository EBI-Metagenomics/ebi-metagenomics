package uk.ac.ebi.interpro.metagenomics.memi.functional;
import static junit.framework.Assert.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class NewTest {
    private WebDriver driver;
    private ScreenshotHelper screenshotHelper;
    private boolean takeScreenShot = true;
    private WebDriverWait wait;
    private String memiURL= "https://www.ebi.ac.uk/metagenomics/";

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
        driver = new ChromeDriver();
        driver.get(memiURL);

        screenshotHelper = new ScreenshotHelper();
        takeScreenShot = true;
        wait = new WebDriverWait(driver, 3);
    }
    @After
    public void afterTest() throws IOException {
        if (takeScreenShot)
            screenshotHelper.saveScreenshot("screenshot.png");
        driver.quit();
    }
    private class ScreenshotHelper {

        public void saveScreenshot(String screenshotFileName) throws IOException {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(screenshotFileName));
        }
    }
}