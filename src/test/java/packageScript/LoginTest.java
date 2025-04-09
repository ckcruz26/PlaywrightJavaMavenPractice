package packageScript;

import com.microsoft.playwright.*;

import org.junit.jupiter.api.*;

import utils.PayrollConfigurator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
  static Playwright playwright;
  static Browser browser;

  BrowserContext context;
  Page page;

  @BeforeAll
  static void launchBrowser() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
        .setHeadless(PayrollConfigurator.HEADLESS_BOOLEAN)
        .setSlowMo(PayrollConfigurator.SLOW_MO_VALUE)
        .setArgs(Arrays.asList(PayrollConfigurator.WINDOW_TYPE)));
  }

  @AfterAll
  static void closeBrowser() {
    playwright.close();
  }

  @BeforeEach
  void createContextAndPage() {
    context = browser.newContext(new Browser.NewContextOptions()
        .setIgnoreHTTPSErrors(true)
        .setViewportSize(null));
    page = context.newPage();
    page.navigate(PayrollConfigurator.TEST_URL);
  }

  @AfterEach
  void closeContext() {
    context.close();
  }

  @Test
  void ePayrollLogin001() {
    assertEquals(page.title(), "ePayroll System");

    Locator employeeIdField = page.locator("//*[@id=\"basic\"]");
    Locator passwordField = page.locator("//*[@id=\"password1\"]/input");
    Locator signInButton = page.locator("//*[@id=\"app\"]/div/div/div/div/div[3]/a");

    employeeIdField.fill(PayrollConfigurator.EMPLOYEE_ID);
    passwordField.fill(PayrollConfigurator.PASSWORD);
    signInButton.click();

    assertEquals(PayrollConfigurator.TEST_URL + "dashboard", page.url());
  }

  @Test
  void ePayrollLogin002() {

    String actualModalMessage = page.locator("xpath=/html/body/div[2]/div/div[2]/div[1]/p").textContent();
    Locator employeeIdField = page.locator("//*[@id=\"basic\"]");
    Locator passwordField = page.locator("//*[@id=\"password1\"]/input");
    Locator signInButton = page.locator("//*[@id=\"app\"]/div/div/div/div/div[3]/a");

    employeeIdField.fill(PayrollConfigurator.EMPLOYEE_ID);
    passwordField.fill("asdkjaikdakjd");
    signInButton.click();

    assertNotEquals(PayrollConfigurator.TEST_URL + "dashboard", page.url());
    assertEquals(PayrollConfigurator.LOGIN_ERROR_INVALID_CREDENTIALS, actualModalMessage);

  }

  @Test
  void ePayrollLogin003() {

    String actualModalMessage = page.locator("xpath=/html/body/div[2]/div/div[2]/div[1]/p").textContent();
    Locator employeeIdField = page.locator("//*[@id=\"basic\"]");
    Locator signInButton = page.locator("//*[@id=\"app\"]/div/div/div/div/div[3]/a");

    employeeIdField.fill(PayrollConfigurator.EMPLOYEE_ID);
    signInButton.click();

    assertNotEquals(PayrollConfigurator.TEST_URL + "dashboard", page.url());
    assertEquals(PayrollConfigurator.LOGIN_ERROR_EMPTY_FIELD, actualModalMessage);
  }

  @Test
  void ePayrollLogin004() {
    Locator passwordField = page.locator("//*[@id=\"password1\"]/input");
    Locator passwordToggle = page.locator("svg");

    passwordField.fill("asdkjaikdakjd");
    passwordToggle.click();

    assertNotEquals(PayrollConfigurator.TEST_URL + "dashboard", page.url());
  }

}
