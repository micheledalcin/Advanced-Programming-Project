package it.micheledalcin.approject.computation;

import it.micheledalcin.approject.computation.expression.Constant;
import it.micheledalcin.approject.computation.expression.Variable;

public class VariableValues {

    private final Variable variable;
    private final Constant[] constants;

    public VariableValues(String varName, double[] values) {
        this.variable = new Variable(varName);
        this.constants = new Constant[values.length];
        for (int i = 0; i < values.length; i++) {
            this.constants[i] = new Constant(values[i]);
        }
    }

    public Variable getVariable() { return this.variable; }

    public String getVariableName() { return this.variable.getName(); }

    public Constant[] getConstants() { return this.constants; }

    public double[] getValues() {
        double[] values = new double[this.constants.length];
        for (int i=0; i<this.constants.length; i++) {
            values[i] = this.constants[i].getValue();
        }
        return values;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof VariableValues)) return false;
        VariableValues variableValues = (VariableValues) obj;
        if (this.getVariable().getName().equals(variableValues.getVariableName()) && this.constants.length == variableValues.getConstants().length) {
            for (int i=0; i<this.constants.length; i++) {
                if (this.constants[i].getValue() != variableValues.getConstants()[i].getValue()) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String string = "Variable: "+this.getVariableName()+" = (";
        for (int i=0; i<this.getConstants().length; i++) {
            if (i==this.getConstants().length-1) {
                string = string.concat(Double.toString(this.getConstants()[i].getValue()));
                string = string.concat(")");
                break;
            }
            string = string.concat(Double.toString(this.getConstants()[i].getValue()));
            string = string.concat(", ");
        }
        return string;
    }


}
