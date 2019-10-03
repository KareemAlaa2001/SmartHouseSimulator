import java.util.ArrayList;

// defining House class, contains appliances and two meters 
public class House {

	// instance variables storing list of appliances and two meters
	private ArrayList<Appliance> applianceList = new ArrayList<Appliance>();
	private Meter elecMeter;  
	private Meter waterMeter;
	
	// constructor - no parameters
	public House()
	{
		// instantiates new meters for electricity and water meters
		this.elecMeter = new Meter("electric", 0.013);
		this.waterMeter = new Meter("water", 0.002);
	}
	
	// constructor - 2 meter parameters, assigns first meter to electricity and 2nd for water
	public House(Meter meter1, Meter meter2)
	{
		this.elecMeter = meter1;
		this.waterMeter = meter2;
	}
	
	//main method
	public static void main(String[] args) throws Exception 
	{
		//  creating a new house
		House testHouse = new House(new ExcessMeter("electric", 0.013,10,(float) 1.5,(float) 2.0), new Meter("water", 0.002));
		int numberOfDaysActive = 0;
		
		// looping over arguments to obtain name of file. This accounts for filenames with spaces.
		String configFileName = "";
		
		for(int i = 0; i< args.length - 2; i++)
		{
			configFileName += args[i];
			configFileName += " ";
		} 
		configFileName += args[args.length - 2];
		
		//Last argument used to define number of days active, try-catch block to anticipate exceptions thrown by any incorrect arguments
		try
		{
			numberOfDaysActive = Integer.parseInt(args[args.length - 1]);
		}
		catch (NumberFormatException e)
		{
			System.err.println("Must put an int as the final argument for program launch!");
		}
		
		//  creating a file handler object which takes file name, loops over contents and creates appliances appropriately
		FileHandler configFileHandler = new FileHandler(configFileName);	
		ArrayList<String> lineArrayList = configFileHandler.getArrayListOfLinesWithText();
		configFileHandler.addAppliancesFromTextArrayList(testHouse, lineArrayList);
			
		// running the sim for number of days specified by last argument in arguments list
		testHouse.activate(24*numberOfDaysActive);
	
	}

	
	// adds an appliance to the appliance list, assigns the electric meter to it
	public void addElectricAppliance(Appliance addedAppliance)
	{
		applianceList.add(addedAppliance);
		addedAppliance.assignMeter(elecMeter);
	}
	
	// adds an appliance to the appliance list, assigns the water meter to it
	public void addWaterAppliance(Appliance addedAppliance)
	{
		applianceList.add(addedAppliance);
		addedAppliance.assignMeter(waterMeter);
	}
	
	//removes appliance in parameter from appliance list
	public void removeAppliance(Appliance applianceToRemove)
	{
		applianceList.remove(applianceToRemove);
	}
	
	// returns number of appliances in house's appliance list
	public int numAppliances()
	{
		return applianceList.size();
	}
	
	//activates house for 1 hour. 
	public double activate()
	{
		//calls timePasses on all appliances in the appliance list
		// handles possible exception thrown if no meter is attached
		for(Appliance currentAppliance: applianceList)
		{
			try 
			{
				currentAppliance.timePasses();
			}
			
			catch (Exception e)
			{
				System.err.println(e);
			}
		}
		
		//returns sum of electric and water meter reports, used as total cost of hour
		double totalCostOfHour = 0;
		totalCostOfHour = elecMeter.report() + waterMeter.report();
		System.out.println();
		return totalCostOfHour;
	}
	
	/* activates the house for the specified number of hours, printing reports each hour and then
	 * printing and returning total cost of the simulation run by the calling of this method */
	public double activate(int numberOfHours)
	{
		double totalCostOfSim = 0;
		
		//activates house for numberOfHours times, adding the total cost of each hour to the total cost of the simulation
		for (int i = 0; i < numberOfHours; i++)
		{
			totalCostOfSim += this.activate();
		}
		
		// printing and returning total cost of sim run by this method
		System.out.println("The total cost of this simulation is: " + totalCostOfSim);
		return totalCostOfSim;
	}
	
}
