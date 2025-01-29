package main.logic.blockchain;

import java.util.ArrayList;

/**
 * class for creating blockchain
 */
public class Blockchain implements BlockchainBridge {
    private static Blockchain instance;
    private final ArrayList<Block> blockchain;

    private Blockchain() {
        blockchain = new ArrayList<>();
    }

    public static synchronized Blockchain getInstance() {
        if (instance == null) {
            instance = new Blockchain();
        }
        return instance;
    }

    /**
     * getting blockchain
     * @return
     */
    public ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    /**
     * function for creating new blocks
     * @param newBlock
     */
    public synchronized void addBlock(Block newBlock) {
        blockchain.add(newBlock);
        if (!isChainValid()) {
            blockchain.remove(newBlock);
            System.out.println("Wrong block deleted");
        }
    }

    /**
     * latest block
     *
     */
    public Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    /**
     * check blockchain validation
     * @return isValid[0]
     */
    public boolean isChainValid() {
        // Using a lambda with forEach to check the integrity of the chain
        final boolean[] isValid = {true}; // Lambda expressions cannot change variables directly, so we use an array
        blockchain.stream().skip(1).forEach((currentBlock) -> {
            Block previousBlock = blockchain.get(blockchain.indexOf(currentBlock) - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("HASH_ERROR");
                isValid[0] = false;
            } else if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("CONNECTING_ERROR");
                isValid[0] = false;
            }
        });

        return isValid[0]; // Return the result of the check
    }
}
