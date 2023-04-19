package com.qspider.atlassian.genericutility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.qspider.atlassian.pomrepository.TrelloHomePage;

public class BaseClassTrello {
	public WebDriver driver;
	public FileUtility fileUtil = new FileUtility();
	public ExcelUtility excelutil = new ExcelUtility();
	public WebDriverUtility webdriverUtil = new WebDriverUtility();
	public WebElementUtility webElementUtil = new WebElementUtility();
	public JavaUtility javaUtil = new JavaUtility();
	public TakesScreenshotUtility tsUtil = new TakesScreenshotUtility();
	public TrelloHomePage trelloHomePage;
	
	@BeforeMethod
	public void configBeforeMethod() throws IOException {
		// launch the browser
		//String browser = fileUtil.readCommondata("browser");
		String browser = System.getProperty("Browser");
		if (browser.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equals("edge")) {
			driver = new EdgeDriver();
		}
		//maximize the browser
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		//trigger the main url of the application
		String url = fileUtil.readCommondata("url");
		driver.get(url);
		trelloHomePage = new TrelloHomePage(driver);
	}
	
	@AfterMethod
	public void configAfterMethod() {
		//minimize the browser and terminate the session
		driver.manage().window().minimize();
		driver.quit();
	}
	
	

}
