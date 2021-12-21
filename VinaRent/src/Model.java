import java.util.List;

public class Model {
    private String number;
    private String name;
    private Transmission transmission;
    private float consumption;
    private int numDoor;

    private List<Car> carList;

    public Model(String number, String name, Transmission transmission,
                 float consumption, int numDoor) {
        this.number = number;
        this.name = name;
        this.transmission = transmission;
        this.consumption = consumption;
        this.numDoor = numDoor;
    }

    public String getNumber() {
        return number;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
}
