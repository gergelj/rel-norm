package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;

import static org.junit.Assert.assertEquals;

public class TestCanonicalSet {

    @Test
    public void canonicalSet1() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"D", "E"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"D", "E"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"F"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "F"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"C"}));

        FunctionalDependencySet canonicalSet = new FunctionalDependencySet();
        canonicalSet.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"D"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"D", "E"}, new String[]{"A"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"D", "E"}, new String[]{"B"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"F"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"F", "B"}, new String[]{"E"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"E"}, new String[]{"C"}));

        assertEquals(canonicalSet, fdset.getCanonicalSet());
    }

    @Test
    public void canonicalSet2() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B", "C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "A"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"E", "F"}, new String[]{"G"}));
        fdset.add(new FunctionalDependency(new String[]{"G"}, new String[]{"F"}));
        fdset.add(new FunctionalDependency(new String[]{"D", "G"}, new String[]{"F"}));

        FunctionalDependencySet canonicalSet = new FunctionalDependencySet();
        canonicalSet.add(new FunctionalDependency(new String[]{"C"}, new String[]{"D"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"C"}, new String[]{"A"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"C"}, new String[]{"B"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"D"}, new String[]{"E"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"F", "E"}, new String[]{"G"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"G"}, new String[]{"F"}));

        assertEquals(canonicalSet, fdset.getCanonicalSet());
    }

    @Test
    public void canonicalSet3() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C", "D"}));
        fdset.add(new FunctionalDependency(new String[]{"D", "E"}, new String[]{"A", "B"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"F"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "F"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"C"}));

        FunctionalDependencySet canonicalSet = new FunctionalDependencySet();
        canonicalSet.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"D"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"D", "E"}, new String[]{"A"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"D", "E"}, new String[]{"B"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"F"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"F", "B"}, new String[]{"E"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"E"}, new String[]{"C"}));

        assertEquals(canonicalSet, fdset.getCanonicalSet());
    }

    @Test
    public void canonicalSet4() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"D", "C"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"B"}, new String[]{"F"}));

        FunctionalDependencySet canonicalSet = new FunctionalDependencySet();
        canonicalSet.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"D"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"D", "C"}, new String[]{"E"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"C"}, new String[]{"A"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"D"}, new String[]{"B"}));
        canonicalSet.add(new FunctionalDependency(new String[]{"B"}, new String[]{"F"}));

        assertEquals(canonicalSet, fdset.getCanonicalSet());
    }

}
