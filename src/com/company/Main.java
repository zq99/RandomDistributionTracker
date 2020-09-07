package com.company;

public class Main {

    public static void main(String[] args) {
        runExamples();
    }

    private static void runExamples(){
        exponentialDistributionExample();
        System.out.println();
        uniformDistributionExample();
    }

    private static void exponentialDistributionExample(){
        RandomDistributionTracker randomDistributionTracker = new RandomDistributionTracker();
        RandomGenerator randomGenerator = new RandomGenerator();
        double lambda = 5;
        for(int i=0;i<=100;i++){
            double random = randomGenerator.getExponentialRandomNext(lambda);
            randomDistributionTracker.addRandomNumber(random);
        }
        randomDistributionTracker.printDistribution();
    }

    private static void uniformDistributionExample(){
        RandomDistributionTracker randomDistributionTracker = new RandomDistributionTracker(0,1,0.1);
        RandomGenerator randomGenerator = new RandomGenerator();
        for(int i=0;i<=10000;i++){
            double random = randomGenerator.getUniformRandomNext();
            randomDistributionTracker.addRandomNumber(random);
        }
        randomDistributionTracker.printDistribution();
    }
}
