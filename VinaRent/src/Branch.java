import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Branch {
    private String branchNumber;
    private List<Branch> neighborList;
    private List<Car> carList;
    private String name;
    private HashMap<Group, List<Rental>> rentalGroup;
    private List<Rental> rentalList;

    public Branch(String branchNumber) {
        this.branchNumber = branchNumber;
        this.carList = new ArrayList<>();
        this.neighborList = new ArrayList<>();
        this.rentalGroup = new HashMap<>();
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
    
    public HashMap<Group, List<Rental>> getRentalGroup() {
		return rentalGroup;
	}
    
    public List<Rental> getRentalList() {
		return rentalList;
	}

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public void setModelList(List<Car> carList) {
        this.carList = carList;
    }

    public void setNeighborList(List<Branch> neighborList) {
        this.neighborList = neighborList;
    }
}
