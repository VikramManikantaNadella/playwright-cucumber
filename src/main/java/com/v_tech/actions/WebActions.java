package com.v_tech.actions;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.v_tech.assertions.GenericAssertions;
import com.v_tech.base.PageFactory;
import com.v_tech.listeners.CustomLogger;
import com.v_tech.util.LocatorUtils;
import com.v_tech.waits.Waits;

public class WebActions {
	private static final Logger logger = new CustomLogger(WebActions.class);

	/**
	 * Returns Text Contents of Given Locator
	 * 
	 * @param locator
	 * @return
	 * @author vikram.nadella
	 */
	public static String getText(Locator locator) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("Locator: \"" + locator.toString() + "\" contains Text: \"" + locator.first().textContent() + "\"");
		try {
			return locator.textContent();
		} catch (Exception expException) {
			GenericAssertions.fail(expException.getLocalizedMessage());
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return null;
	}

	/**
	 * Returns Text Contents of Given Selector from Given Page
	 * 
	 * @param page
	 * @param selector
	 * @return
	 * @author vikram.nadella
	 */
	public static String getText(Page page, String selector) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("Locator: \"" + selector + "\" contains Text: \"" + page.locator(selector).textContent() + "\"");
		try {
			return page.locator(selector).textContent();
		} catch (Exception expException) {
			GenericAssertions.fail(expException.getLocalizedMessage());
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return null;
	}

	/**
	 * Returns Text Contents of Given Field
	 * 
	 * @param pageName
	 * @param fieldName
	 * @return
	 * @author vikram.nadella
	 */
	public static String getText(String pageName, String fieldName) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Locator loc = LocatorUtils.getLocator(pageName, fieldName);
		logger.info("Locator: \"" + loc.toString() + "\" contains Text: \"" + loc.first().textContent() + "\"");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return loc.textContent();
	}

	/**
	 * Returns Input Value of Given Locator
	 * 
	 * @param locator
	 * @return
	 * @author vikram.nadella
	 */
	public static String getInputValue(Locator locator) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("Locator: \"" + locator.toString() + "\" contains Text: \"" + locator.first().textContent() + "\"");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return locator.inputValue();
	}

	/**
	 * Returns Input Value of Given Selector from Given Page
	 * 
	 * @param page
	 * @param selector
	 * @return
	 * @author vikram.nadella
	 */
	public static String getInputValue(Page page, String selector) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("Locator: \"" + selector + "\" contains Text: \"" + page.locator(selector).inputValue() + "\"");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return page.locator(selector).inputValue();
	}

	/**
	 * Returns Input Value of Given Field
	 * 
	 * @param pageName
	 * @param fieldName
	 * @return
	 */
	public static String getInputValue(String pageName, String fieldName) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Locator locator = LocatorUtils.getLocator(pageName, fieldName);
		logger.info("Locator: \"" + locator.toString() + "\" contains Text: \"" + locator.first().inputValue() + "\"");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return locator.inputValue();
	}

	/**
	 * Enters Given Text into Given Locator
	 * 
	 * @param locator
	 * @param text
	 * @param message
	 * @author vikram.nadella
	 */
	public static void enterText(Locator locator, String text, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		locator.clear();
		String inputMode = locator.getAttribute("inputmode");
		if (inputMode!=null && inputMode.equalsIgnoreCase("decimal")) {
			text = replaceNumericals(text);
		}
		locator.fill(text);
		if (locator.toString().contains("Password") || locator.toString().contains("password")
				|| message.contains("Password") || message.contains("password"))
			logger.info("Text: \"********\" entered into \"" + message + "\" with Locator: " + locator.toString());
		else
			logger.info("Text: \"" + text + "\" entered into \"" + message + "\" with Locator: " + locator.toString());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Enters Given Text into Element with Given Selector(Xpath)
	 * 
	 * @param selector
	 * @param text
	 * @param message
	 * @author vikram.nadella
	 */
	public static void enterText(String selector, String text, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().locator(selector).fill(text);
		if (selector.contains("Password") || selector.contains("password") || message.contains("Password")
				|| message.contains("password"))
			logger.info("Text: \"********\" entered into \"" + message + "\" with Locator: " + selector);
		else
			logger.info("Text: \"" + text + "\" entered into \"" + message + "\" with Locator: " + selector);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Click on Given Locator
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void click(Locator locator, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Waits.waitUntilElementIsVisible(locator, message);
		locator.click();
		GenericAssertions.assertTrue(true,
				"Assertion Successful( \"" + message + "\" is Visible, Locator: " + locator.toString() + "\")");
		logger.info("Clicked on \"" + message + "\" with Locator: " + locator.toString());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Click on Element with Given Selector(Xpath)
	 * 
	 * @param selector
	 * @param message
	 * @author vikram.nadella
	 */
	public static void click(String selector, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().locator(selector).click();
		GenericAssertions.assertTrue(true,
				"Assertion Successful( \"" + message + "\" is Visible, Locator: " + selector.toString() + "\")");
		logger.info("Clicked on \"" + message + "\" with Locator: " + selector);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Select Given Option from Given Locator Dropdown
	 * 
	 * @param locator
	 * @param option
	 * @param message
	 * @author vikram.nadella
	 */
	public static void selectFromDropdown(Locator locator, String option, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		locator.selectOption(option);
		logger.info("Selected Option \"" + option + "\" from \"" + message + "\" Dropdown");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Clear the Input Field
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void clearField(Locator locator, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		locator.clear();
		logger.info("Cleared \"" + message + "\" with Locator: " + locator.toString());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Returns Count of Elements with Same Locator
	 * 
	 * @param locator
	 * @return
	 * @author vikram.nadella
	 */
	public static int getCount(Locator locator) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return locator.count();
	}

	/**
	 * Uploads File from Given File Path
	 * 
	 * @param selector
	 * @param fileName
	 * @param filePath
	 * @author vikram.nadella
	 */
	public static void uploadFile(String selector, String fileName, String filePath) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().setInputFiles(selector, Path.of(filePath + File.separator + fileName));
		// Waits.waitForGivenTime(5000);
		logger.info("Successfully uploaded file");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private static String replaceNumericals(String inputText) {
		String locale = System.getProperty("locale", "english");
		ArrayList<String> replaceValues = new ArrayList<String>();
		switch (locale.trim().toLowerCase()) {
		case "german":
			replaceValues.add(".fs:fs,");
			replaceValues.add(",fs:fs.");
			break;
		case "english":
			replaceValues.add(".fs:fs.");
			replaceValues.add(",fs:fs,");
			break;
		default:
			GenericAssertions.fail("");
			break;
		}
		
		String firstReplaceString = replaceValues.get(0).split("fs:fs")[0];
		String firstToBeReplaceString = replaceValues.get(0).split("fs:fs")[1];
		String secondReplaceString = replaceValues.get(1).split("fs:fs")[0];
		String secondToBeReplaceString = replaceValues.get(1).split("fs:fs")[1];
		
		if(firstReplaceString.trim().equalsIgnoreCase("."))
			firstReplaceString = "\\.";
		
		String[] arr = inputText.split(firstReplaceString);
		String resultString = "";
		for (String st : arr) {
			st = st.replace(secondReplaceString, secondToBeReplaceString);
			System.out.println("ST: " + st);
			resultString = resultString + firstToBeReplaceString + st;
		}
		return resultString.substring(1);
	}
}
