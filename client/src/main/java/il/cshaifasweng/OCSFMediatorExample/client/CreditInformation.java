package il.cshaifasweng.OCSFMediatorExample.client;

public class CreditInformation {

    private String cardNumber;
    private String expirationDate; // Format: MM/YY
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
