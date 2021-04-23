package it.micheledalcin.approject;

import it.micheledalcin.approject.exceptions.ComputationException;
import it.micheledalcin.approject.exceptions.DataNotFoundException;
import it.micheledalcin.approject.exceptions.InvalidRequestException;
import it.micheledalcin.approject.exceptions.ParsingNotPossibleException;
import java.io.IOException;

public interface Request {
    RequestType getRequestType();
    String getRequest();
    String executeRequest() throws InvalidRequestException, IOException, ParsingNotPossibleException, IllegalArgumentException, DataNotFoundException, ComputationException;
}
