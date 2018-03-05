package orderitemworkflow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class OrderItemMakePayment {

	private WebDriver webDriver;

	public boolean launchDriver()
	{
		try {
			
			// find out OS name of the system
			String os = System.getProperty("os.name").toLowerCase();
			String driverPath= System.getProperty("user.dir")+"/driver/chromedriver";
			if(os.contains("windows"))
			{
				driverPath=driverPath+".exe";
			}
			System.setProperty("webdriver.chrome.driver", driverPath);		
			
			webDriver=new ChromeDriver();
			webDriver.manage().window().maximize();
		}
		catch(Exception e){
			System.out.println("Failed to launch driver\n");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean goToURL(String url)
	{
		try
		{
			// Navigate to the URL of E-Commerce site
			webDriver.navigate().to(url);
		}
		catch(Exception e){
			System.out.println("Failed to navigate to url : "+url);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean logInAndNavigateToHomeScreen(String userName,String password)
	{

		try
		{
			//Click on Sign In button
			webDriver.findElement(By.xpath("//a[@class='login']")).click();
			//Enter user name
			webDriver.findElement(By.xpath("//input[@id='email']")).sendKeys(userName);
			//Enter password
			webDriver.findElement(By.xpath("//input[@id='passwd']")).sendKeys(password);
			//Click Sign In button
			webDriver.findElement(By.xpath("//button[@id='SubmitLogin']")).click();
			//Click on Home button for Navigate to the home screen. 
			webDriver.findElement(By.xpath("//a[@title='Home']")).click();
		}
		catch(Exception e){
			System.out.println("Failed to login with userName : "+userName+" and password : "+password);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean selectProductAndAddToCart(String productName)
	{

		try
		{
			//Click on Product name
			webDriver.findElement(By.xpath("//img[@title='"+productName+"']")).click();
			Thread.sleep(4000);
			
			WebElement fancyFrame=webDriver.findElement(By.xpath("//iframe[@class='fancybox-iframe']"));
			webDriver.switchTo().frame(fancyFrame);
			
			//Click on "Add to cart" button
			webDriver.findElement(By.xpath("//button[@name='Submit']")).click();
			Thread.sleep(2000);
			
			//Click on Proceed to checkout button
			webDriver.findElement(By.xpath("//a[@title='Proceed to checkout']")).click();
			Thread.sleep(2000);
			
			//Click Proceed to checkout button in Summary page
			webDriver.findElement(By.xpath("//a[@title='Proceed to checkout'][@style]")).click();
			Thread.sleep(2000);
			
			// Process Address
			webDriver.findElement(By.xpath("//button[@name='processAddress']")).click();
			Thread.sleep(1000);
			
			//click on I agree
			webDriver.findElement(By.xpath("//input[@id='cgv']")).click();
			Thread.sleep(1000);
			
			//Process Carrier for delivery
			webDriver.findElement(By.xpath("//button[@name='processCarrier']")).click();
			Thread.sleep(1000);
			
			// Select Payment option : Pay by bank wire
			webDriver.findElement(By.xpath("//a[@title='Pay by bank wire']")).click();
			Thread.sleep(2000);
			
			//Click on Confirm order button
			webDriver.findElement(By.xpath("//span[text()='I confirm my order']/parent::button")).click();
			Thread.sleep(1000);
			
			//Verify message for order confirmation 
			WebElement successMessage=webDriver.findElement(By.xpath("//strong[text()='Your order on My Store is complete.']"));
			Thread.sleep(1000);
			
			if(successMessage==null)
			{
				throw new Exception("Success message not prestn after placing the order");
			}
			System.out.println("Success");
			webDriver.quit();
		}
		catch(Exception e){
			System.out.println("Failed to select product with name : "+productName);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws InterruptedException {
		OrderItemMakePayment scenario=new OrderItemMakePayment();
		scenario.launchDriver();
		scenario.goToURL("http://automationpractice.com/index.php");
		scenario.logInAndNavigateToHomeScreen("soumit30@gmail.com", "admin@123");
		scenario.selectProductAndAddToCart("Blouse");
	}
}
