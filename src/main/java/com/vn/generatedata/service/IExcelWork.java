package com.vn.generatedata.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vn.generatedata.model.PosgresSqlColumn;
import com.vn.generatedata.model.PosgresSqlTable;

public interface IExcelWork {

	void createSheet(List<PosgresSqlColumn> listColumn, List<String[]> initData, XSSFWorkbook workbook);

	String writeToExcelFile(List<PosgresSqlTable> listTable, List<List<String[]>> listTableData) throws IOException;

}