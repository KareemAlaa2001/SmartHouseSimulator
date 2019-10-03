//defining meter class
public class Meter {

	// instance variables for name of utility, unitCost, meterReading and cost of reading
	protected String utilityName;
	private double unitCost;
	protected float meterReading;
	private double readingCost;

	// constructor for Meter, takes type and unitCost as parameters, sets meterReading to 0
	public Meter(String type,double cost)
	{
		this.utilityName = type;
		this.unitCost = cost;
		this.meterReading = 0;
	}
	
	//consumes units by adding them to meterReading
	public void consumeUnits(float unitsConsumed)
	{
		meterReading+= unitsConsumed;
	}

	//returns meterReading (getter)
	public float getMeterReading()
	{
		return meterReading;
	}
	
	//  unitCost getter
	public double getUnitCost()
	{
		return unitCost;
	}
	
	// setter for meterReading
	public void setMeterReading(float newReading)
	{
		meterReading = newReading;
	}
	
	public String getUtilityName()
	{
		return this.utilityName;
	}
	
	/* outputs a report of information in meter. first calculates readingCost by multiplying
	 * meterReading and unitCost. if readingCost is negative its value is given as zero.
	 *  Then prints a series of statements which state the utility's 
	 * name, current meterReading and readingCost. resets meterReading to zero. */
	public double report()
	{
		if (meterReading < 0)
		{
			meterReading = 0;
		}
		
		readingCost = meterReading*unitCost;
		
		System.out.println("Reporting meter reading:");
		System.out.println("Name of utility: " + utilityName);
		System.out.println("Units consumed: " + meterReading);
		System.out.println("Reading Cost: " + readingCost);
		
		meterReading = 0;
		
		return readingCost;
		
	}
	
	
}
