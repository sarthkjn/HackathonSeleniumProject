package com.courses.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.courses.base.BaseUI;
import com.courses.utils.FileIO;

public class SearchResult extends BaseUI{
	public ExtentTest logger;
	public WebDriver driver;
	public static List<String> Course1Data = new ArrayList<String>();
	public static List<String> Course2Data = new ArrayList<String>();

	By langselect = getLocator("langDrop_xpath");
	By engSelect = getLocator("Eng_xpath");
	By levelSelect = getLocator("levelDrop_xpath");
	By begSelect = getLocator("Beg_xpath");
	By courses = getLocator("courses_xpath");
	By nameCourse = getLocator("name_xpath");
	By totalLearning = getLocator("rating_xpath");
	By ratingCourse = getLocator("duration_xpath");
	By checklang = getLocator("checklang_xpath");
	By checklevel = getLocator("checklevel_xpath");
	
	public SearchResult(WebDriver driver,ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}
	
	/*********** Get Search Page Title ***********/
	public String getSearchPageTitle(){
		String title = getTitle();
		return title;
	}
	
	/*********** Filter By Language ***********/
	public void filterByLanguage() {
		clickOn(langselect, 20, "Language DropBox");
	}
	
	/*********** Select English Language ***********/
	public void selectEnglish() {
		clickOn(engSelect, 20, "English CheckBox");
	}
	
	/*********** Filter By Level ***********/
	public void filterByLevel() {
		clickOn(levelSelect, 20, "Level DropBox");
	}
	
	/*********** Select Beginner Level ***********/
	public void selectBeginner() {	
		clickOn(begSelect, "Beginner CheckBox");
	}

	/************* Check language selected **********/
	public String checkLang() {
		String lang = getText(checklang, "checkLang_xpath");
		return lang;
	}
	
	/************* Check level selected **********/
	public String checkLevel() {
		String level = getText(checklevel, "checkLevel_xpath");
		return level;
	}

	/*********** Select Course ***********/
	public void selectCourse(int no) {
		selectListValue(courses, no, 20, "Courses List");
	}
	
	/*********** Print course Details  ***************/
	public void printcourseDetails(int courseno)
	{
		switchToNewTab();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String name = getText(nameCourse, "course" + courseno + "Name_xpath");
		String rating = getText(totalLearning, "course" + courseno + "Rating_xpath");
		String hours = getText(ratingCourse, "course" + courseno + "Hours_xpath");
		System.out.println("Name of Course:- "+name+"  Rating:-"+rating+"  Total Hours:- "+ hours);
		if(courseno == 1) {
			Course1Data.add("Name: "+name);
			Course1Data.add("Rating: "+rating);
			Course1Data.add("Hours: "+hours);
		}
		else {
			Course2Data.add("Name: "+name);
			Course2Data.add("Rating: "+rating);
			Course2Data.add("Hours: "+hours);
		}
		switchToPrevTab();
	}
	
	/*********** Write top 2 courses data in excel ***********/
	public void writeCoursesData() throws Exception{
		FileIO.writeExcel(Course1Data, Course2Data, "Course 1 Data", "Course 2 Data","CoursesDetails");
	}

}

