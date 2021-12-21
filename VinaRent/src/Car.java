public class Car {
    private int regNum;
    private String color;
    private String modelNumber;
    private Status status;
    private String branchNumber;
    private int year;

    public Car(int regNum, String color, int year, String modelNumber, String branchNumber) {
        this.regNum = regNum;
        this.color = color;
        this.year = year;
        this.modelNumber = modelNumber;
        this.branchNumber = branchNumber;
        this.status = Status.READY;
    }

    public int getRegNum() {
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
    
    public Status getStatus() {
		return status;
	}
    
    public int getYear() {
		return year;
	}
}
