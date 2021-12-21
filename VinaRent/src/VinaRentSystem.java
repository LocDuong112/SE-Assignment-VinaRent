import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class VinaRentSystem {
    private List<Branch> branchList;
    private List<Model> modelList;
    private List<Car> carList;
    private List<Customer> customerList;
    private List<Rental> rentalList;

    public VinaRentSystem() {
        this.branchList = new ArrayList<>();
        this.modelList = new ArrayList<>();
        this.carList = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.rentalList = new ArrayList<>();
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
    
 // ------------------------------ END OF PRIVATE METHODS ------------------------------- //
    
    
    
    // 1. Add a branch
    public void addBranch(String branchNumber, String name) throws Exception {
        // check if the branchNumber is duplicated
        for (Branch branch : branchList) {
            if (branch.getBranchNumber() == branchNumber) {
                String errMess = "Branch number is already assigned. Please replace with a new branch number.\n";
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

    // 4. Add a model
    public void addModel(String number, String name, Transmission transmission,
                         float consumption, int numDoor, Group group) throws Exception {
        // check if modelNumber exists
        for (Model model: modelList) {
            if (model.getNumber() == number) {
                String errMess = "Model number is already exists. Please try another model number.\n";
                throw new Exception(errMess);
            }
        }

        // create and add a new model to modelList
        Model newModel = new Model(number, name, transmission, consumption, numDoor, group);
        modelList.add(newModel);
    }

    // 5. Add a car
    public void addCar(int regNum, String color, int year,
                       String modelNumber, String branchNumber) throws Exception {
        // check regNum
        for (Car car: carList) {
            if (car.getRegNum()==regNum) {
                String errMess = "Registration number is already exists. Please try another registration number.\n";
                throw new Exception(errMess);
            }
        }

        // check if branchNumber exists
        Branch branch = getBranch(branchNumber);

        // check if branchNumber exists
        Model model = getModel(modelNumber);

        // Create and add a new car to list
        Car newCar = new Car(regNum, color, year, modelNumber, branchNumber);
        carList.add(newCar);
        branch.getCarlList().add(newCar);

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
//    public Car listCar(String branchNumber, Group group) throws Exception {
//    	// check if branchNumber exists
//    	getBranch(branchNumber);
//    	
//    	// return the rentals 
//    }
    
    // 8. Record the return of a car
    public void recordReturn(String rentalNumber, Date realReturnDate) throws Exception {
    	// check if rentalNumber exists
    	Rental rental = getRental(rentalNumber);
    	
    	
    }
}
