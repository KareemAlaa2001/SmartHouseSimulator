import java.util.Random;

/* Abstract superclass for all appliances, defines basic appliance constructor 
 *  and other general appliance methods used by appliances in a house
 */
public abstract class Appliance {
	
	//instance variables appliance name, attached meter and random number generator
	@SuppressWarnings("unused")
	private String name;
	protected Meter attachedMeter;
	Random randomNumberGenerator = new Random();
	private String utilityUsed;
	
	//appliance constructor, checks name of utility to make sure it is valid
	public Appliance(String applianceName, String utilityUsed) throws IllegalArgumentException
	{
		this.name = applianceName;
		if (utilityUsed.equals("electric") || utilityUsed.equals("electricity") || utilityUsed.equals("water"))
		{
			this.utilityUsed = utilityUsed;
		}
		else throw new IllegalArgumentException("Invalid utility consumed by appliance!");
	}
	
	//assigns a meter to the appliance and makes it the attached meter
	public void assignMeter(Meter assignedMeter)
	{
		//checks that assigned meter monitors the same utility as the one consumed by the appliance
		if (assignedMeter.getUtilityName().equals("electric"))
		{
			if (this.utilityUsed.equals("electric") || this.utilityUsed.equals("electricity"))
			{
				this.attachedMeter = assignedMeter;
			}
			else throw new IllegalArgumentException("Can't assign an electric meter to a non eletric Appliance!");
		}
		else if (assignedMeter.getUtilityName().equals("water"))
		{
			if (this.utilityUsed.equals("water"))
			{
				this.attachedMeter = assignedMeter;
			}
			else throw new IllegalArgumentException("Can't assign a water meter to a non water consuming Appliance!");
		}
		else throw new IllegalArgumentException("Can't assign a meter which monitors neither electricity nor water!");
		
	}

	//denotes 1 hour of time passing, defined in subclasses
	public abstract void timePasses() throws Exception;

	//attached meter consumes amount of units put in parameter.
	protected void tellMeterToConsumeUnits(float amountConsumed)
	{
		this.attachedMeter.consumeUnits(amountConsumed);
	}
	
	//Uses randomNumberGenerator to return a random float in the given range for use in cyclicVaries and randomVaries
	public float getRandomFloatInRange(float min, float max) throws IllegalArgumentException
	{
		//if max is positive max must be bigger than min
		//if max is 25 and min is 1 for example
		if (max > 0)
		{
			//makes sure max is bigger than min
			if(max>min)
			{
				//range is given, in this case 24
				float range = max - min;
				
				/*will return a random float in range, nexFloat returns a float between 0 and 1,
				 *  it is multiplied by 24 in this case and 1 is added
				 *  number returned in range 1->25 */
				return (randomNumberGenerator.nextFloat()*range + min);
				
			
			}
			else throw new IllegalArgumentException("max can't be smaller than min in positive values!");
		}
		
		//if max and min are negative, min is given to be larger in value than max in config file (because max is larger in magnitude)
		//take a case of min -1 and max -25
		else if (max < 0 && min < 0)
		{
			//compares to make sure min is larger than max
			if(max<min)
			{
				//in this case range is 24
				float range = min - max;
				
				//in this case number returned = (0-1)*-1*24 + -1 (in range -25 -> -1)
				return (randomNumberGenerator.nextFloat()*-1*range + min);
			}
			//suitable exception thrown
			else throw new IllegalArgumentException("max can't be bigger than min in negative values!");
		}
		else throw new IllegalArgumentException("cant have a negative max and positive min!");
	}
		
	//returns a random result for a probability parameter, where parameter would be a "6" in 1/6 probability
	public boolean probabilityApplianceStatus(int xInOneInX)
	{
		//returns if the next random int is zero in a generator which randomly returns ints from 0 to the parameter-1
		return randomNumberGenerator.nextInt(xInOneInX)==0;
	}
}
