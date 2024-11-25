package com.v_tech.assertions;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

public class SoftAssertions extends Assertions {
	/**
	 * When an assertion fails, don't throw an exception but record the failure.
	 * Calling {@code assertAll()} will cause an exception to be thrown if at least
	 * one assertion failed.
	 */
	static Logger logger = LogManager.getLogger(SoftAssertions.class.getName());

	/**
	 * LinkedHashMap to Preserve the Order
	 */
	private final Map<AssertionError, IAssert<?>> m_errors = Maps.newLinkedHashMap();

	@Override
	protected void doAssert(IAssert<?> a) {
		onBeforeAssert(a);
		try {
			a.doAssert();
			onAssertSuccess(a);
		} catch (AssertionError ex) {
			onAssertFailure(a, ex);
			m_errors.put(ex, a);
		} finally {
			onAfterAssert(a);
		}
	}

	/**
	 * Asserts all Soft Assertions
	 */
	public void assertAll() {
		if (!m_errors.isEmpty()) {
			StringBuilder sb = new StringBuilder("The following asserts failed:");
			boolean first = true;
			for (Map.Entry<AssertionError, IAssert<?>> ae : m_errors.entrySet()) {
				if (first) {
					first = false;
				} else {
					sb.append(",");
				}
				sb.append("\n\t");
				sb.append(ae.getKey().getMessage());
			}
			logger.info(sb.toString());
			throw new AssertionError(sb.toString());
		}
	}

}
