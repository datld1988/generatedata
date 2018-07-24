package com.vn.generatedata.service.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.vn.generatedata.service.IConnect;

@Service
public class ConnectImpl implements IConnect {

	@Override
	public Connection getConnection(String ip, String dbName, String username, String password) {
		System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return null;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {
			StringBuffer buff = new StringBuffer("jdbc:postgresql://").append(ip).append(":5432/").append(dbName);

			connection = DriverManager.getConnection(buff.toString(), username, password);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;

		}
		return connection;
	}

	@Override
	public DatabaseMetaData getMetaData(Connection conn) throws SQLException {
		return conn.getMetaData();
	}

	
}
