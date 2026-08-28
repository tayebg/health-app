package pages;

public class User {
    private String username;
    private String password;
    private String fullName;
    private String gender;
    private String email;
    private String phone;

    public User(String username, String password, String fullName, String gender, String email, String phone) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
