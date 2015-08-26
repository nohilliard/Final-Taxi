package edu.trident.hilliard.finalassignment;

import java.util.Date;

/**
 * Interface used by {@link CabRecordManager} to represent a single cab entry
 * @author Noah Hilliard
 *
 */
public interface CabRecord
{
	public enum RecordType
	{
		GAS,
		FARE,
		SERVICE
	}
	
	public RecordType getType();
	public Date getDate();
	public String getCabId();
	public double getValue();
	public double getPerGallonCost();
}