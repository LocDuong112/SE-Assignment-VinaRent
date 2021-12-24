import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.rules.Timeout;

public class VinaRentSystemTest {
    @Rule
    public Timeout globalTimeout = new Timeout(2000);

    @BeforeClass
    public static void setup() {
        System.out.println("Test for VinaRentSystem");
//        System.out.println(new String(new char[80]).replace('\0', '/'));
    }

    @After
    public void after() {
    	System.out.println(new String(new char[80]).replace('\0', '/'));
    }
    
    @Before
    public void before() {
    	System.out.println("\n");
    	System.out.println(new String(new char[80]).replace('\0', '/'));
    }
    

// ---------------------------- ATOMIC USE CASES TEST ---------------------------------- //
    @Test
    // 1. add a branch test
    public void addBranchTest() throws Exception {
        System.out.println("Testing for addBranch(branchNumber, name)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add 2 normal branch
        vrs.addBranch("D-1"		,	"District 1 Branch");
        vrs.addBranch("D-TD-1"	,	"Thu Duc District Branch");

        // Branch with same name but different branch number is also acceptable
        vrs.addBranch("D-TD-2"	,	"Thu Duc District Branch");

        System.out.println(vrs.toString(vrs.getBranchList()));

        System.out.println("Print out error");
        try {
            // cannot add a branch with the existed branch number, though the name is unique
            vrs.addBranch("D-1", "District 5 Branch");
        } catch (Exception e) {
        	System.out.println("The list after encounting the error\n");
            System.out.println(vrs.toString(vrs.getBranchList()));
        }
    }
    
    @Test
    // 2. make neighbor test
    public void makeNeighborTest() throws Exception {
        System.out.println("Testing for makeNeighBor(branch1, branch2)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add some normal branches
        vrs.addBranch("D-1"		,	"Dist. 1 Branch");
        vrs.addBranch("D-5"		,	"Dist. 5 Branch");
        vrs.addBranch("D-TD"	,	"Thu Duc Dist. Branch");

        // make branch D-1 & D-5 become neighbor
        vrs.makeNeighbor("D-1", "D-5");

        // 1 branch can have many neighbors
        vrs.makeNeighbor("D-1", "D-TD");

        System.out.println(vrs.toString(vrs.getBranchList()) + "\n");

        System.out.println("Print out error");

        // make neighbor with non-exist branches
        try {
        	System.out.println("Try to make neighbor with non-existed branch (D-7)");
            vrs.makeNeighbor("D-7"	,	"Dist. 7 Branch");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getBranchList()) + "\n");
        }

        // cannot add 2 neighbored branches
        try {
        	System.out.println("Try to make neighbor with already made neighbor branches (D-1 and D-TD)");
            vrs.makeNeighbor("D-1", "D-TD");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getBranchList()));
        }
    }

    @Test
    // 3. Add a rental group test
    public void addRentalGroupTest() throws Exception {
        System.out.println("\nTesting for addRentalGroup()");

        VinaRentSystem vrs = new VinaRentSystem();

        // add customers
        vrs.addCustomer("Customer1", "license1", "email1", "phone1");
        vrs.addCustomer("Customer2", "license2", "email2", "phone2");

        // add branches
        vrs.addBranch("branch1","BranchName1");
        vrs.addBranch("branch2","BranchName2");

        // make branches become neighbor
        vrs.makeNeighbor("branch1", "branch2");

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
                "model2", "branch2");

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
        vrs.addRental("branch1","branch1", date1, date1,
                "model2", "Color2",2009,"license2");
        vrs.addRental("branch2","branch1", date2, date2,
                "model2", "Color1.1",2002,"license2");

        System.out.println(vrs.toString(vrs.getBranchList()));

        System.out.println("No error cases can be created!");
    }

    @Test
    // 4. Add a model test
    public void addModelTest() throws Exception {
        System.out.println("Testing for addModel(number, name, transmission, " +
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

        System.out.println(vrs.toString(vrs.getModelList()));

        System.out.println("Print out error");

        // cannot add a model with the existed model number
        try {
            vrs.addModel("Model1","ModelName2",Transmission.automatic,
                    2.3f, 4, Group.E);
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getModelList()));
        }
    }

    @Test
    // 5. Add a car test
    public void addCarTest() throws Exception {
        System.out.println("Testing for addCar(regNum, color, year, " +
                "modelNumber, branchNumber)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add branches
        vrs.addBranch("D-5"		,	"Dist. 5 Branch");
        vrs.addBranch("D-TD"	,	"Thu Duc Dist. Branch");

        // add models
        vrs.addModel("HONDA100","Honda Civic",Transmission.automatic,
                0.5f, 4, Group.A);
        vrs.addModel("TOYOTA111","Toyota Tundra",Transmission.automatic,
                1.2f, 2, Group.B);

        // add normal cars
       vrs.addCar("ASDF-1234", "red", 2000,
               "HONDA100", "D-5");

       // 1 branch can have many cars. Ex: D-5
       vrs.addCar("QWERTY-2345", "blue", 2009,
                "TOYOTA111", "D-5");

       // 1 model can have many cars. Ex: TOYOTA111
       vrs.addCar("ZXCV-7890", "white", 2010,
                "TOYOTA111", "D-TD");

       System.out.println(vrs.toString(vrs.getBranchList()) + "\n");
       System.out.println(vrs.toString(vrs.getModelList()) + "\n");
       System.out.println(vrs.toString(vrs.getCarList()) + "\n");

       System.out.println("Print out error");

        // cannot add a car with the existed registration number
       try {
    	   System.out.println("Try to add car with existed registration number (QWERTY-2345)");
           vrs.addCar("QWERTY-2345", "red", 1921,
                    "HONDA100", "D-TD");
       } catch (Exception e) {
           System.out.println(vrs.toString(vrs.getCarList()) + "\n");
       }

        // cannot add a car with the non-existed branch
        try {
        	System.out.println("Try to add car to non-existed branch (D-1)");
            vrs.addCar("UIOP-4782", "mint", 1921,
                    "HONDA100", "D-1");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getCarList()) + "\n");
        }

        // cannot add a car with the non-existed model
        try {
        	System.out.println("Try to add car with non-existed model (VINFAST100)");
            vrs.addCar("CVBN-9289", "yellow", 1921,
                    "VINFAST100", "D-5");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getCarList()));
        }
    }

    @Test
    // 6. Add a customer test
    public void addCustomerTest() throws Exception {
        System.out.println("Testing for addCustomer(name, driverLicense, email, phone)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add normal customers
        vrs.addCustomer("Kevin", "KE09124", "kevin@something.com", "09696969");
        vrs.addCustomer("Karen", "KA19386", "karen@something.com", "09420420");

        // Different in driver license is enough to create a new customer
        vrs.addCustomer("Karen", "KA01242", "karen@something.com", "09872345");

        System.out.println(vrs.toString(vrs.getCustomerList()) + "\n");

        System.out.println("Print out error");

        // cannot add a customer with the existed driver license
        try {
        	System.out.println("Try to add customer with existed driver license (John/KE09124)");
            vrs.addCustomer("John", "KE09124", "john@something.com", "09246577");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getCustomerList()));
        }
    }

    @Test
    // 7. List cars test
    public void listCarTest() throws Exception {
        System.out.println("\nTesting for listCar(branchNumber, group)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add customers
        vrs.addCustomer("Customer1", "license1", "email1", "phone1");
        vrs.addCustomer("Customer2", "license2", "email2", "phone2");

        // add branches
        vrs.addBranch("branch1","BranchName1");
        vrs.addBranch("branch2","BranchName2");

        // make branches become neighbor
        vrs.makeNeighbor("branch1", "branch2");

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
                "model2", "branch2");

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
        vrs.addRental("branch1","branch1", date1, date1,
                "model2", "Color2",2009,"license2");
        vrs.addRental("branch2","branch1", date2, date2,
                "model2", "Color1.1",2002,"license2");

        System.out.println(vrs.toString(vrs.getBranchList()));

        // Print the car that is in a rental of branch 1
        System.out.println("Car of group A and in rental of branch1 is: ");
        for (Car car : vrs.listCar("branch1", Group.A)) {
            System.out.print(car.getRegNum());
        }
        System.out.println("\n");

        System.out.println("Print out error");

        // non-existed branch number will cause error
        try {
            vrs.listCar("branch3", Group.A);
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getBranchList()));
        }
    }

    @Test
    // 8. Record return test
    public void recordReturnTest() throws Exception {
        System.out.println("\nTesting for recordReturn(rentalNumber, realReturnDate, realReturnBranchNo)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add customers
        vrs.addCustomer("Customer1", "license1", "email1", "phone1");

        // add branches
        vrs.addBranch("branch1","BranchName1");
        vrs.addBranch("branch2","BranchName2");

        // make branches become neighbor
        vrs.makeNeighbor("branch1", "branch2");

        // add models
        vrs.addModel("model1","ModelName1",Transmission.automatic,
                0.5f, 4, Group.A);

        // add cars
        vrs.addCar("regNum1", "Color1", 2000,
                "model1", "branch1");

        // add string days
        String sDate1 = "31-12-2020 23:37:50";
        String sDate2 = "12-11-1998 11:49:12";
        String sDate3 = "12-12-2006 14:15:16";
        String sDate4 = "27-03-2005 01:02:03";

        // add date formatters
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // add dates
        Date date1 = formatter.parse(sDate1);
        Date date2 = formatter.parse(sDate2);
        Date date3 = formatter.parse(sDate3);
        Date date4 = formatter.parse(sDate4);

        // add rentals
        vrs.addRental("branch1","branch2", date2, date1,
                "model1", "Color1",2000,"license1");
        String rentalNumber1 = "license1-Thu Nov 12 11:49:12 ICT 1998";
        System.out.println(vrs.toString(vrs.getBranchList()));

        vrs.recordReturn(rentalNumber1, date3, "branch2");
        System.out.println(vrs.toString(vrs.getBranchList()));

        System.out.println("Print out error");

        // cannot record return for a returned rental
        try {
            vrs.recordReturn(rentalNumber1, date3, "branch2");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getBranchList()));
        }
    }
// ---------------------------- END OF ATOMIC USE CASES TEST ---------------------------------- //



// ---------------------------- ADDITION ATOMIC USE CASES TEST ---------------------------------- //
    @Test
    // 1. Add a rental test
    public void addRentalTest() throws Exception {
        System.out.println("Testing for addRental(pickupBranch, returnBranch, pickupDate, returnDate, " +
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
        vrs.addRental("branch1","branch1", date1, date1,
                "model2", "Color2",2009,"license1");

        System.out.println(vrs.toString(vrs.getRentalList()));

        System.out.println("Print out error");

        // cannot add a rental with the non-existed branch
        try {
            vrs.addRental("branch4","branch1", date2, date2,
                    "model2", "Color2.1",2003,"license1");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getRentalList()));
        }

        // cannot add a rental with returnDate before pickupDate
        try {
            vrs.addRental("branch1","branch1", date1, date2,
                    "model2", "Color2.1",2003,"license1");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getRentalList()));
        }

        // cannot add a rental with new customer, ask info of new customer
        try {
            vrs.addRental("branch1","branch1", date2, date2,
                    "model2", "Color2.1",2003,"license4");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getRentalList()));
        }

        // cannot add a rental with a blacklisted customer
        try {
            vrs.addRental("branch1","branch1", date2, date2,
                    "model2", "Color2.1",2003,"license3");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getRentalList()));
        }

        // cannot add a rental with a non-existed model
        try {
            vrs.addRental("branch1","branch1", date2, date2,
                    "model3", "Color2.1",2003,"license1");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getRentalList()));
        }

        // cannot add a rental with a non-existed car
        try {
            vrs.addRental("branch1","branch1", date2, date2,
                    "model2", "Color2.1",2009,"license1");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getRentalList()));
        }
    }

    @Test
    // 2. Add a customer to blacklist test
    public void addBlacklistTest() throws Exception {
        System.out.println("\nTesting for addBlacklist(driverLicense)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add normal customers
        vrs.addCustomer("Customer1", "license1", "email1", "phone1");
        vrs.addCustomer("Customer2", "license2", "email2", "phone2");

        System.out.println(vrs.toString(vrs.getCustomerList()));

        // Blacklisted customer 1
        vrs.addBlacklist("license1");
        System.out.println(vrs.toString(vrs.getCustomerList()));

        System.out.println("Print out error");

        // cannot blacklisted a customer with the non-existed driver license
        try {
            vrs.addBlacklist("license3");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getCustomerList()));
        }
    }

// ------------------------ END OF ADDITION ATOMIC USE CASES TEST --------------------------------- //
}
