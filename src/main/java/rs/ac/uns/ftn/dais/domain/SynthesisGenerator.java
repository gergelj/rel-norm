package rs.ac.uns.ftn.dais.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SynthesisGenerator {

    private Relation initialRelation;
    private FunctionalDependencySet canonicalSet;

    public SynthesisGenerator(Relation relation) {
        this.initialRelation = relation;
    }

    public RelationSet synthesize() {
        Printer.print("Decomposing relation by synthesis", initialRelation);
        this.canonicalSet = initialRelation.getFunctionalDependencies().getCanonicalSet();
        Printer.print("Canonical set", canonicalSet);
        Set<Partition> partitions = transformCanonicalSet();
        RelationSet relations = generateRelationSet(partitions);
        return relations;
    }

    private Set<Partition> transformCanonicalSet() {
        Map<LabelSet, FunctionalDependencySet> partitioned = partition(canonicalSet);
        Printer.print("Partitions:", partitioned);
        Set<Partition> joined = joinPartitions(partitioned);
        Printer.print("Partitions after joining:", joined);
        Set<Partition> cleaned = removeTransitiveDependencies(joined);
        return cleaned;
    }

    private Map<LabelSet, FunctionalDependencySet> partition(FunctionalDependencySet canonicalSet) {
        Map<LabelSet, FunctionalDependencySet> partition = new HashMap<>();
        canonicalSet.forEach(fd -> {
            if(!partition.containsKey(fd.getLeftSide())) {
                partition.put(fd.getLeftSide(), new FunctionalDependencySet());
            }
            partition.get(fd.getLeftSide()).add(fd);
        });

        return partition;
    }

    private Set<Partition> joinPartitions(Map<LabelSet, FunctionalDependencySet> partitioned) {
        Map<LabelSet, Partition> partitions = new HashMap<>();
        for(LabelSet labels : partitioned.keySet()) {
            LabelSet closure = this.canonicalSet.closure(labels);
            FunctionalDependencySet functionalDependencies = partitioned.get(labels);
            if(!partitions.containsKey(closure)) {
                partitions.put(closure, new Partition(labels, functionalDependencies));
            }
            partitions.get(closure).add(labels, functionalDependencies);
        }

        return new HashSet<>(partitions.values());
    }

    private Set<Partition> removeTransitiveDependencies(Set<Partition> partitions) {
        FunctionalDependencySet jay = new FunctionalDependencySet();
        for(Partition partition: partitions) {
            FunctionalDependencySet descartes = partition.descartesProductNonTrivial();
            jay.addAll(descartes);
            partition.removeAll(descartes);
        }

        FunctionalDependencySet em = new FunctionalDependencySet();
        em.addAll(jay);
        partitions.forEach(p -> em.addAll(p.getValues()));

        partitions.forEach(partition -> {
            FunctionalDependencySet functionalDependenciesCopy = new FunctionalDependencySet(partition.getValues());
            functionalDependenciesCopy.forEach(fd -> {
                if(fd.isLogicalConsequenceOf(em.difference(fd))) {
                    Printer.print("Transitive FD removed:", fd);
                    partition.remove(fd);  // fd is transitive dependency
                }
            });
        });

        partitions.forEach(partition -> partition.addAll(jay));

        return partitions;
    }

    private RelationSet generateRelationSet(Set<Partition> partitions) {
        RelationSet relations = new RelationSet("S");

        int n = 1;
        for(Partition partition : partitions) {
            LabelSet labels = partition.getLabels();
            Relation relation = new Relation(initialRelation.getName(), n++, labels, partition.getValues());
            relations.add(relation);
        }

        //Check for lossless join
        if(!relations.hasKey(initialRelation.getKeys())) {
            Relation lossless = new Relation(initialRelation.getName(), n, initialRelation.getKeys().stream().findAny().get(), new FunctionalDependencySet());
            Printer.print("Additional relation to preserve lossless connectivity:", lossless);
            relations.add(lossless);
        }

        return relations;
    }
}
