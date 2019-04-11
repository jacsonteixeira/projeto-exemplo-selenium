package com.jteixeira.selenium.webdriver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtils {

	private WebDriverUtils() {
	}

	static final String DRIVERS_DIR = "/src/main/resources/drivers/";

	static final String CHROME_DRIVER_EXE = "chromedriver.exe";

	static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";

	static final String SCREENSHOT_DIR = "/src/main/resources/screenshots/";

	static final String EXTENSION = ".png";

	public static WebDriver createConnectionWebDriverChrome(Boolean fullScreen) {

		System.setProperty(CHROME_DRIVER_PROPERTY, System.getProperty("user.dir") + DRIVERS_DIR + CHROME_DRIVER_EXE);
		WebDriver driver = new ChromeDriver();
		if (fullScreen)
			driver.manage().window().maximize();
		return driver;
	}

	public static void waitForPageLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = n -> ((JavascriptExecutor) driver)
				.executeScript("return document.readyState").equals("complete");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	public static String takePrintAndSave(String keyWord, WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileDir = System.getProperty("user.dir") + SCREENSHOT_DIR + substruingSpecialCaracteres(keyWord)
				+ EXTENSION;
		FileUtils.copyFile(scrFile, new File(fileDir));
		return fileDir;
	}

	public static String substruingSpecialCaracteres(String stringToReplace) {
		return stringToReplace.replaceAll("[^a-zA-Z0-9]", "_");
	}

}
