package com.v_tech.waits;

import java.util.function.BooleanSupplier;

import org.apache.logging.log4j.Logger;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page.WaitForConditionOptions;
import com.microsoft.playwright.Page.WaitForLoadStateOptions;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.LoadState;
import com.v_tech.actions.BrowserActions;
import com.v_tech.actions.MouseActions;
import com.v_tech.base.PageFactory;
import com.v_tech.listeners.CustomLogger;
import com.v_tech.util.PropertiesUtils;

public class Waits {

	private static final Logger logger = new CustomLogger(Waits.class);
	private static double defaultTimeOut = PropertiesUtils.getElementVisibleTimeout();

	/**
	 * Waits for Given Time
	 * 
	 * @param waitTimeMilliSeconds
	 * @author vikram.nadella
	 */
	public static void waitForGivenTime(double waitTimeMilliSeconds) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("Wait for Given Time: " + (int) waitTimeMilliSeconds + " MilliSeconds");
		PageFactory.getPage().waitForTimeout(waitTimeMilliSeconds);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Waits for Page Load to Complete
	 * 
	 * @author vikram.nadella
	 */
	public static void waitForPageLoad() {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("Wait for Page Load, Navigating to URL: " + PageFactory.getPage().url());
		try {
			Thread.sleep(5000);
			PageFactory.getPage().waitForLoadState(LoadState.LOAD,
					new WaitForLoadStateOptions().setTimeout(PropertiesUtils.getPageLoadTimeout()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (PlaywrightException plExp) {
			BrowserActions.reload();
			PageFactory.getPage().waitForLoadState(LoadState.LOAD,
					new WaitForLoadStateOptions().setTimeout(PropertiesUtils.getPageLoadTimeout()));
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Lazy Load Modules, Scrolls Page to Bottom and Top
	 * 
	 * @author vikram.nadella
	 */
	public static void loadLazyloadModules() {
		PageFactory.getPage().mouse().wheel(0, 200);
		PageFactory.getPage().mouse().wheel(0, 200);
		PageFactory.getPage().mouse().wheel(0, 200);
		PageFactory.getPage().mouse().wheel(0, 200);
		PageFactory.getPage().mouse().wheel(0, 200);
		PageFactory.getPage().mouse().wheel(0, 200);
		PageFactory.getPage().mouse().wheel(0, 200);
		PageFactory.getPage().mouse().wheel(0, 200);
		MouseActions.scrollToTop();
	}

	/**
	 * Waits for Given Locator to be Visible
	 * 
	 * @param locator
	 * @param element
	 * @author vikram.nadella
	 */
	public static void waitUntilElementIsVisible(Locator locator, String element) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("Waiting for \"" + element + "\" to be Visibile, Locator: " + locator.toString());
		String timeInSec = String.valueOf(defaultTimeOut / 1000);
		try {
			PageFactory.getPage().waitForCondition(new BooleanSupplier() {
				public boolean getAsBoolean() {
					return locator.isVisible();
				}
			}, new WaitForConditionOptions().setTimeout(defaultTimeOut));
		} catch (TimeoutError e) {
			throw new TimeoutError("Element \"" + element + "\" is not visible on the page after waiting for "
					+ timeInSec + " Seconds");
		} catch (IllegalStateException plExp) {
			BrowserActions.reload();
			Waits.waitForPageLoad();
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Optional Waits for Given Locator to be Visible
	 * 
	 * @param locator
	 * @param timeInSeconds
	 * @param element
	 * @author vikram.nadella
	 */
	public static void optionalWaitUntilElementIsVisible(Locator locator, int timeInSeconds, String element) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("Waiting " + timeInSeconds + "s for \"" + element + "\" to be Visibile, Locator: "
				+ locator.toString());
		try {
			PageFactory.getPage().waitForCondition(new BooleanSupplier() {
				public boolean getAsBoolean() {
					return locator.isVisible();
				}
			}, new WaitForConditionOptions().setTimeout(timeInSeconds * 1000));
		} catch (Exception e) {
			logger.info("Exception Caught, Optional Element Not Visible");
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

}
