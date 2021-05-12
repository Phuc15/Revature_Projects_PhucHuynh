package org.bank.daos;

import org.bank.exeption.BankException;
import org.bank.model.Employee;

public interface EmployeeDAO {

    public void registerForEmployeeAccount(Employee employee) throws BankException;
    public boolean validateEmployeeAccount(String username, String password) throws BankException;

}
