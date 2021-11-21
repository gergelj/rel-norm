package rs.ac.uns.ftn.dais.domain;

import java.util.Objects;

public class FunctionalDependency {
    private final LabelSet leftSide;
    private final LabelSet rightSide;

    public FunctionalDependency(String[] leftSide, String[] rightSide) {
        this.leftSide = new LabelSet(leftSide);
        this.rightSide = new LabelSet(rightSide);
    }

    public FunctionalDependency(LabelSet leftSide, LabelSet rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public FunctionalDependency(LabelSet leftSide, Label rightSide) {
        this.leftSide = leftSide;
        this.rightSide = new LabelSet(rightSide);
    }

    public LabelSet getLeftSide() {
        return leftSide;
    }

    public LabelSet getRightSide() {
        return rightSide;
    }

    public boolean isTrivial() {
        return leftSide.isEmpty() || leftSide.containsAll(rightSide);
    }

    public boolean isLogicalConsequenceOf(FunctionalDependencySet functionalDependencies) {
        LabelSet closing = functionalDependencies.closure(leftSide);
        return closing.containsAll(rightSide);
    }

    @Override
    public String toString() {
        return leftSide.toString() + " -> " + rightSide.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionalDependency that = (FunctionalDependency) o;
        return leftSide.equals(that.leftSide) && rightSide.equals(that.rightSide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftSide, rightSide);
    }
}
