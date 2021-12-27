import java.util.ArrayList;
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
    
    @Override
    public String toString() {
    	String result = "";
        result = String.format("%-20s | %-30s%n", branchNumber, name);
    	return result;
    }
    
    public String toString(List<?> list) {
    	String result = "";
    	if (list.get(0) instanceof Branch) {
    		result += String.format("List of neighbors of %s (%s)%n", name, branchNumber);
			result += new String(new char[result.length()]).replace('\0', '-') + String.format("%n", null);
    		for (Object b : list) {
    			result += String.format("%s", ((Branch)b).toString());
    		}
    	}
    	else if (list.get(0) instanceof Car) {
    		result += String.format("List of cars of %s (%s)%n", name, branchNumber);
			result += new String(new char[result.length()]).replace('\0', '-') + String.format("%n", null);
    		for (Object b : list) {
    			result += String.format("%s", ((Car)b).toString());
    		}
    	}
    	else if (list.get(0) instanceof Rental) {
    		result += String.format("List of rentals of %s (%s)%n", name, branchNumber);
			result += new String(new char[result.length()]).replace('\0', '-') + String.format("%n", null);
    		for (Object b : list) {
    			result += String.format("%s", ((Rental)b).toString());
    		}
    	}
    	return result;
    }
    
    public String toString(Multimap<Group, Rental> rentalGroup) {
    	String result = "";
    	Group group[] = Group.values();
    	result += String.format("%n%-20s | %-20s | %-20s%n", "Branch", "Group", "Rentals");
    	result += new String(new char[result.length()]).replace('\0', '-') + String.format("%n", null);
    	for (Group g : group) {
			if (rentalGroup.get(g) != null) {
				for (Rental r : rentalGroup.get(g)) {
					result += String.format("%-20s | %-20s | %-20s",branchNumber, g, r.toString()); 
				}
			}
    	}
    	return result;
    }
}
