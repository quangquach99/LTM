/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_atm;

import common_atm.Account;
import common_atm.User;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Taotaikhoan extends javax.swing.JFrame {
     
    Connection cont,cont1;
    ArrayList<Account> account;
    Utils utils;
    ArrayList<User> user;
    Server_ATM server;
    /**
     * Creates new form Taotaikhoan
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public Taotaikhoan() throws SQLException, ClassNotFoundException {
        initComponents();
        setLocationRelativeTo(null);
        init();
        try {
            server = new Server_ATM();
            cont = server.cont;
            cont1 = server.cont1;
        } catch (RemoteException ex) {
            Logger.getLogger(Xoataikhoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //
    private void init(){
        rdbtMain.setSelected(true);
        tfnumAccount.setEnabled(false);
        tfcmndmain.setEnabled(false);
    }
    //reset
    private void reset(){
        tfDiachi.setText("");
        tfPhone.setText("");
        tfTen.setText("");
        tfcmnd.setText("");
        tfnumAccount.setText("");
        tfcmndmain.setText("");
    }
     //random string
    private String randomString(int size){
        String str01 = "abcdefghijklmnopqrstuvwxyz";
        String str02 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String str03 = "0123456789";
        String strValid = str01 + str02 + str03;
        Random random = new Random();
        
        String mystring = "";
        for( int i=0; i<size; i++ ) {
             int randnum = random.nextInt(strValid.length()); 
             mystring = mystring + strValid.charAt(randnum);
        } 
        return mystring; 
    }    
    public boolean addAccount(User user,boolean main) throws SQLException, ClassNotFoundException{
        Account acc;
        int id_parent;
        if(main){
            String numAccount;
            do{
                numAccount = randomString(12);
            }while(isExistsNumAccount(numAccount));
            acc =new Account(user.getName(), randomString(6), numAccount, 50000);
            id_parent=0;
        }else{
            if(tfcmndmain.getText().equals(tfcmnd.getText())){ 
                return false;
            }
            double balance = server.getAccount(tfnumAccount.getText(), tfcmndmain.getText()).getBalance();
            id_parent = server.getUser(tfnumAccount.getText(), tfcmndmain.getText()).getId();
            acc =new Account(user.getName(), randomString(6), tfnumAccount.getText(), balance);
        }
        //thêm vào bảng user
        String sql="insert into user(name,address,phone,cmnd,id_parent) values(?,?,?,?,?)";
        PreparedStatement ps;
        ps=cont.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getName());
        ps.setString(2, user.getAddress());
        ps.setString(3, user.getPhone());
        ps.setString(4, user.getCmnd());
        ps.setInt(5, id_parent);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        int id_user=0;
        if (rs.next()) {
            id_user = rs.getInt(1);
        }
        //backup dữ liệu cho bảng user
        ps=cont1.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getName());
        ps.setString(2, user.getAddress());
        ps.setString(3, user.getPhone());
        ps.setString(4, user.getCmnd());
        ps.setInt(5, id_parent);
        ps.executeUpdate();
        rs = ps.getGeneratedKeys();
        int id_user1=0;
        if (rs.next()) {
            id_user1 = rs.getInt(1);
        }
        
        //thêm vào bảng account
        sql="insert into account(username,pin,numAccount,balance) values(?,?,?,?)";
        ps=cont.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, acc.getNameUser());
        ps.setString(2, acc.getPin());
        ps.setString(3, acc.getNumAccount());
        ps.setDouble(4, acc.getBalance());
        ps.executeUpdate();
        rs=ps.getGeneratedKeys();
        int id_account=0;
        if (rs.next()) {
            id_account = rs.getInt(1);
        }
        //backup dữ liệu cho bảng account
        ps=cont1.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, acc.getNameUser());
        ps.setString(2, acc.getPin());
        ps.setString(3, acc.getNumAccount());
        ps.setDouble(4, acc.getBalance());
        ps.executeUpdate();
        rs=ps.getGeneratedKeys();
        int id_account1=0;
        if (rs.next()) {
            id_account1 = rs.getInt(1);
        }
        //thêm vào bảng user_account
        sql="insert into user_account(id_user,id_account) values(?,?)";
        ps=cont.prepareStatement(sql);
        ps.setInt(1, id_user);
        ps.setInt(2,id_account);
        ps.executeUpdate();
        //backup dữ liệu cho bảng user_account
        ps=cont1.prepareStatement(sql);
        ps.setInt(1, id_user1);
        ps.setInt(2,id_account1);
        ps.executeUpdate();
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        tfTen = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfDiachi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfPhone = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfcmnd = new javax.swing.JTextField();
        btReset = new javax.swing.JButton();
        btAdd = new javax.swing.JButton();
        rdbtMain = new javax.swing.JRadioButton();
        rdbtPhu = new javax.swing.JRadioButton();
        lbLink = new javax.swing.JLabel();
        tfnumAccount = new javax.swing.JTextField();
        rtBack = new javax.swing.JButton();
        tfcmndmain = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Tên người dùng : ");

        tfTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTenActionPerformed(evt);
            }
        });

        jLabel2.setText("Địa chỉ               : ");

        tfDiachi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfDiachiActionPerformed(evt);
            }
        });

        jLabel3.setText("Số điện thoại     : ");

        tfPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPhoneActionPerformed(evt);
            }
        });

        jLabel4.setText("Số cmnd            : ");

        btReset.setText("Reset");
        btReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btResetActionPerformed(evt);
            }
        });

        btAdd.setText("Thêm");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbtMain);
        rdbtMain.setText("chủ thẻ chính");
        rdbtMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtMainActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbtPhu);
        rdbtPhu.setText("chủ thẻ phụ");
        rdbtPhu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtPhuActionPerformed(evt);
            }
        });

        lbLink.setText("liên kết với tài khoản có : ");

        tfnumAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfnumAccountActionPerformed(evt);
            }
        });

        rtBack.setText("back");
        rtBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rtBackActionPerformed(evt);
            }
        });

        jLabel5.setText("numAccount : ");

        jLabel6.setText("cmnd            :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btAdd)
                                .addGap(18, 18, 18)
                                .addComponent(btReset))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(lbLink))
                                .addGap(50, 50, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdbtMain)
                        .addGap(21, 21, 21)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbtPhu)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tfDiachi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                .addComponent(tfTen, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(tfPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(tfcmnd, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addContainerGap(47, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(rtBack)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 103, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfnumAccount)
                    .addComponent(tfcmndmain, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel5, jLabel6});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btAdd, btReset, rtBack});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfDiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(tfcmnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbtPhu)
                    .addComponent(rdbtMain))
                .addGap(18, 18, 18)
                .addComponent(lbLink)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfnumAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfcmndmain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAdd)
                    .addComponent(btReset)
                    .addComponent(rtBack))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTenActionPerformed

    private void tfDiachiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfDiachiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfDiachiActionPerformed

    private void tfPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPhoneActionPerformed

    private void tfnumAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfnumAccountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfnumAccountActionPerformed

    private void rdbtPhuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtPhuActionPerformed
       tfnumAccount.setEnabled(true);
       tfcmndmain.setEnabled(true);
    }//GEN-LAST:event_rdbtPhuActionPerformed

    private void rdbtMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtMainActionPerformed
        tfnumAccount.setEnabled(false);
        tfnumAccount.setText("");
        tfcmndmain.setEnabled(false);
        tfcmndmain.setText("");
    }//GEN-LAST:event_rdbtMainActionPerformed

    private boolean isExistsCmnd(String cmnd) throws SQLException{
        String sql="select * from user where user.cmnd =?";
        PreparedStatement ps=cont.prepareStatement(sql);
        ps.setString(1,cmnd);
        return ps.executeQuery()==null;
    }
    private boolean isExistsNumAccount(String numAccount) throws SQLException{
        String sql="select * from account where account.numAccount =?";
        PreparedStatement ps=cont.prepareStatement(sql);
        ps.setString(1,numAccount);
        return ps.executeQuery()==null;
    }
    
    private boolean validateAddAccount(boolean main) throws SQLException{
        if(main){
           if(tfDiachi.getText().equals("")||tfPhone.getText().equals("")
                ||tfTen.getText().equals("")||tfcmnd.getText().equals("")){
                return false;
            }
          
        }else{
            if(tfDiachi.getText().equals("")||tfPhone.getText().equals("")
                ||tfTen.getText().equals("")||tfcmnd.getText().equals("")
                    ||tfnumAccount.getText().equals("")||tfcmndmain.getText().equals("")){
                return false;
            }
        } 
        return true;
    }
    
    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        try {
            if(validateAddAccount(rdbtMain.isSelected())){
                if(rdbtPhu.isSelected()){
                    if(server.getUser(tfnumAccount.getText(), tfcmndmain.getText())==null){
                        JOptionPane.showMessageDialog(null, "Không có người dùng này", "Warning",
                        JOptionPane.WARNING_MESSAGE);  
                        return;
                    }
                }
                if(addAccount(new User(tfTen.getText(), tfDiachi.getText(),
                tfPhone.getText(), tfcmnd.getText()),rdbtMain.isSelected())){
                    JOptionPane.showMessageDialog(null, "Thêm tài khoản thành công"
                        ,"Success", JOptionPane.INFORMATION_MESSAGE);  
                    reset();
                    init();
                }else{
                    JOptionPane.showMessageDialog(null, "Không thể tạo tài khoản bằng số cmnd này", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                    tfcmnd.setText("");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Bạn chưa điền đủ thông tin", "Warning",
                JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Taotaikhoan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Taotaikhoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btAddActionPerformed

    private void btResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btResetActionPerformed
       reset();
    }//GEN-LAST:event_btResetActionPerformed

    private void rtBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rtBackActionPerformed
        try {
            new GUI().setVisible(true);
            dispose();
        } catch (RemoteException ex) {
            Logger.getLogger(Taotaikhoan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Taotaikhoan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Taotaikhoan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(Taotaikhoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rtBackActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Taotaikhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Taotaikhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Taotaikhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Taotaikhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Taotaikhoan().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Taotaikhoan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Taotaikhoan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btReset;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lbLink;
    private javax.swing.JRadioButton rdbtMain;
    private javax.swing.JRadioButton rdbtPhu;
    private javax.swing.JButton rtBack;
    private javax.swing.JTextField tfDiachi;
    private javax.swing.JTextField tfPhone;
    private javax.swing.JTextField tfTen;
    private javax.swing.JTextField tfcmnd;
    private javax.swing.JTextField tfcmndmain;
    private javax.swing.JTextField tfnumAccount;
    // End of variables declaration//GEN-END:variables
}
