package rs.ac.uns.ftn.dais.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class GenericSet<T> implements Set<T> {
    protected Set<T> items;

    public GenericSet() {
        this.items = new HashSet<>();
    }

    public GenericSet(Collection<? extends T> items) {
        this.items = new HashSet<>(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericSet<?> that = (GenericSet<?>) o;
        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return items.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    @Override
    public Object[] toArray() {
        return items.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return items.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return items.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return items.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return items.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return items.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return items.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return items.removeAll(c);
    }

    @Override
    public void clear() {
        items.clear();
    }
}
