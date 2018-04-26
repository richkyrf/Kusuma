package File;

import KomponenGUI.FDateF;
import KomponenGUI.JlableF;
import java.util.Date;
import LSubProces.Koneksi;
import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Threading {

    public Threading(JlableF jl, String query, String pesan, Integer sleep) {
        DBReaderThread dbReader = new DBReaderThread(jl, query, pesan, sleep);
        dbReader.start();
    }
}

class DBReaderThread extends Thread {

    JlableF jlableF;
    String Query;
    String Pesan;
    Integer Sleep;

    public DBReaderThread(JlableF jl, String query, String pesan, Integer sleep) {
        this.jlableF = jl;
        this.Query = query;
        this.Pesan = pesan;
        this.Sleep = sleep;
    }

    @Override
    public void run() {
        try {
            Koneksi koneksi = new Koneksi();
            Connection con = koneksi.getConnection();
            Statement statement = con.createStatement();
            while (true) {
                try {
                    ResultSet resultSet = statement.executeQuery(Query);
                    while (resultSet.next()) {
                        jlableF.setText(Pesan + resultSet.getString(1));
                    }
                    TimeUnit.MILLISECONDS.sleep(Sleep);
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
