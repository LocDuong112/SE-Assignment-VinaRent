import java.util.List;

public class Branch {
    private String branchNumber;
    private List<Branch> neighborList;
    private List<Model> modelList;

    public Branch(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public List<Branch> getNeighborList() {
        return neighborList;
    }

    public List<Model> getModelList() {
        return modelList;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }

    public void setNeighborList(List<Branch> neighborList) {
        this.neighborList = neighborList;
    }
}
