package rs.ac.uns.ftn.dais.domain;

import java.util.Scanner;

public class FunctionalDependencyParser {

    private FunctionalDependencyParser() {}

    public static FunctionalDependency parse() {
        System.out.println("Enter a FD (eg. XY->ZW)");
        Scanner in = new Scanner(System.in);

        String s = in.nextLine();

        String[] sides = s.split("->");
        String[] leftSide = sides[0].split("");
        String[] rightSide = sides[1].split("");

        return new FunctionalDependency(leftSide, rightSide);
    }

}
