import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Branch {
    private String branchNumber;
    private List<Branch> neighborList;
    private List<Car> carList;
    private String name;
    private Multimap<Group, Rental> rentalGroup;
    private List<Rental> rentalList;

    public Branch(String branchNumber) {
        this.branchNumber = branchNumber;
        this.carList = new ArrayList<>();
        this.neighborList = new ArrayList<>();
        this.rentalGroup = new Multimap<>();
        this.rentalList = new ArrayList<>();
    }
    
    public Branch(String branchNumber, String name) {
		this(branchNumber);
		this.name = name;
	}

    public String getBranchNumber() {
        return branchNumber;
    }
    
    public String getName() {
		return name;
	}

    public List<Branch> getNeighborList() {
        return neighborList;
    }

    public List<Car> getCarList() {
        return carList;
    }
    
    public Multimap<Group, Rental> getRentalGroup() {
		return rentalGroup;
	}
    
    public List<Rental> getRentalList() {
		return rentalList;
	}

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public void setNeighborList(List<Branch> neighborList) {
        this.neighborList = neighborList;
    }

    public String toString() {
        String result = null;
        result = String.format("%-20s | %-30s | %-20s | %-20s", "Branch number", "Branch name", "Neighbors", "Car list");
        result += String.format("%-20s | %-30s | ", this.branchNumber, this.name);
        int listSize = this.carList.size();
        if (this.neighborList.size() > listSize)
        	listSize = this.neighborList.size();
        
        for (int i=0; i<listSize; i++) {
        	
        }

        return result;
    }
}
