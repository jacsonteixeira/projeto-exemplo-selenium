package com.jteixeira.selenium;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jteixeira.selenium.paginas.BuscaGoogle;
import com.jteixeira.selenium.webdriver.WebDriverUtils;

@SpringBootApplication
public class ProjetoExemploSeleniumApplication {

	public static void main(String[] args) throws InterruptedException, IOException {
		fecharConexoesWebDriver();
		apagarConteudoDiretorioImagens();
		SpringApplication.run(ProjetoExemploSeleniumApplication.class, args);
		for (String string : args) {
			WebDriver driver = WebDriverUtils.criaConexaoWebDriverChrome(true);
			driver = BuscaGoogle.verificarPrimeirPagina(string, driver);
		}
	}

	public static void fecharConexoesWebDriver() throws IOException {
		Runtime rt = Runtime.getRuntime();
		rt.exec("tskill chromedriver");
	}

	public static void apagarConteudoDiretorioImagens() throws IOException {
		apagaDiretorio(new File(System.getProperty("user.dir") + "/src/main/resources/screenshots/"));
	}

	static boolean apagaDiretorio(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null)
			for (File file : allContents)
				apagaDiretorio(file);
		return directoryToBeDeleted.delete();
	}

}
