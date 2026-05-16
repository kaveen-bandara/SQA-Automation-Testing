import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

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
        Assert.assertEquals(actualResult, expectedResult);
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

    @Test
    public void TC06_VerifyCSVDataDrivenTest() throws IOException, CsvValidationException {
        driver.get("https://www.calculator.net/body-fat-calculator.html");

        String CSVFile = "src/main/resources/dataFiles/testdata.csv";
        CSVReader reader = new CSVReader(new FileReader(CSVFile));
        String[] cell;

        while ((cell = reader.readNext()) != null) {
            if (cell.length < 3) {
                System.out.println("Skipping row due to insufficient data: " + Arrays.toString(cell));
                continue; // Skip this row if it has less than 3 elements
            }

            String age = cell[0];
            String height = cell[1];
            String weight = cell[2];
            String neck = cell[3];
            String waist = cell[4];

            WebElement webAge = driver.findElement(By.name("cage"));
            WebElement webWeight = driver.findElement(By.name("cweightkgs"));
            WebElement webHeight = driver.findElement(By.name("cheightmeter"));
            WebElement webNeck = driver.findElement(By.name("cneckmeter"));
            WebElement webWaist = driver.findElement(By.name("cwaistmeter"));

            webAge.clear();
            webWeight.clear();
            webHeight.clear();
            webNeck.clear();
            webWaist.clear();

            webAge.sendKeys(age);
            webWeight.sendKeys(weight);
            webHeight.sendKeys(height);
            webNeck.sendKeys(neck);
            webWaist.sendKeys(waist);
        }

        driver.findElement(By.name("x")).click();
    }

    @Test
    public void TC07_VerifySleepWait() throws InterruptedException {
        driver.navigate().to("https://www.leafground.com/waits.xhtml");

        driver.findElement(By.id("j_idt87:j_idt89")).click();

        Thread.sleep(10000);

        String expectedButtonText = "I am here";
        String text = driver.findElement(By.id("j_idt87:j_idt90")).getText();
        System.out.println(expectedButtonText);
        System.out.println(text);

        // Assert if expected result equals actual result
        Assert.assertEquals(text, expectedButtonText);
    }

    @Test
    public void TC08_VerifyPageLoadWait() {
        // Page load wait declaration and application
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));

        driver.navigate().to("https://www.leafground.com");
    }

    @Test
    public void TC09_VerifyImplicitWait() {
        // Implicit wait declaration and application
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.navigate().to("https://www.roadmap.sh");
    }

    @Test
    public void TC10_VerifyExplicitWait() {
        // Explicit wait declaration
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.navigate().to("https://www.leafground.com/waits.xhtml");
        driver.findElement(By.id("j_idt87:j_idt89")).click();

        // Explicit wait application to capture button title
        WebElement newButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("j_idt87:j_idt90")));

        String text = newButton.getText();
        System.out.println(text);
    }

    @Test
    public void TC11_VerifySimpleAlert() {
        driver.navigate().to("https://www.leafground.com/alert.xhtml");
        driver.findElement(By.id("j_idt88:j_idt91")).click();
        driver.switchTo().alert().accept();

        String expectedText = "You have successfully clicked an alert";
        String text = driver.findElement(By.id("simple_result")).getText();

        System.out.println(expectedText);
        System.out.println(text);

        // Assert if expected result equals actual result
        Assert.assertEquals(text, expectedText);
    }

    @Test
    public void TC12_VerifyConfirmAlert() {
        driver.navigate().to("https://www.leafground.com/alert.xhtml");
        driver.findElement(By.id("j_idt88:j_idt93")).click();
        driver.switchTo().alert().dismiss();

        String expectedText = "User Clicked : Cancel";
        String text = driver.findElement(By.id("result")).getText();

        System.out.println(expectedText);
        System.out.println(text);

        // Assert if expected result equals actual result
        Assert.assertEquals(text, expectedText);
    }

    @Test
    public void TC13_VerifyPromptAlert() {
        driver.navigate().to("https://www.leafground.com/alert.xhtml");
        driver.findElement(By.id("j_idt88:j_idt104")).click();
        String prompt = "Testing... Testing...1.2.3.";
        driver.switchTo().alert().sendKeys(prompt);
        driver.switchTo().alert().accept();

        String expectedText = "User entered name as: " + prompt;
        String text = driver.findElement(By.id("confirm_result")).getText();

        System.out.println(expectedText);
        System.out.println(text);

        // Assert if expected result equals actual result
        Assert.assertEquals(text, expectedText);
    }

    @Test
    public void TC14_VerifyiFrame() {
        driver.navigate().to("https://www.leafground.com/frame.xhtml");

        // Pass the control to iframe
        driver.switchTo().frame(0);
        driver.findElement(By.id("Click")).click();

        String expectedText = "Hurray! You Clicked Me.";
        String text = driver.findElement(By.id("Click")).getText();

        System.out.println(expectedText);
        System.out.println(text);

        // Assert if expected result equals actual result
        Assert.assertEquals(text, expectedText);
    }
}
