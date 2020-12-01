package server_atm;

import Connect.Connect;
import common_atm.Account;
import common_atm.Bank;
import common_atm.User;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Server_ATM  extends UnicastRemoteObject implements Bank{
    String dbName="bank";
    /**
     * @param args the command line arguments
     */
    Connection cont;
    ArrayList<Account> account;

    @Override
    public Account getAccount(String numAccount,String cmnd) throws SQLException{
        String sql="select a.* from account as a inner join user_account on "
                + "a.id=user_account.id_account inner join user on user_account.id_user = "
                + "user.id where a.numAccount=? and user.cmnd=?";     
        PreparedStatement ps;
        ps=cont.prepareStatement(sql);
        ps.setString(1, numAccount);
        ps.setString(2, cmnd);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return new Account(rs.getString(2), rs.getString(3),
                rs.getString(4),rs.getDouble(5),rs.getInt(1));
        }
        return null;
    }
    
    @Override
    public User getUser(String numAccount,String cmnd) throws SQLException{
        String sql="select u.* from user as u inner join user_account on "
                + "u.id=user_account.id_user inner join account on user_account.id_account = "
                + "account.id where account.numAccount=? and u.cmnd=?";     
        PreparedStatement ps;
        ps=cont.prepareStatement(sql);
        ps.setString(1, numAccount);
        ps.setString(2, cmnd);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return new User(rs.getString(2), rs.getString(3),
                rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(1));
        }
        return null;
    }
    public ResultSet getListAccount() throws SQLException{
        String sql="select u.name as ten,u.phone,u.address,u.cmnd,u.id_parent chu_the,a.pin,a.numAccount,"
                + "a.balance from user as u,account as a,user_account as ua "
                + "where u.id = ua.id_user and a.id = ua.id_account";
        Statement st = cont.createStatement();
        return st.executeQuery(sql);
    }
    @Override
    public ArrayList<String> login(String numAccount,String cmnd,String pin) throws SQLException{
        ArrayList<String> userInfo = new ArrayList<>();
        String sql="select account.numAccount,u.cmnd,account.pin from user as u inner join user_account on "
                + "u.id=user_account.id_user inner join account on user_account.id_account = "
                + "account.id where account.numAccount=? and u.cmnd=? and account.pin=?";     
        PreparedStatement ps;
        ps=cont.prepareStatement(sql);
        ps.setString(1, numAccount);
        ps.setString(2, cmnd);
        ps.setString(3, pin);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            userInfo.add(numAccount);
            userInfo.add(cmnd);
            userInfo.add(pin);
            return userInfo;
        }
        return null;
    }

    public void init(Server_ATM server_atm) throws RemoteException, ClassNotFoundException, SQLException, AlreadyBoundException{
        System.out.println("ATM starting...");

        LocateRegistry.createRegistry(9999);
        Registry registry = LocateRegistry.getRegistry(9999);
        registry.bind("atm",server_atm);
    }
    public Server_ATM() throws RemoteException, ClassNotFoundException, SQLException{
        cont = Connect.getMySQLConnection(dbName);
    }
    @Override
    public ArrayList<String> withdraw(String numAccount,String cmnd,double amount) throws RemoteException,SQLException {
        ArrayList<String> loi = new ArrayList<>();
        Account acc = getAccount(numAccount,cmnd);
        if(amount <= 0){
            loi.add("Số tiền rút phải lớn hơn 0");
        }else{
            if(acc.getBalance() < amount){
                loi.add("Số tiền rút lớn hơn số dư trong tài khoản");
            }else{
                double balance = acc.getBalance() - amount;
                String sql="update account set balance =? where numAccount =?";
                PreparedStatement ps;
                ps=cont.prepareStatement(sql);
                ps.setDouble(1, balance);
                ps.setString(2, numAccount);
                ps.executeUpdate();
            }
        }
        return loi;
    }

    @Override
    public ArrayList<String> inquiry(String numAccount,String cmnd) throws RemoteException, SQLException {
        ArrayList<String> result = new ArrayList<>();
        String sql="select u.name,u.address,u.phone,a.numAccount,u.cmnd,"
                + "a.balance from user as u,account as a,user_account as ua "
                + "where u.id = ua.id_user and a.id = ua.id_account and a.numAccount=? and u.cmnd=?";
        PreparedStatement ps;
        ps=cont.prepareStatement(sql);
        ps.setString(1, numAccount);
        ps.setString(2, cmnd);
        ResultSet rs = ps.executeQuery();
        java.sql.ResultSetMetaData rsm = rs.getMetaData();
        if(rs.next()){
            for(int i=1;i<=rsm.getColumnCount();i++){
                if(i==6){
                   result.add(String.valueOf(rs.getInt(6)));
                }else{
                    result.add(rs.getString(i));
                }
            }
            return result;
        }else{
            return null;
        }
    }
    @Override
    public ArrayList<String> transfer(String numAccount1,String cmnd,String numAccount2,double amount) throws RemoteException, SQLException {
        ArrayList<String> loi = new ArrayList<>();
        if(amount <= 0){
            loi.add("Số tiền chuyển khoản phải lớn hơn 0");
        }else{
            Account acc1 = getAccount(numAccount1);
            Account acc2 = getAccount(numAccount2);
            if(numAccount1.equals(numAccount2)){
                loi.add("Giao dịch thất bại do số tài khoản người nhận là số tài khoản của bạn");
                return loi;
            }
            if(acc1.getBalance() < amount){
                loi.add("Số tiền chuyển khoản lớn hơn số dư trong tài khoản");
                return loi;
            }
            if(acc2==null){
                loi.add("Tài khoản nhập vào không tồn tại");
            }else{
                double balance1 = acc1.getBalance()-amount;
                double balance2 =  acc2.getBalance()+amount;
                String sql="update account as a inner join user_account on "
                + "a.id=user_account.id_account inner join user on user_account.id_user = "
                + "user.id set balance =? where numAccount =?";
                PreparedStatement ps;
                ps=cont.prepareStatement(sql);
                ps.setDouble(1, balance2);
                ps.setString(2, numAccount2);
               // ps.setString(3, cmnd2);
                ps.executeUpdate();

                sql = "update account as a inner join user_account on "
                + "a.id=user_account.id_account inner join user on user_account.id_user = "
                + "user.id set balance =? where numAccount =?";
                ps=cont.prepareStatement(sql);
                ps.setDouble(1, balance1);
                ps.setString(2, numAccount1);
               // ps.setString(3, cmnd1);
                ps.executeUpdate();
            }   
        }
        return loi;
    }

    @Override
    public ArrayList<String> deposit(String numAccount,String cmnd, double amount) throws RemoteException,SQLException {
        ArrayList<String> loi = new ArrayList<>();
        if(amount <= 0){
            loi.add("Số tiền gửi phải lớn hơn 0");
        }else{
            String sql="update account set balance = balance + ? where numAccount =?";
            PreparedStatement ps;
            ps=cont.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setString(2, numAccount);
            ps.executeUpdate();
        }
        return loi;
    }

    @Override
    public Account getAccount(String numAccount) throws RemoteException, SQLException {
        String sql="select * from account where numAccount=? limit 1";     
        PreparedStatement ps;
        ps=cont.prepareStatement(sql);
        ps.setString(1, numAccount);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return new Account(rs.getString(2), rs.getString(3),
                rs.getString(4),rs.getDouble(5),rs.getInt(1));
        }
        return null;
    }

    @Override
    public void doimatkhau(String numAccount,String cmnd,String pw) throws RemoteException, SQLException {
        String sql="update account as a inner join user_account as ua on "
                + "a.id=ua.id_account inner join user as u on ua.id_user = "
                + "u.id set pin=? "
                + "where a.numAccount=? and u.cmnd=?";
        PreparedStatement ps;
        ps=cont.prepareStatement(sql);
        ps.setString(2, numAccount);
        ps.setString(1, pw);
        ps.setString(3, cmnd);
        ps.executeUpdate();
    }
    
}
