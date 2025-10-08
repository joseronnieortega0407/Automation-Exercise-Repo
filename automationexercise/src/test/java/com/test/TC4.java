package com.test;

import java.time.Duration;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC4 {
    
    private static WebDriver driver;
    private static WebDriverWait wait;


    @BeforeClass
    public static void setUp () {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static void slowType(WebElement element, String text, int delayInMillis) throws InterruptedException {
        for (char c : text.toCharArray()) {
            element.sendKeys(Character.toString(c));
        }
    }

    @Test
    public void testProductSearch() throws InterruptedException {
        driver.get("https://automationexercise.com/");
        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"));

        driver.findElement(By.xpath("//a[@href='/products']")).click();

        //VERIFY ALL PRODUCTS PAGE
        WebElement allProductsMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'All Products')]")));
        Assert.assertTrue("All Products title is NOT visible!", allProductsMsg.isDisplayed());
        System.out.println("~ALL PRODUCT TITLE IS VISIBLE");

        //SEARCH ITEM
        slowType(driver.findElement(By.id("search_product")), "Blue Cotton Indie Mickey Dress", 50);
        driver.findElement(By.id("submit_search")).click();
        driver.findElement(By.id("search_product")).clear();
        slowType(driver.findElement(By.id("search_product")), "shirt", 50);
        driver.findElement(By.id("submit_search")).click();

        //VERIFY SEARCH PRODUCTS IS VISIBLE
        WebElement searchedProductsTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Searched Products')]")));
        Assert.assertTrue("Search results are not visible!", searchedProductsTitle.isDisplayed());
        System.out.println("~SEARCH PRODUCTS ARE VISIBLE");
        System.out.println("~ALL SEARCH PRODUCTS ARE VISIBLE");


        WebElement addToCartBtn = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-product-id='18' and contains(@class,'add-to-cart')]")));
        addToCartBtn.click();

        WebElement viewCart = wait.until(
        ExpectedConditions.elementToBeClickable(By.linkText("View Cart")));
        viewCart.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Proceed To Checkout']"))).click();
        
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//u[text()='Register / Login']"))).click();
        slowType(driver.findElement(By.xpath("//input[@data-qa='login-email']")), "johncruz@example.com", 50);
	    slowType(driver.findElement(By.xpath("//input[@data-qa='login-password']")), "12345678", 50);
	    driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

        // CLICK CART
        driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Proceed To Checkout']"))).click();

        WebElement addressDetails = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Address Details']")));
        WebElement reviewOrder = driver.findElement(By.xpath("//h2[text()='Review Your Order']"));
        Assert.assertTrue(addressDetails.isDisplayed());
        Assert.assertTrue(reviewOrder.isDisplayed());

        driver.findElement(By.xpath("//a[text()='Place Order']")).click();

        slowType(driver.findElement(By.name("name_on_card")), "John-Dela-Cruz", 50);
        slowType(driver.findElement(By.name("card_number")), "123456789", 50);
        slowType(driver.findElement(By.name("cvc")), "311", 50);
        slowType(driver.findElement(By.name("expiry_month")), "aug", 50);               
        slowType(driver.findElement(By.name("expiry_year")), "2050", 50);

        driver.findElement(By.id("submit")).click();

        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Congratulations! Your order has been confirmed!')]")));
        Assert.assertTrue("Order not placed!", successMsg.isDisplayed());

        driver.findElement(By.xpath("//a[@href='/']")).click();
        driver.findElement(By.xpath("//a[@href='/logout']")).click();

    }



    @AfterClass
    public static void tearDown(){
        if (driver != null){
			driver.quit();
		}
    }
    
}
