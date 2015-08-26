package edu.trident.hilliard.finalassignment;

/**
 * Taxi models a taxi with a set rate and mileage rate, gas mileage, and gas capacity
 * as well as currentGas, currentMileage, and totalFare to track information related
 * to the specific taxi.<br><br>
 * Variables:<br>
 * RIDE_RATE - constant value for initial ride charge<br>
 * RIDE_MILEAGE_RATE - constant value for per mile ride charge<br>
 * GAS_MILEAGE - constant value for gas mileage of cab<br>
 * GAS_CAPACITY - constant value for capacity of the cab's gas tank<br>
 * currentGas - amount of gas in the cab<br>
 * currentMileage - distance the cab has traveled<br>
 * totalFare - accumulated total of fares<br>
 * 
 * @author Noah Hilliard
 *
 */
public class Taxi 
{

	protected final double RIDE_RATE;
	protected final double RIDE_MILEAGE_RATE;
	protected final double GAS_MILEAGE;
	protected static final double GAS_CAPACITY = 22.9;
	protected double currentGas;
	protected double currentMileage;
	protected double totalFare;
	
	/**
	 * Constructor that creates a Taxi with base values of<br>
	 * RIDE_RATE = 2.0;<br>
	 * RIDE_MILEAGE_RATE = 0.585;<br>
	 * GAS_MILEAGE = 17.8;<br>
	 * currentGas = 22.9;<br>
	 */
	public Taxi() 
	{
		RIDE_RATE = 2.0;
		RIDE_MILEAGE_RATE = 0.585;
		GAS_MILEAGE = 17.8;
		currentGas = 22.9;
		currentMileage = 0.0;
		totalFare = 0.0;
	}

	/**
	 * Returns the remaining gallons of gas in the Taxi.
	 * @return currentGas
	 */
	public double getCurrentGas() 
	{
		return currentGas;
	}

	/**
	 * Returns the miles driven in the Taxi.
	 * @return currentMileage
	 */
	public double getCurrentMileage() 
	{
		return currentMileage;
	}
	
	/**
	 * Returns the fares collected in the Taxi.
	 * @return totalFare
	 */
	public double getTotalFare() 
	{
		return totalFare;
	}
	
	/**
	 * Adds gas to the Taxi, limited to GAS_CAPACITY.
	 * @param addGas - based on user input
	 */
	public void addGas(double addGas) 
	{
		if(currentGas + addGas > GAS_CAPACITY)
		{
			currentGas = GAS_CAPACITY;
		}
		else
		{
			currentGas += addGas;
		}
	}
	
	/**
	 * Called by {@link #addTrip(double) addTrip} to calculate the gas a trip would use by 
	 * dividing tripMiles by GAS_MILEAGE and subtracts that amount of gas from currentGas.
	 * @param tripMiles - received from {@link #addTrip(double) addTrip}
	 */
	public void useGas(double tripMiles)
	{
		double tripGas = 0.0;
		
		tripGas = tripMiles / GAS_MILEAGE;
		
		currentGas -= tripGas;
	}
	
	/**
	 * Called by {@link #addTrip(double) addTrip} to add mileage to the taxi based on the 
	 * value passed to it.
	 * @param addMileage - received from {@link #addTrip(double) addTrip}
	 */
	public void addMileage(double addMileage) 
	{
		currentMileage += addMileage;
	}

	/**
	 * Called by {@link #addTrip(double) addTrip} to add current fare to total fare.
	 * @param fareCost cost of the fare
	 */
	public void addFare(double fareCost) 
	{
		totalFare += fareCost;
	}
	
	/**
	 * Calculates the distance the taxi can travel by multiplying
	 * currentGas by GAS_MILEAGE.
	 * @return mileageRemaining
	 */
	public double milesAvailable()
	{
		double mileageRemaining = 0.0;
		
		mileageRemaining = currentGas * GAS_MILEAGE;
		
		return mileageRemaining;
	}
	
	/**
	 * Called by {@link #addTrip(double) addTrip} to calculate the fare for the current trip based on tripMiles
	 * @param tripMiles - based on user input received by {@link #addTrip(double) addTrip}
	 * @return currentFare
	 */
	public double calculateFare(double tripMiles)
	{
		double currentFare = 0.0;
		
		if(milesAvailable() >= tripMiles)
		{
			currentFare = RIDE_RATE + (RIDE_MILEAGE_RATE * tripMiles);
		}
		
		return currentFare;
		
	}
	
	/**
	 * Calls {@link #milesAvailable() milesAvailable} to see if the Taxi has enough gas to complete 
	 * the trip and calls {@link #calculateFare(double) calculateFare}, {@link #addMileage(double) addMileage}, 
	 * {@link #useGas(double) useGas}, and {@link #addFare(double) addFare} with the mileage of the 
	 * trip if it can. <br>
	 * Sets currentFare to 0.0 if there is not enough gas to complete the trip.
	 * @param tripMiles distance of the entered fare
	 */
	public void addTrip(double tripMiles)
	{
		double currentFare = 0.0;
		if(milesAvailable() >= tripMiles) 
		{
			currentFare = calculateFare(tripMiles);
			addMileage(tripMiles);
			useGas(tripMiles);
			addFare(currentFare);
		}
	}
	
	/**
	 * Resets the mileage on the Taxi and the fares collected.
	 */
	public void resetTaxi()
	{
		currentMileage = 0.0;
		totalFare = 0.0;
	}
}