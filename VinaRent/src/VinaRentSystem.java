import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class VinaRentSystem {
    private List<Branch> branchList;
    private List<Model> modelList;
    private List<Car> carList;
    private List<Customer> customerList;
    private List<Rental> rentalList;
    private List<Customer> blacklist;

    public VinaRentSystem() {
        this.branchList = new ArrayList<>();
        this.modelList = new ArrayList<>();
        this.carList = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.rentalList = new ArrayList<>();
        this.blacklist = new ArrayList<>();
    }

// ---------------------------------- PRIVATE METHODS ---------------------------------- //
    
    private Branch getBranch(String branchNumber) throws Exception{
    	Iterator<Branch> itr = branchList.iterator();
    	while(itr.hasNext()) {
    		Branch branch = itr.next();
    		if (branch.getBranchNumber().equals(branchNumber))
    			return branch;
    	}
    	
    	String msg = "Error: No branch found!";
		System.out.println(msg);
		throw new Exception(msg);
    }
    
    private Model getModel(String modelNumber) throws Exception{
    	Iterator<Model> itr = modelList.iterator();
    	while (itr.hasNext()) {
    		Model model = itr.next();
    		if (model.getNumber().equals(modelNumber))
    			return model;
    	}
    	
    	String msg = "Error: No model found!";
		System.out.println(msg);
		throw new Exception(msg);
    }
    
    private Customer getCustomer(String driverLicense) throws Exception{
    	Iterator<Customer> itr = customerList.iterator();
    	while (itr.hasNext()) {
			Customer customer = (Customer) itr.next();
			if (customer.getDriveLicense().equals(driverLicense))
				return customer;
		}
    	
    	String msg = "Error: No customer found!";
		System.out.println(msg);
		throw new Exception(msg);
    }
    
    private Rental getRental(String rentalNumber) throws Exception {
    	Iterator<Rental> itr = rentalList.iterator();
    	while (itr.hasNext()) {
			Rental rental = (Rental) itr.next();
			if (rental.getNumber().equals(rentalNumber))
				return rental;
		}
    	
    	String msg = "Error: No rental found!";
		System.out.println(msg);
		throw new Exception(msg);
    }

    private Car getCar(String regNum) throws Exception {
    	Iterator<Car> itr = carList.iterator();
    	while (itr.hasNext()) {
			Car car = (Car) itr.next();
			if (car.getRegNum().equals(regNum))
				return car;
		}
    	
    	String msg = "Error: No registration number found!";
		System.out.println(msg);
		throw new Exception(msg);
    }
    
	// check if there is available car in a branch or neighbor branch
	// return a car if exists
	private Car findCar(String modelNum, String color, int year, String branchNumber) throws Exception {
		// check if modelNum and branchNumber exist, then get a list of available cars
		List<Car> availableCars = new ArrayList<>();
		availableCars = findCars(modelNum, branchNumber);
		
		// search for car with specified properties
		for (Car c : availableCars) {
			if (c.getColor().equals(color) && c.getYear() == year)
				return c;
		}
		
		// if this branch does not have the car
		Branch thisBranch = getBranch(branchNumber);
		for (Branch b : thisBranch.getNeighborList()) {
			availableCars.clear();
			availableCars = findCars(modelNum, b.getBranchNumber());
			for (Car c : availableCars) {
				if (c.getColor().equals(color) && c.getYear() == year)
					return c;
			}
		}
		
		// if no car is found
		String msg = "Error: No car found!";
		System.out.println(msg);
		throw new Exception(msg);
	}
	
	private List<Car> findCars(String modelNumber, String branchNumber) throws Exception {
		// check if modelNumber exists
		getModel(modelNumber);
		
		// check if branchNumber exists
		Branch branch =  getBranch(branchNumber);
		
		// return available cars at the branch with given model
		List<Car> availableCars = new ArrayList<>();
		Iterator<Car> itr = branch.getCarList().iterator();
		while (itr.hasNext()) {
			Car car = (Car) itr.next();
			if (car.getModelNumber().equals(modelNumber) && car.getStatus().equals(Status.READY))
				availableCars.add(car);
		}
		return availableCars;
	}

// ------------------------------ END OF PRIVATE METHODS ------------------------------- //
	
	
	
// ------------------------------------- GETTERS --------------------------------------- //
	public List<Branch> getBranchList() {
		return branchList;
	}
	
	public List<Car> getCarList() {
		return carList;
	}
	
	public List<Customer> getCustomerList() {
		return customerList;
	}
	
	public List<Model> getModelList() {
		return modelList;
	}
	
	public List<Rental> getRentalList() {
		return rentalList;
	}
	
	public List<Customer> getBlacklist() {
		return blacklist;
	}
	
// --------------------------------- END OF GETTERS ------------------------------------ //
    
    
    
// --------------------------------- ATOMIC USE CASES ---------------------------------- // 
    // 1. Add a branch
    public void addBranch(String branchNumber, String name) throws Exception {
        // check if the branchNumber is duplicated
        for (Branch branch : branchList) {
            if (branch.getBranchNumber() == branchNumber) {
                String errMess = "Error: Branch number is already assigned. Please replace with a new branch number.\n";
                System.out.println(errMess);
                throw new Exception(errMess);
            }
        }

        // Create and add a new branch
        Branch newBranch = new Branch(branchNumber, name);
        branchList.add(newBranch);
    }

    // 2. Make a pair of branches neighbors to each other
    public void makeNeighbor(String branch1, String branch2) throws Exception {
        // check if branch 1 and branch 2 exist
        Branch b1 = getBranch(branch1);
        Branch b2 = getBranch(branch2);

        // check if branch 1 and the branch 2 are not already neighbor
        for (Branch branch : b1.getNeighborList()) {
        	if (branch.getBranchNumber().equals(branch2)) {
        		String msg = "Error: Branches are already neighbors!";
        		System.out.println(msg);
        		throw new Exception(msg);
        	}
        }

        // add neighbor branch to this branch???s neighborList
        b1.getNeighborList().add(b2);
        b2.getNeighborList().add(b1);
    }
    
    // 3. Add a car rental group
    public void addRentalGroup() throws Exception {    	
    	// iterate through branch list and add rentals to according rental group at its branch
    	Iterator<Branch> itr = branchList.iterator();
    	while (itr.hasNext()) {
			Branch branch = (Branch) itr.next();
			addRentalGroup(branch);
		}
    }
    
    public void addRentalGroup(Branch branch) throws Exception {
    	// add rentals to rental group
    	Iterator<Rental> itr = branch.getRentalList().iterator();
    	while (itr.hasNext()) {
			Rental rental = (Rental) itr.next();
			Group group = getModel(rental.getModelNumber()).getGroup();
			branch.getRentalGroup().put(group, rental);
		}
    }

    // 4. Add a model
    public void addModel(String number, String name, Transmission transmission,
                         float consumption, int numDoor, Group group) throws Exception {
        // check if modelNumber exists
        for (Model model: modelList) {
            if (model.getNumber() == number) {
                String errMess = "Error: Model number is already exists. Please try another model number.\n";
                System.out.println(errMess);
                throw new Exception(errMess);
            }
        }

        // create and add a new model to modelList
        Model newModel = new Model(number, name, transmission, consumption, numDoor, group);
        modelList.add(newModel);
    }

    // 5. Add a car
    public void addCar(String regNum, String color, int year,
                       String modelNumber, String branchNumber) throws Exception {
        // check regNum
        for (Car car: carList) {
            if (car.getRegNum()==regNum) {
                String errMess = "Error: Registration number is already exists. Please try another registration number.\n";
                System.out.println(errMess);
                throw new Exception(errMess);
            }
        }

        // check if branchNumber exists
        Branch branch = getBranch(branchNumber);

        // check if modelNumber exists
        Model model = getModel(modelNumber);

        // Create and add a new car to list
        Car newCar = new Car(regNum, color, year, modelNumber, branchNumber);
        carList.add(newCar);
        branch.getCarList().add(newCar);

        // add new car to carList in model
        model.getCarList().add(newCar);
    }
    
    // 6. Add a customer
    public void addCustomer(String name, String driverLicense, String email, String phone) throws Exception {
    	
    	// check if driverLicense does not exist
    	Iterator<Customer> itr = customerList.iterator();
    	while (itr.hasNext()) {
    		Customer customer = itr.next();
    		if (customer.getDriveLicense().equals(driverLicense)) {
    			String msg = "Error: Customer already existed!";
    			System.out.println(msg);
    			throw new Exception(msg);
    		}
    	}
    	
    	// create and add new customer to list
    	customerList.add(new Customer(name, driverLicense, email, phone));
    }
    
    // 7. List cars that are available at a specified branch and belong to a specified rental group
    // 	  (do not include the cars at neighbor branches)
    public List<Car> listCar(String branchNumber, Group group) throws Exception {
    	// check if branchNumber exists
    	Branch branch =  getBranch(branchNumber);
    	
    	// return the car list
    	List<Rental> rentalList = new ArrayList<>(branch.getRentalGroup().get(group));
    	List<Car> carList = new ArrayList<>();
    	for (Rental r : rentalList) {
    		carList.add(r.getCar());
    	}
    	return carList;
    }
    
    // 8. Record the return of a car
    public void recordReturn(String rentalNumber, Date realReturnDate, String realReturnBranchNo) throws Exception {
    	// check if rentalNumber exists
    	Rental rental = getRental(rentalNumber);
    	
    	// check if realReturnBranch exists
    	Branch newBranch = getBranch(realReturnBranchNo);
    	
    	// check if the rental is not returned
    	if (rental.getStatus().equals(RentalStatus.RETURNED)) {
    		String msg = "Error: The rental has already returned!";
			System.out.println(msg);
			throw new Exception(msg);
    	}
    	
    	// update real return date, real return branch, car status, rental status
    	rental.setRealReturnBranch(realReturnBranchNo);
    	rental.setRealReturnDate(realReturnDate);
    	rental.setStatus(RentalStatus.RETURNED);
    	Car car = rental.getCar();
    	car.setStatus(Status.RETURNED);
    	if (!car.getBranchNumber().equals(realReturnBranchNo)) {
    		getBranch(car.getBranchNumber()).getCarList().remove(car);
    		newBranch.getCarList().add(car);
    		car.setBranchNumber(realReturnBranchNo);
    	}
    }
// ----------------------------- END OF ATOMIC USE CASES ------------------------------- //



// ---------------------------- ADDITION ATOMIC USE CASES ---------------------------------- //
    // 9. Add rental
	public void addRental(String pickupBranch, String returnBranch, Date pickupDate, Date returnDate,
			String modelNumber, String color, int year, String driverLicense) throws Exception {

		// check if pickupBranch exists
		Branch pickupBr = getBranch(pickupBranch);

		// check if returnBranch exists
		getBranch(returnBranch);

		// check if returnDate is bigger than pickupDate
		if (returnDate.compareTo(pickupDate) < 0) {
			String msg = "Error: Return date must be after pickup date!";
			System.out.println(msg);
			throw new Exception(msg);
		}

		// Check if customer is in Blacklist
		for (Customer customer : blacklist) {
			if (customer.getDriveLicense() == driverLicense) {
				String msg = "Error: Customer is in BLACKLIST!";
				System.out.println(msg);
				throw new Exception(msg);
			}
		}

		// check if driverLicense exists, if not, then ask info and create a new customer
		try {
			getCustomer(driverLicense);
		} catch (Exception e) {
			String msg = "Warning: New customer!";
			System.out.println(msg);
			addCustomer(null, driverLicense, null, null);
			throw new Exception(msg);
		}

		// check if model number is right
		getModel(modelNumber);

		// check if there is an available car of the model
		Car car = findCar(modelNumber, color, year, pickupBranch);

		// set status of the car to HELD and wait for the customer last decision
		car.setStatus(Status.HELD);

		// Waiting. . .

		// set status of the car to RESERVED
		car.setStatus(Status.RESERVED);

		// retrieve customer
		Customer customer = getCustomer(driverLicense);

		// Create and add a new rental to rentalList
		Rental newRental = new Rental(customer, pickupBranch, returnBranch, pickupDate, returnDate, modelNumber, car);
		rentalList.add(newRental);

		// Add new rental to rentalGroup
		Group group = getModel(newRental.getModelNumber()).getGroup();
		pickupBr.getRentalList().add(newRental);
		pickupBr.getRentalGroup().put(group, newRental);
	}

	// 10. Add a customer to blacklist
	public void addBlacklist(String driverLicense) throws Exception {
    	// check if driver License exists, retrieve customer
    	Customer customer = getCustomer(driverLicense);
    	
    	// check if driverLicense already in blacklist
    	for (Customer c : blacklist) {
    		if (c.getDriveLicense().equals(driverLicense)) {
    			String msg = "Error: Customer was already in blacklist!";
    			System.out.println(msg);
    			throw new Exception(msg);
    		}
    	}

    	// add customer to blacklist
		blacklist.add(customer);
	}

	// 11. Maintain car
	public void carMaintenance(String regNum, Status status) throws Exception {
		// check if the car exists
		Car car = getCar(regNum);

		// check if the car's status is either RETURNED or SERVICE_NEEDED
		if ((car.getStatus() != Status.RETURNED) && (car.getStatus() != Status.SERVICE_NEEDED)) {
			String msg = "Error: The car is neither RETURNED or SERVICE_NEEDED";
			System.out.println(msg);
			throw new Exception(msg);
		}

		// check if the input status is READY, SERVICE_NEEDED or REMOVED
		if ((status != Status.READY) && (status != Status.SERVICE_NEEDED) && (status != Status.REMOVED)) {
			String msg = "Error: The input status must be READY or SERVICE_NEEDED or REMOVED";
			System.out.println(msg);
			throw new Exception(msg);
		}

		// set the status of the car
		car.setStatus(status);

		// if status is REMOVED then delete car in carList, modelCarList, branchCarList
		if (status == Status.REMOVED) {
			carList.remove(car);
			getModel(car.getModelNumber()).getCarList().remove(car);
			getBranch(car.getBranchNumber()).getCarList().remove(car);
		}
	}

	public String toString(List<?> list) {
		String result = null;
		
		if (list.get(0) instanceof Branch) {
			result = String.format("%-20s | %-30s\n",
					"Branch Number", "BranchName");
			String endLine = result;
			result += new String(new char[result.length()]).replace('\0', '-') + "\n";
			
			for (Object branch : list)
				result += ((Branch)branch).toString();
			
			result += new String(new char[endLine.length()]).replace('\0', '-');	
		}
		
		else if (list.get(0) instanceof Model) {
			result = String.format("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s\n",
					"Model Number", "Model Name", "Transmission",
					"Fuel Consumption", "Number of door", "Group", "Cars list");
			String endLine = result;
			result += new String(new char[result.length()]).replace('\0', '-') + "\n";
			
			for (Object model : list)
				result += ((Model) model).toString();
			
			result += new String(new char[endLine.length()]).replace('\0', '-');
		}
		
		else if (list.get(0) instanceof Car) {
			result = String.format("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s\n",
					"Registration Number", "Color", "Year",
					"Model Number", "Branch Number", "Status");
			String endLine = result;
			result += new String(new char[result.length()]).replace('\0', '-') + "\n";
			
			for (Object car : list)
				result += ((Car) car).toString();
			
			result += new String(new char[endLine.length()]).replace('\0', '-');
		}
		
		else if (list.get(0) instanceof Customer) {
			result = String.format("%-20s | %-20s | %-20s | %-20s\n",
					"Name", "Drive License", "Email", "Phone Number");
			String endLine = result;
			result += new String(new char[result.length()]).replace('\0', '-') + "\n";
			
			for (Object customer : list)
				result += ((Customer) customer).toString();
			
			result += new String(new char[endLine.length()]).replace('\0', '-');
		}
		
		else if (list.get(0) instanceof Rental) {
			result = String.format("%-40s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-40s | %-40s\n",
					"Rental number", "Status", "Customers' Name", "Driver license",
					"Model Number", "Registration Number", "Pickup Branch", "Return Branch",
					"Pickup Date", "Return Date");
			String endLine = result;
			result += new String(new char[result.length()]).replace('\0', '-') + "\n";
			
			
			for (Object rental : list)
				result += ((Rental) rental).toString();
			
			result += new String(new char[endLine.length()]).replace('\0', '-');
		}
		
		return result;
	}
}
