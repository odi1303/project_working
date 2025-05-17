package il.cshaifasweng.OCSFMediatorExample.entities.dal.models;
import jakarta.persistence.*;
import jakarta.persistence.Entity; // Or javax.persistence.Entity depending on your JPA version
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Personal information")
public class PersonalInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full name")
    public String fullName;
    @Column(name = "phone number")
    public String phoneNumber;
    @Column(name = "email")
    public String email;
    public PersonalInformation() {}

    public PersonalInformation(String fullName, String phoneNumber, String email) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
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

public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
