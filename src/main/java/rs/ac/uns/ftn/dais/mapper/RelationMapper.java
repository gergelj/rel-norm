package rs.ac.uns.ftn.dais.mapper;

import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import rs.ac.uns.ftn.dais.domain.LabelSet;
import rs.ac.uns.ftn.dais.domain.Relation;
import rs.ac.uns.ftn.dais.input.RelationInput;

public class RelationMapper {
    public static Relation map(RelationInput input) {
        String name = input.getName();

        LabelSet labels = new LabelSet();
        input.getLabels().forEach(labels::add);

        FunctionalDependencySet dependencies = new FunctionalDependencySet();
        input.getFunctionalDependencies().forEach(fd -> dependencies.add(new FunctionalDependency(fd.getLeft(), fd.getRight())));

        return new Relation(name, labels, dependencies);
    }
}
