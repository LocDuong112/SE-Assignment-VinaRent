public class Customer {
    private String name;
    private String driveLicense;
    private String email;
    private String phone;
    private CustomerStatus status;

    public Customer(String name, String driveLicense, String email, String phone) {
        this.name = name;
        this.driveLicense = driveLicense;
        this.email = email;
        this.phone = phone;
        this.status = CustomerStatus.NORMAL;
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
    
    public CustomerStatus getStatus() {
		return status;
	}
}
