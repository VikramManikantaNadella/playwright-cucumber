package com.v_tech.actions;

import org.apache.logging.log4j.Logger;

import com.v_tech.assertions.GenericAssertions;
import com.v_tech.base.PageFactory;
import com.v_tech.listeners.CustomLogger;
import com.v_tech.waits.Waits;

public class BrowserActions {

	private static final Logger logger = new CustomLogger(BrowserActions.class);

	/**
	 * Navigates to URL
	 * 
	 * @param URL
	 * @author vikram.nadella
	 */
	public static void navigateTo(String URL) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("Navigating To, URL: \"" + URL + "\"");
		logger.debug("Status Code: " + PageFactory.getPage().navigate(URL).status());
		if (PageFactory.getPage().navigate(URL).status() != 200)
			GenericAssertions.fail("Page Navigation is Not Successful");
		logger.info("Page Title: " + PageFactory.getPage().title());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Navigates Back
	 * 
	 * @author vikram.nadella
	 */
	public static void navigateBack() {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		String currentURL = getCurrentURL();
		PageFactory.getPage().goBack();
		Waits.waitForGivenTime(2000);
		if (getCurrentURL().equals(currentURL))
			GenericAssertions.fail("Page Navigation is Not Successful");
		logger.info("Navigating Back, URL: " + PageFactory.getPage().url());
		logger.info("Page Title: " + PageFactory.getPage().title());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Navigates Forward
	 * 
	 * @author vikram.nadella
	 */
	public static void navigateForward() {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		String currentURL = getCurrentURL();
		PageFactory.getPage().goForward();
		Waits.waitForGivenTime(2000);
		if (getCurrentURL().equals(currentURL))
			GenericAssertions.fail("Page Navigation is Not Successful");
		logger.info("Navigating Forward, URL: " + PageFactory.getPage().url());
		logger.info("Page Title: " + PageFactory.getPage().title());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Refreshes the Page
	 * 
	 * @author vikram.nadella
	 */
	public static void reload() {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("Reloading Page, URL: " + PageFactory.getPage().url());
		if (PageFactory.getPage().reload().status() != 200)
			GenericAssertions.fail("Page Navigation is Not Successful");
		logger.info("Page Title: " + PageFactory.getPage().title());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Returns Current URL
	 * 
	 * @return
	 * @author vikram.nadella
	 */
	public static String getCurrentURL() {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("Current URL: " + PageFactory.getPage().url());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return PageFactory.getPage().url();
	}
}
