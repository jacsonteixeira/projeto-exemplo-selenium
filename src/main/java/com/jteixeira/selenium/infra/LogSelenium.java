package com.jteixeira.selenium.infra;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogSelenium {

	private static final String TAG_TAG = "TAG: ";
	private static final String ESPACO_TAG = " ";

	private Logger logger;
	private String classe;

	public LogSelenium(String classe) {
		super();
		this.classe = classe;
		this.logger = LogManager.getLogger(classe);
	}

	public void error(String metodo, String mensagemInfo) {
		StringBuilder msg = new StringBuilder();
		msg.append(TAG_TAG).append(classe).append(".").append(metodo);
		msg.append(ESPACO_TAG).append(mensagemInfo);
		logger.error(msg.toString());
	}

	public void info(String metodo, String mensagemInfo) {
		StringBuilder msg = new StringBuilder();
		msg.append(TAG_TAG).append(classe).append(".").append(metodo);
		msg.append(ESPACO_TAG).append(mensagemInfo);
		logger.info(msg.toString());
	}

}
