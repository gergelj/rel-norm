package rs.ac.uns.ftn.dais;

import org.junit.Test;
import rs.ac.uns.ftn.dais.domain.FunctionalDependency;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import rs.ac.uns.ftn.dais.domain.LabelSet;
import rs.ac.uns.ftn.dais.domain.Relation;
import rs.ac.uns.ftn.dais.domain.TaskParser;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestRelationParser {

    @Test
    public void relationParserTest1() {
        try {
            TaskParser parser = new TaskParser("src/test/resources/task1.json");
            Relation loaded = parser.parseRelation();

            FunctionalDependencySet fdset = new FunctionalDependencySet();
            fdset.add(new FunctionalDependency(new String[]{"E", "F"}, new String[]{"C"}));
            fdset.add(new FunctionalDependency(new String[]{"C"}, new String[]{"E"}));
            LabelSet labels = new LabelSet(new String[]{"E", "F", "C"});
            Relation expected = new Relation("N", labels, fdset);
            assertEquals(expected, loaded);

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void relationParserTest2() {
        try {
            TaskParser parser = new TaskParser("src/test/resources/task2.json");
            Relation loaded = parser.parseRelation();

            FunctionalDependencySet fdset = new FunctionalDependencySet();
            fdset.add(new FunctionalDependency(new String[]{"R", "C", "X"}, new String[]{"A", "L"}));
            fdset.add(new FunctionalDependency(new String[]{"X"}, new String[]{"V"}));
            fdset.add(new FunctionalDependency(new String[]{"X"}, new String[]{"X", "G", "R"}));
            fdset.add(new FunctionalDependency(new String[]{"G"}, new String[]{"G", "R"}));
            fdset.add(new FunctionalDependency(new String[]{"V"}, new String[]{"L"}));
            LabelSet labels = new LabelSet(new String[]{"A", "R", "C", "X", "V", "G", "L", "M"});
            Relation expected = new Relation("N", labels, fdset);
            assertEquals(expected, loaded);

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
