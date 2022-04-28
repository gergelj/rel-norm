package rs.ac.uns.ftn.dais;

import rs.ac.uns.ftn.dais.domain.DecompositionMode;
import rs.ac.uns.ftn.dais.domain.DecompositionTree;
import rs.ac.uns.ftn.dais.domain.FunctionalDependencySet;
import rs.ac.uns.ftn.dais.domain.Label;
import rs.ac.uns.ftn.dais.domain.LabelSet;
import rs.ac.uns.ftn.dais.domain.Printer;
import rs.ac.uns.ftn.dais.domain.Relation;
import rs.ac.uns.ftn.dais.domain.TaskParser;
import rs.ac.uns.ftn.dais.domain.RelationSet;
import rs.ac.uns.ftn.dais.domain.SynthesisGenerator;
import rs.ac.uns.ftn.dais.domain.TaskMode;
import rs.ac.uns.ftn.dais.input.NormalFormInput;
import rs.ac.uns.ftn.dais.mapper.RelationMapper;
import java.io.IOException;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        DecompositionMode mode = DecompositionMode.DECOMPOSITION;
        String filePath = args[0];
        String dmode = args[1];
        if(dmode.equalsIgnoreCase("SYNTHESIS")) {
            mode = DecompositionMode.SYNTHESIS;
        } else if (dmode.equalsIgnoreCase("DECOMPOSITION_I")) {
            mode = DecompositionMode.DECOMPOSITION_I;
        } else if(dmode.equalsIgnoreCase("NORMALFORM_ONLY")) {
            mode = DecompositionMode.NORMALFORM_ONLY;
        }

        TaskParser parser = new TaskParser(filePath);

        try{
            switch (mode) {
                case DECOMPOSITION -> {
                    Relation loaded = parser.parseRelation();
                    Printer.taskMode = TaskMode.DECOMPOSITION;
                    decompose(loaded);
                }
                case DECOMPOSITION_I -> {
                    Relation loaded = parser.parseRelation();
                    Printer.taskMode = TaskMode.DECOMPOSITION;
                    decomposeInt(loaded);
                }
                case SYNTHESIS -> {
                    Relation loaded = parser.parseRelation();
                    Printer.taskMode = TaskMode.SYNTHESIS;
                    synthesize(loaded);
                }
                case NORMALFORM_ONLY -> {
                    NormalFormInput input = parser.parseNormalFormInput();
                    Relation universal = RelationMapper.map(input.getUniversal());
                    FunctionalDependencySet universalDependencies = universal.getFunctionalDependencies();
                    System.out.println(universal);
                    int i = 1;
                    for (List<String> labels: input.getLabels()) {
                        LabelSet labelss = new LabelSet();
                        labels.forEach(label -> labelss.add(new Label(label)));
                        FunctionalDependencySet dependencies = universalDependencies.projection(labelss);
                        Relation r = new Relation("N"+i, labelss, dependencies);
                        System.out.println(r);
                        i++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void decompose(Relation relation) {
        DecompositionTree dt = new DecompositionTree(relation);
        RelationSet relations = dt.decompose();
        Printer.print("**********************************", TaskMode.DECOMPOSITION);
        Printer.print("Results:", relations, TaskMode.DECOMPOSITION);
        relations.printRefIntegrity();
    }

    private static void decomposeInt(Relation relation) {
        DecompositionTree dt = new DecompositionTree(relation);
        RelationSet relations = dt.decomposeInt();
        Printer.print("**********************************", TaskMode.DECOMPOSITION);
        Printer.print("Results:", relations, TaskMode.DECOMPOSITION);
        relations.printRefIntegrity();
    }

    private static void synthesize(Relation relation) {
        SynthesisGenerator generator = new SynthesisGenerator(relation);
        RelationSet relations = generator.synthesize();
        Printer.print("**********************************", TaskMode.SYNTHESIS);
        Printer.print("Results:", relations, TaskMode.SYNTHESIS);
        relations.printRefIntegrity();
    }
}
