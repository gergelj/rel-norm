package rs.ac.uns.ftn.dais.exception;

public class CriteriaNotSatisfiedException extends RuntimeException {
    public CriteriaNotSatisfiedException() {
        super();
    }

    public CriteriaNotSatisfiedException(String message) {
        super(message);
    }
}
