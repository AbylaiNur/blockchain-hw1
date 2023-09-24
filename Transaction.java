public class Transaction {
    private String transactionId;
    private String sender;
    private String recipient;
    private double amount;

    public Transaction(String from, String to, double amount) {
        this.sender = from;
        this.recipient = to;
        this.amount = amount;
        this.transactionId = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(
                sender
                        + recipient
                        + amount
        );
    }

    public String toString() {
        return "  Transaction:" + '\n' +
                "    sender=" + sender + '\n' +
                "    recipient=" + recipient + '\n' +
                "    amount=" + amount + '\n' + '\n';
    }
}
