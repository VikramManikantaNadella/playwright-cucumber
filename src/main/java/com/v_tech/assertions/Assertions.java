package com.v_tech.assertions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Assertions implements IAssertLifecycle {

	static Logger logger = LogManager.getLogger(Assertions.class.getName());

	protected void doAssert(IAssert<?> assertCommand) {
		
	}

	/**
	 * Run the assert command in parameter. Meant to be overridden by subclasses.
	 */
	@Override
	public void executeAssert(IAssert<?> assertCommand) {
		assertCommand.doAssert();
	}

	/**
	 * Invoked when an assert succeeds. Meant to be overridden by subclasses.
	 */
	@Override
	public void onAssertSuccess(IAssert<?> assertCommand) {
		logger.info("Assertion Successful: " + assertCommand.getpassMessage());

	}

	
	/**
	 * Invoked when Asser Fails
	 */
	@Override
	public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
		logger.warn("Assertion Failed: " + assertCommand.getfailMessage());
	}

	/**
	 * Invoked before an assert is run. Meant to be overridden by subclasses.
	 */
	@Override
	public void onBeforeAssert(IAssert<?> assertCommand) {
	}

	/**
	 * Invoked after an assert is run. Meant to be overridden by subclasses.
	 */
	@Override
	public void onAfterAssert(IAssert<?> assertCommand) {
	}

	abstract private static class SimpleAssert<T> implements IAssert<T> {
		private final T actual;
		private final T expected;
		private final String failMessage;
		private final String passMessage;

		public SimpleAssert(String failMessage, String passMessage) {
			this(null, null, failMessage, passMessage);
		}

		public SimpleAssert(T actual, T expected) {
			this(actual, expected, null, null);
		}

		public SimpleAssert(T actual, T expected, String failMessage) {
			this(actual, expected, failMessage, null);
		}

		public SimpleAssert(T actual, T expected, String failMessage, String passMessage) {
			this.actual = actual;
			this.expected = expected;
			this.failMessage = failMessage;
			this.passMessage = passMessage;
		}

		@Override
		public String getfailMessage() {
			return failMessage;
		}

		@Override
		public T getActual() {
			return actual;
		}

		@Override
		public T getExpected() {
			return expected;
		}

		public String getpassMessage() {
			return passMessage;
		}

		@Override
		abstract public void doAssert();
	}

	/**
	 * Assert True
	 * 
	 * @param condition
	 * @param failMessage
	 * @param passMessage
	 */
	public void assertTrue(final boolean condition, final String failMessage, final String passMessage) {
		enteredMethodLog();
		doAssert(new SimpleAssert<Boolean>(condition, Boolean.TRUE, failMessage, passMessage) {
			@Override
			public void doAssert() {
				GenericAssertions.assertTrue(condition, failMessage, passMessage);
			}
		});
		exitedMethodLog();
	}

	/**
	 * Assert Equals
	 * 
	 * @param <T> expected
	 * @param <T> actual
	 * @param failMessage
	 * @param passMessage
	 */
	public <T> void assertEquals(final T expected, final T actual, final String failMessage, final String passMessage) {
		doAssert(new SimpleAssert<T>(actual, expected, failMessage, passMessage) {
			@Override
			public void doAssert() {
				enteredMethodLog();
				GenericAssertions.assertEquals(expected, actual, failMessage, passMessage);
				exitedMethodLog();
			}
		});
	}

	private static void enteredMethodLog() {
		logger.debug("Framework : Entered " + Thread.currentThread().getStackTrace()[1].getMethodName() + " method");
	}

	private static void exitedMethodLog() {
		logger.debug("Framework : Exited " + Thread.currentThread().getStackTrace()[1].getMethodName() + " method");
	}

}
