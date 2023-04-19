package redbus;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class RedBusHomepage extends RedbusTestCases {

	@Test
	public void searchButtonFunctionalityWithoutData() {
		WebElement searchButton = driver.findElement(By.id("search_btn"));
		searchButton.click();
		//sheet.getRow(0).getCell(1).getStringCellValue()
		if (driver.getTitle().equals("Book Bus Travels, AC Volvo Bus, rPool & Bus Hire - redBus India")) {
			Reporter.log("Pass: The Page is not redirected to other page and varified upon the title");
			WebElement dataTextField = driver.findElement(By.cssSelector("div[class='fl search-box clearfix']"));
			if (dataTextField.getAttribute("style").contains("red")) {
				Reporter.log("Pass: The text field border turned to red color and varified");
			}else {
				Reporter.log("Fail: The text field border is not turned to red colour and varified");
			}
		}else {
			Reporter.log("Fail: The Page is redired to other page");
		}
	}
	//@Test
	public void stateOfTheChoosenPlaceAfterRefresh() {
		WebElement fromTextField = driver.findElement(By.id("src"));
		WebElement toTextField = driver.findElement(By.id("dest"));
		fromTextField.sendKeys("Bangalore");
		toTextField.sendKeys("Mysore");
		driver.navigate().refresh();
	}
	@Test
	public void availableBusesForValidData() {
		WebElement fromTextField = driver.findElement(By.id("src"));
		WebElement toTextField = driver.findElement(By.id("dest"));
		WebElement dateDropDown = driver.findElement(By.id("onward_cal"));
		fromTextField.sendKeys("Bangalore");
		wait.until(ExpectedConditions.elementToBeClickable(fromTextField)).sendKeys(Keys.ENTER);
		toTextField.sendKeys("Mysuru");
		wait.until(ExpectedConditions.elementToBeClickable(toTextField)).sendKeys(Keys.ENTER);
		dateDropDown.click();
		driver.findElement(By.cssSelector("td[class='current day']")).click();
		driver.findElement(By.id("search_btn")).click();
		//WebElement searchButton = driver.findElement(By.id("search_btn"));
		//wait.until(ExpectedConditions.elementToBeClickable(searchButton)
		String availability = driver.findElement(By.cssSelector("span[class='f-bold busFound']")).getText();
		System.out.println(availability);
		
	}
}
