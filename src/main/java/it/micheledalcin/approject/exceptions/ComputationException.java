package it.micheledalcin.approject.exceptions;

public class ComputationException extends Exception {

    private String errorMessage;

    public ComputationException() { this.errorMessage = "ComputationException"; }

    public ComputationException(String errorMessage) { this.errorMessage = errorMessage; }

    @Override
    public String getMessage() { return this.errorMessage; }
}
