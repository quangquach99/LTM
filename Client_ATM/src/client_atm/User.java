package client_atm;

import common_atm.Bank;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    Bank bank;
    public static void main(String[] args) {}
    public User(){
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry("192.168.43.220",1996);
            bank=(Bank) registry.lookup("atm");
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
