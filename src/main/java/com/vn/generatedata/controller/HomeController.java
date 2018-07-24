package com.vn.generatedata.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vn.generatedata.model.ConfigConnection;
import com.vn.generatedata.model.GenerateData;
import com.vn.generatedata.model.PosgresSqlTable;
import com.vn.generatedata.service.IConnect;
import com.vn.generatedata.service.IPosgresSQLService;

@Controller
public class HomeController {

	private static final Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	ServletContext context;

	@Autowired
	private IPosgresSQLService service;

	@Autowired
	private IConnect conn;

	private Connection connection = null;

	private DatabaseMetaData dmd = null;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws SQLException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws SQLException {
		System.out.println(locale.getDisplayCountry());
		return "home";
	}

	@RequestMapping(value = "downloadExcel", method = RequestMethod.POST)
	public void downloadExcel(@Validated GenerateData generateData, Model model, HttpServletResponse response)
			throws SQLException, IOException {

		String fileName = service.genData(dmd, generateData);

		try {
			String downloadFolder = context.getRealPath("/WEB-INF/SaveFile");
			File file = new File(downloadFolder + File.separator + fileName);

			if (file.exists()) {
				String mimeType = context.getMimeType(file.getPath());

				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date now = new Date();
				String strDate = sdf.format(now);
				response.setContentType(mimeType);
				response.addHeader("Content-Disposition", "attachment; filename=" + "DataGenerate " + strDate + ".xlsx");
				response.setContentLength((int) file.length());

				OutputStream os = response.getOutputStream();
				FileInputStream fis = new FileInputStream(file);
				byte[] buffer = new byte[4096];
				int b = -1;

				while ((b = fis.read(buffer)) != -1) {
					os.write(buffer, 0, b);
				}
				response.flushBuffer();
				fis.close();
				os.close();
			} else {
				System.out.println("Requested " + fileName + " file not found!!");
			}
		} catch (IOException e) {
			System.out.println("Error:- " + e.getMessage());
		}
	}

	@RequestMapping(value = "/getonetable", method = RequestMethod.GET)
	public String getOneTable(@RequestParam("tableName") String tableName, Model model) throws SQLException {

		PosgresSqlTable oneTable = service.oneTable(tableName, dmd);
		List<String> allTable = service.getListOfAllTables(dmd);

		model.addAttribute("errCode", "0");
		model.addAttribute("oneTable", oneTable);
		model.addAttribute("allTable", allTable);
		return "tabledetail";
	}

	@RequestMapping(value = "/getalltable", method = RequestMethod.POST)
	public String user(@Validated ConfigConnection configConnection, Model model) throws SQLException {
		System.out.println("User Page Requested");
		model.addAttribute("userName", configConnection.getIp());

		// logs debug message
		if (logger.isDebugEnabled()) {
			logger.debug("getWelcome is executed!");
		}

		// logs exception
		logger.error("This is Error message", new Exception("Testing"));

		connection = conn.getConnection(configConnection.getIp(), configConnection.getDbName(),
				configConnection.getUsername(), configConnection.getPassword());

		if (connection == null) {
			model.addAttribute("errCode", "1");
			model.addAttribute("errInfo", "Sai thông tin kết nối. Kiểm tra lại");
			return "home";
		}

		dmd = conn.getMetaData(connection);

		List<PosgresSqlTable> allTableStruct = service.searchForColumnNameInTables(dmd);

		model.addAttribute("errCode", "0");
		model.addAttribute("allTableStruct", allTableStruct);
		return "alltable";
	}
}
