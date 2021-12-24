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

    public String toString(String listName) {
        String result = "";
        result = String.format("%-20s | %-20s | ", branchNumber, name);

        switch (listName) {
            case "branch-neighbor":
                for (Branch branch : neighborList) {
                    result += String.format("%s, ", branch.getBranchNumber());
                }
                break;

            case "branch-car":
                for (Car car : carList) {
                    result += String.format("%s, ", car.getRegNum());
                }
                break;

            case "branch-rental":
                for (Group group : rentalGroup.keySet()) {
                    result += String.format("(Group %s): ", group.toString());
                    for (Rental rental : rentalGroup.get(group)) {
                        result += String.format("%s, ", rental.getNumber());
                    }
                }
                break;
        }
        result += "\n";

        return result;
    }
}
