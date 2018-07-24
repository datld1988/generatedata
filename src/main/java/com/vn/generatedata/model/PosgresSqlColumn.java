package com.vn.generatedata.model;

public class PosgresSqlColumn {
	
	private String nameTable;
	private String nameColumn;
	private String type;
	private int length;
	private int rowConfig;
	
	public PosgresSqlColumn(String nameTable, String nameColumn, String type, int length) {
		super();
		this.nameTable = nameTable.toUpperCase();
		this.nameColumn = nameColumn.toUpperCase();
		this.type = type;
		this.length = length;
	}
	
	
	public PosgresSqlColumn(String nameColumn, String type, int length) {
		this.nameColumn = nameColumn.toUpperCase();
		this.type = type;
		this.length = length;
	}
	
	public PosgresSqlColumn() {
		
	}
	
	public String getNameTable() {
		return nameTable;
	}
	public void setNameTable(String nameTable) {
		this.nameTable = nameTable;
	}
	public String getNameColumn() {
		return nameColumn;
	}
	public void setNameColumn(String nameColumn) {
		this.nameColumn = nameColumn;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

	public int getRowConfig() {
		return rowConfig;
	}

	public void setRowConfig(int rowConfig) {
		this.rowConfig = rowConfig;
	}
}
