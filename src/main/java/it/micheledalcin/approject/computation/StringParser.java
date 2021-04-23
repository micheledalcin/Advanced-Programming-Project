package it.micheledalcin.approject.computation;

import it.micheledalcin.approject.Request;
import it.micheledalcin.approject.exceptions.ParsingNotPossibleException;
import it.micheledalcin.approject.exceptions.InvalidRequestException;
import it.micheledalcin.approject.stat.StatRequest;
import it.micheledalcin.approject.stat.StatRequestType;

public class StringParser {

    public static String[] parseTokens(String string, char separator) throws ParsingNotPossibleException {
        if (string.equals("")) throw new ParsingNotPossibleException("String \"" + string + "\" cannot be parsed because it is empty");
        int countSeparators = 0;
        int index = 0;
        for (int i=0; i<string.length(); i++) {
            if (string.charAt(i) == separator) countSeparators++;
        }
        String[] tokens = new String[countSeparators + 1];
        for (int j=0; j<tokens.length; j++) {
            try {
                tokens[j] = findSubstringWithIndex(index, string, separator);
                index = index + tokens[j].length() + 1;
            } catch (IllegalArgumentException e) {
                throw new ParsingNotPossibleException("The input string \"" + string + "\" does not comply to a well-written Request of any type");
            }
        }
        return tokens;
    }

    public static String findSubstringWithIndex(int index, String string, char separator) throws IllegalArgumentException {
        if (index >= string.length() || string.equals("")) throw new IllegalArgumentException();
        int end = 0;
        for (int i=index; i<string.length(); i++) {
            if (i == string.length()-1) {
                if (string.charAt(i) == separator) {
                    end = i;
                    break;
                }
                end = -1;
                break;
            }
            else if (string.charAt(i) == separator) {
                end = i;
                break;
            }
        }
        if (end == -1) return string.substring(index);
        else return string.substring(index, end);
    }

    public static String[] parseComputationRequest(String input) throws ParsingNotPossibleException {
        String[] tokens = new String[4];
        int index = 0;
        try {
            tokens[0] = StringParser.findSubstringWithIndex(index, input, '_');
        } catch (IllegalArgumentException e) {
            throw new ParsingNotPossibleException("The input string \""+input+"\" does not have a correct ComputationKind field");
        }
        if (!(tokens[0].equals(""))) {
            index = index + tokens[0].length() + 1;
            try {
                tokens[1] = StringParser.findSubstringWithIndex(index, input, ';');
            } catch (IllegalArgumentException e) {
                throw new ParsingNotPossibleException("The input string \""+input+"\" does not have a correct ComputationValuesKind field");
            }
            if (!(tokens[1].equals(""))) {
                index = index + tokens[1].length() + 1;
                try {
                    tokens[2] = StringParser.findSubstringWithIndex(index, input, ';');
                } catch (IllegalArgumentException e) {
                    throw new ParsingNotPossibleException("The input string \""+input+"\" does not have a correct VariableValuesFunction field");
                }
                if (!(tokens[2].equals(""))) {
                    index = index + tokens[2].length() + 1;
                    try {
                        tokens[3] = StringParser.findSubstringWithIndex(index, input, ',');
                        return tokens;
                    } catch (IllegalArgumentException e) {
                        throw new ParsingNotPossibleException("The input string \""+input+"\" does not have a correct Expressions field");
                    }
                }
            }
        }
        throw new ParsingNotPossibleException("The input string \"" + input + "\" does not comply to a well-written Request of any type");
    }

    public static Request parseRequest(String inputString) throws InvalidRequestException, ParsingNotPossibleException {
        String startString = null;
        try {
            startString = findSubstringWithIndex(0, inputString, ';');
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("The input string \"" + inputString + "\" doesn't have a correct Request type");
        }
        if (startString.equals(StatRequestType.STAT_REQUEST.getType()))
            return new StatRequest(inputString, StatRequestType.STAT_REQUEST);
        else if (startString.equals(StatRequestType.STAT_AVERAGE_TIME.getType()))
            return new StatRequest(inputString, StatRequestType.STAT_AVERAGE_TIME);
        else if (startString.equals(StatRequestType.STAT_MAX_TIME.getType()))
            return new StatRequest(inputString, StatRequestType.STAT_MAX_TIME);
        try {
            startString = findSubstringWithIndex(0, inputString, '_');
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("The input string \"" + inputString + "\" does not comply to a well-written ComputationRequest");
        }
        if (startString.equals(ComputationType.MINIMUM.getType()))
            return new ComputationRequest(inputString, ComputationType.MINIMUM);
        else if (startString.equals(ComputationType.MAXIMUM.getType()))
            return new ComputationRequest(inputString, ComputationType.MAXIMUM);
        else if (startString.equals(ComputationType.AVERAGE.getType()))
            return new ComputationRequest(inputString, ComputationType.AVERAGE);
        else if (startString.equals(ComputationType.COUNT.getType()))
            return new ComputationRequest(inputString, ComputationType.COUNT);
        else
            throw new InvalidRequestException("The input string \"" + inputString + "\" does not comply to a well-written Request of any type");
    }
}