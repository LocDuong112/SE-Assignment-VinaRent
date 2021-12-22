import java.util.Date;

public class Rental {
    private String number;
    private Customer customer;
    private String pickupBranch, returnBranch, realReturnBranch;
    private Date pickupDate, returnDate, realReturnDate;
    private String modelNumber;
    private RentalStatus status;
    private Car car;

    public Rental(Customer customer, String pickupBranch, String returnBranch,
                  Date pickupDate, Date returnDate, String modelNumber, Car car) {
        this.customer = customer;
        this.pickupBranch = pickupBranch;
        this.returnBranch = returnBranch;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.modelNumber = modelNumber;
        this.car = car;
        this.status = RentalStatus.NOT_RETURNED;
        this.number = generateNumber();
    }
    
    private String generateNumber() {
		String result = String.format(customer.getDriveLicense(), null);
		result += String.format("-", null);
		result += String.format(pickupDate.toString(), null);
		return result;
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
    
    public RentalStatus getStatus() {
		return status;
	}
    
    public void setStatus(RentalStatus status) {
		this.status = status;
	}
    
    public String getNumber() {
		return this.number;
	}
    
    public Car getCar() {
		return car;
	}
    
    public void setCar(Car car) {
		this.car = car;
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
