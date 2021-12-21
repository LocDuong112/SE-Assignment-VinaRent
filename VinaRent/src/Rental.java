import java.util.Date;

public class Rental {
    private Customer customer;
    private String pickupBranch, returnBranch, realReturnBranch;
    private Date pickupDate, returnDate, realReturnDate;
    private String modelNumber;
    private String regNum;

    public Rental(Customer customer, String pickupBranch, String returnBranch,
                  Date pickupDate, Date returnDate, String modelNumber, String regNum) {
        this.customer = customer;
        this.pickupBranch = pickupBranch;
        this.returnBranch = returnBranch;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.modelNumber = modelNumber;
        this.regNum = regNum;
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
