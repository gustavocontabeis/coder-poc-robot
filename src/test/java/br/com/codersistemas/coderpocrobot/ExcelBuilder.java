package br.com.codersistemas.coderpocapachepoi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelBuilder {
	
	XSSFWorkbook wb;
	private XSSFSheet sheet;
	private int rownum;
	private XSSFRow row;
	private int columnIndex;
	
	public ExcelBuilder(String string) {
		wb = new XSSFWorkbook();
		sheet = wb.createSheet(string);
	}

	public ExcelBuilder createRow() {
		row = sheet.createRow(rownum++);
		columnIndex = 0;
		return this;
	}

	public ExcelBuilder createCell(double value) {
		XSSFCell cell = row.createCell(columnIndex++);
		cell.setCellValue(value);
		return this;
	}

	public ExcelBuilder createCell(String value) {
		XSSFCell cell = row.createCell(columnIndex++);
		cell.setCellValue(value);
		return this;
	}

	public ExcelBuilder createCell(Date value) {
		XSSFCell cell = row.createCell(columnIndex++);
		cell.setCellValue(value);
		return this;
	}

	public void write(String file) {
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			wb.write(outputStream);
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
