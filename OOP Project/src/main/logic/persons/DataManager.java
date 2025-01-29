package main.logic.persons;

import java.util.List;

/**
 * collection for persons and candidates
 */
public interface DataManager {
    List<Person> getAllPersons();

    List<Candidate> getAllCandidates();

    /**
     * collection checking
     * @return
     */
    default boolean isEmpty() {
        if (getAllPersons().size() == 0) {
            System.out.println("Person Collection is empty!");
            return true;
        }
        return false;
    }
}
