package main.logic.blockchain;

import main.logic.blockchain.Block;
import java.util.List;

/**
 * bridge design pattern for blockchain
 */
public interface BlockchainBridge {
    void addBlock(Block block);  // Adding a new block to the blockchain
    Block getLatestBlock();  // Getting the latest block
    List<Block> getBlockchain();  // Getting the entire chain
    boolean isChainValid();  // Checking the validity of the chain
}
