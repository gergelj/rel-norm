package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import rs.ac.uns.ftn.dais.domain.LabelSet;
import rs.ac.uns.ftn.dais.domain.NormalForm;
import rs.ac.uns.ftn.dais.domain.Relation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestNormalForm {
    @Test
    public void normalForm() {
        //zbirka 4.32
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"A"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C", "D"});

        Relation relation = new Relation("N", r, fdset);

        assertEquals(NormalForm.FIRST_NF, relation.getNormalForm());
    }

    @Test
    public void normalForm2() {
        //zbirka 4.33
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B", "C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"A"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C", "D"});

        Relation relation = new Relation("N", r, fdset);

        assertEquals(NormalForm.THIRD_NF, relation.getNormalForm());
    }

    @Test
    public void normalForm3() {
        //zbirka 4.34
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"C"}));

        LabelSet r = new LabelSet(new String[]{"A", "B", "C", "D"});

        Relation relation = new Relation("N", r, fdset);

        assertEquals(NormalForm.SECOND_NF, relation.getNormalForm());
    }

    @Test
    public void normalForm4() {
        //zbirka 4.26
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "C"}, new String[]{"D"}));

        LabelSet r = new LabelSet(new String[]{"A", "C", "D"});

        Relation relation = new Relation("N", r, fdset);

        assertEquals(NormalForm.BCNF, relation.getNormalForm());
    }
}
