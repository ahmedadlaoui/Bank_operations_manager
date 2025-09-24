package Repository.imp;
import Model.User;
import Repository.ClientRepository;
import java.util.ArrayList;

public class InMemoryClientRepository implements ClientRepository {
    private ArrayList<User> users = new ArrayList<>();

    public void Save(User user) {
        this.users.add(user);
    }

    public User FindByEmail(String email) {
        for (User user : users){
            if (user.getEmail().equals(email)){
                return user;
            }
        }return null;
    }
}
