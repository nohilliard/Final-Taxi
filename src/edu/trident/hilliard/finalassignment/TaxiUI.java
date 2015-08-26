package edu.trident.hilliard.finalassignment;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * TaxiUI creates and populates a JFrame with 3 JTextFields for GasInput, Input, and Output,
 * a combo box and button to provide functionality of recording trips and adding gas,
 * reporting gas, distance.<br><br>
 * Variables:<br>
 * currCab - RentedTaxi object that is currently selected in the UI<br>
 * cabList - ArrayList of CabInfo records<br>
 * cabs - ArrayList of RentedTaxi objects<br>
 * cabDate - initialized as the last date of the cab<br>
 * SERVICE_COST - constant value for the cost of servicing a cab<br>
 * cabListPosition - tracks which cab is currently selected in the combo box<br>
 *
 * @author Noah Hilliard
 *
 */
public class TaxiUI implements ActionListener, ItemListener
{
	RentedTaxi currCab;
	ArrayList<CabInfo> cabList;
	ArrayList<RentedTaxi> cabs;
	Date cabDate;
	final double SERVICE_COST = 12.0;
	DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	JFormattedTextField cabDateField;
	String inputFile;
	String outputFile;
	int cabListPosition;

	JFrame frame;
	JTabbedPane tabbedPane;
	JPanel cabInputPanel;
	JPanel filePanel;
	JPanel cabEditPanel;
	JButton btnOk;
	JButton btnUpdate;
	JButton btnDelete;
	JButton btnDeleteCab;
	JComboBox<String> cboSelect;
	JComboBox<String> cboCab;
	JComboBox<String> cboEditCab;
	JComboBox<String> cboCabRecords;
	JComboBox<String> cboRecordType;
	JTextField txtInput;
	JTextField txtGasInput;
	JTextField txtOutput;
	JTextField txtInputFile;
	JTextField txtOutputFile;
	JLabel lblInputFile;
	JLabel lblOutputFile;
	
	/**
	 * Constructor accepts a RentedTaxi object and an ArrayList of CabInfo records
	 * cabList is initialized for use by the UI and the last date of the first cab
	 * is requested through {@link CabInfo#getLastDate() getLastDate} as the cabDate
	 * @param inputCabs ArrayList of RentedTaxi objects
	 * @param inputCabList ArrayList of CabInfo objects
	 * @param input String representing input file name
	 * @param output String representing output file name
	 */
	public TaxiUI(ArrayList<RentedTaxi> inputCabs, ArrayList<CabInfo> inputCabList, String input, String output)
	{
		cabList = inputCabList;
		cabs = inputCabs;
		cabDate = inputCabList.get(0).getLastDate();
		currCab = cabs.get(0);
		inputFile = input;
		outputFile = output;
		init();
	}
	
	/**
	 * Initializes the frame, provides a layout, places the combo box, button, and textfields.  
	 * Adds ActionListener to each button, orients and makes all text legible.
	 * @throws FileNotFoundException 
	 */
	private void init()
	{
		frame = new JFrame("Acme Taxi Tracker Driver");
		tabbedPane = new JTabbedPane();
		
		frame.setLayout(new FlowLayout());
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		frame.add(topPanel);
		createInputTab();
		createFileTab();
		createEditTab();
		tabbedPane.addTab("Cab", cabInputPanel);
		tabbedPane.addTab("File", filePanel);
		tabbedPane.addTab("Edit", cabEditPanel);
		topPanel.add(tabbedPane, BorderLayout.CENTER);
		
		for(CabInfo infoRecord : cabList)
		{
			String cabName = infoRecord.getCabName();
			cboCab.addItem(cabName);
			cboEditCab.addItem(cabName);
			
			if(cabList.indexOf(infoRecord) == 0)
			{
				for(GasRecord record : infoRecord.getGasList())
				{
					cboCabRecords.addItem(record.toString());
				}
			}
		}
		
		WindowListener listener = new WindowAdapter()
		{
			public void windowClosing(WindowEvent w)
			{
				if(outputFile.isEmpty())
				{
					outputFile = "output.csv";
				}
				if(inputFile.isEmpty())
				{
					inputFile = "defaultRecords.csv";
				}
				PrintWriter outputWriter = null;
				PrintWriter inputWriter = null;
				
				try
				{
					outputWriter = new PrintWriter(outputFile);
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
				double summaryGross = 0.0;
				double summaryGasCost = 0.0;
				double summaryServiceCost = 0.0;
				double summaryNet = 0.0;
				double summaryMiles = 0.0;
				double summaryServiceAvg = 0.0;
				SimpleDateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy/MM/dd");
				//Print header of file
				outputWriter.println("Cab ID,Start Date,End Date,Gross Earnings,Gas Cost,Service Cost,Net Earnings,"
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
					
					outputWriter.printf("%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%d,%.2f,%d%n", infoRecord.getCabName(), outputFormatter.format(infoRecord.getFirstDate()),
									outputFormatter.format(infoRecord.getLastDate()),infoRecord.getGrossEarnings(),infoRecord.getTotalGasCost(),
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
				
				outputWriter.close();
				
				try
				{
					inputWriter = new PrintWriter(inputFile);
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
				
				for(CabInfo infoRecord : cabList)
				{
					for(GasRecord record : infoRecord.getGasList())
					{
						inputWriter.printf("%s,%s,%s,%.2f,%.2f%n", inputFormatter.format(record.getDate()), infoRecord.getCabName(),
								"GAS",record.getGasGallons(),record.getGasCost());
					}
					
					for(FareRecord record : infoRecord.getFareList())
					{
						inputWriter.printf("%s,%s,%s,%.2f%n", inputFormatter.format(record.getDate()), infoRecord.getCabName(),
								"FARE",record.getFareMiles());
					}
					
					for(ServiceRecord record : infoRecord.getServiceList())
					{
						inputWriter.printf("%s,%s,%s,%.2f%n", inputFormatter.format(record.getDate()), infoRecord.getCabName(),
								"SERVICE",record.getServiceCost());
					}
				}
				inputWriter.close();
				System.exit(0);
			}
		};
		
		frame.addWindowListener(listener);
		frame.setVisible(true);
		frame.pack();
	}
	
	/**
	 * Creates the input panel and all associated objects
	 */
	public void createInputTab()
	{
		cabInputPanel = new JPanel();
		cabInputPanel.setLayout(new FlowLayout());
		
		btnOk = new JButton("OK");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete Record");
		btnDeleteCab = new JButton("Delete Cab");
		txtInput = new JTextField(10);
		txtGasInput = new JTextField(10);
		txtOutput = new JTextField(30);
		cabDateField = new JFormattedTextField(format);
		String[] taxiOptions = {"Record Trip", "Add Gas", "Service", "Gas Report", "Miles Remaining", "Miles Since Service"};
		cboSelect = new JComboBox<String>(taxiOptions);
		cboCab = new JComboBox<String>();
		
		cabInputPanel.add(cabDateField);
		cabInputPanel.add(txtInput);
		cabInputPanel.add(txtGasInput);
		cabInputPanel.add(cboSelect);
		cabInputPanel.add(cboCab);
		cabInputPanel.add(btnOk);
		cabInputPanel.add(txtOutput);
		
		cabInputPanel.setSize(200, 300);
		txtGasInput.setEnabled(false);
		txtOutput.setEnabled(false);
		txtOutput.setDisabledTextColor(Color.BLACK);
		cabDateField.setValue(cabDate);
		
		btnOk.addActionListener(this);
		cboSelect.addItemListener(this);
		cboCab.addItemListener(this);
		
	}
	
	/**
	 * Creates the file tab and all associated objects
	 */
	public void createFileTab()
	{
		filePanel = new JPanel();
		filePanel.setLayout(new FlowLayout());
		
		txtInputFile = new JTextField(10);
		txtOutputFile = new JTextField(10);
		lblInputFile = new JLabel("Input file name: ");
		lblOutputFile = new JLabel("Output file name: ");
		
		filePanel.add(lblInputFile);
		filePanel.add(txtInputFile);
		filePanel.add(lblOutputFile);
		filePanel.add(txtOutputFile);
		filePanel.add(btnUpdate);
		
		txtInputFile.setText(inputFile);
		txtOutputFile.setText(outputFile);
		
		btnUpdate.addActionListener(this);
	}
	
	/**
	 * Creates the edit tab and all associated objects
	 */
	public void createEditTab()
	{
		cabEditPanel = new JPanel();
		cabEditPanel.setLayout(new FlowLayout());
		
		cboEditCab = new JComboBox<String>();
		cboCabRecords = new JComboBox<String>();
		String[] recordType = {"GAS","FARE","SERVICE"};
		cboRecordType = new JComboBox<String>(recordType);
		
		cabEditPanel.add(cboEditCab);
		cabEditPanel.add(cboRecordType);
		cabEditPanel.add(cboCabRecords);
		cabEditPanel.add(btnDelete);
		cabEditPanel.add(btnDeleteCab);
		
		btnDelete.addActionListener(this);
		btnDeleteCab.addActionListener(this);
		cboCabRecords.addItemListener(this);
		cboRecordType.addItemListener(this);
		cboEditCab.addItemListener(this);	
	}

	/**
	 * Calls methods from RentedTaxi object passed by MainClass to populate the 
	 * UI with data based on the item selected in the combo box.
	 */
	public void actionPerformed(ActionEvent e)
	{
		
		DecimalFormat dollar = new DecimalFormat("0.##");
		DecimalFormat reading = new DecimalFormat("0.####");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		if(e.getSource() == btnOk)
		{
			cabDate = (Date) cabDateField.getValue();
			try {
				//Check for Record Trip selection and txtInput not null
				if(cboSelect.getSelectedItem() == "Record Trip" && !txtInput.getText().equals(""))
				{
					double temp = 0.0;
					double inputValue = 0.0;
					if(Double.parseDouble(txtInput.getText()) >= 0)
					{
						inputValue = Double.parseDouble(txtInput.getText());
						temp = currCab.calculateFare(inputValue);
						
						if(temp == 0.0)
						{
							txtOutput.setText("Not enough gas.");
						}
						else
						{
							
							currCab.addRentedTrip(inputValue);

							txtOutput.setText("The trip cost was $" + String.valueOf(dollar.format(temp)) + ".");
							FareRecord fRecord = new FareRecord(cabDate, inputValue);
							cabList.get(cabListPosition).addFareRecord(fRecord);
							System.out.printf("%s,%s,%s,%.2f%n",formatter.format(cabDate),cabList.get(cabListPosition).getCabName(),
												"FARE",inputValue);
						}
					}
					else
					{
						txtOutput.setText("Please enter a valid number of miles.");
					}
				}
				//Check for Add Gas selection and txtInput/txtGasInput not null
				else if (cboSelect.getSelectedItem() == "Add Gas" && !txtInput.getText().equals("") && !txtGasInput.getText().equals(""))
				{
					double numGallons = Double.parseDouble(txtInput.getText());
					double gasPrice = Double.parseDouble(txtGasInput.getText());
					
					if(numGallons >= 0 && gasPrice >= 0)
					{
						currCab.addGasCost(numGallons,gasPrice);
						currCab.addGas(numGallons);
						txtOutput.setText("Gas added, there are now " + String.valueOf(reading.format(currCab.getCurrentGas())) + " gallons.");
						GasRecord gRecord = new GasRecord(cabDate, numGallons, gasPrice);
						cabList.get(cabListPosition).addGasRecord(gRecord);
						System.out.printf("%s,%s,%s,%.2f%,.2f%n",formatter.format(cabDate),cabList.get(cabListPosition).getCabName(),
								"GAS",numGallons,gasPrice);
					}
					else
					{
						txtOutput.setText("Please enter a valid number of gallons.");
					}
				}
				else if (cboSelect.getSelectedItem() == "Service")
				{
					currCab.serviceTaxi(SERVICE_COST);
					txtOutput.setText("The taxi has been serviced.");
					ServiceRecord sRecord = new ServiceRecord(cabDate, SERVICE_COST);
					cabList.get(cabListPosition).addServiceRecord(sRecord);
					System.out.printf("%s,%s,%s,%.2f%n",formatter.format(cabDate),cabList.get(cabListPosition).getCabName(),
							"SERVICE",SERVICE_COST);
				}
				else if (cboSelect.getSelectedItem() == "Gas Report")
				{
					txtOutput.setText("There are currently " + String.valueOf(reading.format(currCab.getCurrentGas())) + " gallons of gas.");
				}
				else if (cboSelect.getSelectedItem() == "Miles Remaining")
				{
					txtOutput.setText("The taxi can drive " + String.valueOf(reading.format(currCab.milesAvailable())) + " miles on its current gas.");
				}
				else if (cboSelect.getSelectedItem() == "Miles Driven")
				{
					txtOutput.setText("The taxi has " + String.valueOf(reading.format(currCab.getCurrentMileage())) + " miles.");
				}
				else if (cboSelect.getSelectedItem() == "Miles Since Service")
				{
					txtOutput.setText("The taxi has gone " + String.valueOf(reading.format(currCab.getServiceMileage())) + 
							" miles since the last service.");
				}
				else if (cboSelect.getSelectedItem() == "Gross Earnings")
				{
					txtOutput.setText("$" + String.valueOf(dollar.format(currCab.getTotalFare())) + " earned gross.");
				}
				else if (cboSelect.getSelectedItem() == "Net Earnings")
				{
					txtOutput.setText("$" + String.valueOf(dollar.format(currCab.getTotalFare() - currCab.getOperatingCost())) + 
							" earned after operating costs.");
				}
				else if(cboSelect.getSelectedItem() == "Reset")
				{
					currCab.resetRentedTaxi();
					txtOutput.setText("The taxi has been reset.");
				}
				//Request a valid input if the criteria for Record Trip or Add Gas are not met
				else
				{
					txtOutput.setText("Please input a valid value.");
				}
			} 
			//Request valid input if we encounter a conversion exception
			catch (NumberFormatException err)
			{
				txtOutput.setText("Please input a valid value.");
			}
		}
		else if(e.getSource() == btnUpdate)
		{
			if(txtInputFile.getText().length()>0)
			{
				inputFile = txtInputFile.getText();
			}
			else
			{
				txtInputFile.setText(inputFile);
			}
			
			if(txtOutputFile.getText().length()>0)
			{
				outputFile = txtOutputFile.getText();
			}
			else
			{
				txtOutputFile.setText(outputFile);
			}
		}
		else if(e.getSource() == btnDelete)
		{
			int index = cboCabRecords.getSelectedIndex();
			for(CabInfo infoRecord : cabList)
			{
				if(cboEditCab.getSelectedItem().equals(infoRecord.getCabName()))
				{
					if(cboRecordType.getSelectedItem().toString().equals("GAS"))
					{
						infoRecord.removeGasRecord(index);
						cboCabRecords.removeItemAt(index);
					}
					
					if(cboRecordType.getSelectedItem().toString().equals("FARE"))
					{
						infoRecord.removeFareRecord(index);
						cboCabRecords.removeItemAt(index);
					}
					
					if(cboRecordType.getSelectedItem().toString().equals("SERVICE"))
					{
						infoRecord.removeServiceRecord(index);
						cboCabRecords.removeItemAt(index);
					}
					
					if(btnDelete.isEnabled() && cboCabRecords.getItemCount() == 0)
					{
						btnDelete.setEnabled(false);
					}
					if(!btnDelete.isEnabled() && cboCabRecords.getItemCount() > 0)
					{
						btnDelete.setEnabled(true);
					}
					
					if(infoRecord.getServiceCount() == 0 && infoRecord.getFareCount() == 0 && infoRecord.getGasCount() == 0)
					{
						cabList.remove(infoRecord);
						JOptionPane.showMessageDialog(null, cboEditCab.getSelectedItem() + " deleted.");
						cboCab.removeItemAt(cboEditCab.getSelectedIndex());
						cboEditCab.removeItemAt(cboEditCab.getSelectedIndex());
						cboCab.setSelectedIndex(0);
						cboEditCab.setSelectedIndex(0);
						break;
					}
				}
			}
			
		}
		else if(e.getSource() == btnDeleteCab)
		{
			for(CabInfo infoRecord : cabList)
			{
				if(cboEditCab.getSelectedItem().equals(infoRecord.getCabName()))
				{
					cabList.remove(infoRecord);
					JOptionPane.showMessageDialog(null, cboEditCab.getSelectedItem() + " deleted.");
					cboCab.removeItemAt(cboEditCab.getSelectedIndex());
					cboEditCab.removeItemAt(cboEditCab.getSelectedIndex());
					cboCab.setSelectedIndex(0);
					cboEditCab.setSelectedIndex(0);
					break;
				}
			}
		}
	}

	/**
	 * Clears and populates cboCabRecords based on the information in the event
	 * from {@link itemStateChanged}
	 * @param event ItemEvent generated by {@link itemStateChanged}
	 */
	public void populateCabRecordList()
	{
		cboCabRecords.removeAllItems();
		for(CabInfo infoRecord : cabList)
		{
			if(cboEditCab.getSelectedItem().toString().equals(infoRecord.getCabName()))
			{
				if(cboRecordType.getSelectedItem().toString().equals("GAS"))
				for(GasRecord record : infoRecord.getGasList())
				{
					cboCabRecords.addItem(record.toString());
				}
				
				if(cboRecordType.getSelectedItem().toString().equals("FARE"))
				for(FareRecord record : infoRecord.getFareList())
				{
					cboCabRecords.addItem(record.toString());
				}
				
				if(cboRecordType.getSelectedItem().toString().equals("SERVICE"))
				for(ServiceRecord record : infoRecord.getServiceList())
				{
					cboCabRecords.addItem(record.toString());
				}
				
				if(btnDelete.isEnabled() && cboCabRecords.getItemCount() == 0)
				{
					btnDelete.setEnabled(false);
				}
				if(!btnDelete.isEnabled() && cboCabRecords.getItemCount() > 0)
				{
					btnDelete.setEnabled(true);
				}
			}
		}
	}
	
	/**
	 * Controls all combo boxes in the frame
	 */
	public void itemStateChanged(ItemEvent event)
	{
		//Enables gas input field if Add Gas is selected on input tab
		if(event.getSource() == cboSelect)
		{
			if(event.getItem().equals("Add Gas"))
			{
				txtGasInput.setEnabled(true);
			}
			else if(txtGasInput.isEnabled())
			{
				txtGasInput.setEnabled(false);
			}
		}
		//Calls populateCabRecordList
		else if(event.getSource() == cboEditCab)
		{
			if(event.getStateChange() == ItemEvent.SELECTED)
			{
				populateCabRecordList();
			}
			
		}
		//Calls populateCabRecordList
		else if(event.getSource() == cboRecordType)
		{
			if(event.getStateChange() == ItemEvent.SELECTED)
			{
				populateCabRecordList();
			}
			
		}
		else if(event.getSource() == cboCab)
		{
			for(CabInfo infoRecord : cabList)
			{
				if(event.getItem().equals(infoRecord.getCabName()))
				{
					cabListPosition = cabList.indexOf(infoRecord);
					cabDateField.setValue(cabList.get(cabListPosition).getLastDate());
					currCab = cabs.get(cabListPosition);
				}
			}
		}
	}
}
