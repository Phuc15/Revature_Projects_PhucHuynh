package org.bank.model;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Transaction model class is created to  provide info about transaction and control access of the transaction data since
 * the instance variables  in Transaction account are encapsulated
 */

public class Transaction {

    private double previousTransaction;
    private double pendingTransaction;
    private int transactionId;
    private String date;
    private int accountId;

    public Transaction(double previousTransaction, double pendingTransaction, int transactionId, String date, int accountId) {
        this.previousTransaction = previousTransaction;
        this.pendingTransaction = pendingTransaction;
        this.transactionId = transactionId;
        this.date = date;
        this.accountId = accountId;
    }

    public Transaction() {
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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

    public String getDate() {
        return date;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "previousTransaction=" + previousTransaction +
                ", pendingTransaction=" + pendingTransaction +
                ", transactionId=" + transactionId +
                ", date='" + date + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
