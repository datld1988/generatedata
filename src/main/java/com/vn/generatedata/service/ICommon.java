package com.vn.generatedata.service;

import java.util.List;

import com.vn.generatedata.model.GenerateData;
import com.vn.generatedata.model.PosgresSqlColumn;

public interface ICommon {
	String convertIdxColumnToString(int idxColumn);

	String fillDataString(int length, int idxColumn, int numberRecord);
	
	List<String[]> initDataOneTable(List<PosgresSqlColumn> listColumn, GenerateData generateData);
	
//	String getDataTime(PosgresSqlColumn column);
}
