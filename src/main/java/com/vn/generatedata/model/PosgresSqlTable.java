package com.vn.generatedata.model;

import java.util.List;

public class PosgresSqlTable {

	private String tableName;

	private List<PosgresSqlColumn> listColumn;

	public List<PosgresSqlColumn> getListColumn() {
		return listColumn;
	}
	public PosgresSqlTable() {
	
	}

	public PosgresSqlTable(String tableName, List<PosgresSqlColumn> listColumn) {
		super();
		this.tableName = tableName;
		this.listColumn = listColumn;
	}

	public void setListColumn(List<PosgresSqlColumn> listColumn) {
		this.listColumn = listColumn;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
