/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry;

/**
 *
 * @author Aas
 */

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;

//public class classkoneksi {
   //public static Connection con;
    //public static Statement stm;
    //public static void main(String args[]){
        //try {
            //String url ="jdbc:mysql://localhost/laundry";
            //String user="root";
            //String pass="";
            //Class.forName("com.mysql.jdbc.Driver");
            //con =DriverManager.getConnection(url,user,pass);
            //stm = con.createStatement();
            //System.out.println("koneksi berhasil;");
            //} catch (Exception e) {
            //System.err.println("koneksi gagal" +e.getMessage());
        //}
    //}
//}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class classkoneksi { // koneksi ke database
    private static Connection mysqlkonek;
    public static Connection koneksiDB() throws SQLException {
        if(mysqlkonek==null){
            try {
                String DB="jdbc:mysql://localhost:3306/laundry"; // delta_db database
                String user="root"; // user database
                String pass=""; // password database
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                mysqlkonek = (Connection) DriverManager.getConnection(DB,user,pass);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"gagal koneksi karena "+e);
            }
        }
        return mysqlkonek;
    }
}