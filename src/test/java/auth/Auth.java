package auth;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import utils.PayrollConfigurator;

public class Auth {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(PayrollConfigurator.SLOW_MO_VALUE));

            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate(PayrollConfigurator.TEST_URL);

            // Perform login steps
            page.locator("//*[@id=\"basic\"]").fill(PayrollConfigurator.EMPLOYEE_ID);
            page.locator("//*[@id=\"password1\"]/input").fill(PayrollConfigurator.PASSWORD);
            page.locator("//*[@id=\"app\"]/div/div/div/div/div[3]/a").click();

            // Save session state to file
            Path authPath = Paths.get("src/test/java/auth/auth.json");

            context.storageState(new BrowserContext.StorageStateOptions()
    .setPath(authPath));

            System.out.println("Auth state saved at: " + authPath);

            browser.close();
        }
    }
}
