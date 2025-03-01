package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.Arrays;
import java.util.List;

public class PersonalInformation {
    private String fullName;
    private String phoneNumber;
    private String email;

    public PersonalInformation() {}

    public PersonalInformation(String fullName, String phoneNumber, String email) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public List<String> toList() {
        return Arrays.asList(fullName, phoneNumber, email);
    }

    public boolean isValid() {
        return isFullNameValid() && isPhoneNumberValid() && isEmailValid();
    }

    public boolean isFullNameValid() {
        return fullName != null && fullName.matches("^[A-Za-z\\s]+$"); // Only letters and spaces
    }

    public boolean isPhoneNumberValid() {
        return phoneNumber != null && phoneNumber.matches("\\d{10}"); // Exactly 10 digits
    }

    public boolean isEmailValid() {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"); // Email format
    }
}
