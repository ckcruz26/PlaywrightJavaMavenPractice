package scripts;

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

import playwrightjava.pages.ProfilePage;
import playwrightjava.utils.ErmTestData;
import playwrightjava.utils.ErmUtils;

public class ProfileTest {
    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

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

        page.navigate(ErmUtils.URL + "profile.php");

    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void erm_regressionProfileST001() {
        assertEquals("Profile - Employee's Registration Module", page.title());
    }

    @Test
    void erm_regressionProfileST002() {
        ProfilePage profilePage = new ProfilePage(page);
        profilePage.addBankDetails("1234567890");

        String actualMessage = page.locator("#modalDynamicMessage").textContent();
        String expectedMessage = "Bank Information successfully updated.";

        assertEquals("Profile - Employee's Registration Module", page.title());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void erm_regressionProfileST003() {
        ProfilePage profilePage = new ProfilePage(page);
        profilePage.addBankDetails("1234567890");

        String actualMessage = page.locator("#modalDynamicMessage").textContent();
        String expectedMessage = "Bank Information successfully updated.";

        assertEquals("Profile - Employee's Registration Module", page.title());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void erm_regressionProfileST004() {
        ProfilePage profilePage = new ProfilePage(page);
        profilePage.verifyIfOverviewTabIsActive();

        Locator overviewTab = page.locator("//li[contains(@class, 'active')]/a[@href='#overView']");
        assertTrue(overviewTab.isVisible(), "Overview tab should be visible and active.");
    }

    @Test
    void erm_regressionProfileST005() {
        ProfilePage profilePage = new ProfilePage(page);

        profilePage.basicInformation("030000000", "031400000", "031405000", "031405013", "3017", "09285370995",
                "ckcruz@dswd.gov.ph");

        String actualMessage = page.locator("#modalDynamicMessage").textContent();
        String expectedMessage = "Information has been updated successfully.";

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void erm_regressionProfileST006(){
        
        ProfilePage profilePage = new ProfilePage(page);
        profilePage.otherInforamtionPartOne("Bulakan", "1", , "60", "A+", "1234-5678-9101", "21-1234567-", "123-456-789-012");

    }

}
