package com.vn.generatedata.service;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import com.vn.generatedata.model.GenerateData;
import com.vn.generatedata.model.PosgresSqlTable;

public interface IPosgresSQLService {
	List<String> getListOfAllTables(DatabaseMetaData dmd) throws SQLException;
	
	List<PosgresSqlTable> searchForColumnNameInTables(DatabaseMetaData dmd) throws SQLException;
	
	PosgresSqlTable oneTable(String tableName, DatabaseMetaData dmd) throws SQLException;
	
	String genData(DatabaseMetaData dmd, GenerateData generateData) throws IOException, SQLException;
	
	List<List<String[]>> getAllDataTable (DatabaseMetaData dmd, GenerateData generateData) throws SQLException;
	
}
