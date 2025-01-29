package main.logic.persons;

import main.logic.blockchain.Block;
import main.logic.blockchain.Blockchain;
import main.logic.blockchain.BlockchainBridge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * class for all citizens
 */
public class Person implements DataManager {
    private int token = 1;
    private final String name;
    private final String familyName;
    private final int[] ageDDMMYYYY;
    private final String pasNumber;
    static BlockchainBridge blockchainBridge;
    public String getHash_of_vote() {
        return hash_of_vote;
    }

    public void setHash_of_vote(String hash_of_vote) {
        this.hash_of_vote = hash_of_vote;
    }

    private String hash_of_vote = " You haven't voted yet";
    public static List<Person> personList = new ArrayList<>();

    /**
     * if token count != 0, person vote for candidate: send token to him, and creating new block for blockchain
     * @param candidate_choised
     * @param list
     * @param blockchainBridge
     */
    public void vote(Candidate candidate_choised, List<Candidate> list,BlockchainBridge blockchainBridge) {
        for (Candidate candidate : list) {
            if ((Objects.equals(candidate.getFamilyName(), candidate_choised.getFamilyName())) && (Objects.equals(candidate.getPasNumber(), candidate_choised.getPasNumber()))) {
                candidate.setToken(candidate.getToken() + 1);
                this.token = 0;
                Block block = new Block(blockchainBridge.getLatestBlock().getIndex(), blockchainBridge.getLatestBlock().getHash(), candidate_choised.getPasNumber() + this.pasNumber, candidate.getPasNumber());
                blockchainBridge.addBlock(block);
            }
        }
    }

    public int[] getAgeDDMMYYYY() {
        return ageDDMMYYYY;
    }

    public String getPasNumber() {
        return pasNumber;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }


    public String getFamilyName() {
        return familyName;
    }


    public Person(String name, String familyName, int[] ageDDMMYYYY, String pasNumber) {
        this.name = name;
        this.familyName = familyName;
        this.ageDDMMYYYY = ageDDMMYYYY;
        this.pasNumber = pasNumber;
        if (!(this instanceof Candidate)) {
            personList.add(this);
        }
    }

    /**
     * cloning
     * @return
     */
    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

    @Override
    public List<Person> getAllPersons() {
        return personList;
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return new ArrayList<>();
    }
}
