Readme text file for my smart house simulator:

All parts of coursework attempted. 

To run program, include name of config file followed by an integer detailing the number of days you want this simulation to run as parameters.
Example:

java house Config file.txt 7

NOTE: Config file names with spaces in the middle are accepted by the program. However, last argument must be an integer.

Extension attempted: Feed in tariff:

An "ExcessMeter" class was made; it is a subclass of BatteryMeter. It has a randomly carying unitcost, whose min and max values depend on
a factor which is input in the constructor when a new instance of the class is made. It has a feed in tariff ratet which fluctuates
according to time of day between 3 values, also dependant on a factor input as a constructor parameter. Both factors must be bigger than 1, 
and cost factor must be bigger than tariff factor so that unitcost fluctuates at a wider range. This class decides whether to power
house with battery charge or to sell that charge to the elcetric company for the feed in tariff benefit, depending on prices. It reports
cost of units drawn from mains, money gained from selling to electric company and net cost per hour, other than what is reported by the
BatteryMeter.
