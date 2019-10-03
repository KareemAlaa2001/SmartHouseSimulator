/* declaration for cyclicfixed class which defines 
 * appliances with a fixed cycle length and fixed consumption */

public class CyclicFixed extends Appliance{

	//instance variables storing fixed consumption rate,cycle length and time passed since start 
	private float consumptionRate;
	private int cycleLength;
	private int timePassed;
	
	//constructor which takes superclass parameter and parameters for consumptionRate and cycleLength
	public CyclicFixed(String name, String utilityUsed,float rateOfConsumption,int lengthOfCycle) throws IllegalArgumentException
	{
		super(name, utilityUsed);
		this.timePassed = 0;
		this.consumptionRate = rateOfConsumption;
		
		//checking that Cyclelength is between 1 & 24 hours, throws exception if it isn't
		if (lengthOfCycle >=1 && lengthOfCycle <= 24)
		{
			this.cycleLength = lengthOfCycle;
		}
		else throw new IllegalArgumentException("Cycle length can only be between 1 and 24!");
	}
	
	// timePasses method definition. consumes fixed rate in hours less than or equal to cycleLength, timePassed is reset every 24 hours
	// throws an exception if no meter is attached
	public void timePasses() throws Exception
	{
		if (this.attachedMeter == null)
		{
			throw new Exception("No meter attached to appliance, can't call timePassed!");
		}
		
		this.timePassed += 1;
		if (timePassed <= cycleLength)
		{
			this.tellMeterToConsumeUnits(consumptionRate);
		}
		
		if (timePassed == 24)
		{
			timePassed = 0;
		}
	}
}
