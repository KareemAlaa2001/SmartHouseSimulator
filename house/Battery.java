// defines battery class, batteries store charge generated and consume units accordingly in the next hour(s) 
public class Battery {
	
	// instance variables for maximum capacity and stored charge
	private float maxCapacity;
	private float storedCharge;
	
	// constructor, assigns maximum capacity, sets stored charge to 0 on creation 
	public Battery(float maxCapacity)
	{
		this.maxCapacity = maxCapacity;
		this.storedCharge = 0;
	}
	
	// takes an input of units and stores its modulus, so that if input is negative it increases storedCharge by its magnitude, returns excess charge
	public float storeUnitsAndReturnExcess(float unitsInput)
	{
		float excessCharge = 0;
		this.storedCharge += getModulus(unitsInput);
		
		if (this.storedCharge > maxCapacity)
		{
			excessCharge = this.storedCharge - maxCapacity;
			this.storedCharge = maxCapacity;
		}
		
		return excessCharge;
	}
	
	// getter for stored charge
	public float getStoredCharge()
	{
		return this.storedCharge;
	}
	
	// getter for maximum capacity
	public float getMaxCapacity()
	{
		return this.maxCapacity;
	}
	
	// consumes units from those stored in the battery, checks parameter first though
	public void consumeUnits(float unitsToConsume) throws IllegalArgumentException
	{
		// checks for negative parameter and returns suitable exception
		if (unitsToConsume < 0)
		{
			throw new IllegalArgumentException("Can't consume a negative amount!");
		}
		
		//checks for parameter too big and returns suitable exception
		else if (unitsToConsume > this.storedCharge)
		{
			throw new IllegalArgumentException("Can't consume more units than what's stored in the battery!");
		}
		
		//if parameter is within allowed range stored charge is reduced by parameter
		else
		{
			this.storedCharge -= unitsToConsume;
		}
	}
	
	// returns modulus of number, so positive number returns itself and for a negative numebr it returns its positive magnitude
	public float getModulus(float inputNumber)
	{
		float outputNumber = inputNumber;
		if (inputNumber <= 0)
		{
			outputNumber = inputNumber*-1;
		}
		return outputNumber;
	}
}
