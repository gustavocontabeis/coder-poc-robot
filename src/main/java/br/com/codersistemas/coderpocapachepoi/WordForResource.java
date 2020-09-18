package br.com.codersistemas.coderpocapachepoi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class WordForResource {

	private Map<String, String> map;
	private XWPFDocument doc;

	public WordForResource(String resourceName, Map<String, String>map) throws IOException {
		this.map = map;
		
		ClassLoader classLoader = getClass().getClassLoader();
		 
	    File file = new File(classLoader.getResource(resourceName).getFile());
	    
		byte[] bytes = Files.readAllBytes(file.toPath());
		
		doc = new XWPFDocument(new ByteArrayInputStream(bytes));
		
		substituirValoresDoaParametros(doc.getBodyElements());

	}
	private void substituirValoresDoaParametros(List<IBodyElement> bodyElements) {
		bodyElements.forEach(i->processBodyElement(i));
	}

	private void processar(XWPFTable i) {
		i.getRows().forEach(r->processar(r));
	}

	private void processar(XWPFTableRow i) {
		i.getTableCells().forEach(c->processar(c));
	}

	private void processar(XWPFTableCell i) {
		i.getParagraphs().forEach(p->processar(p));
	}

	private void processar(XWPFParagraph i) {
		i.getRuns().forEach(r->substituirValores(r));
	}

	private void substituirValores(XWPFRun r) {
		String text = r.getText(0);
		if(text != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				if(text.contains(entry.getKey())) {
					text = text.replace(entry.getKey(), entry.getValue());
				}
			}
			r.setText(text, 0);
		}
	}

	private void processBodyElement(IBodyElement i) {
		Class classe = i.getClass();
		switch (classe.getSimpleName()) {
		case "XWPFTable":
			XWPFTable obj = (XWPFTable) i;
			processar(obj);
			break;
		case "XWPFTableRow":
			XWPFTableRow xWPFTableRow = (XWPFTableRow) i;
			processar(xWPFTableRow);
			break;
		case "XWPFTableCell":
			XWPFTableCell xWPFTableCell = (XWPFTableCell) i;
			processar(xWPFTableCell);
			break;
		case "XWPFParagraph":
			XWPFParagraph xWPFParagraph = (XWPFParagraph) i;
			processar(xWPFParagraph);
			break;
		case "XWPFRun":
			XWPFRun xWPFRun = (XWPFRun) i;
			substituirValores(xWPFRun);
			break;
		}
	}
	
	public ByteArrayInputStream getByteArrayInputStream() throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		doc.write(byteArrayOutputStream);
		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}
	
	public XWPFDocument getDoc() {
		return doc;
	}
}
