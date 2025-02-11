import java.io.IOException;
import java.util.*;

final class Account{
    private static int counter = 3;
    double balance;

    private String accountNo;
    private int pin;

    private String name;
    private int age;
    private String phoneNo;
    boolean isMinor;

    Account(){
        balance = 0;
    }

    Account(String name,int pin, String phoneNo, int age){
        balance = 5000;
        this.pin = pin;
        setAccountNo();
        this.name = name;
        this.phoneNo = phoneNo;
        this.age = age;
        this.isMinor = (this.age <18);
    }
    String getName(){
        return this.name;
    }
    public String getAccountNo() {
        return accountNo;
    }

    public double getBalance() {
        return balance;
    }

    protected int getPin(){
        return pin;
    }
    
    protected void setPin(int pin){
        this.pin = pin;
    }

    void setAccountNo() {
        counter++;
        accountNo = (counter + 1200000) + "";
    }
}

public class ATMInterface {
    static Map<String, Account> users = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in);

    static boolean authenticate(String accountNo, int pin) {
        Account account;
        if (users.containsKey(accountNo)) {
            account = users.get(accountNo);
            return (account.getPin() == pin);
        }
        return false;
    }

    static void deposit(Account account) {
        System.out.print("Enter amount to be deposited: ");
        double amount = sc.nextInt();
        account.balance += amount;
        System.out.println("Amount " + amount + " deposited successfully");
        System.out.println("Your updated account balance is " + account.balance);
    }

    static void withdraw(Account account) {
        System.out.print("Enter the amount to be withdrawn: ");
        int amount = sc.nextInt();

        if (amount > account.balance) {
            System.out.println("Insufficient Balance!");
            return;
        } else if (account.isMinor)
            if (amount > 5000) {
                System.out.println("Transaction failed! The amount exceeds withdrawal amount of minor account. Please enter a amount within limits.");
                return;
            }
        account.balance -= amount;
        System.out.println("Amount of "+amount+" withdraw successfully.");
        System.out.println("Your updated account balance is " + account.balance);
    }

    static void changePin(Account account) {
        String oldPin, newPin, confirmPin;

        System.out.print("Enter your current pin: ");
        oldPin = sc.next();

        if (Integer.parseInt(oldPin) == account.getPin()) {
            do {
                System.out.print("Enter new Pin: ");
                newPin = sc.next();
                System.out.print("Confirm new Pin: ");
                confirmPin = sc.next();
                if (newPin.length() == 4) {
                    if (!newPin.equals(confirmPin))
                        System.out.println("Pin doesn't match. Please try again.");

                    else if (newPin.equals(oldPin))
                        System.out.println("\nNew pin cannot be same as old Pin, please enter a different Pin!");

                    else if (!newPin.matches("\\d+")){
                        System.out.println("Pin cannot contain characters, Please use numbers only.");
                    }

                    else {
                        account.setPin(Integer.parseInt(newPin));
                        System.out.println("Pin changed successfully!");
                        break;
                    }
                }
                else System.out.println("Pin length should be 4 only.");
            }while (true) ;
        } else System.out.println("Incorrect Pin, Pin change failed.");
    }

    public static void main(String[] args) throws IOException {
        int pin, choice = 0;
        String accountNo;
        Account account;

        account = new Account("Atharv Ajit Patil", 1204, "87511208", 17);    // Acc = 1200004, pin = 1204
        users.put(account.getAccountNo(), account);

        account = new Account("Utkarsh Vikram Patil", 7077, "9021231195", 17);    // Acc = 1200005, pin = 7077
        users.put(account.getAccountNo(), account);

        account = new Account("Vishal Mahadev Munde", 1705, "9887991121", 18);    // Acc = 1200006, pin = 1705
        users.put(account.getAccountNo(), account);

        account = new Account("Shivakant Munde", 1234, "8551275642", 20);   // Acc = 1200007, pin = 1234
        users.put(account.getAccountNo(), account);

        account = new Account("Prashant Yewele", 0711, "1145", 19);    // Acc = 1200008, pin = 0711
        users.put(account.getAccountNo(), account);

        System.out.println("#########################\n     Welcome to ATM!\n#########################");
        System.out.println("Enter a key to continue!");
        System.in.read();
        System.out.println("\n\nPlease enter your account information to proceed!");
        do {
            System.out.print("Enter your Account number: ");
            accountNo = sc.next();
            System.out.print("Enter your pin: ");
            pin = sc.nextInt();
            if (authenticate(accountNo, pin)) {
                try {
                    account = users.get(accountNo);
                    System.out.println("\nAuthentication successful");
                    System.out.println("Press Enter to continue.");
                    System.in.read();
                    System.out.println("\n\n\nWelcome, " + account.getName());
                    do {
                        try {
                            System.out.println("###############################\n         ATM Machine\n###############################");
                            System.out.println("Menu:");
                            System.out.println("    1. Check Balance,\n    2. Deposit,\n    3. Withdraw,\n    4. Change Pin,\n    5. Logout Account,\n    6. Exit Program.");
                            System.out.print("Enter choice (1-6): ");
                            choice = sc.nextInt();
                            switch (choice) {
                                case 1:
                                    System.out.println("Your Current account balance is Rs." + account.getBalance());
                                    break;
                                case 2:
                                    deposit(account);
                                    break;
                                case 3:
                                    withdraw(account);
                                    break;
                                case 4:
                                    changePin(account);
                                    break;
                                case 5:
                                    choice = 0;
                                    break;
                                case 6:
                                    choice = -1;
                                    break;
                                default:
                                    System.out.println("Invalid Input, please enter a valid choice.");
                            }
                        }catch(InputMismatchException IOMismatch) {
                            System.out.println("Invalid input, please enter the correct value.");
                            sc.next();
                            choice = 10;
                        } catch (Exception e) {
                            System.out.println(e);
                            sc.next();
                            choice = 10;
                        }
                        finally{
                            System.out.println("\nPress enter to continue...");
                            System.in.read();
                            System.out.println("\n\n\n");
                        }
                    } while (choice > 0);
                } catch (InputMismatchException IOMismatch) {
                    System.out.println("Please enter a valid input from the given choices!");
                    sc.next();
                    choice = 10;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            else
                System.out.println("Invalid Account Number or Pin, Please Try again!\n\n");
        } while (choice >= 0);
        System.out.println("Thank you for using this ATM!");
    }
}