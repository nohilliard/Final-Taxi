package edu.trident.hilliard.finalassignment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Uses CabRecord interface to create a specific type of CabRecord.<br>
 * Formats output with an overriden toString.<br><br>
 * Variables:<br>
 * recType - type of record<br>
 * cabDate - date of the record<br>
 * cabId - name of cab<br>
 * inputValue - number of gallons<br>
 * gasCost - cost of each gallon<br>
 * @author Noah Hilliard
 *
 */
public class CabRecordManager implements CabRecord
{
	private RecordType recType;
	private Date cabDate;
	private String cabId;
	private double inputValue;
	private double gasCost;
	
	@Override
	public String toString()
	{
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		if(recType.toString() == "GAS") return String.format("%s,%s,%s,%.2f,%.2f,%.2f", cabId,format.format(cabDate),recType,inputValue,gasCost,inputValue*gasCost);
		
		return String.format("%s,%s,%s,%.2f",cabId,format.format(cabDate),recType,inputValue);
	}

	/**
	 * Constructor that sets default values.
	 */
	public CabRecordManager()
	{
		recType = null;
		cabDate = null;
		cabId = "";
		inputValue = 0.0;
		gasCost = 0.0;
	}
	
	@Override
	public RecordType getType()
	{
		return recType;
	}

	@Override
	public Date getDate()
	{
		return cabDate;
	}

	@Override
	public String getCabId()
	{
		return cabId;
	}

	@Override
	public double getValue()
	{
		return inputValue;
	}

	@Override
	public double getPerGallonCost()
	{
		return gasCost;
	}
	
	public double calculateGasTotal()
	{
		return inputValue * gasCost;
	}

	public void setRecType(RecordType recType)
	{
		this.recType = recType;
	}

	public void setCabDate(Date cabDate)
	{
		this.cabDate = cabDate;
	}

	public void setCabId(String cabId)
	{
		this.cabId = cabId;
	}

	public void setInputValue(double inputValue)
	{
		this.inputValue = inputValue;
	}

	public void setGasCost(double gasCost)
	{
		this.gasCost = gasCost;
	}
}