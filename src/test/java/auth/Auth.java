package auth;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import playwrightjava.utils.ErmUtils;

public class Auth {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false)
                            .setArgs(List.of("--no-first-run", "--no-default-browser-check"))
                            .setSlowMo(ErmUtils.SLOW_MO_VALUE)
            );

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setIgnoreHTTPSErrors(ErmUtils.IGNORE_HTTP_ERRORS_BOOLEAN)
            );

           

            Page page = context.newPage();

            System.out.println("Navigating to: " + ErmUtils.URL);
            page.navigate(ErmUtils.URL);

            // Perform login steps
            page.locator("#username").fill(ErmUtils.USERNAME);
            page.locator("#password").fill(ErmUtils.PASSWORD);
            page.locator("#btn_submit").click();
            page.locator("#modalDynamicButton").click(); // Close modal if it appears
            page.waitForTimeout(2000); // Wait for 2 seconds to ensure the modal is closed

             // 👇 Detect and close unexpected tabs (like google.com)
             context.onPage(p -> {
                String openedUrl = p.url();
                System.out.println("New tab opened: " + openedUrl);
                if (openedUrl.contains("google.com")) {
                    System.out.println("Closing unexpected tab: " + openedUrl);
                    p.close();
                }
            });
            
            // Save session state to file
            Path authPath = Paths.get("src/test/java/auth/auth.json");

            context.storageState(new BrowserContext.StorageStateOptions()
                    .setPath(authPath));

            System.out.println("Auth state saved at: " + authPath);

            browser.close();
        }
    }
}
