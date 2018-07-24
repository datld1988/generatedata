package com.vn.generatedata.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vn.generatedata.constant.BitPosgresSQL;
import com.vn.generatedata.constant.NumberPosgresSQL;
import com.vn.generatedata.constant.OtherTypePosgresSQL;
import com.vn.generatedata.constant.TextPosgresSQL;
import com.vn.generatedata.constant.TimePosgresSQL;
import com.vn.generatedata.model.GenerateData;
import com.vn.generatedata.model.PosgresSqlColumn;
import com.vn.generatedata.model.PosgresSqlTable;
import com.vn.generatedata.service.ICommon;

@Service
public class Common implements ICommon {

	Calendar calendar = Calendar.getInstance();

	@Override
	public String convertIdxColumnToString(int idxColumn) {
		switch (idxColumn) {
		case 1:
			return "A";
		case 2:
			return "B";
		case 3:
			return "C";
		case 4:
			return "D";
		case 5:
			return "E";
		case 6:
			return "F";
		case 7:
			return "G";
		case 8:
			return "H";
		case 9:
			return "I";
		case 10:
			return "J";
		case 11:
			return "K";
		case 12:
			return "L";
		case 13:
			return "M";
		case 14:
			return "N";
		case 15:
			return "O";
		case 16:
			return "P";
		case 17:
			return "Q";
		case 18:
			return "R";
		case 19:
			return "S";
		case 20:
			return "T";
		case 21:
			return "U";
		default:
			return "AA";

		}
	}

	@Override
	public String fillDataString(int length, int idxColumn, int numberRecord) {
		StringBuffer data = new StringBuffer();

		switch (length) {
		case 1:
			data.append(convertIdxColumnToString(idxColumn));
			break;

		case 2:
			data.append(convertIdxColumnToString(idxColumn)).append(convertIdxColumnToString(idxColumn));
			break;

		case 3:
			data.append(convertIdxColumnToString(idxColumn)).append(convertIdxColumnToString(idxColumn))
					.append(convertIdxColumnToString(idxColumn));
			break;

		default:
			data.append(convertIdxColumnToString(idxColumn))
					.append(StringUtils.leftPad(String.valueOf(numberRecord), length - 1, "0"));
			break;
		}

		return data.toString();
	}

	private List<PosgresSqlColumn> columnConfig(String tableName, String json) {

		ObjectMapper mapper = new ObjectMapper();
		List<PosgresSqlColumn> listColumn = new ArrayList<PosgresSqlColumn>();

		try {

			// Convert JSON string from file to Object

			Map<String, List<PosgresSqlTable>> map = mapper.readValue("{\"root\":" + json + "}", Map.class);

			List<PosgresSqlTable> lstTable = mapper.convertValue(map.get("root"),
					new TypeReference<List<PosgresSqlTable>>() {
					});

			PosgresSqlTable table = lstTable.stream()
					.filter(tbl -> tbl.getTableName().toLowerCase().equals(tableName.toLowerCase())).findFirst()
					.orElse(null);

			if (table != null) {
				listColumn = table.getListColumn();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listColumn;
	}

	@Override
	public List<String[]> initDataOneTable(List<PosgresSqlColumn> listColumn, GenerateData generateData) {
		List<String[]> dataTable = new ArrayList<>();

		List<PosgresSqlColumn> clu = new ArrayList<PosgresSqlColumn>();

		if (generateData.getRowConfig() != null) {
			clu = columnConfig(listColumn.get(0).getNameTable(), generateData.getRowConfig());
		}

		int numberRecord = Integer.parseInt(generateData.getNumberRecord());

		for (int i = 0; i < numberRecord; i++) {
			int numberColumn = 0;
			String[] record = new String[listColumn.size()];
			for (PosgresSqlColumn column : listColumn) {

				int length = column.getLength();
				if (column.getLength() > 2000)
					length = 2000;

				int config = clu.stream()
						.filter(col -> col.getType().toLowerCase().equals(column.getType().toLowerCase())
								&& col.getNameColumn().toLowerCase().equals(column.getNameColumn().toLowerCase()))
						.findFirst().orElse(new PosgresSqlColumn()).getRowConfig();

				// Create Data Boolean
				if (OtherTypePosgresSQL.BOOLEAN.equals(column.getType())) {
					switch (config) {
					case 1:
						record[numberColumn] = "TRUE";
						break;

					case 2:
						record[numberColumn] = "FALSE";
						break;
					default:
						record[numberColumn] = "TRUE";
						break;
					}
				}
				if (OtherTypePosgresSQL.BOOLEAN_.equals(column.getType())) {
					switch (config) {
					case 1:
						record[numberColumn] = "{TRUE}";
						break;

					case 2:
						record[numberColumn] = "{FALSE}";
						break;
					default:
						record[numberColumn] = "{TRUE}";
						break;
					}
				}

				// Create Data Bit
				if (BitPosgresSQL.BIT.equals(column.getType()) || BitPosgresSQL.BIT_VARYING.equals(column.getType())) {

					Random rand = new Random();

					StringBuffer newText = new StringBuffer();

					for (int j = 0; j < length; j++) {
						newText.append(String.valueOf(rand.nextInt(2)));
					}
					record[numberColumn] = newText.toString();
				} else if (BitPosgresSQL.BIT_VARYING_.equals(column.getType())
						|| BitPosgresSQL.BIT_.equals(column.getType())) {

					Random rand = new Random();

					StringBuffer newText = new StringBuffer();

					for (int j = 0; j < length; j++) {
						newText.append(String.valueOf(rand.nextInt(2)));
					}
					record[numberColumn] = "{ " + newText.toString() + " }";
				}

				// Create Data NUMBER
				if (NumberPosgresSQL.BIGINT.equals(column.getType())
						|| NumberPosgresSQL.BIGSERIAL.equals(column.getType())
						|| NumberPosgresSQL.SERIAL.equals(column.getType())
						|| NumberPosgresSQL.SMALLINT.equals(column.getType())
						|| NumberPosgresSQL.SMALLSERIAL.equals(column.getType())
						|| NumberPosgresSQL.NUMERIC.equals(column.getType())
						|| NumberPosgresSQL.NUMRANGE.equals(column.getType())) {

					length = 10;

					Random rand = new Random();

					StringBuffer newText = new StringBuffer();

					for (int j = 0; j < length; j++) {
						newText.append(String.valueOf(rand.nextInt(9)));
					}
					record[numberColumn] = newText.toString();
				} else if (NumberPosgresSQL.BIGINT_.equals(column.getType())
						|| NumberPosgresSQL.NUMERIC_.equals(column.getType())
						|| NumberPosgresSQL.NUMRANGE_.equals(column.getType())
						|| NumberPosgresSQL.SMALLINT_.equals(column.getType())) {

					length = 10;

					Random rand = new Random();

					StringBuffer newText = new StringBuffer();

					for (int j = 0; j < length; j++) {
						newText.append(String.valueOf(rand.nextInt(9)));
					}
					record[numberColumn] = "{ " + newText.toString() + " }";
				}

				// Create Data String
				if (TextPosgresSQL.CHARACTER.equals(column.getType()) || TextPosgresSQL.CHAR.equals(column.getType())) {
					record[numberColumn] = "a";
				} else if (TextPosgresSQL.CHARACTER_.equals(column.getType())
						|| TextPosgresSQL.CHAR_.equals(column.getType())) {
					record[numberColumn] = "{a}";
				} else if (TextPosgresSQL.CHARACTER_VARYING.equals(column.getType())
						|| TextPosgresSQL.TEXT.equals(column.getType())) {

					switch (config) {
					case 1:

						// Init FULL SIZE
						record[numberColumn] = new StringBuffer("ƒTƒ“ƒvƒ‹ƒeƒLƒXƒg")
								.append(StringUtils.leftPad(getFullSizeNumber(i + 1), (length - 24) / 3, "‚O"))
								.toString();
						break;
					case 2:

						// Init FULL SIZE
						record[numberColumn] = new StringBuffer("Example text")
								.append(StringUtils.leftPad(getFullSizeNumber(i + 1), (length - 12), "0")).toString();
						break;
					case 3:

						Random rand = new Random();

						StringBuffer newText = new StringBuffer();

						for (int j = 0; j < length; j++) {
							newText.append(String.valueOf(rand.nextInt(9)));
						}
						record[numberColumn] = newText.toString();

						break;
					default:
						record[numberColumn] = fillDataString(length, numberColumn + 1, i + 1);
						break;
					}
				} else if (TextPosgresSQL.CHARACTER_VARYING_.equals(column.getType())
						|| TextPosgresSQL.TEXT_.equals(column.getType())) {

					switch (config) {
					case 1:

						// Init FULL SIZE
						record[numberColumn] = "{" + new StringBuffer("ƒTƒ“ƒvƒ‹ƒeƒLƒXƒg")
								.append(StringUtils.leftPad(getFullSizeNumber(i + 1), (length - 24) / 3, "‚O"))
								.toString() + "}";
						break;
					case 2:

						// Init HALF SIZE
						record[numberColumn] = "{" + new StringBuffer("Example text")
								.append(StringUtils.leftPad(getHalfSizeNumber(i + 1), (length - 12), "0")).toString()
								+ "}";
						break;
					case 3:

						Random rand = new Random();

						StringBuffer newText = new StringBuffer();

						for (int j = 0; j < length; j++) {
							newText.append(String.valueOf(rand.nextInt(9)));
						}
						record[numberColumn] = "{" + newText.toString() + "}";

						break;
					default:
						record[numberColumn] = "{" + fillDataString(length, numberColumn + 1, i + 1) + "}";
						break;
					}
				}

				// Create TIME DATA
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

				String timeStampArr = "{ " + timeStamp + " }";

				if (TimePosgresSQL.TIME_WITHOUT_TIME_ZONE.equals(column.getType())
						|| TimePosgresSQL.TIME_WITH_TIME_ZONE.equals(column.getType())
						|| TimePosgresSQL.TIMESTAMP_WITH_TIME_ZONE.equals(column.getType())
						|| TimePosgresSQL.TIMESTAMP_WITHOUT_TIME_ZONE.equals(column.getType())) {

					record[numberColumn] = timeStamp;
				} else if (TimePosgresSQL.TIME_WITH_TIME_ZONE_.equals(column.getType())
						|| TimePosgresSQL.TIME_WITHOUT_TIME_ZONE_.equals(column.getType())
						|| TimePosgresSQL.TIMESTAMP_WITH_TIME_ZONE_.equals(column.getType())
						|| TimePosgresSQL.TIMESTAMP_WITHOUT_TIME_ZONE_.equals(column.getType())) {

					record[numberColumn] = timeStampArr;
				}

				numberColumn++;
			}

			dataTable.add(record);
		}

		return dataTable;
	}

	private String getHalfSizeNumber(int numberColumn) {

		StringBuffer fullSize = new StringBuffer();

		String temp = Integer.toString(numberColumn);
		for (int i = 0; i < temp.length(); i++) {
			switch (temp.charAt(i) - '0') {
			case 1:
				fullSize.append("1");
				break;

			case 2:
				fullSize.append("2");
				break;
			case 3:
				fullSize.append("3");
				break;
			case 4:
				fullSize.append("4");
				break;
			case 5:
				fullSize.append("5");
				break;
			case 6:
				fullSize.append("6");
				break;
			case 7:
				fullSize.append("7");
				break;
			case 8:
				fullSize.append("8");
				break;
			case 9:
				fullSize.append("9");
				break;
			case 0:
				fullSize.append("0");
				break;
			}
		}
		return fullSize.toString();
	}

	private String getFullSizeNumber(int numberColumn) {

		StringBuffer fullSize = new StringBuffer();

		String temp = Integer.toString(numberColumn);
		for (int i = 0; i < temp.length(); i++) {
			switch (temp.charAt(i) - '0') {
			case 1:
				fullSize.append("‚P");
				break;

			case 2:
				fullSize.append("‚Q");
				break;
			case 3:
				fullSize.append("‚R");
				break;
			case 4:
				fullSize.append("‚S");
				break;
			case 5:
				fullSize.append("‚T");
				break;
			case 6:
				fullSize.append("‚U");
				break;
			case 7:
				fullSize.append("‚V");
				break;
			case 8:
				fullSize.append("‚W");
				break;
			case 9:
				fullSize.append("‚X");
				break;
			case 0:
				fullSize.append("‚O");
				break;
			}
		}
		return fullSize.toString();
	}
}
