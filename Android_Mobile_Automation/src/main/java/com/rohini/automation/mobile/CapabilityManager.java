package com.rohini.automation.mobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class CapabilityManager {
	
	private static Log logger = LogFactory.getLog(CapabilityManager.class);

	// singleton object
	public static CapabilityManager capabilityManager = null;

	// This is AndriodDriver for this project
	public RemoteWebDriver remoteWebDriver;

	public static AppiumDriver<WebElement> appiumDriver;
	public static AndroidDriver<WebElement> androidDriver;

	// This is the Desired Capabilities of connected Device
	public static DesiredCapabilities capabilities;

	public static AppiumUtils appiumUtils;

	public static AppiumServiceBuilder appiumServiceBuilder;
	public static AppiumDriverLocalService appiumService;

	// TODO : remove it

	@SuppressWarnings("rawtypes")
	public static AppiumDriver<WebElement> getAppiumDriver() {
		return appiumDriver;
	}

	public RemoteWebDriver getRemoteWebDriver() {
		return remoteWebDriver;
	}

	public AppiumUtils getsReactUiUtils() {
		return appiumUtils;
	}

	private CapabilityManager() {

	}

	public static synchronized CapabilityManager getInstance()
			throws MalformedURLException, IOException, Exception {

		logger.info("****************************** Getting Instance For ***** CapabilityManager ************** ");

		if (capabilityManager == null) {
			capabilityManager = new CapabilityManager();
			capabilityManager.start_Appium_Session();
		}

		return capabilityManager;
	}

	public static void preConditions() throws Exception {
		// Deleting the previously log file.
		//AppiumUtils.deleteFile("./results/rk_log/log_4_j.log");

		// Deleting the previously screenshots file.
		//AppiumUtils.deleteAllFilesOfFolder("./results/rk_screenshot");
		
	}

	@BeforeSuite
	private void start_Appium_Session() throws MalformedURLException, IOException, Exception {

		//CapabilityManager.preConditions();

		BasicConfigurator.configure();
		PropertyConfigurator.configure("./src/main/java/log4j.properties");

		logger.info(
				"****************************** Logging Started For CapabilityManager ****************************");

		logger.info("****************************** Setting Device Capabilities ************************** ");

		String deviceId = getDeviceId();
		String deviceOSVersion = getDeviceOSVersion();
		
		capabilities = settingDeviceCapabilities(deviceOSVersion, deviceId);

		URL appiumUrl = new URL("http://127.0.0.1:4723/wd/hub");
		AndroidDriver<WebElement> androidDriver = new AndroidDriver<>(appiumUrl, capabilities);
		
		appiumDriver = (AppiumDriver<WebElement>) androidDriver;
		remoteWebDriver = (RemoteWebDriver) androidDriver;
		appiumUtils = AppiumUtils.getInstance(remoteWebDriver, appiumDriver);

		logger.info(
				"*****************************************************************************************************");
	}

	@AfterSuite
	public void end_Appium_Session() {
		logger.info("\n" + new Throwable().getStackTrace()[0].getMethodName());
		remoteWebDriver.quit();
		logger.info(" ********** STOPPED ***** Appium Session ********** ");
	}

	/**
	 * This method is used for setting the desired capabilities for any android device.
	 * 
	 * @return DesiredCapabilities
	 */
	private DesiredCapabilities settingDeviceCapabilities(String androidVersion, String deviceName)
			throws WebDriverException {
		restartAppiumServer();

		try {

			Thread.sleep(2000);
			capabilities = new DesiredCapabilities();

			capabilities.setCapability("automationName", "UIAutomator2");
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability(CapabilityType.VERSION, androidVersion);
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("appPackage", "com.google.android.apps.maps");
			capabilities.setCapability("appActivity", "com.google.android.maps.MapsActivity");
			capabilities.setCapability("autoGrantPermissions", "false");
			capabilities.setCapability("autoAcceptAlerts", "true");
			capabilities.setCapability("noReset", "true");
			capabilities.setCapability("fullReset", "false");
			capabilities.setCapability("newCommandTimeout", 90);
			capabilities.setJavascriptEnabled(true);

			return capabilities;
		}catch (Exception e) {
			logger.error(e);
			Assert.fail();
			// e.printStackTrace();
		}
		return null;

	}

	/**
	 * This function will start Appium Server only once during start up before suite
	 * 
	 * @throws AppiumServerHasNotBeenStartedLocallyException
	 */
	//@BeforeClass
	private static void restartAppiumServer() throws AppiumServerHasNotBeenStartedLocallyException {
		logger.info("****************************** STARTING APPIUM SERVER ****************************");

		try {
			
			String APPIUM_HOME = "/usr/local/lib/node_modules/appium";
			//String appiumHome = System.getenv("APPIUM_HOME");

			
			 appiumServiceBuilder = new AppiumServiceBuilder().withAppiumJS(new
			 File(APPIUM_HOME + "/build/lib/main.js"))
			  .withIPAddress("127.0.0.1").usingPort(4723);
			 

			
			 /*AppiumServiceBuilder builder = new AppiumServiceBuilder() .withAppiumJS(new
			 File(appiumHome +"/build/lib/main.js")) .withIPAddress("10.0.1.205")
			 .usingPort(4723);*/
			 

			 /*appiumServiceBuilder = new AppiumServiceBuilder().withIPAddress("127.0.0.1")
			 .usingPort(4723);*/
			 

			appiumService = appiumServiceBuilder.build();
			
			appiumService.stop();
			logger.info("**** Stopping APPIUM ***************************");

			appiumService.start();
			logger.info("**** Started APPIUM ****************************");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public AndroidDriver<WebElement> getAndroidDriver() {
		return androidDriver;
	}

	public void setAndroidDriver(AndroidDriver<WebElement> androidDriver) {
		this.androidDriver = androidDriver;
	}

	public void connectedDevice() throws InterruptedException, IOException {
		String ADB = System.getenv("ANDROID_HOME");
		String cmd = "/platform-tools/adb shell devices";
		logger.info(
				"*************************************** List Of Devices ***********************************************");
		System.out.println(
				"*************************************** List Of Devices ***********************************************");

		try {
			Process proc;
			proc = Runtime.getRuntime().exec(ADB + cmd);
			proc.waitFor();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String cmd1 = "/platform-tools/adb getprop";
		System.out.println(
				"*****************************************************************************************************");
		System.out.println(
				"*****************************************************************************************************");

		try {
			Process proc1 = Runtime.getRuntime().exec(ADB + cmd1);
			proc1.waitFor();
			Thread.sleep(2000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDeviceId() throws Exception, InterruptedException, org.openqa.selenium.SessionNotCreatedException {
		String ADB = System.getenv("ANDROID_HOME");

		String cmd2 = "/platform-tools/adb shell settings get secure android_id";

		Process proc = Runtime.getRuntime().exec(ADB + cmd2);
		proc.waitFor();
		proc.getOutputStream();

		BufferedReader stdInput_forDeviceId = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		BufferedReader stdError_forDeviceId = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

		// read the output from the command
		String s = null;
		while ((s = stdInput_forDeviceId.readLine()) != null) {
			System.out.println("Here is the output for the command to get Device Id: \t" + s);
			logger.info("Here is the output for the command to get Device Id ..... " + s);
			return s;
		}

		// read any errors from the attempted command
		while ((s = stdError_forDeviceId.readLine()) != null) {
			System.out.println("Here is the standard error for the command to get Device Id (if any): \t" + s);
			logger.info("Here is the standard error for the command to get Device Id (if any) ...... " + s);
		}

		return null;

	}

	public String getDeviceOSVersion()
			throws Exception, InterruptedException, org.openqa.selenium.SessionNotCreatedException {
		String ADB = System.getenv("ANDROID_HOME");

		String cmd2 = "/platform-tools/adb shell getprop ro.build.version.release";

		Process proc = Runtime.getRuntime().exec(ADB + cmd2);
		proc.waitFor();
		proc.getOutputStream();

		BufferedReader stdInput_forDeviceOSVersion = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		BufferedReader stdError_forDeviceOSVersion = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

		// read the output from the command
		String s = null;
		while ((s = stdInput_forDeviceOSVersion.readLine()) != null) {
			System.out.println("Here is the output for the command to get Device OS Version: \t" + s);
			logger.info("Here is the output for the command to get Device OS Version ...... " + s);
			return s;
		}

		// read any errors from the attempted command
		while ((s = stdError_forDeviceOSVersion.readLine()) != null) {
			System.out.println("Here is the standard error for the command to get Device OS Version (if any): \t" + s);
			logger.info("Here is the standard error for the command to get Device OS Version (if any) ...... " + s);
			// System.out.println(s);
		}

		return null;

	}




}
