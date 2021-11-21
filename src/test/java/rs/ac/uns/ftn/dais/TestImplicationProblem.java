package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestImplicationProblem {

    @Test
    public void implication1() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "D"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"E"}));


        FunctionalDependency fd = new FunctionalDependency(new String[]{"A"}, new String[]{"D"});

        assertTrue(fd.isLogicalConsequenceOf(fdset));
    }

    @Test
    public void implication2() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"F"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C", "E"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "E"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"F"}, new String[]{"A", "E"}));

        FunctionalDependency fd = new FunctionalDependency(new String[]{"A", "B"}, new String[]{"D"});

        assertTrue(fd.isLogicalConsequenceOf(fdset));
    }

    @Test
    public void implication3() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"F"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C", "E"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "E"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"F"}, new String[]{"A", "E"}));

        FunctionalDependency fd = new FunctionalDependency(new String[]{"A", "B"}, new String[]{"D", "F"});

        assertTrue(fd.isLogicalConsequenceOf(fdset));
    }

    @Test
    public void implication4() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "C", "D"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"E", "G"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "E"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"C", "G"}, new String[]{"B", "D"}));
        fdset.add(new FunctionalDependency(new String[]{"C", "E"}, new String[]{"A", "G"}));

        FunctionalDependency fd = new FunctionalDependency(new String[]{"A", "B", "G"}, new String[]{"E"});

        assertTrue(fd.isLogicalConsequenceOf(fdset));
    }

    @Test
    public void implication5() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "C", "D"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"E", "G"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "E"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"C", "G"}, new String[]{"B", "D"}));
        fdset.add(new FunctionalDependency(new String[]{"C", "E"}, new String[]{"A", "G"}));

        FunctionalDependency fd = new FunctionalDependency(new String[]{"A", "B", "G"}, new String[]{"C", "F"});

        assertFalse(fd.isLogicalConsequenceOf(fdset));
    }
}
