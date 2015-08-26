package edu.trident.hilliard.finalassignment;

import java.util.Date;

/**
 * Generic record for cab data<br><br>
 * Variables:<br>
 * cabDate - record date<br>
 * @author Noah Hilliard
 *
 */
public class GenericCabRecord
{
	private Date cabDate;
	
	/**
	 * Constructor that sets default cabDate to null
	 */
	public GenericCabRecord()
	{
		cabDate = null;
	}

	/**
	 * Returns date of the record
	 * @return date of the record
	 */
	public Date getDate()
	{
		return cabDate;
	}
	
	/**
	 * Sets date of the record
	 * @param recordDate sets the date of the record
	 */
	public void setDate(Date recordDate)
	{
		cabDate = recordDate;
	}
}