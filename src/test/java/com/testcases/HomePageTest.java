package com.testcases;

import org.testng.annotations.Test;

import org.testng.annotations.Test;

import com.base.WebApplicationBase;
import com.utilities.ReadExcelFile;
//import com.base.WebApplicationBase;
import com.utilities.StringManipulation;

import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends WebApplicationBase {
	
	
	StringManipulation stringManipulation = new StringManipulation();

	
	@Test(dataProviderClass =ReadExcelFile.class,dataProvider = "testdata" )
	public void testflightBooking(String departParamDate, String returnParamDate)throws InterruptedException {

		
		String departureDateInput = departParamDate;
		String returnDateInput = returnParamDate;

		String departureJourneyMonthYear = stringManipulation.getStringWithIndex(departureDateInput, 2);
		String departureJourneyDate = stringManipulation.getStringWithIndex(departureDateInput, 0, 2);

		System.out.println("The string indexed " + departureJourneyMonthYear);
		String returnJourneyMonthYear = stringManipulation.getStringWithIndex(returnDateInput, 2);
		String returnJourneyDate = stringManipulation.getStringWithIndex(returnDateInput, 0, 2);
		System.out.println("The return Journey date is after manipulation " + returnJourneyDate);
		System.out.println("The string indexed " + returnJourneyMonthYear);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.
		xpath(locatorProperties.getProperty("accept_btn")))));
		 driver.findElement(By.xpath(locatorProperties.getProperty("accept_btn"))).click();
		 
		 Thread.sleep(3000);
		 
		 
		 
		 try {
				WebElement flightsLink = driver
						.findElement(By.xpath(locatorProperties.getProperty("flights_rdio_btn")));
				flightsLink.click();
			//	Thread.sleep(5000);

				WebElement modalDialogue = driver.findElement(By.xpath(locatorProperties.getProperty("modaldialogue_btn")));
				if (modalDialogue.isDisplayed()) {
					wait.until(ExpectedConditions
							.elementToBeClickable(driver.findElement(By.xpath(locatorProperties.getProperty("modaldialogue_btn")))));
					driver.findElement(By.xpath(locatorProperties.getProperty("modaldialogue_btn"))).click();
				}

				WebElement roundTripRadio = driver.findElement(By.xpath(locatorProperties.getProperty("roundtrip_rdio_btn")));
				roundTripRadio.click();
			//	Thread.sleep(4000);

				WebElement fromLocation = driver.findElement(By.id(locatorProperties.getProperty("fromCity_Input_id")));
				fromLocation.sendKeys("HYD");
				driver.findElement(By.xpath(locatorProperties.getProperty("Hyderabad_fromcity_dropdown"))).click();
			//	Thread.sleep(5000);

				WebElement toLocation = driver.findElement(By.id(locatorProperties.getProperty("toCity_Input_id")));
				toLocation.click();
				toLocation.sendKeys("MAA");
				driver.findElement(By.xpath(locatorProperties.getProperty("chennai_fromcity_dropdown"))).click();
			//	Thread.sleep(2000);

				System.out.println("==========> Start of Date picker");
				
				
				int i = 0;
				label1: do {
					List<WebElement> departureMonths = driver
							.findElements(By.xpath(locatorProperties.getProperty("departureMonths")));
					System.out.println(departureMonths.size());
					if (departureMonths.size() > 0) {
						for (WebElement month : departureMonths) {
							System.out.println("The text is " + month.getText());
							i = i + 1;
							System.out.println("The j values is " + i);
							if (month.getText()
									.equalsIgnoreCase(stringManipulation.getStringWithoutSpace(departureJourneyMonthYear))) {
								month.click();

								WebElement elem = driver.findElement(By.xpath("//*[@class='DayPicker-Month'][" + i
										+ "]//div[@class='DayPicker-Day']//p[text()='" + departureJourneyDate
										+ "']/ancestor::div[@class='DayPicker-Day' and contains(@aria-label,'"
										+ departureJourneyDate + "')]"));
								elem.click();
					//			Thread.sleep(2000);
								i = 0;
								System.out.println("The i value after resetting to 0 is " + i);
								break label1;

							}
						}
					}
					if (i >= departureMonths.size()) {
						System.out.println("Im inside the departure month navigation....");
						WebElement toMonth = driver.findElement(By.xpath(locatorProperties.getProperty("toMonth")));
						toMonth.click();
						i = 0;
					//	Thread.sleep(2000);
					}
				} while (true);
				

				System.out.println("=================== Return date picker ===== ");

				int j = 0;
				label1: do {
					List<WebElement> returnMonths = driver
							.findElements(By.xpath(locatorProperties.getProperty("returnMonths")));
					System.out.println(returnMonths.size());
					if (returnMonths.size() > 0) {
						for (WebElement month : returnMonths) {
							System.out.println("The text is " + month.getText());
							j = j + 1;
							System.out.println("The j values is " + j);
							if (month.getText()
									.equalsIgnoreCase(stringManipulation.getStringWithoutSpace(returnJourneyMonthYear))) {
								month.click();

								WebElement elem = driver.findElement(By.xpath("//*[@class='DayPicker-Month'][" + j
										+ "]//div[@class='DayPicker-Day']//p[text()='" + returnJourneyDate
										+ "']/ancestor::div[@class='DayPicker-Day' and contains(@aria-label,'"
										+ returnJourneyDate + "')]"));
								elem.click();
						//		Thread.sleep(2000);
								j = 0;
								System.out.println("The j value after resetting to 0 is " + j);
								break label1;

							}
						}
					}
					if (j >= returnMonths.size()) {
						System.out.println("Im inside the month navigation....");
						WebElement toMonth = driver.findElement(By.xpath(locatorProperties.getProperty("toMonth")));
						toMonth.click();
						j = 0;
					//	Thread.sleep(2000);
					}
				} while (true);


				Thread.sleep(3000);
				WebElement searchButton = driver.findElement(By.xpath(locatorProperties.getProperty("searchButton")));
				searchButton.click();

				//wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.urlContains("search"));

				

				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(locatorProperties.getProperty("okayMessage")))));
				Assert.assertEquals(true, driver.findElement(By.xpath("//*[text()='OKAY, GOT IT!']")).isDisplayed());
				

			} catch (NoSuchElementException e) {
				e.printStackTrace();
				System.out.println("NoSuchElementException occurred and Captured. Search Page not displayed---> " + e);
				Assert.fail(e.getMessage(), e );
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception occurred and Captured. Search Page not displayed---> " + e);
				Assert.fail(e.getMessage(), e );
			} 
	}

}
