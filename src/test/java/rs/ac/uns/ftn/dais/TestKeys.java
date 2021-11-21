package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import rs.ac.uns.ftn.dais.domain.LabelSet;
import rs.ac.uns.ftn.dais.domain.Relation;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestKeys {

    @Test
    public void keys1() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C", "D", "E"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"C", "D"}, new String[]{"B"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C", "D", "E"});

        Relation relation = new Relation("N", r, fdset);

        Set<LabelSet> calculatedKeys = relation.getKeys();

        Set<LabelSet> keys = new HashSet<>();
        keys.add(new LabelSet(new String[]{"A", "B"}));
        keys.add(new LabelSet(new String[]{"E", "B"}));
        keys.add(new LabelSet(new String[]{"C", "D", "E"}));
        keys.add(new LabelSet(new String[]{"A", "C", "D"}));

        assertEquals(keys, calculatedKeys);
    }

    @Test
    public void keys2() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C", "E"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"E", "D"}, new String[]{"F"}));
        fdset.add(new FunctionalDependency(new String[]{"F"}, new String[]{"G"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F", "G", "H"});

        Relation relation = new Relation("N", r, fdset);

        Set<LabelSet> calculatedKeys = relation.getKeys();

        Set<LabelSet> keys = new HashSet<>();
        keys.add(new LabelSet(new String[]{"A", "B", "D", "H"}));
        keys.add(new LabelSet(new String[]{"A", "C", "D", "H"}));

        assertEquals(keys, calculatedKeys);
    }

    @Test
    public void keys3() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"F"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"F"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F"});

        Relation relation = new Relation("N", r, fdset);

        Set<LabelSet> calculatedKeys = relation.getKeys();

        Set<LabelSet> keys = new HashSet<>();
        keys.add(new LabelSet(new String[]{"A", "B"}));
        keys.add(new LabelSet(new String[]{"B", "C"}));

        assertEquals(keys, calculatedKeys);
    }

}
