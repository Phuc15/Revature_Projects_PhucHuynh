package org.bank.model;

public class Employee {

    private String employeeUsername;
    private String employeePassword;
    private String employeeName;

    public Employee(String employeeUsername, String employeePassword, String employeeName) {
        this.employeeUsername = employeeUsername;
        this.employeePassword = employeePassword;
        this.employeeName = employeeName;
    }

    public Employee(){}

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeUsername='" + employeeUsername + '\'' +
                ", employeePassword='" + employeePassword + '\'' +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
}
