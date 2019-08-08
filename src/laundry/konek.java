/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aas1
 */
package laundry;
import com.mysql.jdbc.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;



public class konek {
    Connection con;
    Statement st;
    ResultSet rs;
    
    public Connection setKoneksi(){
    try {
        Class.forName("com.mysql.jdbc.Driver");
        con = (Connection) 
        DriverManager.getConnection("jdbc:mysql://localhost/laundry","root","");
        st=con.createStatement();
    }
    catch (Exception e)
    {
       JOptionPane.showMessageDialog(null,"<Error> Koneksikan Xampp Terlebih Dahulu : "+e,"Koneksi Gagal",JOptionPane.WARNING_MESSAGE);
    }
    return con;
    }

    Statement createStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
