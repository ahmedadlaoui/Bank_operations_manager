package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Manager extends User {
    private UUID id;
    private String department;
    private List<Client> clients;

    public Manager(String email, String firstName, String lastName, String password, String department) {
        super("MANAGER",email,firstName, lastName, password);
        this.id = UUID.randomUUID();   // auto-generate unique UUID
        this.department = department;
        this.clients = new ArrayList<>();
    }

    public UUID getId() { return id; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public List<Client> getClients() { return clients; }

    public void addClient(Client client) {
        if (client != null) {
            this.clients.add(client);
        }
    }

    @Override
    public String toString() {
        return "Manager{id='" + id + "', name='" + firstName + " " + lastName +
                "', department='" + department + "'}";
    }
}
