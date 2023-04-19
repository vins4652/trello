package trello;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TrelloBoard {

	WebDriver driver;
	WebDriverWait wait;
	String userName="najeerahamad.t@gmail.com";
	String password="naj@1997AHA";
	String boardName = "Automation";
	@BeforeClass
	public void launchTheBrowser() {
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait= new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	@Test(priority=1)
	public void trelloWelcomePage() {
		driver.get("https://trello.com/");
		if (driver.getTitle().equals("Manage Your Team’s Projects From Anywhere | Trello")) {
			System.out.println("Welcome page is displayed and verified upon title.");
		}else {
			System.out.println("Welcome page is not displayed and verified upon title.");
		}
	}
	@Test(priority=2,dependsOnMethods = "trelloWelcomePage")
	public void login() {
		driver.findElement(By.linkText("Log in")).click();
		if (driver.getTitle().equals("Log in to Trello")) {
			System.out.println("Login page is displayed and verified");
			driver.switchTo().activeElement().sendKeys(userName);
			driver.findElement(By.id("login")).submit();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
			driver.findElement(By.id("password")).sendKeys(password);
			driver.findElement(By.id("login-submit")).submit();
		}else {
			System.out.println("Login page is not displayed and verified");
		}
	}
	@AfterClass
	public void closeBrowser() {
		driver.manage().window().minimize();
		driver.quit();
	}
	@Test(priority=3,dependsOnMethods = {"trelloWelcomePage","login"})
	public void createBoard() {
		driver.findElement(By.xpath("//p[text()='Create']")).click();
		driver.findElement(By.xpath("//span[text()='Create board']")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.switchTo().activeElement()));
		driver.switchTo().activeElement().sendKeys(boardName);
		driver.findElement(By.xpath("//button[text()='Create']")).click();
	}
	@Test(priority=4,dependsOnMethods = "createBoard")
	public void deleteBoard() {
		driver.findElement(By.xpath("//p[text()='Boards']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Boards']")));
		Actions action = new Actions(driver);
		WebElement automationBoard = driver.findElement(By.xpath("//a[text()='"+boardName+"']"));
		action.moveToElement(automationBoard).perform();
		driver.findElement(By.xpath("//a[text()='"+boardName+"']/..//button[not (@title)]")).click();
		driver.findElement(By.xpath("//span[text()='Close board']")).click();
		driver.findElement(By.xpath("//button[text()='Close']")).click();
		driver.findElement(By.xpath("//button[text()='View closed boards']")).click();
		driver.findElement(By.xpath("//a[text()='"+boardName+"']/../..//button[text()='Delete']")).click();
		driver.findElement(By.cssSelector("button[data-testid='close-board-delete-board-confirm-button']")).click();
		if (driver.findElement(By.xpath("//span[text()='Board deleted.']")).isDisplayed()) {
			System.out.println("Fail : Board is not deleted successfully and verified");
		}else {
			System.out.println("Pass : Board has deleted successfully and verified");
		}
	}
}
