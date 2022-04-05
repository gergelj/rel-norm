package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.DecompositionTree;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import rs.ac.uns.ftn.dais.domain.LabelSet;
import rs.ac.uns.ftn.dais.domain.Relation;
import rs.ac.uns.ftn.dais.domain.RelationSet;
import rs.ac.uns.ftn.dais.domain.SynthesisGenerator;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestDecomposition {
    @Test
    public void decomposition1() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"B"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"C", "D"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"F"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"B"}, new String[]{"B"}));

        LabelSet labels = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F"});
        Relation relation = new Relation("N", labels, fdset);

        DecompositionTree dt = new DecompositionTree(relation);
        RelationSet relations = dt.decompose();

        //ACTUAL
        RelationSet relationSet = new RelationSet("S");
        FunctionalDependencySet fds1 = new FunctionalDependencySet(new FunctionalDependency[]{
                new FunctionalDependency(new String[]{"A"}, new String[]{"C"}),
                new FunctionalDependency(new String[]{"A"}, new String[]{"D"}),
                new FunctionalDependency(new String[]{"A"}, new String[]{"B"}),
                new FunctionalDependency(new String[]{"B"}, new String[]{"A"})
        });
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "B", "C", "D"}), fds1));

        FunctionalDependencySet fds2 = new FunctionalDependencySet(new FunctionalDependency[]{
                new FunctionalDependency(new String[]{"D"}, new String[]{"E"})
        });
        relationSet.add(new Relation("N", new LabelSet(new String[]{"D", "E"}), fds2));

        FunctionalDependencySet fds3 = new FunctionalDependencySet(new FunctionalDependency[]{
                new FunctionalDependency(new String[]{"E"}, new String[]{"F"})
        });
        relationSet.add(new Relation("N", new LabelSet(new String[]{"E", "F"}), fds3));

        assertEquals(relationSet, relations);
    }

    @Test
    public void decompose2() {
        //Z 5.6
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"B"}));

        LabelSet labels = new LabelSet(new String[]{"A", "B", "C"});
        Relation relation = new Relation("N", labels, fdset);
        DecompositionTree dt = new DecompositionTree(relation);
        RelationSet relations = dt.decompose();

        //ACTUAL
        RelationSet relationSet = new RelationSet("S");
        Set<LabelSet> keys1 = new HashSet<>();
        keys1.add(new LabelSet(new String[]{"C"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"B", "C"}), keys1));

        Set<LabelSet> keys2 = new HashSet<>();
        keys2.add(new LabelSet(new String[]{"A", "C"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "C"}), keys2));

        assertEquals(relationSet, relations);
    }

    @Test
    public void decompose3() {
        //Z 5.7
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"F"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"G"}));
        fdset.add(new FunctionalDependency(new String[]{"C", "A"}, new String[]{"E"}));

        LabelSet labels = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F", "G"});
        Relation relation = new Relation("N", labels, fdset);
        DecompositionTree dt = new DecompositionTree(relation);
        RelationSet relations = dt.decompose();

        //ACTUAL
        RelationSet relationSet = new RelationSet("S");
        Set<LabelSet> keys1 = new HashSet<>();
        keys1.add(new LabelSet(new String[]{"A"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"B", "A"}), keys1));

        Set<LabelSet> keys2 = new HashSet<>();
        keys2.add(new LabelSet(new String[]{"E"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"E", "F", "D"}), keys2));

        Set<LabelSet> keys3 = new HashSet<>();
        keys3.add(new LabelSet(new String[]{"C"}));
        keys3.add(new LabelSet(new String[]{"D"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"D", "C"}), keys3));

        Set<LabelSet> keys4 = new HashSet<>();
        keys4.add(new LabelSet(new String[]{"A", "D"}));
        keys4.add(new LabelSet(new String[]{"A", "E"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "D", "E", "G"}), keys4));

        assertEquals(relationSet, relations);
    }

    @Test
    public void decompose4() {
        //Z 5.8
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"D", "C"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"B"}, new String[]{"F"}));

        LabelSet labels = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F"});
        Relation relation = new Relation("N", labels, fdset);
        DecompositionTree dt = new DecompositionTree(relation);
        RelationSet relations = dt.decompose();

        //ACTUAL
        RelationSet relationSet = new RelationSet("S");
        Set<LabelSet> keys1 = new HashSet<>();
        keys1.add(new LabelSet(new String[]{"C"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"C", "A"}), keys1));

        Set<LabelSet> keys2 = new HashSet<>();
        keys2.add(new LabelSet(new String[]{"B", "C"}));
        keys2.add(new LabelSet(new String[]{"A", "D"}));
        keys2.add(new LabelSet(new String[]{"C", "D"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "B", "C", "D", "E"}), keys2));

        Set<LabelSet> keys3 = new HashSet<>();
        keys3.add(new LabelSet(new String[]{"D"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"D", "B"}), keys3));

        Set<LabelSet> keys4 = new HashSet<>();
        keys4.add(new LabelSet(new String[]{"B"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"B", "F"}), keys4));

        assertEquals(relationSet, relations);
    }
}
