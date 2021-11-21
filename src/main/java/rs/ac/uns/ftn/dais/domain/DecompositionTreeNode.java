package rs.ac.uns.ftn.dais.domain;

import java.util.ArrayList;
import java.util.List;

public class DecompositionTreeNode {
    private Relation relation;
    private List<DecompositionTreeNode> children = new ArrayList<>();
    public static boolean INTERACTIVE_MODE = false;

    public DecompositionTreeNode(Relation relation) {
        this.relation = relation;
    }

    public Relation getRelation() {
        return relation;
    }

    public void decompose() {
        if(relation.isBCNF() || relation.getFunctionalDependencies().isEmpty()) {
            return;
        }

        Printer.print("Decomposing relation:", relation);

        FunctionalDependencySet canonicalSet = relation.getFunctionalDependencies().getCanonicalSet();
        Printer.print("Canonical set:", canonicalSet);
        relation.setFunctionalDependencies(canonicalSet);

        FunctionalDependency selected;
        if(INTERACTIVE_MODE) {
            printAvailableSets();
            selected = FunctionalDependencyParser.parse();
        } else {
            selected = relation.getDecompositionFunctionalDependency();
            Printer.print("Chosen FD:", selected);
            Printer.print("Satisfies P1 = " + relation.satisfiesP1(selected));
            Printer.print("Satisfies P2 = " + relation.satisfiesP2(selected));
            Printer.print("Satisfies P3 = " + relation.satisfiesP3(selected));
        }

        LabelSet subLabels1 = selected.getLeftSide().union(selected.getRightSide());
        Relation subRelation1 = new Relation(relation.getName(),
                                                relation.nextOrder(),
                                                subLabels1,
                                                relation.getDecompositionFirstSubset(selected, subLabels1));

        LabelSet subLabels2 = selected.getLeftSide().union(relation.getLabels().difference(selected.getRightSide()));
        Relation subRelation2 = new Relation(relation.getName(),
                relation.nextOrder() + 1,
                subLabels2,
                relation.getDecompositionSecondSubset(selected, subLabels2));

        Printer.print("-------------------------------------");
        Printer.print("Sub relations:");
        Printer.print(subRelation1);
        Printer.print(subRelation2);
        Printer.print("*************************************");

        DecompositionTreeNode child1 = new DecompositionTreeNode(subRelation1);
        DecompositionTreeNode child2 = new DecompositionTreeNode(subRelation2);
        children.add(child1);
        children.add(child2);

        child1.decompose();
        child2.decompose();
    }

    public void evaluate(RelationSet relations) {
        if(children.isEmpty()) {
            relations.add(relation);
        } else {
            for(DecompositionTreeNode child: children) {
                child.evaluate(relations);
            }
        }
    }

    private void printAvailableSets() {
        FunctionalDependencySet satisfied = relation.getFunctionalDependenciesP1();
        if(!satisfied.isEmpty()) {
            Printer.print("FDs which satisfy P1:", satisfied);
        } else {
            Printer.print("No FDs satisfy P1");
            satisfied = relation.getFunctionalDependenciesP2();
            if(!satisfied.isEmpty()) {
                Printer.print("FDs which satisfy P2:", satisfied);
            } else {
                Printer.print("No FDs satisfy P2");
                satisfied = relation.getFunctionalDependenciesP3();
                if(!satisfied.isEmpty()) {
                    Printer.print("FDs which satisfy P3:", satisfied);
                } else {
                    Printer.print("No FDs satisfy P3");
                    Printer.print("You better end the algorithm altogether :/");
                }
            }
        }
    }
}
