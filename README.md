# Random Number Distribution Tracker

A java class to track the distribution of random numbers.

## Purpose

I wanted to build a simple process to verify that the random numbers I was using for a simulation were actually from an exponential distribution. I also wanted
to see the effect of changing lambda values.

So I created a simple class (called NumberDistributionTracker), where I could store the numbers and output their frequencies to confirm their distribution.

The class also allows the boundaries of the frequency distribution to be adjusted.


## Implementation

This code creates 10,000 random numbers from an exponential distribution and assigns them to the number tracker class.

It then prints out a summary of the distribution.

        NumberDistributionTracker numberDistributionTracker = new NumberDistributionTracker();
        RandomGenerator randomGenerator = new RandomGenerator();
        double lambda = 5;
        for(int i=0;i<=10000;i++){
            double random = randomGenerator.getExponentialRandomNext(lambda);
            numberDistributionTracker.addNumber(random);
        }
        numberDistributionTracker.printDistribution();
        
        
## Output

This is what gets printed to the Console window when the above code is run:

```
> distribution, frequency, total, mean, std_dev, variance,min, max
> 0.00 - 0.10, 401, 17.59, 0.04, 0.03, 0.0, 0.0, 0.1
> 0.10 - 0.20, 236, 34.79, 0.15, 0.03, 0.0, 0.1, 0.2
> 0.20 - 0.30, 135, 33.23, 0.25, 0.03, 0.0, 0.2, 0.3
> 0.30 - 0.40, 81, 27.74, 0.34, 0.03, 0.0, 0.3, 0.4
> 0.40 - 0.50, 50, 22.03, 0.44, 0.03, 0.0, 0.4, 0.5
> 0.50 - 0.60, 30, 16.38, 0.55, 0.02, 0.0, 0.5, 0.59
> 0.60 - 0.70, 32, 20.62, 0.64, 0.02, 0.0, 0.6, 0.69
> 0.70 - 0.80, 19, 14.15, 0.74, 0.02, 0.0, 0.71, 0.79
> 0.80 - 0.90, 6, 5.07, 0.85, 0.04, 0.0, 0.81, 0.89
> 0.90 - 1.00, 4, 3.76, 0.94, 0.02, 0.0, 0.92, 0.97
> 1.00+, 7, 7.88, 1.13, 0.09, 0.01, 1.02, 1.24
```
