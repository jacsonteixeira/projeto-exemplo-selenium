package com.jteixeira.selenium.paginas;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.jteixeira.selenium.webdriver.WebDriverUtils;

public class BuscaGoogle {

	private static final String LINK_BUSCA_GOOGLE = "http://www.google.com";

	public static Map<String, String> verificarPrimeiraPagina(String palavraChave, WebDriver driver)
			throws IOException {
		driver.get(LINK_BUSCA_GOOGLE);
		WebElement filtroBusca = driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div/div[1]/div/div[1]/input"));
		filtroBusca.sendKeys(palavraChave);
		WebDriverUtils.esperarCarregarPagina(driver);
		WebElement clicarFora = driver.findElement(By.xpath("//*[@id=\"lga\"]"));
		clicarFora.click();
		WebElement btnPesquisar = driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div/div[3]/center/input[1]"));
		btnPesquisar.click();
		WebDriverUtils.esperarCarregarPagina(driver);
		try {
			WebElement primeiroLink = driver
					.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/div[1]/div/div/div[1]/a/h3"));
			primeiroLink.click();
		} catch (Exception e) {
			System.err.println("Falha ao buscar palavra: " + palavraChave);
			driver.close();
			return null;
		}
		WebDriverUtils.esperarCarregarPagina(driver);
		String fileDir = WebDriverUtils.tirarPrintESalvar(palavraChave, driver);
		driver.close();
		Map<String, String> map = new HashMap<>();
		map.put(palavraChave, fileDir);
		return map;
	}
}
