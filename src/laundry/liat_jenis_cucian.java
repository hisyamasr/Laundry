package laundry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Aas1
 */
public class liat_jenis_cucian extends java.awt.Dialog {
konek kon=new konek();
public form_penerimaan_cucian data_jenis_cucian = null;
private Object[][] dataakun=null;
private String[] label={"Nama Cucian","Satuan","Biaya Cucian","Id Cucian"};
    /**
     * Creates new form liat_jenis_cucian
     */
    public liat_jenis_cucian(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Form Data Jenis Cucian");
        kon.setKoneksi();
        BacaTabelTransaksi();
    }
    
    private void BacaTabelTransaksi(){
    try{
        String sql="Select *From jenis_cucian order by nama_cucian";
        kon.rs=kon.st.executeQuery(sql);
        ResultSetMetaData m=kon.rs.getMetaData();
        int kolom=m.getColumnCount();
        int baris=0;
        while(kon.rs.next()){
            baris=kon.rs.getRow();
        }
        dataakun=new Object[baris][kolom];
        int x=0;
        kon.rs.beforeFirst();
        while(kon.rs.next()){
            dataakun[x][0]=kon.rs.getString("nama_cucian");
            dataakun[x][1]=kon.rs.getString("satuan");
            dataakun[x][2]=kon.rs.getString("biaya_cucian");
            dataakun[x][3]=kon.rs.getString("id_cucian");
            x++;
        }
        j_tabel_jenis_cucian.setModel (new DefaultTableModel (dataakun, label));
    }
    catch (SQLException e) {
         JOptionPane.showMessageDialog(null, e);
    }
}    
    
    private void BacaTabelTransaksi2(){
    try{
        String sql="Select *From jenis_cucian where nama_cucian like'%"+txt_cari_nama_cucian.getText()+"%'";
        kon.rs=kon.st.executeQuery(sql);
        ResultSetMetaData m=kon.rs.getMetaData();
        int kolom=m.getColumnCount();
        int baris=0;
        while(kon.rs.next()){
            baris=kon.rs.getRow();
        }
        dataakun=new Object[baris][kolom];
        int x=0;
        kon.rs.beforeFirst();
        while(kon.rs.next()){
            dataakun[x][0]=kon.rs.getString("nama_cucian");
            dataakun[x][1]=kon.rs.getString("satuan");
            dataakun[x][2]=kon.rs.getString("biaya_cucian");
            dataakun[x][3]=kon.rs.getString("id_cucian");
            x++;
        }
        j_tabel_jenis_cucian.setModel (new DefaultTableModel (dataakun, label));
    }
    catch (SQLException e) {
         JOptionPane.showMessageDialog(null, e);
    }
}   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        j_tabel_jenis_cucian = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txt_cari_nama_cucian = new javax.swing.JTextField();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(144, 190, 171));

        jLabel1.setFont(new java.awt.Font("Nyala", 1, 24)); // NOI18N
        jLabel1.setText("Data Jenis Cucian");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tabel Data Jenis Cucian"));

        j_tabel_jenis_cucian.setModel(new javax.swing.table.DefaultTableModel(
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
        j_tabel_jenis_cucian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                j_tabel_jenis_cucianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(j_tabel_jenis_cucian);

        jLabel2.setText("Cari Nama Cucian:");

        txt_cari_nama_cucian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cari_nama_cucianKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_cari_nama_cucian))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_cari_nama_cucian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 219, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void j_tabel_jenis_cucianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_j_tabel_jenis_cucianMouseClicked
        // TODO add your handling code here:
        int tabelakun = j_tabel_jenis_cucian.getSelectedRow();
        form_penerimaan_cucian.namaCucian = j_tabel_jenis_cucian.getValueAt(tabelakun, 0).toString();
        form_penerimaan_cucian.satuan = j_tabel_jenis_cucian.getValueAt(tabelakun, 1).toString();
        form_penerimaan_cucian.biayaCucian = j_tabel_jenis_cucian.getValueAt(tabelakun, 2).toString();
        form_penerimaan_cucian.idJenisCucian = j_tabel_jenis_cucian.getValueAt(tabelakun, 3).toString();
        this.dispose();
    }//GEN-LAST:event_j_tabel_jenis_cucianMouseClicked

    private void txt_cari_nama_cucianKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cari_nama_cucianKeyTyped
        // TODO add your handling code here:
        BacaTabelTransaksi2();
    }//GEN-LAST:event_txt_cari_nama_cucianKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                liat_jenis_cucian dialog = new liat_jenis_cucian(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable j_tabel_jenis_cucian;
    private javax.swing.JTextField txt_cari_nama_cucian;
    // End of variables declaration//GEN-END:variables
}
