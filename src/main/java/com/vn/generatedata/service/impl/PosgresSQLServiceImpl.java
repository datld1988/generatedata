package com.vn.generatedata.service.impl;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vn.generatedata.model.GenerateData;
import com.vn.generatedata.model.PosgresSqlColumn;
import com.vn.generatedata.model.PosgresSqlTable;
import com.vn.generatedata.service.ICommon;
import com.vn.generatedata.service.IExcelWork;
import com.vn.generatedata.service.IPosgresSQLService;

@Service
public class PosgresSQLServiceImpl implements IPosgresSQLService {

	@Autowired
	private ICommon common;

	@Autowired
	private IExcelWork excelWork;

	List<List<String[]>> listTableData = new ArrayList<List<String[]>>();

	List<PosgresSqlTable> listTable = new ArrayList<PosgresSqlTable>();

	@Override
	public String genData(DatabaseMetaData dmd, GenerateData generateData) throws IOException, SQLException {

		// see if you can find the column name in any tables
		listTable = searchForColumnNameInTables(dmd);

		// List table selected
		List<PosgresSqlTable> listTableSelected = new ArrayList<PosgresSqlTable>();

		listTableData.clear();

		for (PosgresSqlTable posgresSqlTable : listTable) {

			for (String table : generateData.getListTable()) {

				if (posgresSqlTable.getTableName().equals(table)) {
					List<String[]> initDataOneTable = common.initDataOneTable(posgresSqlTable.getListColumn(), generateData);

					listTableSelected.add(posgresSqlTable);
					listTableData.add(initDataOneTable);
				}
			}
		}
		return excelWork.writeToExcelFile(listTableSelected, listTableData);
	}

	@Override
	public List<List<String[]>> getAllDataTable(DatabaseMetaData dmd, GenerateData generateData) throws SQLException {
		// see if you can find the column name in any tables
		List<PosgresSqlTable> listTable = searchForColumnNameInTables(dmd);

		listTableData.clear();

		for (PosgresSqlTable posgresSqlTable : listTable) {

			for (String table : generateData.getListTable()) {

				if (posgresSqlTable.getTableName().equals(table)) {
					List<String[]> initDataOneTable = common.initDataOneTable(posgresSqlTable.getListColumn(),generateData);

					listTableData.add(initDataOneTable);
				}
			}
		}
		return listTableData;
	}

	@Override
	public List<String> getListOfAllTables(DatabaseMetaData dmd) throws SQLException {
		String[] tableTypes = { "TABLE" };

		List<String> allTable = new ArrayList<String>();

		ResultSet rs = dmd.getTables(null, null, "%", tableTypes);

		while (rs.next()) {
			String tableName = rs.getString(3);
			allTable.add(tableName);
		}
		rs.close();
		return allTable;
	}

	@Override
	public List<PosgresSqlTable> searchForColumnNameInTables(DatabaseMetaData dmd) throws SQLException {
		List<PosgresSqlTable> listTable = new ArrayList<PosgresSqlTable>();
		List<PosgresSqlColumn> listColumn = null;

		Iterator iter = getListOfAllTables(dmd).iterator();

		while (iter.hasNext()) {
			String tableName = (String) iter.next();

			if (listTable.stream().filter(a -> a.getTableName().equals(tableName)).collect(Collectors.toList())
					.size() > 0) {
				break;
			}

			java.sql.ResultSet rs = dmd.getColumns(null, null, tableName, "%");
			listColumn = new ArrayList<PosgresSqlColumn>();

			while (rs.next()) {
				String colName = rs.getString(4);
				String colType = rs.getString(6);
				System.out.println(colType + "\t" + colName + "\t" + tableName);
				int colLength = Integer.parseInt(rs.getString(7));
				listColumn.add(new PosgresSqlColumn(tableName, colName, colType, colLength));
			}
			listTable.add(new PosgresSqlTable(tableName, listColumn));
		}

		return listTable;
	}

	@Override
	public PosgresSqlTable oneTable(String tableName, DatabaseMetaData dmd) throws SQLException {

		List<PosgresSqlTable> table = searchForColumnNameInTables(dmd);

		return table.stream().filter(tbl -> tbl.getTableName().equals(tableName)).findFirst().orElse(null);
	}

}
