public class Car {
    private String regNum;
    private String color;
    private String modelNumber;
    private Status status;
    private String branchNumber;
    private int year;

    public Car(String regNum, String color, int year, String modelNumber, String branchNumber) {
        this.regNum = regNum;
        this.color = color;
        this.year = year;
        this.modelNumber = modelNumber;
        this.branchNumber = branchNumber;
        this.status = Status.READY;
    }

    public String getRegNum() {
        return regNum;
    }
    
    public String getColor() {
		return color;
	}
    
    public String getModelNumber() {
		return modelNumber;
	}
    
    public String getBranchNumber() {
		return branchNumber;
	}
    
    public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}
    
    public Status getStatus() {
		return status;
	}
    
    public void setStatus(Status status) {
		this.status = status;
	}
    
    public int getYear() {
		return year;
	}

    @Override
    public String toString() {
        String result = "";
        result = String.format("%-20s | %-20s | %-20d | %-20s | %-20s | %-20s%n",
                regNum, color, year, modelNumber, branchNumber, status);

        return result;
    }
}
