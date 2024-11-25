package com.v_tech.assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.apache.logging.log4j.Logger;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.v_tech.base.PageFactory;
import com.v_tech.listeners.CustomLogger;

public class AssertionLibrary {

	private static final Logger logger;
	static Page page = PageFactory.getPage();

	/**
	 * Assertion if Locator is Visible
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void locatorIsVisible(Locator locator, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(locator).isVisible();
		logger.info("Assertion Successful( \"" + message + "\" is Visible, Locator: " + locator.toString() + "\")");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Assertion if Locator is Checked
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void locatorIsChecked(Locator locator, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(locator).isChecked();
		logger.info("Assertion Successful( \"" + message + "\" is Checked, Locator: " + locator.toString() + "\")");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Assertion if Locator is Disabled
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void locatorIsDisabled(Locator locator, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(locator).isDisabled();
		logger.info("Assertion Successful( \"" + message + "\" is Disabled, Locator: " + locator.toString() + "\")");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Assertion if Locator is Editable
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void locatorIsEditable(Locator locator, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(locator).isEditable();
		logger.info("Assertion Successful( \"" + message + "\" is Editable, Locator: " + locator.toString() + "\")");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Assertion if Locator is Empty
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void locatorIsEmpty(Locator locator, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(locator).isEmpty();
		logger.info("Assertion Successful( \"" + message + "\" is Empty, Locator: " + locator.toString() + "\")");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Assertion if Locator is Enabled
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void locatorIsEnabled(Locator locator, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(locator).isEnabled();
		logger.info("Assertion Successful( \"" + message + "\" is Enabled, Locator: " + locator.toString() + "\")");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Assertion if Locator is in Focus
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void locatorIsFocused(Locator locator, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(locator).isFocused();
		logger.info("Assertion Successful( \"" + message + "\" is Focused, Locator: " + locator.toString() + "\")");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Assertion if Locator is Hidden
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void locatorIsHidden(Locator locator, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(locator).isHidden();
		logger.info("Assertion Successful( \"" + message + "\" is Hidden, Locator: " + locator.toString() + "\")");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Assertion if Locator is in View Port
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void locatorIsInViewPort(Locator locator, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(locator).isInViewport();
		logger.info(
				"Assertion Successful( \"" + message + "\" is In View Port, Locator: \"" + locator.toString() + "\")");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Assertion if Current URL contains Given String
	 * 
	 * @param string
	 * @author vikram.nadella
	 */
	public static void currentURLContains(String string) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(page).hasURL(string);
		logger.info("Assertion Successful( Current URL: \"" + page.url() + "\" contains Expected Text: \"" + string
				+ "\" )");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Assertion if Current URL is equals to the Given String
	 * 
	 * @param string
	 * @author vikram.nadella
	 */
	public static void currentURL(String string) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		GenericAssertions.assertEquals(page.url(), string, "");
		logger.info("Assertion Successful( Current URL: \"" + page.url() + "\" is equals to Expected URL: \"" + string
				+ "\" )");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Assertion if Locator Text contains Given String
	 * 
	 * @param locator
	 * @param string
	 * @param message
	 * @author vikram.nadella
	 */
	public static void locatorTextContains(Locator locator, String string, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		GenericAssertions.assertTrue(locator.first().textContent().contains(string), "");
		logger.info("Assertion Successful( \"" + message + "\" contains Expected Text: \"" + string + "\" )");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());

	}

	/**
	 * Assertion if Locator Text is equals to the Given String
	 * 
	 * @param locator
	 * @param comparingText
	 * @param message
	 * @author vikram.nadella
	 */
	public static void locatorTextEquals(Locator locator, String comparingText, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		GenericAssertions.assertTrue(locator.first().textContent().equals(comparingText), "");
		logger.info("Assertion Successful( \"" + message + "\" contains Expected Text: \"" + comparingText + "\" )");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Verifying Optional Locator
	 * 
	 * @param locator
	 * @param message
	 * @author vikram.nadella
	 */
	public static void verifyOptionalElement(Locator locator, String message) {
		try {
			locatorIsVisible(locator, message);
		} catch (Exception e) {
			logger.info("Exception( \"" + message + "\" is Not Displayed, Locator: " + locator.toString() + "\")");
		}
	}

	static {
		logger = new CustomLogger(AssertionLibrary.class);
	}
}
