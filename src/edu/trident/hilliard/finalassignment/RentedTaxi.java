package edu.trident.hilliard.finalassignment;

/**
 * RentedTaxi extends {@link Taxi} with operating costs such as servicing
 * and gas.  Tracks miles driven since last service.<br><br>
 * Variables:<br>
 * cabName - name of cab<br>
 * serviceMileage - distance cab has travelled since last service<br>
 * operatingCost - service cost and gas cost total<br>
 * @author Noah Hilliard
 *
 */
public class RentedTaxi extends Taxi
{
	protected String cabName = "";
	protected double serviceMileage = 0.0;
	protected double operatingCost = 0.0;
	
	/**
	 * Constructor that accepts a gasAmount and limits it to the GAS_CAPACITY
	 * of the Taxi.
	 * @param gasAmount amount of gas added
	 */
	public RentedTaxi(double gasAmount)
	{
		super();
		if(gasAmount <= GAS_CAPACITY)
		{
			
			currentGas = gasAmount;
		}
		else
		{
			currentGas = GAS_CAPACITY;
		}
		
	}
	
	/**
	 * Constructor that creates a new cab with a full tank of gas
	 * @param cab name of the cab
	 */
	public RentedTaxi(String cab)
	{
		super();
		currentGas = GAS_CAPACITY;
		cabName = cab;
	}
	
	/**
	 * Provides the miles driven since the last time the taxi was serviced.
	 * @return serviceMileage miles driven since last service
	 */
	public double getServiceMileage()
	{
		return serviceMileage;
	}
	
	/**
	 * Provides the accrued operating cost.
	 * @return operatingCost
	 */
	public double getOperatingCost()
	{
		return operatingCost;
	}
	
	/**
	 * Services the cab, resets the serviceMileage to 0.0 and adds 12 to the operating cost.
	 * @param serviceCost charge for servicing the cab
	 */
	public void serviceTaxi(double serviceCost)
	{
		serviceMileage = 0.0;
		operatingCost += serviceCost;			
	}

	/**
	 * Adds the cost of gas added to operating costs, if the value entered exceeds the amount
	 * that can be put into the tank, the amount is reduced to only fill the tank.
	 * @param gallons number of gallons
	 * @param price cost of each gallon
	 */
	public void addGasCost(double gallons, double price)
	{
		if(currentGas + gallons > GAS_CAPACITY)
		{
			gallons = GAS_CAPACITY - currentGas;
		}
		operatingCost += (gallons * price);
	}
	
	/**
	 * Resets the taxi by calling {@link Taxi#resetTaxi() resetTaxi} from super and setting
	 * operatingCost to 0.0
	 */
	public void resetRentedTaxi()
	{
		operatingCost = 0.0;
		resetTaxi();
	}

	/**
	 * Checks for maintenance needed or admin override and updates 
	 * serviceMileage with tripMiles and calls {@link Taxi#addTrip(double)} from super.
	 * If serviceMileage is 500 or higher it will notify all listeners and set the
	 * maintenanceNeeded boolean to true.
	 * @param tripMiles distance of the entered fare
	 */
	public void addRentedTrip(double tripMiles)
	{
		if(milesAvailable() >= tripMiles) 
		{
			addTrip(tripMiles);
			serviceMileage += tripMiles;
		}
	}
}