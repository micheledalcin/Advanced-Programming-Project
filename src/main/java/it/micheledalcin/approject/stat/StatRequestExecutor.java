package it.micheledalcin.approject.stat;
import it.micheledalcin.approject.DoubleArray;
import it.micheledalcin.approject.LineProcessingServer;
import it.micheledalcin.approject.exceptions.DataNotFoundException;

public class StatRequestExecutor {

    public static String executeStatReq() {
        return Integer.toString(LineProcessingServer.okResponseTimes.size());
    }

    public static String executeStatAvgTime() throws IllegalArgumentException, DataNotFoundException {
        double[] values = new double[LineProcessingServer.okResponseTimes.size()];
        for (int i = 0; i< LineProcessingServer.okResponseTimes.size(); i++) {
            values[i] = LineProcessingServer.okResponseTimes.get(i);
        }
        return Double.toString(DoubleArray.computeAverage(values));
    }

    public static String executeStatMaxTime() throws IllegalArgumentException, DataNotFoundException {
        double[] values = new double[LineProcessingServer.okResponseTimes.size()];
        for (int i = 0; i< LineProcessingServer.okResponseTimes.size(); i++) {
            values[i] = LineProcessingServer.okResponseTimes.get(i);
        }
        return Double.toString(DoubleArray.max(values));
    }
}
