package rs.ac.uns.ftn.dais.domain;

public class Printer {

    public static int taskMode;

    private Printer() {

    }

    public static void print(String message, Object object, int tasks) {
        if((taskMode & tasks) != 0) {
            System.out.println(message);
            System.out.println(object);
            System.out.println();
        }
    }

    public static void print(String message, int tasks) {
        if((taskMode & tasks) != 0) {
            System.out.println(message);
            System.out.println();
        }
    }

    public static void print(Object obj, int tasks) {
        if((taskMode & tasks) != 0) {
            System.out.println(obj);
        }
    }
}
