package org.bank.model;

/**
 * Bank account model class is created to provide info about bank account and control access to the bank account data since
 * the instance variables  in bank account are encapsulated
 */


public class BankAccount {

    private String accountType;
    private double balance;

    private int accountId;

    public BankAccount(String accountType, double balance, int accountId) {
        this.accountType = accountType;
        this.balance = balance;
        this.accountId = accountId;
    }


    public BankAccount() {
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", accountId=" + accountId +
                '}';
    }
}
