package it.micheledalcin.approject.stat;

import it.micheledalcin.approject.RequestType;

public enum StatRequestType implements RequestType {

    STAT_REQUEST("STAT_REQS"),
    STAT_AVERAGE_TIME("STAT_AVG_TIME"),
    STAT_MAX_TIME("STAT_MAX_TIME");

    private final String type;

    StatRequestType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
