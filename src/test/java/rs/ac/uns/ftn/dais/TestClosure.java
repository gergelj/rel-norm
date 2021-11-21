package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import rs.ac.uns.ftn.dais.domain.LabelSet;
import static org.junit.Assert.assertEquals;

public class TestClosure {

    @Test
    public void closure1() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"A", "C"}));
        fdset.add(new FunctionalDependency(new String[]{"C", "D"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "E"}, new String[]{"F"}));


        LabelSet ls = new LabelSet(new String[] {"A", "D"});

        LabelSet calcResult = fdset.closure(ls);
        LabelSet result = new LabelSet(new String[]{"A", "D", "B", "C", "E", "F"});

        assertEquals(result, calcResult);
    }

    @Test
    public void closure2() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "C"}, new String[]{"D"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "C", "D"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"E", "G"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "E"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"C", "G"}, new String[]{"B", "D"}));
        fdset.add(new FunctionalDependency(new String[]{"C", "E"}, new String[]{"A", "G"}));


        LabelSet ls = new LabelSet(new String[] {"B", "D"});

        LabelSet calcResult = fdset.closure(ls);
        LabelSet result = new LabelSet(new String[]{"B", "D", "E", "C", "G", "A"});

        assertEquals(result, calcResult);
    }

    @Test
    public void closure3() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"I"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "I"}, new String[]{"J"}));


        LabelSet ls = new LabelSet(new String[] {"A", "I"});

        LabelSet calcResult = fdset.closure(ls);
        LabelSet result = new LabelSet(new String[]{"A", "I", "B", "C", "E", "J"});

        assertEquals(result, calcResult);
    }

    @Test
    public void closure4() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"I"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "I"}, new String[]{"J"}));


        LabelSet ls = new LabelSet(new String[] {"D", "J"});

        LabelSet calcResult = fdset.closure(ls);
        LabelSet result = new LabelSet(new String[]{"D", "J", "C"});

        assertEquals(result, calcResult);
    }

    @Test
    public void closure5() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"I"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "I"}, new String[]{"J"}));


        LabelSet ls = new LabelSet(new String[] {"B", "E"});

        LabelSet calcResult = fdset.closure(ls);
        LabelSet result = new LabelSet(new String[]{"B", "E", "I", "J"});

        assertEquals(result, calcResult);
    }

    @Test
    public void closure6() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"D"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"E"}, new String[]{"I"}));
        fdset.add(new FunctionalDependency(new String[]{"B", "I"}, new String[]{"J"}));


        LabelSet ls = new LabelSet(new String[] {});

        LabelSet calcResult = fdset.closure(ls);
        LabelSet result = new LabelSet(new String[]{});

        assertEquals(result, calcResult);
    }
}
