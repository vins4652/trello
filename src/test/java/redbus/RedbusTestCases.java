package redbus;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class RedbusTestCases {
	WebDriver driver;
	WebDriverWait wait;
	String browser;
	String url;
	Sheet sheet;
	@BeforeClass
	public void testCaseData() throws Throwable {
	FileInputStream fis = new FileInputStream("./src/test/resources/redBusTestCaseData.xlsx");
	Workbook workbook = WorkbookFactory.create(fis);
	sheet = workbook.getSheet("redbusTestCaseData");
	}
	@BeforeMethod
	public void launchTheBrowser() throws IOException {
		FileInputStream fis = new FileInputStream("./src/test/resources/redBusCommonData.properties");
		Properties pobj = new Properties();
		pobj.load(fis);
		browser = pobj.getProperty("browser");
		url = pobj.getProperty("url");
		if(browser.equals("chrome")) {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			driver.get(url);
		}else if (browser.equals("firefox")) {
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			driver.get(url);
		}
	}
	//@AfterMethod
	public void terminateTheBrowser() {
		driver.manage().window().minimize();
		driver.quit();
	}
	
	
}
