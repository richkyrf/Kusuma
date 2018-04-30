/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.util.Scanner;

/**
 *
 * @author richk
 */
public class Nasabah {

    int Norek;
    String Nama;
    int Saldo;

    Nasabah() {
        Norek = 0;
        Nama = "";
        Saldo = 0;
    }

    Nasabah(int norek, String nama, int saldo) {
        Norek = norek;
        Nama = nama;
        Saldo = saldo;
    }

    void BacaNasabah() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Norek: ");
        Norek = sc.nextInt();
        System.out.print("Nama: ");
        Nama = sc.next();
        System.out.print("Saldo: ");
        Saldo = sc.nextInt();
    }

    void TulisNasabah() {
        System.out.println("Nasabah: " + Norek + ", " + Nama + ", " + Saldo);
    }

    int getNorek() {
        return Norek;
    }

    String getNama() {
        return Nama;
    }

    int getSaldo() {
        return Saldo;
    }

    void setSaldo(int saldo) {
        Saldo = saldo;
    }

    public static void main(String[] args) {
        Nasabah n = new Nasabah();
        n.BacaNasabah();
        n.TulisNasabah();
    }

}
