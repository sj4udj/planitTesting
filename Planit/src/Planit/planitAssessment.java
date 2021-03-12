package Planit;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class planitAssessment {
	WebDriver driver; 
  @Test 
  public void launchBrowser() throws Exception 
  {
	  utilityReader config=new utilityReader();  
	  
	  ChromeOptions option = new ChromeOptions();
	  option.addArguments("--Start-Maximized");
	  System.setProperty("webdriver.chrome.driver",config.chromPath());
	  driver = new ChromeDriver(option);
	  Thread.sleep(1000);
	  driver.get(config.urlPath());
	  driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	  driver.findElement(By.xpath("//*[contains(text(), 'Log in')]")).click();
	  
	  //Validate “Welcome, Please Sign In!” message
	  WebElement actuall=driver.findElement(By.xpath("//*[contains(text(), 'Welcome, Please Sign In!')]"));
	  String welcome=actuall.getText();
	  Thread.sleep(1000);
	  if(welcome.equals("Welcome, Please Sign In!")){
		  System.out.println("Validation Test Passed" + "\n");
	  }
	  else {
		  System.out.println("\n" + "Validation Test Failed" + "\n");
	  }
  }
  
  @Test(dependsOnMethods = "launchBrowser")
  public void loginCredentials() throws Exception {
	  utilityReader config=new utilityReader();
	  
	  //Log in with given credentials
	  driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	  driver.findElement(By.xpath(config.userXpath())).sendKeys(config.userName());
	  driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	  driver.findElement(By.xpath(config.passXpath())).sendKeys(config.password());
	  driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	  driver.findElement(By.xpath("(//*[contains(@value, 'Log in')])[1]")).click();
	  
	  // Validate the user account ID on top right
	  WebElement validateUserID=driver.findElement(By.xpath("//*[contains(@class,'header-links-wrapper')]/descendant::*[4]"));
	  String validUser=validateUserID.getText();
	  if(validUser.equalsIgnoreCase(config.userName())) {
		  System.out.println("userID Test Passed" + "\n");
	  }
	  else {
		  System.out.println("userID Test Failed" + "\n");
	  }
  }
  
  @Test(dependsOnMethods = "loginCredentials")
  public void shoppingCart() throws Exception {
	  driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	  driver.findElement(By.xpath("(//*[contains(text(), 'Shopping cart')])[1]")).click();
	  Thread.sleep(1000);
	  try {
			  WebElement shopclear1 = driver.findElement(By.xpath("(//*[contains(@class, 'cart-item-row')])/descendant::*[13]"));
			  shopclear1.click();
			  shopclear1.clear();
			  shopclear1.sendKeys("0");
			  shopclear1.sendKeys(Keys.ENTER);
			  System.out.println("Clear First Quality" + "\n" );
			  Thread.sleep(1000);
	}catch(Exception e) {
		
	}
	  try {
		  selectBook();
  }catch(Exception e) {
	  System.out.println("Error");
  }
 }
  @Test(dependsOnMethods = "shoppingCart")
  public void addBooktoCart() throws Exception {  
	  WebElement clearAddCarData= driver.findElement(By.xpath("(//*[contains(@class, 'add-to-cart-panel')])/descendant::*[2]"));
	  clearAddCarData.click();
	  clearAddCarData.clear();
	  clearAddCarData.sendKeys("2");
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("(//*[contains(@class, 'add-to-cart-panel')])/descendant::*[4]")).click();
	  Thread.sleep(2000);
	  WebElement addProductMessage=driver.findElement(By.xpath("//*[contains(text(), 'The product has been added to your')]"));
	  System.out.println(addProductMessage.getText() + "\n");
	  Thread.sleep(2000);
	  String addedCart=addProductMessage.getText();
	  Thread.sleep(1000);
	  if(addedCart.equalsIgnoreCase("The product has been added to your shopping cart")) {
		 System.out.println("Product has been added to shopping cart Successfully" + "\n");
	  }
	  else {
		  System.out.println("Product Not added to shopping cart" + "\n");
	  }
  }
  
  @Test(dependsOnMethods = "addBooktoCart")
  public void toprightShoppingCart() throws Exception {
	  WebElement subTotal=driver.findElement(By.xpath("(//*[contains(text(), 'Shopping cart')])[1]"));
	  subTotal.click();
	  
	  WebElement subTotalPrice=driver.findElement(By.xpath("(//*[contains(text(), 'Sub-Total:')])[2]"));
	  String string=subTotalPrice.getText();
	  
	  WebElement subTot1=driver.findElement(By.xpath("(//*[contains(text(), 'Total')])[3]/following::*[1]"));
	  String substring1=subTot1.getText();
	  Thread.sleep(2000);
	  WebElement subTot2=driver.findElement(By.xpath("(//*[contains(text(), 'Sub-Total:')])[2]/following::*[3]"));
	  String substring2=subTot2.getText();
	  
	  if(substring1.equals(substring2)) {
		  System.out.println("Sub-Total Price Price of the Book : " + substring2 + "\n");
	  }else {
		  System.out.println("Different Sub Total Price Price of the Book " + "\n");
	  }
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("(//*[contains(@id, 'termsofservice')])")).click();
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("(//*[contains(@name, 'checkout')])")).click();
  }
  
  @Test(dependsOnMethods = "toprightShoppingCart")
  public void billingAddress() throws Exception {
	  Thread.sleep(3000);
	  
	  // Select “New Address” From “Billing Address” drop down.and Continue
	  WebElement billaddress=driver.findElement(By.xpath("(//*[contains(@id, 'billing-address-select')])"));
	  Actions a1=new Actions(driver);
	  Thread.sleep(2000);
	  a1.click(billaddress).sendKeys(Keys.DOWN).click().build().perform();
	  Thread.sleep(2000);
	  driver.findElement(By.xpath("(//*[contains(@value, 'Continue')])[1]")).click();
	  
	  // Select the “Shipping Address” as same as “Billing Address” from “Shipping Address”
	  WebElement Shippingaddress=driver.findElement(By.xpath("(//*[contains(@id, 'shipping-address-select')])"));
	  Actions a2=new Actions(driver);
	  Thread.sleep(2000);
	  a2.click(Shippingaddress).sendKeys(Keys.DOWN).click().build().perform(); 
	  Thread.sleep(2000);
	  driver.findElement(By.xpath("(//*[contains(@value, 'Continue')])[2]")).click();
	  
	  // Select the shipping method as “Next Day Air” and click on “Continue
	  driver.findElement(By.xpath("(//*[contains(@value, 'Next Day Air___Shipping.FixedRate')])")).click();
	  driver.findElement(By.xpath("(//*[contains(@value, 'Continue')])[3]")).click();
	  
	  // Choose the payment method as COD (Cash on delivery) and click on “Continue”
	  driver.findElement(By.xpath("(//*[contains(@value, 'Continue')])[4]")).click();
	  
	  // Validate the message “You will pay by COD” and click on “Continue”
	  WebElement validateMessage=driver.findElement(By.xpath("(//*[contains(text(), 'You will pay by COD')])"));
	  String payCOD=validateMessage.getText();
	  if(payCOD.equals("You will pay by COD")) {
		  System.out.println("You will pay by COD - Message displayed successfully" + "\n");
	  }else {
		  System.out.println("No message displayed - Test Failed"  + "\n");
	  }
	  driver.findElement(By.xpath("(//*[contains(@value, 'Continue')])[5]")).click();
	  
	  driver.findElement(By.xpath("(//*[contains(@value, 'Confirm')])")).click();
	  
	  WebElement successful=driver.findElement(By.xpath("(//*[contains(text(), 'Your order has been successfully processed!')])"));
	  String confirmsuccessful=successful.getText();
	  if(confirmsuccessful.equals("Your order has been successfully processed!")) {
		  System.out.println("Order Confirmed - Message displayed successfully" + "\n");
	  }else {
		  System.out.println("Order not confirmed - Test Failed" + "\n");
	  }
	  
	  WebElement orderNumerber=driver.findElement(By.xpath("//*[contains(@class, 'details')]/li[1]"));
	  System.out.println(orderNumerber.getText());
	  driver.findElement(By.xpath("(//*[contains(@value, 'Continue')])[1]")).click();
	  
	  Thread.sleep(2000);
	  driver.close();
  }
  
  public void selectBook() throws Exception {
	  driver.findElement(By.xpath("(//*[contains(text(), 'Books')])[1]")).click();
	  Thread.sleep(2000);
	  WebElement scrolltillbook = driver.findElement(By.xpath("(//*[contains(text(), 'Fiction')])[1]"));
	  Thread.sleep(2000);
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeScript("arguments[0].scrollIntoView();", scrolltillbook);
	  scrolltillbook.click();
	  
	  WebElement bookPrice=driver.findElement(By.xpath("(//*[contains(@class, 'product-price')])[2]/child::*[2]"));
	  Thread.sleep(1000);
	  String bookPriceRs=bookPrice.getText();
	  Thread.sleep(1000);
	  System.out.println("Book Price - Rs: " + bookPriceRs + "\n");
  }
 
}
