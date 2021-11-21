package rs.ac.uns.ftn.dais.domain;

import java.util.Objects;

public class Label {

    private String value;

    public Label(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return value.equals(label.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
