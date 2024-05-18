package Belajar_Java;

import java.util.Scanner;

class resto_sederhana {
    public static void main(String[] args) {
        
        Scanner makanan = new Scanner(System.in);

        String menu;
        int jumlah;
        int harga;
        int bayar;
        int total;

        System.out.println("============== SELAMAT DATANG DI PROGRAM RESTORAN SEDERHANA ==========\n");

        System.out.print("1.Gado-gado\t Rp.15000\n");
        System.out.print("2.Soto Ayam\t Rp.15500\n");
        System.out.print("3.Sate Ayam\t Rp.20000\n");
        System.out.print("4.Sate Kambing\t Rp.18000\n");
        System.out.print("5.Sate Lembu\t Rp.23000\n");
        System.out.print("6.Sate Kuda\t Rp.20000\n");

        System.out.print("\nMasukkan Nomor Pesanan Anda: ");
        menu = makanan.nextLine().toLowerCase();

        switch(menu) {
            case "1":
                harga = 15000;
                break;
            case "2":
                harga = 15500;
                break;
            case "3":
                harga = 20000;
                break;
            case "4":
                harga = 18000;
                break;
            case "5":
                harga = 23000;
                break;
            case "6":
                harga = 20000;
                break;
            default:
                System.out.println("\nMaaf Pesanan Anda Tidak Ada Di Resto Kami");
                return;
        }

        System.out.print("Masukkan Jumlah Pesanan Anda: ");
        jumlah = makanan.nextInt();

        if(menu.equals("1")) {
            total = harga * jumlah;
        } else if (menu.equals("2")) {
            total = harga * jumlah;
        } else if (menu.equals("3")) {
            total = harga * jumlah;
        } else if (menu.equals("4")) {
            total = harga * jumlah;
        } else if (menu.equals("5")) {
            total = harga * jumlah;
        } else {
            total = harga * jumlah;
        }

        System.out.print("Masukkan Bayaran: ");
        bayar = makanan.nextInt();

        if(bayar >= total) {
            System.out.println("\nUang Anda Cukup");
            System.out.println("Kembalian Anda Rp." + (bayar - total));
            System.out.println("\n========== TERIMHA KASIH ==========");
        } else {
            System.out.println("\nUang Anda Tidak Cukup");
            System.out.println("Silahkan Bayar Rp." + (total - bayar));
        }
    }
}