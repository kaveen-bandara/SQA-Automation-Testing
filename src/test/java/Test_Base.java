import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Test_Base {

    protected WebDriver driver;

    @BeforeMethod
    public void beforeTest() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--allow-insecure-localhost");
        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.setAcceptInsecureCerts(true);
        chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);

        String curDir = System.getProperty("user.dir");
        final String resourcePath = String.format("%s%s", curDir, "\\src\\main\\resources\\drivers\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", resourcePath);
        driver = new ChromeDriver(chromeOptions);
    }

    @AfterMethod
    public void afterTest() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}