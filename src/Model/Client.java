package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client extends User {
    private UUID id;
    private List<Account> accounts;

    public Client(String email, String firstName, String lastName, String password) {
        super("Client",email,firstName, lastName, password);
        this.id = UUID.randomUUID();
        this.accounts = new ArrayList<>();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }


    @Override
    public String toString() {
        return "Client{id='" + id + "', name='" + firstName + " " + lastName + "', email='" + email + "'}";
    }
}
