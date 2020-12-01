/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_atm;

import common_atm.Account;
import common_atm.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Utils {
    Connection cont;
    String dbName = "bank";
    public Utils() throws SQLException, ClassNotFoundException {
        if(cont==null){
            cont=Connect.Connect.getMySQLConnection(dbName);
        }
    }
    
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
}
