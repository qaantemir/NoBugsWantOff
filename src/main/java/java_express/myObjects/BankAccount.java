package java_express.myObjects;

public class BankAccount {
    private String owner;
    private double balance;

    public BankAccount(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void deposit(double amount) {this.balance += amount;}
    public void withdraw(double amount) {this.balance -= amount;}

    public void printBalance() {
        System.out.println(owner + " balance is: " + balance);
    }
}
