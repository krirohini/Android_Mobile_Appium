package com.rohini.automation.mobile;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;

import io.appium.java_client.AppiumDriver;

public class AppUtils {
	
	public static Log logger = LogFactory.getLog(AppUtils.class);
	
	public static CapabilityManager CAPABILITY_MANAGER;
	public static AppiumUtils APPIUM_UTILS;
	public static AppUtils appUtils;
	public static FileUtil FILE_UTIL;
	public static DateUtil DATE_UTIL;
	
	public static RemoteWebDriver remoteWebDriver;
	public static AppiumDriver<?> appiumDriver;
	
	private AppUtils() throws MalformedURLException, IOException, Exception {
		APPIUM_UTILS = AppiumUtils.getInstance(remoteWebDriver, appiumDriver);
		DATE_UTIL = DateUtil.getInstance();
	}
	

	public static AppUtils getInstance()
			throws IOException, Exception {
		if (appUtils == null) {
			appUtils = new AppUtils();
		}
		return appUtils;
	}
	
	
	public void beforeMethod() throws Exception {
		
		try {
			APPIUM_UTILS.setWifi("Enable");	Thread.sleep(2000);
			APPIUM_UTILS.launchApp();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public void afterMethod(ITestResult result) {
		int status = result.getStatus();
		logger.info(">>>> result.getStatus() >>>> " + status);

		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				logger.info(" **************** FAILED **************** " + result.getName() + " ****************");
				APPIUM_UTILS.getScreenShot(result.getMethod().getMethodName());
				Thread.sleep(2000);
				
				
			} else if (result.getStatus() == ITestResult.SKIP) {
				logger.info(" **************** SKIPPED **************** " + result.getName() + " ****************");
			} else {
				logger.info(" **************** PASSED **************** " + result.getName() + " ****************");
			}
			
			
			APPIUM_UTILS.killApp();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		logger.info(
				"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ");
		logger.info(
				"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n\n\n\n ");

	}

	public void log_4_j_Configure() {
		BasicConfigurator.configure();
		PropertyConfigurator.configure("./src/main/java/log4j.properties");
	}

}
