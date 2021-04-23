package it.micheledalcin.approject.computation;

import it.micheledalcin.approject.Request;
import it.micheledalcin.approject.RequestType;
import it.micheledalcin.approject.computation.expression.Expression;
import it.micheledalcin.approject.computation.expression.ExpressionMethods;
import it.micheledalcin.approject.exceptions.ComputationException;
import it.micheledalcin.approject.exceptions.DataNotFoundException;
import it.micheledalcin.approject.exceptions.ParsingNotPossibleException;
import java.util.LinkedList;
import java.util.List;

public class ComputationRequest implements Request {

    private final String inputString;
    private final ComputationType computationType;
    private final ComputationValuesType valuesType;
    private final String[] variableValuesFunctions;
    private final Expression[] expressions;

    public ComputationRequest(String inputString, ComputationType computationType) throws IllegalArgumentException, ParsingNotPossibleException {
        this.inputString = inputString;
        this.computationType = computationType;
        String[] tokens = StringParser.parseComputationRequest(inputString);
        this.valuesType = ComputationValuesType.findComputationValuesType(tokens[1]);
        this.variableValuesFunctions = StringParser.parseTokens(tokens[2], ',');
        this.expressions = ExpressionMethods.createExpression(StringParser.parseTokens(tokens[3], ';'));
    }
    @Override
    public String getRequest() { return this.inputString;}

    @Override
    public RequestType getRequestType() { return this.computationType;}

    public ComputationValuesType getValuesType() { return this.valuesType;}


    @Override
    public String executeRequest() throws ParsingNotPossibleException, IllegalArgumentException, ComputationException, DataNotFoundException {
        Tuple[] tuples = TupleMethods.convertStringArrayToTupleArray(this.variableValuesFunctions);
        List<VariableValues> listOfAs = new LinkedList<>();
        for (int i = 0; i< tuples.length; i++) {
            listOfAs.add(tuples[i].parseVariableValuesFunctionA());
        }
        List<NTuple<VariableValue>> T = ComputationRequestExecutor.computeTuple(listOfAs, this.valuesType);
        double result = ComputationRequestExecutor.computeResult(computationType, T, expressions);
        if ((result % 1)==0) return Integer.toString((int) result);
        else return Double.toString(result);
    }

    @Override
    public String toString() {
        String string = "ComputationRequest: "+this.computationType.getType()+" "+this.valuesType.getCommand();
        string = string.concat(System.lineSeparator());
        for (int i=0; i<this.variableValuesFunctions.length; i++) {
            string = string.concat("variableValue["+i+"] "+this.variableValuesFunctions[i]+System.lineSeparator());
        }
        for (int j=0; j<this.expressions.length; j++) {
            string = string.concat("expression["+j+"] "+ this.expressions[j]+ System.lineSeparator());
        }
        return string;
    }

}
