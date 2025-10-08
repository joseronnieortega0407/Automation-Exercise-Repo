package com.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TC2 {
	
	private WebDriver driver;

	@Before
	public void  setUp() {
		WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	public static void slowType(WebElement element, String text, int delayInMillis) throws InterruptedException {
        for (char c : text.toCharArray()) {
            element.sendKeys(Character.toString(c));
        }
    }
    
    public static void slowClick(WebElement element, int delayInMillis) throws InterruptedException {
        Thread.sleep(delayInMillis); // wait before click
        element.click();
    }

	
	
    @SuppressWarnings("CallToPrintStackTrace")
	@Test
	public void testUserLogin() throws InterruptedException {
			
		driver.get("https://automationexercise.com/");
		Assert.assertTrue(driver.getTitle().contains("Automation Exercise"));
	         
	    WebElement signupLoginBtn = driver.findElement(By.xpath("//a[@href='/login']"));
	    signupLoginBtn.click();
	         
	    slowType(driver.findElement(By.xpath("//input[@data-qa='login-email']")), "JohnGabrielDelaCruz@example.com", 50);
	    slowType(driver.findElement(By.xpath("//input[@data-qa='login-password']")), "Test@123", 50);
	         
	    driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();
	    driver.findElement(By.xpath("//a[@href='/logout']")).click();
		
	         
	    driver.findElement(By.xpath("//a[@href='/login']")).click();
	         
	    slowType(driver.findElement(By.xpath("//input[@data-qa='login-email']")), "JohnGabrielDelaCruz@example.com", 50);
	    slowType(driver.findElement(By.xpath("//input[@data-qa='login-password']")), "Test@123", 50);
	    driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();
	         
	    driver.findElement(By.xpath("//a[@href='/delete_account']")).click();
	         
	    WebElement accountDeleted = driver.findElement(By.xpath("//b[text()='Account Deleted!']"));
	    Assert.assertTrue(accountDeleted.isDisplayed());
	    

	    // Step 15: Click 'Continue'
	    driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();

	         
	    System.out.println("");
	}
	         
        @After
		public void tearDown(){
        	if (driver != null){
			driver.quit();
		}
	}
}
