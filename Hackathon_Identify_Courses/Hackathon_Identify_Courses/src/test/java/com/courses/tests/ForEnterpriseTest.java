package com.courses.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.courses.base.BaseUI;
import com.courses.pages.ForEnterprise;
import com.courses.pages.Homepage;
import com.courses.utils.FileIO;

public class ForEnterpriseTest extends BaseUI {
	private WebDriver driver;
	private static int testCount = 0;
	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		driver = invokeBrowser();
		openBrowser("websiteURL");
	}
	/***************************************************
	 ******** Verify form submission with valid data ********
	 ***************************************************/
	@Test(dataProvider = "formValidData", priority = 0, groups= {"regression"})
	public void verifyFormForValidData(String firstname, String lastname, String jobfunction, String jobtitle, String email,
			String phoneno, String institutename, String institutetype, String discipline, String country, String state,
			String transform) throws Exception {
		logger = report.createTest("Verify form submission for valid data" );
		Homepage homepage = new Homepage(driver, logger);
		homepage.clickForEnterprise();
		ForEnterprise forEnterprise = new ForEnterprise(driver, logger);
		forEnterprise.getForCampus();
		forEnterprise.getForm(firstname, lastname, jobfunction, jobtitle, email, phoneno, institutename, institutetype,
				discipline, country, state, transform);
		testCount++;
		forEnterprise.writeFormOutputData(testCount);
	}
	/***************************************************
	 ******** Verify form submission with invalid data ********
	 ***************************************************/
	@Test(dataProvider = "formInvalidData", priority = 1, groups = {"regression"})
	public void verifyFormForInvalidData(String firstname, String lastname, String jobfunction, String jobtitle, String email,
			String phoneno, String institutename, String institutetype, String discipline, String country, String state,
			String transform) throws Exception {
		logger = report.createTest("Verify form submission for invalid data");
		Homepage homepage = new Homepage(driver, logger);
		homepage.clickForEnterprise();
		ForEnterprise forEnterprise = new ForEnterprise(driver, logger);
		forEnterprise.getForCampus();
		forEnterprise.getForm(firstname, lastname, jobfunction, jobtitle, email, phoneno, institutename, institutetype,
				discipline, country, state, transform);
		testCount++;
		forEnterprise.writeFormOutputData(testCount);
	}
	/************************************************************
	 ******** Verify form submission with empty data in one field ******
	 ************************************************************/
	@Test(dataProvider = "formEmptyData", priority = 2, groups= {"regression"})
	public void verifyFormForEmptyData(String firstname, String lastname, String jobfunction, String jobtitle, String email, 
			String institutename, String institutetype, String discipline, String country, String state,
			String transform) throws Exception {
		logger = report.createTest("Verify form submission for empty data");
		Homepage homepage = new Homepage(driver, logger);
		homepage.clickForEnterprise();
		ForEnterprise forEnterprise = new ForEnterprise(driver, logger);
		forEnterprise.getForCampus();
		forEnterprise.getForm(firstname, lastname, jobfunction, jobtitle, email, institutename, institutetype,
				discipline, country, state, transform);
		testCount++;
		forEnterprise.writeFormOutputData(testCount);
	}
	/***************************************************
	 ******** Data provider for valid form inputs ********
	 ***************************************************/
	@DataProvider
	public Object[][] formValidData() throws IOException {
		HashMap<String, ArrayList<String>> dataMap = FileIO.readExcelData("ForEnterpriseFormValidData");
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
	/***************************************************
	 ******** Data provider for invalid form inputs ********
	 ***************************************************/
	@DataProvider
	public Object[][] formInvalidData() throws IOException {
		HashMap<String, ArrayList<String>> dataMap = FileIO.readExcelData("ForEnterpriseFormInvalidData");
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
	}/***************************************************
	 ******** Data provider for empty input in one field ********
	 ***************************************************/
	@DataProvider
	public Object[][] formEmptyData() throws IOException {
		HashMap<String, ArrayList<String>> dataMap = FileIO.readExcelData("ForEnterpriseFormEmptyData");
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

	@AfterMethod(alwaysRun = true)
	public void afterTest() {
		driver.quit();
	}
	
	@AfterClass(alwaysRun = true)
	public void afterReport() {
		report.flush();
	}

}
