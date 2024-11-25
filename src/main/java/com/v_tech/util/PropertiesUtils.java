package com.v_tech.util;

import java.io.File;
import java.io.FileReader;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertiesUtils {
	private static PropertiesConfiguration prop;
	private static PropertiesConfiguration staticProperties;

	static Logger logger = LogManager.getLogger(PropertiesUtils.class);

	/**
	 * Reads Properties from Properties Files
	 * 
	 * @author vikram.nadella
	 */
	public PropertiesUtils() {
		String env = System.getProperty("env", "QA");
		try {
			prop = new PropertiesConfiguration();
			PropertiesConfigurationLayout propertiesConfigurationLayout = new PropertiesConfigurationLayout();
			propertiesConfigurationLayout.load(prop, new FileReader(
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
							+ "resources" + File.separator + env.toUpperCase() + File.separator + "config.properties"));
			prop.setLayout(propertiesConfigurationLayout);

			staticProperties = new PropertiesConfiguration();
			PropertiesConfigurationLayout staticPropertiesConfigurationLayout = new PropertiesConfigurationLayout();
			staticPropertiesConfigurationLayout.load(staticProperties, new FileReader(
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
							+ "resources" + File.separator + "config.properties"));
			staticProperties.setLayout(staticPropertiesConfigurationLayout);
		} catch (Exception e) {
			throw new RuntimeException("Unable to load the property file", e);
		}
	}

	/**
	 * Returns Internal User URL
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static String getInternalUserURL() {
		return prop.getString("internalUser_URL");
	}

	/**
	 * Returns External User URL
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static String getExternalUserURL() {
		return prop.getString("externalUser_URL");
	}

	/**
	 * Returns Property Value for Given Key as String
	 * 
	 * @param key
	 * @return
	 * @author vikram.nadella
	 */
	public static String getProperty(String key) {
		return prop.getString(key);
	}

	/**
	 * Returns Property Value for Given Key as Integer
	 * 
	 * @param key
	 * @return
	 * @author vikram.nadella
	 */
	public static int getInt(String key) {
		return Integer.parseInt(prop.getString(key));
	}

	/**
	 * Returns Property Value for Given Key as Double
	 * 
	 * @param key
	 * @return
	 * @author vikram.nadella
	 */
	public static double getDouble(String key) {
		return Double.parseDouble(prop.getString(key));
	}

	/**
	 * Returns Property Value for Given Key and If Null Returns defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 * @author vikram.nadella
	 */
	public static String getProperty(String key, String defaultValue) {
		if (prop.getString(key) == null)
			return defaultValue;
		return prop.getString(key);
	}

	/**
	 * Sets Given Value to the Given Key in Properties
	 * 
	 * @param key
	 * @param value
	 * @author vikram.nadella
	 */
	public static void setProperty(String key, String value) {
		prop.setProperty(key, value);
	}

	/**
	 * Returns Boolean Value for useTestDataJsonFile Property from config.properties
	 * Eg: true, false
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static Boolean useTestDataJsonFile() {
		return Boolean.parseBoolean(staticProperties.getString("useTestDataJsonFile"));
	}

	/**
	 * Returns Test Data Json File Path from Static Properties Relative Path:
	 * 'src\\test\\resources\\{env}\\TestData.json'
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static String getTestDataPath() {
		return staticProperties.getString("testData_Path").replace("{1}", System.getProperty("env", "QA"));
	}

	/**
	 * Returns Editable Fields Json File Path from Static Properties Relative Path:
	 * 'src\\test\\resources\\EditableFieldsCheck.json'
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static String getEditableFieldsPath() {
		return staticProperties.getString("editableFields_Path");
	}

	/**
	 * Returns Detail Page Fields Json File Path from Static Properties Relative
	 * Path: 'src\\test\\resources\\DetailPageFields.json'
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static String getDetailPageFieldsPath() {
		return staticProperties.getString("detailPageFields_Path");
	}

	/**
	 * Returns Boolean Value for useLocatorsJsonFile Property from config.properties
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static Boolean useLocatorsJsonFile() {
		return Boolean.parseBoolean(staticProperties.getString("useLocatorsJsonFile"));
	}

	/**
	 * Returns Locators Json File Path from Static Properties Relative Path:
	 * 'src\\test\\resources\\FieldLabels.json' or
	 * 'src\\test\\resources\\FieldLabels.xlsx'
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static String getLocatorsPath() {
		return staticProperties.getString("locators_Path");
	}

	/**
	 * Returns Boolean Value for usefieldLabels_LanguageFile Property from
	 * config.properties
	 * Eg: true, false
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static Boolean usefieldLabels_LanguageFile() {
		return Boolean.parseBoolean(staticProperties.getString("usefieldLabels_LanguageFile"));
	}

	/**
	 * Returns Field Label Language Json File Path from Static Properties Relative
	 * Path:
	 * 'src\\test\\resources\\FieldLabels_Language.json' or
	 * 'src\\test\\resources\\FieldLabels_Language.xlsx'
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static String getfieldLabels_LanguagePath() {
		return staticProperties.getString("fieldLabels_LanguagePath");
	}

	/**
	 * Returns Report Files Threshold, Default was Set to 5
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static int getReportFilesThreshold() {
		return staticProperties.getInt("filesThreshold_Reports");
	}

	/**
	 * Returns Element Visibility Timeout, Default was Set to 60 Seconds
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static double getElementVisibleTimeout() {
		return staticProperties.getDouble("defaultTimeOut");
	}

	/**
	 * Returns Page Load Timeout, Default was Set to 120 Seconds
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static double getPageLoadTimeout() {
		return staticProperties.getDouble("pageLoadTimeout");
	}

	/**
	 * Returns Slow Motion Time, Default was Set to 200 Milliseconds
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static double getSlowMoTime() {
		return staticProperties.getDouble("sloMoTime");
	}

	/**
	 * Returns True or False for Screen Recording and Tracing
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static String getscreenRecord_Tracing() {
		return staticProperties.getString("screenRecord_Tracing");
	}

}
