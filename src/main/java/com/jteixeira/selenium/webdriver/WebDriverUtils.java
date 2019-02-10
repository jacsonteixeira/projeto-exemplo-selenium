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

	final static String DRIVERS_DIR = "/src/main/resources/drivers/";

	final static String CHROME_DRIVER_EXE = "chromedriver.exe";

	final static String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";

	final static String SCREENSHOT_DIR = "/src/main/resources/screenshots/";

	final static String EXTENSION = ".png";

	public static WebDriver criaConexaoWebDriverChrome(Boolean telaMaximizada) {

		System.setProperty(CHROME_DRIVER_PROPERTY, System.getProperty("user.dir") + DRIVERS_DIR + CHROME_DRIVER_EXE);
		WebDriver driver = new ChromeDriver();
		if (telaMaximizada)
			driver.manage().window().maximize();
		return driver;
	}

	public static void esperarCarregarPagina(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	public static void tirarPrintESalvar(String palavraChave, WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + SCREENSHOT_DIR
				+ substituirCaracteresEspeciais(palavraChave) + EXTENSION));
	}

	public static String substituirCaracteresEspeciais(String stringToReplace) {
		return stringToReplace.replaceAll("[^a-zA-Z0-9]", "_");
	}

}
