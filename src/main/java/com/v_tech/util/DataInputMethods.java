package com.v_tech.util;

import java.util.List;

import com.v_tech.assertions.GenericAssertions;
import com.v_tech.base.BaseTest;

import io.restassured.path.json.JsonPath;

public class DataInputMethods {
	private static final JsonPath testData = BaseTest.testData;

	public static List<Object> getListOfData(String jsonPath) {
		return testData.getList(jsonPath);
	}

	public static String getTestData(String jsonPath) {
		String returnString = testData.getString(jsonPath).replace("[", "").replace("]", "");
		return returnString;
	}

	protected static String getTestData(int testDataIndex, String jsonPathParameter1, String jsonPathParameter2) {
		String returnString = testData
				.getString(
						jsonPathParameter1 + ".findAll{it.TestData_ID=='" + testDataIndex + "'}." + jsonPathParameter2)
				.replace("[", "").replace("]", "");
		if (returnString == null) {
			GenericAssertions.fail("Returning Null Value for Given Json Path: " + jsonPathParameter1
					+ ".findAll{it.TestData_ID=='" + testDataIndex + "'}." + jsonPathParameter2);
		}
		return returnString;
	}

	protected static String getTestData(int testDataIndex, String jsonPathParameter1, String jsonPathParameter2,
			String jsonPathParameter3) {
		String returnString = testData.getString(jsonPathParameter1 + ".findAll{it.TestData_ID=='" + testDataIndex
				+ "'}." + jsonPathParameter2 + "." + jsonPathParameter3).replace("[", "").replace("]", "");
		if (returnString == null) {
			GenericAssertions.fail(
					"Returning Null Value for Given Json Path: " + jsonPathParameter1 + ".findAll{it.TestData_ID=='"
							+ testDataIndex + "'}." + jsonPathParameter2 + "." + jsonPathParameter3);
		}
		return returnString;
	}

	protected static String getTestData(int testDataIndex, String jsonPathParameter1, String jsonPathParameter2,
			String jsonPathParameter3, String jsonPathParameter4) {
		String returnString = testData
				.getString(jsonPathParameter1 + ".findAll{it.TestData_ID=='" + testDataIndex + "'}."
						+ jsonPathParameter2 + "." + jsonPathParameter3 + "." + jsonPathParameter4)
				.replace("[", "").replace("]", "");
		if (returnString == null) {
			GenericAssertions.fail("Returning Null Value for Given Json Path: " + jsonPathParameter1
					+ ".findAll{it.TestData_ID=='" + testDataIndex + "'}." + jsonPathParameter2 + "."
					+ jsonPathParameter3 + "." + jsonPathParameter4);
		}
		return returnString;
	}
}
