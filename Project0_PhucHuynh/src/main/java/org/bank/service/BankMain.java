package org.bank.service;
import org.apache.log4j.Logger;

public class BankMain {
    private static Logger logger = Logger.getLogger(Math.class);
    public static void main(String[] args) {

        BankDisplayMenu bankDriver = new BankDisplayMenu();
        logger.info("Welcome to ABC Bank!!!! \n");
        bankDriver.displayMenu();
    }

}









