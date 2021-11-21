package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import rs.ac.uns.ftn.dais.domain.LabelSet;
import rs.ac.uns.ftn.dais.domain.Relation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestThirdNF {
    @Test
    public void thirdNF() {
        //vezbe NF 10 slide
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B", "C", "D"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"C"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C", "D"});

        Relation relation = new Relation("N", r, fdset);

        assertFalse(relation.isThirdNF());
    }

    @Test
    public void thirdNF2() {
        //predavanja NF 43 slide
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"B"}, new String[]{"A"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C"});

        Relation relation = new Relation("N", r, fdset);

        assertTrue(relation.isThirdNF());
    }
}
