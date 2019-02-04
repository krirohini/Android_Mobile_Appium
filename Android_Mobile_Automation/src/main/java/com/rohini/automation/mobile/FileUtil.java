package com.rohini.automation.mobile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import org.apache.commons.io.FileUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.AppiumDriver;

public class FileUtil {
	private static Log logger = LogFactory.getLog(FileUtil.class);

	public static AppiumUtils appiumUtils;
	public static RemoteWebDriver remoteWebDriver;
	public AppiumDriver<?> appiumDriver;
	public static FileUtil fileUtils;
	public static DateUtil dateUtils;

	private FileUtil() throws MalformedURLException, IOException {

	}

	public static FileUtil getInstance() throws IOException {
		if (fileUtils == null) {
			fileUtils = new FileUtil();
		}
		return fileUtils;
	}
	
	public static void deleteFile(String filePath) {
		try {
			File fileToDelete = new File(filePath);

			if (fileToDelete.delete()) {
				// logger.info("Previous Log File is Deleted Successfully !");
				logger.info("Previous File is Deleted Successfully !");
			} else {
				logger.error("File Delete Operation is Failed !");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteAllFilesOfFolder(String folder) throws IOException {
		File file = new File(folder);
		try {
			if (file.isDirectory()) {
				FileUtils.cleanDirectory(file);
			} else {
				logger.error(">>>> File/Folder Doesn't Exits >>>>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File createFileWithPath(String fileName, String filePathName) throws IOException, NullPointerException {
		// String fileName = "testcase.log";
		// File file = new
		// File("/Users/mps-b2bqa/Documents/Rohini/kp/SamsungHeartwise/app/results/rk_log/testcase.log");

		File file = new File(filePathName);

		try {
			if (file.createNewFile()) {
				logger.info("File named " + fileName + " created successfully !");
				return file;
			} else {
				logger.info("File with name " + fileName + " already exixts !");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static File createFile(String fileName) throws IOException, NullPointerException {

		File file = new File(fileName);

		try {
			if (file.createNewFile()) {
				logger.info("File named " + fileName + " created successfully !");
				return file;
			} else {
				logger.info("File with name " + fileName + " already exixts !");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
