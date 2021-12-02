package rs.ac.uns.ftn.dais.domain;

public class DecompositionTree {
    private DecompositionTreeNode root;

    public DecompositionTree(Relation relation) {
        this.root = new DecompositionTreeNode(relation);
    }

    public RelationSet decompose() {
        this.root.decompose();
        RelationSet relations = getResult();
        relations.join(root.getRelation().getFunctionalDependencies());
        addLosslessRelations(relations);
        return relations;
    }

    private RelationSet addLosslessRelations(RelationSet relations) {
        //Check for lossless join
        Relation initialRelation = root.getRelation();
        if(!relations.hasKey(initialRelation.getKeys())) {
            Relation lossless = new Relation(initialRelation.getName(), 0, initialRelation.getKeys().stream().findAny().get(), new FunctionalDependencySet());
            Printer.print("Additional relation to preserve lossless connectivity:", lossless, TaskMode.DECOMPOSITION);
            relations.add(lossless);
        }

        return relations;
    }

    public RelationSet decomposeInt() {
        DecompositionTreeNode.INTERACTIVE_MODE = true;
        return decompose();
    }

    private RelationSet getResult() {
        RelationSet relations = new RelationSet("S");
        this.root.evaluate(relations);
        return relations;
    }


}
