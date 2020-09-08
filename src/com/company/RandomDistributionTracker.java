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

public class RandomDistributionTracker {

    private LinkedHashMap<Boundary, RandomDistribution> randomDistributionHashMap;
    private ArrayList<Boundary> boundaries;

    public RandomDistributionTracker() {
        // default boundaries for grouping are based upon the numbers being
        // percentage probabilities between 0 and 1
        initialize(0,1.0,0.1);
    }

    public RandomDistributionTracker(double min, double max, double width) {
        initialize(min,max,width);
    }

    private void initialize(double min, double max, double width){
        this.randomDistributionHashMap = new LinkedHashMap<>();
        this.boundaries = Boundary.getBoundaries(min,max,width);
        for(Boundary boundary : boundaries){
            this.randomDistributionHashMap.put(boundary, new RandomDistribution());
        }
    }

    public void addRandomNumber(double n){
        Boundary boundary = getBoundary(n);
        if(boundary !=null) {
            if (randomDistributionHashMap.containsKey(boundary)) {
                RandomDistribution randomDistribution = this.randomDistributionHashMap.get(boundary);
                randomDistribution.addNumber(n);
                this.randomDistributionHashMap.put(boundary, randomDistribution);
            }
        }
    }

    public static double round(double value, int places) {
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

        if(this.randomDistributionHashMap.size() > 0) {
            String headers = "distribution, frequency, total, mean, std_dev, variance, min, max";
            System.out.println(headers);
            for (Map.Entry<Boundary, RandomDistribution> entry : this.randomDistributionHashMap.entrySet()) {
                Boundary boundary = entry.getKey();
                RandomDistribution randomDistribution = entry.getValue();
                String summary = boundary.toString() + ", " +
                        randomDistribution.getFrequency() + ", " +
                        round(randomDistribution.getTotal(),decimalCount) + ", " +
                        round(randomDistribution.getMean(),decimalCount) + ", " +
                        round(randomDistribution.getStdDev(),decimalCount) + ", " +
                        round(randomDistribution.getVariance(),decimalCount) + ", " +
                        round(randomDistribution.getMinValue(),decimalCount) + ", " +
                        round(randomDistribution.getMaxValue(),decimalCount);

                System.out.println(summary);
            }
        }else{
            System.out.println("no numbers found!");
        }
    }

    private Boundary getBoundary(double value) {
        for(Boundary boundary : boundaries){
            if(boundary.isValueInRange(value)){
                return boundary;
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
                return format(this.lower) + "+";
            }
            return format(this.lower) + " - " + format(this.upper);
        }

        private String format(double value){
            return String.format("%.2f", value);
        }

        public static ArrayList<Boundary> getBoundaries(double min, double max, double width){
            ArrayList<Boundary> boundaries = new ArrayList<>();
            while(min <= max){
                double lower = min;
                double upper = (lower+width) > max ? Double.MAX_VALUE : lower+width;
                Boundary boundary = new Boundary(lower,upper);
                boundaries.add(boundary);
                min+=width;
            }
            return boundaries;
        }
    }

    private static class RandomDistribution {

        // this is for storing information about each distribution of a number

        private int frequency=0;
        private double total=0.0;
        private final ArrayList<Double> numbersAdded = new ArrayList<>();
        private double minValue=0.0;
        private double maxValue=0.0;

        public RandomDistribution() {
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
            if(numbersAdded.size() == 0){
                return 0;
            }
            double mean = getMean();
            double temp = 0;
            for (double val : numbersAdded) {
                double squareDiffToMean = Math.pow(val - mean, 2);
                temp += squareDiffToMean;
            }
            double meanOfDiffs = temp / (double) (numbersAdded.size());
            return Math.sqrt(meanOfDiffs);
        }

        public double getVariance()
        {
            if(numbersAdded.size() == 0){
                return 0;
            }
            double mean = getMean();
            double square = 0;
            for(double val :numbersAdded)
                square += (val-mean)*(val-mean);
            return square/numbersAdded.size();
        }

        public void addNumber(double number){
            this.frequency +=1;
            this.total+=number;
            if(this.numbersAdded.size()==0){
                this.maxValue = number;
                this.minValue = number;
            }else{
                this.minValue = Math.min(number, this.minValue);
                this.maxValue = Math.max(number,this.maxValue);
            }
            this.numbersAdded.add(number);
        }

    }
}
