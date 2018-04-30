/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package List;

import LSubProces.Delete;
import Master.*;
import FunctionGUI.JOptionPaneF;
import static GlobalVar.Var.*;
import KomponenGUI.FDateF;
import LSubProces.DRunSelctOne;
import LSubProces.Insert;
import Proses.*;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Frame.NORMAL;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author richky
 */
public class List extends javax.swing.JFrame {

    /**
     * Creates new form ListKaryawan
     */
    String Type;

    public List(String type) {
        Type = type;
        initComponents();
        setVisible(true);
        JBRegister.setVisible(false);
        jCheckBoxF1.setVisible(false);
        switch (Type) {
            case "Master Barang":
                setTitle("List Master Barang");
                break;
            case "Master Dokter":
                setTitle("List Master Dokter");
                break;
            case "Master Pasien":
                setTitle("List Master Pasien");
                JBRegister.setVisible(true);
                break;
            case "Master Beautician":
                setTitle("List Master Beautician");
                break;
            case "Master Tindakan":
                setTitle("List Master Tindakan");
                break;
            case "Antrian":
                setTitle("List Antrian");
                JBRegister.setVisible(true);
                JBRegister.setText("Perawatan");
                JBTambah.setVisible(false);
                JBUbah.setVisible(false);
                JBHapus.setText("Batal");
                break;
            case "Master Pemasok":
                setTitle("List Master Pemasok");
                break;
            case "Penjualan":
                setTitle("List Penjualan");
                jCheckBoxF1.setVisible(true);
                break;
            case "Barang Masuk":
                setTitle("List Barang Masuk");
                jCheckBoxF1.setVisible(true);
                break;
            case "Penyesuaian Stok":
                setTitle("Penyesuaian Stok");
                break;
            case "Perawatan":
                setTitle("List Perawatan");
                JBTambah.setVisible(false);
                jCheckBoxF1.setVisible(true);
                break;
            case "Antrian Billing":
                setTitle("List Billing");
                JBRegister.setVisible(true);
                JBRegister.setText("Billing");
                JBTambah.setVisible(false);
                JBUbah.setVisible(false);
                JBHapus.setVisible(false);
                break;
            case "Billing":
                setTitle("List Billing");
                jCheckBoxF1.setVisible(true);
                break;
            default:
                throw new AssertionError();
        }
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        refreshAll();
        if (!GlobalVar.VarL.level.equals("Administrator")) {
            JBHapus.setVisible(false);
        }
    }

    String getNoAntrian() {
        String NoAntrian;
        DRunSelctOne dRunSelctOne = new DRunSelctOne();
        dRunSelctOne.seterorm("Gagal getNoAntrian()");
        dRunSelctOne.setQuery("SELECT COUNT(`NoAntrian`) FROM `tbantrian` WHERE `Tanggal` = '" + FDateF.datetostr(new Date(), "yyyy-MM-dd") + "' ORDER BY `NoAntrian` DESC ");
        ArrayList<String> list = dRunSelctOne.excute();
        if (list.get(0).equals("0")) {
            NoAntrian = "1";
        } else {
            NoAntrian = String.valueOf(Integer.valueOf(list.get(0)) + 1);
        }
        return NoAntrian;
    }

    void tambahAntrian() {
        if (jcomCari1.getSelectedRow() < 0) {
            JOptionPaneF.showMessageDialog(this, "Gagal. Silahkan Pilih Data Terlebih Dahulu");
        } else {
            Insert insert = new Insert();
            Boolean berhasil = insert.simpan("INSERT INTO `tbantrian`(`Tanggal`, `NoAntrian`, `IdPasien`) VALUES ('" + FDateF.datetostr(new Date(), "yyyy-MM-dd") + "'," + getNoAntrian() + ",'" + jcomCari1.GetIDTable() + "')", "Antrian", this);
            if (berhasil) {
                if (listMasterPasien != null) {
                    listMasterPasien.load();
                }
                if (listAntrian != null) {
                    listAntrian.load();
                }
            }
        }
    }

    void tambahPerawatan() {
        if (jcomCari1.getSelectedRow() < 0) {
            JOptionPaneF.showMessageDialog(this, "Gagal. Silahkan Pilih Data Terlebih Dahulu");
        } else {
            if (tambahPerawatan == null) {
                tambahPerawatan = new Perawatan("Antrian", jcomCari1.GetIDTable1());
            } else {
                tambahPerawatan.setState(NORMAL);
                tambahPerawatan.toFront();
            }
        }
    }

    void tambahBilling() {
        if (jcomCari1.getSelectedRow() < 0) {
            JOptionPaneF.showMessageDialog(this, "Gagal. Silahkan Pilih Data Terlebih Dahulu");
        } else {
            if (tambahBilling == null) {
                tambahBilling = new Billing("Antrian Billing", jcomCari1.GetIDTable1());
            } else {
                tambahBilling.setState(NORMAL);
                tambahBilling.toFront();
            }
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

        JBUbah = new KomponenGUI.JbuttonF();
        JBHapus = new KomponenGUI.JbuttonF();
        JBRefresh = new KomponenGUI.JbuttonF();
        jbuttonF4 = new KomponenGUI.JbuttonF();
        JBTambah = new KomponenGUI.JbuttonF();
        JBRegister = new KomponenGUI.JbuttonF();
        jcomCari1 = new KomponenGUI.JcomCari();
        jlableF1 = new KomponenGUI.JlableF();
        jtextF1 = new KomponenGUI.JtextF();
        jCheckBoxF1 = new KomponenGUI.JCheckBoxF();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        JBUbah.setText("Ubah");
        JBUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBUbahActionPerformed(evt);
            }
        });

        JBHapus.setText("Hapus");
        JBHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBHapusActionPerformed(evt);
            }
        });

        JBRefresh.setText("Refresh");
        JBRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBRefreshActionPerformed(evt);
            }
        });

        jbuttonF4.setText("Kembali");
        jbuttonF4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonF4ActionPerformed(evt);
            }
        });

        JBTambah.setText("Tambah");
        JBTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBTambahActionPerformed(evt);
            }
        });

        JBRegister.setText("Register");
        JBRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBRegisterActionPerformed(evt);
            }
        });

        jcomCari1.jtablef.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTableTindakanMouseClicked(evt);
            }
        });

        jlableF1.setText("Pencarian :");

        jtextF1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtextF1KeyReleased(evt);
            }
        });

        jCheckBoxF1.setSelected(false);
        jCheckBoxF1.setText("Tampilkan Detail");
        jCheckBoxF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxF1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcomCari1, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlableF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtextF1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbuttonF4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBoxF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JBRegister, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JBRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JBTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JBUbah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JBHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcomCari1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlableF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtextF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JBUbah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbuttonF4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBRegister, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JBUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBUbahActionPerformed
        ubah();
    }//GEN-LAST:event_JBUbahActionPerformed

    private void jbuttonF4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonF4ActionPerformed
        dispose();
    }//GEN-LAST:event_jbuttonF4ActionPerformed

    private void JBHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBHapusActionPerformed
        hapus();
    }//GEN-LAST:event_JBHapusActionPerformed

    private void JBRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBRefreshActionPerformed
        refreshAll();
    }//GEN-LAST:event_JBRefreshActionPerformed

    private void JBTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBTambahActionPerformed
        tambah();
    }//GEN-LAST:event_JBTambahActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        switch (Type) {
            case "Master Barang":
                listMasterBarang = null;
                break;
            case "Master Dokter":
                listMasterDokter = null;
                break;
            case "Master Pasien":
                listMasterPasien = null;
                break;
            case "Master Beautician":
                listMasterBeautician = null;
                break;
            case "Master Tindakan":
                listMasterTindakan = null;
                break;
            case "Antrian":
                listAntrian = null;
                break;
            case "Master Pemasok":
                listMasterPemasok = null;
                break;
            case "Penjualan":
                listPenjualan = null;
                break;
            case "Barang Masuk":
                listBarangMasuk = null;
                break;
            case "Penyesuaian Stok":
                listPenyesuaianStok = null;
                break;
            case "Perawatan":
                listPerawatan = null;
                break;
            case "Antrian Billing":
                listAntrianBilling = null;
                break;
            case "Billing":
                listBilling = null;
                break;
            default:
                throw new AssertionError();
        }
    }//GEN-LAST:event_formWindowClosed

    private void JBRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBRegisterActionPerformed
        register();
    }//GEN-LAST:event_JBRegisterActionPerformed

    private void jtextF1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtextF1KeyReleased
        String text = jtextF1.getText();
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(jcomCari1.jtablef.getModel());
        jcomCari1.jtablef.setRowSorter(rowSorter);
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }//GEN-LAST:event_jtextF1KeyReleased

    private void jCheckBoxF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxF1ActionPerformed
        load();
    }//GEN-LAST:event_jCheckBoxF1ActionPerformed

    private void JTableTindakanMouseClicked(java.awt.event.MouseEvent evt) {
        if (Type.equals("Master Pasien")) {
            if (jcomCari1.jtablef.getValueAt(jcomCari1.jtablef.getSelectedRow(), jcomCari1.jtablef.getColumnCount() - 1).equals("Antri")) {
                JBRegister.setEnabled(false);
                JBRegister.setText("Sedang Antri");
            } else {
                JBRegister.setEnabled(true);
                JBRegister.setText("Register");
            }
        }
    }

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
            java.util.logging.Logger.getLogger(List.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(List.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(List.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(List.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new List("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private KomponenGUI.JbuttonF JBHapus;
    private KomponenGUI.JbuttonF JBRefresh;
    private KomponenGUI.JbuttonF JBRegister;
    private KomponenGUI.JbuttonF JBTambah;
    private KomponenGUI.JbuttonF JBUbah;
    private KomponenGUI.JCheckBoxF jCheckBoxF1;
    private KomponenGUI.JbuttonF jbuttonF4;
    public static KomponenGUI.JcomCari jcomCari1;
    private KomponenGUI.JlableF jlableF1;
    private KomponenGUI.JtextF jtextF1;
    // End of variables declaration//GEN-END:variables

    void refreshAll() {
//        jcomCari1.refresh();
        load();
    }

    void register() {
        switch (Type) {
            case "Master Pasien":
                tambahAntrian();
                break;
            case "Antrian":
                tambahPerawatan();
                break;
            case "Antrian Billing":
                tambahBilling();
                break;
            default:
                throw new AssertionError();
        }
    }

    void tambah() {
        switch (Type) {
            case "Master Barang":
                if (tambahMasterBarang == null) {
                    tambahMasterBarang = new MasterBarang();
                } else {
                    tambahMasterBarang.setState(NORMAL);
                    tambahMasterBarang.toFront();
                }
                break;
            case "Master Dokter":
                if (tambahMasterDokter == null) {
                    tambahMasterDokter = new MasterDokter();
                } else {
                    tambahMasterDokter.setState(NORMAL);
                    tambahMasterDokter.toFront();
                }
                break;
            case "Master Pasien":
                if (tambahMasterPasien == null) {
                    tambahMasterPasien = new MasterPasien();
                } else {
                    tambahMasterPasien.setState(NORMAL);
                    tambahMasterPasien.toFront();
                }
                break;
            case "Master Beautician":
                if (tambahMasterBeautician == null) {
                    tambahMasterBeautician = new MasterBeautician();
                } else {
                    tambahMasterBeautician.setState(NORMAL);
                    tambahMasterBeautician.toFront();
                }
                break;
            case "Master Tindakan":
                if (tambahMasterTindakan == null) {
                    tambahMasterTindakan = new MasterTindakan();
                } else {
                    tambahMasterTindakan.setState(NORMAL);
                    tambahMasterTindakan.toFront();
                }
                break;
            case "Antrian":
                break;
            case "Master Pemasok":
                if (tambahMasterPemasok == null) {
                    tambahMasterPemasok = new MasterPemasok();
                } else {
                    tambahMasterPemasok.setState(NORMAL);
                    tambahMasterPemasok.toFront();
                }
                break;
            case "Penjualan":
                if (tambahPenjualan == null) {
                    tambahPenjualan = new Penjualan();
                } else {
                    tambahPenjualan.setState(NORMAL);
                    tambahPenjualan.toFront();
                }
                break;
            case "Barang Masuk":
                if (tambahBarangMasuk == null) {
                    tambahBarangMasuk = new BarangMasuk();
                } else {
                    tambahBarangMasuk.setState(NORMAL);
                    tambahBarangMasuk.toFront();
                }
                break;
            case "Penyesuaian Stok":
                if (tambahPenyesuaianStok == null) {
                    tambahPenyesuaianStok = new PenyesuaianStok();
                } else {
                    tambahPenyesuaianStok.setState(NORMAL);
                    tambahPenyesuaianStok.toFront();
                }
                break;
            case "Perawatan":
                break;
            case "Antrian Billing":
                break;
            case "Billing":
                break;
            default:
                throw new AssertionError();
        }
    }

    void hapus() {
        if (jcomCari1.getSelectedRow() < 0) {
            JOptionPaneF.showMessageDialog(this, "Gagal. Silahkan Pilih Data Terlebih Dahulu");
        } else {
            Delete delete = new Delete();
            Boolean berhasil = false;
            switch (Type) {
                case "Master Barang":
                    berhasil = delete.Hapus("ID " + jcomCari1.GetIDTable(), "DELETE FROM `tbmbarang` WHERE `IdBarang` = " + jcomCari1.GetIDTable(), "Barang", this);
                    break;
                case "Master Dokter":
                    berhasil = delete.Hapus("ID " + jcomCari1.GetIDTable(), "DELETE FROM `tbmdokter` WHERE `IdDokter` = " + jcomCari1.GetIDTable(), "Dokter", this);
                    break;
                case "Master Pasien":
                    berhasil = delete.Hapus("ID " + jcomCari1.GetIDTable(), "DELETE FROM `tbmpasien` WHERE `IdPasien` = " + jcomCari1.GetIDTable(), "Pasien", this);
                    break;
                case "Master Beautician":
                    berhasil = delete.Hapus("ID " + jcomCari1.GetIDTable(), "DELETE FROM `tbmbeautician` WHERE `IdBeautician` = " + jcomCari1.GetIDTable(), "Beautician", this);
                    break;
                case "Master Tindakan":
                    berhasil = delete.Hapus("ID " + jcomCari1.GetIDTable(), "DELETE FROM `tbmtindakan` WHERE `IdTindakan` = " + jcomCari1.GetIDTable(), "Tindakan", this);
                    break;
                case "Antrian":
                    berhasil = delete.Hapus("Nomor Antrian " + jcomCari1.GetIDTable(), "DELETE FROM `tbantrian` WHERE `NoAntrian` = " + jcomCari1.GetIDTable() + " AND `Tanggal` = CURDATE()", "Antrian", this);
                    break;
                case "Master Pemasok":
                    berhasil = delete.Hapus("ID " + jcomCari1.GetIDTable(), "DELETE FROM `tbmpemasok` WHERE `IdPemasok` = " + jcomCari1.GetIDTable(), "Pemasok", this);
                    break;
                case "Penjualan":
                    berhasil = delete.Hapus("ID " + jcomCari1.GetIDTable(), "DELETE FROM `tbpenjualan` WHERE `IdPenjualan` = " + jcomCari1.GetIDTable(), "Penjualan", this);
                    break;
                case "Barang Masuk":
                    berhasil = delete.Hapus("ID " + jcomCari1.GetIDTable(), "DELETE FROM `tbbarangmasuk` WHERE `IdBarangMasuk` = " + jcomCari1.GetIDTable(), "Barang Masuk", this);
                    break;
                case "Penysuaian Stok":
                    break;
                case "Perawatan":
                    berhasil = delete.Hapus("ID " + jcomCari1.GetIDTable(), "DELETE FROM `tbperawatan` WHERE `IdPerawatan` = " + jcomCari1.GetIDTable(), "Perawatan", this);
                    break;
                case "Antrian Billing":
                    break;
                case "Billing":
                    berhasil = delete.Hapus("ID " + jcomCari1.GetIDTable(), "DELETE FROM `tbbilling` WHERE `IdBilling` = " + jcomCari1.GetIDTable(), "Perawatan", this);
                    break;
                default:
                    throw new AssertionError();
            }
            if (berhasil) {
                refreshAll();
            }
        }
    }

    void ubah() {
        if (jcomCari1.getSelectedRow() < 0) {
            JOptionPaneF.showMessageDialog(this, "Gagal. Silahkan Pilih Data Terlebih Dahulu");
        } else {
            switch (Type) {
                case "Master Barang":
                    if (ubahMasterBarang == null) {
                        ubahMasterBarang = new MasterBarang(jcomCari1.GetIDTable());
                    } else {
                        ubahMasterBarang.setState(NORMAL);
                        ubahMasterBarang.toFront();
                    }
                    break;
                case "Master Dokter":
                    if (ubahMasterDokter == null) {
                        ubahMasterDokter = new MasterDokter(jcomCari1.GetIDTable());
                    } else {
                        ubahMasterDokter.setState(NORMAL);
                        ubahMasterDokter.toFront();
                    }
                    break;
                case "Master Pasien":
                    if (ubahMasterPasien == null) {
                        ubahMasterPasien = new MasterPasien(jcomCari1.GetIDTable());
                    } else {
                        ubahMasterPasien.setState(NORMAL);
                        ubahMasterPasien.toFront();
                    }
                    break;
                case "Master Beautician":
                    if (ubahMasterBeautician == null) {
                        ubahMasterBeautician = new MasterBeautician(jcomCari1.GetIDTable());
                    } else {
                        ubahMasterBeautician.setState(NORMAL);
                        ubahMasterBeautician.toFront();
                    }
                    break;
                case "Master Tindakan":
                    if (ubahMasterTindakan == null) {
                        ubahMasterTindakan = new MasterTindakan(jcomCari1.GetIDTable());
                    } else {
                        ubahMasterTindakan.setState(NORMAL);
                        ubahMasterTindakan.toFront();
                    }
                    break;
                case "Antrian":
                    break;
                case "Master Pemasok":
                    if (ubahMasterPemasok == null) {
                        ubahMasterPemasok = new MasterPemasok(jcomCari1.GetIDTable());
                    } else {
                        ubahMasterPemasok.setState(NORMAL);
                        ubahMasterPemasok.toFront();
                    }
                    break;
                case "Penjualan":
                    if (ubahPenjualan == null) {
                        ubahPenjualan = new Penjualan(jcomCari1.GetIDTable());
                    } else {
                        ubahPenjualan.setState(NORMAL);
                        ubahPenjualan.toFront();
                    }
                    break;
                case "Barang Masuk":
                    if (ubahBarangMasuk == null) {
                        ubahBarangMasuk = new BarangMasuk(jcomCari1.GetIDTable());
                    } else {
                        ubahBarangMasuk.setState(NORMAL);
                        ubahBarangMasuk.toFront();
                    }
                    break;
                case "Penyesuaian Stok":
                    break;
                case "Perawatan":
                    if (ubahPerawatan == null) {
                        ubahPerawatan = new Perawatan("Ubah", jcomCari1.GetIDTable());
                    } else {
                        ubahPerawatan.setState(NORMAL);
                        ubahPerawatan.toFront();
                    }
                    break;
                case "Antrian Billing":
                    break;
                case "Billing":
                    if (ubahBilling == null) {
                        ubahBilling = new Billing("Ubah", jcomCari1.GetIDTable());
                    } else {
                        ubahBilling.setState(NORMAL);
                        ubahBilling.toFront();
                    }
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    public void load() {
        switch (Type) {
            case "Master Barang":
                jcomCari1.setQuery("SELECT `IdBarang` as 'ID', `NamaBarang` as 'Nama Barang', `JenisBarang`, `Harga`, a.`Keterangan`, IF(`Status`=1,'Aktif','Tidak Aktif') as 'Status' FROM `tbmbarang`a JOIN `tbsmjenisbarang`b ON a.`IdJenisBarang`=b.`IdJenisBarang` WHERE 1");
                jcomCari1.setRender(new int[]{3}, new String[]{"Number"});
                jcomCari1.setOrder(" ORDER BY `JenisBarang`, `NamaBarang` ");
                break;
            case "Master Dokter":
                jcomCari1.setQuery("SELECT `IdDokter` as 'ID', `NamaDokter` as 'Nama Dokter', `NoTelepon` as 'No Telpon', `Alamat`, `Keterangan`, IF(`Status`=1,'Aktif','Tidak Aktif') as 'Status' FROM `tbmdokter` WHERE 1 ");
                jcomCari1.setOrder(" ORDER BY `NamaDokter` ");
                break;
            case "Master Pasien":
                jcomCari1.setQuery("SELECT a.`IdPasien` as 'ID', `KodePasien` as 'Kode', `NamaPasien` as 'Nama', `JenisKelamin` as 'Jenis Kelamin', DATE_FORMAT(`TanggalDaftar`,'%d-%m-%Y') as 'Tanggal Daftar', DATE_FORMAT(`TanggaLahir`,'%d-%m-%Y') as 'Tanggal Lahir', `NoTelpon` as 'No. Telpon', `Pekerjaan`, `Email`, `Alamat`, `Catatan`, `NoKartu`, IF((`NoAntrian` IS NOT NULL AND `Tanggal` = CURDATE()) AND b.`Status` = 0,'Antri','Tidak') as 'Antrian' FROM `tbmpasien`a LEFT JOIN `tbantrian`b ON a.`IdPasien`=b.`IdPasien` AND `Status` = 0 WHERE 1");
                jcomCari1.jtablef.useColor(true);
                jcomCari1.setOrder(" ORDER BY `NamaPasien`, a.`IdPasien` ");
                jcomCari1.setSelectedIndex(11);
                break;
            case "Master Beautician":
                jcomCari1.setQuery("SELECT `IdBeautician` as 'ID', `NamaBeautician` as 'Nama Beautician', `NoTelepon` as 'No Telpon', `Alamat`, `Keterangan`, IF(`Status`=1,'Aktif','Tidak Aktif') as 'Status' FROM `tbmbeautician` WHERE 1 ");
                jcomCari1.setOrder(" ORDER BY `NamaBeautician` ");
                break;
            case "Master Tindakan":
                jcomCari1.setQuery("SELECT `IdTindakan` as 'ID', `NamaTindakan` as 'Nama Tindakan', IFNULL(`TipeTindakan`,'') as 'Tipe', `Harga`, a.`Keterangan`, IF(`Status`=1,'Aktif','Tidak Aktif') as 'Status' FROM `tbmtindakan`a LEFT JOIN `tbsmtipetindakan`b ON a.`IdTipeTindakan`=b.`IdTipeTindakan` WHERE 1 ");
                jcomCari1.setOrder(" ORDER BY `NamaTindakan` ");
                break;
            case "Antrian":
                jcomCari1.setQuery("SELECT `NoAntrian`, `KodePasien` as 'Kode', `NamaPasien` as 'Nama', `JenisKelamin` as 'Jenis Kelamin', DATE_FORMAT(`TanggalDaftar`,'%d-%m-%Y') as 'Tanggal Daftar', DATE_FORMAT(`TanggaLahir`,'%d-%m-%Y') as 'Tanggal Lahir', `NoTelpon` as 'No. Telpon', `Pekerjaan`, `Email`, `Alamat`, `Catatan`, `NoKartu` FROM `tbmpasien`a LEFT JOIN `tbantrian`b ON a.`IdPasien`=b.`IdPasien` WHERE `IdAntrian` IS NOT NULL AND `Tanggal` = CURDATE() AND b.`Status` = 0");
                jcomCari1.setOrder(" ORDER BY `NoAntrian` ");
                break;
            case "Master Pemasok":
                jcomCari1.setQuery("SELECT `IdPemasok` as 'ID', `NamaPemasok` as 'Nama Pemasok',  `NoTelpon`, `Alamat`, `Keterangan`, IF(`Status`=1,'Aktif','Tidak Aktif') as 'Status' FROM `tbmpemasok` WHERE 1 ");
                jcomCari1.setOrder(" ORDER BY `NamaPemasok` ");
                break;
            case "Penjualan":
                if (jCheckBoxF1.isSelected()) {
                    jcomCari1.setQuery("SELECT `IdPenjualanDetail` as 'ID', a.`NoTransaksi` as 'No. Transaksi', DATE_FORMAT(`Tanggal`,'%d-%m-%Y') as 'Tanggal', `NamaPasien` as 'Nama Pasien',`NoKolom` as 'No.', `NamaBarang` as 'Nama Barang', FORMAT(`Jumlah`,0) as 'Jumlah', FORMAT(b.`Harga`,0) as 'Harga', FORMAT(`Jumlah`*b.`Harga`,0) as 'Sub Total', a.`Keterangan` FROM `tbpenjualan`a JOIN `tbpenjualandetail`b ON a.`NoTransaksi`=b.`NoTransaksi` JOIN `tbmpasien`c ON a.`IdPasien`=c.`IdPasien` JOIN `tbmbarang`d ON b.`IdBarang`=d.`IdBarang` WHERE 1");
                } else {
                    jcomCari1.setQuery("SELECT `IdPenjualan` as 'ID', `NoTransaksi` as 'No. Transaksi', DATE_FORMAT(`Tanggal`,'%d-%m-%Y') as 'Tanggal', IFNULL(`NamaPasien`,'-') as 'Nama Pasien', a.`Keterangan` FROM `tbpenjualan`a LEFT JOIN `tbmpasien`b ON a.`IdPasien`=b.`IdPasien` WHERE 1");
                }
                jcomCari1.setOrder(" ORDER BY a.`NoTransaksi` DESC ");
                break;
            case "Barang Masuk":
                if (jCheckBoxF1.isSelected()) {
                    jcomCari1.setQuery("SELECT `IdBarangMasukDetail` as 'ID', a.`NoTransaksi` as 'No. Transaksi', DATE_FORMAT(`Tanggal`,'%d-%m-%Y') as 'Tanggal', `NamaPemasok` as 'Nama Pemasok', `NoKolom` as 'No', `NamaBarang` as 'Nama Barang', FORMAT(`Jumlah`,0) as 'Jumlah', FORMAT(b.`Harga`,0) as 'Harga', FORMAT(`Jumlah`*b.`Harga`,0) as 'Sub Total', a.`Keterangan` FROM `tbbarangmasuk`a JOIN `tbbarangmasukdetail`b ON a.`NoTransaksi`=b.`NoTransaksi` JOIN `tbmpemasok`c ON a.`IdPemasok`=c.`IdPemasok` JOIN `tbmbarang`d ON b.`IdBarang`=d.`IdBarang` WHERE 1");
                } else {
                    jcomCari1.setQuery("SELECT `IdBarangMasuk` as 'ID', a.`NoTransaksi` as 'No. Transaksi', DATE_FORMAT(`Tanggal`,'%d-%m-%Y') as 'Tanggal', IFNULL(`NamaPemasok`,'-') as 'Nama Pemasok', a.`Keterangan` FROM `tbbarangmasuk`a LEFT JOIN `tbmpemasok`b ON a.`IdPemasok`=b.`IdPemasok` WHERE 1");
                }
                jcomCari1.setOrder(" ORDER BY a.`NoTransaksi` DESC ");
                break;
            case "Penyesuaian Stok":
                jcomCari1.setQuery("SELECT `IdPenyesuaian` as 'ID', `NoPenyesuaian` as 'No. Penyesuaian', DATE_FORMAT(`Tanggal`,'%d-%m-%Y') as 'Tanggal', IFNULL(`NamaBarang`,'-') as 'Nama Barang', `Jumlah`, a.`Keterangan` FROM `tbpenyesuaianstok`a LEFT JOIN `tbmbarang`b ON a.`IdBarangLain`=b.`IdBarang` WHERE 1");
                jcomCari1.setOrder(" ORDER BY `NoPenyesuaian` DESC ");
                break;
            case "Perawatan":
                if (jCheckBoxF1.isSelected()) {
                    jcomCari1.setQuery("SELECT * FROM ((SELECT `IdPerawatanDetail` as 'ID', DATE_FORMAT(a.`Tanggal`,'%d-%m-%Y') as 'Tanggal', a.`NoAntrian` as 'No. Antrian', a.`NoInvoice` as 'No. Invoice', `NamaDokter` as 'Dokter', IFNULL(`NamaBeautician`,'-') as 'Beautician', `NamaPasien` as 'Pasien', 'Tindakan' as 'Jenis', `NamaTindakan` as 'Ket', FORMAT(`Jumlah`,0) as 'Jumlah', `Keluhan`, `Diagnosa`, a.`Catatan` FROM `tbperawatan`a JOIN `tbperawatandetail`b ON a.`NoInvoice`=b.`NoInvoice` JOIN `tbmdokter`c ON a.`IdDokter`=c.`IdDokter` LEFT JOIN `tbmbeautician`d ON a.`IdBeautician`=d.`IdBeautician` JOIN `tbmtindakan`e ON b.`IdTindakan`=e.`IdTindakan` JOIN `tbantrian`f ON a.`NoAntrian`=f.`NoAntrian` AND a.`Tanggal`=f.`Tanggal` JOIN `tbmpasien`g ON f.`IdPasien`=g.`IdPasien` WHERE 1 UNION ALL SELECT `IdObatDetail` as 'ID', DATE_FORMAT(a.`Tanggal`,'%d-%m-%Y') as 'Tanggal', a.`NoAntrian` as 'No. Antrian', a.`NoInvoice` as 'No. Invoice', `NamaDokter` as 'Nama Dokter', IFNULL(`NamaBeautician`,'-') as 'Beautician', `NamaPasien` as 'Pasien', 'Obat' as 'Jenis', `NamaBarang` as 'Ket', FORMAT(`Jumlah`,0) as 'Jumlah', `Keluhan`, `Diagnosa`, a.`Catatan` FROM `tbperawatan`a JOIN `tbobatdetail`b ON a.`NoInvoice`=b.`NoInvoice` JOIN `tbmdokter`c ON a.`IdDokter`=c.`IdDokter` LEFT JOIN `tbmbeautician`d ON a.`IdBeautician`=d.`IdBeautician` JOIN `tbmbarang`e ON b.`IdObat`=e.`IdBarang` JOIN `tbantrian`f ON a.`NoAntrian`=f.`NoAntrian` AND a.`Tanggal`=f.`Tanggal` JOIN `tbmpasien`g ON f.`IdPasien`=g.`IdPasien` WHERE 1) t1)");
                    jcomCari1.setOrder(" ORDER BY `No. Invoice` DESC, `Jenis` DESC ");
                } else {
                    jcomCari1.setQuery("SELECT `IdPerawatan` as 'ID', DATE_FORMAT(a.`Tanggal`,'%d-%m-%Y') as 'Tanggal', a.`NoAntrian` as 'No Antrian', `NoInvoice` as 'No. Invoice', `NamaDokter` as 'Dokter', `NamaBeautician` as 'Beautician', `NamaPasien` as 'Pasien', `Keluhan`, `Diagnosa`, a.`Catatan` FROM `tbperawatan`a JOIN `tbmdokter`b ON a.`IdDokter`=b.`IdDokter` LEFT JOIN `tbmbeautician`c ON a.`IdBeautician`=c.`IdBeautician` JOIN `tbantrian`d ON a.`NoAntrian`=d.`NoAntrian` AND a.`Tanggal`=d.`Tanggal` JOIN `tbmpasien`e ON d.`IdPasien`=e.`IdPasien` WHERE 1");
                    jcomCari1.setOrder(" ORDER BY a.`NoInvoice` DESC ");
                }
                break;
            case "Antrian Billing":
                jcomCari1.setQuery("SELECT b.`NoAntrian` as 'No. Antrian', c.`NoInvoice` as 'No. Invoice', `KodePasien` as 'Kode', `NamaPasien` as 'Nama', `JenisKelamin` as 'Jenis Kelamin', DATE_FORMAT(`TanggalDaftar`,'%d-%m-%Y') as 'Tanggal Daftar', DATE_FORMAT(`TanggaLahir`,'%d-%m-%Y') as 'Tanggal Lahir', `NoTelpon` as 'No. Telpon', `Pekerjaan`, `Email`, `Alamat`, a.`Catatan`, `NoKartu` FROM `tbmpasien`a LEFT JOIN `tbantrian`b ON a.`IdPasien`=b.`IdPasien` LEFT JOIN `tbperawatan`c ON b.`NoAntrian`=c.`NoAntrian` AND b.`Tanggal`=c.`Tanggal` LEFT JOIN `tbbilling`d ON c.`NoInvoice`=d.`NoInvoice` WHERE `IdAntrian` IS NOT NULL AND b.`Tanggal` = CURDATE() AND b.`Status` = 1 AND `IdBilling` IS NULL");
                jcomCari1.setOrder(" ORDER BY b.`NoAntrian` ");
                break;
            case "Billing":
                if (jCheckBoxF1.isSelected()) {
                    jcomCari1.setQuery("SELECT * FROM((SELECT `IdBillingTindakan` as 'ID', a.`NoBilling` as 'No. Billing', DATE_FORMAT(`Tanggal`,'%d-%m-%Y') as 'Tanggal', `NoInvoice` as 'No. Invoice', 'Tindakan' as 'Jenis', `NamaTindakan` as 'Item', FORMAT(`Jumlah`,0) as 'Jumlah', FORMAT(b.`Harga`,0) as 'Harga', FORMAT(`Jumlah`*b.`Harga`,0) as 'Sub Total', FORMAT(`Bayar`,0) as 'Bayar' FROM `tbbilling`a JOIN `tbbillingtindakan`b ON a.`NoBilling`=b.`NoBilling` JOIN `tbmtindakan`c ON b.`IdTindakan`=c.`IdTindakan` WHERE 1 UNION ALL SELECT `IdBillingObat` as 'ID', a.`NoBilling` as 'No. Billing', DATE_FORMAT(`Tanggal`,'%d-%m-%Y') as 'Tanggal', `NoInvoice` as 'No. Invoice', 'Obat' as 'Jenis', `NamaBarang` as 'Item', FORMAT(`Jumlah`,0) as 'Jumlah', FORMAT(b.`Harga`,0) as 'Harga', FORMAT(`Jumlah`*b.`Harga`,0) as 'Sub Total', FORMAT(`Bayar`,0) as 'Bayar' FROM `tbbilling`a JOIN `tbbillingobat`b ON a.`NoBilling`=b.`NoBilling` JOIN `tbmbarang`c ON b.`IdObat`=c.`IdBarang` WHERE 1) t1) ");
                    jcomCari1.setOrder(" ORDER BY `No. Invoice` DESC, `Jenis` DESC ");
                } else {
                    jcomCari1.setQuery("SELECT `IdBilling` as 'ID', `NoBilling` as 'No. Billing', DATE_FORMAT(`Tanggal`,'%d-%m-%Y') as 'Tanggal', `NoInvoice` as 'No. Invoice', FORMAT(`Bayar`,0) as 'Jumlah Bayar' FROM `tbbilling` WHERE 1");
                    jcomCari1.setOrder(" ORDER BY `NoBilling` DESC ");
                }
                break;
            default:
                throw new AssertionError();
        }
        jcomCari1.tampilkan();
    }
}
