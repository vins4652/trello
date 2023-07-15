package com.qspider.atlassian.genericutility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class TakesScreenshotUtility {

	public String getScreenShot(WebDriver driver, String methodName) throws IOException {
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String filePath = "./errorshots/"+methodName+"_"+LocalDateTime.now().toString().replace(':', '-')+".png";
		File destFile= new File(filePath);
		FileUtils.copyFile(srcFile, destFile);
		return filePath;
	}
}
