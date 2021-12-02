package rs.ac.uns.ftn.dais.domain;

public class TaskMode {
    private TaskMode() {}

    public static final int ALL = ~ 0b0;
    public static final int DECOMPOSITION = 0b1;
    public static final int SYNTHESIS = 0b10;
    public static final int NF = 0b100;
}
