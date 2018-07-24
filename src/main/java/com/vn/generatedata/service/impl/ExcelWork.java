package com.vn.generatedata.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vn.generatedata.model.PosgresSqlColumn;
import com.vn.generatedata.model.PosgresSqlTable;
import com.vn.generatedata.service.IExcelWork;

@Service
public class ExcelWork implements IExcelWork {

	@Autowired
	private ServletContext context;

	@Override
	public void createSheet(List<PosgresSqlColumn> listColumn, List<String[]> initData, XSSFWorkbook workbook) {
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		XSSFSheet sheet = workbook.createSheet(listColumn.get(0).getNameTable());

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		headerCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		headerCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		headerCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < listColumn.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(listColumn.get(i).getNameColumn());
			cell.setCellStyle(headerCellStyle);
		}
		

		// Create Other rows and cells with employees data
		int rowNum = 1;

		DataFormat fmt = workbook.createDataFormat();
		CellStyle cell = workbook.createCellStyle();
		cell.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cell.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cell.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cell.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		
		cell.setDataFormat(fmt.getFormat("@"));

		for (int i = 0; i < initData.size(); i++) {
			XSSFRow  row = sheet.createRow(rowNum++);

			String[] dataRow = initData.get(i);

			for (int j = 0; j < dataRow.length; j++) {
				XSSFCell cellJ = row.createCell(j);
				cellJ.setCellStyle(cell);
				cellJ.setCellValue(dataRow[j]);
			}
		}

		// Resize all columns to fit the content size
		for (int i = 0; i < listColumn.size(); i++) {
			sheet.autoSizeColumn(i);
		}
	}

	@Override
	public String writeToExcelFile(List<PosgresSqlTable> listTable, List<List<String[]>> listTableData) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for
		// generating `.xls` file

		for (int i = 0; i < listTable.size(); i++) {

			createSheet(listTable.get(i).getListColumn(), listTableData.get(i), workbook);

		}

		// Write the output to a file
		FileOutputStream fileOut = null;

		String pathFile = context.getRealPath("/WEB-INF/SaveFile");

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		workbook.write(bos);

		String uniqueID = UUID.randomUUID().toString();

		StringBuffer fileName = new StringBuffer(uniqueID).append(".xlsx");

		// Get the file and save it somewhere
		byte[] bytes = bos.toByteArray();
		Path path = Paths.get(pathFile + fileName.toString());
		Files.write(path, bytes);
		
		return fileName.toString();
	}
}
