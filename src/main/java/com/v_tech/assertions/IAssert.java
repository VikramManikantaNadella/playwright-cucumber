package com.v_tech.assertions;

public interface IAssert<T> {
	String getpassMessage();

	String getfailMessage();

	void doAssert();

	T getActual();

	T getExpected();
}
