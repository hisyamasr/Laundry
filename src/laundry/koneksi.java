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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class koneksi {
    private Connection akses;
    public Connection akses(){
        try {
            String url = "jdbc:mysql://localhost:3306/laundry";
            akses = (Connection) DriverManager.getConnection(url,"root", "");
        } catch (SQLException ex) {
            Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return akses;
    }    
}
