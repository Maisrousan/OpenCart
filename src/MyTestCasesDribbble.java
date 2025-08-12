import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mysql.cj.protocol.Resultset;

public class MyTestCasesDribbble {

	WebDriver driver = new ChromeDriver();
	String RegisterURL = "https://opencart.dreamvention.com/index.php?route=account/register";
	String LoginURL = "https://opencart.dreamvention.com/index.php?route=account/login";
	Connection con;
	Statement stmt;
	ResultSet rs;
	Random rand = new Random();

	String FisrtName;
	String LastName;
	String Mobile;
	
	String Email ;
	String Password = "KKvv234&";

	

	@BeforeTest
	public void MySetUp() throws SQLException {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "123456");

		String URL = "https://opencart.dreamvention.com/";

		driver.get(URL);
		driver.manage().window().maximize();

		// WebElement = driver.findElement();

	}

	@Test(priority = 1)
	public void SignUp() throws  SQLException {

		driver.navigate().to(RegisterURL);

		

	
		String QueryToRead = "SELECT contactFirstName, contactLastName, phone\r\n"
				+ "FROM customers\r\n"
				+ "WHERE customerNumber BETWEEN 103 AND 200\r\n"
				+ "ORDER BY RAND()\r\n"
				+ "LIMIT 1; ";
		stmt = con.createStatement();
		rs = stmt.executeQuery(QueryToRead);
		while (rs.next()) {

			FisrtName = rs.getNString("contactFirstName");
			LastName = rs.getNString("contactLastName");
			Mobile = rs.getNString("phone");
		}
		int RandomNum = rand.nextInt(238);
		Email = FisrtName + RandomNum+LastName + "@gmail.com";

		WebElement FirstNameInput = driver.findElement(By.id("input-firstname"));
		WebElement LastNameInput = driver.findElement(By.id("input-lastname"));
		WebElement EmailInput = driver.findElement(By.id("input-email"));
		WebElement TelephoneInput = driver.findElement(By.id("input-telephone"));
		WebElement PasswordInput = driver.findElement(By.id("input-password"));
		WebElement PasswordConfirmInput = driver.findElement(By.id("input-confirm"));
		WebElement SubscribeButton = driver.findElement(By.xpath("//input[@value='0']"));
		WebElement AgreeBox = driver.findElement(By.xpath("//input[@name='agree']"));
		WebElement RegisterButton = driver.findElement(By.cssSelector(".btn.btn-primary"));

		
		
		FirstNameInput.sendKeys(FisrtName);
		LastNameInput.sendKeys(LastName);
		EmailInput.sendKeys(Email);
		TelephoneInput.sendKeys(Mobile);

		

		PasswordInput.sendKeys(Password);
		PasswordConfirmInput.sendKeys(Password);
		SubscribeButton.click();
		AgreeBox.click();
		RegisterButton.click();
		String ConfirmationMsg = "Your Account Has Been Created!";
		boolean ActualResult = driver.getPageSource().contains(ConfirmationMsg);
		Assert.assertEquals(ActualResult, true, "To test signup");

	}
	
	
	@Test (priority = 2 , enabled=true)
	public void LogOut () {
		
		WebElement MyAccountButton = driver.findElement(By.xpath("//a[@title='My Account']"));
		MyAccountButton.click();
		
		WebElement LogOutButton = driver.findElement(By.linkText("Logout"));
		LogOutButton.click();
		
		String ConfirmMsg = "You have been logged off your account";
		boolean ExpectedResult = driver.getPageSource().contains(ConfirmMsg);
		Assert.assertEquals(ExpectedResult, true , "To tset Logout");
		
	}

	
	@Test (priority = 3, enabled =true)
	public void Login () throws InterruptedException {
		
		Thread.sleep(2000);
		driver.navigate().to(LoginURL);
		WebElement EmailInput = driver.findElement(By.xpath("//input[@id='input-email']"));
		WebElement PasswordInput = driver.findElement(By.id("input-password"));
		WebElement LoginButton = driver.findElement(By.xpath("//input[@value='Login']"));
	
		EmailInput.clear();
		EmailInput.sendKeys(Email);
		PasswordInput.sendKeys(Password);
        LoginButton.click();
		
	}
        @Test (priority = 4 , enabled=false)
        
        public void SortItems()  {
        	
        
        	WebElement Phones = driver.findElement(By.cssSelector("li:nth-child(6) a:nth-child(1)"));
        	Phones.click();
        	
        	WebElement SortIteam = driver.findElement(By.id("input-sort"));
        	Select MySelect = new Select(SortIteam);
        	MySelect.selectByContainsVisibleText("Price (High > Low)");
      
        }
        
        @Test (priority= 5, enabled=true)
        public void SearchForIteam() {
        	
        	WebElement SearchInput = driver.findElement(By.xpath("//input[@placeholder='Search']"));
        	WebElement SearchButton = driver.findElement(By.xpath("//button[@class='btn btn-default btn-lg']"));
        	SearchInput.sendKeys("MAC");
        	SearchButton.click();
        	
        	WebElement Apperance = driver.findElement(By.xpath("//i[@class='fa fa-th-list']"));
        	Apperance.click();
        	
        	
        }
        @Test (priority= 6, enabled=true)
        public void SearchNotFoundItem () {
        	
        	
        	WebElement SearchInput = driver.findElement(By.xpath("//input[@placeholder='Search']"));
        	WebElement SearchButton = driver.findElement(By.xpath("//button[@class='btn btn-default btn-lg']"));
        	
        	SearchInput.clear();
        	SearchInput.sendKeys("Mais");
        	SearchButton.click();
        	String ConfirmMsg = "There is no product that matches the search criteria";
        	boolean ExpectedResult = driver.getPageSource().contains(ConfirmMsg);
        	Assert.assertEquals(ExpectedResult, true , "test notfound item");
        
        	
        }
	
	
	
        
}
