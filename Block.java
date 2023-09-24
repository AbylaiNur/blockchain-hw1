import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Block {
    private String hash;
    private String previousHash;
    private long timeStamp;
    private List<Transaction> transactions;
    private long nonce;
    private MerkleTree merkleTree;


    public Block(String previousHash, List<Transaction> transactions) {
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.transactions = transactions;
        this.hash = calculateHash();
        this.merkleTree = new MerkleTree(transactions);
    }

    public String getPreviousHash() {
        return previousHash;
    }
    public String getHash() {
        return hash;
    }

    public String calculateHash() {
        String dataToHash = previousHash
                + timeStamp
                + nonce
                + (merkleTree != null ? merkleTree.getRoot() : "");
        return StringUtil.applySha256(dataToHash);
    }

        public String mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            hash = calculateHash();
        }
        return hash;
    }


    public String toString() {
        return "Block" + '\n' +
                "hash=" + hash + '\n' +
                "previousHash=" + previousHash + '\n' +
                "timeStamp=" + timeStamp + '\n' +
                "transactions=" + '\n' + transactions + '\n' +
                "nonce=" + nonce + '\n' +
                "merkleTree=" + merkleTree + '\n' + '\n';
    }
}
