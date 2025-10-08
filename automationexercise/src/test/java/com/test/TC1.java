package com.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC1 {
    private WebDriver driver;

    @Before
    public void setUp() {
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

    @Test
    public void testUserRegistration() throws InterruptedException {
        // Step 1: Go to automationexercise
        driver.get("https://automationexercise.com/");

        // Step 2: Verify home page is visible successfully
        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"));

        // Step 3: Click 'Signup / Login'
        WebElement signupLoginBtn = driver.findElement(By.xpath("//a[@href='/login']"));
        slowClick(signupLoginBtn, 500);

        // Step 4: Verify 'New User Signup!' is visible
        WebElement newUserSignup = driver.findElement(By.xpath("//h2[text()='New User Signup!']"));
        Assert.assertTrue(newUserSignup.isDisplayed());

        // Step 5: Enter name and email
        slowType(driver.findElement(By.xpath("//input[@data-qa='signup-name']")), "TestUser", 50);
        slowType(driver.findElement(By.xpath("//input[@data-qa='signup-email']")), "JohnGabrielDelaCruz@example.com", 50);

        // Step 6: Click 'Signup' button
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        // Step 7: Verify 'Enter Account Information' is visible
        WebElement accountInfo = driver.findElement(By.xpath("//b[text()='Enter Account Information']"));
        Assert.assertTrue(accountInfo.isDisplayed());

        driver.findElement(By.id("id_gender1")).click();  // Title: Mr.
        slowType(driver.findElement(By.id("password")), "Test@123", 50);

        // Select Days, Months, Years
        new Select(driver.findElement(By.id("days"))).selectByValue("10");
        new Select(driver.findElement(By.id("months"))).selectByValue("5");
        new Select(driver.findElement(By.id("years"))).selectByValue("1995");

        // Newsletter and offers
        driver.findElement(By.id("newsletter")).click();
        driver.findElement(By.id("optin")).click();

        // Fill address
        slowType(driver.findElement(By.id("first_name")), "John Gab", 50);
        slowType(driver.findElement(By.id("last_name")), "Dela Cruz", 50);
        slowType(driver.findElement(By.id("company")), "St. Jude Knights", 50);
        slowType(driver.findElement(By.id("address1")), "St. Jude", 50);
        driver.findElement(By.id("address2")).sendKeys("Suite 456");

        new Select(driver.findElement(By.id("country"))).selectByVisibleText("United States");

        slowType(driver.findElement(By.id("state")), "Pampanga", 50);
        slowType(driver.findElement(By.id("city")), "San Fernando", 50);
        slowType(driver.findElement(By.id("zipcode")), "2000", 50);
        slowType(driver.findElement(By.id("mobile_number")), "1234567890", 50);

        // Create account
        driver.findElement(By.xpath("//button[@data-qa='create-account']")).click();

        WebElement accountCreated = driver.findElement(By.xpath("//b[text()='Account Created!']"));
        Assert.assertTrue(accountCreated.isDisplayed());

        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();
        driver.findElement(By.xpath("//a[@href='/logout']")).click();

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
