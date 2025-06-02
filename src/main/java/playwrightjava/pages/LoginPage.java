package playwrightjava.pages;



import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;




import playwrightjava.interfaces.LoginInterface;

public class LoginPage implements LoginInterface {
    
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;

    public LoginPage(Page page) {
        this.usernameField = page.locator("#username"); // Initialize with a sample selector
        this.passwordField = page.locator("#password"); // Initialize with a sample selector
        this.loginButton = page.locator("#btn_submit"); // Initialize with a sample selector
        
    }

    @Override
    public void successfulLogin(String username, String password) {
        this.usernameField.fill(username); // Fill the username field
        this.passwordField.fill(password); // Fill the password field
        this.loginButton.click(); // Click the login button
    }

    @Override
    public void failedLogin(String username, String password) {
       this.usernameField.fill(username); // Fill the username field
       this.passwordField.fill(password); // Fill the password field
       this.loginButton.click(); // Click the login button
       
    }

 

}
