/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Aas1
 */
public class form_transaksi extends javax.swing.JFrame {

    static String idOrder;
    static String noOrder;
    static String idPelanggan;
    static String namaPelanggan;
    static String tanggalPenerimaan;
    static String tanggalPengambilan = null;
    static String total;
    static String status;
    static String estimasiPengerjaan;

    static String kembalian;
    
    
    

    konek kon = new konek();
    private Object[][] datasementara = null;
    private String[] labelsementara = {"Id Order", "Id Cucian", "Nama Cucian", "Satuan", "Harga", "Jumlah Satuan", "Subtotal"};

    public Date date = new Date();
    public SimpleDateFormat noformat = new SimpleDateFormat("yyMM");
//    public String namaPelanggan;

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    /**
     * Creates new form_penerimaan_cucian
     */
    public form_transaksi() {
        initComponents();
        this.setLocationRelativeTo(null);
                
                        txt_id_order.setVisible(false);

        setTitle("Form Transaksi");
        kon.setKoneksi();

        awal();
    }

    private void bersih() {
        txt_no_order.setText("");
        txt_no_transaksi.setText("");
        txt_nama_pelanggan.setText("");
        txt_tanggal_penerimaan.setText("");
        txt_tanggal_pengambilan.setText("");
        txt_estimasi_pengerjaan.setText("");

        txt_bayar.setText("");

        ltotal.setText("0");
    }

    private void nonaktif() {
        
        btbrowse_no_order.setEnabled(false);
        txt_no_transaksi.setEnabled(false);
        txt_no_order.setEnabled(false);
        txt_tanggal_penerimaan.setEnabled(false);
        txt_tanggal_pengambilan.setEnabled(false);
        txt_estimasi_pengerjaan.setEnabled(false);

        txt_nama_pelanggan.setEnabled(false);

        txt_bayar.setEnabled(true);

        btsimpan.setEnabled(false);
        btbatal.setEnabled(false);
    }

    private void aktif() {
//        btbrowse_no_order.setEnabled(true);
//        txt_no_transaksi.setEnabled(true);
//        txt_nama_pelanggan.setEnabled(true);
        txt_bayar.setEnabled(true);

        btsimpan.setEnabled(false);
        btbatal.setEnabled(true);
    }

    private void awal() {
        nonaktif();
    }

    void setTanggal() {
        java.util.Date skrg = new java.util.Date();
        java.text.SimpleDateFormat kal = new java.text.SimpleDateFormat("yyyy-MM-dd");
        txt_tanggal_pengambilan.setText(kal.format(skrg));
    }

    public String nomor() {
        String urutan = null;
        try {
            kon.rs = kon.st.executeQuery("select right (no_transaksi,4)+1 "
                    + "from transaksi as Nomor order by no_transaksi desc");
            if (kon.rs.next()) {
                urutan = kon.rs.getString(1);
                while (urutan.length() < 4) {
                    urutan = "0" + urutan;
                }
                urutan = "T-" + noformat.format(date) + urutan;
            } else {
                urutan = "T-" + noformat.format(date) + "0001";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return urutan;
    }

    private void TampilTabelSementara() {
        try {
            String sql = "SELECT * FROM `detail_penerimaan_cucian` "
                    + "JOIN jenis_cucian on detail_penerimaan_cucian.id_cucian = jenis_cucian.id_cucian "
                    + "JOIN penerimaan_cucian on detail_penerimaan_cucian.id_order = penerimaan_cucian.id_order "
                    + "where detail_penerimaan_cucian.id_order='" + txt_id_order.getText() + "'";
            kon.rs = kon.st.executeQuery(sql);
            ResultSetMetaData m = kon.rs.getMetaData();
            int kolom = m.getColumnCount();
            int baris = 0;
            while (kon.rs.next()) {
                baris = kon.rs.getRow();
            }
            datasementara = new Object[baris][kolom];
            int x = 0;
            kon.rs.beforeFirst();
            while (kon.rs.next()) {
                datasementara[x][0] = kon.rs.getString("id_order");
                datasementara[x][1] = kon.rs.getString("id_cucian");
                datasementara[x][2] = kon.rs.getString("nama_cucian");
                datasementara[x][3] = kon.rs.getString("satuan");
                datasementara[x][4] = kon.rs.getString("biaya_cucian");
                datasementara[x][5] = kon.rs.getString("jumlah");
                datasementara[x][6] = kon.rs.getString("subtotal");
                x++;
            }
            tbtransaksi.setModel(new DefaultTableModel(datasementara, labelsementara));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    private void SimpanTransaksi() {
        try {
            String sql = "insert into transaksi values(null,'" + txt_no_transaksi.getText() + "',"
                    //                    + "'" + txt_id_pelanggan.getText() + "',"
                    + "'" + txt_id_order.getText() + "','Lunas',"
                    + "'" + txt_tanggal_pengambilan.getText() + "')";
            System.out.println("simpan Transaksi:" + sql);
            kon.st.executeUpdate(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void updatePengambilan() {
        try {
            
            String updateTanggalPengambilan = "UPDATE penerimaan_cucian SET "
                    + "tgl_pengambilan = '"+ txt_tanggal_pengambilan.getText() +"' "
                    + "WHERE penerimaan_cucian.id_order = '"+ txt_id_order.getText() +"'";
            int noOrder = 0;
            kon.st.executeUpdate(updateTanggalPengambilan);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal Update Pengambilan :" +e);
        }
    }
    
    private void HapusTabelSementara() {
        try {
            String sql = "Delete from sementara";
            kon.st.executeUpdate(sql);
            TampilTabelSementara();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    private void HapusIsiSementara() {
        try {
            int row = tbtransaksi.getSelectedRow();
            int x;
            int total = Integer.parseInt(ltotal.getText());
            x = Integer.parseInt((String) tbtransaksi.getValueAt(row, 5));
            total = total - x;
            ltotal.setText(Integer.toString(total));
            String sql = "Delete from sementara where kd_barang='" + (String) tbtransaksi.getValueAt(row, 0) + "'";
            kon.st.executeUpdate(sql);
            TampilTabelSementara();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void cetak() {
        try {
            String file = "src/laporan/struk.jasper";
            HashMap param = new HashMap();
            param.put("no_transaksi", txt_no_transaksi.getText());
            JasperPrint print = JasperFillManager.fillReport(file, param, kon.setKoneksi());
            JasperViewer.viewReport(print, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_bayar = new javax.swing.JTextField();
        btbatal = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btbrowse_no_order = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        btkeluar = new javax.swing.JButton();
        ltotal = new javax.swing.JLabel();
        bttambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbtransaksi = new javax.swing.JTable();
        btsimpan = new javax.swing.JButton();
        txt_id_order = new javax.swing.JTextField();
        txt_estimasi_pengerjaan = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_tanggal_penerimaan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_nama_pelanggan = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_no_order = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_tanggal_pengambilan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        l_kembalian = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_no_transaksi = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_bayarKeyTyped(evt);
            }
        });
        getContentPane().add(txt_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 380, 201, -1));

        btbatal.setText("BATAL");
        btbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbatalActionPerformed(evt);
            }
        });
        getContentPane().add(btbatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, -1, -1));

        jLabel8.setText("Pembayaran");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 360, -1, -1));

        btbrowse_no_order.setText("...");
        btbrowse_no_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbrowse_no_orderActionPerformed(evt);
            }
        });
        getContentPane().add(btbrowse_no_order, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 60, -1));

        jLabel12.setText("Rp.");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 350, 20, 20));

        btkeluar.setText("KELUAR");
        btkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btkeluarActionPerformed(evt);
            }
        });
        getContentPane().add(btkeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, -1, -1));

        ltotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ltotal.setText("TOTAL");
        getContentPane().add(ltotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(628, 350, 80, -1));

        bttambah.setText("TAMBAH");
        bttambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttambahActionPerformed(evt);
            }
        });
        getContentPane().add(bttambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        tbtransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tbtransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbtransaksiKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbtransaksi);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 700, 123));

        btsimpan.setText("SIMPAN");
        btsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsimpanActionPerformed(evt);
            }
        });
        getContentPane().add(btsimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, -1, -1));

        txt_id_order.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                txt_id_orderComponentAdded(evt);
            }
        });
        txt_id_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_orderActionPerformed(evt);
            }
        });
        getContentPane().add(txt_id_order, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 121, -1));

        txt_estimasi_pengerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_estimasi_pengerjaanActionPerformed(evt);
            }
        });
        txt_estimasi_pengerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_estimasi_pengerjaanKeyTyped(evt);
            }
        });
        getContentPane().add(txt_estimasi_pengerjaan, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 215, -1));

        jLabel9.setText("Estimasi Pengerjaan");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 118, -1));

        txt_tanggal_penerimaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tanggal_penerimaanActionPerformed(evt);
            }
        });
        getContentPane().add(txt_tanggal_penerimaan, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 170, -1));

        jLabel3.setText("Tanggal Penerimaan");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, -1, -1));

        txt_nama_pelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nama_pelangganActionPerformed(evt);
            }
        });
        getContentPane().add(txt_nama_pelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 130, 170, -1));

        jLabel13.setText("Nama Pelanggan");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, -1, -1));

        txt_no_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_no_orderActionPerformed(evt);
            }
        });
        getContentPane().add(txt_no_order, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 215, -1));

        jLabel2.setText("No. Order");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));
        getContentPane().add(txt_tanggal_pengambilan, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 190, 170, -1));

        jLabel4.setText("Tanggal Pengambilan");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, -1, -1));

        jLabel11.setText("Kembalian");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 380, 50, 20));

        l_kembalian.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        l_kembalian.setText("0");
        getContentPane().add(l_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 380, -1, -1));

        jLabel5.setText("No Transaksi");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        txt_no_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_no_transaksiActionPerformed(evt);
            }
        });
        getContentPane().add(txt_no_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 215, -1));

        jLabel18.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 102, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/6.png"))); // NOI18N
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbatalActionPerformed
        // TODO add your handling code here:
        awal();
        HapusTabelSementara();
        bersih();
        txt_no_order.setText("");
    }//GEN-LAST:event_btbatalActionPerformed

    private void btbrowse_no_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbrowse_no_orderActionPerformed
        // TODO add your handling code here:
        boolean closable = true;
        liat_penerimaan_cucian penerimaan_cucian = new liat_penerimaan_cucian(null, closable);
        penerimaan_cucian.penerimaan_cucian = this;
        penerimaan_cucian.setVisible(true);
        penerimaan_cucian.setResizable(true);
        txt_id_order.setText(idOrder);
        txt_no_order.setText(noOrder);
        txt_nama_pelanggan.setText(namaPelanggan);
        txt_tanggal_penerimaan.setText(tanggalPenerimaan);
        txt_estimasi_pengerjaan.setText(estimasiPengerjaan);
        ltotal.setText(total);

        txt_no_transaksi.setText(nomor());
        
        if (tanggalPengambilan != null) {
            JOptionPane.showMessageDialog(null, "Sudah diambil");
            txt_tanggal_pengambilan.setText(tanggalPengambilan);
        } else {
            java.util.Date skrg = new java.util.Date();
            java.text.SimpleDateFormat kal = new java.text.SimpleDateFormat("yyyy-MM-dd");
            txt_tanggal_pengambilan.setText(kal.format(skrg));
        }
        TampilTabelSementara();

        txt_bayar.requestFocus();

    }//GEN-LAST:event_btbrowse_no_orderActionPerformed

    
    
    private void btkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btkeluarActionPerformed
        // TODO add your handling code here:
        HapusTabelSementara();
        dispose();
        new form_utama().setVisible(true);
    }//GEN-LAST:event_btkeluarActionPerformed

    private void txt_nama_pelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nama_pelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nama_pelangganActionPerformed

    private void bttambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttambahActionPerformed
        // TODO add your handling code here:
        aktif();
        txt_no_transaksi.setText(nomor());
        btbrowse_no_order.setEnabled(true);
    }//GEN-LAST:event_bttambahActionPerformed

    private void tbtransaksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbtransaksiKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
//            HapusIsiSementara();
        }
    }//GEN-LAST:event_tbtransaksiKeyPressed

    private void btsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsimpanActionPerformed
        // TODO add your handling code here:

//        int cekKembali = Integer.parseInt(tkembali.getText());
//        if (tbayar.getText().equals("")) {
//            JOptionPane.showMessageDialog(null, "harap masukan uang bayar");
//        } else if (cekKembali < 0) {
//            JOptionPane.showMessageDialog(null, "Uang bayar belum sesuai");
//        } else {
        SimpanTransaksi();
        updatePengambilan();
        TampilTabelSementara();
        JOptionPane.showMessageDialog(null, "Cetak Struk");
            if (JOptionPane.showConfirmDialog(this, "Mau Cetak Nota / Tagihan?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        cetak();
        HapusTabelSementara();
        awal();
        bersih();
        txt_no_order.setText("");
        TampilTabelSementara();
            } else {
                HapusIsiSementara();
                awal();
                bersih();
                txt_no_order.setText("");
            }
//        }
    }//GEN-LAST:event_btsimpanActionPerformed

    private void txt_no_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_no_orderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_no_orderActionPerformed

    private void txt_id_orderComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_txt_id_orderComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_orderComponentAdded

    private void txt_id_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_orderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_orderActionPerformed

    private void txt_estimasi_pengerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_estimasi_pengerjaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_estimasi_pengerjaanActionPerformed

    private void txt_estimasi_pengerjaanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_estimasi_pengerjaanKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_estimasi_pengerjaanKeyTyped

    private void txt_tanggal_penerimaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tanggal_penerimaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tanggal_penerimaanActionPerformed

    private void txt_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyReleased
        // TODO add your handling code here:
        int kembali, total, bayar;
        total = Integer.parseInt(ltotal.getText());
        if (txt_bayar.getText().equals("")) {
            bayar = 0;
            l_kembalian.setText("");
        } else {
            bayar = Integer.parseInt(txt_bayar.getText());
            kembali = bayar - total;
            l_kembalian.setText(Integer.toString(kembali));
            if (kembali >= 0) {
                btsimpan.setEnabled(true);
            } else {
                btsimpan.setEnabled(false);
            }
        }
    }//GEN-LAST:event_txt_bayarKeyReleased

    private void txt_bayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_bayarKeyTyped

    private void txt_no_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_no_transaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_no_transaksiActionPerformed

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
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btbatal;
    private javax.swing.JButton btbrowse_no_order;
    private javax.swing.JButton btkeluar;
    private javax.swing.JButton btsimpan;
    private javax.swing.JButton bttambah;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel l_kembalian;
    private javax.swing.JLabel ltotal;
    private javax.swing.JTable tbtransaksi;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_estimasi_pengerjaan;
    private javax.swing.JTextField txt_id_order;
    private javax.swing.JTextField txt_nama_pelanggan;
    private javax.swing.JTextField txt_no_order;
    private javax.swing.JTextField txt_no_transaksi;
    private javax.swing.JTextField txt_tanggal_penerimaan;
    private javax.swing.JTextField txt_tanggal_pengambilan;
    // End of variables declaration//GEN-END:variables
}
