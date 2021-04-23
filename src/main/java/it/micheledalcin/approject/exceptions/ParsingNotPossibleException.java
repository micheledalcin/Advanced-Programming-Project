package it.micheledalcin.approject.exceptions;

public class ParsingNotPossibleException extends Throwable {

    private String errorMessage;

    public ParsingNotPossibleException(String errorMessage) { this.errorMessage = errorMessage; }

    @Override
    public String getMessage() { return this.errorMessage; }
}