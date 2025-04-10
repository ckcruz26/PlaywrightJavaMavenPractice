package packageScript;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;

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

public class ReportsTest {
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
        page.navigate(PayrollConfigurator.TEST_URL + "reports");
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void ePayrollReports001() {
        assertThat(page).hasURL(Pattern.compile(".*\\/reports.*"));
    }

    @Test
    void ePayrollReports002() {
        Locator reportsLink = page.locator("//*[@id=\"app\"]/div/div[2]/div/div[1]/ul/li[4]/ul/li/a");
        reportsLink.click();
    }
}
