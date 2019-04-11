package com.jteixeira.selenium.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

import com.jteixeira.selenium.infra.LogSelenium;

public class CustomXWPFDocument extends XWPFDocument {
	
	private static LogSelenium logger = new LogSelenium(CustomXWPFDocument.class.getSimpleName());

	public CustomXWPFDocument(InputStream in) throws IOException {
		super(in);
	}

	public void createPicture(String blipId, int id, int width, int height) {
		final int EMU = 9525;
		width *= EMU;
		height *= EMU;

		CTInline inline = createParagraph().createRun().getCTR().addNewDrawing().addNewInline();

		StringBuilder sbPicXml = new StringBuilder();
		sbPicXml.append("<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">");
		sbPicXml.append("   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">");
		sbPicXml.append("      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">");
		sbPicXml.append("         <pic:nvPicPr>");
		sbPicXml.append("            <pic:cNvPr id=\"").append(id).append("\" name=\"Generated\"/>");
		sbPicXml.append("            <pic:cNvPicPr/>");
		sbPicXml.append("         </pic:nvPicPr>");
		sbPicXml.append("         <pic:blipFill>");
		sbPicXml.append("            <a:blip r:embed=\"").append(blipId)
				.append("\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>");
		sbPicXml.append("            <a:stretch>");
		sbPicXml.append("               <a:fillRect/>");
		sbPicXml.append("            </a:stretch>");
		sbPicXml.append("         </pic:blipFill>");
		sbPicXml.append("         <pic:spPr>");
		sbPicXml.append("            <a:xfrm>");
		sbPicXml.append("               <a:off x=\"0\" y=\"0\"/>");
		sbPicXml.append("               <a:ext cx=\"").append(width).append("\" cy=\"").append(height).append("\"/>");
		sbPicXml.append("            </a:xfrm>");
		sbPicXml.append("            <a:prstGeom prst=\"rect\">");
		sbPicXml.append("               <a:avLst/>");
		sbPicXml.append("            </a:prstGeom>");
		sbPicXml.append("         </pic:spPr>");
		sbPicXml.append("      </pic:pic>");
		sbPicXml.append("   </a:graphicData>");
		sbPicXml.append("</a:graphic>");

		XmlToken xmlToken = null;
		try {
			xmlToken = XmlToken.Factory.parse(sbPicXml.toString());
		} catch (XmlException xe) {
			logger.info("createPicture", "Falha ao realizar parse do xml: " + xe.getMessage());
		}
		inline.set(xmlToken);

		inline.setDistT(0);
		inline.setDistB(0);
		inline.setDistL(0);
		inline.setDistR(0);

		CTPositiveSize2D extent = inline.addNewExtent();
		extent.setCx(width);
		extent.setCy(height);

		CTNonVisualDrawingProps docPr = inline.addNewDocPr();
		docPr.setId(id);
		docPr.setName("Picture " + id);
		docPr.setDescr("Generated");
	}
}