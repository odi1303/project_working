package il.cshaifasweng.OCSFMediatorExample.entities.models;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class CreditInformation implements Serializable {
    //@Column(name = "Card number")
    private String cardNumber;
    //@Column(name = "Expiration date")
    private String expirationDate;
    //@Column(name = "cvv")
    private String cvv;
    public CreditInformation() {}

    public CreditInformation(String cardNumber, String expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public boolean isValid() {
        return isCardNumberValid() && isExpirationDateValid() && isCvvValid();
    }

    public boolean isCardNumberValid() {
        return cardNumber != null && cardNumber.matches("\\d{16}"); // 16-digit card
    }

    public boolean isExpirationDateValid() {
        return expirationDate != null && expirationDate.matches("^(0[1-9]|1[0-2])/\\d{2}$"); // MM/YY
    }

    public boolean isCvvValid() {
        return cvv != null && cvv.matches("\\d{3}"); // 3-digit CVV
    }
}
