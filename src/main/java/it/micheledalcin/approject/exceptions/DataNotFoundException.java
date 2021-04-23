package it.micheledalcin.approject.exceptions;

public class DataNotFoundException extends Exception {
    private String errorMessage;

    public DataNotFoundException(String errorMessage) { this.errorMessage = errorMessage; }

    @Override
    public String getMessage() { return this.errorMessage; }
}
