package rs.ac.uns.ftn.dais.domain;

public class Printer {
    private Printer() {

    }

    public static void print(String message, Object object) {
        System.out.println(message);
        System.out.println(object);
        System.out.println();
    }

    public static void print(String message) {
        System.out.println(message);
        System.out.println();
    }

    public static void print(Object obj) {
        System.out.println(obj);
    }
}
