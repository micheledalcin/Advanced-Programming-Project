package it.micheledalcin.approject.computation;

import it.micheledalcin.approject.DoubleArray;
import it.micheledalcin.approject.computation.expression.Expression;
import it.micheledalcin.approject.exceptions.ComputationException;
import it.micheledalcin.approject.exceptions.DataNotFoundException;
import java.util.*;

public class ComputationRequestExecutor{

    public static List<NTuple<VariableValue>> computeTuple(List<VariableValues> variableValuesList, ComputationValuesType valuesType) throws IllegalArgumentException {
        if (valuesType == ComputationValuesType.LIST) return computeTupleAsList(variableValuesList);
        else return computeTupleAsGrid(variableValuesList);
    }

    public static double computeResult(ComputationType computationType, List<NTuple<VariableValue>> tuple, Expression[] expressionArray) throws IllegalArgumentException, ComputationException, DataNotFoundException {
        if (computationType==ComputationType.COUNT) { return tuple.size(); }
        else if (computationType==ComputationType.AVERAGE) {
            double[] results = new double[tuple.size()];
            int k = 0;
            for (int j=0; j<tuple.size(); j++) {
                results[k] = computeO(tuple.get(j), expressionArray[0]);
                k++;
            }
            return DoubleArray.computeAverage(results);
        }
        else {
            double[] results = new double[tuple.size()*expressionArray.length];
            int k = 0;
            for (int i=0; i<expressionArray.length; i++) {
                for (int j=0; j<tuple.size(); j++) {
                    results[k] = computeO(tuple.get(j), expressionArray[i]);
                    k++;
                }
            }
            if (computationType==ComputationType.MAXIMUM) return DoubleArray.max(results);
            else return DoubleArray.min(results);
        }
    }

    private static double computeO(NTuple<VariableValue> nTuple, Expression expression) throws  IllegalArgumentException, ComputationException {
        Map<String, Double> map = new HashMap<>();
        for (int i=0; i<nTuple.getElements().size(); i++) {
            map.put(nTuple.getElements().get(i).getVariableName(), nTuple.getElements().get(i).getValue());
        }
        return expression.compute(map);
    }

    private static List<NTuple<VariableValue>> computeTupleAsGrid(List<VariableValues> listOfA) {
        if (listOfA.size() == 0) throw new IllegalArgumentException("Not enough VariableValuesFunction for GRID command");
        else if (listOfA.size() == 1) return convertListA(listOfA.get(0));
        List<Set<VariableValue>> sets = new LinkedList<>();
        for (int i=0; i<listOfA.size(); i++) {
            Set<VariableValue> set = new HashSet<>();
            for (int j=0; j<listOfA.get(i).getValues().length; j++) {
                set.add(new VariableValue(listOfA.get(i).getVariableName(), listOfA.get(i).getValues()[j]));
            }
            sets.add(set);
        }
        Set<Set<VariableValue>> setResult = cartesianProduct(0, sets);
        List<NTuple<VariableValue>> list = new LinkedList<>();
        for (Set<VariableValue> setResultElement: setResult) {
            List<VariableValue> couples = new LinkedList<>(setResultElement);
            NTuple<VariableValue> nTuple = new NTuple<>(couples);
            list.add(nTuple);
        }
        return list;
    }

    private static List<NTuple<VariableValue>> computeTupleAsList(List<VariableValues> listOfA) throws IllegalArgumentException {
        if (listOfA.size() == 1) {
            return convertListA(listOfA.get(0));
        }
        int len = listOfA.get(0).getValues().length;
        for (VariableValues a: listOfA) {
            if (!(a.getValues().length == len)) throw new IllegalArgumentException("LIST command on lists (computed from function a) with different length");
        }
        List<NTuple<VariableValue>> tupleList = new LinkedList<>();
        for (int i=0; i<len; i++) {
            List<VariableValue> temp  = new LinkedList<>();
            for (int j=0; j<listOfA.size(); j++) {
                temp.add(j, new VariableValue(listOfA.get(j).getVariableName(), listOfA.get(j).getValues()[i]));
            }
            tupleList.add(new NTuple<>(temp));
        }
        return tupleList;
    }

    public static List<NTuple<VariableValue>> convertListA(VariableValues variableValues) {
        List<NTuple<VariableValue>> list = new LinkedList<>();
        for (int i=0; i<variableValues.getConstants().length; i++) {
            List<VariableValue> v = new LinkedList<>();
            v.add(new VariableValue(variableValues.getVariable(), variableValues.getConstants()[i]));
            list.add(new NTuple<>(v));
        }
        return list;
    }

    private static Set<Set<VariableValue>> cartesianProduct(int index, List<Set<VariableValue>> list) {
        Set<Set<VariableValue>> setResult = new HashSet<>();
        if (index == list.size()) {
            setResult.add(new HashSet<>());
        }
        else {
            for (VariableValue v : list.get(index)) {
                for (Set<VariableValue> set : cartesianProduct(index+1, list)) {
                    set.add(v);
                    setResult.add(set);
                }
            }
        }
        return setResult;
    }

}
