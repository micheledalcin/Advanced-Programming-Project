package it.micheledalcin.approject.exceptions;

public class InvalidRequestException extends Exception {

    private String errorMessage;

    public InvalidRequestException() { this.errorMessage = "InvalidRequestException"; }

    public InvalidRequestException(String errorMessage) { this.errorMessage = errorMessage; }

    @Override
    public String getMessage() { return this.errorMessage; }
}