package it.micheledalcin.approject.computation;

public class Tuple {

    private final String variableName;
    private final double lower;
    private final double step;
    private final double upper;

    public Tuple(String varName, double lower, double step, double upper) throws IllegalArgumentException {
        if (step <= 0) throw new IllegalArgumentException("VariableValuesFunction "+this.toString()+" has the step JavaNum <= 0");
        else if (upper<lower) throw new IllegalArgumentException("VariableValuesFunction "+this.toString()+" has the upper limit < to the inferior limit");
        this.variableName = varName;
        this.lower = lower;
        this.step = step;
        this.upper = upper;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Tuple)) return false;
        Tuple tuple = (Tuple) obj;
        return (this.variableName.equals(tuple.getVariableName())) && (this.lower == tuple.getLower()) && (this.step == tuple.getStep()) && (this.upper == tuple.getUpperName());
    }

    @Override
    public String toString() {
        return "("+this.variableName+", "+this.lower +", "+this.step +", "+this.upper +")";
    }

    public String getVariableName() { return this.variableName; }

    public double getLower() { return this.lower; }

    public double getStep() { return this.step; }

    public double getUpperName() { return this.upper; }

    public VariableValues parseVariableValuesFunctionA() {
        double K = Math.abs(this.upper - this.lower) / this.step;
        double[] a;
        if ((K % 1)==0) a = new double[((int) K)+1];
        else a = new double[((int) K) + 2];
        a[0] = this.lower;
        a[a.length-1] = this.upper;
        for (int i=1; i<a.length-1; i++) {
            a[i] = a[i-1] + this.step;
        }
        return new VariableValues(this.variableName, a);
    }
}
