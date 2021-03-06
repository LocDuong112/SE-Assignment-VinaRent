public class Customer {
    private String name;
    private String driveLicense;
    private String email;
    private String phone;

    public Customer(String name, String driveLicense, String email, String phone) {
        this.name = name;
        this.driveLicense = driveLicense;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
		return name;
	}
    
    public String getDriveLicense() {
		return driveLicense;
	}
    
    public String getEmail() {
		return email;
	}
    
    public String getPhone() {
		return phone;
	}

    @Override
    public String toString() {
        String result = "";
        result = String.format("%-20s | %-20s | %-20s | %-20s\n",
                name, driveLicense, email, phone);

        return result;
    }
}
