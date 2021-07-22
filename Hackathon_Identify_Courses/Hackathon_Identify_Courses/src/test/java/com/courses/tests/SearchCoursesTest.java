package com.courses.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.courses.base.BaseUI;
import com.courses.pages.Homepage;
import com.courses.pages.SearchResult;
import com.courses.utils.FileIO;

public class SearchCoursesTest extends BaseUI {
	private WebDriver driver;

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		driver = invokeBrowser();
		openBrowser("websiteURL");
	}

	/***************************************************
	 ******** Verify page title of coursera page ********
	 ***************************************************/
	@Test(dataProvider = "courseData", groups = {"smoke","regression"})
	public void verifyCourseraPageTitleTest(String HomePageTitle, String SearchBoxText, String Language, String Level, String WebDevelopmentPageTitle) {
		logger = report.createTest("Verify Coursera Page Title" );
		Homepage homePage = new Homepage(driver, logger);
		Assert.assertEquals(homePage.getHomeTitle(), HomePageTitle);
	}

	/******************************************************************************
	 **** Verify that search button ****
	 ******************************************************************************/
	@Test(dependsOnMethods = "verifyCourseraPageTitleTest", dataProvider = "courseData", groups = {"smoke","regression"})
	public void verifySearchTest(String HomePageTitle, String SearchBoxText, String Language, String Level, String WebDevelopmentPageTitle) {
		logger = report.createTest("Verify Search Functionality on HomePage" );
		Homepage homePage = new Homepage(driver, logger);
		homePage.searchBoxTyping(SearchBoxText);
		homePage.clickSearch();
	}
	/***************************************************
	 ******** Verify page title of Search page ********
	 ***************************************************/
	@Test(dependsOnMethods = "verifySearchTest", dataProvider = "courseData", groups = {"regression"})
	public void verifyWebDevelopmentPageTitle(String HomePageTitle, String SearchBoxText, String Language, String Level, String WebDevelopmentPageTitle) {
		logger = report.createTest("Verify Web Development Page Title");
		SearchResult search = new SearchResult(driver, logger);
		Assert.assertEquals(search.getSearchPageTitle(), WebDevelopmentPageTitle);
	}
	/***************************************************
	 ******** Verify filter courses by language ********
	 ***************************************************/
	@Test(dependsOnMethods = "verifyWebDevelopmentPageTitle", dataProvider = "courseData", groups = {"regression"})
	public void filtercoursesByLang(String HomePageTitle, String SearchBoxText, String Language, String Level, String WebDevelopmentPageTitle) {
		logger = report.createTest("Test Filter functionality for Language" );
		SearchResult search = new SearchResult(driver, logger);
		search.filterByLanguage();
		search.selectEnglish();
		Assert.assertEquals(search.checkLang(), Language);
	}
	/***************************************************
	 ******** Verify filter courses by level ********
	 ***************************************************/
	@Test(dependsOnMethods = "filtercoursesByLang", dataProvider = "courseData", groups = {"regression"})
	public void filtercoursesByLevel(String HomePageTitle, String SearchBoxText, String Language, String Level, String WebDevelopmentPageTitle) {
		logger = report.createTest("Test Filter functionality for Level" );
		SearchResult search = new SearchResult(driver, logger);
		search.filterByLevel();
		search.selectBeginner();
		Assert.assertEquals(search.checkLevel(), Level);
	}
	/***************************************************
	 ******** Verify getting first course details ********
	 ***************************************************/
	@Test(dependsOnMethods = "filtercoursesByLevel", groups = {"regression"})
	public void filtercourseFirst() throws InterruptedException {
		logger = report.createTest("First Course" );
		SearchResult search = new SearchResult(driver, logger);
		search.selectCourse(1);
		search.printcourseDetails(1);
	}
	/***************************************************
	 ******** Verify getting second course details ********
	 ***************************************************/
	@Test(dependsOnMethods = "filtercourseFirst", groups = {"regression"})
	public void filtercourseSecond() throws InterruptedException {
		logger = report.createTest("Second Course" );
		SearchResult search = new SearchResult(driver, logger);
		search.selectCourse(2);
		search.printcourseDetails(2);
	}
	/***************************************************
	 ******** Writing output data in excel ********
	 ***************************************************/
	@Test(dependsOnMethods = "filtercourseSecond", groups = {"regression"})
	public void writeCourseDetailsExcel() throws Exception {
		SearchResult search = new SearchResult(driver, logger);
		search.writeCoursesData();
	}
	

	/**********************************************
	 ******* Data Provider for Search Course data ********
	 **********************************************/
	@DataProvider
	public Object[][] courseData() throws IOException {
		HashMap<String, ArrayList<String>> dataMap = FileIO.readExcelData("SearchCourse");
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
		report.flush();
		driver.quit();
	}

}
