package com.jteixeira.selenium.runner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

import com.jteixeira.selenium.infra.LogSelenium;
import com.jteixeira.selenium.paginas.BuscaGoogle;
import com.jteixeira.selenium.util.DocWriterUtils;
import com.jteixeira.selenium.webdriver.WebDriverUtils;

public class SeleniumRunner implements CommandLineRunner {

	private static final String USER_DIR = "user.dir";
	
	private static LogSelenium logger = new LogSelenium(SeleniumRunner.class.getSimpleName());

	@Autowired
	private ConfigurableApplicationContext ctx;

	@Override
	public void run(String... args) throws Exception {
		args = castArgs(args);
		closeWebDriverConnection();
		deleteContentDirecotry();
		Map<String, String> screenshots = takeScreenshots(args);
		DocWriterUtils.writePictureIntoDocx(screenshots);
		DocWriterUtils.writePictureIntoPDF(screenshots);
		ctx.close();
	}

	private String[] castArgs(String... args) {
		if (args.length == 0) {
			args = new String[5];
			args[0] = "Springboot";
			args[1] = "Gradle";
			args[2] = "Junit";
			args[3] = "Selenium";
			args[4] = "JPA";
		}
		return args;
	}
	
	private Map<String, String> takeScreenshots(String[] args) {
		Map<String, String> screenshots = new HashMap<>();
		for (String string : args) {
			WebDriver driver = WebDriverUtils.createConnectionWebDriverChrome(true);
			try {
				Map<String, String> retornoBusca = BuscaGoogle.verifyFirstPage(string, driver);
				if (retornoBusca != null)
					retornoBusca.forEach((screenshots)::putIfAbsent);
			} catch (IOException e) {
				logger.info("takeScreenshots", "Falha ao verificar primeira página");
			}
		}
		return screenshots;
	}

	public static void closeWebDriverConnection() {
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec("tskill chromedriver");

		} catch (Exception e) {
			logger.info("closeWebDriverConnection", "Falha ao encerrar webdriver");
		}
	}

	public static void deleteContentDirecotry() {
		try {
			deleteDirectory(new File(System.getProperty(USER_DIR) + "/src/main/resources/screenshots/"));
			deleteDirectory(
					new File(System.getProperty(USER_DIR) + "/src/main/resources/documents/selenium-document.docx"));
			deleteDirectory(
					new File(System.getProperty(USER_DIR) + "/src/main/resources/documents/selenium-document.pdf"));

		} catch (Exception e) {
			logger.info("deleteContentDirecotry", "Falha ao apagar diretórios");
		}
	}

	static boolean deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null)
			for (File file : allContents)
				deleteDirectory(file);
		boolean returned = false;
		try {
			Files.delete(directoryToBeDeleted.toPath());
			returned = true;
		} catch (Exception e) {
			logger.info("deleteDirectory", "Falha ao apagar diretórios");
		}
		return returned;
	}
}
