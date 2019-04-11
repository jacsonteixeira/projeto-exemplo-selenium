package com.jteixeira.selenium.paginas;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.jteixeira.selenium.infra.LogSelenium;
import com.jteixeira.selenium.webdriver.WebDriverUtils;

public class BuscaGoogle {
	
	private BuscaGoogle() {
	}
	
	private static LogSelenium logger = new LogSelenium(BuscaGoogle.class.getSimpleName());

	private static final String LINK_BUSCA_GOOGLE = "http://www.google.com";
	private static final String XPATH_FILTRO_BUSCA = "//*[@id=\"tsf\"]/div[2]/div/div[1]/div/div[1]/input";
	private static final String XPATH_CLICAR_FORA = "//*[@id=\"lga\"]";
	private static final String XPATH_BTN_PESQUISAR = "//*[@id=\"tsf\"]/div[2]/div/div[3]/center/input[1]";
	private static final String XPATH_PRIMEIRO_LINK = "//*[@id=\"rso\"]/div[1]/div/div[1]/div/div/div[1]/a/h3";


	public static Map<String, String> verifyFirstPage(String palavraChave, WebDriver driver)
			throws IOException {
		driver.get(LINK_BUSCA_GOOGLE);
		WebElement filtroBusca = driver.findElement(By.xpath(XPATH_FILTRO_BUSCA));
		filtroBusca.sendKeys(palavraChave);
		WebDriverUtils.waitForPageLoad(driver);
		WebElement clicarFora = driver.findElement(By.xpath(XPATH_CLICAR_FORA));
		clicarFora.click();
		WebElement btnPesquisar = driver.findElement(By.xpath(XPATH_BTN_PESQUISAR));
		btnPesquisar.click();
		WebDriverUtils.waitForPageLoad(driver);
		try {
			WebElement primeiroLink = driver
					.findElement(By.xpath(XPATH_PRIMEIRO_LINK));
			primeiroLink.click();
		} catch (Exception e) {
			logger.info("verifyFirstPage", "Falha ao buscar palavra: " + palavraChave);
			driver.close();
			return null;
		}
		WebDriverUtils.waitForPageLoad(driver);
		String fileDir = WebDriverUtils.takePrintAndSave(palavraChave, driver);
		driver.close();
		Map<String, String> map = new HashMap<>();
		map.put(palavraChave, fileDir);
		return map;
	}
}
