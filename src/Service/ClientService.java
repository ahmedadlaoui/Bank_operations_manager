package Service;
import Model.Client;
import Model.User;

public class ClientService {

    public void DisplayPersonalInformation(User user){


        System.out.println();
        System.out.println("=== Personal Information ===");
        if (user instanceof Client) {
            Client client = (Client) user;
            System.out.println("Client ID: " + client.getId());
        }
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Role: " + user.getRole());
    }
}
