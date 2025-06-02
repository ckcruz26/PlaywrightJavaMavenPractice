package playwrightjava.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

import playwrightjava.interfaces.ProfileInterface;

public class ProfilePage implements ProfileInterface {

    private final Locator frmBankDetails; // Locator for the bank details form
    private final Locator accountNumberField; // Locator for the account number field
    private final Locator saveButton; // Locator for the save button
    private final Locator modalConfirmYes; // Locator for the confirmation modal's "Yes" button
    private final Locator dynamicModalMsg;
    

    //tabs element locators
    private final Locator overviewTab; // Locator for the overview tab
    private final Locator basicInfoTab; // Locator for the basic info tab


    //basic information field locators
    private final Locator regionSelector;
    private final Locator provinceSelector;
    private final Locator citySelector;
    private final Locator barangaySelecator;
    private final Locator zipCodeField;
    private final Locator mobileNoField;
    private final Locator emailAddressField;
    private final Locator saveBasicInfoButton; // Locator for the save button in the basic info section

    //tab clicks
    private final Locator basicInfoTabSelect;
    private final Locator otherInformationPartOneSelect;


    public ProfilePage(Page page) {
        
        this.frmBankDetails = page.locator("#frmBankDetails");
        this.accountNumberField = page.locator("#addBankAccount");
        this.saveButton = page.locator("#btnSaveAccountNumber"); // Initialize with a sample selector
        this.modalConfirmYes = page.locator("#modalDynamicConfirmButtonYes");
        this.dynamicModalMsg = page.locator("#modalDynamicMessage"); // Initialize with a sample selector

        //tabs element locators
        this.overviewTab = page.locator("//li[contains(@class, 'active')]/a[@href='#overView']"); // Initialize with a sample selector
        this.basicInfoTab = page.locator("//li[contains(@class, 'active')]/a[@href='#basicInfo']"); // Initialize with a sample selector

        //click tabs
        this.basicInfoTabSelect = page.locator("a[href='#basicInfo']");
        this.otherInformationPartOneSelect = page.locator("a[href='#otherInfo']");

        //basic information field locators
        this.regionSelector = page.locator("#AddRegion"); // Initialize with a sample selector
        this.provinceSelector = page.locator("#AddProvince"); // Initialize with a sample selector
        this.citySelector = page.locator("#AddCity"); // Initialize with a sample selector
        this.barangaySelecator = page.locator("#AddBarangay"); // Initialize with a sample selector
        this.zipCodeField = page.locator("#AddZipCode"); // Initialize with a sample selector
        this.mobileNoField = page.locator("#AddMobileNo"); // Initialize with a sample selector
        this.emailAddressField = page.locator("#AddEmail"); // Initialize with a sample selector
        this.saveBasicInfoButton = page.locator("#btnUserBasicInfoUpdate"); // Initialize with a sample selector
    }
    
    @Override
    public void addBankDetails(String accountNumber) {
        // Wait for the bank details form to be visible
        frmBankDetails.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        
        // Fill in the account number field
        accountNumberField.fill(accountNumber);
        
        // Click the save button
        saveButton.click();
        
        // Wait for the confirmation modal to be visible and click "Yes"
        modalConfirmYes.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        modalConfirmYes.click();
        
        // Wait for the success message to be visible
        dynamicModalMsg.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    @Override
    public void verifyIfOverviewTabIsActive() {
        overviewTab.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    @Override
    public void verifyBasicInfoTabIsActive() {
        basicInfoTab.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    @Override
    public void basicInformation(String region, String province, String city, String barangay, String zipCode, String mobileNo,
            String emailAddress) {

        
        basicInfoTabSelect.click(); // Click the Basic Info tab to switch to it
        verifyBasicInfoTabIsActive();
        // Fill in the basic information fields
        regionSelector.selectOption(new SelectOption().setValue(region));
        provinceSelector.selectOption(new SelectOption().setValue(province));
        citySelector.selectOption(new SelectOption().setValue(city));   
        barangaySelecator.selectOption(new SelectOption().setValue(barangay)); // Select the barangay
        zipCodeField.fill(zipCode);
        mobileNoField.fill(mobileNo);
        emailAddressField.fill(emailAddress);
        
        saveBasicInfoButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        saveBasicInfoButton.click();

        modalConfirmYes.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        modalConfirmYes.click();
        
        // Wait for the success message to be visible
        dynamicModalMsg.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

    }

    @Override
    public void otherInforamtionPartOne(String placeOfBirth, String civilStatus, String height, String weight,
            String bloodType, String pagIbigNumber, String philHealthNumber, String tinID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'otherInforamtionPartOne'");
    }
    
}
