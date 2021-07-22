package com.courses.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.courses.utils.DateUtils;
import com.courses.utils.ExtentReportManager;
import com.courses.utils.FileIO;

public class BaseUI {
	public static WebDriver driver;
	public static ExtentTest logger;
	public static Properties prop;
	public static String browser_choice;
	public static String timestamp = DateUtils.getTimeStamp();
	public ExtentReports report = ExtentReportManager.getReportInstance();


	public BaseUI() {
		prop = FileIO.initProperties();
	}

	/************** Invoke Browser ****************/
	public static WebDriver invokeBrowser() {
		browser_choice = prop.getProperty("browserName");
		try {
			if (browser_choice.equalsIgnoreCase("firefox")) {
				driver = DriverSetup.getFirefoxDriver();
			} 
			else if (browser_choice.equalsIgnoreCase("chrome")) {
				driver = DriverSetup.getChromeDriver();
			} else {
				throw new Exception("Invalid browser name provided in property file");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	/************** Get browser option from user ****************/
	public static int getBrowserOption() {
		int choice = 1;
		System.out.println("Browser options\n1 - Chrome\n2 - Firefox\nEnter choice: ");
		Scanner sc = new Scanner(System.in);

		choice = sc.nextInt();
		while (choice != 1 && choice != 2 && choice != 3) {
			System.out.println("Invalid choice entered.");
			System.out.println("Browser options\n1 - Chrome\n2 - Firefox\nEnter choice: ");
			choice = sc.nextInt();
		}
		sc.close();
		return choice;
	}

	/************** Open website URL ****************/
	public static void openBrowser(String websiteUrlKey) {
		try {
			driver.get(prop.getProperty(websiteUrlKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/************** Get title of web page ****************/
	public static String getTitle() {
		String title = "default";
		try {
			title = driver.getTitle();
			logger.log(Status.INFO,"Title Fetched: " + title);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		return title;
	}

	/************** Switch to new tab ****************/
	public static void switchToNewTab() {
		try {
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(tabs.size() - 1));
			logger.log(Status.INFO,"Switched to next tab");
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Switch to previous tab ****************/
	public static void switchToPrevTab() {
		try {
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.close();
			driver.switchTo().window(tabs.get(tabs.size() - 2));
			logger.log(Status.INFO,"Switched to previous tab");
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Get list of web elements ****************/
	public static List<WebElement> getListOfElements(By locator) {
		List<WebElement> list = null;
		try {
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(locator));
			list = driver.findElements(locator);
		}  catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		return list;
	}

	/************** Check if an element is present ****************/
	public static boolean isElementPresent(By locator, int timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
			return false;
		}
	}

	/************** Send text to an element ****************/
	public static void sendText(By locator, String text, String locatorKey) {
		try {
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(locator));
			driver.findElement(locator).sendKeys(text);
			logger.log(Status.PASS, "'" + text + "'" + " entered into TextBox: " + locatorKey);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Get text of element ****************/
	public static String getText(By locator, String locatorKey) {
		String text = null;
		try {
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(locator));
			text = driver.findElement(locator).getText();
			logger.log(Status.PASS, "'" + text + "'" + " fetched from locator: " + locatorKey);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		return text;
	}

	/************** Get text of element : Overloaded for web element****************/
	public static String getText(WebElement element) {
		return element.getText();
	}

	/************** Get attribute value of element****************/
	public static String getAttribute(WebElement element, String attributeName) {
		return element.getAttribute(attributeName);
	}

	/************** Click on element****************/
	public static void clickOn(By locator, int timeout,String locatorKey) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(locator));
			driver.findElement(locator).click();
			logger.log(Status.PASS, "Clicked: "+locatorKey);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Click on element: overloaded without timeout****************/
	public static void clickOn(By locator, String locatorKey) {
		try {
			driver.findElement(locator).click();
			logger.log(Status.PASS, "Clicked: "+locatorKey);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	
	/**************** Get By locator using locator key ****************/
	public static By getLocator(String locatorKey) {
		try {
			if (locatorKey.endsWith("_id")) {
				logger.log(Status.INFO, "Locator Identidied: " + locatorKey);
				return By.id(prop.getProperty(locatorKey));			
			}
			if (locatorKey.endsWith("_name")) {
				logger.log(Status.INFO, "Locator Identidied: " + locatorKey);
				return (By.name(prop.getProperty(locatorKey)));
			}
			if (locatorKey.endsWith("_className")) {
				logger.log(Status.INFO, "Locator Identidied: " + locatorKey);
				return (By.className(prop.getProperty(locatorKey)));
			}
			if (locatorKey.endsWith("_xpath")) {
				logger.log(Status.INFO, "Locator Identidied: " + locatorKey);
				return (By.xpath(prop.getProperty(locatorKey)));
			}
			if (locatorKey.endsWith("_css")) {
				logger.log(Status.INFO, "Locator Identidied: " + locatorKey);
				return (By.cssSelector(prop.getProperty(locatorKey)));
			}
			if (locatorKey.endsWith("_linkText")) {
				logger.log(Status.INFO, "Locator Identidied: " + locatorKey);
				return (By.linkText(prop.getProperty(locatorKey)));
			}
			if (locatorKey.endsWith("_partialLinkText")) {
				logger.log(Status.INFO, "Locator Identidied: " + locatorKey);
				return (By.partialLinkText(prop.getProperty(locatorKey)));
			}
			if (locatorKey.endsWith("_tagName")) {
				logger.log(Status.INFO, "Locator Identidied: " + locatorKey);
				return (By.tagName(prop.getProperty(locatorKey)));
			}
		} catch (Exception e) {
			reportFail("Couldn't locate locator: " + locatorKey);		
		}
		return null;		
	}

	/************** List Value Select ****************/
	public static void selectListValue(By locator, int position, int timeout, String locatorKey) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(locator));
			List<WebElement> li = driver.findElements(locator);
			li.get(position).click();
			logger.log(Status.PASS, "Index " + position + " item selected from: " + locatorKey);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Mouse hover actions ****************/
	public static void hover(By locator, By locator2) {
		try {
			Actions action = new Actions(driver);
			WebElement element = locator.findElement(driver);
			WebElement element2 = locator2.findElement(driver);
			action.moveToElement(element);
			action.moveToElement(element2).click().build().perform();
			logger.log(Status.PASS, "Mouse hover action successful");
		} catch (Exception  e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Scroll Down ****************/
	public static void scrollDown(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			logger.log(Status.INFO, "Window Scroll successful");
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** select element by visible text ****************/
	public static void selectElement(By locator, String str2, String locatorKey) {
		try {
			Select s = new Select(driver.findElement(locator));
			s.selectByVisibleText(str2);
			logger.log(Status.PASS, "'" + str2 + "'" + " selected from DropBox: " + locatorKey);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}
	
	/************** Take screenshot on failure****************/
	public static void  takeScreenShotOnFailure() {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		String path = System.getProperty("user.dir") + "//ScreenShots//" + DateUtils.getTimeStamp() + ".png";
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(path);
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/************** Report fail test ****************/
	public static void reportFail(String reportMessage) {
		logger.log(Status.FAIL, reportMessage);
		takeScreenShotOnFailure();
		Assert.fail("Test case failed: " + reportMessage);
	}
}
