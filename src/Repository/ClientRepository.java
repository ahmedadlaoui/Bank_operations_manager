package Repository;
import Model.User;

public interface ClientRepository {
    public void Save(User user);
    public User FindByEmail(String email);
}
