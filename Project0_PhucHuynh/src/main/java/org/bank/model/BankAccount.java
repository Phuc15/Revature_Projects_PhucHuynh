package org.bank.model;


public class BankAccount {

    private String accountType;
    private double balance;
    private double  previousTransaction;
    private int accountId;

    private double pendingTransaction;

    public BankAccount(String accountType, double balance, double previousTransaction) {
        this.accountType = accountType;
        this.balance = balance;
        this.previousTransaction = previousTransaction;
    }

    public BankAccount(String accountType,double balance, double previousTransaction, int accountId) {
        this.accountType = accountType;
        this.balance = balance;
        this.previousTransaction = previousTransaction;
        this.accountId = accountId;
    }
    public BankAccount(){}

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

    public double getPreviousTransaction() {
        return previousTransaction;
    }

    public void setPreviousTransaction(double previousTransaction) {
        this.previousTransaction = previousTransaction;
    }
    public double getPendingTransaction() {
        return pendingTransaction;
    }

    public void setPendingTransaction(double pendingTransaction) {
        this.pendingTransaction = pendingTransaction;
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
                ", previousTransaction=" + previousTransaction +
                ", accountId=" + accountId +
                '}';
    }
}
