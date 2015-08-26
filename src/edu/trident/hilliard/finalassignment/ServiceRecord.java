package edu.trident.hilliard.finalassignment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Object for handling CabRecords of the Service type<br><br>
 * Variables:<br>
 * serviceCost - cost of the service<br>
 * @author Noah Hilliard
 *
 */
public class ServiceRecord extends GenericCabRecord
{
	private double serviceCost;
	
	/**
	 * Constructor that creates ServiceRecord based on the value and date of
	 * the passed CabRecord
	 * @param record CabRecord that represents a servicing
	 */
	public ServiceRecord(CabRecord record)
	{
		serviceCost = record.getValue();
		setDate(record.getDate());
	}
	
	/**
	 * Constructor that creates ServiceRecord based on passed value and date
	 * @param cabDate date of service
	 * @param cost cost of service
	 */
	public ServiceRecord(Date cabDate, double cost)
	{
		serviceCost = cost;
		setDate(cabDate);
	}
	
	/**
	 * Returns cost of service
	 * @return cost of service
	 */
	public double getServiceCost()
	{
		return serviceCost;
	}
	
	@Override
	public String toString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		return "SERVICE: " + formatter.format(getDate()) + "," + serviceCost;
	}
}