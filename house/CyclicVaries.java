// CyclicVaries class, defines appliances with fixed cycle length but random consumption rate
public class CyclicVaries extends Appliance{
	
	// intance variables for length of cycle, time passed, min and max rates of consumption
	private int cycleLength;
	private int timePassed;
	private float minConsumptionRate;
	private float maxConsumptionRate;
	
	// class constructor, takes superclass parameter, and other parameters for instance variables. sets time passed to zero
	public CyclicVaries(String applianceName, String utilityUsed, int lengthOfCycle, float minRate,float maxRate) throws IllegalArgumentException
	{
		super(applianceName, utilityUsed);
		this.minConsumptionRate = minRate;
		this.maxConsumptionRate = maxRate;
		this.timePassed = 0;
		
		// checks if cyclelength given is valid
		if (lengthOfCycle >=1 && lengthOfCycle <= 24)
		{
			this.cycleLength = lengthOfCycle;
		}
		else throw new IllegalArgumentException("Cycle length can only be between 1 and 24!");
	}
	
	// timePasses method. if cycle isnt over, a random amount of units in range is consumed, timePassed is reset every 24 hours
	// Throws an exception if no meter is attached
	public void timePasses() throws Exception
	{
		if (this.attachedMeter == null)
		{
			throw new Exception("No meter attached to appliance, can't call timePassed!");
		}
		this.timePassed += 1;
		if (timePassed <= cycleLength)
		{
			this.tellMeterToConsumeUnits(this.getRandomFloatInRange(this.minConsumptionRate, this.maxConsumptionRate));
		}
		if (timePassed == 24)
		{
			timePassed = 0;
		}
	}
}
