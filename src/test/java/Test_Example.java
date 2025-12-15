import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class Test_Example extends Test_Base {

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
        // driver.findElement(By.partialLinkText("Set")).click();  // Location strategy 7: Partial link text
        driver.findElement(By.cssSelector("input[type='submit']")).click(); // Location strategy 8: CSS selector
    }
}