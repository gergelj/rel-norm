package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import rs.ac.uns.ftn.dais.domain.LabelSet;
import rs.ac.uns.ftn.dais.domain.Relation;
import rs.ac.uns.ftn.dais.domain.RelationSet;
import rs.ac.uns.ftn.dais.domain.SynthesisGenerator;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestSynthesis {

    @Test
    public void synthesis1() {
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
        SynthesisGenerator sg = new SynthesisGenerator(relation);

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

        assertEquals(relationSet, sg.synthesize());
    }

    @Test
    public void synthesis2() {
        // Z 5.1
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C", "D"}));
        fdset.add(new FunctionalDependency(new String[]{"E", "D"}, new String[]{"A", "B"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"F"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "F"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"C"}));

        LabelSet labels = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F"});
        Relation relation = new Relation("N", labels, fdset);
        SynthesisGenerator sg = new SynthesisGenerator(relation);

        //ACTUAL
        RelationSet relationSet = new RelationSet("S");
        Set<LabelSet> keys1 = new HashSet<>();
        keys1.add(new LabelSet(new String[]{"A", "B"}));
        keys1.add(new LabelSet(new String[]{"D", "E"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "B", "D", "E"}), keys1));

        Set<LabelSet> keys2 = new HashSet<>();
        keys2.add(new LabelSet(new String[]{"A", "C"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "C", "F"}), keys2));

        Set<LabelSet> keys3 = new HashSet<>();
        keys3.add(new LabelSet(new String[]{"F", "B"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"B", "F", "E"}), keys3));

        Set<LabelSet> keys4 = new HashSet<>();
        keys4.add(new LabelSet(new String[]{"E"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"C", "E"}), keys4));

        assertEquals(relationSet, sg.synthesize());
    }

    @Test
    public void synthesis3() {
        //Z 5.2
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"D", "C"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"B"}, new String[]{"F"}));

        LabelSet labels = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F"});
        Relation relation = new Relation("N", labels, fdset);
        SynthesisGenerator sg = new SynthesisGenerator(relation);

        //ACTUAL
        RelationSet relationSet = new RelationSet("S");
        Set<LabelSet> keys1 = new HashSet<>();
        keys1.add(new LabelSet(new String[]{"A", "B"}));
        keys1.add(new LabelSet(new String[]{"C", "D"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "B", "C", "D", "E"}), keys1));

        Set<LabelSet> keys2 = new HashSet<>();
        keys2.add(new LabelSet(new String[]{"C"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "C"}), keys2));

        Set<LabelSet> keys3 = new HashSet<>();
        keys3.add(new LabelSet(new String[]{"D"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"B", "D"}), keys3));

        Set<LabelSet> keys4 = new HashSet<>();
        keys4.add(new LabelSet(new String[]{"B"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"B", "F"}), keys4));

        assertEquals(relationSet, sg.synthesize());
    }

    @Test
    public void synthesis4() {
        //Z 5.3
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B", "C"}, new String[]{"D", "E"}));
        fdset.add(new FunctionalDependency(new String[]{"D", "E"}, new String[]{"B", "F"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"F"}));
        fdset.add(new FunctionalDependency(new String[]{"F"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"F"}, new String[]{"I"}));
        fdset.add(new FunctionalDependency(new String[]{"I"}, new String[]{"G"}));
        fdset.add(new FunctionalDependency(new String[]{"F", "G"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"D", "I"}, new String[]{"H"}));

        LabelSet labels = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I"});
        Relation relation = new Relation("N", labels, fdset);
        SynthesisGenerator sg = new SynthesisGenerator(relation);

        //ACTUAL
        RelationSet relationSet = new RelationSet("S");
        Set<LabelSet> keys1 = new HashSet<>();
        keys1.add(new LabelSet(new String[]{"A", "B"}));
        keys1.add(new LabelSet(new String[]{"E", "D"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "B", "D", "E"}), keys1));

        Set<LabelSet> keys2 = new HashSet<>();
        keys2.add(new LabelSet(new String[]{"A"}));
        keys2.add(new LabelSet(new String[]{"F"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "C", "F", "I"}), keys2));

        Set<LabelSet> keys3 = new HashSet<>();
        keys3.add(new LabelSet(new String[]{"I"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"I", "G"}), keys3));

        Set<LabelSet> keys4 = new HashSet<>();
        keys4.add(new LabelSet(new String[]{"D", "I"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"D", "H", "I"}), keys4));

        assertEquals(relationSet, sg.synthesize());
    }

    @Test
    public void synthesis5() {
        //Z 5.4
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"D", "C"}, new String[]{"A", "E"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"C", "F"}));
        fdset.add(new FunctionalDependency(new String[]{"B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"F"}, new String[]{"A", "E"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "D"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "C"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B", "E"}, new String[]{"C"}));

        LabelSet labels = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F", "G"});
        Relation relation = new Relation("N", labels, fdset);
        SynthesisGenerator sg = new SynthesisGenerator(relation);

        //ACTUAL
        RelationSet relationSet = new RelationSet("S");
        Set<LabelSet> keys1 = new HashSet<>();
        keys1.add(new LabelSet(new String[]{"A"}));
        keys1.add(new LabelSet(new String[]{"F"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "C", "F", "E"}), keys1));

        Set<LabelSet> keys2 = new HashSet<>();
        keys2.add(new LabelSet(new String[]{"C", "D"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "C", "D"}), keys2));

        Set<LabelSet> keys3 = new HashSet<>();
        keys3.add(new LabelSet(new String[]{"B"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "B"}), keys3));

        Set<LabelSet> keys4 = new HashSet<>();
        keys4.add(new LabelSet(new String[]{"B", "D", "G"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"B", "D", "G"}), keys4));

        assertEquals(relationSet, sg.synthesize());
    }

    @Test
    public void synthesis6() {
        //Z 5.5
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"A", "B", "C", "G"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "C"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B", "E"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"E", "H"}, new String[]{"A", "I"}));
        fdset.add(new FunctionalDependency(new String[]{"B"}, new String[]{"H"}));
        fdset.add(new FunctionalDependency(new String[]{"F"}, new String[]{"H"}));

        LabelSet labels = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I"});
        Relation relation = new Relation("N", labels, fdset);
        SynthesisGenerator sg = new SynthesisGenerator(relation);

        //ACTUAL
        RelationSet relationSet = new RelationSet("S");
        Set<LabelSet> keys1 = new HashSet<>();
        keys1.add(new LabelSet(new String[]{"A", "B"}));
        keys1.add(new LabelSet(new String[]{"E"}));
        keys1.add(new LabelSet(new String[]{"B", "C"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "B", "C", "D", "E", "G", "I"}), keys1));

        Set<LabelSet> keys2 = new HashSet<>();
        keys2.add(new LabelSet(new String[]{"B"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"B", "H"}), keys2));

        Set<LabelSet> keys3 = new HashSet<>();
        keys3.add(new LabelSet(new String[]{"F"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"F", "H"}), keys3));

        // We add a fourth relation with either keys [ABF], [EF] or [BCF] in order to preserve lossless connectivity
        Set<LabelSet> keys4 = new HashSet<>();
        keys4.add(new LabelSet(new String[]{"A", "B", "F"}));
        relationSet.add(new Relation("N", new LabelSet(new String[]{"A", "B", "F"}), keys4));

        assertEquals(relationSet, sg.synthesize());
    }
}
