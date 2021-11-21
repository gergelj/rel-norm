package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import rs.ac.uns.ftn.dais.domain.LabelSet;

import static org.junit.Assert.assertEquals;

public class TestProjection {
    @Test
    public void projection1() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"D", "C"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "E"}, new String[]{"F"}));


        LabelSet projectionLabels = new LabelSet(new String[]{"A", "D", "F"});
        FunctionalDependencySet calculatedProjection = fdset.projection(projectionLabels);

        FunctionalDependencySet projection = new FunctionalDependencySet();
        projection.add(new FunctionalDependency(new String[]{"A", "D"}, new String[]{"F"}));

        assertEquals(projection, calculatedProjection);
    }

    @Test
    public void projection2() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"C", "B"}, new String[]{"D"}));


        LabelSet projectionLabels = new LabelSet(new String[]{"A", "D", "B"});
        FunctionalDependencySet calculatedProjection = fdset.projection(projectionLabels);

        FunctionalDependencySet projection = new FunctionalDependencySet();
        projection.add(new FunctionalDependency(new String[]{"A", "D"}, new String[]{"B"}));
        projection.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));

        assertEquals(projection, calculatedProjection);
    }

    @Test
    public void projection3() {
        FunctionalDependencySet fdset = new FunctionalDependencySet();
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"A"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "B"}, new String[]{"C"}));
        fdset.add(new FunctionalDependency(new String[]{"D", "C"}, new String[]{"E"}));
        fdset.add(new FunctionalDependency(new String[]{"A"}, new String[]{"B"}));
        fdset.add(new FunctionalDependency(new String[]{"A", "E"}, new String[]{"F"}));


        LabelSet projectionLabels = new LabelSet(new String[]{"A", "C", "E"});
        FunctionalDependencySet calculatedProjection = fdset.projection(projectionLabels);

        FunctionalDependencySet projection = new FunctionalDependencySet();
        projection.add(new FunctionalDependency(new String[]{"A", "E"}, new String[]{"C"}));
        projection.add(new FunctionalDependency(new String[]{"A"}, new String[]{"C"}));

        assertEquals(projection, calculatedProjection);
    }
}
