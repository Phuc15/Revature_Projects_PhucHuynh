package org.bank.service;

import org.apache.log4j.Logger;

/**
 * This is a main class where the program starts
 */
public class BankMain {
    private static Logger logger = Logger.getLogger(BankMain.class);

    public static void main(String[] args) {
        BankDisplayMenu bankDriver = new BankDisplayMenu();
        logger.info("------------------------------------------------------");
        logger.info("Welcome to Billy Bank!!!! ");
        logger.info("-------------------------------------------------------");
        bankDriver.displayMenu();
    }

}










