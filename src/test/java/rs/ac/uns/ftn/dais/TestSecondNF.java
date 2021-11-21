package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import rs.ac.uns.ftn.dais.domain.LabelSet;
import rs.ac.uns.ftn.dais.domain.Relation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSecondNF {
    @Test
    public void secondNF1() {
        //vezbe NF 6 slide
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B", "C", "D"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"F"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F"});

        Relation relation = new Relation("N", r, fdset);

        assertFalse(relation.isSecondNF());
    }

    @Test
    public void secondNF2() {
        //vezbe NF 7 slide
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B", "C", "D"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"C"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C", "D"});

        Relation relation = new Relation("N", r, fdset);

        assertTrue(relation.isSecondNF());
    }

    @Test
    public void secondNF3() {
        //vezbe NF 14 slide
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"C", "D", "E", "F", "G"}));
        fdset.add(new FunctionalDependency(new String[]{"B"}, new String[]{"G", "H"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"F", "G"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"I"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I"});

        Relation relation = new Relation("N", r, fdset);

        assertFalse(relation.isSecondNF());
    }

    @Test
    public void secondNF4() {
        //vezbe NF 20 slide
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C", "D", "E"});

        Relation relation = new Relation("N", r, fdset);

        assertFalse(relation.isSecondNF());
    }

    @Test
    public void secondNF5() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"M", "R"}, new String[]{"S"}));
        fdset.add(new FunctionalDependency(new String[]{"M"}, new String[]{"O"}));

        LabelSet r = new LabelSet(new String[]{"M", "R", "S", "O"});

        Relation relation = new Relation("N", r, fdset);

        assertFalse(relation.isSecondNF());
    }
}
