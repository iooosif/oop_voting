package main.logic;

/**
 * exception, if person already vote
 */
public class AlreadyVotedException extends RuntimeException {
    public AlreadyVotedException(String message) {
        super(message);
    }
}