import java.util.Random;

/*  excess meter class handles excess electricity from that in the battery by selling it to electricity company.
    It also generates a variable pricing scenario where the unitCost randomly fluctuates in a range defined by 
    a factor taken as a constructor parameter. The feed in tariff value per unit switches between three values
    depending on time of day */
public class ExcessMeter extends BatteryMeter
{
	//  instance variables needed to calculate feed in tariff, unitCost, also to store excess units and time of day
	private double tariffFactor;
	private double costFactor;
	private float excessUnits;
	private int timeOfDay;
	Random randomGenerator = new Random();

	//  excess meter constructor. Takes superclass parameters and parameters for tariffFactor and cost factor. 
	//  cost factor must be bigger than tariff factor
	public ExcessMeter(String type, double baseUnitCost, float maxCapacity, float tariffFactor, float costFactor)
	{
		super(type, baseUnitCost, maxCapacity);
		
		this.timeOfDay = 0;
		
		if (costFactor > 1)
		{
			this.costFactor = costFactor;
		}
		else throw new IllegalArgumentException("Cost Factor must be larger than 1!");
		
		if (tariffFactor < costFactor && tariffFactor > 1)
		{
			this.tariffFactor = tariffFactor;
		}
		else throw new IllegalArgumentException("tariff factor must be between 1 and cost factor!");
	}
	
	//  calculates unit cost for this hour by getting a random float in a range. 
	//  Multiplies unitCost by costFactor for maximum cost. Divides by cost factor for min cost. Range is max - min
	private double getUnitCostThisHour()
	{
		double min, max, range;
		
		min = this.getUnitCost()/costFactor;
		max = this.getUnitCost()*costFactor;
		range = max - min;
		
		return randomGenerator.nextFloat()*range + min;
	}
	
	/*  feedInTariff calculated by this method. It can be one of 3 values, depending on time of day. 
	 *  middle value is base unitCost. High value is unitCost multiplied by tariff factor, and
	 *  low value is unitCost divided by tariff factor. Increments time of day with each call and resets it when it reaches 24 */
	private double getFeedInTariffThisHour()
	{
		double feedInTariff = 0;
		timeOfDay++;

		//  low price times
		if ((timeOfDay >= 1 && timeOfDay <= 6) || timeOfDay == 23 || timeOfDay == 24)
		{
			feedInTariff = this.getUnitCost()/tariffFactor;
		}
		
		//  medium price
		else if((timeOfDay >= 7 && timeOfDay <= 10) || (timeOfDay >= 19 && timeOfDay <= 22))
		{
			feedInTariff =  this.getUnitCost();
		}
		
		//  high price
		else if(timeOfDay >= 11 && timeOfDay <= 18)
		{
			feedInTariff = this.getUnitCost() * tariffFactor;
		}
		
		//  resetting time
		if (timeOfDay == 24)
		{
			timeOfDay = 0;
		}
		
		//  making sure time keeping works
		if (timeOfDay > 24 || timeOfDay < 0)
		{
			throw new IllegalArgumentException("time of day must not be negative and can't be greater than 24!!");
		}
		
		return feedInTariff;
	}

	// overridden report method which calculates and reports cost, amount drawn from mains and amount drawn from battery 
	public double report()
	{	
		//  calculating feed in tariff and unit cost for current hour
		double costOfReading = 0;
		double feedInTariffThisHour = getFeedInTariffThisHour();
		double unitCostThisHour = getUnitCostThisHour();
		
		//  if amount consumed is negative amount is stored in battery, both drawn from mains and battery are set to zero
		//  if excess units are produced from unit storage, they are assigned to excessUnits variable
		if (drawnFromMains < 0)
		{
			excessUnits = this.storeUnitsAndGetExcess(drawnFromMains);
			drawnFromMains = 0;
			drawnFromBattery = 0;	
		}
		
		//  if amount consumed is non negative
		else 
		{
			/*  if amount consumed is less than or equal to battery charge, compare feed in tariff to unitcost.
			  if feed in tariff is more, sell the equivalent of the amount consumed from mains from the battery 
			  to the electric company, and power the house with mains electricity. Thus more money is made from 
			  selling than using battery. If unit cost is more, save more money by powering appliances with battery */
			if (this.connectedBattery.getStoredCharge() >= drawnFromMains)
			{
				if (feedInTariffThisHour > unitCostThisHour)
				{
					drawnFromBattery = drawnFromMains;
					excessUnits = drawnFromBattery;
					this.connectedBattery.consumeUnits(drawnFromBattery);
				}
				
				else 
				{
					drawnFromBattery = drawnFromMains;
					this.connectedBattery.consumeUnits(drawnFromBattery);
					drawnFromMains = 0;
					excessUnits = 0;
				}
			}
			// if reading is greater than battery charge
			else 
			{
				/*  if amount consumed is more than battery charge, compare feed in tariff to unitcost.
				  if feed in tariff is more, sell the battery's stored charge, and power the house with mains electricity. 
				  If unit cost is more, save more money by partially powering appliances with battery */
				if (feedInTariffThisHour > unitCostThisHour)
				{
					drawnFromBattery = connectedBattery.getStoredCharge();
					this.connectedBattery.consumeUnits(drawnFromBattery);
					excessUnits = drawnFromBattery;
				}
				
				else 
				{
					drawnFromBattery = connectedBattery.getStoredCharge();
					this.connectedBattery.consumeUnits(drawnFromBattery);
					drawnFromMains -= drawnFromBattery;
					excessUnits = 0;
				}
			}
		}
		
		// calculating costs from main consumption, money made from feed in tariffs and net cost
		double paidOut = drawnFromMains * unitCostThisHour;
		double paidIn = excessUnits * feedInTariffThisHour;
		costOfReading += paidOut;
		costOfReading -= paidIn;
		
		// printing out report and resetting reading for amounts drawn from mains, drawn from battery and excess units
		System.out.println("Reporting readings:");
		System.out.println("Name of utility: " + this.utilityName);
		System.out.println("Drawn From Battery: " + drawnFromBattery);
		System.out.println("Drawn From Mains: " + drawnFromMains);
		System.out.println("Money paid out to electric company this hour: " + paidOut);
		System.out.println("Money received from electric company this hour: " + paidIn);
		System.out.println("Net Reading Cost: " + costOfReading);
		
		drawnFromMains = 0;
		drawnFromBattery = 0;
		excessUnits = 0;
		return costOfReading;
	}
}
