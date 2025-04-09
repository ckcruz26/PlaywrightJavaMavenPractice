package packageScript;

import com.microsoft.playwright.*;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

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

  @Epic("Login")
  @Feature("Login functionality")
  @Story("Valid login")
  @Severity(SeverityLevel.CRITICAL)
  @Test
  @Description("Test for valid login credentials")
  void ePayrollLogin001() {
    assertEquals(page.title(), "ePayroll System");
    page.locator("//*[@id=\"basic\"]").fill(PayrollConfigurator.EMPLOYEE_ID);
    page.locator("//*[@id=\"password1\"]/input").fill(PayrollConfigurator.PASSWORD);
    page.locator("//*[@id=\"app\"]/div/div/div/div/div[3]/a").click();
    assertEquals(PayrollConfigurator.TEST_URL + "dashboard", page.url());
  }

  @Test
  void ePayrollLogin002() {
    page.locator("//*[@id=\"basic\"]").fill(PayrollConfigurator.EMPLOYEE_ID);
    page.locator("//*[@id=\"password1\"]/input").fill("asdkjaikdakjd");
    page.locator("//*[@id=\"app\"]/div/div/div/div/div[3]/a").click();
    String actualModalMessage = page.locator("xpath=/html/body/div[2]/div/div[2]/div[1]/p").textContent();

    assertNotEquals(PayrollConfigurator.TEST_URL + "dashboard", page.url());
    assertEquals(PayrollConfigurator.LOGIN_ERROR_INVALID_CREDENTIALS, actualModalMessage);
  }

  @Test
  void ePayrollLogin003() {
    page.locator("//*[@id=\"basic\"]").fill(PayrollConfigurator.EMPLOYEE_ID);
    page.locator("//*[@id=\"app\"]/div/div/div/div/div[3]/a").click();
    String actualModalMessage = page.locator("xpath=/html/body/div[2]/div/div[2]/div[1]/p").textContent();

    assertNotEquals(PayrollConfigurator.TEST_URL + "dashboard", page.url());
    assertEquals(PayrollConfigurator.LOGIN_ERROR_EMPTY_FIELD, actualModalMessage);
  }

  @Test
  void ePayrollLogin004() {
    page.locator("//*[@id=\"password1\"]/input").fill("asdkjaikdakjd");
    page.locator("svg").click();
    assertNotEquals(PayrollConfigurator.TEST_URL + "dashboard", page.url());
  }

}
