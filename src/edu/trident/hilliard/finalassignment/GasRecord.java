package edu.trident.hilliard.finalassignment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Object for handling CabRecords of the Gas type<br><br>
 * Variables:<br>
 * gasGallons - number of gallons<br>
 * gasCost - cost of each gallon<br>
 * @author Noah Hilliard
 *
 */
public class GasRecord extends GenericCabRecord
{
	private double gasGallons;
	private double gasCost;
	
	/**
	 * Constructor that creates GasRecord based on the pergallon cost, value, and
	 * date of the passed CabRecord
	 * @param record CabRecord that represents a GasRecord
	 */
	public GasRecord(CabRecord record)
	{
		gasGallons = record.getValue();
		gasCost = record.getPerGallonCost();
		setDate(record.getDate());
	}
	
	/**
	 * Constructor that creates GasRecord based on passed date, gallons, and cost
	 * @param cabDate date of gas record
	 * @param gallons gallons of gas
	 * @param cost cost of gallon
	 */
	public GasRecord(Date cabDate, double gallons, double cost)
	{
		gasGallons = gallons;
		gasCost = cost;
		setDate(cabDate);
	}
	
	/**
	 * Returns gallons of gas
	 * @return gallons of gas
	 */
	public double getGasGallons()
	{
		return gasGallons;
	}
	
	/**
	 * Returns cost of each gallon of gas
	 * @return cost of one gallon of gas
	 */
	public double getGasCost()
	{
		return gasCost;
	}
	
	/**
	 * Calculates and returns the cost of gas
	 * @return total gas cost
	 */
	public double calculateGasTotal()
	{
		return gasGallons * gasCost;
	}

	@Override
	public String toString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		return "GAS: " + formatter.format(getDate()) + "," + gasGallons + "," + gasCost;
	}
}