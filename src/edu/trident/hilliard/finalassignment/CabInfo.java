package edu.trident.hilliard.finalassignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Object responsible for managing records for each individual cab.<br><br>
 * Variables:<br>
 * cabName - name of cab<br>
 * @author Noah Hilliard
 *
 */
public class CabInfo
{
	private String cabName;
	private ArrayList<FareRecord> fareList = new ArrayList<FareRecord>();
	private ArrayList<ServiceRecord> serviceList = new ArrayList<ServiceRecord>();
	private ArrayList<GasRecord> gasList = new ArrayList<GasRecord>();
	private ArrayList<Long> serviceDayList = new ArrayList<Long>();
	
	/**
	 * Constructor that accepts a CabRecord and set cabName to the cabId of that record.
	 * @param record CabRecord that identifies the new cab
	 */
	public CabInfo(CabRecord record)
	{
		cabName = record.getCabId();
	}
	
	/**
	 * Constructor that accepts a cab name
	 * @param name name that identifies the new cab
	 */
	public CabInfo(String name)
	{
		cabName = name;
	}

	/**
	 * Returns cabName as a String
	 * @return cabName
	 */
	public String getCabName()
	{
		return cabName;
	}
	
	/**
	 * Returns number of services performed
	 * @return size of the service record ArrayList
	 */
	public int getServiceCount()
	{
		return serviceList.size();
	}
	
	/**
	 * Returns the number of gas records
	 * @return size of the gas record ArrayList
	 */
	public int getGasCount()
	{
		return gasList.size();
	}
	
	/**
	 * Returns the number of fare records
	 * @return size of the fare record ArrayList
	 */
	public int getFareCount()
	{
		return fareList.size();
	}
	
	/**
	 * Create a FareRecord based on the passed CabRecord and add it to {@link fareList}
	 * @param record CabRecord to be added to {@link fareList}
	 */
	public void addFareRecord(CabRecord record)
	{
		FareRecord fRecord = new FareRecord(record);
		
		fareList.add(fRecord);
	}
	
	/**
	 * Add an existing FareRecord to {@link fareList}
	 * @param record FareRecord to be added to {@link fareList}
	 */
	public void addFareRecord(FareRecord record)
	{
		fareList.add(record);
	}
	
	/**
	 * Remove a FareRecord from {@link fareList}
	 * @param recNum index to be removed from {@link fareList}
	 */
	public void removeFareRecord(int recNum)
	{
		fareList.remove(recNum);
	}
	
	/**
	 * Create a GasRecord based on the passed CabRecord and add it to {@link gasList}
	 * @param record CabRecord to be added to {@link gasList}
	 */
	public void addGasRecord(CabRecord record)
	{
		GasRecord gRecord = new GasRecord(record);
		
		gasList.add(gRecord);
	}
	
	/**
	 * Add an existing GasRecord to {@link gasList}
	 * @param record GasRecord to be added to {@link gasList}
	 */
	public void addGasRecord(GasRecord record)
	{
		gasList.add(record);
	}
	
	/**
	 * Remove a GasRecord from {@link gasList}
	 * @param recNum index to be removed from {@link gasList}
	 */
	public void removeGasRecord(int recNum)
	{
		gasList.remove(recNum);
	}
	
	/**
	 * Create a ServiceRecord based on the passed CabRecord and add it to {@link serviceList}
	 * @param record CabRecord to be added to {@link serviceList}
	 */
	public void addServiceRecord(CabRecord record)
	{
		ServiceRecord sRecord = new ServiceRecord(record);
		
		serviceList.add(sRecord);
	}
	
	/**
	 * Add an existing ServiceRecord to {@link serviceList}
	 * @param record ServiceRecord to be added to {@link serviceList}
	 */
	public void addServiceRecord(ServiceRecord record)
	{
		serviceList.add(record);
	}
	
	/**
	 * Remove a ServiceRecord from {@link serviceList}
	 * @param recNum index to be removed from {@link serviceList}
	 */
	public void removeServiceRecord(int recNum)
	{
		serviceList.remove(recNum);
	}
	
	/**
	 * Calculate and return net earnings based on values retrieved from {@link CabInfo#getGrossEarnings() getGrossEarnings}, 
	 * {@link CabInfo#getTotalGasCost() getTotalGasCost}, and {@link CabInfo#getTotalServiceCost() getTotalServiceCost}
	 * @return the net earnings of the cab
	 */
	public double getNetEarnings()
	{
		double earnings = getGrossEarnings();
		double costs = getTotalGasCost() + getTotalServiceCost();
		double net = earnings - costs;
		
		return net;
	}
	
	/**
	 * Calculate total service cost by adding values of every record in the Service ArrayList
	 * @return the total service cost for the cab
	 */
	public double getTotalServiceCost()
	{
		double serviceTotal = 0.0;

		for(ServiceRecord records : serviceList)
		{
			serviceTotal += records.getServiceCost();
		}
		
		return serviceTotal;
	}
	
	/**
	 * Call {@link GasRecord#calculateGasTotal() calculateGasTotal} and return the sum of accumulated values
	 * @return the total gas cost for the cab
	 */
	public double getTotalGasCost()
	{
		double gasTotal = 0.0;
		
		for(GasRecord records : gasList)
		{
			gasTotal += records.calculateGasTotal();
		}
		
		return gasTotal;
	}
	
	/**
	 * Calculate gross earnings by accumulating {@link FareRecord#calculateFareCost() calculateFareCost} 
	 * values for each FareRecord
	 * @return the gross earnings for the cab
	 */
	public double getGrossEarnings()
	{
		double earnings = 0.0;
		
		for(FareRecord records : fareList)
		{
			earnings += records.calculateFareCost();
		}
		
		return earnings;
	}
	
	/**
	 * Returns the highest value in the sorted ArrayList, serviceDayList
	 * @return the highest number of days the cab went without service
	 */
	public long getMaxDaysBetweenService()
	{
		populateServiceDayList();
		long maxServiceDay = 0;

		for(long serviceDays : serviceDayList)
		{
			if(serviceDays > maxServiceDay)
			{
				maxServiceDay = serviceDays;
			}
		}
		
		return maxServiceDay;
	}
	
	/**
	 * Calls {@link CabInfo#populateServiceDayList() populateServiceDayList} to calculate the average number 
	 * of days between services.
	 * @return the average number of days the cab went without service
	 */
	public double getAvgServiceDays()
	{
		double cabDayTotal = 0;
		
		cabDayTotal = countDays(getFirstDate(),getLastDate());
		
		if(serviceList.size() > 0)
		{
			return cabDayTotal/(serviceList.size()+1);
		}
		
		return 0;
	}
	
	/**
	 * Iterates through the Service ArrayList and counts the number of days between services.
	 * If the size of the ArrayList is 0, calls {@link CabInfo#getFirstDate() getFirstDate} as the 
	 * first date.  If the iteration is equal to the size of the ArrayList, calls {@link CabInfo#getLastDate() getLastDate}
	 * as the second date. Uses the last service record compared to the current service record in other comparisons.
	 */
	public void populateServiceDayList()
	{
		Date date1 = null;
		Date date2 = null;
		long serviceDays = 0;
		
		serviceDayList.clear();
		
		if(serviceList.size() > 0)
		{
			for(int i = 0; i <= serviceList.size(); i++)
			{
				if(i==0)
				{
					date1 = getFirstDate();
					date2 = serviceList.get(i).getDate();
				}
				else if(i==serviceList.size())
				{
					date1 = serviceList.get(i-1).getDate();
					date2 = getLastDate();
				}
				else
				{
					date1 = serviceList.get(i-1).getDate();
					date2 = serviceList.get(i).getDate();
				}
				
				serviceDays = countDays(date1,date2);
				
				serviceDayList.add(serviceDays);
			}
		}
	}
	
	/**
	 * Calculates totalMiles by iterating through Fare ArrayList adding fareMiles together.
	 * @return the total miles on the cab
	 */
	public double getTotalMiles()
	{
		double totalMiles = 0.0;
		
		for(FareRecord record : fareList)
		{
			totalMiles += record.getFareMiles();
		}
		
		return totalMiles;
	}
	
	/**
	 * Returns the size of the Service ArrayList for use by other methods
	 * @return the number of times the cab was serviced
	 */
	public int getTotalServices()
	{
		return serviceList.size();
	}
	
	/**
	 * Sorts the Fare, Gas, and Service ArrayLists
	 */
	public void sortArrays()
	{
		Collections.sort(fareList, new Comparator<FareRecord>(){
   		    public int compare(FareRecord first, FareRecord second) {
                return first.getDate().compareTo(second.getDate());
   		    }
		});
		
		Collections.sort(serviceList, new Comparator<ServiceRecord>(){
   		    public int compare(ServiceRecord first, ServiceRecord second) {
                return first.getDate().compareTo(second.getDate());
   		    }
		});
		
		Collections.sort(gasList, new Comparator<GasRecord>(){
   		    public int compare(GasRecord first, GasRecord second) {
                return first.getDate().compareTo(second.getDate());
   		    }
		});
	}
	
	/**
	 * Calculates firstDate by comparing the Date of the first record in the
	 * Fare, Service, and Gas ArrayLists
	 * @return the first date of the cab
	 */
	public Date getFirstDate()
	{
		Date firstDate = null;
		boolean fareDate = false;
		boolean serviceDate = false;
		
		if(fareList.size() > 0)
		{
			firstDate = fareList.get(0).getDate();
			fareDate = true;
		}
		else if(serviceList.size() > 0)
		{
			firstDate = serviceList.get(0).getDate();
			serviceDate = true;
		}
		else if(gasList.size() > 0)
		{
			firstDate = gasList.get(0).getDate();
		}
		
		if(serviceList.size() > 0 && fareDate)
		{
			if(firstDate.compareTo(serviceList.get(0).getDate()) > 0)
			{
				firstDate = serviceList.get(0).getDate();
			}
		}
		
		if(gasList.size() > 0 && (fareDate || serviceDate))
		{
			if(firstDate.compareTo(gasList.get(0).getDate()) > 0)
			{
				firstDate = gasList.get(0).getDate();
			}
		}
		
		return firstDate;
	}
	
	/**
	 * Calculates lastDate by comparing the Date of the last record in the
	 * Fare, Service, and Gas ArrayLists
	 * @return the last date of the cab
	 */
	public Date getLastDate()
	{
		Date lastDate = null;
		boolean fareDate = false;
		boolean serviceDate = false;
		
		if(fareList.size() > 0)
		{
			lastDate = fareList.get(fareList.size()-1).getDate();
			fareDate = true;
		}
		else if(serviceList.size() > 0)
		{
			lastDate = serviceList.get(serviceList.size()-1).getDate();
			serviceDate = true;
		}
		else if(gasList.size() > 0)
		{
			lastDate = gasList.get(gasList.size()-1).getDate();
		}
		
		if(serviceList.size() > 0 && fareDate)
		{
			if(lastDate.compareTo(serviceList.get(serviceList.size()-1).getDate()) < 0)
			{
				lastDate = serviceList.get(serviceList.size()-1).getDate();
			}
		}
		
		if(gasList.size() > 0 && (fareDate || serviceDate))
		{
			if(lastDate.compareTo(gasList.get(gasList.size()-1).getDate()) < 0)
			{
				lastDate = gasList.get(gasList.size()-1).getDate();
			}
		}
		
		return lastDate;
	}
	
	/**
	 * Returns {@link fareList}
	 * @return the FareRecord ArrayList of the cab
	 */
	public ArrayList<FareRecord> getFareList()
	{
		return fareList;
	}
	
	/**
	 * Returns {@link gasList}
	 * @return the GasRecord ArrayList of the cab
	 */
	public ArrayList<GasRecord> getGasList()
	{
		return gasList;
	}
	
	/**
	 * Returns {@link serviceList}
	 * @return the ServiceRecord ArrayList of the cab
	 */
	public ArrayList<ServiceRecord> getServiceList()
	{
		return serviceList;
	}
	
	/**
	 * Calculates the number of days between 2 dates
	 * @param d1 - earlier date
	 * @param d2 - later date
	 * @return days between d1 and d2
	 */
	public long countDays(Date d1, Date d2)
	{
		final long LENGTH_OF_DAY = 24 * 60 * 60 * 1000;
		long dayDifference = 0;
		
		long t1;
		long t2;

		t1 = d1.getTime();
		t2 = d2.getTime();
		
		long diff = t2 - t1;
		
		dayDifference = (diff/LENGTH_OF_DAY);
		
		return dayDifference;
	}
}