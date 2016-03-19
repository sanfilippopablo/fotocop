package integrationtests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class LoginServletIT {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://192.168.33.10:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testLoginServletIT() throws Exception {
    driver.get(baseUrl + "/login");
    driver.findElement(By.name("action")).click();
    assertEquals("Este campo no puede estar vacío.", driver.findElement(By.cssSelector("div.error.error-username")).getText());
    assertEquals("Este campo no puede estar vacío.", driver.findElement(By.cssSelector("div.error.error-password")).getText());
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("warf");
    driver.findElement(By.name("action")).click();
    assertEquals("Este campo no puede estar vacío.", driver.findElement(By.cssSelector("div.error.error-password")).getText());
    driver.findElement(By.xpath("//form[@id='login']/div[2]/div/label")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("sdgf");
    driver.findElement(By.name("action")).click();
    assertEquals("Usuario inexistente.", driver.findElement(By.xpath("//form[@id='login']/div[3]/div")).getText());
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("pablo");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("sdfg");
    driver.findElement(By.name("action")).click();
    assertEquals("Contraseña incorrecta.", driver.findElement(By.xpath("//form[@id='login']/div[3]/div")).getText());
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("123");
    driver.findElement(By.name("action")).click();
    assertEquals("Welcome to OpenShift", driver.getTitle());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
