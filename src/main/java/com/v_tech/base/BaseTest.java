package com.v_tech.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.microsoft.playwright.Tracing;
import com.v_tech.assertions.GenericAssertions;
import com.v_tech.util.ExcelUtils;
import com.v_tech.util.PropertiesUtils;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.restassured.path.json.JsonPath;

public class BaseTest extends PageFactory {
	private static final Logger logger;
	public static final String ReportsPath = System.getProperty("user.dir") + File.separator + "Reports"
			+ File.separator;

	public static JsonPath locators_JsonPath;
	public static JsonPath testData;
	public static JsonPath fieldLabels_Language;

	public static String env = System.getProperty("env", "QA");

	public static Scenario scenario;

	static ObjectMapper objectMapper = new ObjectMapper();
	public static JsonNode locators_JsonNode;
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> fieldLabelsMap;

	/**
	 * Executes at Start of Test Run
	 * 
	 * @throws Exception
	 * @author vikram.nadella
	 */
	@BeforeAll
	public static void beforeAll() throws Exception {
		System.setProperty("file.encoding", "UTF-8");
		new PropertiesUtils();
		if (PropertiesUtils.useTestDataJsonFile())
			testData = new JsonPath(new File(PropertiesUtils.getTestDataPath()));
		if (PropertiesUtils.useLocatorsJsonFile()) {
			locators_JsonPath = new JsonPath(new File(PropertiesUtils.getLocatorsPath()));
			locators_JsonNode = objectMapper.readTree(new File(PropertiesUtils.getLocatorsPath()));
		}

		if (PropertiesUtils.usefieldLabels_LanguageFile()) {
			try {
				if (PropertiesUtils.getfieldLabels_LanguagePath().contains(".json"))
					fieldLabels_Language = new JsonPath(new File(PropertiesUtils.getfieldLabels_LanguagePath()));
				else if (PropertiesUtils.getfieldLabels_LanguagePath().contains(".xlsx")) {
					fieldLabels_Language = ExcelUtils
							.getExcelDataAsJsonObject(new File(PropertiesUtils.getfieldLabels_LanguagePath()));
					fieldLabelsMap = ExcelUtils.getHashMapFromExcel("src\\test\\resources\\FieldLabels.xlsx");
				} else
					throw new Exception("Unable to Parse \'" + PropertiesUtils.getfieldLabels_LanguagePath()
							+ "\' file, .json and .xlsx are allowed");
			} catch (FileNotFoundException filException) {
				GenericAssertions.fail(
						"File not Found at Given Location '" + PropertiesUtils.getfieldLabels_LanguagePath() + "'");
			} catch (Exception exp) {
				GenericAssertions.fail("Unable to Parse '" + PropertiesUtils.getfieldLabels_LanguagePath()
						+ "' file, .json and .xlsx are allowed");
			}
		}
	}

	/**
	 * Executes Before Every Scenario
	 * 
	 * @param scenario
	 * @author vikram.nadella
	 */
	@Before
	public void beforeMethod(Scenario scenario) {
		logger.info("Launching Browser...............");
		logger.info("Scenario Name: " + scenario.getName());
		PageFactory.initializeBrowser();
	}

	/**
	 * Executes After Every Scenario
	 * 
	 * @param scenario
	 * @author vikram.nadella
	 */
	@After
	public void afterMethod(Scenario scenario) {
		logger.info("Scenario Execution Status: " + scenario.getStatus());
		boolean recordScreen = Boolean.parseBoolean(PropertiesUtils.getscreenRecord_Tracing());
		if (recordScreen) {
			PageFactory.getContext().tracing()
					.stop(new Tracing.StopOptions().setPath(Paths.get(ReportsPath + File.separator + "Traces"
							+ File.separator + scenario.getName().replaceAll("[^a-zA-Z0-9]", "-")
							+ getDateAndTimeStamp() + "-trace.zip")));
		}

		PageFactory.getContext().close();
		int filesThreshold = PropertiesUtils.getReportFilesThreshold();
		deleteDirectory(new File(ReportsPath + "FullReports"), filesThreshold);
		deleteDirectory(new File(ReportsPath + "Logs"), filesThreshold);
		deleteDirectory(new File(ReportsPath + "PDF-Reports"), filesThreshold);
		deleteDirectory(new File(ReportsPath + "Screenshots"), filesThreshold);
		deleteDirectory(new File(ReportsPath + "Recordings"), filesThreshold);
		deleteDirectory(new File(ReportsPath + "Traces"), filesThreshold);
	}

	/**
	 * deleting files from 'directoryToBeDeleted' leaving 'countAccepted' number of
	 * recent files, Note: if countAccepted=0 no files will be deleted
	 * 
	 * @param directoryToBeDeleted
	 * @param countAccepted
	 * @author vikram.nadella
	 */
	public void deleteDirectory(final File directoryToBeDeleted, int countAccepted) {
		final File[] allFiles = directoryToBeDeleted.listFiles();
		if (countAccepted != 0) {
			if (allFiles != null) {
				if (allFiles.length > countAccepted) {
					Arrays.sort(allFiles, Comparator.comparingLong(File::lastModified).reversed());
					for (int i = countAccepted; i < allFiles.length; i++) {
						try {
							allFiles[i].delete();
						} catch (Exception e) {

						}
					}
				}
			}
		}
	}

	/**
	 * Returns Time Stamp in dd-MM-yyyy HH:mm:ss Format
	 * 
	 * @return current timestamp
	 * @author vikram.nadella
	 */
	public static String getDateAndTimeStamp() {
		final Date date = new Date();
		final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return formatter.format(date).replaceAll("/", "_").replaceAll("-", "_").replaceAll(":", "_").replaceAll(" ",
				"_");
	}

	static {
		logger = LogManager.getLogger(BaseTest.class);
	}
}
