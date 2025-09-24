package Util;
import Model.Client;
import Model.Manager;
import Model.User;

public class UserFactory {

        public User CreateUserInstance(String role, String email, String firstname, String lastname, String password, String department){
            if ("CLIENT".equalsIgnoreCase(role)) {
                return new Client(email,firstname, lastname, password);
            } else if ("MANAGER".equalsIgnoreCase(role)) {
                return new Manager(email,firstname, lastname, password, department);
            } else {
                throw new IllegalArgumentException("❌ Unknown role: " + role);
            }
        }

}
