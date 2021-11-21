package rs.ac.uns.ftn.dais.domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LabelSet extends GenericSet<Label> {

    public LabelSet() {
        super();
    }

    public LabelSet(Set<Label> items) {
        super(items);
    }

    public LabelSet(Label item) {
        super();
        add(item);
    }

    public LabelSet(String[] labels) {
        super(Arrays.stream(labels).map(Label::new).toList());
    }

    //Idea from: https://www.baeldung.com/java-power-set-of-a-set#3-binary-representation
    public Set<LabelSet> getPowerSet() {
        int size = this.size();

        List<Label> itemList = items.stream().toList();

        Set<LabelSet> powerSet = new HashSet<>();
        for(int i = 0; i < Math.pow(2, size); i++) {
            LabelSet subset = new LabelSet();
            for(int j = 0; j < size; j++) {
                boolean needed = (i & (1 << j)) != 0;
                if(needed) {
                    Label item = itemList.get(j);
                    subset.add(item);
                }
            }
            powerSet.add(subset);
        }
        return powerSet;
    }

    public List<LabelSet> getPowerSetSorted() {
        return getPowerSet().stream().sorted(Comparator.comparing(GenericSet::size)).toList();
    }

    @Override
    public String toString() {
        return items.isEmpty() ? "{}" : String.join("", items.stream().map(Label::getValue).sorted().toList());
    }

    public boolean add(String s) {
        return add(new Label(s));
    }

    public LabelSet union(LabelSet other) {
        LabelSet union = new LabelSet();
        union.addAll(this);
        union.addAll(other);
        return union;
    }

    public LabelSet intersection(LabelSet other) {
        LabelSet intersection = new LabelSet();
        intersection.addAll(this);
        intersection.retainAll(other);
        return intersection;
    }

    public LabelSet difference(LabelSet other) {
        LabelSet difference = new LabelSet();
        difference.addAll(this);
        difference.removeAll(other);
        return difference;
    }
}
