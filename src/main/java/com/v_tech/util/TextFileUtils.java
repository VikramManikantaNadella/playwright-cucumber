package com.v_tech.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextFileUtils {
	private static final Logger logger = LogManager.getLogger(TextFileUtils.class);

	/**
	 * Read Data from File and Return as String
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @author vikram.nadella
	 */
	public static String readFileAsString(String filePath) throws IOException {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			return Files.readString(Path.of(filePath));
		} catch (IOException e) {
			throw new IOException("File Not Found at Given Path: " + filePath);
		}
	}

	/**
	 * Read Data from File and Return as String Buffer
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @author vikram.nadella
	 */
	public static StringBuffer readFileAsStringBuffer(String filePath) throws IOException {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			return new StringBuffer(Files.readString(Path.of(filePath)));
		} catch (IOException e) {
			throw new IOException("File Not Found at Given Path: " + filePath);
		}
	}

	/**
	 * Read Data from File and Return as List of Strings
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @author vikram.nadella
	 */
	public static List<String> readFileAsList(String filePath) throws IOException {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			return Arrays.asList(Files.readString(Path.of(filePath)).split("\n"));
		} catch (IOException e) {
			throw new IOException("File Not Found at Given Path: " + filePath);
		}
	}

	/**
	 * Clears Given File
	 * 
	 * @param filePath
	 * @throws IOException
	 * @author vikram.nadella
	 */
	public static void clearFile(String filePath) throws IOException {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			Files.writeString(Path.of(filePath), "", StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new IOException("File Not Found at Given Path: " + filePath);
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public static void writeToFile(String filePath) throws IOException {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			Files.writeString(Path.of(filePath), "", StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new IOException("File Not Found at Given Path: " + filePath);
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	public static void appendToFile(String filePath, String appendText) throws IOException {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		StringBuffer sbf = new StringBuffer(Files.readString(Path.of(filePath)));
		try {
			Files.writeString(Path.of(filePath), sbf + appendText, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new IOException("File Not Found at Given Path: " + filePath);
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	public static void appendToFileInNewLine(String filePath, String appendText) throws IOException {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		StringBuffer sbf = new StringBuffer(Files.readString(Path.of(filePath)));
		try {
			Files.writeString(Path.of(filePath), sbf + "\n" + appendText, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new IOException("File Not Found at Given Path: " + filePath);
		}
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

}
