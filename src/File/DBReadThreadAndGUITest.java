package File;

import KomponenGUI.FDateF;
import java.util.Date;
import LSubProces.Koneksi;
import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class DBReadThreadAndGUITest {

    public DBReadThreadAndGUITest() {
        GUIThread gui = new GUIThread();
        gui.start();

        DBReaderThread dbReader = new DBReaderThread(gui);
        dbReader.start();
    }

    public static void main(String[] args) {

        GUIThread gui = new GUIThread();
        gui.start();

        DBReaderThread dbReader = new DBReaderThread(gui);
        dbReader.start();
    }

}

class GUIThread extends Thread {

    @Override
    public void run() {
        JFrame frame = new MenuUtama();
        frame.setVisible(true);
    }
}

class DBReaderThread extends Thread {

    GUIThread gui;
    int count = 0;

    public DBReaderThread(GUIThread gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            Koneksi koneksi = new Koneksi();
            Connection con = koneksi.getConnection();
            Statement statement = con.createStatement();
            while (true) {
                try {
                    ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM `tbantrian` WHERE `Status` = 0 AND `Tanggal` = '" + FDateF.datetostr(new Date(), "yyyy-MM-dd") + "'");
                    while (resultSet.next()) {
                        MenuUtama.jlableF1.setText("Pasien Hari Ini: " + resultSet.getString(1));
                    }
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBReaderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
;
}
