package rs.ac.uns.ftn.dais.input;

import java.util.List;

public class FunctionalDependencyInput {
    private List<String> left;
    private List<String> right;

    public FunctionalDependencyInput() {

    }

    public List<String> getLeft() {
        return left;
    }

    public List<String> getRight() {
        return right;
    }
}
