package it.micheledalcin.approject.computation;

import it.micheledalcin.approject.RequestType;

public enum ComputationType implements RequestType {

    MINIMUM("MIN"),
    MAXIMUM("MAX"),
    AVERAGE("AVG"),
    COUNT("COUNT");

    private final String type;

    ComputationType(String type) {
        this.type = type;
    }

    public String getType() { return this.type; }

}
