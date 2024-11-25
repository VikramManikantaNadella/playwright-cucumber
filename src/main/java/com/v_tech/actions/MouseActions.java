package com.v_tech.actions;

import org.apache.logging.log4j.Logger;

import com.microsoft.playwright.Locator;
import com.v_tech.base.PageFactory;
import com.v_tech.listeners.CustomLogger;
import com.v_tech.waits.Waits;

public class MouseActions {
	private static final Logger logger = new CustomLogger(MouseActions.class);

	/**
	 * Click at Given X and Y Co-Ordinates
	 * 
	 * @param x_coordinate
	 * @param y_coordinate
	 * @param message
	 * @author vikram.nadella
	 */
	public static void click(double x_coordinate, double y_coordinate, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().mouse().click(x_coordinate, y_coordinate);
		logger.info("Clicked on \"" + message + "\" with 'X' Co-Ordinate: " + x_coordinate + " and 'Y' Co-Ordinate: "
				+ y_coordinate);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Double Click at Given X and Y Co-Ordinates
	 * 
	 * @param x_coordinate
	 * @param y_coordinate
	 * @param message
	 * @author vikram.nadella
	 */
	public static void doubleClick(double x_coordinate, double y_coordinate, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().mouse().dblclick(x_coordinate, y_coordinate);
		logger.info("Double Clicked on \"" + message + "\" with 'X' Co-Ordinate: " + x_coordinate
				+ " and 'Y' Co-Ordinate: " + y_coordinate);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Move Mouse to Given X and Y Co-Ordinates
	 * 
	 * @param x_coordinate
	 * @param y_coordinate
	 * @param message
	 * @author vikram.nadella
	 */
	public static void hover(double x_coordinate, double y_coordinate, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().mouse().move(x_coordinate, y_coordinate);
		logger.info("Mouse Moved to \"" + message + "\" with 'X' Co-Ordinate: " + x_coordinate
				+ " and 'Y' Co-Ordinate: " + y_coordinate);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Scroll Page with Given X and Y Co-Ordinates
	 * 
	 * @param x_coordinate
	 * @param y_coordinate
	 * @author vikram.nadella
	 */
	public static void scroll(double x_coordinate, double y_coordinate) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().mouse().wheel(x_coordinate, y_coordinate);
		logger.info("Scrolling Page with 'X' Co-Ordinate: " + x_coordinate + " and 'Y' Co-Ordinate: " + y_coordinate);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Scroll to Top of Page
	 * 
	 * @author vikram.nadella
	 */
	public static void scrollToTop() {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		KeyBoardActions.pressKey("Home");
		Waits.waitForGivenTime(1000);
		logger.info("Scrolled to Top of the Page");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Scroll to Bottom of Page
	 * 
	 * @author vikram.nadella
	 */
	public static void scrollToBottom() {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		KeyBoardActions.pressKey("End");
		Waits.waitForGivenTime(1000);
		logger.info("Scrolled to Bottom of the Page");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Scroll into View of Given Locator
	 * 
	 * @author vikram.nadella
	 */
	public static void scrollIntoView(Locator loc) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		loc.scrollIntoViewIfNeeded();
		logger.info("Scrolled Page to View Element with Locator '" + loc.toString() + "'");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	public static void hover(String selector) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().hover(selector);
		logger.info("Scrolled Page to View Element with Locator '" + selector + "'");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}
}
