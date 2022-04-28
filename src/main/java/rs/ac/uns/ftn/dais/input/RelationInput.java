package rs.ac.uns.ftn.dais.input;

import java.util.List;

public class RelationInput {
    private String name;
    private List<String> labels;
    private List<FunctionalDependencyInput> functionalDependencies;

    public RelationInput() {

    }

    public String getName() {
        return name;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<FunctionalDependencyInput> getFunctionalDependencies() {
        return functionalDependencies;
    }

}
