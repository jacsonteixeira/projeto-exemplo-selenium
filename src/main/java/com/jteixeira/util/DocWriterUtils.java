package com.jteixeira.util;

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

public class DocWriterUtils {

	final static String DOCS_DIR = "/src/main/resources/documents/";

	final static String FILE_DOC_DIR = System.getProperty("user.dir") + DOCS_DIR + "selenium-document.docx";

	final static String FILE_PDF_DIR = System.getProperty("user.dir") + DOCS_DIR + "selenium-document.pdf";

	public static void WritePictureIntoDocx(Map<String, String> screenshots)
			throws IOException, InvalidFormatException {
		CreateDocument();
		CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream(new File(FILE_DOC_DIR)));
		FileOutputStream fos = new FileOutputStream(new File(FILE_DOC_DIR));
		for (Map.Entry<String, String> entry : screenshots.entrySet()) {
			String blipId = document.addPictureData(new FileInputStream(new File(entry.getValue())),
					Document.PICTURE_TYPE_PNG);
			System.out.println(document.getNextPicNameNumber(Document.PICTURE_TYPE_PNG));
			document.createPicture(blipId, document.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 570, 255);
		}
		document.write(fos);
		fos.flush();
		fos.close();
		document.close();
	}

	public static void WritePictureIntoPDF(Map<String, String> screenshots) throws IOException {
		PDDocument doc = new PDDocument();
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
		doc.close();

	}

	public static XWPFDocument CreateDocument() throws IOException {
		XWPFDocument document = new XWPFDocument();
		FileOutputStream out = new FileOutputStream(new File(FILE_DOC_DIR));
		document.write(out);
		out.close();
		return document;
	}

}
