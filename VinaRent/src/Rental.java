import java.util.Date;

public class Rental {
	private String number;
    private Customer customer;
    private String pickupBranch, returnBranch, realReturnBranch;
    private Date pickupDate, returnDate, realReturnDate;
    private String modelNumber;
    private String regNum;
    private RentalStatus status;
    private Car car;

    public Rental(String number, Customer customer, String pickupBranch, String returnBranch,
                  Date pickupDate, Date returnDate, String modelNumber, String regNum) {
        this.customer = customer;
        this.pickupBranch = pickupBranch;
        this.returnBranch = returnBranch;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.modelNumber = modelNumber;
        this.regNum = regNum;
        this.status = RentalStatus.NOT_RETURNED;
    }
    
    public Customer getCustomer() {
		return customer;
	}
    
    public String getPickupBranch() {
		return pickupBranch;
	}
    
    public String getReturnBranch() {
		return returnBranch;
	}
    
    public Date getPickupDate() {
		return pickupDate;
	}
    
    public Date getReturnDate() {
		return returnDate;
	}
    
    public String getModelNumber() {
		return modelNumber;
	}
    
    public String getRegNum() {
		return regNum;
	}
    
    public RentalStatus getStatus() {
		return status;
	}
    
    public void setStatus(RentalStatus status) {
		this.status = status;
	}
    
    public String getNumber() {
		return number;
	}
    
    public Car getCar() {
		return car;
	}
    
    public void setCar(String regNum, String color, int year, String modelNumber, String branchNumber) {
		this.car = new Car(regNum, color, year, modelNumber, branchNumber);
	}

    public String getRealReturnBranch() {
        return realReturnBranch;
    }

    public void setRealReturnBranch(String realReturnBranch) {
        this.realReturnBranch = realReturnBranch;
    }

    public Date getRealReturnDate() {
        return realReturnDate;
    }

    public void setRealReturnDate(Date realReturnDate) {
        this.realReturnDate = realReturnDate;
    }
}
