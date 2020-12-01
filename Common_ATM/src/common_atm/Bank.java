/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common_atm;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Bank extends Remote{
    public ArrayList<String> deposit(String numAccount,String cmnd,double amount) throws RemoteException,SQLException;
    public ArrayList<String> withdraw(String numAccount,String cmnd,double amount) throws RemoteException,SQLException;
    public ArrayList<String> inquiry(String numAccount,String cmnd) throws RemoteException,SQLException;
    public ArrayList<String> login(String numAccount,String cmnd,String pin) throws RemoteException,SQLException;
    public ArrayList<String> transfer(String numAccount1,String cmnd,String numAccount2,double amount) 
            throws RemoteException,SQLException;
    public Account getAccount(String numAccount,String cmnd) throws RemoteException,SQLException;
    public Account getAccount(String numAccount) throws RemoteException,SQLException;
    public void doimatkhau(String numAccount,String cmnd,String pw) throws RemoteException,SQLException;
    public User getUser(String numAccount,String cmnd) throws RemoteException,SQLException;
}
