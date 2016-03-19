package integrationtests;

import java.util.regex.Pattern;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import users.data.*;
import users.entities.User;

public class RegistrationServletIT {
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
  public void testRegistrationServletIT() throws Exception {
    driver.get(baseUrl + "/registration");
    driver.findElement(By.name("action")).click();
    assertEquals("Este campo no puede estar vacío.", driver.findElement(By.cssSelector("div.error.error-username")).getText());
    assertEquals("Este campo no puede estar vacío.", driver.findElement(By.cssSelector("div.error.error-email")).getText());
    assertEquals("Este campo no puede estar vacío.", driver.findElement(By.cssSelector("div.error.error-password")).getText());
    assertEquals("Este campo no puede estar vacío.", driver.findElement(By.cssSelector("div.error.error-password2")).getText());
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("wergreg");
    driver.findElement(By.name("action")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("wergreg@awfe");
    driver.findElement(By.name("action")).click();
    assertEquals("", driver.findElement(By.cssSelector("div.error.error-email")).getText());
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("asd");
    driver.findElement(By.id("password2")).clear();
    driver.findElement(By.id("password2")).sendKeys("123");
    driver.findElement(By.name("action")).click();
    assertEquals("Las contraseñas no coinciden.", driver.findElement(By.cssSelector("div.error.error-password2")).getText());
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("pablo");
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("asd@wqer");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("asd");
    driver.findElement(By.id("password2")).clear();
    driver.findElement(By.id("password2")).sendKeys("asd");
    driver.findElement(By.name("action")).click();
    assertEquals("Ya existe un usuario con ese username.", driver.findElement(By.cssSelector("div.error.error-username")).getText());
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("hola");
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("asd@wqer");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("hola");
    driver.findElement(By.id("password2")).clear();
    driver.findElement(By.id("password2")).sendKeys("hola");
    driver.findElement(By.name("action")).click();
    assertEquals("Welcome to OpenShift", driver.getTitle());
    
    UsersService us = new UsersService();
    User u = null;
    u = us.getUserByUsername("hola");
    assertNotNull(u);
    
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
