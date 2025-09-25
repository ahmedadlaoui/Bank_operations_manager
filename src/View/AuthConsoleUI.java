package View;

import java.util.Scanner;

import Model.User;
import Service.AuthService;
import View.ClientConsoleUI;
import View.ManagerConsoleUI;

public class AuthConsoleUI {
    private final Scanner input;
    private final AuthService authService;
    private final ManagerConsoleUI managerConsoleUI;
    private final ClientConsoleUI clientConsoleUI;

    public AuthConsoleUI() {
        this.clientConsoleUI = new ClientConsoleUI();
        this.managerConsoleUI = new ManagerConsoleUI();
        this.input = new Scanner(System.in);
        this.authService = new AuthService();
    }

    public void Start() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1 - Sign Up");
            System.out.println("2 - Sign In");
            System.out.println("3 - Exit");
            System.out.print("Enter your choice : ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
                continue;
            }

            switch (choice) {
                case 1: // SIGN UP
                    this.HandleSignUp();
                    break;
                case 2: // SIGN IN
                    this.HandleSignIn();
                    break;
                case 3: // EXIT
                    System.out.println("Goodbye!");
                    input.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void HandleSignUp() {
        System.out.println("\n=== SIGN UP ===");
        System.out.print("Choose a role (Client or Manager) :");
        String role = input.nextLine();

        if (!role.equalsIgnoreCase("Client") && !role.equalsIgnoreCase("Manager")) {
            System.out.println("Invalid role. Please try again.");
            return;
        }

        System.out.print("Enter your E-mail : ");
        String email = input.nextLine();

        System.out.print("Enter your first name : ");
        String firstname = input.nextLine();

        System.out.print("Enter your last name : ");
        String lastname = input.nextLine();

        System.out.print("Choose a password (password must be at least 6 characters) : ");
        String password = input.nextLine();

        if (role.equalsIgnoreCase("Client")) {
            this.authService.RegisterNewUser(role, email, firstname, lastname, password, null);
        } else {
            System.out.print("Enter your Department : ");
            String department = input.nextLine();
            this.authService.RegisterNewUser(role, email, firstname, lastname, password, department);
        }

    }

    public void HandleSignIn() {
        System.out.println("\n=== SIGN IN ===");
        System.out.print("Enter E-mail: ");
        String email = input.nextLine();
        System.out.print("Enter Password: ");
        String password = input.nextLine();

        User CurrentUser = this.authService.LogIn(email, password);
        if (CurrentUser != null && CurrentUser.getRole().equalsIgnoreCase("Client")) {
            this.clientConsoleUI.start(CurrentUser);
        }
        else if (CurrentUser != null && CurrentUser.getRole().equalsIgnoreCase("Manager")) {
            this.managerConsoleUI.start(CurrentUser);
        }
        else {
            System.out.println("⚠️ Wrong email or password.");
        }
    }
}
