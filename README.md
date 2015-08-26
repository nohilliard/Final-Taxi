# Final-Taxi
Advanced Java Final Project

The program can be run from RecordRunner.java, when it is run the user will encounter 3 dialog boxes requesting the name of
the file to be read from, the name of the file to be output to, and the name of a cab if they wish to add a new one.  In the 
event of default entry input file name is defaultRecords.csv, output file name is output.csv, and no new cab is created.  An 
initial summary is generated based on the data contained in the input file.

New Cab:
Since a cab cannot exist without records, the new cab is created with a 0 mile fare which can be deleted once new records are
added for the cab.

Once the UI launches the user is presented with a tabbed window that contains 3 tabs named “Cab”, “File”, and “Edit”.

Cab:
a date field that is initially populated as the last date the selected cab was operated.
an input field for record adding
a second input field for recording gas cost
a combo box to select operations to be performed on the cab
a combo box to select which cab the operations will be performed on
a button labeled “OK” that will execute the selected option on the selected cab
an output field that will report information regarding the operation performed

File:
2 labeled fields for changing inputName and outputName
a button labeled “Update” which sets the above variables to the values in the fields

Edit:
a combo box to select the cab
a combo box to select the record type
a combo box populated with all records for the selected cab and type
a button to delete an individual record, deletes the cab if the last record is deleted
a button to delete an entire cab

Close:
When the frame is closed, a new input and output file are generated based on inputName and outputName variables, containing 
the information added during the execution of the program.  This also generates a new summary in the same format as the one 
generated when the program is done reading all of the records.
