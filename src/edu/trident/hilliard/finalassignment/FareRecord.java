package edu.trident.hilliard.finalassignment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Object for handling CabRecords of the Fare type<br><br>
 * Variables:<br>
 * fareMiles - number of miles<br>
 * RIDE_RATE - constant value for initial ride charge<br>
 * RIDE_MILEAGE_RATE - constant value for per mile ride charge<br>
 * @author Noah Hilliard
 *
 */
public class FareRecord extends GenericCabRecord
{
	private double fareMiles;
	final double RIDE_RATE = 2.0;
	final double RIDE_MILEAGE_RATE = 0.585;
	
	/**
	 * Constructor that creates FareRecord based on the value and date of
	 * the passed CabRecord
	 * @param record CabRecord that represents a fare
	 */
	public FareRecord(CabRecord record)
	{
		fareMiles = record.getValue();
		setDate(record.getDate());
	}
	
	/**
	 * Constructor that creates FareRecord based on the value and date passed
	 * @param recordDate date of fare
	 * @param recordInput distance of fare
	 */
	public FareRecord(Date recordDate, double recordInput)
	{
		fareMiles = recordInput;
		setDate(recordDate);
	}

	/**
	 * Returns mileage of fare
	 * @return number of miles on the fare
	 */
	public double getFareMiles()
	{
		return fareMiles;
	}
	
	/**
	 * Calculates and returns the cost of the FareRecord
	 * @return cost of the fare
	 */
	public double calculateFareCost()
	{
		return RIDE_RATE + (RIDE_MILEAGE_RATE * fareMiles);
	}
	
	@Override
	public String toString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		return "FARE: " + formatter.format(getDate()) + "," + fareMiles;
	}
}