package com.v_tech.util;

import static com.v_tech.util.DataInputMethods.getTestData;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.microsoft.playwright.Locator;
import com.v_tech.actions.WebActions;
import com.v_tech.assertions.GenericAssertions;
import com.v_tech.base.BaseTest;
import com.v_tech.base.PageFactory;
import com.v_tech.listeners.CustomLogger;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.restassured.path.json.JsonPath;

public class LocatorUtils {
	private static final Logger logger = new CustomLogger(LocatorUtils.class);

	private static final JsonPath fieldSetdataJsonPath = BaseTest.locators_JsonPath;
	private static final JsonNode fieldSetdataJsonNode = BaseTest.locators_JsonNode;

		/**
		 * Returns Field Label of Given Language for Given Alias
		 * 
		 * @param page
		 * @param alias
		 * @return
		 * @author vikram.nadella
		 */
		public static String getFieldLabelInLanguage(String page, String alias) {
			logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
			String language = System.getProperty("language", "English");
			String tempRef = null;
			if(PropertiesUtils.getfieldLabels_LanguagePath().contains(".json")) {
				tempRef = BaseTest.fieldLabels_Language
						.getString(page + ".findAll {it.alias=='" + alias + "'}." + language).replace("[", "").replace("]", "");
			}
			else if(PropertiesUtils.getfieldLabels_LanguagePath().contains(".xlsx")){
				tempRef = BaseTest.fieldLabelsMap.get(page).get(alias).get(language);
			}
			else {
				GenericAssertions.fail("Given Field Labels File is Not of Format JSON or XLSX");
			}
			if (tempRef == null) {
				tempRef = "";
			}
			logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
			return tempRef;
		}

	/**
	 * 
	 * @param fieldLabel
	 * @param locator
	 * @param dataType
	 * @param value
	 * @author vikram.nadella
	 */
	private static void enterData(String fieldLabel, Locator locator, String dataType, String value) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.debug("Data Type: " + dataType);
		logger.debug("Value: " + value);
		logger.debug("Field Label: " + fieldLabel);
		logger.debug("Locator: " + locator.toString());

		switch (dataType.trim().toLowerCase()) {
		case "text box":
			WebActions.enterText(locator, value, fieldLabel);
			break;
		case "button":
			WebActions.click(locator, fieldLabel);
			break;
		case "lookup field":

			break;
		case "lookup field value":

			break;
		case "dropdown":

			break;
		case "dropdown value":

			break;
		case "check box":

			break;
		default:
			GenericAssertions.fail("Generic Selector for Given Attribute Type is not Available");
			break;
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Returns Locator for Given Field Label based on Data Type and Custom Xpath
	 * 
	 * @param fieldLabel
	 * @param dataType
	 * @param customXpath
	 * @return
	 * @author vikram.nadella
	 */
	private static Locator getFieldLocator(String fieldLabel, String dataType, JsonNode customXpath) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		String xpath = "";

		if (customXpath == null) {
//			return PageFactory.getPage().locator(getCommanXpath(fieldLabel, dataType));
			xpath = getCommanXpath(dataType);

		} else {
//			return PageFactory.getPage()
//					.locator(customXpath.textValue().replaceAll("<fieldLabel_LowerCase>", fieldLabel.toLowerCase())
//							.replaceAll("<fieldLabel_UpperCase>", fieldLabel.toUpperCase())
//							.replaceAll("<fieldLabel>", fieldLabel));

			xpath = customXpath.textValue();
			logger.debug("Derived Custom Xpath: " + xpath);
		}
		String generatedXpath = xpath.replaceAll("<fieldLabel_LowerCase>", fieldLabel.toLowerCase())
				.replaceAll("<fieldLabel_UpperCase>", fieldLabel.toUpperCase()).replaceAll("<fieldLabel>", fieldLabel);
		logger.debug("Generated Xpath: " + generatedXpath);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return PageFactory.getPage().locator(generatedXpath);
	}

	/**
	 * Returns Common Xpath for Given Data Type and Field Label
	 * 
	 * @param fieldLabel
	 * @param dataType
	 * @return
	 * @author vikram.nadella
	 */
	private static String getCommanXpath(String fieldLabel, String dataType) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		String tempRef = fieldSetdataJsonPath
				.getString("CommonXpaths.findAll{it.DataType=='" + dataType + "'.CommonXpath");
		if (tempRef == null)
			GenericAssertions
					.fail("Common Xpath for Given Data Type " + dataType + " is Not Available in Locator Json");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return tempRef.replaceAll("<fieldLabel_LowerCase>", fieldLabel.toLowerCase())
				.replaceAll("<fieldLabel_UpperCase>", fieldLabel.toUpperCase()).replaceAll("<fieldLabel>", fieldLabel);
	}

	private static String getCommanXpath(String dataType) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		String tempRef = fieldSetdataJsonPath
				.getString("CommonXpaths.findAll{it.DataType=='" + dataType + "'.CommonXpath");
		if (tempRef == null)
			GenericAssertions
					.fail("Common Xpath for Given Data Type " + dataType + " is Not Available in Locator Json");
		logger.debug("Derived Common Xpath: " + tempRef);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return tempRef;
	}

	/**
	 * Fill all Fields in a Page in Sequence
	 * 
	 * @param pageName
	 * @param testDataIndex
	 * @author vikram.nadella
	 */
	public static void enterDataIntoPage(String pageName, int testDataIndex) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		int size = fieldSetdataJsonNode.get(pageName).size();
		for (int i = 0; i < size; i++) {
			String dataType = fieldSetdataJsonNode.get(pageName).get(0).get("DataType").textValue();
			List<String> fieldLabels = Arrays
					.asList(fieldSetdataJsonNode.get(pageName).get(0).get("alias").textValue().split("fs:fs"));
			JsonNode customXpath = fieldSetdataJsonNode.get(pageName).get(0).get("CustomXpath");
			for (String fieldLabel_alias : fieldLabels) {
				enterData(fieldLabel_alias,
						getFieldLocator(getFieldLabelInLanguage(pageName, fieldLabel_alias), dataType, customXpath),
						dataType, getTestData(testDataIndex, pageName, fieldLabel_alias));
			}
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Fill Given Field with Given Data from Test Data File based on Data Type
	 * 
	 * @param pageName
	 * @param fieldLabel
	 * @param testDataIndex
	 * @author vikram.nadella
	 */
	public static void enterDataIntoField(String pageName, String fieldLabel, int testDataIndex) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		int size = fieldSetdataJsonNode.get(pageName).size();
		for (int index = 0; index < size; index++) {
			JsonNode refNode = fieldSetdataJsonNode.get(pageName).get(index);
			if (refNode == null)
				GenericAssertions.fail("Expected PageName '" + pageName + "' is Not Available in Locators Json");

			if (!(refNode.get("alias").textValue().contains("fs" + fieldLabel + "fs")
					|| refNode.get("alias").textValue().contains(fieldLabel + "fs")
					|| refNode.get("alias").textValue().contains("fs" + fieldLabel))) {
				if (index != (size - 1))
					continue;
				else
					GenericAssertions
							.fail("Expected Field Label '" + fieldLabel + "' is Not Available in Locators Json");
			}
			String dataType = refNode.get("DataType").textValue();
			JsonNode customXpath = refNode.get("CustomXpath");
			enterData(fieldLabel, getFieldLocator(getFieldLabelInLanguage(pageName, fieldLabel), dataType, customXpath),
					dataType, getTestData(testDataIndex, pageName, fieldLabel));
			break;
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	public static Locator getLocator(String pageName, String fieldLabel) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		int size = fieldSetdataJsonNode.get(pageName).size();
		Locator loc = null;
		for (int index = 0; index < size; index++) {
			JsonNode refNode = fieldSetdataJsonNode.get(pageName).get(index);
			if (refNode == null)
				GenericAssertions.fail("Expected PageName " + pageName + " is Not Available in Locators Json");

			if (!refNode.get("alias").textValue().contains("fs" + fieldLabel + "fs")) {
				continue;
			}
			String dataType = refNode.get("DataType").textValue();
			JsonNode customXpath = refNode.get("CustomXpath");
			loc = getFieldLocator(getFieldLabelInLanguage(pageName, fieldLabel), dataType, customXpath);
		}
		if (loc == null) {
			GenericAssertions.fail("Unable to Parse Locator or Locator not Found");
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return loc;
	}
}
