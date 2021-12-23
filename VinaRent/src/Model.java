import java.util.ArrayList;
import java.util.List;

public class Model {
    private String number;
    private String name;
    private Transmission transmission;
    private float consumption;
    private int numDoor;
    private Group group;
    private List<Car> carList;

    public Model(String number, String name, Transmission transmission, float consumption, int numDoor, Group group) {
        this.number = number;
        this.name = name;
        this.transmission = transmission;
        this.consumption = consumption;
        this.numDoor = numDoor;
        this.group = group;
        carList = new ArrayList<>();
    }

    public String getNumber() {
        return number;
    }

    public List<Car> getCarList() {
        return carList;
    }
    
    public String getName() {
		return name;
	}
    
    public float getConsumption() {
		return consumption;
	}
    
    public Transmission getTransmission() {
		return transmission;
	}
    
    public Group getGroup() {
		return group;
	}
    
    public int getNumDoor() {
		return numDoor;
	}

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    @Override
    public String toString() {
        String result = "";
        result = String.format("%-20s | %-20s | %-20s | %-20.1f | %-20d | %-20s | ",
                number, name, transmission.toString(), consumption, numDoor, group.toString());
        for (Car car : carList) {
            result += String.format("%s, ", car.getRegNum());
        }
        result += "\n";

        return result;
    }
}
