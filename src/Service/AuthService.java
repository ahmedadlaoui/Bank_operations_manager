package Service;

import Model.User;
import Repository.ClientRepository;
import Util.UserFactory;
import Util.PasswordValidator;
import Repository.imp.InMemoryClientRepository;

public class AuthService {
    private final UserFactory userFactory;
    private final ClientRepository userRepository = InMemoryClientRepository.getInstance();
    private final PasswordValidator passwordValidator;

    public AuthService() {
        this.userFactory = new UserFactory();
        this.passwordValidator = new PasswordValidator();
    }

    public void RegisterNewUser(String role, String email, String firstname, String lastname, String password, String department) {
        if (this.userRepository.FindByEmail(email) != null) {
            System.out.println("An account with this email already exists.");
            return;
        }
        if (!this.passwordValidator.validate(password)) {
            System.out.println("The password must be at least 6 characters long.");
            return;
        }
        User NewUser = this.userFactory.CreateUserInstance(role, email, firstname, lastname, password, department);
        this.userRepository.Save(NewUser);
        System.out.println("Your account is successfully created.");
    }


    public User LogIn(String email, String password) {
        User UserTryingToLogIn = this.userRepository.FindByEmail(email);
        if (UserTryingToLogIn != null && UserTryingToLogIn.getPassword().equals(password)) {
            return UserTryingToLogIn;
        }
        return null;
    }
}
