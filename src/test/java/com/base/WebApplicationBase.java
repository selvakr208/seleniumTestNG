
package com.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.FileReader;
import java.util.Properties;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebApplicationBase {

	public static WebDriver driver;
	public static Properties configProperties = new Properties();
	public static Properties locatorProperties = new Properties();
	public static FileReader configFileReader;
	public static FileReader locatorsReader;
	

	
	@BeforeMethod
	public void setUp() throws IOException {
		
		System.out.println("Before Suite is invoked....");

		if (driver == null) {
			
			System.out.println(" The Path is"+System.getProperty("user.dir"));
			configFileReader = new FileReader(System.getProperty("user.dir")+"/configfiles/config.properties");
			locatorsReader = new FileReader(System.getProperty("user.dir")+"/configfiles/locators.properties");
			
			
			configProperties.load(configFileReader);
			locatorProperties.load(locatorsReader);
			
			System.out.println("The browser is "+configProperties.getProperty("browser"));
		}

		if (configProperties.getProperty("browser").equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.get(configProperties.getProperty("testurl"));
			System.out.println("Browser launched the URL successfully...!");

		} else if (configProperties.getProperty("browser").equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.get(configProperties.getProperty("testurl"));
		} else if (configProperties.getProperty("browser").equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			driver.get(configProperties.getProperty("testurl"));
		}
	}
	
	public WebDriver getdriver() {
		return driver;
	}
	
	public void setDriver(WebDriver driver) {
		this.driver=driver;
	}


	@AfterMethod
	public void tearDown() {
		
		System.out.println("After Suite is invoked....");
		driver.close();
		driver.quit();

	}

}
