package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        runExamples();
    }

    private static void runExamples(){
        negativeExponentialDistributionExample();
        System.out.println();
        uniformDistributionExample();
    }

    private static void negativeExponentialDistributionExample(){
        NumberDistributionTracker numberDistributionTracker = new NumberDistributionTracker();
        RandomGenerator randomGenerator = new RandomGenerator();
        double lambda = 5;
        for(int i=0;i<=10000;i++){
            double random = randomGenerator.getNegativeExponentialRandomNext(lambda);
            numberDistributionTracker.addNumber(random);
        }
        numberDistributionTracker.printDistribution();
    }

    private static void uniformDistributionExample(){

        NumberDistributionTracker numberDistributionTracker = new NumberDistributionTracker(0,1,0.1);
        RandomGenerator randomGenerator = new RandomGenerator();
        for(int i=0;i<=10000;i++){
            double random = randomGenerator.getUniformRandomNext();
            numberDistributionTracker.addNumber(random);
        }
        numberDistributionTracker.printDistribution();

        System.out.println();

        //example of resizing the distribution after it has been populated with numbers
        resizeAndPrintTest(0.25,numberDistributionTracker);
    }


    private static void resizeAndPrintTest(double width, NumberDistributionTracker numberDistributionTracker){
        numberDistributionTracker.changeDistributionForWidth(0.25);
        numberDistributionTracker.printDistribution();
    }

    private static void listNumbersInDistributionTest(String band, NumberDistributionTracker numberDistributionTracker) {
        // this gets all the numbers in a frequency distribution for a boundary
        // the string 'band' refers to the value from the to_string method of the boundary class
        ArrayList<Double> numbers = numberDistributionTracker.getNumbersInDistributionForBoundary(band);
        for (double d : numbers) {
            System.out.println(d);
        }
    }


}
