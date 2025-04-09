package packageScript;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class SettingsTest implements SettingsInterface {
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
        Path sessionPath = Paths.get(System.getProperty("user.dir"))
                .resolve("src/test/java/auth/auth.json");

        if (!Files.exists(sessionPath)) {
            throw new RuntimeException("Session file not found at: " + sessionPath);
        }

        context = browser.newContext(new Browser.NewContextOptions()
                .setIgnoreHTTPSErrors(true)
                .setViewportSize(null)
                .setStorageStatePath(sessionPath));

        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Override
    @Test
    public void ePayrollSettings001() {

        try {
            String fullUrl = PayrollConfigurator.TEST_URL + "settings/salary-grade";
            page.navigate(fullUrl);
            assertEquals(fullUrl, page.url());
        } catch (UnsupportedOperationException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    @Test
    public void ePayrollSettings002() {

        try {
            String fullUrl = PayrollConfigurator.TEST_URL + "settings/salary-grade";
            page.navigate(fullUrl);

            Locator searchField = page.locator("//*[@id=\"app\"]/div/div[3]/div[1]/div[1]/div[2]/div[1]/input");
            searchField.fill(PayrollConfigurator.SEARCH_DATA_NAME_PAYROLL);

            page.waitForSelector("//*[@id=\"app\"]/div/div[3]/div[1]/div[2]/div/div[1]/table/tbody/tr/td");

            Locator tableCells = page.locator("//*[@id=\"app\"]/div/div[3]/div[1]/div[2]/div/div[1]/table/tbody/tr/td");
            int rowsCount = tableCells.count();

            for (int i = 0; i < rowsCount; i++) {
                Locator rowLocator = tableCells.nth(i);
                String cellText = rowLocator.innerText();

                if (cellText.contains(PayrollConfigurator.SEARCH_DATA_NAME_PAYROLL)) {
                    System.out.println("Found matching cell: " + cellText);
                    break;
                }
            }

        } catch (UnsupportedOperationException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    @Test
    public void ePayrollSettings003() {
        try {
            String fullUrl = PayrollConfigurator.TEST_URL + "settings/salary-grade";
            page.navigate(fullUrl);

            Locator buttonCreate = page.locator("//*[@id=\"app\"]/div/div[3]/div[1]/div[1]/div[2]/div[4]/button");
            Locator salaryGradeTableCreateLabel = page.locator("//*[@id=\"pv_id_3_header\"]");

            buttonCreate.click();

            assertTrue(salaryGradeTableCreateLabel.isVisible()); // Just check visibility

            String expectedText = "Create Salary Grade Table"; // replace with your expected text
            String actualText = salaryGradeTableCreateLabel.innerText();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ePayrollSettings004() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
