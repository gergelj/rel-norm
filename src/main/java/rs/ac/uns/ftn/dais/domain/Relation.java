package rs.ac.uns.ftn.dais.domain;

import rs.ac.uns.ftn.dais.exception.CriteriaNotSatisfiedException;

import java.util.Objects;
import java.util.Set;

public class Relation {
    private final String name;
    private int order = 0;
    private final LabelSet labels;
    private Set<LabelSet> keys;
    private FunctionalDependencySet functionalDependencies;

    public Relation(String name, String[] labels, FunctionalDependency[] functionalDependencies) {
        this.name = name;
        this.labels = new LabelSet(labels);
        this.functionalDependencies = new FunctionalDependencySet(functionalDependencies);
        this.calculateKeys();
    }

    public Relation(String name, LabelSet labels, FunctionalDependencySet functionalDependencies) {
        this.name = name;
        this.labels = labels;
        this.functionalDependencies = functionalDependencies;
        calculateKeys();
    }

    public Relation(String name, int order, LabelSet labels, FunctionalDependencySet functionalDependencies) {
        this(name, labels, functionalDependencies);
        this.order = order;
    }

    public Relation(String name, LabelSet labels, Set<LabelSet> keys) {
        //WARNING: Functional dependency set is empty in this case, so key generating doesn't make sense
        this.name = name;
        this.labels = labels;
        this.keys = keys;
        functionalDependencies = new FunctionalDependencySet();
    }

    private void calculateKeys() {
        KeyTree kt = new KeyTree(labels, functionalDependencies);
        this.keys = kt.getKeys();
    }

    public FunctionalDependency getDecompositionFunctionalDependency() {
        for(FunctionalDependency fd : functionalDependencies) {
            if(satisfiesP1(fd)) {
                return fd;
            }
        }

        for(FunctionalDependency fd : functionalDependencies) {
            if(satisfiesP2(fd)) {
                return fd;
            }
        }

        for(FunctionalDependency fd : functionalDependencies) {
            if(satisfiesP3(fd)) {
                return fd;
            }
        }

        throw new CriteriaNotSatisfiedException("No functional dependency satisfies P1, P2 or P3 criteria.");
    }

    public boolean isFirstNF() {
        return true;
    }

    public boolean isSecondNF() {
        LabelSet nonPrimaryLabels = getNonPrimaryLabels();
        if(nonPrimaryLabels.isEmpty()) {
            return true;
        }

        return functionalDependencies.stream().allMatch(fd -> {
           if(nonPrimaryLabels.containsAll(fd.getRightSide())) {
               return getKeys().stream().allMatch(key -> fd.getLeftSide().containsAll(key) || fd.getLeftSide().stream().noneMatch(key::contains));
           } else {
               return true;
           }
        });
    }

    public boolean isThirdNF() {
        LabelSet nonPrimaryLabels = getNonPrimaryLabels();
        if(nonPrimaryLabels.isEmpty()) {
            return true;
        }

        return functionalDependencies.stream().allMatch(fd -> {
            if(nonPrimaryLabels.containsAll(fd.getRightSide())) {
                return getKeys().stream().anyMatch(key -> fd.getLeftSide().containsAll(key));
            } else {
                return true;
            }
        });
    }

    public boolean isBCNF() {
        return functionalDependencies.stream().filter(fd -> !fd.isTrivial()).allMatch(fd -> getKeys().stream().anyMatch(key -> fd.getLeftSide().containsAll(key)));
    }

    private LabelSet getPrimaryLabels() {
        LabelSet primaryLabels = new LabelSet();
        keys.forEach(primaryLabels::addAll);
        return primaryLabels;
    }

    private LabelSet getNonPrimaryLabels() {
        LabelSet allLabels = new LabelSet(labels);
        allLabels.removeAll(getPrimaryLabels());
        return allLabels;
    }

    public boolean satisfiesP1(FunctionalDependency fd) {
        return !fd.isTrivial() &&
                keys.stream().noneMatch(k -> fd.getLeftSide().containsAll(k)) &&
                functionalDependencies.equivalent(functionalDependencies.projection(fd.getLeftSide().union(labels.difference(fd.getRightSide())))
                        .union(functionalDependencies.projection(fd.getLeftSide().union(fd.getRightSide()))));
    }

    public boolean satisfiesP2(FunctionalDependency fd) {
        return !fd.isTrivial() &&
                !fd.getLeftSide().union(fd.getRightSide()).containsAll(labels) &&
                functionalDependencies.equivalent(functionalDependencies.projection(fd.getLeftSide().union(labels.difference(fd.getRightSide())))
                        .union(functionalDependencies.projection(fd.getLeftSide().union(fd.getRightSide()))));
    }

    public boolean satisfiesP3(FunctionalDependency fd) {
        return !fd.isTrivial() && keys.stream().noneMatch(k -> fd.getLeftSide().containsAll(k));
    }

    public NormalForm getNormalForm() {
        return isBCNF() ? NormalForm.BCNF :
                isThirdNF() ? NormalForm.THIRD_NF :
                        isSecondNF() ? NormalForm.SECOND_NF :
                                isFirstNF() ? NormalForm.FIRST_NF : NormalForm.UNDEFINED;
    }

    public Set<LabelSet> getKeys() {
        return keys;
    }

    public boolean hasKey(LabelSet key) {
        return keys.stream().anyMatch(k -> k.equals(key));
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return name + order;
    }

    public LabelSet getLabels() {
        return labels;
    }

    public FunctionalDependencySet getFunctionalDependencies() {
        return functionalDependencies;
    }

    public int nextOrder() {
        return order + 1;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return labels.equals(relation.labels) && keys.equals(relation.keys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labels, keys);
    }

    @Override
    public String toString() {
        return this.name + (this.order == 0 ? "" : this.order) +
                " ({ " + this.labels + " }, " +
                this.functionalDependencies +
                " ) - Keys: { "
                + this.keys + " } - [" + getNormalForm() + "]";
    }

    public FunctionalDependencySet getFunctionalDependenciesP1() {
        FunctionalDependencySet satisfyCriteria = new FunctionalDependencySet();
        functionalDependencies.stream().filter(this::satisfiesP1).forEach(satisfyCriteria::add);
        return satisfyCriteria;
    }

    public FunctionalDependencySet getFunctionalDependenciesP2() {
        FunctionalDependencySet satisfyCriteria = new FunctionalDependencySet();
        functionalDependencies.stream().filter(this::satisfiesP2).forEach(satisfyCriteria::add);
        return satisfyCriteria;
    }

    public FunctionalDependencySet getFunctionalDependenciesP3() {
        FunctionalDependencySet satisfyCriteria = new FunctionalDependencySet();
        functionalDependencies.stream().filter(this::satisfiesP3).forEach(satisfyCriteria::add);
        return satisfyCriteria;
    }

    public void setFunctionalDependencies(FunctionalDependencySet canonicalSet) {
        functionalDependencies = canonicalSet;
    }

    public FunctionalDependencySet getDecompositionFirstSubset(FunctionalDependency selected, LabelSet labels) {
        //TODO: Proveriti da li važi pravilo ako obeležje na desnoj strani fz (A->B) nalazi se samo jedanput u skupu fz
        // tada projekcije skupa fz na AB i A(R/B) su {A->B} i F/{A->B} redom.
        if(rightSideIsOnlyPresentOnce(selected)) {
            return new FunctionalDependencySet(selected);
        } else {
            return functionalDependencies.projection(labels);
        }
    }

    public FunctionalDependencySet getDecompositionSecondSubset(FunctionalDependency selected, LabelSet labels) {
        if(rightSideIsOnlyPresentOnce(selected)) {
            return functionalDependencies.difference(selected);
        } else {
            return functionalDependencies.projection(labels);
        }
    }

    private boolean rightSideIsOnlyPresentOnce(FunctionalDependency functionalDependency) {
        LabelSet rightSide = functionalDependency.getRightSide();
        FunctionalDependencySet others = functionalDependencies.difference(functionalDependency);
        return rightSide.size() == 1 && others.stream().noneMatch(fd -> fd.getRightSide().containsAll(rightSide) || fd.getLeftSide().containsAll(rightSide));
    }
}
