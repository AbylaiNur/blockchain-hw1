import java.util.ArrayList;
import java.util.List;

public class MerkleTree {
    private List<String> transactions;
    private String root;

    public MerkleTree(List<Transaction> transactions) {
        List<String> transactionIds = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionIds.add(transaction.calculateHash());
        }
        this.transactions = transactionIds;
        this.root = constructTree(transactionIds);
    }

    private String constructTree(List<String> transactionIds) {
        if (transactionIds.size() == 0) {
            return "";
        }
        if (transactionIds.size() == 1) {
            return transactionIds.get(0);
        }

        List<String> updatedList = new ArrayList<>();
        for (int i = 0; i < transactionIds.size(); i += 2) {
            if (i + 1 < transactionIds.size()) {
                updatedList.add(StringUtil.applySha256(transactionIds.get(i) + transactionIds.get(i + 1)));
            } else {
                updatedList.add(transactionIds.get(i));
            }
        }

        return constructTree(updatedList);
    }

    public String toString() {
        return "MerkleTree:" + '\n' +
                "  root=" + root + '\n' + '\n';
    }

    public String getRoot() {
        return this.root;
    }
}
