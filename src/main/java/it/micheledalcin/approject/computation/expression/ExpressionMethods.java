package it.micheledalcin.approject.computation.expression;

import it.micheledalcin.approject.exceptions.ComputationException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ExpressionMethods {

    public static Expression[] createExpression(String... expressions) {
        Expression[] expressionsArray = new Expression[expressions.length];
        for (int i=0; i<expressions.length; i++) {
            expressionsArray[i] = new Expression(expressions[i]);
        }
        return expressionsArray;
    }

    public static double parseExpression(Node n, Map<String, Double> map) throws IllegalArgumentException, ComputationException {
        List<Node> N = n.getChildren();
        if (nodeIsSingle(n)) {
            return computeAtomicExpression(n, map);
        }
        else if (N.size() == 0) {
            if (n instanceof Variable) {
                if (map.containsKey(((Variable) n).getName())) return map.get(((Variable) n).getName());
                else throw new ComputationException("No values from VariableValuesFunction field found applicable to parsed Variable \""+n.toString()+"\"");
            }
            else if (n instanceof Constant) { return ((Constant) n).getValue(); }
            else throw new ComputationException("Error");
        }
        else if (N.size() == 2) {
            double[] numbers = new double[2];
            int i = 0;
            for (Node m: N) {
                numbers[i] = parseExpression(m, map);
                i++;
            }
            return computeExpression(n, numbers);
        }
        throw new ComputationException();
    }

    private static boolean nodeIsSingle(Node n) {
        List<Node> N = n.getChildren();
        if (N.size() == 2) {
            int size = 0;
            for (Node m: N) {
                size = size + m.getChildren().size();
            }
            return (size == 0);
        }
        return false;
    }

    private static double computeAtomicExpression(Node n, Map<String, Double> map) throws IllegalArgumentException {
        if (n.getChildren().size() != 2) throw new IllegalArgumentException();
        Function<double[], Double> f = findAtomicFunction(n);
        double[] numbers = new double[2];
        List<Node> ln = n.getChildren();
        if (ln.get(0) instanceof Variable) { numbers[0] = map.get(((Variable) ln.get(0)).getName()); }
        else if (ln.get(0) instanceof Constant) { numbers[0] = ((Constant) ln.get(0)).getValue(); }

        if (ln.get(1) instanceof Variable) { numbers[1] = map.get(((Variable) ln.get(1)).getName()); }
        else if (ln.get(1) instanceof Constant) { numbers[1] = ((Constant) ln.get(1)).getValue(); }

        return f.apply(numbers);
    }

    private static Function<double[], Double> findAtomicFunction(Node n) throws IllegalArgumentException {
        if (n.getChildren().size() != 2) throw new IllegalArgumentException();
        String s = n.toString();
        for (int i = 0; i < s.length(); i++) {
            try {
                Operator.Type op = findOperatorType(s.charAt(i));
                return op.getFunction();
            } catch (IllegalArgumentException e) {
                //throw a new one later
            }
        }
        throw new IllegalArgumentException("No supported operator found in node \""+n.toString()+"\"");
    }

    private static Operator.Type findOperatorType(char c) throws IllegalArgumentException {
        if (c == Operator.Type.SUM.getSymbol()) return Operator.Type.SUM;
        else if (c == Operator.Type.SUBTRACTION.getSymbol()) return Operator.Type.SUBTRACTION;
        else if (c == Operator.Type.MULTIPLICATION.getSymbol()) return Operator.Type.MULTIPLICATION;
        else if (c == Operator.Type.DIVISION.getSymbol()) return Operator.Type.DIVISION;
        else if (c == Operator.Type.POWER.getSymbol()) return Operator.Type.POWER;
        else throw new IllegalArgumentException("Character \""+c+"\" is not recognized as a supported operator");
    }

    private static double computeExpression(Node n, double[] numbers) throws IllegalArgumentException {
        Function<double[], Double> f = findFunction(n);
        return f.apply(numbers);
    }

    private static Function<double[], Double> findFunction(Node n) throws IllegalArgumentException {
        if (n.getChildren().size() != 2) throw new IllegalArgumentException();
        String s = n.toString();
        String a = n.getChildren().get(0).toString();

        for (int i = a.length(); i < s.length(); i++) {
            try {
                Operator.Type op = findOperatorType(s.charAt(i));
                return op.getFunction();
            } catch (IllegalArgumentException e) {
                //throw a new one later
            }
        }
        throw new IllegalArgumentException("No supported function found in node \""+n.toString()+"\"");
    }
}

