// RandomVaries class, defines appliances with random on status & variable consumption
public class RandomVaries extends Appliance
{
	//instance variables for probability it is on, min and max consumption rates
	private int xInOneInX;
	private float minConsumptionRate;
	private float maxConsumptionRate;
	
	//constructor for class, takes superclass parameter and parameters for instance variables
	public RandomVaries(String name, String utilityConsumed, int xInOneInX, float minRate,float maxRate)
	{
		super(name, utilityConsumed);
		this.minConsumptionRate = minRate;
		this.maxConsumptionRate = maxRate;
		this.xInOneInX = xInOneInX;
	}
	
	/* timePassesmethod. gets appliance status using method in Appliance class then consumes a random amount
	* in consumption range. Throws an exception if no meter is attached */
	public void timePasses() throws Exception
	{
		if (this.attachedMeter == null)
		{
			throw new Exception("No meter attached to appliance, can't call timePassed!");
		}
		
		if(probabilityApplianceStatus(xInOneInX))
		{
			this.tellMeterToConsumeUnits(getRandomFloatInRange(minConsumptionRate, maxConsumptionRate));
		}
	}
}
