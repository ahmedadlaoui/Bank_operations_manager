package Model;

public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected String role; // "CLIENT" or "MANAGER"

    public User( String role,String email,String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters & Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "[" + role + "] " + firstName + " " + lastName + " (" + email + ")";
    }
}




