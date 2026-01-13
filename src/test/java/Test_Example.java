import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.sql.DriverManager.getDriver;

public class Test_Example extends Test_Base {

    static ExtentReports report;
    static ExtentTest test;
    static ExtentReports extent = new ExtentReports();

    @Test
    public void TC01_VerifyNavigationOptions() {
        // driver.navigate().to("https://www.google.com");   // Navigation option 1
        driver.get("https://www.google.com");                // Navigation option 2

        driver.findElement(By.name("q")).sendKeys("Sri Lanka");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
    }

    @Test
    public void TC02_VerifyLocationStrategy() {
        driver.navigate().to("https://www.calculator.net/calorie-calculator.html");

        WebElement age = driver.findElement(By.className("inhalf"));    // Location strategy 1: ClassName
        WebElement gender = driver.findElement(By.xpath("//label[@for='csex1']"));  // Location strategy 2: Xpath
        WebElement height = driver.findElement(By.id("cheightmeter"));  // Location strategy 3: ID
        WebElement weight = driver.findElement(By.name("ckg")); // Location strategy 4: Name
        WebElement activity = driver.findElement(By.tagName("select")); // Location strategy 5: Tag name

        age.clear();
        height.clear();
        weight.clear();

        age.sendKeys("25");
        height.sendKeys("170");
        weight.sendKeys("70");
        gender.click();
        new Select(activity).selectByIndex(4);

        driver.findElement(By.linkText("+ Settings")).click();  // Location strategy 6: Link text
        driver.findElement(By.partialLinkText("Set")).click();  // Location strategy 7: Partial link text
        driver.findElement(By.cssSelector("input[value='Calculate']")).click(); // Location strategy 8: CSS selector
    }

    @Test
    public void TC03_VerifyOutcome() {
        driver.navigate().to("https://www.calculator.net/binary-calculator.html");

        driver.findElement(By.xpath("//*[@id=\"content\"]/form/table/tbody/tr[2]/td/input[3]")).click();

        driver.findElement(By.id("number1")).sendKeys("0110");
        driver.findElement(By.id("number2")).sendKeys("0001");

        driver.findElement(By.xpath("//*[@id='content']/form/table/tbody/tr[2]/td/input[2]")).click();

        String expectedResult = "0111";

        WebElement titleElement = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/font/b"));
        String actualResult = titleElement.getText();

        System.out.println(expectedResult);
        System.out.println(actualResult);

        // Assert if expected result equals actual result
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void TC04_ValidateDataDrivenTest() throws IOException {
        //Create and load property file
        Properties properties = new Properties();
        FileInputStream input = new FileInputStream("src/main/resources/dataFiles/testdata.properties");
        properties.load(input);

        driver.navigate().to(properties.getProperty("uri"));

        WebElement total_price = driver.findElement(By.name("serviceprice"));

        total_price.clear();
        total_price.sendKeys(properties.getProperty("price"));

        driver.findElement(By.cssSelector("input[value='Calculate']")).click();
    }

    @Test
    public void TC05_ValidateExtentReports() {
        driver.get("https://www.calculator.net/root-calculator.html");

        // Generate Extent Report
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        extent.attachReporter(spark);
        test = extent.createTest("Validate root calculator functionality", "This is to calculate the root of a number.");

        driver.findElement(By.name("cvar1")).sendKeys("100");
        driver.findElement(By.name("x")).click();

        // Write data to the report
        extent.flush();
    }
}