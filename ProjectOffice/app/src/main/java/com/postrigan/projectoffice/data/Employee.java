package com.postrigan.projectoffice.data;

public class Employee {
    private final int _id;
    private String lastName;
    private String firstName;
    private String login;
    private String phoneNum;

    public Employee(int _id, String lastName, String firstName, String login, String phoneNum) {
        this._id = _id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.phoneNum = phoneNum;
    }

    public int get_id() {
        return _id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return lastName + " " + firstName.charAt(0) + ".";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
