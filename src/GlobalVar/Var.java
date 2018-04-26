/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GlobalVar;

import File.*;
import Master.*;
import List.*;
import Proses.*;
import static java.awt.Frame.NORMAL;
import javax.swing.JFrame;

/**
 *
 * @author richky
 */
public class Var {

    public static Login login;
    public static TambahUser tambahUser;
    public static ResetPasswordUser resetPasswordUser;
    public static GantiPassword gantiPassword;
    public static MenuUtama menuUtama;

    public static MasterBarang tambahMasterBarang, ubahMasterBarang;
    public static MasterDokter tambahMasterDokter, ubahMasterDokter;
    public static MasterPasien tambahMasterPasien, ubahMasterPasien;
    public static MasterPemasok tambahMasterPemasok, ubahMasterPemasok;
    public static MasterBeautician tambahMasterBeautician, ubahMasterBeautician;
    public static MasterTindakan tambahMasterTindakan, ubahMasterTindakan;

    public static List listMasterBarang, listMasterDokter, listMasterPasien, listPenjualan, listMasterBeautician, listMasterTindakan, listAntrian, listMasterPemasok, listBarangMasuk, listPenyesuaianStok, listPerawatan;

    public static Jcari jcari;
    public static Penjualan tambahPenjualan, ubahPenjualan;
    public static BarangMasuk tambahBarangMasuk, ubahBarangMasuk;
    public static Perawatan tambahPerawatan, ubahPerawatan;
    public static PenyesuaianStok tambahPenyesuaianStok;
}