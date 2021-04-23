package it.micheledalcin.approject.computation;

import java.util.List;

public class NTuple<T> {

    private List<T> elements;

    public NTuple(List<T> elements) { this.elements = elements; }

    public List<T> getElements() { return this.elements; }

    @Override
    public String toString() {
        String string = "(";
        for (int i=0; i<this.elements.size(); i++) {
            if (i==this.elements.size()-1) {
                string = string.concat(elements.get(i).toString());
                break;
            }
            string = string.concat(elements.get(i).toString());
            string = string.concat(", ");
        }
        return (string+")");
    }
}
