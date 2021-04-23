package it.micheledalcin.approject.computation.expression;

import it.micheledalcin.approject.exceptions.ComputationException;

import java.util.Map;

public class Expression {

    private final String expression;

    public Expression(String string) {
        this.expression = string;
    }

    public double compute(Map<String, Double> map) throws IllegalArgumentException, ComputationException {
        ExpressionParser expressionParser = new ExpressionParser(this.expression);
        return ExpressionMethods.parseExpression(expressionParser.parse(), map);
    }

    public String getExpression() { return this.expression; }

    @Override
    public String toString() {
        return this.expression;
    }
}
