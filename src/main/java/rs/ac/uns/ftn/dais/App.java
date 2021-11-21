package rs.ac.uns.ftn.dais;

import rs.ac.uns.ftn.dais.domain.DecompositionMode;
import rs.ac.uns.ftn.dais.domain.DecompositionTree;
import rs.ac.uns.ftn.dais.domain.Printer;
import rs.ac.uns.ftn.dais.domain.Relation;
import rs.ac.uns.ftn.dais.domain.RelationParser;
import rs.ac.uns.ftn.dais.domain.RelationSet;
import rs.ac.uns.ftn.dais.domain.SynthesisGenerator;
import java.io.IOException;

public class App {
    public static void main( String[] args ) {
        DecompositionMode mode = DecompositionMode.DECOMPOSITION;
        String filePath = args[0];
        String dmode = args[1];
        if(dmode.equalsIgnoreCase("SYNTHESIS")) {
            mode = DecompositionMode.SYNTHESIS;
        } else if (dmode.equalsIgnoreCase("DECOMPOSITION_I")) {
            mode = DecompositionMode.DECOMPOSITION_I;
        }

        RelationParser parser = new RelationParser();
        Relation loaded;

        try {
            loaded = parser.parse(filePath);
            switch (mode) {
                case DECOMPOSITION -> decompose(loaded);
                case DECOMPOSITION_I -> decomposeInt(loaded);
                case SYNTHESIS -> synthesize(loaded);
            }
        } catch (IOException e) {
            Printer.print(e.getMessage());
        }
    }

    private static void decompose(Relation relation) {
        DecompositionTree dt = new DecompositionTree(relation);
        RelationSet relations = dt.decompose();
        Printer.print("**********************************");
        Printer.print("Results:", relations);
        relations.printRefIntegrity();
    }

    private static void decomposeInt(Relation relation) {
        DecompositionTree dt = new DecompositionTree(relation);
        RelationSet relations = dt.decomposeInt();
        Printer.print("**********************************");
        Printer.print("Results:", relations);
        relations.printRefIntegrity();
    }

    private static void synthesize(Relation relation) {
        SynthesisGenerator generator = new SynthesisGenerator(relation);
        RelationSet relations = generator.synthesize();
        Printer.print("**********************************");
        Printer.print("Results:", relations);
        relations.printRefIntegrity();
    }
}
