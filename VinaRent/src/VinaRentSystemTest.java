import java.awt.desktop.ScreenSleepEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
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
        System.out.println("1. Testing for addBranch(branchNumber, name)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add 2 normal branch
        System.out.println("Normal cases");
        vrs.addBranch("D-1"		,	"District 1 Branch");
        vrs.addBranch("D-TD-1"	,	"Thu Duc District Branch");

        // Branch with same name but different branch number is also acceptable
        vrs.addBranch("D-TD-2"	,	"Thu Duc District Branch");

        System.out.println(vrs.toString(vrs.getBranchList()) + "\n");

        System.out.println("Print out error");
        try {
            // cannot add a branch with the existed branch number, though the name is unique
        	System.out.println("Try to add branch with existed number (D-1)");
            vrs.addBranch("D-1", "District 5 Branch");
        } catch (Exception e) {
        	System.out.println("The list after encounting the error");
            System.out.println(vrs.toString(vrs.getBranchList()));
        }
    }
    
    @Test
    // 2. make neighbor test
    public void makeNeighborTest() throws Exception {
        System.out.println("2. Testing for makeNeighBor(branch1, branch2)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add some normal branches
        vrs.addBranch("D-1"		,	"Dist. 1 Branch");
        vrs.addBranch("D-5"		,	"Dist. 5 Branch");
        vrs.addBranch("D-TD"	,	"Thu Duc Dist. Branch");

        // make branch D-1 & D-5 become neighbor
        System.out.println("Normal case: Try to make D-1 and D-TD becomes neighbor.");
        vrs.makeNeighbor("D-1", "D-5");

        // 1 branch can have many neighbors
        vrs.makeNeighbor("D-1", "D-TD");

        Iterator<Branch> itr = vrs.getBranchList().iterator();
        while (itr.hasNext()) {
			Branch branch = (Branch) itr.next();
			System.out.println(branch.toString(branch.getNeighborList()));
		}

        System.out.println("Print out error");

        // make neighbor with non-exist branches
        try {
        	System.out.println("Try to make neighbor with non-existed branch (D-7)");
            vrs.makeNeighbor("D-7"	,	"Dist. 7 Branch");
        } catch (Exception e) {
        	itr = vrs.getBranchList().iterator();
        	while (itr.hasNext()) {
				Branch branch = (Branch) itr.next();
				System.out.println(branch.toString(branch.getNeighborList()));
			}
        }

        // cannot add 2 neighbored branches
        try {
        	System.out.println("Try to make neighbor with already made neighbor branches (D-1 and D-TD)");
            vrs.makeNeighbor("D-1", "D-TD");
        } catch (Exception e) {
        	itr = vrs.getBranchList().iterator();
        	while (itr.hasNext()) {
				Branch branch = (Branch) itr.next();
				System.out.println(branch.toString(branch.getNeighborList()));
			}
        }
    }

    @Test
    // 3. Add a rental group test
    public void addRentalGroupTest() throws Exception {
        System.out.println("3. Testing for addRentalGroup()");

        VinaRentSystem vrs = new VinaRentSystem();

        // add customers
        vrs.addCustomer("Karen", "KA23984", "karen@something.com", "0923543426");
        vrs.addCustomer("Kevin", "KE13095", "kevin@something.com", "0912355468");

        // add branches
        vrs.addBranch("D-1"		,	"Dist. 1 Branch");
        vrs.addBranch("D-5"		,	"Dist. 5 Branch");

        // make branches become neighbor
        vrs.makeNeighbor("D-1", "D-5");

        // add models
        vrs.addModel("HONDA100","Honda Civic",Transmission.automatic,
                0.5f, 4, Group.A);
        vrs.addModel("TOYOTA100","Toyota Camry",Transmission.automatic,
                1.2f, 2, Group.B);

        // add cars
        vrs.addCar("IUEG-3646", "red", 2000,
                "HONDA100", "D-1");
        vrs.addCar("EOIV-0877", "green", 2002,
                "TOYOTA100", "D-1");
        vrs.addCar("ONIG-9908", "blue", 2009,
                "HONDA100", "D-5");
        vrs.addCar("UTVY-7896", "white", 2003,
                "TOYOTA100", "D-5");

        // add string days
        String sDate1 = "31-12-2021 23:37:50";
        String sDate2 = "10-1-2022 11:49:12";

        // add date formatters
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // add dates
        Date date1 = formatter.parse(sDate1);
        Date date2 = formatter.parse(sDate2);

        // add rentals
        vrs.getRentalList().clear();
        vrs.addRental("D-1","D-1", date1, date2,
                "HONDA100", "red",2000,"KA23984");
        vrs.addRental("D-5","D-5", date1, date2,
                "TOYOTA100", "white",2003,"KA23984");
        vrs.addRental("D-1","D-1", date1, date2,
                "HONDA100", "blue",2009,"KE13095");

//        System.out.println(vrs.toString(vrs.getRentalList()));
        for (Branch b : vrs.getBranchList())
        	System.out.println(b.toString(b.getRentalList()));
        Iterator<Branch> itr = vrs.getBranchList().iterator();
        while (itr.hasNext()) {
			Branch branch = (Branch) itr.next();
			System.out.println(branch.toString(branch.getRentalGroup()));
		}

        System.out.println("No error cases can be created!");
    }

    @Test
    // 4. Add a model test
    public void addModelTest() throws Exception {
        System.out.println("4. Testing for addModel(number, name, transmission, " +
                "consumption, numDoor, group)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add normal models
        System.out.println("Normal case: add normal models");
        vrs.addModel("HONDA100","Honda Civic",Transmission.automatic,
                0.5f, 4, Group.A);
        vrs.addModel("TOYOTA100","Toyota Camry",Transmission.automatic,
                1.2f, 2, Group.A);

        // Different in Model Number is enough to create a new model
        vrs.addModel("TOYOTA200","Toyota Tundra",Transmission.automatic,
                0.5f, 4, Group.B);

        System.out.println(vrs.toString(vrs.getModelList()) + "\n");

        System.out.println("Print out error");

        // cannot add a model with the existed model number
        try {
        	System.out.println("Try to add existed model number (HONDA100)");
            vrs.addModel("HONDA100","Honda something idk",Transmission.automatic,
                    2.3f, 4, Group.E);
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getModelList()));
        }
    }

    @Test
    // 5. Add a car test
    public void addCarTest() throws Exception {
        System.out.println("5. Testing for addCar(regNum, color, year, " +
                "modelNumber, branchNumber)");

        VinaRentSystem vrs = new VinaRentSystem();

        System.out.println("Normal case: add cars of specified models to specified branches");

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
        System.out.println("6. Testing for addCustomer(name, driverLicense, email, phone)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add normal customers
        System.out.println("Normal cases");
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
        System.out.println("7. Testing for listCar(branchNumber, group)");

        VinaRentSystem vrs = new VinaRentSystem();

        System.out.println("Normal case: list all the rented cars of a specified group of a specified branch");

        // add customers
        vrs.addCustomer("Karen", "KA789345", "karen@something.com", "098762345");
        vrs.addCustomer("Kevin", "KE239458", "kevin@something.com", "092385762");

        // add branches
        vrs.addBranch("D-1"		,	"Dist. 1 Branch");
        vrs.addBranch("D-5"		,	"Dist. 5 Branch");

        // make branches become neighbor
        vrs.makeNeighbor("D-1", "D-5");

        // add models
        vrs.addModel("HONDA100","Honda Civic",Transmission.automatic,
                0.5f, 4, Group.A);
        vrs.addModel("TOYOTA100","Toyota Camry",Transmission.automatic,
                1.2f, 2, Group.B);

        // add cars
        vrs.addCar("QWERTY-1234", "red", 2000,
                "HONDA100", "D-1");
        vrs.addCar("ASDF-2345", "blue", 2002,
                "HONDA100", "D-1");
        vrs.addCar("ZXCV-7890", "green", 2009,
                "TOYOTA100", "D-5");
        vrs.addCar("HJKL-2346", "white", 2003,
                "TOYOTA100", "D-5");

        // add string days
        String sDate1 = "25-12-2021 23:37:50";
        String sDate2 = "10-1-2022 11:49:12";

        // add date formatters
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // add dates
        Date date1 = formatter.parse(sDate1);
        Date date2 = formatter.parse(sDate2);

        // add rentals
        vrs.addRental("D-1","D-1", date1, date2,
                "HONDA100", "red", 2000, "KA789345");
        vrs.addRental("D-5","D-5", date1, date2,
                "TOYOTA100", "white", 2003, "KA789345");
        vrs.addRental("D-5","D-5", date1, date2,
                "TOYOTA100", "green", 2009,"KE239458");

        System.out.println(vrs.toString(vrs.getBranchList()) + "\n");
        System.out.println(vrs.toString(vrs.getRentalList()) + "\n");

        // Print the car that is in a rental of branch 1
        System.out.println("Cars of group A and in rental of D-1 are: ");
        for (Car car : vrs.listCar("D-1", Group.A)) {
            System.out.print(car.getRegNum());
        }
        System.out.println("\n");

        System.out.println("Print out error");

        // non-existed branch number will cause error
        try {
        	System.out.println("Try non-existed branch");
            vrs.listCar("D-TD", Group.A);
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getBranchList()));
        }
    }

    @Test
    // 8. Record return test
    public void recordReturnTest() throws Exception {
        System.out.println("8. Testing for recordReturn(rentalNumber, realReturnDate, realReturnBranchNo)");

        VinaRentSystem vrs = new VinaRentSystem();

        System.out.println("Normal case: Record the returning of a rental");

        // add customers
        vrs.addCustomer("Karen", "KA26835", "karen@something.com", "092354254");

        // add branches
        vrs.addBranch("D-1"		,	"Dist. 1 Branch");
        vrs.addBranch("D-5"		,	"Dist. 5 Branch");

        // make branches become neighbor
        vrs.makeNeighbor("D-1", "D-5");

        // add models
        vrs.addModel("HONDA100","Honda Civic",Transmission.automatic,
                0.5f, 4, Group.A);

        // add cars
        vrs.addCar("UVYU-2489", "blue", 2000,
                "HONDA100", "D-1");

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
        vrs.addRental("D-1","D-1", date2, date1,
                "HONDA100", "blue",2000,"KA26835");
        String rentalNumber1 = "KA26835-Thu Nov 12 11:49:12 ICT 1998";
        System.out.println(vrs.toString(vrs.getRentalList()) + "\n");

        vrs.recordReturn(rentalNumber1, date3, "D-5");
        System.out.println(vrs.toString(vrs.getRentalList()) + "\n");

        System.out.println("Print out error");

        // cannot record return for a returned rental
        try {
            System.out.println("Trying to record the returning of a returned rental (rental: \"license1-Thu Nov 12 11:49:12 ICT 1998\")");
            vrs.recordReturn(rentalNumber1, date3, "D-5");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getRentalList()));
        }
    }
// ---------------------------- END OF ATOMIC USE CASES TEST ---------------------------------- //



// ---------------------------- ADDITION ATOMIC USE CASES TEST ---------------------------------- //
    @Test
    // 1. Add a rental test
    public void addRentalTest() throws Exception {
        System.out.println("9. Testing for addRental(pickupBranch, returnBranch, pickupDate, returnDate, " +
                "modelNumber, color, year, driverLicense)");

        VinaRentSystem vrs = new VinaRentSystem();

        System.out.println("Normal case: Add a new rental");

        // add customers
        vrs.addCustomer("Karen"	, "KA235436", "karen@something.com"	, "0945233234");
        vrs.addCustomer("John"	, "JO547532", "john@something.com"	, "0945243552");
        vrs.addCustomer("Kevin"	, "KE346236", "kevin@something.com"	, "091446675");

        // add KA235436 to blacklist
        vrs.addBlacklist("KA235436");

        // add branches
        vrs.addBranch("D-1"	,	"Dist. 1 Branch");
        vrs.addBranch("D-5"	,	"Dist. 5 Branch");
        vrs.addBranch("D-7"	,	"Dist. 7 Branch");

        // make branches become neighbor
        vrs.makeNeighbor("D-1", "D-5");
        vrs.makeNeighbor("D-1", "D-7");

        // add models
        vrs.addModel("HONDA100","Honda Civic",Transmission.automatic,
                0.5f, 4, Group.A);
        vrs.addModel("TOYOTA100","Toyota Camry",Transmission.automatic,
                1.2f, 2, Group.B);

        // add cars
        vrs.addCar("EQRR-6432", "red", 2000,
                "HONDA100", "D-1");
        vrs.addCar("VRNJ-3890", "green", 2002,
                "HONDA100", "D-5");
        vrs.addCar("ESZR-3048", "blue", 2009,
                "TOYOTA100", "D-7");
        vrs.addCar("GFOG-1223", "white", 2003,
                "TOYOTA100", "D-1");

        // add string days
        String sDate1 = "31-12-2020 23:37:50";
        String sDate2 = "12-11-1998 11:49:12";

        // add date formatters
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // add dates
        Date date1 = formatter.parse(sDate1);
        Date date2 = formatter.parse(sDate2);

        // add rentals
        vrs.addRental("D-1","D-1", date2, date1,
                "TOYOTA100", "blue",2009,"KE346236");

        // can add a rental with the same pickupBranch, returnBranch or the same
        // pickupDate and returnDate.
        // 1 customer can have many rentals.
        // System can find a car in neighbor branches of pickupBranch (the car here is in branch2).
        vrs.addRental("D-5","D-5", date1, date1,
                "HONDA100", "green",2002,"JO547532");

        System.out.println(vrs.toString(vrs.getRentalList()));

        System.out.println("\nPrint out error");

        // cannot add a rental with the non-existed branch
        try {
            System.out.println("Cannot add a rental with the non-existed branch (D-TD)");
            vrs.addRental("D-TD","D-1", date2, date2,
                    "HONDA100", "green",2002,"JO547532");
        } catch (Exception e) {
        	System.out.println("Rental list after the failed test");
            System.out.println(vrs.toString(vrs.getRentalList()));
        }

        // cannot add a rental with returnDate before pickupDate
        try {
            System.out.println("\nCannot add a rental with returnDate before pickupDate");
            vrs.addRental("D-1","D-1", date1, date2,
                    "TOYOTA100", "blue",2009,"KE346236");
        } catch (Exception e) {
        	System.out.println("Rental list after the failed test");
            System.out.println(vrs.toString(vrs.getRentalList()));
        }

        // cannot add a rental with new customer, ask info of new customer
        try {
            System.out.println("\nCannot add a rental with new customer (GR345259), need to ask information of the new customer");
            vrs.addRental("D-1","D-1", date2, date2,
                    "TOYOTA100", "blue",2009,"GR345259");
        } catch (Exception e) {
        	System.out.println("Rental list after the failed test");
            System.out.println(vrs.toString(vrs.getCustomerList()));
        }

        // cannot add a rental with a blacklisted customer
        try {
            System.out.println("\nCannot add a rental with a blacklisted customer (KA235436)");
            vrs.addRental("D-1","D-1", date2, date2,
                    "TOYOTA100", "blue",2009,"KA235436");
        } catch (Exception e) {
        	System.out.println("Rental list after the failed test");
            System.out.println(vrs.toString(vrs.getCustomerList()));
        }

        // cannot add a rental with a non-existed model
        try {
            System.out.println("\nCannot add a rental with a non-existed model (VINFAST100)");
            vrs.addRental("D-5","D-5", date2, date2,
                    "VINFAST100", "red",2003,"KE346236");
        } catch (Exception e) {
        	System.out.println("Rental list after the failed test");
            System.out.println(vrs.toString(vrs.getModelList()));
        }

        // cannot add a rental with a non-existed car
        try {
            System.out.println("\nCannot add a rental with a non-existed car (cannot find car that match the color, year and model)");
            vrs.addRental("D-1","D-1", date2, date2,
                    "HONDA100", "green",2020,"KE346236");
        } catch (Exception e) {
        	System.out.println("The car list after the failed test");
            System.out.println(vrs.toString(vrs.getCarList()));
            System.out.println("The rental list in total");
            System.out.println(vrs.toString(vrs.getRentalList()));
        }
    }

    @Test
    // 2. Add a customer to blacklist test
    public void addBlacklistTest() throws Exception {
        System.out.println("10. Testing for addBlacklist(driverLicense)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add normal customers
        vrs.addCustomer("Karen", "KA142423", "karen@something.com", "09322332");
        vrs.addCustomer("Kevin", "KE238013", "kevin@something.com", "09124276");

        System.out.println(vrs.toString(vrs.getCustomerList()));

        // Blacklisted customer 1
        System.out.println("\nNormal case: Add customer 1 to blacklist");
        vrs.addBlacklist("KA142423");
        System.out.println("Print out blacklist");
        System.out.println(vrs.toString(vrs.getBlacklist()));

        System.out.println("\nPrint out error");

        // cannot blacklisted a customer with the non-existed driver license
        try {
            System.out.println("Cannot blacklisted a customer with the non-existed driver license in the system (JO231774)");
            vrs.addBlacklist("JO231774");
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getCustomerList()));
        }
    }

    @Test
    // 3. Update status of a car after being returned test
    public void carMaintenanceTest() throws Exception {
        System.out.println("11. Testing for carMaintenance(regNum, status)");

        VinaRentSystem vrs = new VinaRentSystem();

        // add customers
        vrs.addCustomer("Karen", "KA789345", "karen@something.com", "098762345");
        vrs.addCustomer("Kevin", "KE239458", "kevin@something.com", "092385762");

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
        vrs.addCar("QWERTY-2345", "blue", 2009,
                "TOYOTA111", "D-TD");
        vrs.addCar("ZXCV-7890", "white", 2010,
                "TOYOTA111", "D-TD");

        // add string days
        String sDate1 = "31-12-2020 23:37:50";
        String sDate2 = "26-06-1998 11:49:12";
        String sDate3 = "26-06-2021 12:13:14";

        // add date formatters
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // add dates
        Date date1 = formatter.parse(sDate1);
        Date date2 = formatter.parse(sDate2);
        Date date3 = formatter.parse(sDate3);

        // add rentals
        vrs.addRental("D-5","D-5", date2, date1,
                "HONDA100", "red",2000,"KA789345");
        String rentalNumber1 = "KA789345-Fri Jun 26 11:49:12 ICT 1998";

        vrs.addRental("D-TD","D-5", date1, date1,
                "TOYOTA111", "blue",2009,"KE239458");
        String rentalNumber2 = "KE239458-Thu Dec 31 23:37:50 ICT 2020";

        // record return of rental "KA789345-Fri Jun 26 11:49:12 ICT 1998"
        vrs.recordReturn(rentalNumber1, date3, "D-5");
        vrs.recordReturn(rentalNumber2, date3, "D-5");
        System.out.println(vrs.toString(vrs.getCarList()) + "\n");

        // set status of the RETURNED car to SERVICE_NEEDED
        System.out.println("Normal case: Maintain a RETURNED car (ASDF-1234)");
        vrs.carMaintenance("ASDF-1234", Status.SERVICE_NEEDED);
        System.out.println(vrs.toString(vrs.getCarList()) + "\n");

        // set status of the SERVICE_NEEDED car to READY
        System.out.println("Normal case: Maintain a SERVICE_NEEDED car (ASDF-1234)");
        vrs.carMaintenance("ASDF-1234", Status.READY);
        System.out.println(vrs.toString(vrs.getCarList()) + "\n");

        System.out.println("Print out error");

        // cannot maintain with the unavailable regNum
        try {
            System.out.println("Try to maintain car with non-existed registration number (QWERTY-123)");
            vrs.carMaintenance("QWERTY-123", Status.READY);
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getCarList()) + "\n");
        }

        // cannot maintain the car whose status is neither RETURNED or SERVICE_NEEDED
        try {
            System.out.println("Try to maintain car whose status is neither RETURNED or SERVICE_NEEDED (ZXCV-7890)");
            vrs.carMaintenance("ZXCV-7890", Status.READY);
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getCarList()) + "\n");
        }

        // cannot maintain a car with a wrong input status (neither READY or SERVICE_NEEDED or REMOVED)
        try {
            System.out.println("Try to maintain a car with a wrong input status (neither READY or SERVICE_NEEDED or REMOVED)");
            vrs.carMaintenance("QWERTY-2345", Status.HELD);
        } catch (Exception e) {
            System.out.println(vrs.toString(vrs.getCarList()));
        }
    }


// ------------------------ END OF ADDITION ATOMIC USE CASES TEST --------------------------------- //
}
