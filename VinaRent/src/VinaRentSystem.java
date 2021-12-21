import java.util.ArrayList;
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

    // Add a branch
    public void addBranch(String branchNumber) throws Exception {
        // check if the branchNumber is duplicated
        for (Branch branch:branchList) {
            if (branch.getBranchNumber() == branchNumber) {
                String errMess = "Branch number is already assigned. Please replace with a new branch number.\n";
                throw new Exception(errMess);
            }
        }

        // Create and add a new branch
        Branch newBranch = new Branch(branchNumber);
        branchList.add(newBranch);
    }

    // Make a pair of branches neighbors to each other
    public void makeNeighbor (String branch1, String branch2) throws Exception {
        // check if branch 1 and branch 2 exist
        boolean checkBranch1 = false, checkBranch2 = false;
        for (Branch branch:branchList) {
            if (branch.getBranchNumber() == branch1) {
                checkBranch1 = true;
            }
            if (branch.getBranchNumber() == branch2) {
                checkBranch2 = true;
            }
        }
        if (checkBranch1 && checkBranch2) {
            String errMess = "Branches don't exists.\n";
            throw new Exception(errMess);
        }

        // check if branch 1 and the branch 2 are not already neighbor
        for (Branch branch:branchList) {
            if (branch.getBranchNumber() == branch1) {
                boolean checkNeighbor = false;
                for (Branch neighbor:branch.getNeighborList()) {
                    if (neighbor.getBranchNumber() == branch2) {
                        checkNeighbor = true;
                        break;
                    }
                }
                if (checkNeighbor) {
                    String errMess = "The branches are already neighbor.\n";
                    throw new Exception(errMess);
                }
            }
        }

        // add neighbor branch to this branch’s neighborList
        for (Branch b1:branchList) {
            for (Branch b2:branchList) {
                if ((b1.getBranchNumber()==branch1) &&
                        (b2.getBranchNumber()==branch2)) {
                    List<Branch> neighborTmp;
                    neighborTmp = b1.getNeighborList();
                    neighborTmp.add(b2);
                    b1.setNeighborList(neighborTmp);

                    neighborTmp = b2.getNeighborList();
                    neighborTmp.add(b1);
                    b1.setNeighborList(neighborTmp);
                }
            }
        }
    }

    // Add a model
    public void addModel(String number, String name, Transmission transmission,
                         float consumption, int numDoor) throws Exception {
        // check if modelNumber exists
        for (Model model: modelList) {
            if (model.getNumber() == number) {
                String errMess = "Model number is already exists. Please try another model number.\n";
                throw new Exception(errMess);
            }
        }

        // create and add a new model to modelList
        Model newModel = new Model(number, name, transmission, consumption, numDoor);
        modelList.add(newModel);
    }

    // Add a car
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
        boolean checkBranch = false;
        for (Branch branch: branchList) {
            if (branch.getBranchNumber() == branchNumber) {
                checkBranch = true;
                break;
            }
        }
        if (!checkBranch) {
            String errMess = "Branch doesn't exists.\n";
            throw new Exception(errMess);
        }

        // check if branchNumber exists
        boolean checkModel = false;
        for (Model model: modelList) {
            if (model.getNumber()==modelNumber) {
                checkModel = true;
                break;
            }
        }
        if (!checkModel) {
            String errMess = "Model doesn't exists.\n";
            throw new Exception(errMess);
        }

        // Create and add a new car to list
        Car newCar = new Car(regNum, color, year, modelNumber, branchNumber);
        carList.add(newCar);

        // add new car to carList in model
        for (Model model: modelList) {
            if (model.getNumber() == modelNumber) {
                List<Car> carTmp;
                carTmp = model.getCarList();
                carTmp.add(newCar);
                model.setCarList(carTmp);
            }
        }
    }
}
