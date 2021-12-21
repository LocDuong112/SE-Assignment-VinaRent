import java.util.ArrayList;
import java.util.List;

public class Branch {
    private String branchNumber;
    private List<Branch> neighborList;
    private List<Car> carList;
    private String name;

    public Branch(String branchNumber) {
        this.branchNumber = branchNumber;
        this.carList = new ArrayList<>();
        this.neighborList = new ArrayList<>();
    }
    
    public Branch(String branchNumber, String name) {
		this(branchNumber);
		this.name = name;
	}

    public String getBranchNumber() {
        return branchNumber;
    }

    public List<Branch> getNeighborList() {
        return neighborList;
    }

    public List<Car> getCarlList() {
        return carList;
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
