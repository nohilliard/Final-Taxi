package edu.trident.hilliard.finalassignment;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 * Gathers input and output file names, sets defaults values in project directory if
 * user does not input values.  Creates CabRecord object to hold current record
 * being added to CabInfo ArrayList.  Checks for existing CabInfo by cabName before adding
 * a new cab to the CabInfo ArrayList, calls the appropriate addRecord based on record type.
 * Manages PrintWriter to save output to file.  Creates TaxiUI for adding additional records.<br><br>
 * Variables:<br>
 * summaryGross - holds gross earnings for summary<br>
 * summaryGasCost - holds gas cost for summary<br>
 * summaryServiceCost - holds service cost for summary<br>
 * summaryNet - holds net earnings for summary<br>
 * summaryMiles - holds total miles for summary<br>
 * summaryServiceAvg - holds service average total for summary<br>
 * @author Noah Hilliard
 */
public class RecordRunner
{
	
	public static void main(String[] args) throws FileNotFoundException
	{
		double summaryGross = 0.0;
		double summaryGasCost = 0.0;
		double summaryServiceCost = 0.0;
		double summaryNet = 0.0;
		double summaryMiles = 0.0;
		double summaryServiceAvg = 0.0;
		
		String inputFile = JOptionPane.showInputDialog("Enter input file path and name: ");
		
		String outputFile = JOptionPane.showInputDialog("Enter output file path and name: ");
		
		String newCab = JOptionPane.showInputDialog("Enter new cab name: ");
		
		if(outputFile.isEmpty())
		{
			outputFile = "output.csv";
		}
		
		if(inputFile.isEmpty())
		{
			inputFile = "defaultRecords.csv";
		}
		
		CabRecord record = new CabRecordManager();
		
		ArrayList<CabInfo> cabList = new ArrayList<CabInfo>();
		ArrayList<RentedTaxi> cabs = new ArrayList<RentedTaxi>();
		CabRecordReader reader = new CabRecordReader(inputFile);
		PrintWriter writer = new PrintWriter(outputFile);
		
		if(!newCab.isEmpty())
		{
			CabInfo newCabInfo = new CabInfo(newCab);
			cabList.add(newCabInfo);
			FareRecord fRecord = new FareRecord(new Date(),0);
			cabList.get(0).addFareRecord(fRecord);
		}
		
		while(reader.hasMoreRecords())
		{
			//Save CabRecord and check it for null before adding to ArrayList
			record = reader.getNextRecord();
			if(record != null)
			{
				int cabPosition = -1;
				CabInfo infoRecord = new CabInfo(record);
				/*for(int i = 0; i <= cabList.size()-1; i++)
				{
					if(record.getCabId().equals(cabList.get(i).getCabName()))
					{
						cabPosition = i;
					}
				}*/
				
				for(CabInfo cabInfo : cabList)
				{
					if(record.getCabId().equals(cabInfo.getCabName()))
					{
						cabPosition = cabList.indexOf(cabInfo);
					}
				}
				
				if(cabPosition == -1)
				{
					cabPosition = cabList.size();
					cabList.add(infoRecord);
				}
				
				switch(record.getType())
				{
					case GAS: cabList.get(cabPosition).addGasRecord(record);
								break;
					case FARE: cabList.get(cabPosition).addFareRecord(record);
								break;
					case SERVICE: cabList.get(cabPosition).addServiceRecord(record);
								break;
				}
				
			}	
		}
		
		
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		//Print header of file
		writer.println("Cab ID,Start Date,End Date,Gross Earnings,Gas Cost,Service Cost,Net Earnings,"
						+ "Total Miles,Services Performed,Average Service,Max Service");
		
		//Sort by cabName
		Collections.sort(cabList, new Comparator<CabInfo>(){
   		    public int compare(CabInfo first, CabInfo second) {
                return first.getCabName().compareTo(second.getCabName());
   		    }
		});
		
		//Sort each CabInfo record and print values to file
		for(CabInfo infoRecord : cabList)
		{
			infoRecord.sortArrays();
			cabs.add(new RentedTaxi(infoRecord.getCabName()));
			writer.printf("%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%d,%.2f,%d%n", infoRecord.getCabName(), formatter.format(infoRecord.getFirstDate()),
							formatter.format(infoRecord.getLastDate()),infoRecord.getGrossEarnings(),infoRecord.getTotalGasCost(),
							infoRecord.getTotalServiceCost(),infoRecord.getNetEarnings(),infoRecord.getTotalMiles(),infoRecord.getServiceCount(),
							infoRecord.getAvgServiceDays(),infoRecord.getMaxDaysBetweenService());
			
			//Calculate running totals for summary
			summaryGross += infoRecord.getGrossEarnings();
			summaryGasCost += infoRecord.getTotalGasCost();
			summaryServiceCost += infoRecord.getTotalServiceCost();
			summaryNet += infoRecord.getNetEarnings();
			summaryMiles += infoRecord.getTotalMiles();
			summaryServiceAvg += infoRecord.getAvgServiceDays();
		}
		
		//Divide SummaryServiceAvg Total by number of records
		summaryServiceAvg = summaryServiceAvg/cabList.size();
		
		//Print summary to console
		System.out.printf("Summary for %d cabs:%n", cabList.size());
		System.out.printf("---------------------%n");
		System.out.printf("Gross Earnings: %.2f%n", summaryGross);
		System.out.printf("Total Gas Cost: %.2f%n", summaryGasCost);
		System.out.printf("Total Service Cost: %.2f%n", summaryServiceCost);
		System.out.printf("Net Earnings: %.2f%n", summaryNet);
		System.out.printf("Total Miles Driven: %.2f%n", summaryMiles);
		System.out.printf("Average Miles Between Services: %.2f%n", summaryServiceAvg);
		
		writer.close();
		
		TaxiUI cabWindow = new TaxiUI(cabs,cabList,inputFile,outputFile);
	}	
}