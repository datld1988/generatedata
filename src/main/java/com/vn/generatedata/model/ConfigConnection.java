package com.vn.generatedata.model;

import java.io.Serializable;

public class ConfigConnection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String type;

	private String ip;

	private String dbName;

	private String username;

	private String password;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}