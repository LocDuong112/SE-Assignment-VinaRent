import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class VinaRentSystemTest {
    @Rule
    public Timeout globalTimeout = new Timeout(2000);

    @BeforeClass
    public static void setup() {
        System.out.println("Test for VinaRentSystem");
        System.out.println(new String(new char[80]).replace('\0', '-'));
    }

    @Test
    public void addBranchTest() throws Exception {
        System.out.println("\nTesting for addBranch(branchNumber, name)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add 2 normal branch
        vrs.addBranch("Branch1","BranchName1");
        vrs.addBranch("Branch2","BranchName2");

        // Branch with same name but different branch number is also acceptable
        vrs.addBranch("Branch3","BranchName1");

        System.out.println(vrs.toString("branch"));

        System.out.println("Print out error");
        try {
            // cannot add a branch with the existed branch number, though the name is unique
            vrs.addBranch("Branch1", "BranchName4");
        } catch (Exception e) {
            System.out.println(vrs.toString("branch"));
        }
    }

    @Test
    public void makeNeighborTest() throws Exception {
        System.out.println("\nTesting for makeNeighBor(branch1, branch2)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add some normal branches
        vrs.addBranch("Branch1","BranchName1");
        vrs.addBranch("Branch2","BranchName2");
        vrs.addBranch("Branch3","BranchName3");

        // make branch 1 & 2 become neighbor
        vrs.makeNeighbor("Branch1", "Branch2");

        // 1 branch can have many neighbors
        vrs.makeNeighbor("Branch1", "Branch3");

        System.out.println(vrs.toString("branch"));

        System.out.println("Print out error");

        // make neighbor with non-exist branches
        try {
            vrs.makeNeighbor("Branch0","Branch2");
        } catch (Exception e) {
            System.out.println(vrs.toString("branch"));
        }

        // cannot add 2 neighbored branches
        try {
            vrs.makeNeighbor("Branch2", "Branch1");
        } catch (Exception e) {
            System.out.println(vrs.toString("branch"));
        }
    }

    @Test
    public void addModelTest() throws Exception {
        System.out.println("\nTesting for addModel(number, name, transmission, " +
                "consumption, numDoor, group)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add normal models
        vrs.addModel("Model1","ModelName1",Transmission.automatic,
                0.5f, 4, Group.A);
        vrs.addModel("Model2","ModelName2",Transmission.automatic,
                1.2f, 2, Group.B);

        // Different in Model Number is enough to create a new model
        vrs.addModel("Model3","ModelName1",Transmission.automatic,
                0.5f, 4, Group.A);

        System.out.println(vrs.toString("model"));

        System.out.println("Print out error");

        // cannot add a model with the existed model number
        try {
            vrs.addModel("Model1","ModelName2",Transmission.automatic,
                    2.3f, 4, Group.E);
        } catch (Exception e) {
            System.out.println(vrs.toString("model"));
        }
    }

    @Test
    public void addCarTest() throws Exception {
        System.out.println("\nTesting for addCar(regNum, color, year, " +
                "modelNumber, branchNumber)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add branches
        vrs.addBranch("branch1","BranchName1");
        vrs.addBranch("branch2","BranchName2");

        // add models
        vrs.addModel("model1","ModelName1",Transmission.automatic,
                0.5f, 4, Group.A);
        vrs.addModel("model2","ModelName2",Transmission.automatic,
                1.2f, 2, Group.B);

        // add normal cars
       vrs.addCar("regNum1", "Color1", 2000,
               "model1", "branch1");

       // 1 branch can have many cars. Ex: branch1
       vrs.addCar("regNum2", "Color2", 2009,
                "model2", "branch1");

       // 1 model can have many cars. Ex: model2
       vrs.addCar("regNum3", "Color3", 2010,
                "model2", "branch2");

       System.out.println(vrs.toString("branch"));
       System.out.println(vrs.toString("model"));
       System.out.println(vrs.toString("car"));

       System.out.println("Print out error");

        // cannot add a car with the existed registration number
       try {
           vrs.addCar("regNum1", "Color3", 1921,
                    "model1", "branch2");
       } catch (Exception e) {
           System.out.println(vrs.toString("car"));
       }

        // cannot add a car with the non-existed branch
        try {
            vrs.addCar("regNum4", "Color3", 1921,
                    "model1", "branch3");
        } catch (Exception e) {
            System.out.println(vrs.toString("car"));
        }

        // cannot add a car with the non-existed model
        try {
            vrs.addCar("regNum4", "Color3", 1921,
                    "model3", "branch2");
        } catch (Exception e) {
            System.out.println(vrs.toString("car"));
        }
    }

    @Test
    public void addCustomerTest() throws Exception {
        System.out.println("\nTesting for addCustomer(name, driverLicense, email, phone)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add normal customers
        vrs.addCustomer("Customer1", "license1", "email1", "phone1");
        vrs.addCustomer("Customer2", "license2", "email2", "phone2");

        // Different in driver license is enough to create a new customer
        vrs.addCustomer("Customer1", "license3", "email1", "phone1");

        System.out.println(vrs.toString("customer"));

        System.out.println("Print out error");

        // cannot add a customer with the existed driver license
        try {
            vrs.addCustomer("Customer3", "license1", "email3", "phone3");
        } catch (Exception e) {
            System.out.println(vrs.toString("customer"));
        }
    }

    @Test
    public void addRentalTest() throws Exception {
        System.out.println("\nTesting for addRental(pickupBranch, returnBranch, pickupDate, returnDate, " +
                "modelNumber, color, year, driverLicense)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add customers
        vrs.addCustomer("Customer1", "license1", "email1", "phone1");
        vrs.addCustomer("Customer2", "license2", "email2", "phone2");

        // add branches
        vrs.addBranch("branch1","BranchName1");
        vrs.addBranch("branch2","BranchName2");

        // add models
        vrs.addModel("model1","ModelName1",Transmission.automatic,
                0.5f, 4, Group.A);
        vrs.addModel("model2","ModelName2",Transmission.automatic,
                1.2f, 2, Group.B);

        // add cars
        vrs.addCar("regNum1", "Color1", 2000,
                "model1", "branch1");
        vrs.addCar("regNum2", "Color2", 2009,
                "model2", "branch1");

        // add rentals
    }
}
