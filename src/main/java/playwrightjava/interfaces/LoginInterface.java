package playwrightjava.interfaces;

public interface LoginInterface {
    void successfulLogin(String username, String password); // Method to perform a successful login
    void failedLogin(String username, String password); // Method to perform a failed login
}
