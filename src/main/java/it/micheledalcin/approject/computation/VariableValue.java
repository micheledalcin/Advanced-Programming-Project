package it.micheledalcin.approject.computation;

import it.micheledalcin.approject.computation.expression.Constant;
import it.micheledalcin.approject.computation.expression.Variable;

public class VariableValue {

    private final Variable variable;
    private final Constant constant;

    public VariableValue(Variable variable, Constant constant) {
        this.variable = variable;
        this.constant = constant;
    }

    public VariableValue(String name, double value) {
        this.variable = new Variable(name);
        this.constant = new Constant(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof VariableValue)) return false;
        VariableValue param = (VariableValue) obj;
        return (this.constant.getValue() == param.getValue() && this.variable.getName().equals(param.getVariableName()));
    }

    @Override
    public String toString() {
        return "("+this.getVariableName()+ "=" +this.getValue()+")";
    }

    public String getVariableName() { return this.variable.getName(); }

    public double getValue() { return this.constant.getValue(); }


}
