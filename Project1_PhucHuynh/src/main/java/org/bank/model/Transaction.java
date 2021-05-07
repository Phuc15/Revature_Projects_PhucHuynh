package org.bank.model;
/**
 * Transaction model class is created to  provide info about transaction and control access of the transaction data since
 * the instance variables  in Transaction account are encapsulated
 */

public class Transaction {

    private double previousTransaction;
    private double pendingTransaction;
    private int transactionId;

    public Transaction(double previousTransaction, double pendingTransaction, int transactionId) {
        this.previousTransaction = previousTransaction;
        this.pendingTransaction = pendingTransaction;
        this.transactionId = transactionId;
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


    @Override
    public String toString() {
        return "Transaction{" +
                "previousTransaction=" + previousTransaction +
                ", pendingTransaction=" + pendingTransaction +
                ", transactionId=" + transactionId +
                '}';
    }
}
