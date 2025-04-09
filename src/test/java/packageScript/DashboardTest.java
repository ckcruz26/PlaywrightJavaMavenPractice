package packageScript;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import utils.PayrollConfigurator;

public class DashboardTest {
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
        // Resolve the session path relative to project root
        Path sessionPath = Paths.get(System.getProperty("user.dir"))
                .resolve("src")
                .resolve("test")
                .resolve("java")
                .resolve("auth")
                .resolve("auth.json");

        // Check if the file exists
        if (!Files.exists(sessionPath)) {
            throw new RuntimeException("Session file not found at: " + sessionPath);
        }

        context = browser.newContext(new Browser.NewContextOptions()
                .setIgnoreHTTPSErrors(true)
                .setViewportSize(null)
                .setStorageStatePath(sessionPath) // Load the saved session
        );

        page = context.newPage();

        // Navigate using session login

        page.navigate(PayrollConfigurator.TEST_URL + "dashboard");
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void ePayrollDashboard001() {
        assertEquals(PayrollConfigurator.TEST_URL + "dashboard", page.url());
    }

    @Test
    void ePayrollDashboard002() {

        Locator payrollManagementLink = page.locator("//*[@id=\"app\"]/div/div[3]/div[1]/div[3]/div/div[1]/div/div/p[2]");
        assertEquals(PayrollConfigurator.TEST_URL + "dashboard", page.url());
        payrollManagementLink.click();
        assertEquals(PayrollConfigurator.TEST_URL + "payroll-management", page.url());
    }
}
