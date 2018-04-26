/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static GlobalVar.Var.*;
import KomponenGUI.FDateF;
import LSubProces.DRunSelctOne;
import LSubProces.MultiInsert;
import LSubProces.RunSelct;
import static Proses.Penjualan.JCPasien;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author richk
 */
public class Billing extends javax.swing.JFrame {

    /**
     * Creates new form Perawatan
     */
    String Dari, Parameter;

    public Billing(String dari, Object parameter) {
        Parameter = parameter.toString();
        Dari = dari;
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        JTNoInvoice.setText(getNoBilling());
        JBUbah.setVisible(false);
        if (dari.equals("Antrian Billing")) {
            setTitle("Tambah Billing");
            loadPerawatan(parameter);
            JBUbah.setVisible(false);
        } else {
            setTitle("Ubah Billing");
            JBTambah.setVisible(false);
            loadData(parameter);
        }
        JCNamaDokter.requestFocus();
    }

    void loadData(Object idEdit) {
        DRunSelctOne dRunSelctOne = new DRunSelctOne();
        dRunSelctOne.seterorm("Eror gagal Menampilkan Data Billing");
        dRunSelctOne.setQuery("SELECT `IdBilling` as 'ID', `NoBilling` as 'No. Billing', DATE_FORMAT(a.`Tanggal`,'%d-%m-%Y') as 'Tanggal', a.`NoInvoice` as 'No. Invoice', `NamaDokter` as 'Nama Dokter', IFNULL(`NamaBeautician`,'-- Pilih Nama Beautician --') as 'Nama Beautician', FORMAT(`Bayar`,0) as 'Jumlah Bayar' FROM `tbbilling`a JOIN `tbperawatan`b ON a.`NoInvoice`=b.`NoInvoice` JOIN `tbmdokter`c ON b.`IdDokter`=c.`IdDokter` LEFT JOIN `tbmbeautician`d ON b.`IdBeautician`=d.`IdBeautician` WHERE `IdBilling` = '" + Parameter + "'");
        ArrayList<String> list = dRunSelctOne.excute();
        JTNoBilling.setText(list.get(1));
        JDTanggal.setDate(FDateF.strtodate(list.get(2), "dd-MM-yyyy"));
        JTNoInvoice.setText(list.get(3));
        JCNamaDokter.setSelectedItem(list.get(3));
        JCNamaBeautician.setSelectedItem(list.get(4));
        //JTBayar.setInt(list.get(5));
        DefaultTableModel model = (DefaultTableModel) JTableTindakan.getModel();
        model.getDataVector().removeAllElements();
        RunSelct runSelct = new RunSelct();
        runSelct.setQuery("SELECT `IdBillingTindakan` as 'ID', `NoBilling` as 'No. Billing', `NamaTindakan` as 'Nama Tindakan', FORMAT(`Jumlah`,0) as 'Jumlah', FORMAT(a.`Harga`,0) as 'Harga', FORMAT(`Jumlah`*a.`Harga`,0) as 'Sub Total' FROM `tbbillingtindakan`a JOIN `tbmtindakan`b ON a.`IdTindakan`=b.`IdTindakan` WHERE `NoBilling` = '" + JTNoBilling.getText() + "'");
        try {
            ResultSet rs = runSelct.excute();
            int row = 0;
            while (rs.next()) {
                model.addRow(new Object[]{"", "", "", "", ""});
                JTableTindakan.setValueAt(rs.getString(3), row, 0);
                JTableTindakan.setValueAt(rs.getString(4).replace(",", "."), row, 1);
                JTableTindakan.setValueAt(rs.getString(5).replace(",", "."), row, 2);
                JTableTindakan.setValueAt(rs.getString(6).replace(",", "."), row, 2);
                row++;
            }
        } catch (SQLException e) {
            out.println("E6" + e);
            showMessageDialog(null, "Gagal Panggil Data Billing Tindakan");
        } finally {
            runSelct.closecon();
        }
        DefaultTableModel model2 = (DefaultTableModel) JTableObat.getModel();
        model2.getDataVector().removeAllElements();
        RunSelct runSelct2 = new RunSelct();
        runSelct2.setQuery("SELECT `IdBillingObat` as 'ID', `NoBilling` as 'No. Billing', `NamaBarang` as 'Nama Obat', FORMAT(`Jumlah`,0) as 'Jumlah', FORMAT(`Harga`,0) as 'Harga', FORMAT(`Jumlah`*a.`Harga`,0) as 'Sub Total' FROM `tbbillingobat`a JOIN `tbmbarang`b ON a.`IdObat`=b.`IdBarang` WHERE `NoBilling` = '" + JTNoInvoice.getText() + "'");
        try {
            ResultSet rs2 = runSelct2.excute();
            int row2 = 0;
            while (rs2.next()) {
                model2.addRow(new Object[]{"", "", "", "", ""});
                JTableObat.setValueAt(rs2.getString(3), row2, 0);
                JTableObat.setValueAt(rs2.getString(4).replace(",", "."), row2, 1);
                JTableObat.setValueAt(rs2.getString(5).replace(",", "."), row2, 2);
                JTableObat.setValueAt(rs2.getString(6).replace(",", "."), row2, 3);
                row2++;
            }
        } catch (SQLException e) {
            out.println("E6" + e);
            showMessageDialog(null, "Gagal Panggil Data Billing Obat");
        } finally {
            runSelct2.closecon();
        }
    }

    void loadPerawatan(Object noInvoice) {
        DRunSelctOne dRunSelctOne = new DRunSelctOne();
        dRunSelctOne.seterorm("Gagal loadPerawatan()");
        dRunSelctOne.setQuery("SELECT `IdPerawatan` as 'ID', CONCAT('(',`KodePasien`,') ',`NamaPasien`) as 'Nama Pasien', DATE_FORMAT(a.`Tanggal`,'%d-%m-%Y') as 'Tanggal', a.`NoAntrian` as 'No. Antrian', `NoInvoice` as 'No. Invoice', `NamaDokter` as 'Nama Dokter', `NamaBeautician` as 'Nama Beautician' FROM `tbperawatan`a JOIN `tbantrian`b ON a.`NoAntrian`=b.`NoAntrian` AND a.`Tanggal`=b.`Tanggal` JOIN `tbmpasien`c ON b.`IdPasien`=c.`IdPasien` JOIN `tbmdokter`d ON a.`IdDokter`=d.`IdDokter` LEFT JOIN `tbmbeautician`e ON a.`IdBeautician`=e.`IdBeautician` WHERE a.`NoInvoice` = '" + noInvoice + "'");
        ArrayList<String> list = dRunSelctOne.excute();
        JTNamaPasien.setText(list.get(1));
        JDTanggal.setDate(FDateF.strtodate(list.get(2), "dd-MM-yyyy"));
        JTNoAntrian.setText(list.get(3));
        JTNoInvoice.setText(list.get(4));
        JCNamaDokter.setSelectedItem(list.get(5));
        JCNamaBeautician.setSelectedItem(list.get(6));
        DefaultTableModel model = (DefaultTableModel) JTableTindakan.getModel();
        model.getDataVector().removeAllElements();
        RunSelct runSelct = new RunSelct();
        runSelct.setQuery("SELECT `IdPerawatanDetail` as 'ID', `NoInvoice` as 'No. Invoice', `NamaTindakan` as 'Nama Tindakan', FORMAT(`Jumlah`,0) as 'Jumlah', FORMAT(`Harga`,0) as 'Harga', FORMAT(`Jumlah`*`Harga`,0) as 'Sub Total' FROM `tbperawatandetail`a JOIN `tbmtindakan`b ON a.`IdTindakan`=b.`IdTindakan` WHERE `NoInvoice` = '" + JTNoInvoice.getText() + "'");
        try {
            ResultSet rs = runSelct.excute();
            int row = 0;
            while (rs.next()) {
                model.addRow(new Object[]{"", "", "", "", ""});
                JTableTindakan.setValueAt(rs.getString(3), row, 0);
                JTableTindakan.setValueAt(rs.getString(4).replace(",", "."), row, 1);
                JTableTindakan.setValueAt(rs.getString(5).replace(",", "."), row, 2);
                JTableTindakan.setValueAt(rs.getString(6).replace(",", "."), row, 3);
                row++;
            }
        } catch (SQLException e) {
            out.println("E6" + e);
            showMessageDialog(null, "Gagal Panggil Data Detail Tindakan");
        } finally {
            runSelct.closecon();
        }
        DefaultTableModel model2 = (DefaultTableModel) JTableObat.getModel();
        model2.getDataVector().removeAllElements();
        RunSelct runSelct2 = new RunSelct();
        runSelct2.setQuery("SELECT `IdObatDetail` as 'ID', `NoInvoice` as 'No. Invoice', `NamaBarang` as 'Nama Obat', FORMAT(`Jumlah`,0) as 'Jumlah', FORMAT(`Harga`,0) as 'Harga', FORMAT(`Jumlah`*`Harga`,0) as 'Sub Total' FROM `tbobatdetail`a JOIN `tbmbarang`b ON a.`IdObat`=b.`IdBarang` WHERE `NoInvoice` = '" + JTNoInvoice.getText() + "'");
        try {
            ResultSet rs2 = runSelct2.excute();
            int row2 = 0;
            while (rs2.next()) {
                model2.addRow(new Object[]{"", "", "", "", ""});
                JTableObat.setValueAt(rs2.getString(3), row2, 0);
                JTableObat.setValueAt(rs2.getString(4).replace(",", "."), row2, 1);
                JTableObat.setValueAt(rs2.getString(5).replace(",", "."), row2, 2);
                JTableObat.setValueAt(rs2.getString(6).replace(",", "."), row2, 3);
                row2++;
            }
        } catch (SQLException e) {
            out.println("E6" + e);
            showMessageDialog(null, "Gagal Panggil Data Detail Obat");
        } finally {
            runSelct2.closecon();
        }
    }

    public static String getNoBilling() {
        NumberFormat nf = new DecimalFormat("000000");
        String NoTransaksi = null;
        RunSelct runSelct = new RunSelct();
        runSelct.setQuery("SELECT `NoBilling` FROM `tbbilling` ORDER BY `NoBilling` DESC LIMIT 1");
        try {
            ResultSet rs = runSelct.excute();
            if (!rs.isBeforeFirst()) {
                NoTransaksi = "KB-" + "000001" + "-BIL";
            }
            while (rs.next()) {
                String nobarangmasuk = rs.getString("NoBilling");
                String number = nobarangmasuk.substring(3, 9);
                //String month = nobarangmasuk.substring(8, 10);
                int p = 1 + parseInt(number);
                /*if (month.equals(FDateF.datetostr(new Date(), "MM"))) {
                    p = 1 + parseInt(number);
                } else {
                    p = 1;
                }*/
                if (p != 999999) {
                    NoTransaksi = "KB-" + nf.format(p) + "-BIL";
                } else if (p == 999999) {
                    p = 1;
                    NoTransaksi = "KB-" + nf.format(p) + "-BIL";
                }
            }
        } catch (SQLException e) {
            out.println("E6" + e);
            showMessageDialog(null, "Gagal Generate Nomor Billing");
        } finally {
            runSelct.closecon();
        }
        return NoTransaksi;
    }

    boolean checkInput() {
        if (JDTanggal.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Tanggal Tidak Boleh Kosong");
            return false;
        } else if (JTNoInvoice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No. Invoice Boleh Kosong");
            return false;
        } else if (JCNamaDokter.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Nama Dokter Terlebih Dahulu.");
            return false;
        } else if (JTableTindakan.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Silahkan Isi Tindakan Terlebih Dahulu.");
            return false;
        } else if (JTableObat.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Silahkan Isi Obat Terlebih Dahulu.");
            return false;
        } else {
            return true;
        }
    }

    boolean checkTableTindakan() {
        if (JCTindakan.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Tindakan Terlebih Dahulu.");
            return false;
        } else if (JTJumlahTindakan.getText().replace("0", "").isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jumlah Tidak Boleh Kosong.");
            return false;
        } else if (JTHargaTindakan.getText().replace("0", "").isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harga Tidak Boleh Kosong.");
            return false;
        } else {
            return true;
        }
    }

    boolean checkTableObat() {
        if (JCObat.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Obat Terlebih Dahulu.");
            return false;
        } else if (JTJumlahObat.getText().replace("0", "").isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jumlah Tidak Boleh Kosong.");
            return false;
        } else if (JTHargaObat.getText().replace("0", "").isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harga Tidak Boleh Kosong.");
            return false;
        } else {
            return true;
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

        jlableF2 = new KomponenGUI.JlableF();
        jlableF3 = new KomponenGUI.JlableF();
        JTNamaPasien = new KomponenGUI.JtextF();
        jlableF4 = new KomponenGUI.JlableF();
        jlableF5 = new KomponenGUI.JlableF();
        JCNamaDokter = new KomponenGUI.JcomboboxF();
        jlableF6 = new KomponenGUI.JlableF();
        jlableF7 = new KomponenGUI.JlableF();
        JCNamaBeautician = new KomponenGUI.JcomboboxF();
        jPanel1 = new javax.swing.JPanel();
        jbuttonF1 = new KomponenGUI.JbuttonF();
        JTJumlahTindakan = new KomponenGUI.JPlaceHolder();
        JCTindakan = new KomponenGUI.JcomboboxF();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTableTindakan = new KomponenGUI.JtableF();
        jbuttonF3 = new KomponenGUI.JbuttonF();
        JTHargaTindakan = new KomponenGUI.JPlaceHolder();
        JTSubTotalTindakan = new KomponenGUI.JPlaceHolder();
        jPanel2 = new javax.swing.JPanel();
        jbuttonF2 = new KomponenGUI.JbuttonF();
        JTJumlahObat = new KomponenGUI.JPlaceHolder();
        JCObat = new KomponenGUI.JcomboboxF();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTableObat = new KomponenGUI.JtableF();
        jbuttonF4 = new KomponenGUI.JbuttonF();
        JTHargaObat = new KomponenGUI.JPlaceHolder();
        JTSubTotalObat = new KomponenGUI.JPlaceHolder();
        JBTambah = new KomponenGUI.JbuttonF();
        JDTanggal = new KomponenGUI.JdateCF();
        jlableF14 = new KomponenGUI.JlableF();
        jlableF15 = new KomponenGUI.JlableF();
        jlableF16 = new KomponenGUI.JlableF();
        JTNoInvoice = new KomponenGUI.JtextF();
        jlableF17 = new KomponenGUI.JlableF();
        JBKembali = new KomponenGUI.JbuttonF();
        JBUbah = new KomponenGUI.JbuttonF();
        jSeparator1 = new javax.swing.JSeparator();
        jlableF18 = new KomponenGUI.JlableF();
        jSeparator2 = new javax.swing.JSeparator();
        jlableF19 = new KomponenGUI.JlableF();
        jlableF20 = new KomponenGUI.JlableF();
        jlableF21 = new KomponenGUI.JlableF();
        JTNoAntrian = new KomponenGUI.JtextF();
        jlableF22 = new KomponenGUI.JlableF();
        JTNoBilling = new KomponenGUI.JtextF();
        jlableF23 = new KomponenGUI.JlableF();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jlableF2.setText("Nama Pasien");

        jlableF3.setText(":");

        JTNamaPasien.setEnabled(false);

        jlableF4.setText("Nama Dokter");

        jlableF5.setText(":");

        JCNamaDokter.load("SELECT '-- Pilih Nama Dokter --' as 'NamaDokter' UNION ALL SELECT `NamaDokter` FROM `tbmdokter`");
        JCNamaDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JCNamaDokterKeyPressed(evt);
            }
        });

        jlableF6.setText("Nama Beautician");

        jlableF7.setText(":");

        JCNamaBeautician.load("SELECT '-- Pilih Nama Beautician --' as 'Nama Beautician' UNION ALL SELECT `NamaBeautician` FROM `tbmbeautician`");
        JCNamaBeautician.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JCNamaBeauticianKeyPressed(evt);
            }
        });

        jbuttonF1.setText("Tambah");
        jbuttonF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonF1ActionPerformed(evt);
            }
        });

        JTJumlahTindakan.setPlaceholder("Jumlah");
        JTJumlahTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTJumlahTindakanKeyPressed(evt);
            }
        });

        JCTindakan.load("SELECT '-- Pilih Tindakan --' as 'NamaTindakan' UNION ALL SELECT `NamaTindakan` FROM `tbmtindakan`");
        JCTindakan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JCTindakanItemStateChanged(evt);
            }
        });
        JCTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JCTindakanKeyPressed(evt);
            }
        });

        JTableTindakan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tindakan", "Jumlah", "Harga", "Sub Total"
            }
        ));
        jScrollPane1.setViewportView(JTableTindakan);
        if (JTableTindakan.getColumnModel().getColumnCount() > 0) {
            JTableTindakan.getColumnModel().getColumn(0).setMinWidth(500);
            JTableTindakan.getColumnModel().getColumn(0).setPreferredWidth(500);
            JTableTindakan.getColumnModel().getColumn(0).setMaxWidth(500);
            JTableTindakan.getColumnModel().getColumn(1).setMinWidth(105);
            JTableTindakan.getColumnModel().getColumn(1).setPreferredWidth(105);
            JTableTindakan.getColumnModel().getColumn(1).setMaxWidth(105);
            JTableTindakan.getColumnModel().getColumn(2).setMinWidth(105);
            JTableTindakan.getColumnModel().getColumn(2).setPreferredWidth(105);
            JTableTindakan.getColumnModel().getColumn(2).setMaxWidth(105);
        }
        JTableTindakan.setrender(new int[]{1,2}, new String[]{"Number","Number"});

        jbuttonF3.setText("Hapus");
        jbuttonF3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonF3ActionPerformed(evt);
            }
        });

        JTHargaTindakan.setPlaceholder("Harga");
        JTHargaTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTHargaTindakanKeyPressed(evt);
            }
        });

        JTSubTotalTindakan.setPlaceholder("Sub Total");
        JTSubTotalTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTSubTotalTindakanKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(JCTindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTJumlahTindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTHargaTindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTSubTotalTindakan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 877, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbuttonF3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbuttonF1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JCTindakan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbuttonF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTJumlahTindakan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTHargaTindakan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTSubTotalTindakan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbuttonF3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbuttonF2.setText("Tambah");
        jbuttonF2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonF2ActionPerformed(evt);
            }
        });

        JTJumlahObat.setPlaceholder("Jumlah");
        JTJumlahObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTJumlahObatKeyPressed(evt);
            }
        });

        JCObat.load("SELECT '-- Pilih Obat --' as 'NamaBarang' UNION ALL SELECT `NamaBarang` FROM `tbmbarang`");
        JCObat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JCObatItemStateChanged(evt);
            }
        });
        JCObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JCObatKeyPressed(evt);
            }
        });

        JTableObat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Obat", "Jumlah", "Harga", "Sub Total"
            }
        ));
        jScrollPane2.setViewportView(JTableObat);
        if (JTableObat.getColumnModel().getColumnCount() > 0) {
            JTableObat.getColumnModel().getColumn(0).setMinWidth(500);
            JTableObat.getColumnModel().getColumn(0).setPreferredWidth(500);
            JTableObat.getColumnModel().getColumn(0).setMaxWidth(500);
            JTableObat.getColumnModel().getColumn(1).setMinWidth(105);
            JTableObat.getColumnModel().getColumn(1).setPreferredWidth(105);
            JTableObat.getColumnModel().getColumn(1).setMaxWidth(105);
            JTableObat.getColumnModel().getColumn(2).setMinWidth(105);
            JTableObat.getColumnModel().getColumn(2).setPreferredWidth(105);
            JTableObat.getColumnModel().getColumn(2).setMaxWidth(105);
        }
        JTableObat.setrender(new int[]{1,2}, new String[]{"Number","Number"});

        jbuttonF4.setText("Hapus");
        jbuttonF4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonF4ActionPerformed(evt);
            }
        });

        JTHargaObat.setPlaceholder("Harga");
        JTHargaObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTHargaObatKeyPressed(evt);
            }
        });

        JTSubTotalObat.setPlaceholder("Sub Total");
        JTSubTotalObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTSubTotalObatKeyPressed(evt);
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
                        .addComponent(JCObat, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTJumlahObat, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTHargaObat, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTSubTotalObat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 877, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbuttonF4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbuttonF2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JCObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbuttonF2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTJumlahObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTHargaObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTSubTotalObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbuttonF4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JBTambah.setText("Simpan");
        JBTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBTambahActionPerformed(evt);
            }
        });

        JDTanggal.setDate(new Date());
        JDTanggal.setDateFormatString("dd-MM-yyyy");
        JDTanggal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                JDTanggalPropertyChange(evt);
            }
        });
        JDTanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JDTanggalKeyPressed(evt);
            }
        });

        jlableF14.setText(":");

        jlableF15.setText("Tanggal");

        jlableF16.setText(":");

        JTNoInvoice.setEnabled(false);

        jlableF17.setText("No. Invoice");

        JBKembali.setText("Kembali");

        JBUbah.setText("Ubah");
        JBUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBUbahActionPerformed(evt);
            }
        });

        jlableF18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlableF18.setText("-- Tindakan Pasien --");
        jlableF18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jlableF19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlableF19.setText("-- Obat & Injeksi Pasien --");
        jlableF19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jlableF20.setText("Antrian No.");

        jlableF21.setText(":");

        JTNoAntrian.setEnabled(false);

        jlableF22.setText(":");

        JTNoBilling.setEnabled(false);

        jlableF23.setText("No. Billing");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlableF18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jlableF2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jlableF4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jlableF6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jlableF3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(JTNamaPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jlableF15, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jlableF14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(JDTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jlableF5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JCNamaDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jlableF7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JCNamaBeautician, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jlableF20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jlableF21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JTNoAntrian, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jlableF17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jlableF16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(JTNoInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(JBKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(JBUbah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JBTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlableF19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(744, 744, 744)
                .addComponent(jlableF23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlableF22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTNoBilling, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlableF2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlableF3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(JTNamaPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlableF14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlableF15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(JDTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlableF4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlableF5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JCNamaDokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlableF20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlableF21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTNoAntrian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlableF6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlableF7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JCNamaBeautician, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlableF17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlableF16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTNoInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTNoBilling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlableF22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlableF23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jlableF18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jlableF19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 96, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JBTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBUbah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbuttonF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonF1ActionPerformed
        tambahTableTindakan();
    }//GEN-LAST:event_jbuttonF1ActionPerformed

    private void JCTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JCTindakanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JTJumlahTindakan.requestFocus();
        }
    }//GEN-LAST:event_JCTindakanKeyPressed

    private void JTJumlahTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTJumlahTindakanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JTHargaTindakan.requestFocus();
        }
    }//GEN-LAST:event_JTJumlahTindakanKeyPressed

    private void jbuttonF2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonF2ActionPerformed
        tambahTableObat();
    }//GEN-LAST:event_jbuttonF2ActionPerformed

    private void JTJumlahObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTJumlahObatKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JTHargaObat.requestFocus();
        }
    }//GEN-LAST:event_JTJumlahObatKeyPressed

    private void JCObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JCObatKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JTJumlahTindakan.requestFocus();
        }
    }//GEN-LAST:event_JCObatKeyPressed

    private void JDTanggalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_JDTanggalPropertyChange

    }//GEN-LAST:event_JDTanggalPropertyChange

    private void JDTanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JDTanggalKeyPressed

    }//GEN-LAST:event_JDTanggalKeyPressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (JBTambah.isVisible()) {
            tambahPerawatan = null;
        } else {
            ubahPerawatan = null;
        }
    }//GEN-LAST:event_formWindowClosed

    private void jbuttonF3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonF3ActionPerformed
        hapusTableTindakan();
    }//GEN-LAST:event_jbuttonF3ActionPerformed

    private void jbuttonF4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonF4ActionPerformed
        hapusTableObat();
    }//GEN-LAST:event_jbuttonF4ActionPerformed

    private void JTHargaTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTHargaTindakanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JTSubTotalTindakan.requestFocus();
        }
    }//GEN-LAST:event_JTHargaTindakanKeyPressed

    private void JTHargaObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTHargaObatKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JTSubTotalObat.requestFocus();
        }
    }//GEN-LAST:event_JTHargaObatKeyPressed

    private void JBTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBTambahActionPerformed
        tambahData();
    }//GEN-LAST:event_JBTambahActionPerformed

    private void JBUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBUbahActionPerformed
        ubahData();
    }//GEN-LAST:event_JBUbahActionPerformed

    private void JCTindakanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JCTindakanItemStateChanged
        setHargaTindakan();
    }//GEN-LAST:event_JCTindakanItemStateChanged

    private void JCObatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JCObatItemStateChanged
        setHargaObat();
    }//GEN-LAST:event_JCObatItemStateChanged
    private void JBKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBKembaliActionPerformed
        dispose();
    }//GEN-LAST:event_JBKembaliActionPerformed

    private void JCNamaDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JCNamaDokterKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JCNamaBeautician.requestFocus();
        }
    }//GEN-LAST:event_JCNamaDokterKeyPressed

    private void JCNamaBeauticianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JCNamaBeauticianKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JCTindakan.requestFocus();
        }
    }//GEN-LAST:event_JCNamaBeauticianKeyPressed

    private void JTSubTotalTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTSubTotalTindakanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tambahTableTindakan();
        }
    }//GEN-LAST:event_JTSubTotalTindakanKeyPressed

    private void JTSubTotalObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTSubTotalObatKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tambahTableObat();
        }
    }//GEN-LAST:event_JTSubTotalObatKeyPressed

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
            java.util.logging.Logger.getLogger(Billing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Billing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Billing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Billing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Billing("", "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private KomponenGUI.JbuttonF JBKembali;
    private KomponenGUI.JbuttonF JBTambah;
    private KomponenGUI.JbuttonF JBUbah;
    private KomponenGUI.JcomboboxF JCNamaBeautician;
    private KomponenGUI.JcomboboxF JCNamaDokter;
    private KomponenGUI.JcomboboxF JCObat;
    private KomponenGUI.JcomboboxF JCTindakan;
    private static KomponenGUI.JdateCF JDTanggal;
    private KomponenGUI.JPlaceHolder JTHargaObat;
    private KomponenGUI.JPlaceHolder JTHargaTindakan;
    private KomponenGUI.JPlaceHolder JTJumlahObat;
    private KomponenGUI.JPlaceHolder JTJumlahTindakan;
    private KomponenGUI.JtextF JTNamaPasien;
    private KomponenGUI.JtextF JTNoAntrian;
    private KomponenGUI.JtextF JTNoBilling;
    private KomponenGUI.JtextF JTNoInvoice;
    private KomponenGUI.JPlaceHolder JTSubTotalObat;
    private KomponenGUI.JPlaceHolder JTSubTotalTindakan;
    private KomponenGUI.JtableF JTableObat;
    private KomponenGUI.JtableF JTableTindakan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private KomponenGUI.JbuttonF jbuttonF1;
    private KomponenGUI.JbuttonF jbuttonF2;
    private KomponenGUI.JbuttonF jbuttonF3;
    private KomponenGUI.JbuttonF jbuttonF4;
    private KomponenGUI.JlableF jlableF14;
    private KomponenGUI.JlableF jlableF15;
    private KomponenGUI.JlableF jlableF16;
    private KomponenGUI.JlableF jlableF17;
    private KomponenGUI.JlableF jlableF18;
    private KomponenGUI.JlableF jlableF19;
    private KomponenGUI.JlableF jlableF2;
    private KomponenGUI.JlableF jlableF20;
    private KomponenGUI.JlableF jlableF21;
    private KomponenGUI.JlableF jlableF22;
    private KomponenGUI.JlableF jlableF23;
    private KomponenGUI.JlableF jlableF3;
    private KomponenGUI.JlableF jlableF4;
    private KomponenGUI.JlableF jlableF5;
    private KomponenGUI.JlableF jlableF6;
    private KomponenGUI.JlableF jlableF7;
    // End of variables declaration//GEN-END:variables

    void tambahTableTindakan() {
        if (checkTableTindakan()) {
            if (JCTindakan.getSelectedIndex() != 0) {
                DefaultTableModel model = (DefaultTableModel) JTableTindakan.getModel();
                model.addRow(new Object[]{JCTindakan.getSelectedItem(), JTJumlahTindakan.getText(), JTHargaTindakan.getText()});
                JCTindakan.requestFocus();
                JTJumlahTindakan.setText("");
                JTHargaTindakan.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan Pilih Tindakan Terlebih Dahulu");
            }
        }
    }

    void tambahTableObat() {
        if (checkTableObat()) {
            if (JCObat.getSelectedIndex() != 0) {
                DefaultTableModel model = (DefaultTableModel) JTableObat.getModel();
                model.addRow(new Object[]{JCObat.getSelectedItem(), JTJumlahObat.getText(), JTHargaObat.getText()});
                JCObat.requestFocus();
                JTJumlahObat.setText("");
                JTHargaObat.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan Pilih Obat Terlebih Dahulu");
            }

        }
    }

    void hapusTableTindakan() {
        if (JTableTindakan.getSelectedRow() != -1) {
            ((DefaultTableModel) JTableTindakan.getModel()).removeRow(JTableTindakan.getSelectedRow());
        }
    }

    void hapusTableObat() {
        if (JTableObat.getSelectedRow() != -1) {
            ((DefaultTableModel) JTableObat.getModel()).removeRow(JTableObat.getSelectedRow());
        }
    }

    void setHargaTindakan() {
        if (JCTindakan.getSelectedIndex() != 0) {
            DRunSelctOne dRunSelctOne = new DRunSelctOne();
            dRunSelctOne.seterorm("Gagal setHargaTindakan()");
            dRunSelctOne.setQuery("SELECT `Harga` FROM `tbmtindakan` WHERE `NamaTindakan` = '" + JCTindakan.getSelectedItem() + "'");
            ArrayList<String> list = dRunSelctOne.excute();
            JTHargaTindakan.setInt(list.get(0));
        }
    }

    void setHargaObat() {
        if (JCObat.getSelectedIndex() != 0) {
            DRunSelctOne dRunSelctOne = new DRunSelctOne();
            dRunSelctOne.seterorm("Gagal setHargaObat()");
            dRunSelctOne.setQuery("SELECT `Harga` FROM `tbmbarang` WHERE `NamaBarang` = '" + JCObat.getSelectedItem() + "'");
            ArrayList<String> list = dRunSelctOne.excute();
            JTHargaObat.setInt(list.get(0));
        }
    }

    void tambahData() {
        if (checkInput()) {
            boolean Berhasil;
            MultiInsert multiInsert = new MultiInsert();
            Berhasil = multiInsert.OpenConnection();
            if (Berhasil) {
                Berhasil = multiInsert.setautocomit(false);
                if (Berhasil) {
                    //Berhasil = multiInsert.Excute("INSERT INTO `tbperawatan`(`Tanggal`, `NoInvoice`, `NoAntrian`, `IdDokter`, `IdBeautician`, `Keluhan`, `Diagnosa`, `Catatan`) VALUES ('" + FDateF.datetostr(JDTanggal.getDate(), "yyyy-MM-dd") + "','" + JTNoInvoice.getText() + "', '" + JTNoAntrian.getText() + "', (SELECT `IdDokter` FROM `tbmdokter` WHERE `NamaDokter` = '" + JCNamaDokter.getSelectedItem() + "'),(SELECT `IdBeautician` FROM `tbmbeautician` WHERE `NamaBeautician` = '" + JCNamaBeautician.getSelectedItem() + "'),'" + JTKeluhanPasien.getText() + "','" + JTDiagnosaPasien.getText() + "','" + JTCatatanPasien.getText() + "')", null);
                    if (Berhasil) {
                        for (int i = 0; i < JTableTindakan.getRowCount(); i++) {
                            Berhasil = multiInsert.Excute("INSERT INTO `tbperawatandetail`(`NoInvoice`, `IdTindakan`, `Jumlah`, `Harga`) VALUES ('" + JTNoInvoice.getText() + "',(SELECT `IdTindakan` FROM `tbmtindakan` WHERE `NamaTindakan` = '" + JTableTindakan.getValueAt(i, 0) + "'),'" + JTableTindakan.getValueAt(i, 1).toString().replace(".", "") + "','" + JTableTindakan.getValueAt(i, 2).toString().replace(".", "") + "')", null);
                            if (Berhasil) {
                                for (int j = 0; j < JTableObat.getRowCount(); j++) {
                                    Berhasil = multiInsert.Excute("INSERT INTO `tbobatdetail`(`NoInvoice`, `IdObat`, `Jumlah`, `Harga`) VALUES ('" + JTNoInvoice.getText() + "',(SELECT `IdBarang` FROM `tbmbarang` WHERE `NamaBarang` = '" + JTableObat.getValueAt(i, 0) + "'),'" + JTableObat.getValueAt(i, 1).toString().replace(".", "") + "','" + JTableObat.getValueAt(i, 2).toString().replace(".", "") + "')", null);
                                    if (Berhasil) {
                                        Berhasil = multiInsert.Excute("UPDATE `tbantrian` SET `Status` = 1 WHERE `NoAntrian` = '" + JTNoAntrian.getText() + "'", null);
                                    }
                                }
                            }
                        }
                    }
                }
                if (Berhasil == false) {
                    multiInsert.rollback();
                    multiInsert.closecon();
                    JOptionPane.showMessageDialog(this, "Gagal Tambah Data Perawatan");
                }
                if (Berhasil == true) {
                    JOptionPane.showMessageDialog(this, "Berhasil Tambah Data Perawatan");
                    multiInsert.Commit();
                    multiInsert.closecon();
//                if (print) {
//                    printing();
//                }
                    if (listPerawatan != null) {
                        listPerawatan.load();
                    }
                    if (listAntrian != null) {
                        listAntrian.load();
                        if (listAntrian.jcomCari1.jtablef.getRowCount() == 0) {
                            listAntrian.dispose();
                        }
                    }
//                    JTableTindakan.setModel(new javax.swing.table.DefaultTableModel(
//                            new Object[][]{}, new String[]{"Tindakan", "Jumlah", "Harga"}
//                    ));
//                    JTableTindakan.getColumnModel().getColumn(0).setPreferredWidth(668);
//                    JTableTindakan.getColumnModel().getColumn(1).setPreferredWidth(105);
//                    JCTindakan.requestFocus();
//                    JTJumlahTindakan.setText("");
//                    JTHargaTindakan.setText("");
//                    JTableTindakan.clearSelection();
//
//                    JTableObat.setModel(new javax.swing.table.DefaultTableModel(
//                            new Object[][]{}, new String[]{"Obat", "Jumlah", "Harga"}
//                    ));
//                    JTableObat.getColumnModel().getColumn(0).setPreferredWidth(668);
//                    JTableObat.getColumnModel().getColumn(1).setPreferredWidth(105);
//                    JCObat.requestFocus();
//                    JTJumlahObat.setText("");
//                    JTHargaObat.setText("");
//                    JTableObat.clearSelection();
//                    JTNoInvoice.setText(getNoInvoice());
                    dispose();
                }
            }
        }
    }

    void ubahData() {
        if (checkInput()) {
            boolean Berhasil;
            MultiInsert multiInsert = new MultiInsert();
            Berhasil = multiInsert.OpenConnection();
            if (Berhasil) {
                Berhasil = multiInsert.setautocomit(false);
                if (Berhasil) {
                    //Berhasil = multiInsert.Excute("UPDATE `tbperawatan` SET `Tanggal`='" + FDateF.datetostr(JDTanggal.getDate(), "yyyy-MM-dd") + "',`NoInvoice`='" + JTNoInvoice.getText() + "',`NoAntrian`='" + JTNoAntrian.getText() + "',`IdDokter`=(SELECT `IdDokter` FROM `tbmdokter` WHERE `NamaDokter` = '" + JCNamaDokter.getSelectedItem() + "'),`IdBeautician`=(SELECT `IdBeautician` FROM `tbmbeautician` WHERE `NamaBeautician` = '" + JCNamaBeautician.getSelectedItem() + "'),`Keluhan`='" + JTKeluhanPasien.getText() + "',`Diagnosa`='" + JTDiagnosaPasien.getText() + "',`Catatan`='" + JTCatatanPasien.getText() + "' WHERE `IdPerawatan` = '" + Parameter + "'", null);
                    if (Berhasil) {
                        Berhasil = multiInsert.Excute("DELETE FROM `tbperawatandetail` WHERE `NoInvoice` = '" + JTNoInvoice.getText() + "'", null);
                        if (Berhasil) {
                            Berhasil = multiInsert.Excute("DELETE FROM `tbobatdetail` WHERE `NoInvoice` = '" + JTNoInvoice.getText() + "'", null);
                            if (Berhasil) {
                                for (int i = 0; i < JTableTindakan.getRowCount(); i++) {
                                    Berhasil = multiInsert.Excute("INSERT INTO `tbperawatandetail`(`NoInvoice`, `IdTindakan`, `Jumlah`, `Harga`) VALUES ('" + JTNoInvoice.getText() + "',(SELECT `IdTindakan` FROM `tbmtindakan` WHERE `NamaTindakan` = '" + JTableTindakan.getValueAt(i, 0) + "'),'" + JTableTindakan.getValueAt(i, 1).toString().replace(".", "") + "','" + JTableTindakan.getValueAt(i, 2).toString().replace(".", "") + "')", null);
                                    if (Berhasil) {
                                        for (int j = 0; j < JTableObat.getRowCount(); j++) {
                                            Berhasil = multiInsert.Excute("INSERT INTO `tbobatdetail`(`NoInvoice`, `IdObat`, `Jumlah`, `Harga`) VALUES ('" + JTNoInvoice.getText() + "',(SELECT `IdBarang` FROM `tbmbarang` WHERE `NamaBarang` = '" + JTableObat.getValueAt(i, 0) + "'),'" + JTableObat.getValueAt(i, 1).toString().replace(".", "") + "','" + JTableObat.getValueAt(i, 2).toString().replace(".", "") + "')", null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (Berhasil == false) {
                    multiInsert.rollback();
                    multiInsert.closecon();
                    JOptionPane.showMessageDialog(this, "Gagal Ubah Data Perawatan");
                }
                if (Berhasil == true) {
                    JOptionPane.showMessageDialog(this, "Berhasil Ubah Data Perawatan");
                    multiInsert.Commit();
                    multiInsert.closecon();
//                if (print) {
//                    printing();
//                }
                    dispose();
                    ubahPerawatan = null;
                    if (listPerawatan != null) {
                        listPerawatan.load();
                    }
                }
            }
        }
    }
}
