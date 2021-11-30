package rs.ac.uns.ftn.dais.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KeyTreeNode {
    private final LabelSet node;
    private boolean isCandidateKey = false;
    private List<KeyTreeNode> children;
    private final Map<LabelSet, KeyTreeNode> processed;

    public KeyTreeNode(LabelSet node, Map<LabelSet, KeyTreeNode> processed) {
        this.node = new LabelSet(node);
        this.children = new ArrayList<>();
        this.processed = processed;
    }

    public void calculateKeys(LabelSet rootLabels, FunctionalDependencySet functionalDependencies) {
        if(processed.containsKey(node)) {
            KeyTreeNode processedNode = processed.get(node);
            this.isCandidateKey = processedNode.isCandidateKey;
            this.children = processedNode.children;
            return;
        }

        isCandidateKey = functionalDependencies.closure(node).equals(rootLabels);

        if(isCandidateKey) {
            for(Label label: node) {
                LabelSet reducedLabels = node.difference(label);
                KeyTreeNode child = new KeyTreeNode(reducedLabels, processed);
                children.add(child);
                child.calculateKeys(rootLabels, functionalDependencies);
            }
        }

        processed.put(node, this);
    }

    public boolean isCandidateKey() {
        return isCandidateKey;
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
