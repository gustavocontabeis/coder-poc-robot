package br.com.codersistemas.coderpocapachepoi;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WordTests {
	
	private Map<String, String>map;
	
	@Before
	public void antes() {
		map = new HashMap<String, String>();
		map.put("#GILIEDOUSUARIO", "GITE/PO");
		map.put("#NUMEROATESTE", "45654565");
		map.put("#GILOG", "7876");
		map.put("#NOME-FORNECEDOR", "FULANO CORPORATION");
		map.put("#CNPJ-FORNECEDOR", "443534534543543");
		map.put("#VLRPAGAMENTO", "123,56");
		map.put("#COMPETENCIA", "01/2020");
		map.put("#NUMEROCOMPROMISSO", "98989");
		map.put("#CNPJ-FORNECEDOR", "1313131312");
		map.put("#TEXTOSISFIN", "SDADADAS");
		map.put("#MULTA", "");
		map.put("#1", "X");
		map.put("#2", "");
		map.put("#3", "");
		map.put("#4", "");
		map.put("#CIDADE", "PORTO ALEGRE");
		map.put("#DD", "26");
		map.put("#MES", "AGOSTO");
		map.put("#ANO", "2020");
	}

	@Test
	void gerarDocumentoWord() throws IOException, InvalidFormatException {

		XWPFDocument document = new XWPFDocument();
		
		XWPFParagraph pLogoCaixa = document.createParagraph();
		pLogoCaixa.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun runLogoCaixa = pLogoCaixa.createRun();
		String fileLogoCaixa = "/home/gustavo/Pictures/logo-caixa.jpg";
	    FileInputStream isLogoCaixa = new FileInputStream(fileLogoCaixa);
	    runLogoCaixa.addBreak();
	    runLogoCaixa.addPicture(isLogoCaixa, XWPFDocument.PICTURE_TYPE_JPEG, fileLogoCaixa, Units.toEMU(100), Units.toEMU(50));
	    isLogoCaixa.close();
		
		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run2 = paragraph2.createRun();
		run2.setText("Ateste para Pagamento a Fornecedor");
		
		XWPFTable table = document.createTable();
		
		XWPFTableRow tableRow1 = table.getRow(0);
		tableRow1.getCell(0).setText("");
		tableRow1.addNewTableCell().setText("");
		tableRow1.addNewTableCell().setText("Grau de sigilo");
		
		XWPFTableRow tableRow2 = table.createRow();
		tableRow2.getCell(0).setText("");
		tableRow2.getCell(1).setText("");
		tableRow2.getCell(2).setText("#PÚBLICO");
		
		XWPFTable table2 = document.createTable();
		
		XWPFTableRow tableRow21 = table2.getRow(0);
		tableRow21.getCell(0).setText("");
		tableRow21.addNewTableCell();
		
		tableRow21.getTable().getRow(0).getCell(0).addParagraph().createRun().setText("De <gilie do usuario> Ateste nº <>.");

		
		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File("/home/gustavo/teste/apache-poi-blank.docx"));
		document.write(out);
		out.close();
		System.out.println("------------------------- ok -------------------------");
	}

	@org.junit.Test
	public void read() throws IOException {
		String file = "/home/gustavo/teste/modelo.docx";
		Path path = Paths.get(file);
		byte[] byteData = Files.readAllBytes(path);

		// read as XWPFDocument from byte[]
		XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(byteData));
		
		xxx0(doc.getBodyElements());
		
		// save it
		FileOutputStream fos = new FileOutputStream(new File("/home/gustavo/teste/modelo-ok.docx"));
		doc.write(fos);
	}

	private void xxx0(List<IBodyElement> bodyElements) {
		bodyElements.forEach(i->processBodyElement(i));
	}

	private Object processBodyElement(XWPFTable i) {
		System.out.println("XWPFTable: " + i.getClass());
		i.getRows().forEach(r->processBodyElement(r));
		return null;
	}

	private Object processBodyElement(XWPFTableRow i) {
		System.out.println("XWPFTableRow: "+i.getClass());
		i.getTableCells().forEach(c->processBodyElement(c));
		return null;
	}

	private Object processBodyElement(XWPFTableCell i) {
		System.out.println("XWPFTableCell: "+i.getClass());
		i.getParagraphs().forEach(p->processBodyElement(p));
		return null;
	}

	private Object processBodyElement(XWPFParagraph i) {
		System.out.println("XWPFParagraph: "+i.getClass());
		i.getRuns().forEach(r->processBodyElement(r));
		return null;
	}

	private Object processBodyElement(XWPFRun r) {
		System.out.println("XWPFRun: "+r.getClass());
		String text = r.getText(0);
		System.out.println(text);
		if(text != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				if(text.contains(entry.getKey())) {
					text = text.replace(entry.getKey(), entry.getValue());
				}
			}
			r.setText(text, 0);
		}
		return null;
	}

	private IBodyElement processBodyElement(IBodyElement i) {
		Class class1 = i.getClass();
		System.out.println("IBodyElement: " + class1);
		switch (class1.getSimpleName()) {
		case "XWPFTable":
			XWPFTable obj = (XWPFTable) i;
			processBodyElement(obj);
			break;
		case "XWPFTableRow":
			XWPFTableRow xWPFTableRow = (XWPFTableRow) i;
			processBodyElement(xWPFTableRow);
			break;
		case "XWPFTableCell":
			XWPFTableCell xWPFTableCell = (XWPFTableCell) i;
			processBodyElement(xWPFTableCell);
			break;
		case "XWPFParagraph":
			XWPFParagraph xWPFParagraph = (XWPFParagraph) i;
			processBodyElement(xWPFParagraph);
			break;
		case "XWPFRun":
			XWPFRun xWPFRun = (XWPFRun) i;
			processBodyElement(xWPFRun);
			break;
		default:
			break;
		}
		return i;
	}

	@Test
	public void robot() throws IOException, AWTException, InterruptedException {
		Robot r = new Robot();
		do {
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int x = (int) b.getX();
			int y = (int) b.getY();
			r.mouseMove(x+=1,y);
			Thread.sleep(1000*1);
		} while (1==1);
	}

}
