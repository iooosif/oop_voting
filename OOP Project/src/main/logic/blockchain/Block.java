package main.logic.blockchain;

import java.util.Date;

/**
 * class for creating blocks for blockchain
 */
public class Block {
    private final int index;

    /**
     * getting hash of previous block, using for validation
     * @return previousHash
     */
    public String getPreviousHash() {
        return previousHash;
    }

    private final String previousHash;
    private final String hash;
    private final String data;
    private final String candidate_pas;

    public int getIndex() {
        return index;
    }

    public String getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    private final long timestamp;

    public Block(int index, String previousHash, String data, String candidate_pas) {
        this.index = index;
        this.previousHash = previousHash;
        this.data = data;
        this.candidate_pas = candidate_pas;
        this.timestamp = new Date().getTime();
        this.hash = calculateHash();
    }

    /**
     * geting candidate in this block
     *
     */
    public String getCandidate_pas() {
        return candidate_pas;
    }

    /**
     * creating hash using time, previous hash and voise
     */
    // calculating hash
    public String calculateHash() {

        return StringUtil.applySha256(
                previousHash +
                        Long.toString(timestamp) +
                        Integer.toString(index) +
                        data
        );
    }

    /**
     *  getting hash
     * @return hash
     */
    public String getHash() {
        return hash;
    }

}
