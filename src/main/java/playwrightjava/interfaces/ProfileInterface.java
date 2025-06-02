package playwrightjava.interfaces;

public interface ProfileInterface {
    
    void addBankDetails(String accountNumber);
    
    //verify tabs are active functions
    void verifyIfOverviewTabIsActive();
    void verifyBasicInfoTabIsActive();

    //fill ups
    void basicInformation(String region, String province, String city, String barangay, String zipCode, String mobileNo, String emailAddress);
    void otherInforamtionPartOne(String placeOfBirth, String civilStatus, String height, String weight, String bloodType, String pagIbigNumber, String philHealthNumber, String tinID);
    
}
