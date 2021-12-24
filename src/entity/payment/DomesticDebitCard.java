package entity.payment;

public class DomesticDebitCard extends PaymentCard {

    private String issueBank;
    private String cardNumber;
    private String fromDate;
    private String cardHolderName;

    public DomesticDebitCard(String issueBank, String cardNumber, String fromDate, String cardHolderName) {
        super();
        this.issueBank = issueBank;
        this.cardNumber = cardNumber;
        this.fromDate = fromDate;
        this.cardHolderName = cardHolderName;
    }

    public DomesticDebitCard() {
    }

    public String getIssueBank() {
        return issueBank;
    }

    public void setIssueBank(String issueBank) {
        this.issueBank = issueBank;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
}
