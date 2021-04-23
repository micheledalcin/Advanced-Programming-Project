package it.micheledalcin.approject.computation;

import it.micheledalcin.approject.exceptions.ParsingNotPossibleException;

public class TupleMethods {

    public static Tuple[] convertStringArrayToTupleArray(String[] variableValuesFunctions) throws ParsingNotPossibleException, IllegalArgumentException {
        Tuple[] tuples = new Tuple[variableValuesFunctions.length];
        for (int i=0; i<variableValuesFunctions.length; i++) {
            tuples[i] = createTupleFromVariableValue(variableValuesFunctions[i]);
        }
        if (tuples.length > 1) {
            for (int i = 0; i < tuples.length; i++) {
                for (int j = i + 1; j < tuples.length; j++) {
                    if (tuples[i].getVariableName().equals(tuples[j].getVariableName()))
                        throw new IllegalArgumentException("VariableValuesFunction field contains variables with the same name \"" + tuples[i].getVariableName() + "\"");
                }
            }
        }
        return tuples;
    }

    public static Tuple createTupleFromVariableValue(String variableValuesFunction) throws ParsingNotPossibleException, IllegalArgumentException {
        int index = 0;
        String nameVariable;
        double lower, step, upper;
        try {
            nameVariable = StringParser.findSubstringWithIndex(index, variableValuesFunction, ':');
            index = index + nameVariable.length() + 1;
        } catch (IllegalArgumentException e) {
            throw new ParsingNotPossibleException("Can't parse VarName from VariableValuesFunction field \"" + variableValuesFunction + "\"");
        }
        try {
            String lowerString = StringParser.findSubstringWithIndex(index, variableValuesFunction, ':');
            try {
                lower = Double.parseDouble(lowerString);
            } catch (NumberFormatException e) {
                throw new ParsingNotPossibleException("Inferior limit JavaNum \"" + lowerString + "\" is not a parsable double");
            }
            index = index + lowerString.length() + 1;
        } catch (IllegalArgumentException e) {
            throw new ParsingNotPossibleException("Can't parse the inferior limit JavaNum from VariableValuesFunction field \"" + variableValuesFunction + "\"");
        }
        try {
            String stepString = StringParser.findSubstringWithIndex(index, variableValuesFunction, ':');
            try {
                step = Double.parseDouble(stepString);
            } catch (NumberFormatException e) {
                throw new ParsingNotPossibleException("Step JavaNum \"" + stepString + "\" is not a parsable double");
            }
            index = index + stepString.length() + 1;
        } catch (IllegalArgumentException e) {
            throw new ParsingNotPossibleException("Can't parse the step JavaNum from VariableValuesFunction field \"" + variableValuesFunction + "\"");
        }
        try {
            String upperString = StringParser.findSubstringWithIndex(index, variableValuesFunction, ',');
            try {
                upper = Double.parseDouble(upperString);
            } catch (NumberFormatException e) {
                throw new ParsingNotPossibleException("Step JavaNum \"" + upperString + "\" is not a parsable double");
            }
        } catch (IllegalArgumentException e) {
            throw new ParsingNotPossibleException("Can't parse the superior limit JavaNum from VariableValuesFunction field \"" + variableValuesFunction + "\"");
        }
        return new Tuple(nameVariable, lower, step, upper);
    }
}
