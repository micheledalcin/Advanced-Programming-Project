package it.micheledalcin.approject;

import it.micheledalcin.approject.computation.StringParser;
import it.micheledalcin.approject.exceptions.ComputationException;
import it.micheledalcin.approject.exceptions.DataNotFoundException;
import it.micheledalcin.approject.exceptions.InvalidRequestException;
import it.micheledalcin.approject.exceptions.ParsingNotPossibleException;
import java.io.IOException;
import java.util.Date;

public class CommandExecutor implements CommandProcessor {

    public synchronized String process(String inputString) {
        Request request;
        try {
            request = StringParser.parseRequest(inputString);
        } catch (InvalidRequestException | IllegalArgumentException | ParsingNotPossibleException exception) {
            System.err.println("["+(new Date())+"] "+exception.getClass().getSimpleName()+": "+exception.getMessage());
            return "ERR;("+exception.getClass().getSimpleName()+") "+exception.getMessage()+ System.lineSeparator();
        }
        try {
            return request.executeRequest();
        } catch (IllegalArgumentException | InvalidRequestException | IOException | ParsingNotPossibleException | DataNotFoundException | ComputationException exception) {
            System.err.println("["+(new Date())+"] "+exception.getClass().getSimpleName()+": "+exception.getMessage());
            return "ERR;("+exception.getClass().getSimpleName()+") "+exception.getMessage()+ System.lineSeparator();
        }
    }
}