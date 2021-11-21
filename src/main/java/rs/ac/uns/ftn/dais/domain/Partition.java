package rs.ac.uns.ftn.dais.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Partition {
    private Set<LabelSet> keys;
    private FunctionalDependencySet values;

    public Partition(LabelSet key, FunctionalDependencySet values) {
        this.keys = new HashSet<>();
        keys.add(key);
        this.values = values;
    }

    public void add(LabelSet key, FunctionalDependencySet values) {
        this.keys.add(key);
        this.values = this.values.union(values);
    }

    public void addAll(FunctionalDependencySet functionalDependencies) {
        functionalDependencies.stream()
                .filter(fd -> keys.contains(fd.getLeftSide()))
                .forEach(fd -> values.add(fd));
    }

    public void removeAll(FunctionalDependencySet items) {
        this.values.removeAll(items);
    }

    public void remove(FunctionalDependency functionalDependency) {
        this.values.remove(functionalDependency);
    }

    public FunctionalDependencySet getValues() {
        return values;
    }

    public boolean hasMultipleKeys() {
        return keys.size() > 1;
    }

    public FunctionalDependencySet descartesProductNonTrivial() {
        FunctionalDependencySet descartes = new FunctionalDependencySet();
        if(!hasMultipleKeys()) {
            return descartes;
        }

        for(LabelSet labels: keys) {
            for(LabelSet other: keys) {
                FunctionalDependency fd1 = new FunctionalDependency(labels, other);
                FunctionalDependency fd2 = new FunctionalDependency(other, labels);
                if(!fd1.isTrivial()) {
                    descartes.add(fd1);
                }

                if(!fd2.isTrivial()) {
                    descartes.add(fd2);
                }
            }
        }

        return descartes;
    }

    public LabelSet getLabels() {
        return values.getLabels();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partition partition = (Partition) o;
        return keys.equals(partition.keys) && values.equals(partition.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keys, values);
    }

    @Override
    public String toString() {
        return "G " + keys.toString() + " = " + values.toString();
    }

}
