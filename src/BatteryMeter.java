//defining batteryMeter, a special meter with a battery attached
public class BatteryMeter extends Meter{
	
	/* instance variables of a BatteryMeter object, store power drawn from battery, 
	 * drawn from mains and the connected battery */
	protected float drawnFromBattery;
	protected float drawnFromMains;
	protected Battery connectedBattery;
	
	/* constructor, takes superclass parameters and instantiates
	 * connectedBattery to one with maxCapacity in parameter */
	public BatteryMeter(String type, double unitCost, float maxCapacity)
	{
		super(type, unitCost);
		connectedBattery = new Battery(maxCapacity);
	}
	
	// method which stores parameter units in the battery
	public float storeUnitsAndGetExcess(float unitsStored)
	{
		return this.connectedBattery.storeUnitsAndReturnExcess(unitsStored);
	}

	/* overridden report method which calculates and reports cost, 
	 * amount drawn from mains and amount drawn from battery */
	public double report()
	{	
		// if amount consumed is negative amount is stored in battery, both drawn from mains and battery are set to zero
		if (drawnFromMains < 0)
		{
			this.storeUnitsAndGetExcess(drawnFromMains);
			drawnFromMains = 0;
			drawnFromBattery = 0;
		}
		// if amount consumed is zero or more and is less than or equal to battery charge, draw amount from battery
		else if (this.connectedBattery.getStoredCharge() >= drawnFromMains)
		{
			drawnFromBattery = drawnFromMains;
			this.connectedBattery.consumeUnits(drawnFromBattery);
			drawnFromMains = 0;
		}
		// if reading is greater than battery charge, stored charge is subtracted from reading, and the rest is drawn from mains
		else 
		{
			drawnFromBattery = connectedBattery.getStoredCharge();
			this.connectedBattery.consumeUnits(drawnFromBattery);
			drawnFromMains -= drawnFromBattery;
		}
		
		// printing out report and resetting reading for amounts drawn from mains and battery
		double costOfReading = drawnFromMains * this.getUnitCost();
		
		System.out.println("Reporting battery meter reading:");
		System.out.println("Name of utility: " + this.utilityName);
		System.out.println("Reading Cost: " + costOfReading);
		System.out.println("Drawn From Battery: " + drawnFromBattery);
		System.out.println("Drawn From Mains: " + drawnFromMains);
		
		drawnFromMains = 0;
		drawnFromBattery = 0;
		return costOfReading;
	}
	
	// overridden variant of consumeUnits method, consumes units and adds it to amount drawn from mains
	public void consumeUnits(float unitsConsumed)
	{
		drawnFromMains += unitsConsumed;
	}
}
