package com.rohini.automation.mbile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rohini.automation.mobile.AppUtils;
import com.rohini.automation.mobile.AppiumUtils;
import com.rohini.automation.mobile.CapabilityManager;
import com.rohini.automation.mobile.DateUtil;

import io.appium.java_client.AppiumDriver;

public class DemoTest {
	
	public Log logger = LogFactory.getLog(DemoTest.class);

	public static RemoteWebDriver remoteWebDriver;
	public static AppiumDriver<?> appiumDriver;
	public ChromeDriver chromeDriver;
	
	public static CapabilityManager capabilityManager;

	public static AppiumUtils appiumUtils;
	public static DateUtil dateUtils;
	public static AppUtils appUtils;

	
	@BeforeClass
	public void setUp() throws Exception {
		try {

			capabilityManager = CapabilityManager.getInstance();
			remoteWebDriver = capabilityManager.getRemoteWebDriver();
			appiumDriver = CapabilityManager.getAppiumDriver();
			
			appiumUtils = AppiumUtils.getInstance(remoteWebDriver, appiumDriver);
			appUtils = AppUtils.getInstance();
			dateUtils = DateUtil.getInstance();
			
			appUtils.log_4_j_Configure();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@BeforeMethod
	public void beforeMethod() throws Exception {
		logger.info(" ******** BEFORE METHOD ******** ");
		appUtils.beforeMethod();
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		appUtils.afterMethod(result);
	}

	@Test
	public void demoTest_1() {
		logger.info(new Throwable().getStackTrace()[0].getMethodName());
		
	}
}
