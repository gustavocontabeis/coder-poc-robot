package br.com.codersistemas.coderpocapachepoi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoderPogApachePoiApplicationTests {

	@Test
	void gerarExcelCores() {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Colors");

		int rowNum = 0;
		for (IndexedColors indexedColor : IndexedColors.values()) {
			int colNum = 0;
			Row row = sheet.createRow(rowNum++);
			for (FillPatternType fillPatternType : FillPatternType.values()) {
				
				CellStyle style = workbook.createCellStyle();
				style.setFillBackgroundColor(indexedColor.getIndex());
				//style.setFillPattern(FillPatternType.BIG_SPOTS);
				style.setFillPattern(fillPatternType);
				
				Cell cell = row.createCell(colNum++);
				cell.setCellStyle(style);
				cell.setCellValue(indexedColor.name()+" / "+fillPatternType.name());
			}

		}

		try {
			FileOutputStream outputStream = new FileOutputStream("/home/gustavo/excelColors.xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

	@Test
	void gerarExcelLagura() {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Larguras");

		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		Cell cell1 = row.createCell(0);
		cell1.setCellValue("OK");
		sheet.setColumnWidth(0, 5000);
		try {
			FileOutputStream outputStream = new FileOutputStream("/home/gustavo/excelLargura.xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

	@Test
	void gerarExcelMerceCells() {
		
		XSSFWorkbook wb = new XSSFWorkbook();
		
		XSSFCellStyle cellStyle0 = wb.createCellStyle();
		setCellAligment(cellStyle0);
		setCellBorders(cellStyle0);
		cellStyle0.setFillBackgroundColor(IndexedColors.WHITE1.getIndex());
		cellStyle0.setFillPattern(FillPatternType.FINE_DOTS);
		
		XSSFCellStyle cellStyleCefus = wb.createCellStyle();
		setCellAligment(cellStyleCefus);
		setCellBorders(cellStyleCefus);
		cellStyleCefus.setFillBackgroundColor(IndexedColors.DARK_BLUE.getIndex());
		cellStyleCefus.setFillPattern(FillPatternType.SPARSE_DOTS);
		
		XSSFCellStyle cellStyle1 = wb.createCellStyle();
		setCellAligment(cellStyle1);
		setCellBorders(cellStyle1);
		cellStyle1.setFillBackgroundColor(IndexedColors.PALE_BLUE.getIndex());
		cellStyle1.setFillPattern(FillPatternType.LEAST_DOTS);
		
		
		XSSFSheet sheet = wb.createSheet("new sheet");
		String[] cols0 = {"Uso da Unidade Demandante", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "Uso da CEFUS", "", "", ""};
		String[] cols1 = {"Sequencial", "Empreendimento", "", "Imovel", "", "Contrato", "", "", "", "", "Despesa", "", "", "", "", "", "", "", "", "Análise Judicial", "Análise de Liberação", "Liberação", "Assistente"};
		String[] cols2 = {"", "Codigo", "Nome", "Numero", "Situação", "Identificacao", "", "Reintegracao de posse", "", "Cobranca", "Tipo", "Mês de Competencia ou de referencia", "Parcela", "Data de pagamento", "Valor Principal", "Encargos", "Valor Total", "Valor SISFIN", "Observacoes"};
		String[] cols3 = {"A", "B", "C", "D", "E", "numero", "Situacao especial", "numero do processo de reintegracao de posse", "data de ajuizamento do processo de reintegracao de posse", "numero do processo", "", "", "", "", "", "", "", ""};
		
		Row row0 = sheet.createRow(0);
		for (int i = 0; i < cols0.length; i++) {
			Cell cell = row0.createCell(i);
			cell.setCellValue(cols0[i]);
			cell.setCellStyle(cellStyle0);
		}
		Row row1 = sheet.createRow(1);
		for (int i = 0; i < cols1.length; i++) {
			Cell cell = row1.createCell(i);
			cell.setCellValue(cols1[i]);
			cell.setCellStyle(cellStyle1);
		}
		Row row2 = sheet.createRow(2);
		for (int i = 0; i < cols2.length; i++) {
			Cell cell = row2.createCell(i);
			cell.setCellValue(cols2[i]);
			cell.setCellStyle(cellStyle1);
		}
		Row row3 = sheet.createRow(3);
		for (int i = 0; i < cols3.length; i++) {
			Cell cell = row3.createCell(i);
			cell.setCellValue(cols3[i]);
			cell.setCellStyle(cellStyle1);
		}
		
		DataFormat format = wb.createDataFormat();
		XSSFCellStyle numberStyle = wb.createCellStyle();
		numberStyle.setDataFormat(format.getFormat("#,##0.00"));
		
		Row row4 = sheet.createRow(4);
		Cell cell1 = row4.createCell(0);
		cell1.setCellStyle(numberStyle);
		cell1.setCellValue(12.345);
		Cell cell2 = row4.createCell(1);
		cell2.setCellStyle(numberStyle);
		cell2.setCellValue(1);
		
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,18));//Uso da Unidade Demandante
		sheet.addMergedRegion(new CellRangeAddress(0,0,19,22));//Uso da CEFUS
		sheet.addMergedRegion(new CellRangeAddress(1,3,0,0));//Sequencial
		sheet.addMergedRegion(new CellRangeAddress(1,1,1,2));//Empreendimento
		sheet.addMergedRegion(new CellRangeAddress(2,3,1,1));//Empreendimento > Codigo
		sheet.addMergedRegion(new CellRangeAddress(2,3,2,2));//Empreendimento > Nome
		sheet.addMergedRegion(new CellRangeAddress(1,1,3,4));//Imovel
		sheet.addMergedRegion(new CellRangeAddress(2,3,3,3));//Imovel > numero
		sheet.addMergedRegion(new CellRangeAddress(2,3,4,4));//Imovel > situacao
		sheet.addMergedRegion(new CellRangeAddress(1,1,5,9));//Contrato
		sheet.addMergedRegion(new CellRangeAddress(2,2,5,6));//Contrato > Identificacao
		sheet.addMergedRegion(new CellRangeAddress(2,2,7,8));//Contrato > Reintegracao de posse
		sheet.addMergedRegion(new CellRangeAddress(1,1,10,18));//Despesa
		sheet.addMergedRegion(new CellRangeAddress(2,3,10,10));//Despesa > Tipo
		sheet.addMergedRegion(new CellRangeAddress(2,3,11,11));//Despesa > Tipo
		sheet.addMergedRegion(new CellRangeAddress(2,3,12,12));//Despesa > Tipo
		sheet.addMergedRegion(new CellRangeAddress(2,3,13,13));//Despesa > Tipo
		sheet.addMergedRegion(new CellRangeAddress(2,3,14,14));//Despesa > Tipo
		sheet.addMergedRegion(new CellRangeAddress(2,3,15,15));//Despesa > Tipo
		sheet.addMergedRegion(new CellRangeAddress(2,3,16,16));//Despesa > Tipo
		sheet.addMergedRegion(new CellRangeAddress(2,3,17,17));//Despesa > Tipo
		sheet.addMergedRegion(new CellRangeAddress(2,3,18,18));//Despesa > Tipo
		sheet.addMergedRegion(new CellRangeAddress(1,3,19,19));//Uso da CEFUS
		sheet.addMergedRegion(new CellRangeAddress(1,3,20,20));//Uso da CEFUS
		sheet.addMergedRegion(new CellRangeAddress(1,3,21,21));//Uso da CEFUS
		sheet.addMergedRegion(new CellRangeAddress(1,3,22,22));//Uso da CEFUS
		
		sheet.getRow(0).getCell(19).setCellStyle(cellStyleCefus);
		sheet.getRow(1).getCell(19).setCellStyle(cellStyleCefus);
		sheet.getRow(1).getCell(20).setCellStyle(cellStyleCefus);
		sheet.getRow(1).getCell(21).setCellStyle(cellStyleCefus);
		sheet.getRow(1).getCell(22).setCellStyle(cellStyleCefus);
		
		sheet.getRow(3).setHeight((short) 15);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(11);
		
		try {
			FileOutputStream outputStream = new FileOutputStream("/home/gustavo/excelMerge.xlsx");
			wb.write(outputStream);
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}
	
	/**
	 * 
	createCell(wb, sheet, row3, i, cols3[i]+"???", cellStyle1, "XXXX");
	 */
	private void createCell(XSSFWorkbook wb, XSSFSheet sheet, Row row, int colNumber, String value, XSSFCellStyle cellStyle1, String comentario) {
		Cell cell = row.createCell(colNumber);
		cell.setCellValue(value);
		cell.setCellStyle(cellStyle1);
		CreationHelper factory = wb.getCreationHelper();
		ClientAnchor anchor = factory.createClientAnchor();
		anchor.setCol1(cell.getColumnIndex());
		anchor.setCol2(cell.getColumnIndex()+1);
		anchor.setRow1(row.getRowNum());
		anchor.setRow2(row.getRowNum()+3);
		
		Drawing drawing = sheet.createDrawingPatriarch();
		Comment comment = drawing.createCellComment(anchor);
		RichTextString str = factory.createRichTextString(comentario);
		comment.setString(str);
		comment.setAuthor("Apache POI");
		cell.setCellComment(comment);
	}

	
	@Test
	void gerarExcelComentario() throws FileNotFoundException, IOException {
		Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
		CreationHelper factory = wb.getCreationHelper();
		Sheet sheet = wb.createSheet();
		Row row   = sheet.createRow(3);
		Cell cell = row.createCell(5);
		cell.setCellValue("F4");
		Drawing drawing = sheet.createDrawingPatriarch();
		// When the comment box is visible, have it show in a 1x3 space
		ClientAnchor anchor = factory.createClientAnchor();
		anchor.setCol1(cell.getColumnIndex());
		anchor.setCol2(cell.getColumnIndex()+1);
		anchor.setRow1(row.getRowNum());
		anchor.setRow2(row.getRowNum()+3);
		// Create the comment and set the text+author
		Comment comment = drawing.createCellComment(anchor);
		RichTextString str = factory.createRichTextString("Hello, World!");
		comment.setString(str);
		comment.setAuthor("Apache POI");
		// Assign the comment to the cell
		cell.setCellComment(comment);
		String fname = "/home/gustavo/comment-xssf.xls";
		if(wb instanceof XSSFWorkbook) fname += "x";
		try (OutputStream out = new FileOutputStream(fname)) {
		    wb.write(out);
		}
		wb.close();
		    
	}

	private void setCellAligment(XSSFCellStyle cellStyle0) {
		cellStyle0.setAlignment(HorizontalAlignment.CENTER);
		cellStyle0.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle0.setWrapText(true);
	}

	private void setCellBorders(XSSFCellStyle cellStyle1) {
		cellStyle1.setBorderTop(BorderStyle.THIN);
		cellStyle1.setTopBorderColor(IndexedColors.BLACK.getIndex());
		
		cellStyle1.setBorderLeft(BorderStyle.THIN);
		cellStyle1.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		
		cellStyle1.setBorderRight(BorderStyle.THIN);
		cellStyle1.setRightBorderColor(IndexedColors.BLACK.getIndex());
		
		cellStyle1.setBorderBottom(BorderStyle.THIN);
		cellStyle1.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	}

	@Test
	void number() {
		Locale localeBR = new Locale("pt", "BR");
		NumberFormat dinheiroReal = NumberFormat.getInstance(localeBR);
		dinheiroReal.setMinimumFractionDigits(2);
		dinheiroReal.setMaximumFractionDigits(2);
		System.out.println(dinheiroReal.format(123456789.123456));
	}
	
	@Test
	void testeExcelBuilder() throws FileNotFoundException, IOException {
		
		ExcelBuilder excel = new ExcelBuilder("plan1");
		for (int i = 0; i < 15; i++) {
			excel.createRow()
			.createCell(i)
			.createCell("> "+i)
			.createCell(new Date());
		}
		excel.write("/home/gustavo/builder.xlsx");
		
		
		
	}


}

