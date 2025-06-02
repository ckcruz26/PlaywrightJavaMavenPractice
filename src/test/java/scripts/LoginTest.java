package scripts;

import com.microsoft.playwright.*;

import playwrightjava.pages.LoginPage;
import playwrightjava.utils.ErmUtils;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;
    LoginPage loginPage;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(ErmUtils.HEADLESS_BOOLEAN)
                .setSlowMo(ErmUtils.SLOW_MO_VALUE)
                .setArgs(Arrays.asList(ErmUtils.WINDOW_TYPE)));
    }

    @AfterAll
    static void closeBrowser() {
        if (playwright != null) {
            playwright.close();
        }
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setIgnoreHTTPSErrors(ErmUtils.IGNORE_HTTP_ERRORS_BOOLEAN)
                .setViewportSize(null));
        page = context.newPage();
        page.navigate(ErmUtils.URL);
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void erm_regressionLoginST001() {
        System.out.println("EPM_REGRESSION_LOGIN_ST_001");
        LoginPage loginPage = new LoginPage(page);
        loginPage.successfulLogin(ErmUtils.USERNAME, ErmUtils.PASSWORD);

        String actualMessage = page.locator("#modalDynamicMessage").textContent();
        String expectedMessage = "You have logged in successfully. Please close this modal to continue to the main page.";

        assertEquals(actualMessage, expectedMessage);

    }

    @Test
    void erm_regressionLoginST002() {
        System.out.println("EPM_REGRESSION_LOGIN_ST_002");

        LoginPage loginPage = new LoginPage(page);

        String actualMessage = page.locator("#modalDynamicMessage").textContent();
        String expectedMessage = "Oops! Invalid Credentials. You have 2 more attempt(s) before your account is locked. Please contact HRPPMS-RSP for further assistance in verifying your information.";

        loginPage.failedLogin(ErmUtils.USERNAME, "wrongpassword");
        assertNotEquals(actualMessage, expectedMessage);
    }
}
