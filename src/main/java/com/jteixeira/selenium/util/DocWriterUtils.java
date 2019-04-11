package com.jteixeira.selenium.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.jteixeira.selenium.infra.LogSelenium;

public class DocWriterUtils {

	private DocWriterUtils() {
	}

	private static LogSelenium logger = new LogSelenium(DocWriterUtils.class.getSimpleName());

	static final String DOCS_DIR = "/src/main/resources/documents/";
	static final String FILE_DOC_DIR = System.getProperty("user.dir") + DOCS_DIR + "selenium-document.docx";
	static final String FILE_PDF_DIR = System.getProperty("user.dir") + DOCS_DIR + "selenium-document.pdf";

	public static void writePictureIntoDocx(Map<String, String> screenshots)
			throws IOException, InvalidFormatException {
		createDocument();
		try (CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream(new File(FILE_DOC_DIR)));
				FileOutputStream fos = new FileOutputStream(new File(FILE_DOC_DIR))) {
			for (Map.Entry<String, String> entry : screenshots.entrySet()) {
				String blipId = document.addPictureData(new FileInputStream(new File(entry.getValue())),
						Document.PICTURE_TYPE_PNG);
				logger.info("writePictureIntoDocx",
						"Número de documento: " + document.getNextPicNameNumber(Document.PICTURE_TYPE_PNG));
				document.createPicture(blipId, document.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 570, 255);
			}
			document.write(fos);
			fos.flush();
		} catch (Exception e) {
			logger.info("writePictureIntoDocx", "Falha ao realizar parse do xml: " + e.getMessage());
		}
	}

	public static void writePictureIntoPDF(Map<String, String> screenshots) throws IOException {
		try (PDDocument doc = new PDDocument()) {
			int i = 0;
			for (Map.Entry<String, String> entry : screenshots.entrySet()) {
				PDPage myPage = new PDPage();
				doc.addPage(myPage);
				PDPage page = doc.getPage(i);
				PDImageXObject pdImage = PDImageXObject.createFromFile(entry.getValue(), doc);
				PDPageContentStream contentStream = new PDPageContentStream(doc, page);
				contentStream.drawImage(pdImage, 20, 520, 570, 255);
				contentStream.close();
			}
			doc.save(FILE_PDF_DIR);

		} catch (Exception e) {
			logger.info("writePictureIntoPDF", "Falha ao escrever no pdf: " + e.getMessage());
		}

	}

	public static XWPFDocument createDocument() throws IOException {
		try (XWPFDocument document = new XWPFDocument()) {
			FileOutputStream out = new FileOutputStream(new File(FILE_DOC_DIR));
			document.write(out);
			out.close();
			return document;
		} catch (Exception e) {
			logger.info("writePictureIntoPDF", "Falha ao escrever no pdf: " + e.getMessage());
		}
		return null;
	}

}
