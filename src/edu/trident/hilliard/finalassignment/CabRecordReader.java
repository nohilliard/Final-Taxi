package edu.trident.hilliard.finalassignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import edu.trident.hilliard.finalassignment.CabRecord.RecordType;

/**
 * Opens file at inputPath and reads records into an ArrayList.<br><br>
 * Variables:<br>
 * cabList - ArrayList of cabs
 * @author Noah Hilliard
 *
 */
public class CabRecordReader
{
	private Scanner input;
	private ArrayList<CabRecordManager> cabList = new ArrayList<CabRecordManager>();

	/**
	 * Constructor that accepts inputPath and opens the file or prints
	 * to System.err if it encounters FileNotFound Exception.
	 * @param inputPath path that the input file is read from
	 */
	public CabRecordReader(String inputPath)
	{
		try
		{
			input = new Scanner(new File(inputPath));
		} catch (FileNotFoundException e)
		{
			System.err.println("File not found.");
			System.exit(1);
		}
	}
	
	/**
	 * Checks if the scanner sees more data in the file.
	 * @return true or false based on data in file
	 */
	public boolean hasMoreRecords()
	{
		return input.hasNext();
	}
	
	/**
	 * Reads one line from the file, verifies the input, and creates a
	 * CabRecordManager object which is saved into an ArrayList of
	 * CabRecordManager objects.<br><br>
	 * Variables:<br>
	 * tmpRecType - temporary record type holder<br>
	 * tmpCabDate - temporary cab date holder<br>
	 * tmpCabId - temporary cab name holder<br>
	 * tmpInputValue - temporary record input value<br>
	 * tmpGasCost - temporary gas cost value<br>
	 * @return Individual CabRecord
	 */
	public CabRecord getNextRecord()
	{
		boolean validRecord = true;
		
		CabRecordManager recordManager = null;
		
		RecordType tmpRecType = null;
		Date tmpCabDate = null;
		String tmpCabId = null;
		double tmpInputValue = 0;
		double tmpGasCost = 0;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String line;
		String[] fields;

		line = input.nextLine();
		fields = line.split(",");
		
		try
		{
			tmpCabDate = format.parse(fields[0]);
			tmpCabId = fields[1];
			tmpRecType = CabRecord.RecordType.valueOf(fields[2]);
			tmpInputValue = Double.parseDouble(fields[3]);
			if(fields[2].equals("GAS"))
			{
				tmpGasCost = Double.parseDouble(fields[4]);
			}
		} 
		catch (ParseException e)
		{
			validRecord = false;
			System.err.println("Parse error: " + line);
		}
		catch (NumberFormatException e)
		{
			validRecord = false;
			System.err.println("Conversion error: " + line);
		}
		catch (Exception e)
		{
			validRecord = false;
			System.err.println("Other error: " + line);
		}
		
		if(validRecord)
		{
			recordManager = new CabRecordManager();
			
			recordManager.setCabDate(tmpCabDate);
			recordManager.setCabId(tmpCabId);
			recordManager.setRecType(tmpRecType);
			recordManager.setInputValue(tmpInputValue);
			recordManager.setGasCost(tmpGasCost);
			
			cabList.add(recordManager);
		}

		//Frustrating to check null - how to just return nothing on error?
		return recordManager;
	}
}