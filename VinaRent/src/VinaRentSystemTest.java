import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        vrs.addCustomer("Customer3", "license3", "email3", "phone3");

        // add customer 3 to blacklist
        vrs.addBlacklist("license3");

        // add branches
        vrs.addBranch("branch1","BranchName1");
        vrs.addBranch("branch2","BranchName2");
        vrs.addBranch("branch3","BranchName3");

        // make branches become neighbor
        vrs.makeNeighbor("branch1", "branch2");
        vrs.makeNeighbor("branch1", "branch3");

        // add models
        vrs.addModel("model1","ModelName1",Transmission.automatic,
                0.5f, 4, Group.A);
        vrs.addModel("model2","ModelName2",Transmission.automatic,
                1.2f, 2, Group.B);

        // add cars
        vrs.addCar("regNum1", "Color1", 2000,
                "model1", "branch1");

        vrs.addCar("regNum1.1", "Color1.1", 2002,
                "model2", "branch1");
        vrs.addCar("regNum2", "Color2", 2009,
                "model2", "branch2");
        vrs.addCar("regNum2.1", "Color2.1", 2003,
                "model2", "branch3");

        // add string days
        String sDate1 = "31-12-2020 23:37:50";
        String sDate2 = "12-11-1998 11:49:12";

        // add date formatters
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // add dates
        Date date1 = formatter.parse(sDate1);
        Date date2 = formatter.parse(sDate2);

        // add rentals
        vrs.addRental("branch1","branch2", date2, date1,
                "model1", "Color1",2000,"license1");

        // can add a rental with the same pickupBranch, returnBranch or the same
        // pickupDate and returnDate.
        // 1 customer can have many rentals.
        // System can find a car in neighbor branches of pickupBranch (the car here is in branch2).
        vrs.addRental("branch1","branch1", date2, date2,
                "model2", "Color2",2009,"license1");

        System.out.println(vrs.toString("rental"));

        System.out.println("Print out error");

        // cannot add a rental with the non-existed branch
        try {
            vrs.addRental("branch4","branch1", date2, date2,
                    "model2", "Color2.1",2003,"license1");
        } catch (Exception e) {
            System.out.println(vrs.toString("rental"));
        }

        // cannot add a rental with returnDate before pickupDate
        try {
            vrs.addRental("branch1","branch1", date1, date2,
                    "model2", "Color2.1",2003,"license1");
        } catch (Exception e) {
            System.out.println(vrs.toString("rental"));
        }

        // cannot add a rental with new customer, ask info of new customer
        try {
            vrs.addRental("branch1","branch1", date2, date2,
                    "model2", "Color2.1",2003,"license4");
        } catch (Exception e) {
            System.out.println(vrs.toString("rental"));
        }

        // cannot add a rental with a blacklisted customer
        try {
            vrs.addRental("branch1","branch1", date2, date2,
                    "model2", "Color2.1",2003,"license3");
        } catch (Exception e) {
            System.out.println(vrs.toString("rental"));
        }

        // cannot add a rental with a non-existed model
        try {
            vrs.addRental("branch1","branch1", date2, date2,
                    "model3", "Color2.1",2003,"license1");
        } catch (Exception e) {
            System.out.println(vrs.toString("rental"));
        }

        // cannot add a rental with a non-existed car
        try {
            vrs.addRental("branch1","branch1", date2, date2,
                    "model2", "Color2.1",2009,"license1");
        } catch (Exception e) {
            System.out.println(vrs.toString("rental"));
        }
    }
}
