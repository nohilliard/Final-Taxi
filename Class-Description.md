Taxi and its child RentedTaxi are objects representing the physical taxi cabs.  These classes contain the logic necessary to 
ensure proper operation of the cab by using methods such as addFare, addGas, addMileage, addTrip, calculateFare, and useGas 
which use other methods included in the class to verify that the cab can perform the requested action.

CabRecordManager implements the interface CabRecord and is used as an object that stores data about a specific record of cab 
data.  The methods used by CabRecordManager are primarily used to get and set the values of variables, though it also includes 
calculateGasTotal which returns the cost of the record if it is a gas record.  CabRecordManager contains an overriden toString 
which allows the object to be printed in a specific format.

CabRecordReader is used to create CabRecordManager objects through use of its methods hasMoreRecords and getNextRecord.

TaxiUI is the user interface and manages the cab and data objects as well as the input and output file saving when the day is 
finished and the program is closed.  It contains 3 tabbed panes that are used for separate purposes: Cab, File, and Edit.  Cab 
allows the user to add records to a selected cab and generate summaries related to the records added during a specific run of the program.  File allows the user to change the input and output file names that will be used to save the data that was loaded in, the data that was added, and any data that was modified during the operation of the program.  Edit allows for the removal of any specific records or an entire cab, it can be operated by selecting a Cab name and record type to generate a list of the records that exist for that combination.

GenericCabRecord and its children FareRecord, GasRecord, and ServiceRecord are objects that represent individual records created 
from either a CabRecordManager created by CabRecordReader or created directly with information from TaxiUI.  These classes 
operate with the use of get methods to retrieve the data stored in them.

CabInfo contains a String that identifies a specific cab and 3 ArrayLists that will be loaded with the Fare, Gas, and Service 
records for that specific cab.  It contains an additional ArrayList that is used to collect data regarding the frequency of 
service. CabInfo contains methods to retrieve individual pieces of data or the 3 record specific ArrayLists as well as methods to add or remove records from those ArrayLists.

RecordRunner is the main portion of the program, it prompts the user for an input file name, output file name, and name of new 
cab to be created.  If the file names are left blank the default values of “defaultRecords.csv” and “output.csv” will be used.  
If the cab name is left blank TaxiUI will open without creating a new cab, if a cab name is entered a new cab will be created 
with a single fare record of 0 miles.  RecordRunner then reads all of the data from the input file, populates it’s ArrayLists 
of RentedTaxi and CabInfo objects, and generates an initial summary before launching TaxiUI by passing it the ArrayLists and 
the file names.
