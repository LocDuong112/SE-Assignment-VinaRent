public class Customer {
    private String name;
    private String driveLicense;
    private String email;
    private String phone;
    private String status;

    public Customer(String name, String driveLicense, String email, String phone) {
        this.name = name;
        this.driveLicense = driveLicense;
        this.email = email;
        this.phone = phone;
        this.status = "NORMAL";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
