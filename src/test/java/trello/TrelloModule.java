package trello;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class TrelloModule {
	
	WebDriver driver;
	WebDriverWait wait;
	String userName="vinaymannur9@gmail.com";
	String password="@149vinay";
	String boardName = "createdboard";
	String url = "https://trello.com/";
	@Test
	public void launchTheBrowser() {
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait= new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	@Test
	public void trelloWelcomePage() {
		driver.get(url);
		if (driver.getTitle().equals("Manage Your Team’s Projects From Anywhere | Trello")) {
			System.out.println("Welcome page is displayed and verified upon title.");
		}else {
			System.out.println("Welcome page is not displayed and verified upon title.");
		}
	}
	@Test
	public void login() {
		driver.findElement(By.xpath("//a[text()='Log in']")).click();
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

}
