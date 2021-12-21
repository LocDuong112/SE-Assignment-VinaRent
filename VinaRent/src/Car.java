public class Car {
    private int regNum;
    private String color;
    private String modelNumber;
    private String status;
    private String branchNumber;
    private int year;

    public Car(int regNum, String color, int year, String modelNumber, String status, String branchNumber) {
        this.regNum = regNum;
        this.color = color;
        this.year = year;
        this.modelNumber = modelNumber;
        this.branchNumber = branchNumber;
        this.status = status;
    }
}
