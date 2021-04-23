package it.micheledalcin.approject;

import it.micheledalcin.approject.exceptions.DataNotFoundException;

public class DoubleArray {
    public static double max(double... values) throws DataNotFoundException {
        if (values.length==0) throw new DataNotFoundException("No data available");
        double max = values[0];
        for (int i=1; i<values.length; i++) {
            max = (max > values[i]) ? max : values[i];
        }
        return max;
    }

    public static double min(double... values) throws DataNotFoundException {
        if (values.length==0) throw new DataNotFoundException("No data available");
        double min = values[0];
        for (int i=1; i<values.length; i++) {
            min = (min < values[i]) ? min : values[i];
        }
        return min;
    }

    public static double computeAverage(double... values) throws DataNotFoundException {
        if (values.length==0) throw new DataNotFoundException("No data available");
        double sum = 0;
        for (int i=0; i<values.length; i++) {
            sum = sum + values[i];
        }
        return (sum / values.length);
    }

}
