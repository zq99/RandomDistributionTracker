package com.company;

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

        numberDistributionTracker.changeDistributionForWidth(0.25);
        numberDistributionTracker.printDistribution();

    }
}
