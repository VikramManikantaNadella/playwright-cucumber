package com.v_tech.actions;

import org.apache.logging.log4j.Logger;

import com.v_tech.base.PageFactory;
import com.v_tech.listeners.CustomLogger;

public class KeyBoardActions {
	private static final Logger logger = new CustomLogger(KeyBoardActions.class);

	/**
	 * Key Down and Hold the Given Key
	 * 
	 * {@code F1} - {@code F12}, {@code Digit0}- {@code Digit9}, {@code KeyA}-
	 * {@code KeyZ}, {@code Backquote}, {@code Minus}, {@code Equal},
	 * {@code Backslash}, {@code Backspace}, {@code Tab}, {@code Delete},
	 * {@code Escape}, {@code ArrowDown}, {@code End}, {@code Enter}, {@code Home},
	 * {@code Insert}, {@code PageDown}, {@code PageUp}, {@code ArrowRight},
	 * {@code ArrowUp}
	 * 
	 * @param key
	 * @author vikram.nadella
	 */
	public static void keyDown(String key) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().keyboard().down(key);
		logger.info("Key Down Action Performed for Key " + key);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Key Up the Given Key
	 * 
	 * {@code F1} - {@code F12}, {@code Digit0}- {@code Digit9}, {@code KeyA}-
	 * {@code KeyZ}, {@code Backquote}, {@code Minus}, {@code Equal},
	 * {@code Backslash}, {@code Backspace}, {@code Tab}, {@code Delete},
	 * {@code Escape}, {@code ArrowDown}, {@code End}, {@code Enter}, {@code Home},
	 * {@code Insert}, {@code PageDown}, {@code PageUp}, {@code ArrowRight},
	 * {@code ArrowUp}
	 * 
	 * @param key
	 * @author vikram.nadella
	 */
	public static void keyUp(String key) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().keyboard().up(key);
		logger.info("Key Up Action Performed for Key " + key);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Key Down and Key Up from Keyboard Keys
	 * 
	 * {@code F1} - {@code F12}, {@code Digit0}- {@code Digit9}, {@code KeyA}-
	 * {@code KeyZ}, {@code Backquote}, {@code Minus}, {@code Equal},
	 * {@code Backslash}, {@code Backspace}, {@code Tab}, {@code Delete},
	 * {@code Escape}, {@code ArrowDown}, {@code End}, {@code Enter}, {@code Home},
	 * {@code Insert}, {@code PageDown}, {@code PageUp}, {@code ArrowRight},
	 * {@code ArrowUp}
	 * 
	 * @param key
	 * @author vikram.nadella
	 */
	public static void pressKey(String key) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().keyboard().down(key);
		PageFactory.getPage().keyboard().up(key);
		logger.info("Click Action Performed for Key " + key);
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * Typing Given Text from Keyboard Keys
	 * 
	 * @param text
	 * @author vikram.nadella
	 */
	public static void type(String text) {
		logger.debug("Entered " + Thread.currentThread().getStackTrace()[1].getMethodName());
		PageFactory.getPage().keyboard().type(text);
		logger.info("Typing '" + text + "' Using Keyboard");
		logger.debug("Exited " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}
}
