package com.jteixeira.selenium;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jteixeira.selenium.paginas.BuscaGoogle;
import com.jteixeira.selenium.webdriver.WebDriverUtils;
import com.jteixeira.util.DocWriterUtils;

@SpringBootApplication
public class ProjetoExemploSeleniumApplication {

	public static void main(String[] args) throws InvalidFormatException, IOException {
		if (args.length == 0) {
			args = new String[5];
			args[0] = "Springboot";
			args[1] = "Gradle";
			args[2] = "Junit";
			args[3] = "Selenium";
			args[4] = "JPA";
		}
		fecharConexoesWebDriver();
		apagarConteudoDiretorios();
		SpringApplication.run(ProjetoExemploSeleniumApplication.class, args);
		Map<String, String> screenshotsDir = new HashMap<>();
		for (String string : args) {
			WebDriver driver = WebDriverUtils.criaConexaoWebDriverChrome(true);
			try {
//				screenshotsDir = BuscaGoogle.verificarPrimeiraPagina(string, driver);

				Map<String, String> retornoBusca = BuscaGoogle.verificarPrimeiraPagina(string, driver);
				if (retornoBusca != null)
					retornoBusca.forEach((screenshotsDir)::putIfAbsent);
			} catch (IOException e) {
				System.err.println("Falha ao verificar primeira página");
			}
		}
		DocWriterUtils.WritePictureIntoDocx(screenshotsDir);
		DocWriterUtils.WritePictureIntoPDF(screenshotsDir);

	}

	public static void fecharConexoesWebDriver() {
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec("tskill chromedriver");
		} catch (Exception e) {
			System.err.println("Falha ao encerrar webdriver");
		}
	}

	public static void apagarConteudoDiretorios() {
		try {
			apagaDiretorio(new File(System.getProperty("user.dir") + "/src/main/resources/screenshots/"));
			apagaDiretorio(
					new File(System.getProperty("user.dir") + "/src/main/resources/documents/selenium-document.docx"));
			apagaDiretorio(
					new File(System.getProperty("user.dir") + "/src/main/resources/documents/selenium-document.pdf"));

		} catch (Exception e) {
			System.err.println("Falha ao apagar diretórios");
		}
	}

	static boolean apagaDiretorio(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null)
			for (File file : allContents)
				apagaDiretorio(file);
		return directoryToBeDeleted.delete();
	}

}
