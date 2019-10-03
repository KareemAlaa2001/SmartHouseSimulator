//defining RandomFixed class. appliances with a random on status but fixed consumption
public class RandomFixed extends Appliance
{
	//instance variables for fixed consumption rate and probability appliance is on
	private float consumptionRate;
	private int xInOneInX;
	
	//constructor, takes superclass parameter and parameters for instance variables
	public RandomFixed(String name, String utilityUsed, int xInOneInX, float rateOfConsumption)
	{
		super(name, utilityUsed);
		this.consumptionRate = rateOfConsumption;
		this.xInOneInX = xInOneInX;
	}
	
	//timePasses definition, uses probability to check if on then consumes fixed rate. Throws an exception if no meter is attached
	public void timePasses() throws Exception
	{
		if (this.attachedMeter == null)
		{
			throw new Exception("No meter attached to appliance, can't call timePassed!");
		}
		if(probabilityApplianceStatus(xInOneInX))
		{
			this.tellMeterToConsumeUnits(consumptionRate);
		}
	}

}
