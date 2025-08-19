import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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
	String HomePage = "https://opencart.dreamvention.com/index.php?route=common/home";
	Connection con;
	Statement stmt;
	ResultSet rs;
	Random rand = new Random();

	String FisrtName;
	String LastName;
	String Mobile;
	String Company ;
	String Address ;
	String City;
	
	String Email ;
	String Password = "KKvv234&";

	

	@BeforeTest
	public void MySetUp() throws SQLException {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "123456");

		String URL = "https://opencart.dreamvention.com/";

		driver.get(URL);
		driver.manage().window().maximize();
		
		String QueryToRead =
			    "SELECT contactFirstName, contactLastName, phone, addressLine1, customerName , city " +
			    	    "FROM customers " +
			    	    "WHERE customerNumber BETWEEN 103 AND 200 " +
			    	    "AND contactFirstName IS NOT NULL " +
			    	    "AND contactLastName IS NOT NULL " +
			    	    "AND phone IS NOT NULL " +
			    	    "AND addressLine1 IS NOT NULL " +
			    	    "AND customerName IS NOT NULL " +
			    	    "AND city IS NOT NULL " +
			    	    "ORDER BY RAND() " +
			    	    "LIMIT 1";

		
		stmt = con.createStatement();
	    rs = stmt.executeQuery(QueryToRead);

	    if (rs.next()) {
	        FisrtName = rs.getString("contactFirstName");
	        LastName  = rs.getString("contactLastName");
	        Mobile    = rs.getString("phone");
	        Address  = rs.getNString("addressLine1");
	        Company = rs.getNString("customerName");
	        City = rs.getNString("city");
	    
	    }
		
		
		int RandomNum = rand.nextInt(238);
		Email = FisrtName + RandomNum+LastName + "@gmail.com";

	}

	@Test(priority = 1 , enabled = true)
	public void SignUp() throws  SQLException {

		driver.navigate().to(RegisterURL);

		

	
	


	
		

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
	
	
	@Test (priority = 2 , enabled=false)
	public void LogOut () {
		
		WebElement MyAccountButton = driver.findElement(By.xpath("//a[@title='My Account']"));
		MyAccountButton.click();
		
		WebElement LogOutButton = driver.findElement(By.linkText("Logout"));
		LogOutButton.click();
		
		String ConfirmMsg = "You have been logged off your account";
		boolean ExpectedResult = driver.getPageSource().contains(ConfirmMsg);
		Assert.assertEquals(ExpectedResult, true , "To tset Logout");
		
	}

	
	@Test (priority = 3, enabled =false)
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
        
        @Test (priority= 5, enabled=false)
        public void SearchForIteam() {
        	
        	WebElement SearchInput = driver.findElement(By.xpath("//input[@placeholder='Search']"));
        	WebElement SearchButton = driver.findElement(By.xpath("//button[@class='btn btn-default btn-lg']"));
        	SearchInput.sendKeys("MAC");
        	SearchButton.click();
        	
        	WebElement Apperance = driver.findElement(By.xpath("//i[@class='fa fa-th-list']"));
        	Apperance.click();
        	
        	
        }
        @Test (priority= 6, enabled=false)
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
	
	@Test (priority = 7 , enabled = false)
	public void OpenIteamPage () throws InterruptedException {
		
		driver.navigate().to(HomePage);
		
		
		WebElement Iteam1 = driver.findElement(By.xpath("//img[@title='Canon EOS 5D']"));
		Iteam1.click();
		
		WebElement ItemPic = driver.findElement(By.xpath("//li[2]//a[1]//img[1]"));
		ItemPic.click();
		
		WebElement NextOption = driver.findElement(By.xpath("//button[@title='Next (Right arrow key)']"));
		NextOption.click();
		Thread.sleep(1000);
		WebElement CloseButton = driver.findElement(By.xpath("//button[normalize-space()='×']"));
		CloseButton.click();
		
		driver.navigate().back();
		
	}
	
	@Test (priority = 8 , enabled = false)
	public void ChangeCurrency () throws InterruptedException {
		
		WebElement CurrencyButton = driver.findElement(By.cssSelector(".btn.btn-link.dropdown-toggle"));
		CurrencyButton.click();
		
		Thread.sleep(2000);
		
		List<WebElement> Options = driver.findElements(By.cssSelector("ul[class='dropdown-menu']"));
		int OptionsSize = Options.size();
		int RandomIndex = rand.nextInt(OptionsSize);
		Options.get(RandomIndex).click();
		
	}
        
	@Test (priority = 8 , enabled =false)
	  public void ContactForm () throws InterruptedException {
		WebElement ContactUsButton = driver.findElement(By.xpath("//a[normalize-space()='Contact Us']"));
		ContactUsButton.click();
		WebElement NameInput = driver.findElement(By.id("input-name"));
		NameInput.sendKeys(FisrtName + LastName);
		
		WebElement EmailInput = driver.findElement(By.id("input-email"));
		EmailInput.sendKeys(Email);
		WebElement Enquiry = driver.findElement(By.id("input-enquiry"));
		Enquiry.sendKeys("Just For Test");
		
		Thread.sleep(2000);
		
		WebElement SubmitButton = driver.findElement(By.xpath("//input[@value='Submit']"));
		SubmitButton.click();
		
		
	}
	
	
	@Test (priority = 9 , enabled =true)
	public void  AddToTheCart () throws InterruptedException {
		driver.navigate().to(HomePage);
		
		List <WebElement> ItemsContainer = driver.findElements(By.cssSelector(".product-thumb.transition"));
		int HomePageItems = ItemsContainer.size();
		int RandomItemIndex = rand.nextInt(HomePageItems);
		WebElement ItemClick =  ItemsContainer.get(RandomItemIndex);
	
		WebElement PicLink = ItemClick.findElement(By.cssSelector(".image a"));
		PicLink.click();
		
		Thread.sleep(2000);		
		WebElement Qty = driver.findElement(By.id("input-quantity"));
		Qty.clear();
		int RandomQty = rand.nextInt(3 ,7);
	
		Qty.sendKeys(String.valueOf(RandomQty));
		WebElement AddButton = driver.findElement(By.cssSelector("#button-cart"));
		AddButton.click();
		
		
		
	}
	
	
	
	@Test (priority = 10 , enabled =true)
	public void CheckOut () throws InterruptedException  {
		
		WebElement CheckOut = driver.findElement(By.xpath("//span[normalize-space()='Checkout']"));
		CheckOut.click();
		Thread.sleep(2000);
		
		
		WebElement FirstName2 = driver.findElement(By.id("input-payment-firstname"));
		WebElement LastName2 = driver.findElement(By.id("input-payment-lastname"));
		WebElement Company2 = driver.findElement(By.id("input-payment-company"));
		WebElement Adress1 = driver.findElement(By.id("input-payment-address-1"));
		WebElement Adress2 = driver.findElement(By.id("input-payment-address-2"));
		WebElement City2 = driver.findElement(By.id("input-payment-city"));
		WebElement PostCode = driver.findElement(By.id("input-payment-postcode"));
		WebElement Country = driver.findElement(By.id("input-payment-country"));
		WebElement State = driver.findElement(By.id("input-payment-zone"));
		WebElement ContButton = driver.findElement(By.id("button-payment-address"));
		
		 
		
		FirstName2.sendKeys(FisrtName);
		LastName2.sendKeys(LastName);
		Company2.sendKeys(Company);
		Adress1.sendKeys(Address);
		Adress2.sendKeys(Address);
		City2.sendKeys(City);
		PostCode.sendKeys("888");
		
		int SizeOfCountryOptions= Country.findElements(By.tagName("option")).size();
		Select CountySelect = new Select(Country);
		int RandomCountryIndex = rand.nextInt(1 , SizeOfCountryOptions);
		CountySelect.selectByIndex(RandomCountryIndex);
		 
		Thread.sleep(2000);
		
		int SizeOfStateOptions = State.findElements(By.tagName("option")).size();
		Select StateSelect = new Select(State);
		int RandomStateIndex = rand.nextInt(1 , SizeOfStateOptions);
		StateSelect.selectByIndex(RandomStateIndex);
		
		ContButton.click();
		
		//Step 3
		Thread.sleep(2000);
		WebElement RadioButton = driver.findElement(By.cssSelector("input[value='existing'][name='shipping_address']"));
		WebElement Contbutton2 = driver.findElement(By.xpath("//input[@id='button-shipping-address']"));
		
		RadioButton.click();
        Contbutton2.click();
		
		
		//Step 4
		Thread.sleep(2000);
		WebElement PickUp = driver.findElement(By.cssSelector("input[value='pickup.pickup']"));
		WebElement Comment = driver.findElement(By.xpath("//textarea[@name='comment']"));
		WebElement Contbutton3 = driver.findElement(By.xpath("//input[@id='button-shipping-method']"));
		
		PickUp.click();
		Comment.sendKeys("Hello");
		Contbutton3.click();
		
		//Step5
		Thread.sleep(2000);
		
		WebElement PaymentMethod = driver.findElement(By.xpath("//input[@value='paypal']"));
		WebElement ContButton4= driver.findElement(By.cssSelector("#button-payment-method"));
		
		PaymentMethod.click();
		ContButton4.click();
		
		
	}
	
	
}
