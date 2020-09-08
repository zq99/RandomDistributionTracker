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
distribution, frequency, total, mean, std_dev, variance, min, max
{0.00 to 0.10}, 953, 48.45, 0.05, 0.03, 0.0, 0.0, 0.1
{0.10 to 0.20}, 993, 150.18, 0.15, 0.03, 0.0, 0.1, 0.2
{0.20 to 0.30}, 982, 243.76, 0.25, 0.03, 0.0, 0.2, 0.3
{0.30 to 0.40}, 972, 340.93, 0.35, 0.03, 0.0, 0.3, 0.4
{0.40 to 0.50}, 983, 443.04, 0.45, 0.03, 0.0, 0.4, 0.5
{0.50 to 0.60}, 1049, 578.17, 0.55, 0.03, 0.0, 0.5, 0.6
{0.60 to 0.70}, 1027, 667.09, 0.65, 0.03, 0.0, 0.6, 0.7
{0.70 to 0.80}, 1048, 785.01, 0.75, 0.03, 0.0, 0.7, 0.8
{0.80 to 0.90}, 1037, 881.82, 0.85, 0.03, 0.0, 0.8, 0.9
{0.90 to 1.00}, 957, 907.89, 0.95, 0.03, 0.0, 0.9, 1.0
{1.00+}, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
```
