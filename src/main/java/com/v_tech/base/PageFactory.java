package com.v_tech.base;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.RecordVideoSize;
import com.v_tech.util.PropertiesUtils;

public class PageFactory {
	private static final Logger logger;

	public static final String ReportsPath = System.getProperty("user.dir") + File.separator + "Reports"
			+ File.separator;

	static ThreadLocal<Playwright> playwright = new ThreadLocal<Playwright>();
	static ThreadLocal<BrowserContext> context = new ThreadLocal<BrowserContext>();
	static ThreadLocal<Page> page = new ThreadLocal<Page>();
	static ThreadLocal<Browser> browser = new ThreadLocal<Browser>();

	static String headless = System.getProperty("headless", "false");
	static String browserName = System.getProperty("browserName", "chrome");

	/**
	 * Initializing Playwright Instance | Initializing Browser Instance |
	 * Initializing BrowserContext Instance | Initializing Page Instance |
	 * 
	 * @author kiran.thaduri
	 */
	public static void initializeBrowser() {
		Page page = null;
		BrowserContext context = null;
		Browser browser = null;
		Playwright playwright = null;

		playwright = Playwright.create();

		browser = getBrowserType(browserName, playwright);

		boolean recordScreen = Boolean.parseBoolean(PropertiesUtils.getscreenRecord_Tracing());
		if (recordScreen) {
			context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null)
					.setRecordVideoSize(new RecordVideoSize(1536, 746))
					.setRecordVideoDir(Paths.get(ReportsPath + File.separator + "Recordings" + File.separator)));
			context.tracing()
					.start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
		} else {
			context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
		}

		page = context.newPage();
		page.setDefaultTimeout(PropertiesUtils.getElementVisibleTimeout());
		PageFactory.page.set(page);
		PageFactory.browser.set(browser);
		PageFactory.context.set(context);
		PageFactory.playwright.set(playwright);

		logger.info("Context Set Successful");
		logger.info(browserName + " Browser Initialization Successful");
	}

	/**
	 * Returns Browser Instance from Given Browser Name
	 * 
	 * @param browserName
	 * @param playwright
	 * @return
	 * @author vikram.nadella
	 */
	private static Browser getBrowserType(String browserName, Playwright playwright) {
		Browser browser = null;
		switch (browserName.toLowerCase()) {
		case "chrome":
			browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions()
							.setArgs(new ArrayList<String>(Arrays.asList("--start-maximized")))
							.setSlowMo(PropertiesUtils.getSlowMoTime()).setHeadless(Boolean.parseBoolean(headless))
							.setChannel(browserName));
			break;
		case "chromium":
			browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions()
							.setArgs(new ArrayList<String>(Arrays.asList("--start-maximized")))
							.setSlowMo(PropertiesUtils.getSlowMoTime()).setHeadless(Boolean.parseBoolean(headless)));
			break;
		case "msedge":
			browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions()
							.setArgs(new ArrayList<String>(Arrays.asList("--start-maximized")))
							.setSlowMo(PropertiesUtils.getSlowMoTime()).setHeadless(Boolean.parseBoolean(headless))
							.setChannel(browserName));
			break;
		case "firefox":
			browser = playwright.firefox()
					.launch(new BrowserType.LaunchOptions()
							.setArgs(new ArrayList<String>(Arrays.asList("--start-maximized")))
							.setSlowMo(PropertiesUtils.getSlowMoTime()).setHeadless(Boolean.parseBoolean(headless)));
			break;
		case "webkit":
			browser = playwright.webkit()
					.launch(new BrowserType.LaunchOptions()
							.setArgs(new ArrayList<String>(Arrays.asList("--start-maximized")))
							.setSlowMo(PropertiesUtils.getSlowMoTime()).setHeadless(Boolean.parseBoolean(headless)));
			break;

		default:
			browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions()
							.setArgs(new ArrayList<String>(Arrays.asList("--start-maximized")))
							.setSlowMo(PropertiesUtils.getSlowMoTime()).setHeadless(Boolean.parseBoolean(headless))
							.setChannel(browserName));
			break;
		}
		return browser;
	}

	/**
	 * Returns Current Page Instance
	 * 
	 * @return
	 * @author kiran.thaduri
	 */
	public static Page getPage() {
		return page.get();
	}

	/**
	 * Returns Current BrowserContext Instance
	 * 
	 * @return
	 * @author kiran.thaduri
	 */
	public static BrowserContext getContext() {
		return context.get();
	}

	static {
		logger = LogManager.getLogger(BaseTest.class);
	}

}
