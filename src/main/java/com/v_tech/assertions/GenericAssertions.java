package com.v_tech.assertions;

import org.apache.logging.log4j.Logger;

import com.microsoft.playwright.Page;
import com.v_tech.base.PageFactory;
import com.v_tech.listeners.CustomLogger;

public class GenericAssertions {
	private static final Logger logger = new CustomLogger(GenericAssertions.class);
	static Page page = PageFactory.getPage();

	/**
	 * Validates if Given Condition is True
	 * 
	 * @param condition
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertTrue(boolean condition, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (!condition) {
			fail(failMessage);
		}
		assertionPassed(passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validates if Given Condition is True
	 * 
	 * @param condition
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertTrue(boolean condition, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (!condition) {
			fail(null);
		}
		assertionPassed(passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Given Condition is False
	 * 
	 * @param condition
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertFalse(boolean condition, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (condition) {
			fail(failMessage);
		}
		assertionPassed(passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Given Condition is False
	 * 
	 * @param condition
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertFalse(boolean condition, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (condition) {
			fail(null);
		}
		assertionPassed(passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Fails Test Case by Throwing Assertion Error
	 * 
	 * @param message
	 * @author vikram.nadella
	 */
	public static void fail(String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (message == null) {
			throw new AssertionError();
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		throw new AssertionError((Object) message);
	}

	/**
	 * Fails Test Case by Throwing Assertion Error
	 * 
	 * @author vikram.nadella
	 */
	public static void fail() {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		fail(null);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(Object expected, Object actual, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (expected == null && actual == null) {
			failNotEquals(expected, actual, failMessage);
			return;
		}
		if (expected != null && expected.equals(actual)) {
			assertionPassed(passMessage);
			return;
		}
		failNotEquals(expected, actual, failMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(String expected, String actual, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (expected == null && actual == null) {
			assertionPassed(passMessage);
			logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
			return;
		}
		if (expected == null ^ actual == null) {
			failNotEquals(actual, expected, failMessage);
			logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		if (expected.equals(actual) && actual.equals(expected)) {
			assertionPassed(passMessage);
			logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
			return;
		}
		failNotEquals(actual, expected, failMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(String expected, String actual, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, actual, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertNotEquals(String expected, String actual, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertFalse(expected.equals(actual), failMessage, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param delta
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(double expected, double actual, double delta, String failMessage,
			String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (Double.compare(expected, actual) == 0) {
			assertionPassed(passMessage);
			logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
			return;
		}
		if (Math.abs(expected - actual) > delta) {
			failNotEquals(expected, actual, failMessage);
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param delta
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(double expected, double actual, double delta, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, actual, delta, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param delta
	 * @param failMessage
	 * @param passMessage Validated if Expected and Actual are Equal
	 */
	public static void assertEquals(float expected, float actual, float delta, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (Float.compare(expected, actual) == 0) {
			assertionPassed(passMessage);
			logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
			return;
		}
		if (Math.abs(expected - actual) > delta) {
			failNotEquals(expected, actual, failMessage);
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param delta
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(float expected, float actual, float delta, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, actual, delta, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(long expected, long actual, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, (Object) actual, failMessage, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param passMessage
	 * @param expected
	 * @param actual
	 * @author vikram.nadella
	 */
	public static void assertEquals(String passMessage, long expected, long actual) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, actual, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(boolean expected, boolean actual, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals((Object) expected, (Object) actual, failMessage, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(boolean expected, boolean actual, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, actual, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(byte expected, byte actual, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, (Object) actual, failMessage, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(byte expected, byte actual, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, actual, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(char expected, char actual, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, (Object) actual, failMessage, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(char expected, char actual, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, actual, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(short expected, short actual, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, (Object) actual, failMessage, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(short expected, short actual, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, actual, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(int expected, int actual, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, (Object) actual, failMessage, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validated if Expected and Actual are Equal
	 * 
	 * @param expected
	 * @param actual
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertEquals(int expected, int actual, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertEquals(expected, actual, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validates if Given Object is Not Null
	 * 
	 * @param object
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertNotNull(Object object, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertNotNull(object, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validates if Given Object is Not Null
	 * 
	 * @param object
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertNotNull(Object object, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertTrue(object != null, failMessage, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validates if Given Object is Not Null
	 * 
	 * @param object
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertNull(Object object, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (object != null) {
			assertNull("Expected: <null> but was: " + object.toString(), passMessage, object);
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validates if Given Object is Not Null
	 * 
	 * @param failMessage
	 * @param passMessage
	 * @param object
	 * @author vikram.nadella
	 */
	public static void assertNull(String failMessage, String passMessage, Object object) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertTrue(object == null, failMessage, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validates if Given Expected and Actual are Same
	 * 
	 * @param expected
	 * @param actual
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertSame(Object expected, Object actual, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (expected == actual) {
			assertionPassed(passMessage);
			logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
			return;
		}
		failNotSame(expected, actual, failMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validates if Given Expected and Actual are Same
	 * 
	 * @param expected
	 * @param actual
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertSame(Object expected, Object actual, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertSame(expected, actual, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validates if Given Expected and Actual are Not Same
	 * 
	 * @param expected
	 * @param actual
	 * @param failMessage
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertNotSame(Object expected, Object actual, String failMessage, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (expected == actual) {
			failSame(failMessage);
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Validates if Given Expected and Actual are Not Same
	 * 
	 * @param expected
	 * @param actual
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertNotSame(Object expected, Object actual, String passMessage) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertNotSame(expected, actual, null, passMessage);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * 
	 * @param message
	 * @author vikram.nadella
	 */
	public static void failSame(String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		String formatted = (message != null) ? (message + " ") : "";
		fail(formatted + "expected not same");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * 
	 * @param expected
	 * @param actual
	 * @param message
	 * @author vikram.nadella
	 */
	public static void failNotSame(Object expected, Object actual, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		String formatted = (message != null) ? (message + " ") : "";
		fail(formatted + "expected same:<" + expected + "> was not:<" + actual + ">");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * 
	 * @param expected
	 * @param actual
	 * @param message
	 * @author vikram.nadella
	 */
	public static void failNotEquals(Object expected, Object actual, String message) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		fail(format(message, expected, actual));
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * 
	 * @param message
	 * @param expected
	 * @param actual
	 * @return
	 * @author vikram.nadella
	 */
	public static String format(String message, Object expected, Object actual) {
		String formatted = "";
		if (message != null && message.length() > 0) {
			formatted = message + " ";
		}
		return formatted + "expected: \"" + expected + "\" but was: \"" + actual + "\"";
	}

	/**
	 * 
	 * @param passMessage
	 * @author vikram.nadella
	 */
	public static void assertionPassed(String passMessage) {
		logger.info("Assertion Successful (" + passMessage + ")");
	}
}
