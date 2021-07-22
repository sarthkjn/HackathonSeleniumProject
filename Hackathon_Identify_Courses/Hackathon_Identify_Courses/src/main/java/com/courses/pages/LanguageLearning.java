package com.courses.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import com.courses.base.BaseUI;
import com.courses.utils.FileIO;

public class LanguageLearning extends BaseUI {
	public static List<WebElement> languagesNamesList;
	public static List<WebElement> languagesCountsList;
	public static List<WebElement> levelsNamesList;
	public static List<WebElement> levelsCountsList;
	public static List<String> languagesData = new ArrayList<String>();
	public static List<String> levelsData = new ArrayList<String>();
	
	By languageDropBox = getLocator("languageDropBox_xpath");
	By levelDropBox = getLocator("levelDropBox_xpath");
	By languagesOptionsName = getLocator("languagesOptionsName_xpath");
	By languagesOptionsCount = getLocator("languagesOptionsCount_xpath");
	By levelOptionsName = getLocator("levelOptionsName_xpath");
	By levelOptionsCount = getLocator("levelOptionsCount_xpath");
	By showAllLanguage = getLocator("showAllLanguage_xpath");
	By closeAllLanguageBox = getLocator("closeAllLanguageBox_xpath");
	
	public LanguageLearning(WebDriver driver, ExtentTest logger) {
		BaseUI.driver = driver;
		BaseUI.logger = logger;
	}
	
	public String getLanguagePageTitle(){
		String title = getTitle();
		return title;
	}
	
	/************************** Open Language DropBox **************************/
	public void openLanguageDropBox() {
		clickOn(languageDropBox, 20,"Language DropBox");
	}
	
	/*********************** Get Languages names and counts **********************/
	public void getLanguagesOptions() {
		languagesNamesList = getListOfElements(languagesOptionsName);
		languagesCountsList = getListOfElements(languagesOptionsCount);
	}
	
	/************************** Open Level DropBox **************************/
	public void openLevelDropBox() {
		clickOn(levelDropBox, 20,"Level DropBox");
	}
	
	/************************** Get Levels names and counts **************************/
	public void getLevelsOptions() {
		levelsNamesList = getListOfElements(levelOptionsName);
		levelsCountsList = getListOfElements(levelOptionsCount);
	}
	
	/************************** Click Show all in Languages DropBox **************************/
	public void clickShowAll() {
		clickOn(showAllLanguage, 20,"Show All Option");
	}
	
	/************************** Close the Languages Box Overlay **************************/
	public void closeShowAllBox() {
		clickOn(closeAllLanguageBox, 20,"Close Icon");
	}
	
	/************************** Get all languages Count **************************/
	public void getLanguagesCount() {
		System.out.println("Number of courses available for each language:");
		for(int i = 0; i < languagesNamesList.size(); ++i) {
			String languageName = getText(languagesNamesList.get(i));
			String languageCount = getText(languagesCountsList.get(i));
			languageCount = languageCount.substring(1, languageCount.length()-1);
			System.out.println(languageName + ": " + languageCount);
			String languageDataItem = languageName + ": " + languageCount;
			languagesData.add(languageDataItem);
		}
	}
	
	/************************** Get all levels Count **************************/
	public void getLevelsCount() {
		System.out.println("Number of courses available level wise:");
		for(int i = 0; i < levelsNamesList.size(); ++i) {
			String levelName = getAttribute(levelsNamesList.get(i),"value");
			String levelCount = getText(levelsCountsList.get(i));
			levelCount = levelCount.substring(1, levelCount.length()-1);
			System.out.println(levelName + ": " + levelCount);
			String levelDataItem = levelName + ": " + levelCount;
			levelsData.add(levelDataItem);
		}
	}
	
	/************************** Writing languages and levels course count in excel sheet **************************/
	public void writeCoursesData() throws Exception{
		FileIO.writeExcel(languagesData, levelsData, "Language Courses Count", "Level Courses Count","LanguageLearning");
	}
}


