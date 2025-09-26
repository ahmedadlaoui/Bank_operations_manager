package Service;

import Model.Account;
import Model.Client;
import Model.User;
import Model.enums.AccountType;
import Repository.AccountRepository;
import Repository.imp.InMemoryAccountRepository;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AccountService {
    private final Scanner input = new Scanner(System.in);
    private final InMemoryAccountRepository accountRepo = InMemoryAccountRepository.getInstance();

    public void AddAccount(User user) {
        Client client = (Client) user;
        System.out.print("Enter account Type (Current, Savings or Term deposit) :");
        String AccountTypeInput = input.nextLine().toUpperCase();

        AccountType selectedAccountType;
        try {
            selectedAccountType = AccountType.valueOf(AccountTypeInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid account type. Defaulting to Current.");
            selectedAccountType = AccountType.CURRENT;
        }
        Account NewAccount = new Account(selectedAccountType, client);
        this.accountRepo.Save(NewAccount);
        System.out.println("New account has been successfully created.");
        System.out.println();
        System.out.println("=== Account Informations ===");

        System.out.println("Account ID   : " + NewAccount.getId());
        System.out.println("Account Type : " + NewAccount.getType());
        System.out.println("Balance      : " + NewAccount.getBalance());
        System.out.println("Client       : " + NewAccount.getClient().getFirstName() + " " + NewAccount.getClient().getLastName());
    }

    public void DisplayAccountsByClientID(User user) {
        Client client = (Client) user;
        UUID ClientID = client.getId();
        List<Account> ClientAccounts = this.accountRepo.FindByClientID(ClientID);
        if (ClientAccounts.isEmpty()) {
            System.out.println("No client accounts have been created.");
        } else {
            System.out.println();
            System.out.println("=== Your Accounts ===");
            for (Account account : ClientAccounts) {
                System.out.println("------------------------------");
                System.out.println("Account ID  : " + account.getId());
                System.out.println("Type        : " + account.getType());
                System.out.println("Balance     : " + account.getBalance());
            }
        }
        System.out.println("------------------------------");
    }

    public void CalculateTotals(User user) {
        if (!(user instanceof Client)) {
            System.out.println("⚠️ Only clients have balances.");
            return;
        }

        Client client = (Client) user;
        Double totalCurrentBalance = 0.0;
        Double totalWithdrawal = 0.0;
        Double totalLongTermDeposit = 0.0;
        Double grandTotal = 0.0;

        List<Account> AllAccounts = this.accountRepo.FindByClientID(client.getId());

        for (Account account : AllAccounts) {
            switch (account.getType()) {
                case CURRENT:
                    totalCurrentBalance += account.getBalance();
                    break;
                case SAVINGS:
                    totalWithdrawal += account.getBalance();
                    break;
                case TERM_DEPOSIT:
                    totalLongTermDeposit += account.getBalance();
                    break;
            }
            grandTotal += account.getBalance();
        }

        System.out.println();
        System.out.println("=== Balance Summary for " + client.getFirstName() + " " + client.getLastName() + " ===");
        System.out.println("Total in Current Accounts     : " + totalCurrentBalance);
        System.out.println("Total in Savings Accounts     : " + totalWithdrawal);
        System.out.println("Total in Long-Term Deposits   : " + totalLongTermDeposit);
        System.out.println("---------------------------------------");
        System.out.println("Grand Total Across All Accounts: " + grandTotal);
    }

    public void DisplayAllAccounts(){

        List<Account> AllAccounts = this.accountRepo.findAll();
        if (AllAccounts.isEmpty()) {
            System.out.println("No account found.");
        } else {
            System.out.println();
            System.out.println("=== All Accounts ===");
            for (Account account : AllAccounts) {
                System.out.println("------------------------------");
                System.out.println("Account ID  : " + account.getId());
                System.out.println("Type        : " + account.getType());
                System.out.println("Balance     : " + account.getBalance());
            }
        }
        System.out.println("------------------------------");
    }
}
