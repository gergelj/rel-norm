package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestEquivalence {
    @Test
    public void equivalence1() {
        FunctionalDependencySet fdset1 = new FunctionalDependencySet();
        fdset1.add(new FunctionalDependency(new String[]{"A"}, new String[]{"C", "D"}));
        fdset1.add(new FunctionalDependency(new String[]{"D"}, new String[]{"E"}));
        fdset1.add(new FunctionalDependency(new String[]{"D", "B"}, new String[]{"A"}));
        fdset1.add(new FunctionalDependency(new String[]{"E"}, new String[]{"B"}));
        fdset1.add(new FunctionalDependency(new String[]{"B"}, new String[]{"C"}));

        FunctionalDependencySet fdset2 = new FunctionalDependencySet();
        fdset2.add(new FunctionalDependency(new String[]{"A"}, new String[]{"D"}));
        fdset2.add(new FunctionalDependency(new String[]{"A"}, new String[]{"E"}));
        fdset2.add(new FunctionalDependency(new String[]{"D", "B"}, new String[]{"A"}));
        fdset2.add(new FunctionalDependency(new String[]{"E"}, new String[]{"C"}));

        assertFalse(fdset1.equivalent(fdset2));
    }

    @Test
    public void equivalence2() {
        FunctionalDependencySet fdset1 = new FunctionalDependencySet();
        fdset1.add(new FunctionalDependency(new String[]{"C"}, new String[]{"D"}));
        fdset1.add(new FunctionalDependency(new String[]{"D"}, new String[]{"C"}));
        fdset1.add(new FunctionalDependency(new String[]{"E"}, new String[]{"C"}));
        fdset1.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"G"}));
        fdset1.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"E"}));

        FunctionalDependencySet fdset2 = new FunctionalDependencySet();
        fdset2.add(new FunctionalDependency(new String[]{"C"}, new String[]{"D"}));
        fdset2.add(new FunctionalDependency(new String[]{"D"}, new String[]{"C"}));
        fdset2.add(new FunctionalDependency(new String[]{"E"}, new String[]{"C"}));
        fdset2.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"G"}));
        fdset2.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"E"}));
        fdset2.add(new FunctionalDependency(new String[]{"E"}, new String[]{"D"}));

        assertTrue(fdset1.equivalent(fdset2));
    }
}
