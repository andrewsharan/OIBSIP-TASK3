import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    String type;
    double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": ₹" + amount;
    }
}

class Account {
    private String username;
    private String password;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public Account(String username, String password, double initialBalance) {
        this.username = username;
        this.password = password;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.getUsername(), amount));
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History for " + username + ":");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
        System.out.println("Current Balance: ₹" + balance);
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Account userAccount = new Account("Andrew", "amount", 2000.0);


        System.out.print("Enter username: ");
        String enteredUsername = scanner.next();
        System.out.print("Enter password: ");
        String enteredPassword = scanner.next();

        if (!userAccount.authenticate(enteredPassword) || !enteredUsername.equals(userAccount.getUsername())) {
            System.out.println("Authentication failed. Exiting...");
            System.exit(0);
        }

        while (true) {
            System.out.println("\nATM Interface\n1. Transactions History\n2. Withdraw\n3. Deposit\n4. Transfer\n5. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    userAccount.printTransactionHistory();

                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ₹");
                    double withdrawAmount = scanner.nextDouble();
                    userAccount.withdraw(withdrawAmount);
                    System.out.println("Amount withdrawn");
                    userAccount.getBalance();
                    break;
                case 3:
                    System.out.print("Enter deposit amount: ₹");
                    double depositAmount = scanner.nextDouble();
                    userAccount.deposit(depositAmount);
                    System.out.println("Amount deposited");
                    userAccount.getBalance();
                    break;
                case 4:
                    System.out.print("Enter transfer amount: ₹");
                    double transferAmount = scanner.nextDouble();
                    System.out.print("Enter recipient account balance: ₹");
                    double recipientBalance = scanner.nextDouble();

                    Account recipientAccount = new Account("root", "password", recipientBalance);
                    userAccount.transfer(recipientAccount, transferAmount);
                    System.out.println("Amount transferred");
                    userAccount.getBalance();
                    break;
                case 5:
                    System.out.println("Exiting ATM. Thank you!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
