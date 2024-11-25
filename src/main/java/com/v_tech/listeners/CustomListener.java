package com.v_tech.listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.aventstack.extentreports.service.ExtentService;
import com.microsoft.playwright.Page;
import com.v_tech.base.BaseTest;
import com.v_tech.base.PageFactory;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestSourceRead;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import tech.grasshopper.pdf.extent.ExtentPDFCucumberReporter;

public class CustomListener implements ConcurrentEventListener {

	private Logger logger = LogManager.getLogger(CustomListener.class);
	private ExtentSparkReporter spark;
	private ExtentPDFCucumberReporter pdfReport;
	private ExtentReports extent;

	Map<String, ExtentTest> feature = new HashMap<String, ExtentTest>();
	ThreadLocal<ExtentTest> scenario = new ThreadLocal<ExtentTest>();

	public volatile static ThreadLocal<ExtentTest> step = new ThreadLocal<ExtentTest>();

	public void set(final ExtentTest test) {
		CustomListener.step.set(test);
	}

	public ExtentTest get() {
		return CustomListener.step.get();
	}

	/**
	 * 
	 * 
	 * @param args
	 */
	public CustomListener(String args) {
		String timeStamp = BaseTest.getDateAndTimeStamp();
		this.spark = new ExtentSparkReporter(BaseTest.ReportsPath + "FullReports" + File.separator
				+ System.getProperty("env", "Test") + "_Automation-HTML-Report_" + timeStamp + ".html");
		this.pdfReport = new ExtentPDFCucumberReporter(new File(
				BaseTest.ReportsPath + "PDF-Reports" + File.separator + System.getProperty("env", "Test")
						+ "_Automation-PDF-Report_" + timeStamp + ".pdf"),
				"");
		this.extent = new ExtentReports();
		ExtentService.getInstance();
	}

	@Override
	public void setEventPublisher(EventPublisher publisher) {
		publisher.registerHandlerFor(TestRunStarted.class, this::runStarted);
		publisher.registerHandlerFor(TestRunFinished.class, this::runFinished);
		publisher.registerHandlerFor(TestSourceRead.class, this::featureRead);
		publisher.registerHandlerFor(TestCaseStarted.class, this::scenarioStarted);
		publisher.registerHandlerFor(TestCaseFinished.class, this::scenarioFinished);
		publisher.registerHandlerFor(TestStepStarted.class, this::stepStarted);
		publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
	}

	private void runStarted(TestRunStarted event) {
		logger.info("---------TestRunStarted--------------------");
		this.spark.config().setTheme(Theme.DARK);
		this.spark.config().setDocumentTitle(System.getProperty("appName", "Automation") + " Test Report");
		this.spark.config().setReportName(System.getProperty("appName", "Automation") + " Test Report");
		this.spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		this.spark.config().thumbnailForBase64(true);
		this.spark.viewConfigurer().viewOrder()
				.as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY }).apply();
		extent.attachReporter(spark);
		extent.attachReporter(pdfReport);
	}

	private void runFinished(TestRunFinished event) {
		logger.info("---------runFinished--------------------");
		extent.flush();
	}

	private void featureRead(TestSourceRead event) {
		logger.info("---------featureRead--------------------");
	}

	// private void scenarioStarted(TestCaseStarted event) {
	// logger.info("---------ScenarioStarted--------------------");
	// String featureSource = event.getTestCase().getUri().toString();
	// String featureName = featureSource.split(".*/")[1].replace(".feature", "");
	// System.out.println("In Secanrio Started: " + featureName);

	// if (feature.get(featureSource) == null)
	// feature.putIfAbsent(featureSource, extent.createTest(featureName));

	// scenario.set(feature.get(featureSource).createNode(event.getTestCase().getName()));
	// for (String tag : event.getTestCase().getTags())
	// scenario.get().assignCategory(tag.replace("@", ""));
	// logger.info(featureName);
	// }

	private void scenarioStarted(TestCaseStarted event) {
		logger.info("---------ScenarioStarted--------------------");
		String featureSource = event.getTestCase().getUri().toString();
		String scenarioName = event.getTestCase().getName();
		System.out.println("In Secanrio Started: " + scenarioName);

		if (feature.get(featureSource) == null)
			scenario.set(extent.createTest(scenarioName));
		scenario.get().assignCategory(event.getTestCase().getName());
	}

	private void scenarioFinished(TestCaseFinished event) {
		logger.info("---------ScenarioFinished--------------------");
	}

	private void stepStarted(TestStepStarted event) {
		logger.info("---------stepStarted--------------------");
		String stepName = " ";
		String keyword = "Triggered the hook :";

		if (event.getTestStep() instanceof PickleStepTestStep) {
			PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
			stepName = steps.getStep().getText();
			keyword = steps.getStep().getKeyword();
			CustomListener.step.set(scenario.get().createNode(Given.class, keyword + " " + stepName));
		}
	}

	private void stepFinished(TestStepFinished event) {
		logger.info("---------stepFinished--------------------");
		Result result = event.getResult();
		if (event.getTestStep() instanceof PickleStepTestStep) {
			if (event.getResult().getStatus().toString() == "PASSED") {
				CustomListener.step.get().log(Status.PASS, "Step Execution Status --> Passed");
			} else if (event.getResult().getStatus().toString() == "SKIPPED") {
				CustomListener.step.get().log(Status.WARNING, result.getError());
				CustomListener.step.get().log(Status.SKIP, "Step Execution Status --> Skipped ");
			} else {
				CustomListener.step.get().log(Status.FAIL, "" + result.getError());
				CustomListener.step.get().log(Status.FAIL, "Step Execution Status --> Failed");
				CustomListener.step.get().addScreenCaptureFromBase64String(getScreenshotinBase64());
			}
		}
	}

	public static void stepLog(Status status, String message) {
		CustomListener.step.get().log(status, message);
	}

	public static String getScreenshot() {
		String path = System.getProperty("user.dir") + File.separator + "Reports" + File.separator + "Screenshots"
				+ File.separator + "screenshot_" + BaseTest.getDateAndTimeStamp() + ".png";
		PageFactory.getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		return path;
	}

	public static String getScreenshotinBase64() {
		final File scrFile = new File(getScreenshot());
		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try {
			fileInputStreamReader = new FileInputStream(scrFile);
			final byte[] bytes = new byte[(int) scrFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return "data:image/png;base64," + encodedBase64;
	}
}
