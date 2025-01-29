package main.logic.work;

import main.logic.persons.Candidate;
import main.logic.persons.Person;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

/**
 * results of elections
 */
public class Results {
    private static Results instance;
    private final List<Candidate> candidates;
    private final List<Person> persons;

    private Results(List<Candidate> candidates, List<Person> persons) {
        this.candidates = candidates;
        this.persons = persons;
    }

    public static synchronized Results getInstance(List<Candidate> candidates, List<Person> persons) {
        if (instance == null) {
            instance = new Results(candidates, persons);
        }
        return instance;
    }

    /**
     * result for concrete
     * @param pas_cand
     * @return
     */
    public double results_for_concrete(String pas_cand) {
        int n = 0;
        for (Candidate candidate : candidates) {
            n += candidate.getToken();
        }
        for (Candidate candidate : candidates) {
            if (Objects.equals(candidate.getPasNumber(), pas_cand)) {
                return (double) candidate.getToken() != 0 ? ((double) candidate.getToken() / (double) n) : 0;
            }
        }
        return 0;
    }

    /**
     *percentage of voters
     */
    public double percentage_of_voters() {
        int n = 0;
        for (Person person : persons) {
            n += (person.getToken() == 0) ? 1 : 0;
        }
        return 100 * ((double) n / (double) persons.size());
    }

    /**
     *
     *returning  winner of elections
     */
    public String winner() {
        double max = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        int ind = 0;
        for (int i = 0; i < candidates.size(); i++) {
            if (results_for_concrete(candidates.get(i).getPasNumber()) * 100 > max) {
                max = results_for_concrete(candidates.get(i).getPasNumber()) * 100;
                ind = i;
            }
        }
        return "Winner is " + candidates.get(ind).getName() + " " + candidates.get(ind).getFamilyName() + " with " + df.format(max) + "% of votes";
    }
}
