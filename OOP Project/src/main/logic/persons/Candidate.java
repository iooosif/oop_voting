package main.logic.persons;

import java.util.ArrayList;
import java.util.List;

/**
 * class for candidates for election
 */
public class Candidate extends Person implements DataManager, Comparable<Candidate> {
    public static List<Candidate> candidateList = new ArrayList<>();
    private int token = 0;

    public Candidate(String name, String familyName, int[] ageDDMMYYYY, String pasNumber) {
        super(name, familyName, ageDDMMYYYY, pasNumber);
        candidateList.add(this);
    }

    @Override
    public int getToken() {
        return token;
    }

    @Override
    public void setToken(int token) {
        this.token = token;
    }


    @Override
    public List<Person> getAllPersons() {
        return new ArrayList<>();
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return candidateList;
    }

    /**
     * to find correct candidate
     * @param other
     * @return
     */
    @Override
    public int compareTo(Candidate other) {
        return Integer.compare(this.token, other.token);
    }
}
