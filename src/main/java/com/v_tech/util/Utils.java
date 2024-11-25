package com.v_tech.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.math3.util.Precision;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.v_tech.listeners.CustomListener;
import com.v_tech.listeners.CustomLogger;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Utils {
	
	private static final Logger logger = new CustomLogger(Utils.class);
	
	/**
	 * Returns Random Number of Three Digits
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static int genRandomNum() {
		int min = 100;
		int max = 999;
		return (int) (Math.random() * (max - min + 1) + min);
	}

	/**
	 * Returns String from Integer based on ASCII
	 * 
	 * @param integer
	 * @return
	 * @author vikram.nadella
	 */
	public static String intergerToString(int integer) {
		return integer < 0 ? "" : intergerToString((integer / 26) - 1) + (char) (65 + integer % 26);
	}

	/**
	 * Returns Double value from Given String
	 * 
	 * @param doubleText
	 * @return
	 * @author vikram.nadella
	 */
	public static double convertToDouble(String doubleText) {
		if (doubleText == null || doubleText.trim().equalsIgnoreCase("null") || doubleText.equals("")
				|| doubleText == "") {
			return 0;
		} else {
			if(doubleText.replaceAll("[^0-9.]", "").equals(""))
				return 0;
			else
				return Double.parseDouble(doubleText.replaceAll("[^0-9.]", ""));
		}
	}

	/**
	 * Reads Json from Given File Path and Returns as JsonPath
	 * 
	 * @param filePath
	 * @return
	 * @author vikram.nadella
	 */
	public static JsonPath getJsonPath(String filePath) {
		return new JsonPath(new File(filePath));
	}

	/**
	 * Returns Integer from Given String
	 * 
	 * @param stringInteger
	 * @return
	 * @author vikram.nadella
	 */
	public static int getIntegerFromString(String stringInteger) {
		return Integer.parseInt(stringInteger.replaceAll("[^0-9]", ""));
	}

	/**
	 * Returns Double from Given String
	 * 
	 * @param stringInteger
	 * @return
	 * @author vikram.nadella
	 */
	public static double getDoubleFromString(String stringInteger) {
		return convertToDouble(stringInteger);
	}

	/**
	 * Returns Double from Given String after Rounding with Given Decimal Places
	 * 
	 * @param stringInteger
	 * @param decimalPlaces
	 * @return
	 * @author vikram.nadella
	 */
	public static double getDoubleFromString(String stringInteger, int decimalPlaces) {
		return Precision.round(Double.parseDouble(stringInteger.replaceAll("[^0-9.]", "")), decimalPlaces);
	}

	/**
	 * Returns Double from Given Double Value after Rounding with Given Decimal Places
	 * 
	 * @param number
	 * @param decimalPlaces
	 * @return
	 * @author vikram.nadella
	 */
	public static double getRoundedDecimal(double number, int decimalPlaces) {
		return Precision.round(number, decimalPlaces);
	}

	/**
	 * Returns a Random Number between Given Range of Minimum an Maximum
	 * 
	 * @param min
	 * @param max
	 * @return
	 * @author vikram.nadella
	 */
	public static int genRandomNum(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}

	/**
	 * Returns Current Date in Given Date Format
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDateTime now = LocalDateTime.now();
		String date = dtf.format(now).replaceAll("^0+(?!$)", "");
		String mon = date.split("/")[1];

		if (mon.charAt(0) == '0') {
			String newMon = String.valueOf(mon.charAt(1));
			return date.split("/")[0] + "/" + newMon + "/" + date.split("/")[2];
		}

		else {
			return date;
		}
	}

	/**
	 * Returns Ebcdic Value of Given String
	 * 
	 * @param string
	 * @return
	 */
	public static String getEbcdicValue(String string) {
		return string.replaceAll("0", "a").replaceAll("1", "b").replaceAll("2", "c").replaceAll("3", "d")
				.replaceAll("4", "e").replaceAll("5", "f").replaceAll("6", "g").replaceAll("7", "h")
				.replaceAll("8", "i").replaceAll("9", "j").replaceAll("A", "00").replaceAll("B", "01")
				.replaceAll("C", "02").replaceAll("D", "03").replaceAll("E", "04").replaceAll("F", "05")
				.replaceAll("G", "06").replaceAll("H", "07").replaceAll("I", "08").replaceAll("J", "09")
				.replaceAll("K", "10").replaceAll("L", "11").replaceAll("M", "12").replaceAll("N", "13")
				.replaceAll("O", "14").replaceAll("P", "15").replaceAll("Q", "16").replaceAll("R", "17")
				.replaceAll("S", "18").replaceAll("T", "19").replaceAll("U", "20").replaceAll("V", "21")
				.replaceAll("W", "22").replaceAll("X", "23").replaceAll("Y", "24").replaceAll("Z", "25")
				.replaceAll("a", "26").replaceAll("b", "27").replaceAll("c", "28").replaceAll("d", "29")
				.replaceAll("e", "30").replaceAll("f", "31").replaceAll("g", "32").replaceAll("h", "33")
				.replaceAll("i", "34").replaceAll("j", "35");
	}

	/**
	 * Returns Date with New Format Given
	 * 
	 * @param date
	 * @param oldFormat
	 * @param newFormat
	 * @return
	 * @throws ParseException
	 * @author vikram.nadella
	 */
	public static String dateFormatter(String date, String oldFormat, String newFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		Date d = sdf.parse(date);
		sdf.applyPattern(newFormat);
		return sdf.format(d);
	}

	/**
	 * Converts Json Response to Map and Returns it
	 * 
	 * @param response
	 * @return
	 * @author srinivas
	 */
	public static LinkedHashMap<String, LinkedHashMap<String, String>> parseJSON(Response response) {
		LinkedHashMap<String, LinkedHashMap<String, String>> jsonMap = new LinkedHashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.asString());

			int totalSize = jsonNode.get("totalSize").asInt();
			JsonNode records = jsonNode.get("records");
			
			for (JsonNode record : records) {
				Iterator<String> fieldNames = record.fieldNames();
				LinkedHashMap<String, String> attrMap = new LinkedHashMap<>();
				while (fieldNames.hasNext()) {
					String fieldName = fieldNames.next();
					if (!fieldName.trim().equalsIgnoreCase("attributes")) {
						if(record.get(fieldName).isObject()) {
							LinkedHashMap<String,String> objAttrMap = buildObjectAttrMap(record.get(fieldName));
							attrMap.putAll(objAttrMap);
						}else {
							attrMap.put(fieldName, getFieldValue(record.get(fieldName)));
						}
					}
				}
				jsonMap.put(attrMap.get("Name"), attrMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonMap;
	}

	
	private static LinkedHashMap<String, String> buildObjectAttrMap(JsonNode jsonNode){
		try {
			Iterator<String> fieldNames = jsonNode.fieldNames();
			LinkedHashMap<String, String> attrMap = new LinkedHashMap<>();
			while (fieldNames.hasNext()) {
				String fieldName = fieldNames.next();
				if (!fieldName.trim().equalsIgnoreCase("attributes")) {
					if(jsonNode.get(fieldName).isObject()) {
						LinkedHashMap<String,String> objAttrMap = buildObjectAttrMap(jsonNode.get(fieldName));
						attrMap.putAll(objAttrMap);
					}else {
						attrMap.put(fieldName, getFieldValue(jsonNode.get(fieldName)));
					}
				}
			}
			return attrMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getFieldValue(JsonNode jsonNode) {
		if (jsonNode.isObject()) {
			return "object";
		} else if (jsonNode.isArray()) {
			return "array";
		} else if (jsonNode.isTextual()) {
			return jsonNode.asText();
		} else if (jsonNode.isNumber()) {
			return String.valueOf(jsonNode.asDouble());
		} else if (jsonNode.isBoolean()) {
			return String.valueOf(jsonNode.asBoolean());
		} else if (jsonNode.isNull()) {
			return "null";
		}

		return "";
	}

	/**
	 * Appends Data to Given Excel File
	 * 
	 * @param filePath
	 * @param sheetName
	 * @param colIndex
	 * @param newVal
	 */
	public static void appendDataToExcelFile(String filePath, String sheetName, int colIndex, String newVal) {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);
			newRow.createCell(colIndex).setCellValue(newVal);
			try (FileOutputStream fos = new FileOutputStream(filePath)) {
				workbook.write(fos);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns Previous or Future Date from Current Date based on Given 'num' in Given 'format'
	 * 'num' If Positive Method will Return Future Date of that many Days from Current Date
	 * 'num' If Negative Method will Return Past Date of that many Days from Current Date
	 * 
	 * @param num
	 * @param format
	 * @return Formatted Date
	 * @author Vikram.Nadella
	 */
	public static String getPreviousFutureDate(int num, String format) {
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(format);
		cal.add(Calendar.DATE, num);
		logger.info(num + " days Previous Date is " + dateFormat.format(cal.getTime()));
		return dateFormat.format(cal.getTime());
	}
	
	/**
	 * Returns Current Date in Given Format
	 * 
	 * @return Current Time
	 * @author Vikram.Nadella
	 */
	public static String getcurrentTime(String format) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
		LocalDateTime now = LocalDateTime.now();
		logger.info("Current Time is " + dateTimeFormatter.format(now));
		return dateTimeFormatter.format(now);
	}

	/**
	 * Returns Given Date in Expected Format
	 * 
	 * @param date
	 * @param currentFormat
	 * @param expectedfFormat
	 * @return
	 * @throws ParseException
	 * @author vikram.nadella
	 */
	public static String changeDateFormat(String date, String currentFormat, String expectedfFormat)
			throws ParseException {
		Date dt = new SimpleDateFormat(currentFormat).parse(date);
		return new SimpleDateFormat(expectedfFormat).format(dt);
	}
	
	public static void captureScreenshot() {
		CustomListener.step.get().addScreenCaptureFromBase64String(CustomListener.getScreenshotinBase64());
	}
}