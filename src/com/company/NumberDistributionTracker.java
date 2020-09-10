package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/*
This is class to track the generation of random numbers in program.
The purpose to is to validate how the numbers are being distributed.
 */

public class NumberDistributionTracker {

    private LinkedHashMap<Boundary, FrequencyDistribution> numberDistributionHashMap;
    private ArrayList<Boundary> boundaries;

    // default boundaries for grouping are based upon the numbers being
    // percentage probabilities between 0 and 1

    private double min = 0;
    private double max = 1.0;
    private double width = 0.1;
    private ArrayList<Double> numbersAdded;

    public NumberDistributionTracker() {
        initialize();
    }

    public NumberDistributionTracker(double min, double max, double width) {
        this.min = min;
        this.max = max;
        this.width = width;
        initialize();
    }

    private void initialize(){
        this.numbersAdded = new ArrayList<>();
        createNewDistribution();
    }

    private void createNewDistribution() {
        this.numberDistributionHashMap = new LinkedHashMap<>();
        this.boundaries = Boundary.getBoundaries(min, max, width);
        for (Boundary boundary : boundaries) {
            this.numberDistributionHashMap.put(boundary, new FrequencyDistribution());
        }
    }

    public void addNumber(double n){
        Boundary boundary = getBoundaryForValue(n);
        if(boundary !=null) {
            if (numberDistributionHashMap.containsKey(boundary)) {
                this.numbersAdded.add(n);
                updateFrequencyForBoundary(boundary,n);
            }
        }
    }

    private void updateFrequencyForBoundary(Boundary boundary, double n){
        FrequencyDistribution frequencyDistribution = this.numberDistributionHashMap.get(boundary);
        frequencyDistribution.addNumber(n);
        this.numberDistributionHashMap.put(boundary, frequencyDistribution);
    }

    public void reset(){
        initialize();
    }

    public void changeDistributionForWidth(double width){
        // this allows for an existing set of numbers to be 're-dimensioned'
        // into a different set of bands
        this.width = width;
        createNewDistribution();
        if(this.numbersAdded.size() > 0) {
            for (double n : this.numbersAdded) {
                Boundary boundary = getBoundaryForValue(n);
                if(boundary !=null) {
                    if (numberDistributionHashMap.containsKey(boundary)) {
                        updateFrequencyForBoundary(boundary,n);
                    }
                }
            }
        }
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void printDistribution(){
        printDistribution(2);
    }

    public void printDistribution(int decimalCount){

        // this is the main output in the following format:
        // distribution, frequency, total, mean, std_dev, variance,min, max
        // 0.00 - 0.10, 1017, 51.74, 0.05, 0.02, 8.32, 7.95, 0.09

        if(this.numberDistributionHashMap.size() > 0) {
            String headers = "distribution, frequency, total, mean, std_dev, variance, min, max";
            System.out.println(headers);
            for (Map.Entry<Boundary, FrequencyDistribution> entry : this.numberDistributionHashMap.entrySet()) {
                Boundary boundary = entry.getKey();
                FrequencyDistribution frequencyDistribution = entry.getValue();
                String summary = boundary.toString() + ", " +
                        frequencyDistribution.getFrequency() + ", " +
                        round(frequencyDistribution.getTotal(),decimalCount) + ", " +
                        round(frequencyDistribution.getMean(),decimalCount) + ", " +
                        round(frequencyDistribution.getStdDev(),decimalCount) + ", " +
                        round(frequencyDistribution.getVariance(),decimalCount) + ", " +
                        round(frequencyDistribution.getMinValue(),decimalCount) + ", " +
                        round(frequencyDistribution.getMaxValue(),decimalCount);

                System.out.println(summary);
            }
        }else{
            System.out.println("no numbers found!");
        }
    }

    private Boundary getBoundaryForValue(double value) {
        for(Boundary boundary : boundaries){
            if(boundary.isValueInRange(value)){
                return boundary;
            }
        }
        return null;
    }

    public ArrayList<Double> getNumbersInDistributionForBoundary(String name){

        // takes in the to_string value of a boundary
        // to retrieve all numbers for the frequency in the boundary

        for(Boundary boundary : boundaries){
            if(boundary.toString().equals(name)){
                FrequencyDistribution frequencyDistribution = numberDistributionHashMap.get(boundary);
                if(frequencyDistribution!=null){
                    return frequencyDistribution.getNumbersInDistribution();
                }
            }
        }
        return null;
    }

    private static class Boundary{

        // inner class stores the limits to each boundary and validates

        private final double lower;
        private final double upper;

        public Boundary(double lower, double upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public boolean isValueInRange(double value){
            return value >= this.lower && value < this.upper;
        }

        public String toString(){
            if(this.upper == Double.MAX_VALUE){
                return "{" + format(this.lower) + "+}";
            }
            return "{" + format(this.lower) + " to " + format(this.upper) + "}";
        }

        private String format(double value){
            return String.format("%.2f", value);
        }

        public static ArrayList<Boundary> getBoundaries(double min, double max, double width){
            ArrayList<Boundary> boundaries = new ArrayList<>();
            while(min <= max){
                double lower = min;
                double upper = (lower+width) > max ? Double.MAX_VALUE : lower+width;
                boundaries.add(new Boundary(lower,upper));
                min+=width;
            }
            return boundaries;
        }
    }

    private static class FrequencyDistribution {

        // this is for storing information about each distribution of a number

        private int frequency=0;
        private double total=0.0;
        private final ArrayList<Double> numbersInDistribution;
        private double minValue=0.0;
        private double maxValue=0.0;

        public FrequencyDistribution() {
            this.numbersInDistribution = new ArrayList<>();
        }

        public ArrayList<Double> getNumbersInDistribution(){
            return this.numbersInDistribution;
        }

        public int getFrequency() {
            return frequency;
        }

        public double getTotal() {
            return total;
        }

        public double getMean(){
            if(this.frequency > 0) {
                return this.total / this.frequency;
            }
            return 0;
        }

        public double getMinValue() {
            return minValue;
        }

        public double getMaxValue() {
            return maxValue;
        }

        public double getStdDev()
        {
            if(numbersInDistribution.size() == 0){
                return 0;
            }
            double mean = getMean();
            double temp = 0;
            for (double val : numbersInDistribution) {
                double squareDiffToMean = Math.pow(val - mean, 2);
                temp += squareDiffToMean;
            }
            double meanOfDiffs = temp / (double) (numbersInDistribution.size());
            return Math.sqrt(meanOfDiffs);
        }

        public double getVariance()
        {
            if(numbersInDistribution.size() == 0){
                return 0;
            }
            double mean = getMean();
            double square = 0;
            for(double val : numbersInDistribution)
                square += (val-mean)*(val-mean);
            return square/ numbersInDistribution.size();
        }

        public void addNumber(double number){
            this.frequency +=1;
            this.total+=number;
            if(this.numbersInDistribution.size()==0){
                this.maxValue = number;
                this.minValue = number;
            }else{
                this.minValue = Math.min(number, this.minValue);
                this.maxValue = Math.max(number,this.maxValue);
            }
            this.numbersInDistribution.add(number);
        }

    }
}
