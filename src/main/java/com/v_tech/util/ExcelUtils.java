package com.v_tech.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import io.restassured.path.json.JsonPath;

public class ExcelUtils {
	private static final Logger logger = LogManager.getLogger(ExcelUtils.class);

	/**
	 * Return Cell Value from Excel based on the Sheet Name, Column Header and Row
	 * Header
	 * 
	 * @param sheetName
	 * @param columHeader
	 * @param rowHeader
	 * @return
	 * @throws IOException
	 * @author vikram.nadella
	 */
	public static String getExceldata(File xlFile, String sheetName, String columHeader, String rowHeader)
			throws IOException {
		FileInputStream fs = new FileInputStream(xlFile);
		try (XSSFWorkbook xlWB = new XSSFWorkbook(fs)) {
			XSSFSheet xlSheet = xlWB.getSheet(sheetName);
			int numRows = xlSheet.getLastRowNum() + 1;
			int numCols = xlSheet.getRow(0).getLastCellNum();
			int columHeaderIndex = 0;
			int rowHeaderIndex = 0;

			for (int i = 0; i < numRows; i++) {
				XSSFRow xlRow = xlSheet.getRow(i);
				for (int j = 0; j < numCols; j++) {
					XSSFCell xlCell = xlRow.getCell(j);
					if (rowHeader.equalsIgnoreCase(xlCell.toString()))
						rowHeaderIndex = i;
					if (columHeader.equalsIgnoreCase(xlCell.toString()))
						columHeaderIndex = j;
				}
			}

			XSSFRow xlRow = xlSheet.getRow(rowHeaderIndex);
			XSSFCell xlCell = xlRow.getCell(columHeaderIndex);
			return xlCell.toString();
		}
	}

	/**
	 * Writes into Cell Value from Excel based on the Sheet Name, Column Header and
	 * Row Header
	 * 
	 * @param xlFile
	 * @param sheetName
	 * @param columHeader
	 * @param rowHeader
	 * @param setText
	 * @throws IOException
	 * @author vikram.nadella
	 */
	public static void setExceldata(File xlFile, String sheetName, String columHeader, String rowHeader, Object setText)
			throws IOException {
		FileInputStream fs = new FileInputStream(xlFile);
		try (XSSFWorkbook xlWB = new XSSFWorkbook(fs)) {
			XSSFSheet xlSheet = xlWB.getSheet(sheetName);
			int numRows = xlSheet.getLastRowNum() + 1;
			int numCols = xlSheet.getRow(0).getLastCellNum();
			int columHeaderIndex = 0;
			int rowHeaderIndex = 0;

			for (int i = 0; i < numRows; i++) {
				XSSFRow xlRow = xlSheet.getRow(i);
				for (int j = 0; j < numCols; j++) {
					XSSFCell xlCell = xlRow.getCell(j);
					if (rowHeader.equalsIgnoreCase(xlCell.toString()))
						rowHeaderIndex = i;
					if (columHeader.equalsIgnoreCase(xlCell.toString()))
						columHeaderIndex = j;
				}
			}

			XSSFRow xlRow = xlSheet.getRow(rowHeaderIndex);
			XSSFCell xlCell = xlRow.getCell(columHeaderIndex);
			xlCell.setCellValue(setText.toString());
		}
	}

	/**
	 * Converts Excel into JsonPath and Returns it
	 * 
	 * @param excelFile
	 * @return
	 * @author vikram.nadella
	 */
	public static JsonPath getExcelDataAsJsonObject(File excelFile) {
		logger.info("Converting EXCEL to JSON...");
		JsonObject sheetsJsonObject = new JsonObject();
		Workbook workbook = null;

		try {
			workbook = new XSSFWorkbook(excelFile);
		} catch (InvalidFormatException | IOException e) {
		}

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			JsonArray sheetArray = new JsonArray();
			ArrayList<String> columnNames = new ArrayList<String>();
			Sheet sheet = workbook.getSheetAt(i);
			Iterator<Row> sheetIterator = sheet.iterator();

			while (sheetIterator.hasNext()) {
				Row currentRow = sheetIterator.next();
				JsonObject jsonObject = new JsonObject();
				if (currentRow.getRowNum() != 0) {
					for (int j = 0; j < columnNames.size(); j++) {
						if (currentRow.getCell(j) != null) {
							if (currentRow.getCell(j).getCellType() == CellType.STRING)
								jsonObject.addProperty(columnNames.get(j), currentRow.getCell(j).getStringCellValue());
							else if (currentRow.getCell(j).getCellType() == CellType.NUMERIC)
								jsonObject.addProperty(columnNames.get(j), currentRow.getCell(j).getNumericCellValue());
							else if (currentRow.getCell(j).getCellType() == CellType.BOOLEAN)
								jsonObject.addProperty(columnNames.get(j), currentRow.getCell(j).getBooleanCellValue());
							else if (currentRow.getCell(j).getCellType() == CellType.BLANK)
								jsonObject.addProperty(columnNames.get(j), "");
						} else
							jsonObject.addProperty(columnNames.get(j), "");
					}
					sheetArray.add(jsonObject);
				} else {
					for (int k = 0; k < currentRow.getPhysicalNumberOfCells(); k++)
						columnNames.add(currentRow.getCell(k).getStringCellValue());
				}
			}
			sheetsJsonObject.add(workbook.getSheetName(i), sheetArray);
		}
		return new JsonPath(sheetsJsonObject.toString());
	}

	/**
	 * Append New Data into CSV File in New Line
	 * 
	 * @param filePath
	 * @param data
	 * @throws IOException
	 * @author Kiran.thaduri
	 */
	public static void setDataInExcel(String filePath, String[] data) throws IOException {
		CSVWriter writer = null;
		try {
			File file = new File(filePath);
			FileWriter outputfile = new FileWriter(file, true);
			writer = new CSVWriter(outputfile);
			writer.writeNext(data);
		} catch (Exception e) {
			logger.error("Exception: " + e.getLocalizedMessage());
		} finally {
			writer.flush();
			writer.close();
		}
	}

	/**
	 * Clear Given Number of Rows from Start in a CSV File
	 * 
	 * @param filePath
	 * @param rowNum
	 * @throws IOException
	 * @throws CsvException
	 * @author Kiran.thaduri
	 */
	public static void clearExcelRows(String filePath, int rowNum) throws IOException, CsvException {
		CSVReader reader = null;
		CSVWriter writer = null;
		try {
			File file = new File(filePath);
			reader = new CSVReader(new FileReader(filePath));
			List<String[]> allElements = reader.readAll();
			logger.debug("Before remove: " + allElements);
			for (int i = 1; i <= rowNum; i++) {
				allElements.remove(i);
			}
			logger.debug("After remove: " + allElements);
			FileWriter outputfile = new FileWriter(file);
			writer = new CSVWriter(outputfile);
			writer.writeAll(allElements);
			logger.info("Excel Rows Cleared Successfully");
		} catch (IOException e) {
			logger.error("IO Exception " + e.getLocalizedMessage());
		} catch (CsvException e) {
			logger.error("CSV Exception " + e.getLocalizedMessage());
		} catch (IndexOutOfBoundsException e) {
		} finally {
			if (writer != null)
				writer.close();
			if (reader != null)
				reader.close();
		}
	}

	/**
	 * Clear All Rows from Start in a CSV File
	 * 
	 * @param filePath
	 * @throws IOException
	 * @throws CsvException
	 * @author Kiran.thaduri
	 */
	public static void clearExcelRows(String filePath) throws IOException, CsvException {
		CSVReader reader = null;
		CSVWriter writer = null;
		try {
			File file = new File(filePath);
			reader = new CSVReader(new FileReader(filePath));
			List<String[]> allElements = reader.readAll();
			logger.debug("Before remove: " + allElements);
			for (int i = 1; i < allElements.size(); i++) {
				allElements.remove(i);
			}
			logger.debug("After remove: " + allElements);
			FileWriter outputfile = new FileWriter(file);
			writer = new CSVWriter(outputfile);
			writer.writeAll(allElements);
			logger.info("Excel Rows Cleared Successfully");
		} catch (IOException e) {
			logger.error("IO Exception " + e.getLocalizedMessage());
		} catch (CsvException e) {
			logger.error("CSV Exception " + e.getLocalizedMessage());
		} catch (IndexOutOfBoundsException e) {
		} finally {
			if (writer != null)
				writer.close();
			if (reader != null)
				reader.close();
		}
	}

	/**
	 * Returns Excel File as a LinkedHashMap
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 * @author vikram.nadella
	 */
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> getHashMapFromExcel(
			String filePath) throws Exception {
		FileInputStream file = new FileInputStream(filePath);
		XSSFWorkbook wb = new XSSFWorkbook(file);
		int sheetCount = wb.getNumberOfSheets();
		LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> fileMap = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>();
		for (int i = 0; i < sheetCount; i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			Iterator<Row> itr = sheet.iterator();
			logger.debug("Working in Sheet: " + sheet.getSheetName());
			LinkedHashMap<String, LinkedHashMap<String, String>> sheetMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
			while (itr.hasNext()) {
				LinkedHashMap<String, String> rowMap = new LinkedHashMap<String, String>();
				Row row = itr.next();
				logger.debug("Row Index: " + row.getRowNum() + " | Row Header: " + row.getCell(0));
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					rowMap.put(getCellValue(sheet.getRow(0).getCell(cell.getColumnIndex())), getCellValue(cell));
				}
				sheetMap.put(getCellValue(row.getCell(0)), rowMap);
			}
			fileMap.put(wb.getSheetName(i), sheetMap);
		}
		return fileMap;
	}

	/**
	 * Returns Excel File as a LinkedHashMap
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static LinkedHashMap<String, LinkedHashMap<Integer, LinkedHashMap<String, String>>> getHashMapFromExcelWithIndex(
			String filePath) throws Exception {
		FileInputStream file = new FileInputStream(filePath);
		XSSFWorkbook wb = new XSSFWorkbook(file);
		int sheetCount = wb.getNumberOfSheets();
		LinkedHashMap<String, LinkedHashMap<Integer, LinkedHashMap<String, String>>> fileMap = new LinkedHashMap<String, LinkedHashMap<Integer, LinkedHashMap<String, String>>>();
		for (int i = 0; i < sheetCount; i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			Iterator<Row> itr = sheet.iterator();
			logger.debug("Working in Sheet: " + sheet.getSheetName());
			LinkedHashMap<Integer, LinkedHashMap<String, String>> sheetMap = new LinkedHashMap<Integer, LinkedHashMap<String, String>>();
			while (itr.hasNext()) {
				LinkedHashMap<String, String> rowMap = new LinkedHashMap<String, String>();
				Row row = itr.next();
				logger.debug("Row Index: " + row.getRowNum() + " | Row Header: " + row.getCell(0));
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					rowMap.put(getCellValue(sheet.getRow(0).getCell(cell.getColumnIndex())), getCellValue(cell));
				}
				sheetMap.put(row.getRowNum(), rowMap);
			}
			fileMap.put(wb.getSheetName(i), sheetMap);
		}
		return fileMap;
	}

	/**
	 * Get Cell Value based on Type
	 * 
	 * @param cell
	 * @return
	 * @author vikram.nadella
	 */
	private static String getCellValue(Cell cell) {
		switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case FORMULA:
				return cell.getCellFormula();
			case BLANK:
				return "";
			default:
				return "";
		}
	}

	public static void writeExcel(LinkedHashMap<Integer, LinkedHashMap<String, String>> finalMap, File outputFile) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet("Vehicle Result");
		XSSFRow row = spreadsheet.createRow(0);
		int cellid = 0;
		Iterator<String> columnHeaders = finalMap.get(1).keySet().iterator();

		while (columnHeaders.hasNext()) {
			String key = (String) columnHeaders.next();
			row.createCell(cellid++).setCellValue(key);
		}

		int rowid = 1;
		Iterator<Entry<Integer, LinkedHashMap<String, String>>> mapIterator = finalMap.entrySet().iterator();

		while (mapIterator.hasNext()) {
			Entry<Integer, LinkedHashMap<String, String>> entry = mapIterator.next();
			row = spreadsheet.createRow(rowid++);
			LinkedHashMap<String, String> map = entry.getValue();
			cellid = 0;
			Iterator<Map.Entry<String, String>> var10 = map.entrySet().iterator();

			while (var10.hasNext()) {
				Entry<String, String> entry1 = var10.next();
				row.createCell(cellid++).setCellValue((String) entry1.getValue());
			}
		}

		try {
			FileOutputStream out = new FileOutputStream(outputFile);
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
			workbook.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}
}