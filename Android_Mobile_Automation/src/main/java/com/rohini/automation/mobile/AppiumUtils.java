package com.rohini.automation.mobile;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class AppiumUtils {
	
	public final String ADB = System.getenv("ANDROID_HOME");

	public static Log logger = LogFactory.getLog(AppiumUtils.class);
	
	public static RemoteWebDriver remoteWebDriver;
	public static AppiumDriver<?> appiumDriver;
	public static AppiumUtils appiumUtils;

	private AppiumUtils(RemoteWebDriver remoteWebDriver, AppiumDriver<?> appiumDriver) throws MalformedURLException {
		BasicConfigurator.configure();
		PropertyConfigurator.configure("./src/main/java/log4j.properties");
		logger.info(" ****************************** ReactUiUtils is Coming in *************************** ");
		AppiumUtils.remoteWebDriver = remoteWebDriver;

		AppiumUtils.appiumDriver = appiumDriver;
	}

	public static AppiumUtils getInstance(RemoteWebDriver remoteWebDriver, AppiumDriver<?> appiumDriver)
			throws MalformedURLException {
		if (appiumUtils == null) {
			appiumUtils = new AppiumUtils(remoteWebDriver, appiumDriver);
		}
		return appiumUtils;
	}
	
	public void setWifi(String status) {
		logger.info(">>> Setting WiFi >>>> " + status);
		try {
			if (status.equalsIgnoreCase("Disable")) {
				String cmd = "adb shell svc wifi disable";
				Process proc = Runtime.getRuntime().exec(cmd);
				proc.waitFor();
				Thread.sleep(2000);
			} else {
				String cmd = "adb shell svc wifi enable";
				Process proc = Runtime.getRuntime().exec(cmd);
				proc.waitFor();
				Thread.sleep(2000);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void setMobileData(String status) {
		logger.info(">>> Setting Mobile Data >>>> " + status);
		try {
			if (status.equals("Disable")) {
				String cmd = "adb shell svc data disable";
				Process proc = Runtime.getRuntime().exec(cmd);
				proc.waitFor();
				Thread.sleep(2000);
			} else {
				String cmd = "adb shell svc data enable";
				Process proc = Runtime.getRuntime().exec(cmd);
				proc.waitFor();
				Thread.sleep(2000);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void setAirPlaneMode(String mode) {
		try {
			if (mode.equals("on")) {
				String cmd = "adb shell settings put global airplane_mode_on 1";
				Process proc = Runtime.getRuntime().exec(cmd);
				proc.waitFor();
				Thread.sleep(2000);
			} else {
				String cmd = "adb shell settings put global airplane_mode_on 0";
				Process proc = Runtime.getRuntime().exec(cmd);
				proc.waitFor();
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void launchApp() {
		appiumDriver.launchApp();
	}

	public void getScreenShot(String methodName) throws Exception {
		File srcFile = ((TakesScreenshot) remoteWebDriver).getScreenshotAs(OutputType.FILE);
		try {
			String screenShotFile = "./results/rk_screenshot/" + methodName + ".jpg";
			FileUtils.copyFile(srcFile, new File(screenShotFile));
			logger.info(" **************** Saved Screen Shot File **************** " + screenShotFile);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	public void killApp() {
		((AppiumDriver<?>) remoteWebDriver).closeApp();
	}

	public void pressBackButton() {
		((AndroidDriver<?>) remoteWebDriver).pressKeyCode(AndroidKeyCode.BACK);
		logger.info("******** Pressed Device Back Button ********");
	}

	public void pressRecentButton() {
		((AndroidDriver<?>) remoteWebDriver).pressKeyCode(AndroidKeyCode.ENTER);
	}

	public void pressHomeButton() {
		((AndroidDriver<?>) remoteWebDriver).pressKeyCode(AndroidKeyCode.HOME);
		logger.info("******** Pressed Device Home Button ********");
	}




}
