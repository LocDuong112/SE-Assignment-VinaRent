import java.util.List;

public class Branch {
    private int branchNumber;
    private List<Branch> neighborList;
    private List<Model> modelList;

    public Branch(int branchNumber) {
        this.branchNumber = branchNumber;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public List<Branch> getNeighborList() {
        return neighborList;
    }

    public List<Model> getModelList() {
        return modelList;
    }

    public void setBranchNumber(int branchNumber) {
        this.branchNumber = branchNumber;
    }

    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }

    public void setNeighborList(List<Branch> neighborList) {
        this.neighborList = neighborList;
    }
}
