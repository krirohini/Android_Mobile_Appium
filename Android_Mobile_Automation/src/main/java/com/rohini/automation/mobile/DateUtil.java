package com.rohini.automation.mobile;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.AppiumDriver;

public class DateUtil {
	private static Log logger = LogFactory.getLog(DateUtil.class);

	public static AppiumUtils appiumUtils;
	public static DateUtil dateUtils;

	private DateUtil() {
		
	}

	public static DateUtil getInstance()
			throws IOException {
		if (dateUtils == null) {
			dateUtils = new DateUtil();
		}
		return dateUtils;
	}

	public String todayDate_Month_Day_Of_Week_And_Year_Only() {
		String todayDate;
		SimpleDateFormat dayMonthYear = new SimpleDateFormat("dd-M-yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		todayDate = dayMonthYear.format(cal.getTime());
		return todayDate;
	}

}
