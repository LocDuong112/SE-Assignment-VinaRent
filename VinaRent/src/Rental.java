import java.util.Date;

public class Rental {
    private Customer customer;
    private String pickupBranch, returnBranch, realReturnBranch;
    private Date pickupDate, returnDate, realReturnDate;
    private String modelNumber;
    private String regNum;
    private String status;

    public Rental(Customer customer, String pickupBranch, String returnBranch,
                  Date pickupDate, Date returnDate, String modelNumber, String regNum) {
        this.customer = customer;
        this.pickupBranch = pickupBranch;
        this.returnBranch = returnBranch;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.modelNumber = modelNumber;
        this.regNum = regNum;

        this.realReturnDate = new Date();
        this.realReturnBranch = "NULL";
        this.status = "NOT RETURNED";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
