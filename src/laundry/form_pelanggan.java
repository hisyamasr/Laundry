
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Aas1
 */
public class form_pelanggan extends javax.swing.JFrame {
    private Connection con;
    private Statement stat;
    private ResultSet res;
    private String t;
    
    
    konek kon = new konek();
    public Date date = new Date();
    public SimpleDateFormat noformat = new SimpleDateFormat("yyMM");
    
    private void koneksi(){
    try {
    Class.forName("com.mysql.jdbc.Driver");
    con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/laundry","root","");
    stat=(Statement) con.createStatement();
    } catch (ClassNotFoundException | SQLException e) {
    JOptionPane.showMessageDialog(null, e);
    }
    }
    
    
    
    public String nomor() {
        String urutan = null;
//        try {            
//            String datasql = "select right(kode_pelanggan,3)+1 as kopel from pelanggan order by kode_pelanggan desc";
//System.out.println(datasql);
//            kon.rs = kon.st.executeQuery(datasql);

//            kon.rs = kon.st.executeQuery("");
//            System.out.println(kon.rs);            
//            if (kon.rs.next()) {
//                urutan = kon.rs.getString(1);
//
//                while (urutan.length() < 3) {
//                    urutan = "0" + urutan;
//                }
//                urutan = "P" + noformat.format(date) + urutan;
//                
//            } else {
//                urutan = "P" + noformat.format(date) + "001";
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
         try{ 
         res=stat.executeQuery("select right(kode_pelanggan,3)+1 as kopel from pelanggan order by kode_pelanggan desc"); 
if (res.next()) {
                urutan = res.getString("kopel");
//
                while (urutan.length() < 3) {
                    urutan = "0" + urutan;
                }
                urutan = "P" + noformat.format(date) + urutan;
//                
            } else {
                urutan = "P" + noformat.format(date) + "001";
//            }
//         urutan ="123";
                 
         }
         }catch (Exception e) { 
         JOptionPane.showMessageDialog(rootPane, e); 
         }        




        return urutan;
    }
    private void kosongkan()
        {
        txt_id_pelanggan.setText("");
        txt_kode_pelanggan.setText(""); 
        txt_nama_pelanggan.setText(""); 
        txt_alamat.setText("");
        txt_no_telepon.setText("");
        txt_kode_pelanggan.requestFocus();
         } 
    /**
     * Creates new form FrDataAkun
     */
    public form_pelanggan() {
        initComponents();
        setTitle("Data Pelanggan");
        koneksi();
        
        tabel();
        kosongkan();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(
        (screenSize.width - frameSize.width) / 2,
        (screenSize.height - frameSize.height) / 2);
    }
    
    private void tabel(){ 
        DefaultTableModel t= new DefaultTableModel();
         t.addColumn("Kode"); 
         t.addColumn("Nama Pelanggan"); 
         t.addColumn("Alamat");
         t.addColumn("No Telepon");
         jTable1.setModel(t); 
         try{ 
         res=stat.executeQuery("select * from pelanggan"); 
         while (res.next()) { 
         t.addRow(new Object[]{ res.getString("kode_pelanggan"),
         res.getString("nama_pelanggan"), 
         res.getString("alamat"), 
         res.getString("no_telepon_pelanggan") 
         }); 
         }
         }catch (Exception e) { 
         JOptionPane.showMessageDialog(rootPane, e); 
         }
    }
    
        private void nonaktif()
    {
        txt_nama_pelanggan.setEnabled(false);
        txt_alamat.setEnabled(false);
        txt_no_telepon.setEnabled(false);
        btsimpan.setEnabled(false);
        btubah.setEnabled(false);
        bthapus.setEnabled(false);
        btbatal.setEnabled(false);
    }
    
    private void aktif(){
          txt_nama_pelanggan.setEnabled(true);
          txt_alamat.setEnabled(true);
          txt_no_telepon.setEnabled(true);
          btsimpan.setEnabled(true);
          btubah.setEnabled(true);
          bthapus.setEnabled(true);
          btbatal.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        bttambah = new javax.swing.JButton();
        btcari = new javax.swing.JButton();
        btsimpan = new javax.swing.JButton();
        btubah = new javax.swing.JButton();
        bthapus = new javax.swing.JButton();
        btbatal = new javax.swing.JButton();
        btkembali = new javax.swing.JButton();
        txt_kode_pelanggan = new javax.swing.JTextField();
        txt_nama_pelanggan = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        txt_no_telepon = new javax.swing.JTextField();
        txt_id_pelanggan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 440, 131));

        bttambah.setBackground(new java.awt.Color(153, 204, 255));
        bttambah.setText("Tambah");
        bttambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttambahActionPerformed(evt);
            }
        });
        jPanel5.add(bttambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, 100, 35));

        btcari.setBackground(new java.awt.Color(153, 204, 255));
        btcari.setText("Cari");
        btcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcariActionPerformed(evt);
            }
        });
        jPanel5.add(btcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 100, 35));

        btsimpan.setBackground(new java.awt.Color(153, 204, 255));
        btsimpan.setText("Simpan");
        btsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsimpanActionPerformed(evt);
            }
        });
        jPanel5.add(btsimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 80, 35));

        btubah.setBackground(new java.awt.Color(153, 204, 255));
        btubah.setText("Ubah");
        btubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btubahActionPerformed(evt);
            }
        });
        jPanel5.add(btubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 130, 80, 35));

        bthapus.setBackground(new java.awt.Color(153, 204, 255));
        bthapus.setText("Hapus");
        bthapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bthapusActionPerformed(evt);
            }
        });
        jPanel5.add(bthapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 80, 35));

        btbatal.setBackground(new java.awt.Color(153, 204, 255));
        btbatal.setText("Batal");
        btbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbatalActionPerformed(evt);
            }
        });
        jPanel5.add(btbatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 180, 80, 35));

        btkembali.setBackground(new java.awt.Color(153, 204, 255));
        btkembali.setText("Kembali");
        btkembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btkembaliActionPerformed(evt);
            }
        });
        jPanel5.add(btkembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 250, 80, 33));

        txt_kode_pelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_kode_pelangganKeyPressed(evt);
            }
        });
        jPanel5.add(txt_kode_pelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 190, 20));

        txt_nama_pelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nama_pelangganActionPerformed(evt);
            }
        });
        jPanel5.add(txt_nama_pelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 190, 20));

        txt_alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_alamatActionPerformed(evt);
            }
        });
        jPanel5.add(txt_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 190, 20));

        txt_no_telepon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_no_teleponActionPerformed(evt);
            }
        });
        jPanel5.add(txt_no_telepon, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 191, 20));

        txt_id_pelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_pelangganActionPerformed(evt);
            }
        });
        jPanel5.add(txt_id_pelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 320, 88, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/3.png"))); // NOI18N
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_kode_pelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_kode_pelangganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kode_pelangganKeyPressed

    private void btcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcariActionPerformed
        // TODO add your handling code here:
         aktif();
        try { 
        res=stat.executeQuery("select * from pelanggan where "+ "kode_pelanggan='" +txt_kode_pelanggan.getText()
        +"'" );
        while (res.next()) 
        {
            txt_id_pelanggan.setText(res.getString("id_pelanggan"));
            txt_kode_pelanggan.setText(res.getString("kode_pelanggan")); 
            txt_nama_pelanggan.setText(res.getString("nama_pelanggan"));
            txt_alamat.setText(res.getString("alamat"));
            txt_no_telepon.setText(res.getString("no_telepon_pelanggan"));
        }                                        
         }       
        catch (Exception e) { 
        JOptionPane.showMessageDialog(rootPane, e); 
}
    }//GEN-LAST:event_btcariActionPerformed

    private void btsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsimpanActionPerformed
        // TODO add your handling code here:
         aktif();
        
        try {
        stat.executeUpdate("insert into pelanggan values (NULL," 
       + "'" + txt_kode_pelanggan.getText()+"',"
       + "'" + txt_nama_pelanggan.getText()+"',"
       + "'" + txt_alamat.getText()+"',"
       + "'" + txt_no_telepon.getText()+"')");
       JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data"); 
       } catch (SQLException | HeadlessException e) { 
       JOptionPane.showMessageDialog(null, "Perintah Salah : "+e);
       }finally{
                tabel();
                kosongkan();
                txt_kode_pelanggan.setEnabled(true);
        } 
    }//GEN-LAST:event_btsimpanActionPerformed

    private void btubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btubahActionPerformed
        // TODO add your handling code here:
        try {
            stat.executeUpdate("update pelanggan set "
                + "kode_pelanggan='"+txt_kode_pelanggan.getText()+"',"
                + "nama_pelanggan='"+txt_nama_pelanggan.getText()+"',"
                + "alamat='"+txt_alamat.getText()+"',"
                + "no_telepon_pelanggan='"+txt_no_telepon.getText()+"'"

                + " where " + "id_pelanggan='"+txt_id_pelanggan.getText()+"'" );
            
            JOptionPane.showMessageDialog(rootPane, "Data berhasil Diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }finally{
         tabel();
         kosongkan();
        }
    }//GEN-LAST:event_btubahActionPerformed

    private void bthapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bthapusActionPerformed
        // TODO add your handling code here:
        try { 
            stat.executeUpdate("delete from pelanggan where " 
            + "kode_pelanggan='"+txt_kode_pelanggan.getText()
            +"'" ); 
            kosongkan(); 
            JOptionPane.showMessageDialog(null, "Berhasil");
             } catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "pesan salah : "+e);
             }finally{
                     tabel();
          }
    }//GEN-LAST:event_bthapusActionPerformed

    private void btbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbatalActionPerformed
        // TODO add your handling code here:
        kosongkan();
        nonaktif();
        txt_kode_pelanggan.setEnabled(true);
    }//GEN-LAST:event_btbatalActionPerformed

    private void btkembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btkembaliActionPerformed
        // TODO add your handling code here:
        new form_utama().setVisible(true);
        dispose();
    }//GEN-LAST:event_btkembaliActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int i = jTable1.getSelectedRow();
        if (i == -1){
            return;
        }        
        String kode_pelanggan = (String) jTable1.getValueAt(i,0);
        txt_kode_pelanggan.setText(kode_pelanggan);
        String nama_pelanggan = (String) jTable1.getValueAt(i,1);
        txt_nama_pelanggan.setText(nama_pelanggan);
        String alamat = (String) jTable1.getValueAt(i,2);
        txt_alamat.setText(alamat);
        String no_telepon_pelanggan = (String) jTable1.getValueAt(i,3);
        txt_no_telepon.setText(no_telepon_pelanggan); 
        
    try { 
        res=stat.executeQuery("select * from pelanggan where "+ "kode_pelanggan='" +txt_kode_pelanggan.getText()
        +"'" );
        while (res.next()) 
        {
            txt_id_pelanggan.setText(res.getString("id_pelanggan"));
        }                                        
         }       
        catch (Exception e) { 
        JOptionPane.showMessageDialog(rootPane, e); 
}    
        
        
        
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        nonaktif();
        tabel();
    }//GEN-LAST:event_formWindowActivated

    private void bttambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttambahActionPerformed
        // TODO add your handling code here:
        aktif();
        
        kosongkan();
        txt_kode_pelanggan.setText(nomor());
        txt_kode_pelanggan.setEnabled(false);
    }//GEN-LAST:event_bttambahActionPerformed

    private void txt_no_teleponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_no_teleponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_no_teleponActionPerformed

    private void txt_id_pelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_pelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_pelangganActionPerformed

    private void txt_alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_alamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_alamatActionPerformed

    private void txt_nama_pelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nama_pelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nama_pelangganActionPerformed

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
            java.util.logging.Logger.getLogger(form_pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_pelanggan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btbatal;
    private javax.swing.JButton btcari;
    private javax.swing.JButton bthapus;
    private javax.swing.JButton btkembali;
    private javax.swing.JButton btsimpan;
    private javax.swing.JButton bttambah;
    private javax.swing.JButton btubah;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_id_pelanggan;
    private javax.swing.JTextField txt_kode_pelanggan;
    private javax.swing.JTextField txt_nama_pelanggan;
    private javax.swing.JTextField txt_no_telepon;
    // End of variables declaration//GEN-END:variables
}
