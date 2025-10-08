package com.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC3 {

    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

    }

    public static void slowType(WebElement element, String text, int delayInMillis) throws InterruptedException {
        for (char c : text.toCharArray()) {
            element.sendKeys(Character.toString(c));
        }
    }

    @Test
    public void testAddToCart() throws InterruptedException{

        // LAUNCH BROWSER
        driver.get("https://automationexercise.com/");
	    driver.manage().window().maximize();
        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"));

        // ADD PRODUCT
        WebElement product4Details = driver.findElement(By.xpath("//a[@href='/product_details/4']"));
        product4Details.click();

        // ADD QUANTITY OF PRODUCT
        WebElement spinner = driver.findElement(By.id("quantity"));
        int target = 10;
        int current = Integer.parseInt(spinner.getAttribute("value"));

        while (current < target) {
            spinner.sendKeys(Keys.ARROW_UP);
            current = Integer.parseInt(spinner.getAttribute("value"));
        }

        while (current > target) {
            spinner.sendKeys(Keys.ARROW_DOWN);
            current = Integer.parseInt(spinner.getAttribute("value"));
        }

        // ADD TO CART
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'btn btn-default cart')]")));
        addToCartBtn.click();

        // VIEW CART
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View Cart"))).click();
        
        // DISPLAY THE PRODUCT QUANTITY
        WebElement cartQuantity = driver.findElement(By.xpath("//td[@class='cart_quantity']//button"));
        int cartValue = Integer.parseInt(cartQuantity.getText().trim());
        Assert.assertEquals("Cart quantity did not match spinner value!", target, cartValue);
        
        // PROCEED TO CHECKOUT
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Proceed To Checkout']"))).click();

        // CLICK LOGIN/SIGNIN & INPUT DETAILS
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//u[text()='Register / Login']"))).click();
        slowType(driver.findElement(By.xpath("//input[@data-qa='login-email']")), "johncruz@example.com", 50);
	    slowType(driver.findElement(By.xpath("//input[@data-qa='login-password']")), "12345678", 50);
	    driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

        // CLICK CART
        driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();

        // CLICK PROCEED AGAIN
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Proceed To Checkout']"))).click();

        // 14. Verify Address Details and Review Your Order
        WebElement addressDetails = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Address Details']")));
        WebElement reviewOrder = driver.findElement(By.xpath("//h2[text()='Review Your Order']"));
        Assert.assertTrue(addressDetails.isDisplayed());
        Assert.assertTrue(reviewOrder.isDisplayed());

        driver.findElement(By.xpath("//a[text()='Place Order']")).click();

        // TYPE CARD INFO
        slowType(driver.findElement(By.name("name_on_card")), "John-Dela-Cruz", 50);
        slowType(driver.findElement(By.name("card_number")), "123456789", 50);
        slowType(driver.findElement(By.name("cvc")), "311", 50);
        slowType(driver.findElement(By.name("expiry_month")), "aug", 50);               
        slowType(driver.findElement(By.name("expiry_year")), "2050", 50);

        // SUBMIT
        driver.findElement(By.id("submit")).click();

        // Verify success message
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Congratulations! Your order has been confirmed!')]")));
        Assert.assertTrue("Order not placed!", successMsg.isDisplayed());

        driver.findElement(By.xpath("//a[@href='/']")).click();
        driver.findElement(By.xpath("//a[@href='/logout']")).click();


    } 
        
    @After
        public void tearDown(){
        	if (driver != null){
			driver.quit();
		}
    }
}
