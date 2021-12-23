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
    	
    	String msg = "Error: No rental found!";
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
    
    
    
// --------------------------------- ATOMIC USE CASES ---------------------------------- // 
    // 1. Add a branch
    public void addBranch(String branchNumber, String name) throws Exception {
        // check if the branchNumber is duplicated
        for (Branch branch : branchList) {
            if (branch.getBranchNumber() == branchNumber) {
                String errMess = "Branch number is already assigned. Please replace with a new branch number.\n";
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

        // add neighbor branch to this branch’s neighborList
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
                String errMess = "Model number is already exists. Please try another model number.\n";
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
                String errMess = "Registration number is already exists. Please try another registration number.\n";
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
    
    
    // BP1. Add rental
	public void addRental(String pickupBranch, String returnBranch, Date pickupDate, Date returnDate,
			String modelNumber, String color, int year, String driverLicense) throws Exception {

		// check if pickupBranch exists
		Branch pickupBr = getBranch(pickupBranch);

		// check if returnBranch exists
		getBranch(returnBranch);

		// check if driverLicense exists, if not, then ask info and create a new customer
		try {
			getCustomer(driverLicense);
		} catch (Exception e) {
			addCustomer(null, driverLicense, null, null);
		}

		// Check if customer is in Blacklist
		Customer customer = getCustomer(driverLicense);
		if (blacklist.contains(customer)) {
			String msg = "The customer is in BLACKLIST";
			System.out.println(msg);
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

		// Create and add a new rental to rentalList
		Rental newRental = new Rental(customer, pickupBranch, returnBranch, pickupDate, returnDate, modelNumber, car);
		rentalList.add(newRental);

		// Add new rental to rentalGroup
		Group group = getModel(newRental.getModelNumber()).getGroup();
		pickupBr.getRentalGroup().put(group, newRental);
	}

	public String toString(String listName) {
		String result = "";
		switch (listName) {
			// Branch list
			case "branch":
				result = String.format("%-20s | %-20s | %-20s | %-20s\n",
						"Branch Number", "BranchName", "Neighbor Branches", "Cars list");
				result += new String(new char[result.length()]).replace('\0', '-') + "\n";
				for (Branch branch : branchList)
					result += branch.toString();
				break;

			// Model list
			case "model":
				result = String.format("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s\n",
						"Model Number", "Model Name", "Transmission",
						"Fuel Consumption", "Number of door", "Group", "Cars list");
				result += new String(new char[result.length()]).replace('\0', '-') + "\n";
				for (Model model : modelList)
					result += model.toString();
				break;

			// Car list
			case "car":
				result = String.format("%-20s | %-20s | %-20s | %-20s | %-20s\n",
						"Registration Number", "Color", "Year",
						"Model Number", "Branch Number");
				result += new String(new char[result.length()]).replace('\0', '-') + "\n";
				for (Car car : carList) {
					result += car.toString();
				}
				break;

			// Customer list
			case "customer":
				result = String.format("%-20s | %-20s | %-20s | %-20s\n",
						"Name", "Drive License", "Email", "Phone Number");
				result += new String(new char[result.length()]).replace('\0', '-') + "\n";
				for (Customer customer : customerList) {
					result += customer.toString();
				}
				break;

			// Rental list
			case "rental":
				result = String.format("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s\n",
						"Number", "Status", "Customers' Name", "Customers' Drive License",
						"Model Number", "Car's Registration Number", "Pickup Branch", "Return Branch",
						"Pickup Date", "Return Date");
				result += new String(new char[result.length()]).replace('\0', '-') + "\n";
				for (Rental rental : rentalList) {
					result += rental.toString();
				}
				break;
		}
		return result;
	}
}
