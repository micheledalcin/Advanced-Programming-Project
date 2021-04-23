package it.micheledalcin.approject.stat;

import it.micheledalcin.approject.Request;
import it.micheledalcin.approject.RequestType;
import it.micheledalcin.approject.exceptions.DataNotFoundException;
import it.micheledalcin.approject.exceptions.InvalidRequestException;

public class StatRequest implements Request {

    private String inputString;
    private StatRequestType requestType;


    public StatRequest(String inputString, StatRequestType requestType){
        this.inputString = inputString;
        this.requestType = requestType;
    }

    @Override
    public RequestType getRequestType() {
        return this.requestType;
    }

    @Override
    public String getRequest() {
        return this.inputString;
    }

    @Override
    public String executeRequest() throws InvalidRequestException, DataNotFoundException {
        if (this.getRequestType() ==  StatRequestType.STAT_REQUEST)
            return StatRequestExecutor.executeStatReq();
        else if (this.getRequestType() ==  StatRequestType.STAT_AVERAGE_TIME)
            return StatRequestExecutor.executeStatAvgTime();
        else if (this.getRequestType() ==  StatRequestType.STAT_MAX_TIME)
            return StatRequestExecutor.executeStatMaxTime();
        else throw new InvalidRequestException();
    }

    @Override
    public String toString() {
        return "StatRequest: "  +  this.getRequest();
    }
}
