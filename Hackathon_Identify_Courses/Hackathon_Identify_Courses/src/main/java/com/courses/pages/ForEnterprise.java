package com.courses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.courses.base.BaseUI;
import com.courses.utils.FileIO;

public class ForEnterprise extends BaseUI {
	By products = getLocator("products_xpath");
	By campus = getLocator("forCampus_xpath");
	By fname = getLocator("firstname_xpath");
	By lname = getLocator("lastname_xpath");
	By jfunction = getLocator("jobFunction_xpath");
	By jtitle = getLocator("jobTitle_xpath");
	By email = getLocator("workEmail_xpath");
	By number = getLocator("phoneNumber_xpath");
	By institute = getLocator("institution_xpath");
	By institute_type = getLocator("institutionType_xpath");
	By discipline = getLocator("primaryDiscipline_xpath");
	By Country = getLocator("country_xpath");
	By State = getLocator("state_xpath");
	By transform = getLocator("transformtext_xpath");
	By submitBtn = getLocator("submit_xpath");
	By alert = getLocator("alert_xpath");
	By alert_success = getLocator("alertSuccess_xpath");
	By scroll = getLocator("scroll_xpath");

	public static String formResponse;

	public ForEnterprise(WebDriver driver, ExtentTest logger) {
		BaseUI.driver = driver;
		BaseUI.logger = logger;
	}

	/*********** Get title of the current page ***********/
	public String getCurrentPageTitle() {
		String title = getTitle();
		return title;
	}

	/*********** Get alert message after entering data into form ***********/
	public static void alertMessage(By locator1, By locator2) {
		String present;
		try {
			String result = locator1.findElement(driver).getText();
			formResponse = result;
			System.out.println(result);
			present = "Please enter valid details.";
		} catch(NoSuchElementException e)
		{
			String res = locator2.findElement(driver).getText();
			formResponse = res;
			System.out.println(res);
			present = "You have entered valid details";
		}
		System.out.println(present);
	}

	/***********Get For Campus page************/
	public void getForCampus() {
		hover(products, campus);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		switchToNewTab();
	}

	/*********** Enter data into form ***********/
	public void getForm(String firstname, String lastname, String jobfunction, String jobtitle, String emailid, String mob, String instname, String insttype, String dis, String country, String state, String trans) {

		scrollDown(scroll);
		sendText(fname, firstname, "First Name");
		sendText(lname, lastname, "Last Name");
		selectElement(jfunction, jobfunction, "Job Function");
		sendText(jtitle, jobtitle, "Job Title");
		sendText(email, emailid, "Work Email");
		sendText(number, mob, "Phone Number");
		sendText(institute, instname, "Institution name");
		selectElement(institute_type, insttype, "Institution Type");
		selectElement(discipline, dis, "Primary Discipline");
		selectElement(Country, country, "Country");
		selectElement(State, state, "State");
		sendText(transform, trans, "Tell us");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clickOn(submitBtn, 20,"Submit Button");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		alertMessage(alert,alert_success);
	}

	/*********** Enter data into form : overloaded for empty mobile number***********/
	public void getForm(String firstname, String lastname, String jobfunction, String jobtitle, String emailid, String instname, String insttype, String dis, String country, String state, String trans) {
		scrollDown(scroll);
		sendText(fname, firstname, "First Name");
		sendText(lname, lastname, "Last Name");
		selectElement(jfunction, jobfunction, "Job Function");
		sendText(jtitle, jobtitle, "Job Title");
		sendText(email, emailid, "Work Email");
		sendText(institute, instname, "Institution name");
		selectElement(institute_type, insttype, "Institution Type");
		selectElement(discipline, dis, "Primary Discipline");
		selectElement(Country, country, "Country");
		selectElement(State, state, "State");
		sendText(transform, trans, "Tell us");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clickOn(submitBtn, 20,"Submit Button");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		alertMessage(alert,alert_success);

	}

	/*********** Write form output into Excel ***********/
	public void writeFormOutputData(int row) throws Exception{
		FileIO.writeExcel(formResponse,"Form Response","FormOutput", row);
	}
}
