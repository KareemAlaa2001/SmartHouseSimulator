import java.util.ArrayList;
import java.io.*;


/* this class handles the file taken as a parameter upon program startup,
 * used to add appliances in file to house
 */
public class FileHandler {

	// Instance variables for file name, array list of lines of text in file, and for buffered reader which reads through the file
	private String fileName;
	private ArrayList<String> fileLineArrayList;
	private BufferedReader fileReader;
	
	//constructor for class, takes file name as a parameter
	public FileHandler(String nameOfFile)
	{
		// assigning filename, and instantiating array list
		this.fileName = nameOfFile;
		fileLineArrayList = new ArrayList<String>();
		
		// checks if filename can be found, throws exception if not
		try 
		{
		fileReader = new BufferedReader(new FileReader(new File(this.fileName)));
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File not found!!");
		}
	}
	
	// Method which returns an arraylist of the lines containing text in the text file, ignoring those without text. Checks file format
	public ArrayList<String> getArrayListOfLinesWithText() throws Exception
	{
		String currentLine;
		while ((currentLine = fileReader.readLine()) != null)
		{
			if (!currentLine.equals(""))
			{
				fileLineArrayList.add(currentLine);
			}	
		}
		// checking file format by looping through arraylist lines
		for (int i = 0; i < fileLineArrayList.size(); i++)
		{
			boolean followsFormat = true;
			if (i % 8 == 0)
			{
				if (!fileLineArrayList.get(i).split(":")[0].equals("name"))
				{
					followsFormat = false;
				}
			}
			if (i % 8 == 1)
			{
				if (!fileLineArrayList.get(i).split(":")[0].equals("subclass"))
				{
					followsFormat = false;
				}
			}
			if (i % 8 == 2)
			{
				if (!fileLineArrayList.get(i).split(":")[0].equals("meter"))
				{
					followsFormat = false;
				}
			}
			if (i % 8 == 3)
			{
				if (!fileLineArrayList.get(i).split(":")[0].equals("Min units consumed"))
				{
					followsFormat = false;
				}
			}
			if (i % 8 == 4)
			{
				if (!fileLineArrayList.get(i).split(":")[0].equals("Max units consumed"))
				{
					followsFormat = false;
				}
			}
			if (i % 8 == 5)
			{
				if (!fileLineArrayList.get(i).split(":")[0].equals("Fixed units consumed"))
				{
					followsFormat = false;
				}
			}
			if (i % 8 == 6)
			{
				if (!fileLineArrayList.get(i).split(":")[0].equals("Probability switched on"))
				{
					followsFormat = false;
				}
			}
			if (i % 8 == 7)
			{
				if (!fileLineArrayList.get(i).split(":")[0].equals("Cycle length"))
				{
					followsFormat = false;
				}
			}
			if (followsFormat == false)
			{
				throw new Exception("The entered configuration file doesn't follow the required format!");
			}
		}
		return fileLineArrayList;
	}
	
	// uses an arraylist with appliance properties taken from text file as a parameter to add appliances to the house 
	public void addAppliancesFromTextArrayList(House testHouse, ArrayList<String> lineArrayList) throws IllegalArgumentException
	{
		// looping through arraylist of text
		for (int i = 0; i < lineArrayList.size(); i++)
		{
			// each appliance had 8 lines of text defining it, so 8 items in the arraylist. they maintained the same order throughout the config file
			if (i % 8 == 0)
			{
				// Declaring and assigning characteristics of all appliances: Name, Subclass and assigned meter
				String currApplianceName = lineArrayList.get((i)).split(": ")[1];
				String currApplianceType = lineArrayList.get(i+1).split(": ")[1];
				String currApplianceMeter = lineArrayList.get(i+2).split(": ")[1];
				// Checking subclass
				if (currApplianceType.equals("CyclicFixed"))
				{
					// Finding out consumption rate, cycle length and meter assigned to be able to add the correct appliances to the house
					float currApplianceFixedRate = (float) Float.parseFloat(lineArrayList.get(i+5).split(": ")[1]);
					int currApplianceCycleLength = Integer.parseInt(lineArrayList.get(i+7).split(": ")[1].split("/")[0]);
					
					if(currApplianceMeter.equals("electric") || currApplianceMeter.equals("electricity"))
					{
						testHouse.addElectricAppliance(new CyclicFixed(currApplianceName, currApplianceMeter, currApplianceFixedRate, currApplianceCycleLength));
					}
					
					else if(currApplianceMeter.equals("water"))
					{
						testHouse.addWaterAppliance(new CyclicFixed(currApplianceName, currApplianceMeter, currApplianceFixedRate, currApplianceCycleLength));
					}
					else throw new IllegalArgumentException("Invalid utility consumed by appliance!");
				}
				
				// Finding out relevant information for CyclicVaries type appliances in the list and creating them accordingly
				else if (currApplianceType.equals("CyclicVaries"))
				{
					float currApplianceMinRate = (float) Float.parseFloat(lineArrayList.get(i+3).split(": ")[1]);
					float currApplianceMaxRate = (float) Float.parseFloat(lineArrayList.get(i+4).split(": ")[1]);
					int currApplianceCycleLength = Integer.parseInt(lineArrayList.get(i+7).split(": ")[1].split("/")[0]);
					
					if(currApplianceMeter.equals("electric") || currApplianceMeter.equals("electricity"))
					{
						testHouse.addElectricAppliance(new CyclicVaries(currApplianceName, currApplianceMeter, currApplianceCycleLength, currApplianceMinRate, currApplianceMaxRate));
					}
					
					else if(currApplianceMeter.equals("water"))
					{
						testHouse.addWaterAppliance(new CyclicVaries(currApplianceName, currApplianceMeter, currApplianceCycleLength, currApplianceMinRate, currApplianceMaxRate));
					}
					
					else throw new IllegalArgumentException("Invalid utility consumed by appliance!");
				}
				
				// Finding out relevant information for RandomFixed type appliances in the list and creating them accordingly
				else if (currApplianceType.equals("RandomFixed"))
				{
					float currApplianceFixedRate = (float) Float.parseFloat(lineArrayList.get(i+5).split(": ")[1]);
					int currApplianceOnProbability = Integer.parseInt(lineArrayList.get(i+6).split(": ")[1].split(" ")[2]);
					
					if(currApplianceMeter.equals("electric") || currApplianceMeter.equals("electricity"))
					{
						testHouse.addElectricAppliance(new RandomFixed(currApplianceName, currApplianceMeter, currApplianceOnProbability, currApplianceFixedRate));
					}
					
					else if(currApplianceMeter.equals("water"))
					{
						testHouse.addWaterAppliance(new RandomFixed(currApplianceName, currApplianceMeter, currApplianceOnProbability, currApplianceFixedRate));
					}
					
					else throw new IllegalArgumentException("Invalid utility consumed by appliance!");
				} 
				
				// Finding out relevant information for RandomVaries type appliances in the list and creating them accordingly
				else if (currApplianceType.equals("RandomVaries"))
				{
					float currApplianceMinRate = (float) Float.parseFloat(lineArrayList.get(i+3).split(": ")[1]);
					float currApplianceMaxRate = (float) Float.parseFloat(lineArrayList.get(i+4).split(": ")[1]);
					int currApplianceOnProbability = Integer.parseInt(lineArrayList.get(i+6).split(": ")[1].split(" ")[2]);
					
					if(currApplianceMeter.equals("electric") || currApplianceMeter.equals("electricity"))
					{
						testHouse.addElectricAppliance(new RandomVaries(currApplianceName, currApplianceMeter, currApplianceOnProbability, currApplianceMinRate, currApplianceMaxRate));
					}
					
					else if(currApplianceMeter.equals("water"))
					{
						testHouse.addWaterAppliance(new RandomVaries(currApplianceName, currApplianceMeter, currApplianceOnProbability, currApplianceMinRate, currApplianceMaxRate));
					}
					
					else throw new IllegalArgumentException("Invalid utility consumed by appliance!");
				} 
				
				else throw new IllegalArgumentException("Invalid subclass entered!");
			}
		}
	}
	
}
