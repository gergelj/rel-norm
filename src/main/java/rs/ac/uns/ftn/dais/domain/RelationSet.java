package rs.ac.uns.ftn.dais.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RelationSet extends GenericSet<Relation> {

    private String name;

    public RelationSet(String name) {
        super();
        this.name = name;
    }

    public RelationSet(String name, Set<Relation> relations) {
        super(relations);
        this.name = name;
    }

    public boolean hasKey(Set<LabelSet> keys) {
        return keys.stream().anyMatch(key -> items.stream().anyMatch(relation -> relation.hasKey(key)));
    }

    public void join(FunctionalDependencySet startingDependencies) {
        Map<LabelSet, RelationSet> keyClosures = new HashMap<>();
        for(Relation relation : items) {
            for(LabelSet key: relation.getKeys()) {
                LabelSet keyClosure = startingDependencies.closure(key);
                if(!keyClosures.containsKey(keyClosure)) {
                    keyClosures.put(keyClosure, new RelationSet("S"));
                }

                keyClosures.get(keyClosure).add(relation);
            }
        }

        clear();
        int order = 1;
        for (RelationSet relations: keyClosures.values()) {
            if(relations.size() == 1) {
                Relation r = relations.stream().findFirst().get();
                r.setOrder(order++);
                add(r);
            } else {
                LabelSet labels = new LabelSet();
                labels = relations.stream().map(Relation::getLabels).reduce(labels, LabelSet::union);

                Printer.print("Preserving lossless join by joining relations", relations, TaskMode.DECOMPOSITION);

                FunctionalDependencySet functionalDependencies = new FunctionalDependencySet();
                //TODO: Check if FD set should be (1) union of all sets, (2) projection of the initial FD set on the union of labels or (3) FDs depending from key only
                // (1)
                //functionalDependencies = relations.stream().map(Relation::getFunctionalDependencies).reduce(functionalDependencies, FunctionalDependencySet::union);
                // (2)
                // functionalDependencies = startingDependencies.projection(labels);
                // (3)
                LabelSet finalLabels = labels;
                relations.stream().map(Relation::getKeys).forEach(keyS -> keyS.stream().map(key -> new FunctionalDependency(key, finalLabels)).forEach(functionalDependencies::add));

                add(new Relation("N", order++, labels, functionalDependencies));
            }
        }
    }

    public void printRefIntegrity() {
        Printer.print("*********************", TaskMode.DECOMPOSITION | TaskMode.SYNTHESIS);
        Printer.print("Referential integrity", TaskMode.DECOMPOSITION | TaskMode.SYNTHESIS);
        for(Relation relation: items) {
            for(Relation other: items) {
                if(!relation.equals(other)) {
                    for (LabelSet key: relation.getKeys()) {
                        if(other.getLabels().containsAll(key)) {
                            Printer.print(String.format("%s[%s] ⊆ %s[%s]", other.getFullName(), key, relation.getFullName(), key), TaskMode.DECOMPOSITION | TaskMode.SYNTHESIS);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return name + " = {\n" + String.join("\n", items.stream().map(Relation::toString).toList()) + "\n}";
    }
}
