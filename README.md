# Random Number Distribution Tracker

A java class to track the distribution of random numbers.

## Purpose

I wanted to build a simple process to verify that the random numbers I was using for a simulation were actually from an exponential distribution. I also wanted
to see the effect of changing lambda values.

So I created a simple class (called NumberDistributionTracker), where I could store the numbers and output their frequencies to confirm their distribution.

The class also allows the boundaries of the frequency distribution to be adjusted.


## Implementation

This code creates 10,000 random numbers from a negative exponential distribution and assigns them to the number tracker class.

It then prints out a summary of the distribution.

        NumberDistributionTracker numberDistributionTracker = new NumberDistributionTracker();
        RandomGenerator randomGenerator = new RandomGenerator();
        double lambda = 5;
        for(int i=0;i<=10000;i++){
            double random = randomGenerator.getNegativeExponentialRandomNext(lambda);
            numberDistributionTracker.addNumber(random);
        }
        numberDistributionTracker.printDistribution();
        
        
## Output

This is what gets printed to the Console window when the above code is run:

```
distribution, frequency, total, mean, std_dev, variance, min, max
{0.00 to 0.10}, 3918, 179.39, 0.05, 0.03, 0.0, 0.0, 0.1
{0.10 to 0.20}, 2346, 342.3, 0.15, 0.03, 0.0, 0.1, 0.2
{0.20 to 0.30}, 1464, 358.82, 0.25, 0.03, 0.0, 0.2, 0.3
{0.30 to 0.40}, 883, 306.28, 0.35, 0.03, 0.0, 0.3, 0.4
{0.40 to 0.50}, 516, 229.38, 0.44, 0.03, 0.0, 0.4, 0.5
{0.50 to 0.60}, 365, 199.67, 0.55, 0.03, 0.0, 0.5, 0.6
{0.60 to 0.70}, 211, 135.87, 0.64, 0.03, 0.0, 0.6, 0.7
{0.70 to 0.80}, 116, 86.98, 0.75, 0.03, 0.0, 0.7, 0.8
{0.80 to 0.90}, 66, 55.59, 0.84, 0.03, 0.0, 0.8, 0.9
{0.90 to 1.00}, 55, 51.8, 0.94, 0.03, 0.0, 0.9, 1.0
{1.00+}, 61, 73.56, 1.21, 0.25, 0.06, 1.0, 2.38
```

## Configure

You can define class boundaries for each distribution by specifying a min, max and width value in the class constructor.
For example, the following line would group the numbers from 0 to 1, in band widths of 0.25:

        NumberDistributionTracker numberDistributionTracker = new NumberDistributionTracker(0,1,0.25);
        
You can also change the width of an existing distribution like so:

        numberDistributionTracker.changeDistributionForWidth(0.25);
        numberDistributionTracker.printDistribution();
 
Here is the output for a distribution where the width has been changed to 0.25:
 
```
distribution, frequency, total, mean, std_dev, variance, min, max
{0.00 to 0.25}, 2500, 303.14, 0.12, 0.07, 0.01, 0.0, 0.25
{0.25 to 0.50}, 2446, 921.16, 0.38, 0.07, 0.01, 0.25, 0.5
{0.50 to 0.75}, 2499, 1558.88, 0.62, 0.07, 0.01, 0.5, 0.75
{0.75 to 1.00}, 2556, 2234.18, 0.87, 0.07, 0.01, 0.75, 1.0
{1.00+}, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
```
