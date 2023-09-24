import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Blockchain {
    private List<Block> chain = new ArrayList<>();
    private int difficulty;

    public Blockchain(int difficulty) {
        this.difficulty = difficulty;
        chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return new Block("0", Arrays.asList());
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        chain.add(newBlock);
    }

    public List<Block> getChain() {
        return chain;
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!currentBlock.getPreviousHash().equals(previousBlock.calculateHash())) {
                System.out.println("Chain is not valid.");
                return false;
            }
            if (!currentBlock.calculateHash().equals(currentBlock.getHash())) {
                System.out.println("Chain is not valid.");
                return false;
            }
        }
        System.out.println("Chain is valid.");
        return true;
    }
}