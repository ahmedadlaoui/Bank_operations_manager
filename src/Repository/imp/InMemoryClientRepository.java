package Repository.imp;

import Model.User;
import Repository.ClientRepository;
import java.util.ArrayList;

public class InMemoryClientRepository implements ClientRepository {

    private static InMemoryClientRepository instance; // Singleton instance
    private final ArrayList<User> users;

    // Private constructor to prevent external instantiation
    private InMemoryClientRepository() {
        this.users = new ArrayList<>();
    }

    // Public method to get the singleton instance
    public static InMemoryClientRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryClientRepository();
        }
        return instance;
    }

    @Override
    public void Save(User user) {
        this.users.add(user);
    }

    @Override
    public User FindByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
