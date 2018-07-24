package com.vn.generatedata.model;

import java.io.Serializable;

public class GenerateData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String rowConfig;

	private String[] listTable;

	private String numberRecord;

	public String[] getListTable() {
		return listTable;
	}

	public void setListTable(String[] listTable) {
		this.listTable = listTable;
	}

	public String getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

	public String getRowConfig() {
		return rowConfig;
	}

	public void setRowConfig(String rowConfig) {
		this.rowConfig = rowConfig;
	}
}
