package com.courses.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.courses.base.BaseUI;
import com.courses.pages.Homepage;
import com.courses.pages.LanguageLearning;
import com.courses.utils.FileIO;

public class LanguageLearningTest extends BaseUI{
	private WebDriver driver;
	
	@BeforeClass(alwaysRun = true)
	public void setUp() {
		driver = invokeBrowser();
		openBrowser("websiteURL");
	}


	/***************************************************
	 ******** Verify Search Functionality: Language Learning ********
	 ***************************************************/
	@Test(dataProvider = "languageLearningData", groups= {"smoke","regression"})
	public void verifySearchFunctionality(String SearchBoxText, String LanguageLearningPageTitle) {
		logger = report.createTest("Verify Search Functionality with 'Language Learning'" );
		Homepage homepage = new Homepage(driver, logger);
		homepage.searchBoxTyping(SearchBoxText);
		homepage.clickSearch();
	}
	/***************************************************
	 ******** Verify title of the page ********
	 ***************************************************/
	@Test(dataProvider = "languageLearningData", dependsOnMethods = "verifySearchFunctionality",groups= {"smoke","regression"})
	public void verifyLanguageLearningPageTitle(String SearchBoxText, String LanguageLearningPageTitle) {
		logger = report.createTest("Verify Language Learning Page Title");
		LanguageLearning learningLanguage = new LanguageLearning(driver, logger);
		Assert.assertEquals(learningLanguage.getLanguagePageTitle(), LanguageLearningPageTitle);
	}
	/***************************************************
	 ******** Verify getting language count ********
	 ***************************************************/
	@Test(dependsOnMethods = "verifyLanguageLearningPageTitle",groups={"regression"})
	public void getLanguageCoursesCount() throws Exception {
		logger = report.createTest("Get Languages Count" );
		LanguageLearning learningLanguage = new LanguageLearning(driver, logger);
		learningLanguage.openLanguageDropBox();
		learningLanguage.clickShowAll();
		learningLanguage.getLanguagesOptions();
		learningLanguage.getLanguagesCount();
		learningLanguage.closeShowAllBox();
	}
	/***************************************************
	 ******** Verify getting level count ********
	 ***************************************************/
	@Test(dependsOnMethods = "getLanguageCoursesCount", groups= {"regression"})
	public void getLevelsCoursesCount() throws Exception {
		logger = report.createTest("Get Levels Count" );
		LanguageLearning learningLanguage = new LanguageLearning(driver, logger);
		learningLanguage.openLevelDropBox();
		learningLanguage.getLevelsOptions();
		learningLanguage.getLevelsCount();
		learningLanguage.writeCoursesData();
	}
	
	@AfterTest(alwaysRun = true)
	public void endReport() {
	report.flush();

	}
	
	/**********************************************
	 ******* Data Provider for Language Learning data ********
	 **********************************************/
	@DataProvider
	public Object[][] languageLearningData() throws IOException {
		HashMap<String, ArrayList<String>> dataMap = FileIO.readExcelData("LanguageLearning");
		int noRow = dataMap.size();
		int noCol = dataMap.get("1").size();
		Object[][] data = new Object[noRow][noCol];
		for (int i = 0; i < noRow; ++i) {
			ArrayList<String> rowData = dataMap.get("" + (i + 1));
			for (int j = 0; j < noCol; ++j) {
				data[i][j] = rowData.get(j);
			}
		}
		return data;
	}
	@AfterClass(alwaysRun = true)
	public void afterTest() {
		driver.quit();
	}
}
