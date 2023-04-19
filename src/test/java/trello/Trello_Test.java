package trello;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.qspider.atlassian.genericutility.BaseClassTrello;
import com.qspider.atlassian.pomrepository.LogoutOfAtlassianAccountPage;
import com.qspider.atlassian.pomrepository.TrelloAccountBoardPage;
import com.qspider.atlassian.pomrepository.TrelloCreatedBoardPage;
import com.qspider.atlassian.pomrepository.TrelloLoginPage;
import com.qspider.atlassian.pomrepository.TrelloLoginToContinuePage;

public class Trello_Test extends BaseClassTrello {
	
	@Test(priority = 1)
	public void home_page_check() throws Exception {
		String actulTitleOfHomePage = trelloHomePage.getHomePageTitle();
		String completeHomepageTitle = excelutil.readStringData("trellotestcasedata", 3, 1);
		Assert.assertEquals(actulTitleOfHomePage,completeHomepageTitle,
				"Trello home page is not displayed and verified upon title.");
		Reporter.log("Trello home page is displayed and verified upon title.");
		String actualHomePageUrl = trelloHomePage.getHomePageUrl();
		String completeHomepageUrl = fileUtil.readCommondata("url");
		Assert.assertEquals(actualHomePageUrl,completeHomepageUrl,
				"Trello home page is not displayed and verified upon url.");
		Reporter.log("Trello home page is displayed and verified upon url.");
	}
	
	@Test(priority = 2)
	public void login_page_check() throws Exception {
		// clicking on Login button
		TrelloLoginPage trelloLoginPage = trelloHomePage.clickOnLogin();		
		// Validating Title
		String actualLoginPageTitle = trelloLoginPage.getLoginPageTitle();
		String completeLoginpageTitle = excelutil.readStringData("trellotestcasedata", 4, 1);
		Assert.assertEquals(actualLoginPageTitle,completeLoginpageTitle,
						"Trello login page is not displayed and verified upon title.");
		Reporter.log("Trello login page is displayed and verified upon title.");
				// validating URL
		String actualLoginPageUrl = trelloLoginPage.getLoginPageUrl();
		String completeLoginpageUrl = excelutil.readStringData("trellotestcasedata", 5, 1);
		Assert.assertEquals(actualLoginPageUrl,completeLoginpageUrl,
						"Trello login page is not displayed and verified upon url.");
		Reporter.log("Trello login page is displayed and verified upon url.");
	}
	
	@Test(priority = 3)
	public void login_To_Account() throws Throwable {
		
		TrelloLoginPage trelloLoginPage = trelloHomePage.clickOnLogin();
		trelloLoginPage.enterUsername(excelutil.readStringData("trellotestcasedata", 0, 1));
		TrelloLoginToContinuePage trellologinToContinuePage = trelloLoginPage.submitContinue();
		// Validating Title
		String actualLoginToContinuePageTitle = trellologinToContinuePage.getLoginToContinuePageTitle();
		String completeLoginToContinuePageTitle = excelutil.readStringData("trellotestcasedata", 6, 1);
		Assert.assertEquals(actualLoginToContinuePageTitle,completeLoginToContinuePageTitle,
				"Trello login to continue page is not displayed and verified upon title.");
		Reporter.log("Trello login to continue page is displayed and verified upon title.");
		
		trellologinToContinuePage.enterPassword(excelutil.readStringData("trellotestcasedata", 1, 1));
		TrelloAccountBoardPage trelloAccountBoardPage = trellologinToContinuePage.submitLogin();
		String actualAccountBoardPageTitle = trelloAccountBoardPage.getTrelloAccountPageTitle();
		String expectedboardTitle = excelutil.readStringData("trellotestcasedata", 7, 1);
		Assert.assertEquals(actualAccountBoardPageTitle,expectedboardTitle,
				"Trello board page is not displayed and verified upon title.");
		Reporter.log("Trello board page is displayed and verified upon title.");
		// validating URL
		String boardpageUrlContains = excelutil.readStringData("trellotestcasedata", 8, 1);
		boolean actualAccountPageUrl = trelloAccountBoardPage.getTrelloAccountPageUrl(boardpageUrlContains);
		Assert.assertTrue(actualAccountPageUrl,"Trello board page is not displayed and verified upon url contains email reference.");
		Reporter.log("Trello board page is displayed and verified upon url contains email reference.");
		logout();
	}
	
	public void logout() throws Throwable {
		TrelloCreatedBoardPage createdboard4 = new TrelloCreatedBoardPage(driver);
		webElementUtil.clickOnVisibleElement(driver, createdboard4.profieMenuButton());
		Assert.assertTrue(webElementUtil.displayOfElement(driver, createdboard4.menulogoutButton()),
				"Logout Option in menu is not display");
		Reporter.log("LogOut option is Displayed");
		webElementUtil.clickOnVisibleElement(driver, createdboard4.menulogoutButton());
		LogoutOfAtlassianAccountPage logoutPage = new LogoutOfAtlassianAccountPage(driver);
		String logoupageUrlContains = excelutil.readStringData("trellotestcasedata", 13, 1);
		Assert.assertTrue(webdriverUtil.explicitwaitForUrlcontains(driver, logoupageUrlContains),
				"Log out of your Atlassian account page is not displayed and verified upon url contains email reference.");
		Reporter.log(
				"Log out of your Atlassian account page is displayed and verified upon url contains email reference.");
		webElementUtil.clickOnVisibleElement(driver, logoutPage.logoutButton());
		String homepageUrlContains = excelutil.readStringData("trellotestcasedata", 14, 1);
		// it is throwing timeout Exception due to some problem
		Assert.assertTrue(webdriverUtil.explicitwaitForUrlcontains(driver, homepageUrlContains),
				"Log out of your Atlassian account is not successfull and verified upon url contains email reference.");
		Reporter.log(
				"Log out of your Atlassian account is successfull and verified upon homepage URL");
	}
	
	public TrelloAccountBoardPage deleteBoard() throws Throwable {
		TrelloCreatedBoardPage createdpage1 = new TrelloCreatedBoardPage(driver);
		// Checking for Board Menu Pop-up
		webElementUtil.clickOnVisibleElement(driver, createdpage1.boardMenubar());
		Assert.assertTrue(webElementUtil.displayOfElement(driver, createdpage1.moreOptionInBoardMenu()),
				"Board menu is not displyed");
		webElementUtil.clickOnVisibleElement(driver, createdpage1.moreOptionInBoardMenu());
		webElementUtil.clickOnVisibleElement(driver, createdpage1.closeBoardOptionInMore());
		webElementUtil.clickOnVisibleElement(driver, createdpage1.confirmCloseButton());
		webElementUtil.clickOnVisibleElement(driver, createdpage1.permenentlyDeleteBoardLink());
		webElementUtil.clickOnVisibleElement(driver, createdpage1.confirmPermenentlyDeleteButton());
		// Validating Board Deleted Pop-up
		Assert.assertTrue(webElementUtil.displayOfElement(driver, createdpage1.boardDeletedConfirmationPopup()),
				"Board is not deleted and board deleted popup is not displayed");
		Reporter.log("Board is succefully deleted and varified on popup displayed");
		return new TrelloAccountBoardPage(driver);
	}
	
	
	@Test(priority = 4)
	public void create_and_delete_board() throws Throwable {
		TrelloLoginPage trelloLoginPage = trelloHomePage.clickOnLogin();
		trelloLoginPage.enterUsername(excelutil.readStringData("trellotestcasedata", 0, 1));
		TrelloLoginToContinuePage trellologinToContinuePage = trelloLoginPage.submitContinue();
		// Validating Title
		String actualLoginToContinuePageTitle = trellologinToContinuePage.getLoginToContinuePageTitle();
		String completeLoginToContinuePageTitle = excelutil.readStringData("trellotestcasedata", 6, 1);
		Assert.assertEquals(actualLoginToContinuePageTitle,completeLoginToContinuePageTitle,
				"Trello login to continue page is not displayed and verified upon title.");
		Reporter.log("Trello login to continue page is displayed and verified upon title.");
		
		trellologinToContinuePage.enterPassword(excelutil.readStringData("trellotestcasedata", 1, 1));
		TrelloAccountBoardPage trelloAccountBoardPage = trellologinToContinuePage.submitLogin();
		String actualAccountBoardPageTitle = trelloAccountBoardPage.getTrelloAccountPageTitle();
		String expectedboardTitle = excelutil.readStringData("trellotestcasedata", 7, 1);
		Assert.assertEquals(actualAccountBoardPageTitle,expectedboardTitle,
				"Trello board page is not displayed and verified upon title.");
		Reporter.log("Trello board page is displayed and verified upon title.");
		// validating URL
		String boardpageUrlContains = excelutil.readStringData("trellotestcasedata", 8, 1);
		boolean actualAccountPageUrl = trelloAccountBoardPage.getTrelloAccountPageUrl(boardpageUrlContains);
		Assert.assertTrue(actualAccountPageUrl,"Trello board page is not displayed and verified upon url contains email reference.");
		Reporter.log("Trello board page is displayed and verified upon url contains email reference.");
		
		trelloAccountBoardPage.getCreateBoardPopup();
		trelloAccountBoardPage.enterBoarName(excelutil.readStringData("trellotestcasedata", 2, 1));
		TrelloCreatedBoardPage trellocreatedBoardPage = trelloAccountBoardPage.createBoard();
		// Validating URL
		
		Boolean actualBoardPageUrl = trellocreatedBoardPage.getBoardPageUrl();
		Assert.assertTrue(actualBoardPageUrl,"Created board page is not displayed and verified upon url contains boardname.");
		Reporter.log("Created board page is displayed and verified upon url contains boardname.");
		// Validating BoardName
		String actualCreatedBoardName = trellocreatedBoardPage.boardNameText();
		String expectedCreatedBoardName = excelutil.readStringData("trellotestcasedata", 2, 1);
		Assert.assertEquals(actualCreatedBoardName,expectedCreatedBoardName,
				"Created board page is not displayed and verified upon boardname.");
		Reporter.log("Created board page is displayed and verified upon boardname.");
		deleteBoard();
		logout();
	}
	
	@Test(priority = 5)
	public void create_2_lists_in_board() throws Throwable {
		TrelloLoginPage trelloLoginPage = trelloHomePage.clickOnLogin();
		trelloLoginPage.enterUsername(excelutil.readStringData("trellotestcasedata", 0, 1));
		TrelloLoginToContinuePage trellologinToContinuePage = trelloLoginPage.submitContinue();
		// Validating Title
		String actualLoginToContinuePageTitle = trellologinToContinuePage.getLoginToContinuePageTitle();
		String completeLoginToContinuePageTitle = excelutil.readStringData("trellotestcasedata", 6, 1);
		Assert.assertEquals(actualLoginToContinuePageTitle,completeLoginToContinuePageTitle,
				"Trello login to continue page is not displayed and verified upon title.");
		Reporter.log("Trello login to continue page is displayed and verified upon title.");
		
		trellologinToContinuePage.enterPassword(excelutil.readStringData("trellotestcasedata", 1, 1));
		TrelloAccountBoardPage trelloAccountBoardPage = trellologinToContinuePage.submitLogin();
		String actualAccountBoardPageTitle = trelloAccountBoardPage.getTrelloAccountPageTitle();
		String expectedboardTitle = excelutil.readStringData("trellotestcasedata", 7, 1);
		Assert.assertEquals(actualAccountBoardPageTitle,expectedboardTitle,
				"Trello board page is not displayed and verified upon title.");
		Reporter.log("Trello board page is displayed and verified upon title.");
		// validating URL
		String boardpageUrlContains = excelutil.readStringData("trellotestcasedata", 8, 1);
		boolean actualAccountPageUrl = trelloAccountBoardPage.getTrelloAccountPageUrl(boardpageUrlContains);
		Assert.assertTrue(actualAccountPageUrl,"Trello board page is not displayed and verified upon url contains email reference.");
		Reporter.log("Trello board page is displayed and verified upon url contains email reference.");
		
		trelloAccountBoardPage.getCreateBoardPopup();
		trelloAccountBoardPage.enterBoarName(excelutil.readStringData("trellotestcasedata", 2, 1));
		TrelloCreatedBoardPage trellocreatedBoardPage = trelloAccountBoardPage.createBoard();
		// Validating URL
		
		Boolean actualBoardPageUrl = trellocreatedBoardPage.getBoardPageUrl();
		Assert.assertTrue(actualBoardPageUrl,"Created board page is not displayed and verified upon url contains boardname.");
		Reporter.log("Created board page is displayed and verified upon url contains boardname.");
		// Validating BoardName
		String actualCreatedBoardName = trellocreatedBoardPage.boardNameText();
		String expectedCreatedBoardName = excelutil.readStringData("trellotestcasedata", 2, 1);
		Assert.assertEquals(actualCreatedBoardName,expectedCreatedBoardName,
				"Created board page is not displayed and verified upon boardname.");
		Reporter.log("Created board page is displayed and verified upon boardname.");
		
		
		for (int cellnum = 1; cellnum < excelutil.getLastCellNumInSheet("trellotestcasedata", 10); cellnum++) {
			trellocreatedBoardPage.enterListTitle(excelutil.readStringData("trellotestcasedata", 10, cellnum));
			trellocreatedBoardPage.submitList();
		}
		Assert.assertEquals(trellocreatedBoardPage.listsCreated().size(), excelutil.readNumericData("trellotestcasedata", 9, 1),
				"2 lists not created and varified upon count");
		Reporter.log("2 lists successfully created and varified upon count");
		deleteBoard();
		logout();
	}
	@Test(priority = 6)
	public void swipe_2_lists() throws Throwable {
		TrelloLoginPage trelloLoginPage = trelloHomePage.clickOnLogin();
		trelloLoginPage.enterUsername(excelutil.readStringData("trellotestcasedata", 0, 1));
		TrelloLoginToContinuePage trellologinToContinuePage = trelloLoginPage.submitContinue();
		// Validating Title
		String actualLoginToContinuePageTitle = trellologinToContinuePage.getLoginToContinuePageTitle();
		String completeLoginToContinuePageTitle = excelutil.readStringData("trellotestcasedata", 6, 1);
		Assert.assertEquals(actualLoginToContinuePageTitle,completeLoginToContinuePageTitle,
				"Trello login to continue page is not displayed and verified upon title.");
		Reporter.log("Trello login to continue page is displayed and verified upon title.");
		
		trellologinToContinuePage.enterPassword(excelutil.readStringData("trellotestcasedata", 1, 1));
		TrelloAccountBoardPage trelloAccountBoardPage = trellologinToContinuePage.submitLogin();
		String actualAccountBoardPageTitle = trelloAccountBoardPage.getTrelloAccountPageTitle();
		String expectedboardTitle = excelutil.readStringData("trellotestcasedata", 7, 1);
		Assert.assertEquals(actualAccountBoardPageTitle,expectedboardTitle,
				"Trello board page is not displayed and verified upon title.");
		Reporter.log("Trello board page is displayed and verified upon title.");
		// validating URL
		String boardpageUrlContains = excelutil.readStringData("trellotestcasedata", 8, 1);
		boolean actualAccountPageUrl = trelloAccountBoardPage.getTrelloAccountPageUrl(boardpageUrlContains);
		Assert.assertTrue(actualAccountPageUrl,"Trello board page is not displayed and verified upon url contains email reference.");
		Reporter.log("Trello board page is displayed and verified upon url contains email reference.");
		
		trelloAccountBoardPage.getCreateBoardPopup();
		trelloAccountBoardPage.enterBoarName(excelutil.readStringData("trellotestcasedata", 2, 1));
		TrelloCreatedBoardPage trellocreatedBoardPage = trelloAccountBoardPage.createBoard();
		// Validating URL
		
		Boolean actualBoardPageUrl = trellocreatedBoardPage.getBoardPageUrl();
		Assert.assertTrue(actualBoardPageUrl,"Created board page is not displayed and verified upon url contains boardname.");
		Reporter.log("Created board page is displayed and verified upon url contains boardname.");
		// Validating BoardName
		String actualCreatedBoardName = trellocreatedBoardPage.boardNameText();
		String expectedCreatedBoardName = excelutil.readStringData("trellotestcasedata", 2, 1);
		Assert.assertEquals(actualCreatedBoardName,expectedCreatedBoardName,
				"Created board page is not displayed and verified upon boardname.");
		Reporter.log("Created board page is displayed and verified upon boardname.");
		
		for (int cellnum = 1; cellnum < excelutil.getLastCellNumInSheet("trellotestcasedata", 10); cellnum++) {
			trellocreatedBoardPage.enterListTitle(excelutil.readStringData("trellotestcasedata", 10, cellnum));
			trellocreatedBoardPage.submitList();
		}
		Assert.assertEquals(trellocreatedBoardPage.listsCreated().size(), excelutil.readNumericData("trellotestcasedata", 9, 1),
				"2 lists not created and varified upon count");
		Reporter.log("2 lists successfully created and varified upon count");
		//===========
		Reporter.log("Order of Lists before swapping");
		for (WebElement listName : trellocreatedBoardPage.createdListNames()) {
			String text = listName.getText();
			Reporter.log(text);
		}
		trellocreatedBoardPage.swipeTheList(driver, trellocreatedBoardPage.listInBoard(excelutil.readStringData("trellotestcasedata", 10, 1)));
		Reporter.log("Order of Lists after swapping");
		for (WebElement listName : trellocreatedBoardPage.createdListNames()) {
			String text = listName.getText();
			Reporter.log(text);
		}
		//=========
		deleteBoard();
		logout();
	}
	
	@Test(priority = 7)
	public void sort_cards_alpabetically() throws Throwable {
		TrelloLoginPage trelloLoginPage = trelloHomePage.clickOnLogin();
		trelloLoginPage.enterUsername(excelutil.readStringData("trellotestcasedata", 0, 1));
		TrelloLoginToContinuePage trellologinToContinuePage = trelloLoginPage.submitContinue();
		// Validating Title
		String actualLoginToContinuePageTitle = trellologinToContinuePage.getLoginToContinuePageTitle();
		String completeLoginToContinuePageTitle = excelutil.readStringData("trellotestcasedata", 6, 1);
		Assert.assertEquals(actualLoginToContinuePageTitle,completeLoginToContinuePageTitle,
				"Trello login to continue page is not displayed and verified upon title.");
		Reporter.log("Trello login to continue page is displayed and verified upon title.");
		
		trellologinToContinuePage.enterPassword(excelutil.readStringData("trellotestcasedata", 1, 1));
		TrelloAccountBoardPage trelloAccountBoardPage = trellologinToContinuePage.submitLogin();
		String actualAccountBoardPageTitle = trelloAccountBoardPage.getTrelloAccountPageTitle();
		String expectedboardTitle = excelutil.readStringData("trellotestcasedata", 7, 1);
		Assert.assertEquals(actualAccountBoardPageTitle,expectedboardTitle,
				"Trello board page is not displayed and verified upon title.");
		Reporter.log("Trello board page is displayed and verified upon title.");
		// validating URL
		String boardpageUrlContains = excelutil.readStringData("trellotestcasedata", 8, 1);
		boolean actualAccountPageUrl = trelloAccountBoardPage.getTrelloAccountPageUrl(boardpageUrlContains);
		Assert.assertTrue(actualAccountPageUrl,"Trello board page is not displayed and verified upon url contains email reference.");
		Reporter.log("Trello board page is displayed and verified upon url contains email reference.");
		
		trelloAccountBoardPage.getCreateBoardPopup();
		trelloAccountBoardPage.enterBoarName(excelutil.readStringData("trellotestcasedata", 2, 1));
		TrelloCreatedBoardPage trellocreatedBoardPage = trelloAccountBoardPage.createBoard();
		// Validating URL
		
		Boolean actualBoardPageUrl = trellocreatedBoardPage.getBoardPageUrl();
		Assert.assertTrue(actualBoardPageUrl,"Created board page is not displayed and verified upon url contains boardname.");
		Reporter.log("Created board page is displayed and verified upon url contains boardname.");
		// Validating BoardName
		String actualCreatedBoardName = trellocreatedBoardPage.boardNameText();
		String expectedCreatedBoardName = excelutil.readStringData("trellotestcasedata", 2, 1);
		Assert.assertEquals(actualCreatedBoardName,expectedCreatedBoardName,
				"Created board page is not displayed and verified upon boardname.");
		Reporter.log("Created board page is displayed and verified upon boardname.");
		
		trellocreatedBoardPage.enterListTitle(excelutil.readStringData("trellotestcasedata", 10, 1));
		trellocreatedBoardPage.submitList();
		
		Assert.assertEquals(trellocreatedBoardPage.listsCreated().size(), excelutil.readNumericData("trellotestcasedata", 9, 1)-1,
				"list not created");
		Reporter.log("list successfully created and varified");
		
		trellocreatedBoardPage.clickToAddCard(excelutil.readStringData("trellotestcasedata", 10, 1));
		for (int cellNum = 1; cellNum < excelutil.getLastCellNumInSheet("trellotestcasedata", 12); cellNum++) {
			String data = excelutil.readStringData("trellotestcasedata", 12, cellNum);
			trellocreatedBoardPage.enterTextInListCard(excelutil.readStringData("trellotestcasedata", 10, 1), data);
			trellocreatedBoardPage.addCardInList(excelutil.readStringData("trellotestcasedata", 10, 1));
		}
		trellocreatedBoardPage.cancelAddCard();
		Reporter.log("Cards before sorting");
		for (WebElement element : trellocreatedBoardPage.sortedCardText()) {
			String text = trellocreatedBoardPage.getTagtexOfElement(driver, element);
			Reporter.log(text);
		}
		trellocreatedBoardPage.sortCardsAlpabetically(excelutil.readStringData("trellotestcasedata", 10, 1));
		Reporter.log("Card name After sorting");
		for (WebElement element : trellocreatedBoardPage.sortedCardText()) {
			String text = trellocreatedBoardPage.getTagtexOfElement(driver, element);
			Reporter.log(text);
		}
		deleteBoard();
		logout();
	}
	@Test(priority = 8)
	public void get_count_Of_lists_and_cards() throws Throwable {
		TrelloLoginPage trelloLoginPage = trelloHomePage.clickOnLogin();
		trelloLoginPage.enterUsername(excelutil.readStringData("trellotestcasedata", 0, 1));
		TrelloLoginToContinuePage trellologinToContinuePage = trelloLoginPage.submitContinue();
		// Validating Title
		String actualLoginToContinuePageTitle = trellologinToContinuePage.getLoginToContinuePageTitle();
		String completeLoginToContinuePageTitle = excelutil.readStringData("trellotestcasedata", 6, 1);
		Assert.assertEquals(actualLoginToContinuePageTitle,completeLoginToContinuePageTitle,
				"Trello login to continue page is not displayed and verified upon title.");
		Reporter.log("Trello login to continue page is displayed and verified upon title.");
		
		trellologinToContinuePage.enterPassword(excelutil.readStringData("trellotestcasedata", 1, 1));
		TrelloAccountBoardPage trelloAccountBoardPage = trellologinToContinuePage.submitLogin();
		String actualAccountBoardPageTitle = trelloAccountBoardPage.getTrelloAccountPageTitle();
		String expectedboardTitle = excelutil.readStringData("trellotestcasedata", 7, 1);
		Assert.assertEquals(actualAccountBoardPageTitle,expectedboardTitle,
				"Trello board page is not displayed and verified upon title.");
		Reporter.log("Trello board page is displayed and verified upon title.");
		// validating URL
		String boardpageUrlContains = excelutil.readStringData("trellotestcasedata", 8, 1);
		boolean actualAccountPageUrl = trelloAccountBoardPage.getTrelloAccountPageUrl(boardpageUrlContains);
		Assert.assertTrue(actualAccountPageUrl,"Trello board page is not displayed and verified upon url contains email reference.");
		Reporter.log("Trello board page is displayed and verified upon url contains email reference.");
		
		trelloAccountBoardPage.getCreateBoardPopup();
		trelloAccountBoardPage.enterBoarName(excelutil.readStringData("trellotestcasedata", 2, 1));
		TrelloCreatedBoardPage trellocreatedBoardPage = trelloAccountBoardPage.createBoard();
		// Validating URL
		
		Boolean actualBoardPageUrl = trellocreatedBoardPage.getBoardPageUrl();
		Assert.assertTrue(actualBoardPageUrl,"Created board page is not displayed and verified upon url contains boardname.");
		Reporter.log("Created board page is displayed and verified upon url contains boardname.");
		// Validating BoardName
		String actualCreatedBoardName = trellocreatedBoardPage.boardNameText();
		String expectedCreatedBoardName = excelutil.readStringData("trellotestcasedata", 2, 1);
		Assert.assertEquals(actualCreatedBoardName,expectedCreatedBoardName,
				"Created board page is not displayed and verified upon boardname.");
		Reporter.log("Created board page is displayed and verified upon boardname.");
		
		trellocreatedBoardPage.enterListTitle(excelutil.readStringData("trellotestcasedata", 10, 1));
		trellocreatedBoardPage.submitList();
		
		Assert.assertEquals(trellocreatedBoardPage.listsCreated().size(), excelutil.readNumericData("trellotestcasedata", 9, 1)-1,
				"list not created");
		Reporter.log("list successfully created and varified");
		
		trellocreatedBoardPage.clickToAddCard(excelutil.readStringData("trellotestcasedata", 10, 1));
		for (int cellNum = 1; cellNum < excelutil.getLastCellNumInSheet("trellotestcasedata", 12); cellNum++) {
			String data = excelutil.readStringData("trellotestcasedata", 12, cellNum);
			trellocreatedBoardPage.enterTextInListCard(excelutil.readStringData("trellotestcasedata", 10, 1), data);
			trellocreatedBoardPage.addCardInList(excelutil.readStringData("trellotestcasedata", 10, 1));
		}
		trellocreatedBoardPage.cancelAddCard();
		Reporter.log("Cards before sorting");
		for (WebElement element : trellocreatedBoardPage.sortedCardText()) {
			String text = trellocreatedBoardPage.getTagtexOfElement(driver, element);
			Reporter.log(text);
		}
		trellocreatedBoardPage.sortCardsAlpabetically(excelutil.readStringData("trellotestcasedata", 10, 1));
		Reporter.log("Card name After sorting");
		for (WebElement element : trellocreatedBoardPage.sortedCardText()) {
			String text = trellocreatedBoardPage.getTagtexOfElement(driver, element);
			Reporter.log(text);
		}
		int countOfList = trellocreatedBoardPage.createdListNames().size();
		int countOfCardsInList = trellocreatedBoardPage.sortedCardText().size();
		Reporter.log("Count of created lists in Board : " + countOfList);
		Reporter.log("Ccount of cards created in list : " + countOfCardsInList);
		deleteBoard();
		logout();
	}
	
	@Test(priority = 9)
	public void move_loaded_list_to_another_board() throws Throwable {
		TrelloLoginPage trelloLoginPage = trelloHomePage.clickOnLogin();
		trelloLoginPage.enterUsername(excelutil.readStringData("trellotestcasedata", 0, 1));
		TrelloLoginToContinuePage trellologinToContinuePage = trelloLoginPage.submitContinue();
		// Validating Title
		String actualLoginToContinuePageTitle = trellologinToContinuePage.getLoginToContinuePageTitle();
		String completeLoginToContinuePageTitle = excelutil.readStringData("trellotestcasedata", 6, 1);
		Assert.assertEquals(actualLoginToContinuePageTitle,completeLoginToContinuePageTitle,
				"Trello login to continue page is not displayed and verified upon title.");
		Reporter.log("Trello login to continue page is displayed and verified upon title.");
		
		trellologinToContinuePage.enterPassword(excelutil.readStringData("trellotestcasedata", 1, 1));
		TrelloAccountBoardPage trelloAccountBoardPage = trellologinToContinuePage.submitLogin();
		String actualAccountBoardPageTitle = trelloAccountBoardPage.getTrelloAccountPageTitle();
		String expectedboardTitle = excelutil.readStringData("trellotestcasedata", 7, 1);
		Assert.assertEquals(actualAccountBoardPageTitle,expectedboardTitle,
				"Trello board page is not displayed and verified upon title.");
		Reporter.log("Trello board page is displayed and verified upon title.");
		// validating URL
		String boardpageUrlContains = excelutil.readStringData("trellotestcasedata", 8, 1);
		boolean actualAccountPageUrl = trelloAccountBoardPage.getTrelloAccountPageUrl(boardpageUrlContains);
		Assert.assertTrue(actualAccountPageUrl,"Trello board page is not displayed and verified upon url contains email reference.");
		Reporter.log("Trello board page is displayed and verified upon url contains email reference.");
		
		TrelloCreatedBoardPage trellocreatedBoardPage=trelloAccountBoardPage.createBoards("trellotestcasedata", 15);
		Boolean actualBoardPageUrl = trellocreatedBoardPage.getBoardPageUrl();
		Assert.assertTrue(actualBoardPageUrl,"Created board page is not displayed and verified upon url contains boardname.");
		Reporter.log("Created board page is displayed and verified upon url contains boardname.");
		// Validating BoardName
		String actualCreatedBoardName = trellocreatedBoardPage.boardNameText();
		String expectedCreatedBoardName = excelutil.readStringData("trellotestcasedata", 15, 2);
		Assert.assertEquals(actualCreatedBoardName,expectedCreatedBoardName,
				"Created board page is not displayed and verified upon boardname.");
		Reporter.log("Created board page is displayed and verified upon boardname.");
		
		trellocreatedBoardPage.enterListTitle(excelutil.readStringData("trellotestcasedata", 10, 1));
		trellocreatedBoardPage.submitList();
		
		trellocreatedBoardPage.clickToAddCard(excelutil.readStringData("trellotestcasedata", 10, 1));
		for (int cellNum = 1; cellNum < excelutil.getLastCellNumInSheet("trellotestcasedata", 12); cellNum++) {
			String data = excelutil.readStringData("trellotestcasedata", 12, cellNum);
			trellocreatedBoardPage.enterTextInListCard(excelutil.readStringData("trellotestcasedata", 10, 1), data);
			trellocreatedBoardPage.addCardInList(excelutil.readStringData("trellotestcasedata", 10, 1));
		}
		trellocreatedBoardPage.cancelAddCard();
		
		trellocreatedBoardPage.moveListToAnotherBoard("trellotestcasedata", 10, 1, 15, 1);
		TrelloAccountBoardPage acountBoardpage = deleteBoard();
		trellocreatedBoardPage=acountBoardpage.selectBoardOnAccountPage(excelutil.readStringData("trellotestcasedata", 15, 1));
		Assert.assertTrue(trellocreatedBoardPage.list(), "List not moved successfully");
		Reporter.log("List moved sucessfully: Fully loaded list cotains card");
		for (WebElement element : trellocreatedBoardPage.sortedCardText()) {
			String text = trellocreatedBoardPage.getTagtexOfElement(driver, element);
			Reporter.log(text);
		}
		deleteBoard();
		logout();
	}
}
