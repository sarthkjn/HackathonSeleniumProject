package com.courses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.courses.base.BaseUI;

public class Homepage extends BaseUI{
	public ExtentTest logger;
	public WebDriver driver;

	By searchBox = getLocator("searchBox_xpath");
	By searchButton = getLocator("searchButton_xpath");
	By enterprise = getLocator("forEnterprise_xpath");
	
	public Homepage(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}
	
	/*********** Get title of the current page ***********/
	public String getHomeTitle(){
		String title = getTitle();
		return title;
	}

	/*********** Entering in Search Box ***********/
	public void searchBoxTyping(String data) {
		sendText(searchBox, data, "What do you want to learn?");
	}
	
	/************* Click on search button *************/
	public void clickSearch() {
		clickOn(searchButton, 20,"Search Button");		
	}

	/************* Click on 'For Enterprise' *************/	
	public void clickForEnterprise() {
		clickOn(enterprise, 20,"For Enterprise");
	}
}
