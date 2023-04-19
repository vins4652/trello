package trello;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TrelloApplication {

	WebDriver driver;
	String browser = "browser";
	WebDriverWait wait;
	String url = "url";
	String userName;
	String password;
	String welcomePageTitle;
	String loginpageTitle;
	String boardName;
	String testdatasheetname= "trellotestcasedata";

	public String commondata(String datareq ) throws IOException {
		FileInputStream fis = new FileInputStream("./src/test/resources/trellocommondata.properties");
		Properties pobj = new Properties();
		pobj.load(fis);	
		String value = pobj.getProperty(datareq);
		return value;
	}
	
	public String testcasedata(String sheetName,int rowNum,int cellNum) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("./src/test/resources/trelloAppTC.xlsx");
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(cellNum);
		String tcdata = cell.getStringCellValue();
		return tcdata;
	}
	@BeforeClass
	public void launchTheBrowser() throws IOException {
			browser = commondata(browser);
		if (browser.equals("chrome")) {
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			wait= new WebDriverWait(driver, Duration.ofSeconds(10));
		}else if (browser.equals("firefox")) {
			driver=new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			wait= new WebDriverWait(driver, Duration.ofSeconds(10));
		}else {
			Reporter.log("Unsupported browser mentioned.");
		}
		
	}
	@Test(priority=1)
	public void trelloWelcomePage() throws IOException {
		url = commondata(url);
		welcomePageTitle = testcasedata(testdatasheetname, 3, 1);
		//Reporter.log(welcomePageTitle);
		driver.get(url);
		if (driver.getTitle().equals(welcomePageTitle)) {
			Reporter.log("Welcome page is displayed and verified upon title.");
		}else {
			Reporter.log("Welcome page is not displayed and verified upon title.");
		}
	}
	@Test(priority=2,dependsOnMethods = "trelloWelcomePage")
	public void loginpage() throws EncryptedDocumentException, IOException {
		loginpageTitle = testcasedata(testdatasheetname, 4, 1);
		//TrelloLoginPage trelloLoginPage = new TrelloLoginPage(driver);
		userName = testcasedata(testdatasheetname, 0, 1);
		password = testcasedata(testdatasheetname, 1, 1);
		driver.findElement(By.linkText("Log in")).click();
		if (driver.getTitle().equals(loginpageTitle)) {
			Reporter.log("Login page is displayed and verified");
			driver.switchTo().activeElement().sendKeys(userName);
			driver.findElement(By.id("login")).submit();
			//trelloLoginPage.continueButton().submit();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
			driver.findElement(By.id("password")).sendKeys(password);
			driver.findElement(By.id("login-submit")).submit();
			
		}else {
			Reporter.log("Login page is not displayed and verified");
		}
	}
	@Test(priority = 5, dependsOnMethods = "loginpage")
	public void logout() throws EncryptedDocumentException, IOException {
		welcomePageTitle = testcasedata(testdatasheetname, 3, 1);
		driver.findElement(By.className("yRPuNUIoZpQWwj")).click();
		driver.findElement(By.xpath("//span[text()='Log out']")).click();
		driver.findElement(By.xpath("//span[text()='Log out']")).click();
		if (driver.getTitle().contains("home")) {
			Reporter.log("Logged out Sucessfully and varified");
		}else {
			Reporter.log("Logged out is not Sucessfull and varified");
		}
		
	}

	@Test(priority=3,dependsOnMethods = {"trelloWelcomePage","loginpage"})
	public void createBoard() throws EncryptedDocumentException, IOException {
		boardName = testcasedata(testdatasheetname, 2, 1);
		driver.findElement(By.xpath("//p[text()='Create']")).click();
		driver.findElement(By.xpath("//span[text()='Create board']")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.switchTo().activeElement()));
		driver.switchTo().activeElement().sendKeys(boardName);
		driver.findElement(By.xpath("//button[text()='Create']")).click();
		if (driver.getCurrentUrl().contains(boardName) && driver.findElement(By.cssSelector("h1[dir='auto']")).getText().equals(boardName)) {
			
			Reporter.log("Pass: Board is Created Succesfully and varified upon url and board name");
			
//			driver.findElement(By.cssSelector("button[aria-label='Show menu']")).click();
//			driver.findElement(By.xpath("//a[contains(@class,'js-open-more')]")).click();
//			driver.findElement(By.xpath("//a[contains(text(),'Close board')]")).click();
//			driver.findElement(By.cssSelector("input[value='Close']")).click();
//			driver.findElement(By.cssSelector("button[data-testid='close-board-delete-board-button']")).click();
//			driver.findElement(By.cssSelector("button[data-testid='close-board-delete-board-confirm-button']")).click();
//			if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Board deleted.']"))).getText().equals("Board deleted.")) {
//				Reporter.log("Pass : Board has deleted successfully and verified");
//				System.out.println("executed");
//			}else {
//				Reporter.log("Fail : Board is not deleted successfully and verified");
//			}
			
		}else {
			Reporter.log("Pass: Board is not Created Succesfully and varified upon url and board name");
		}
	}
	@Test(priority=4,dependsOnMethods = "createBoard")
	public void deleteBoard() {
		driver.findElement(By.xpath("//p[text()='Boards']")).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Boards']")));
		Actions action = new Actions(driver);
		WebElement automationBoard = driver.findElement(By.xpath("//a[text()='"+boardName+"']"));
		wait.until(ExpectedConditions.visibilityOf(automationBoard));
		action.moveToElement(automationBoard).perform();
		driver.findElement(By.xpath("//a[text()='"+boardName+"']/..//button[not (@title)]")).click();
		driver.findElement(By.xpath("//span[text()='Close board']")).click();
		driver.findElement(By.xpath("//button[text()='Close']")).click();
		driver.findElement(By.xpath("//button[text()='View closed boards']")).click();
		driver.findElement(By.xpath("//button[text()='Delete']")).click();
		driver.findElement(By.cssSelector("button[data-testid='close-board-delete-board-confirm-button']")).click();
		if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Board deleted.']"))).getText().equals("Board deleted.")) {
			Reporter.log("Pass : Board has deleted successfully and verified");
		}else {
			Reporter.log("Fail : Board is not deleted successfully and verified");
		}
	}
	@AfterClass
	public void closeBrowser() {
		driver.manage().window().minimize();
		driver.quit();
	}
}
