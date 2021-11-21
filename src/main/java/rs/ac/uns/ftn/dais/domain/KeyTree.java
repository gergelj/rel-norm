package rs.ac.uns.ftn.dais.domain;

import java.util.HashSet;
import java.util.Set;

public class KeyTree {
    private KeyTreeNode root;
    public KeyTree(LabelSet rootLabels, FunctionalDependencySet functionalDependencies) {
        this.root = new KeyTreeNode(rootLabels);
        this.root.calculateKeys(rootLabels, functionalDependencies);
    }

    public Set<LabelSet> getKeys() {
        Set<LabelSet> keySet = new HashSet<>();

        root.evaluate(keySet);
        return keySet;
    }
}
