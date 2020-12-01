/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common_atm;

import java.io.Serializable;


public class Account implements Serializable{
    private String nameUser;
    private String pin;
    private String numAccount;
    private double balance;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNumAccount() {
        return numAccount;
    }

    public void setNumAccount(String numAccount) {
        this.numAccount = numAccount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public Account(String nameUser, String pin,String numAccount, double balance) {
        this.nameUser = nameUser;
        this.pin = pin;
        this.numAccount = numAccount;
        this.balance = balance;
    }

    public Account(String nameUser, String pin,String numAccount) {
        this.nameUser = nameUser;
        this.pin = pin;
        this.numAccount = numAccount;
    }

    public Account(String nameUser, String pin, String numAccount, double balance, int id) {
        this.nameUser = nameUser;
        this.pin = pin;
        this.numAccount = numAccount;
        this.balance = balance;
        this.id = id;
    }
    
}
