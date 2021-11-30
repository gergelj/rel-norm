package rs.ac.uns.ftn.dais.domain;

import java.util.Arrays;
import java.util.Collection;

public class FunctionalDependencySet extends GenericSet<FunctionalDependency> {

    public FunctionalDependencySet() {
        super();
    }

    public FunctionalDependencySet(FunctionalDependency[] functionalDependencies) {
        super();
        this.addAll(Arrays.asList(functionalDependencies));
    }

    public FunctionalDependencySet(FunctionalDependencySet other) {
        super(other);
    }

    public FunctionalDependencySet(FunctionalDependency item) {
        super();
        add(item);
    }

    public LabelSet closure(LabelSet labels) {
        LabelSet result = new LabelSet(labels);
        LabelSet lastResult;
        do {
            lastResult = new LabelSet(result);

            this.items.forEach(fd -> {
                if(result.containsAll(fd.getLeftSide())) {
                    result.addAll(fd.getRightSide());
                }
            });
        } while(!lastResult.equals(result));
        return result;
    }

    public FunctionalDependencySet closure() {
        LabelSet labels = new LabelSet();
        items.forEach(fd -> {
            labels.addAll(fd.getLeftSide());
            labels.addAll(fd.getRightSide());
        });

        return projection(labels);
    }

    public FunctionalDependencySet projection(LabelSet labels) {
        FunctionalDependencySet projection = new FunctionalDependencySet();
        for(LabelSet subset: labels.getPowerSet()) {
            LabelSet closure = this.closure(subset);
            closure.retainAll(labels);
            closure.removeAll(subset);
            if(!closure.isEmpty()) {
                FunctionalDependency fd = new FunctionalDependency(subset, closure);
                projection.add(fd);
            }
        }
        return projection;
    }

    public FunctionalDependencySet getReducedRight() {
        FunctionalDependencySet reduced = new FunctionalDependencySet();
        items.forEach(
                fd -> fd.getRightSide().forEach(
                        label -> reduced.add(new FunctionalDependency(fd.getLeftSide(), label))
                )
        );

        return reduced;
    }

    public FunctionalDependencySet getCanonicalSet() {
        //Dekompozicija desnih strana skupa fz
        FunctionalDependencySet canonicalSet = getReducedRight();

        //Redukcija levih strana fz
        FunctionalDependencySet reducedCanonicalSet = new FunctionalDependencySet(canonicalSet);
        canonicalSet.forEach(
                fd -> fd.getLeftSide().getPowerSetSorted().stream()
                        .filter(subset -> !subset.isEmpty() && !subset.equals(fd.getLeftSide()))
                        .map(subset -> new FunctionalDependency(subset, fd.getRightSide()))
                        .filter(partial -> partial.isLogicalConsequenceOf(reducedCanonicalSet))
                        .limit(1) //We must remove only the first partial fd (with the smallest possible subset on the left) that is a logical consequence of the canonical set.
                        .forEach(partial -> {
                            Printer.print("Partial removed: " + fd.toString());
                            Printer.print("Reduced to: " + partial.toString());
                            reducedCanonicalSet.add(partial);
                            reducedCanonicalSet.remove(fd);
                        })
        );

        //Eliminacija redundantnih fz
        FunctionalDependencySet setToRemove = new FunctionalDependencySet();
        reducedCanonicalSet.stream()
                .filter(fd -> fd.isLogicalConsequenceOf(reducedCanonicalSet.difference(fd).difference(setToRemove)))
                .forEach(setToRemove::add);

        reducedCanonicalSet.removeAll(setToRemove);
        Printer.print("Redundant FDs:", setToRemove);

        return reducedCanonicalSet;
    }

    public boolean equivalent(FunctionalDependencySet other) {
        return this.stream().allMatch(fd -> fd.isLogicalConsequenceOf(other)) && other.stream().allMatch(fd -> fd.isLogicalConsequenceOf(this));
    }

    public LabelSet getLabels() {
        LabelSet labels = new LabelSet();
        items.forEach(fd -> {
            labels.addAll(fd.getLeftSide());
            labels.addAll(fd.getRightSide());
        });

        return labels;
    }

    @Override
    public String toString() {
        String itStr = String.join(", ", items.stream().map(FunctionalDependency::toString).toList());
        return "{ " + itStr + " }";
    }

    @Override
    public boolean add(FunctionalDependency functionalDependency) {
        if(functionalDependency.isTrivial()) {
            return false;
        } else {
            return items.add(functionalDependency);
        }
    }

    @Override
    public boolean addAll(Collection<? extends FunctionalDependency> c) {
        return items.addAll(c.stream().filter(fd -> !fd.isTrivial()).toList());
    }

    public FunctionalDependencySet union(FunctionalDependencySet other) {
        FunctionalDependencySet union = new FunctionalDependencySet();
        union.addAll(this);
        union.addAll(other);
        return union;
    }

    public FunctionalDependencySet intersection(FunctionalDependencySet other) {
        FunctionalDependencySet intersection = new FunctionalDependencySet();
        intersection.addAll(this);
        intersection.retainAll(other);
        return intersection;
    }

    public FunctionalDependencySet difference(FunctionalDependencySet other) {
        FunctionalDependencySet difference = new FunctionalDependencySet();
        difference.addAll(this);
        difference.removeAll(other);
        return difference;
    }

    public FunctionalDependencySet difference(FunctionalDependency other) {
        return this.difference(new FunctionalDependencySet(other));
    }
}
