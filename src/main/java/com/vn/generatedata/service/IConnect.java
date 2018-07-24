package com.vn.generatedata.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public interface IConnect {
	
	/** Kiểm tra két nối đến database. Nếu kết nối thành công trả về TRUE, ngược lại thì FALSE */
	Connection getConnection(String ip, String dbName, String username, String password);
	
	DatabaseMetaData getMetaData(Connection conn) throws SQLException;	
}
