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
public class form_penerimaan_cucian extends javax.swing.JFrame {

    static String idPelanggan;
    static String namaPelanggan;

    static String idJenisCucian;
    static String namaCucian;
    static String satuan;
    static String biayaCucian;

    konek kon = new konek();
    private Object[][] datasementara = null;
    private String[] labelsementara = {"Kode Cucian", "Nama Cucian","Satuan", "Harga", "Jumlah Satuan", "Subtotal"};

    public Date date = new Date();
    public SimpleDateFormat noformat = new SimpleDateFormat("yyMM");
//    public String namaPelanggan;

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    /**
     * Creates new form_penerimaan_cucian
     */
    public form_penerimaan_cucian() {
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Form Penerimaan Cucian");
        kon.setKoneksi();
        setTanggal();
        awal();
        
        
        txt_id_cucian.setVisible(false);
        txt_id_pelanggan.setVisible(false);
    }

    private void bersih() {
        txt_no_order.setText("");
        txt_nama_jenis_cucian.setText("");
        txt_nama_pelanggan.setText("");
        txt_biaya_cucian.setText("");
        tjumbel.setText("");
        tsubtotal.setText("");
//        tbayar.setText("");
//        tkembali.setText("");
        ltotal.setText("0");
        txt_estimasi_pengerjaan.setText("");
    }

    private void nonaktif() {
        txt_no_order.setEnabled(false);
        txt_tanggal.setEnabled(false);
        txt_nama_jenis_cucian.setEnabled(false);
        txt_satuan.setEnabled(false);
        txt_nama_pelanggan.setEnabled(false);
        txt_biaya_cucian.setEnabled(false);
        tjumbel.setEnabled(false);
        tsubtotal.setEnabled(false);
        txt_estimasi_pengerjaan.setEnabled(false);
        
//        tbayar.setEnabled(false);
//        tkembali.setEnabled(false);
        btsimpan.setEnabled(false);
        btbatal.setEnabled(false);
        
                btbrowse_jenis_cucian.setEnabled(false);
        btbrowse_pelanggan.setEnabled(false);
    }

    private void aktif() {


        
//        btbrowse_jenis_cucian.setEnabled(true);
        
        btbrowse_pelanggan.setEnabled(true);
        
//        txt_nama_pelanggan.setEnabled(true);
//        txt_nama_jenis_cucian.setEnabled(true);
//        tjumbel.setEnabled(true);
//        tbayar.setEnabled(true);
        btsimpan.setEnabled(false);
        btbatal.setEnabled(true);
        
//        txt_estimasi_pengerjaan.setEnabled(true);
    }

    private void awal() {
        nonaktif();
    }

    void setTanggal() {
        java.util.Date skrg = new java.util.Date();
        java.text.SimpleDateFormat kal = new java.text.SimpleDateFormat("yyyy-MM-dd");
        txt_tanggal.setText(kal.format(skrg));
    }

    public String nomor() {
        String urutan = null;
        try {
            kon.rs = kon.st.executeQuery("select right (no_order,4)+1 "
                    + "from penerimaan_cucian as Nomor order by no_order desc");
            if (kon.rs.next()) {
                urutan = kon.rs.getString(1);
                while (urutan.length() < 4) {
                    urutan = "0" + urutan;
                }
                urutan = "O-" + noformat.format(date) + urutan;
            } else {
                urutan = "O-" + noformat.format(date) + "0001";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return urutan;
    }

    private void TampilTabelSementara() {
        try {
            String sql = "Select *From sementara";
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
                datasementara[x][0] = kon.rs.getString("id_cucian");
                datasementara[x][1] = kon.rs.getString("nama_cucian");
                datasementara[x][2] = kon.rs.getString("satuan");
                datasementara[x][3] = kon.rs.getString("harga");
                datasementara[x][4] = kon.rs.getString("jumlah");
                datasementara[x][5] = kon.rs.getString("subtotal");

                x++;
            }
            tbtransaksi.setModel(new DefaultTableModel(datasementara, labelsementara));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void tampildatabarang() {
        try {
            String sql = "select *from data_barang where kd_barang='" + txt_nama_jenis_cucian.getText() + "'";
            kon.rs = kon.st.executeQuery(sql);
            if (kon.rs.next()) {
                txt_satuan.setText(kon.rs.getString("nm_barang"));
                txt_biaya_cucian.setText(kon.rs.getString("harga_barang"));
                tjumbel.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Kode Barang" + txt_nama_jenis_cucian.getText() + "tidak ditemukan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void SimpanSementara() {
        try {
            String sql = "insert into sementara values('" + txt_id_cucian.getText() + "','" + txt_nama_jenis_cucian.getText() + "',"
                    + "'" + txt_satuan.getText() + "','" + txt_biaya_cucian.getText() + "',"
                    + "'" + tjumbel.getText() + "','" + tsubtotal.getText() + "')";
            System.out.println(sql);
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

    private void SimpanTransaksi() {
        try {
            String sql = "insert into penerimaan_cucian values(null,'" + txt_no_order.getText() + "',"
                    + "'" + txt_id_pelanggan.getText() + "',"
                    + "'" + txt_tanggal.getText() + "',null,"
                    + "'" + ltotal.getText() + "','belum',"
                    + "'" + txt_estimasi_pengerjaan.getText() + "')";
//            System.out.println("simpan Transaksi:" + sql);
            kon.st.executeUpdate(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void SimpanDetailTransaksi() {
        try {
            String ambilNoOrderterakhir = "SELECT id_order FROM penerimaan_cucian ORDER BY id_order DESC LIMIT 1;";
            int noOrder = 0;
            kon.rs = kon.st.executeQuery(ambilNoOrderterakhir);
            if (kon.rs.next()) {
                noOrder = kon.rs.getInt("id_order");
            } else {
                JOptionPane.showMessageDialog(null, "ambil No transaksi terakhir :"+kon.rs);
            }
            

            String detail = "insert detail_penerimaan_cucian select '" + noOrder + "',"
                    + "id_cucian,jumlah,subtotal from sementara";
            kon.st.executeUpdate(detail);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
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

    private void cetak() {
        try {
            String file = "src/laporan/nota.jasper";
            HashMap param = new HashMap();
            param.put("no_order", txt_no_order.getText());
            
//            JOptionPane.showMessageDialog(null, txt_no_order.getText());

//            JasperFillManager.fillReport(jasperReport, param, connection)

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

        txt_biaya_cucian = new javax.swing.JTextField();
        tsubtotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tjumbel = new javax.swing.JTextField();
        btbatal = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btbrowse_jenis_cucian = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btkeluar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txt_nama_jenis_cucian = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_no_order = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_tanggal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btbrowse_pelanggan = new javax.swing.JButton();
        txt_nama_pelanggan = new javax.swing.JTextField();
        ltotal = new javax.swing.JLabel();
        bttambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbtransaksi = new javax.swing.JTable();
        txt_satuan = new javax.swing.JTextField();
        txt_id_pelanggan = new javax.swing.JTextField();
        btsimpan = new javax.swing.JButton();
        txt_id_cucian = new javax.swing.JTextField();
        txt_estimasi_pengerjaan = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(txt_biaya_cucian, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 127, -1));
        getContentPane().add(tsubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 190, 160, -1));

        jLabel4.setText("Jenis Cucian");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        jLabel6.setText("Biaya Cucian");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, -1, -1));

        tjumbel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tjumbelActionPerformed(evt);
            }
        });
        tjumbel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tjumbelKeyTyped(evt);
            }
        });
        getContentPane().add(tjumbel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 95, -1));

        btbatal.setBackground(new java.awt.Color(153, 153, 255));
        btbatal.setText("BATAL");
        btbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbatalActionPerformed(evt);
            }
        });
        getContentPane().add(btbatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 380, 80, -1));

        jLabel8.setText("Sub Total");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 170, -1, -1));

        btbrowse_jenis_cucian.setText("...");
        btbrowse_jenis_cucian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbrowse_jenis_cucianActionPerformed(evt);
            }
        });
        getContentPane().add(btbrowse_jenis_cucian, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 44, -1));

        jLabel12.setText("Rp.");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        jLabel5.setText("Satuan");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, -1, -1));

        btkeluar.setBackground(new java.awt.Color(153, 153, 255));
        btkeluar.setText("KELUAR");
        btkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btkeluarActionPerformed(evt);
            }
        });
        getContentPane().add(btkeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 380, 80, -1));

        jLabel7.setText("Jumlah Satuan");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, -1, -1));

        txt_nama_jenis_cucian.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                txt_nama_jenis_cucianComponentAdded(evt);
            }
        });
        txt_nama_jenis_cucian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nama_jenis_cucianActionPerformed(evt);
            }
        });
        getContentPane().add(txt_nama_jenis_cucian, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 160, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("No. Order");

        txt_no_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_no_orderActionPerformed(evt);
            }
        });

        jLabel3.setText("Tanggal");

        jLabel13.setText("Nama Pelanggan");

        btbrowse_pelanggan.setText("...");
        btbrowse_pelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbrowse_pelangganActionPerformed(evt);
            }
        });

        txt_nama_pelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nama_pelangganActionPerformed(evt);
            }
        });
        txt_nama_pelanggan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txt_nama_pelangganPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_no_order, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nama_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btbrowse_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_no_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(btbrowse_pelanggan)
                    .addComponent(txt_nama_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 710, -1));

        ltotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ltotal.setText("TOTAL");
        getContentPane().add(ltotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, -1, -1));

        bttambah.setBackground(new java.awt.Color(153, 153, 255));
        bttambah.setText("TAMBAH");
        bttambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttambahActionPerformed(evt);
            }
        });
        getContentPane().add(bttambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 380, 90, -1));

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
        getContentPane().add(txt_satuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 60, -1));

        txt_id_pelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_pelangganActionPerformed(evt);
            }
        });
        getContentPane().add(txt_id_pelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, 137, -1));

        btsimpan.setBackground(new java.awt.Color(153, 153, 255));
        btsimpan.setText("SIMPAN");
        btsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsimpanActionPerformed(evt);
            }
        });
        getContentPane().add(btsimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 380, 80, -1));

        txt_id_cucian.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                txt_id_cucianComponentAdded(evt);
            }
        });
        txt_id_cucian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_cucianActionPerformed(evt);
            }
        });
        getContentPane().add(txt_id_cucian, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 121, -1));

        txt_estimasi_pengerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_estimasi_pengerjaanActionPerformed(evt);
            }
        });
        txt_estimasi_pengerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_estimasi_pengerjaanKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_estimasi_pengerjaanKeyTyped(evt);
            }
        });
        getContentPane().add(txt_estimasi_pengerjaan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 350, 215, -1));

        jLabel9.setText("Estimasi Pengerjaan");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 350, 118, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/11.png"))); // NOI18N
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tjumbelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tjumbelActionPerformed
        // TODO add your handling code here:
        int harga, jumbel, total;
        harga = Integer.parseInt(txt_biaya_cucian.getText());
        jumbel = Integer.parseInt(tjumbel.getText());
        total = harga * jumbel;
        tsubtotal.setText(Integer.toString(total));
        SimpanSementara();
        TampilTabelSementara();
        int ttl = 0;
        for (int a = 0; a < tbtransaksi.getRowCount(); a++) {
            int sub = Integer.parseInt((String) tbtransaksi.getValueAt(a, 5));
            ttl += sub;
        }
        ltotal.setText(Integer.toString(ttl));
        if (JOptionPane.showConfirmDialog(this, "Mau Tambah Cucian?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            aktif();
            txt_nama_jenis_cucian.requestFocus();
            txt_nama_jenis_cucian.setText("");
            txt_satuan.setText("");
            txt_biaya_cucian.setText("");
            tjumbel.setText("");
            tsubtotal.setText("");
            tjumbel.setEnabled(false);
//            tbayar.setEnabled(false);
        } else {
//            tbayar.setEnabled(true);
//            tbayar.requestFocus();
//            btsimpan.setEnabled(true);
            txt_estimasi_pengerjaan.setEnabled(true);
        }
    }//GEN-LAST:event_tjumbelActionPerformed

    private void btbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbatalActionPerformed
        // TODO add your handling code here:
        awal();
        HapusTabelSementara();
        bersih();
        txt_no_order.setText("");
    }//GEN-LAST:event_btbatalActionPerformed

    private void btbrowse_jenis_cucianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbrowse_jenis_cucianActionPerformed
        // TODO add your handling code here:
        boolean closable = true;
        liat_jenis_cucian jenis_cucian = new liat_jenis_cucian(null, closable);
        jenis_cucian.data_jenis_cucian = this;
        jenis_cucian.setVisible(true);
        jenis_cucian.setResizable(true);
        txt_id_cucian.setText(idJenisCucian);
        txt_nama_jenis_cucian.setText(namaCucian);
        txt_satuan.setText(satuan);
        txt_biaya_cucian.setText(biayaCucian);
        tjumbel.setEnabled(true);
    }//GEN-LAST:event_btbrowse_jenis_cucianActionPerformed

    private void btkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btkeluarActionPerformed
        // TODO add your handling code here:
        HapusTabelSementara();
        dispose();
        new form_utama().setVisible(true);
    }//GEN-LAST:event_btkeluarActionPerformed

    private void txt_nama_jenis_cucianComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_txt_nama_jenis_cucianComponentAdded
        // TODO add your handling code here:
        tjumbel.requestFocus();
    }//GEN-LAST:event_txt_nama_jenis_cucianComponentAdded

    private void txt_nama_jenis_cucianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nama_jenis_cucianActionPerformed
        // TODO add your handling code here:
        tampildatabarang();
    }//GEN-LAST:event_txt_nama_jenis_cucianActionPerformed

    private void txt_nama_pelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nama_pelangganActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_nama_pelangganActionPerformed

    private void bttambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttambahActionPerformed
        // TODO add your handling code here:
        aktif();
        txt_no_order.setText(nomor());
    }//GEN-LAST:event_bttambahActionPerformed

    private void tbtransaksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbtransaksiKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            HapusIsiSementara();
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
        SimpanDetailTransaksi();
        TampilTabelSementara();
//        JOptionPane.showMessageDialog(null, "Cetak Nota / Tagihan");
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

    private void tjumbelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tjumbelKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_tjumbelKeyTyped

    private void btbrowse_pelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbrowse_pelangganActionPerformed
        boolean closable = true;
        liat_pelanggan data_pelanggan = new liat_pelanggan(null, closable);

        
        data_pelanggan.data_pelanggan = this;
        data_pelanggan.setVisible(true);
        data_pelanggan.setResizable(true);
        txt_id_pelanggan.setText(idPelanggan);
        txt_nama_pelanggan.setText(namaPelanggan);
btbrowse_jenis_cucian.setEnabled(true);


//        txt_satuan.setText(NamaBarang);
//        txt_biaya_cucian.setText(HargaBarang);

// TODO add your handling code here:

    }//GEN-LAST:event_btbrowse_pelangganActionPerformed

    private void txt_no_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_no_orderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_no_orderActionPerformed

    private void txt_id_pelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_pelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_pelangganActionPerformed

    private void txt_id_cucianComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_txt_id_cucianComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_cucianComponentAdded

    private void txt_id_cucianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_cucianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_cucianActionPerformed

    private void txt_estimasi_pengerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_estimasi_pengerjaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_estimasi_pengerjaanActionPerformed

    private void txt_estimasi_pengerjaanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_estimasi_pengerjaanKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_estimasi_pengerjaanKeyTyped

    private void txt_nama_pelangganPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txt_nama_pelangganPropertyChange
        // TODO add your handling code here:
        if (txt_nama_pelanggan.getText().equals("")) {
            btbrowse_jenis_cucian.setEnabled(false);
        }else{
            btbrowse_jenis_cucian.setEnabled(true);
        }
    }//GEN-LAST:event_txt_nama_pelangganPropertyChange

    private void txt_estimasi_pengerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_estimasi_pengerjaanKeyPressed
        // TODO add your handling code here:
        if (txt_estimasi_pengerjaan.getText().equals("")) {
            btsimpan.setEnabled(true);
        }else{
            btsimpan.setEnabled(false);
        }
    }//GEN-LAST:event_txt_estimasi_pengerjaanKeyPressed

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
            java.util.logging.Logger.getLogger(form_penerimaan_cucian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_penerimaan_cucian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_penerimaan_cucian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_penerimaan_cucian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_penerimaan_cucian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btbatal;
    private javax.swing.JButton btbrowse_jenis_cucian;
    private javax.swing.JButton btbrowse_pelanggan;
    private javax.swing.JButton btkeluar;
    private javax.swing.JButton btsimpan;
    private javax.swing.JButton bttambah;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel ltotal;
    private javax.swing.JTable tbtransaksi;
    private javax.swing.JTextField tjumbel;
    private javax.swing.JTextField tsubtotal;
    private javax.swing.JTextField txt_biaya_cucian;
    private javax.swing.JTextField txt_estimasi_pengerjaan;
    private javax.swing.JTextField txt_id_cucian;
    private javax.swing.JTextField txt_id_pelanggan;
    private javax.swing.JTextField txt_nama_jenis_cucian;
    private javax.swing.JTextField txt_nama_pelanggan;
    private javax.swing.JTextField txt_no_order;
    private javax.swing.JTextField txt_satuan;
    private javax.swing.JTextField txt_tanggal;
    // End of variables declaration//GEN-END:variables
}
