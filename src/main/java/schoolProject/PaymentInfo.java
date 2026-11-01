package schoolProject;

public class PaymentInfo {

    private final String cardNumber;
    private final String expiration;
    private final String cvv;
    private final String name;

    public PaymentInfo(String cardNumber, String expiration, String cvv, String name) {
        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.cvv = cvv;
        this.name = name;
    }

    public String getCardNumber() { return cardNumber; }
    public String getExpiration() { return expiration; }
    public String getCvv() { return cvv; }
    public String getName() { return name; }
}