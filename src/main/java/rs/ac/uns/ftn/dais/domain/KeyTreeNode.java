package rs.ac.uns.ftn.dais.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class KeyTreeNode {
    private LabelSet node;
    private boolean candidateKey = true;
    private List<KeyTreeNode> children;

    public KeyTreeNode(LabelSet node) {
        this.node = new LabelSet(node);
        this.children = new ArrayList<>();
    }

    public void calculateKeys(LabelSet rootLabels, FunctionalDependencySet functionalDependencies) {
        boolean isCandidate = functionalDependencies.closure(node).equals(rootLabels);

        if(isCandidate) {
            List<Label> labels = node.stream().toList();
            for(Label label: labels) {
                LabelSet reducedLabels = new LabelSet(node);
                reducedLabels.remove(label);
                KeyTreeNode child = new KeyTreeNode(reducedLabels);
                children.add(child);
                child.calculateKeys(rootLabels, functionalDependencies);
            }
        } else {
            this.candidateKey = false;
        }
    }

    public boolean isCandidateKey() {
        return candidateKey;
    }

    public void evaluate(Set<LabelSet> keySet) {
        if(atLeastOneChildIsCandidate()){
            for(KeyTreeNode child: children) {
                if(child.isCandidateKey()) {
                    child.evaluate(keySet);
                }
            }
        } else {
            keySet.add(node);
        }
    }

    private boolean atLeastOneChildIsCandidate() {
        return children.stream().anyMatch(KeyTreeNode::isCandidateKey);
    }
}
