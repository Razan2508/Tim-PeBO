/*
Nama Program  : Selisih Waktu
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil , DJEREMY RIELDY MARCHIANO PANJAITAN , MUHAMMAD IRSYAD AZHARUL HAQ
NPM  Anggota  : 140810250090 , 140810250063 , 140810250078
Tanggal Buat  : 26/09/2026
Deskripsi     : Mencari selisih waktu berdasarkan input jam , menit , detik
*/
import java.util.Scanner;

class Waktu {
    private int jam, menit, detik;

    Waktu() { 
        this.jam = 0;
        this.menit = 0;
        this.detik = 0;
    }
    
    Waktu(int jam, int menit, int detik) { 
        this.jam = jam;
        this.menit = menit;
        this.detik = detik;
    }

    void setJam(int jam) {
        this.jam = jam;
    }
    
    void setMenit(int menit) {
        this.menit = menit;
    }
    
    void setDetik(int detik) {
        this.detik = detik;
    }

    static int bacaAngka(Scanner sc, String label, int min, int max) {
        while (true) {
            System.out.print(label + " (" + min + "-" + max + ") : ");
            if (sc.hasNextInt()) {
                int n = sc.nextInt();
                if (n >= min && n <= max) return n;
            } else {
                sc.next();
            }
            System.out.println("Input tidak valid! Masukkan angka " + min + " sampai " + max + ".");
        }
    }

    int getJam() { 
        return this.jam; 
    }
    int getMenit() { 
        return this.menit; 
    }
    int getDetik() { 
        return this.detik; 
    }

    void inputDalam(Scanner sc) {
        int jam = Waktu.bacaAngka(sc, "Jam  ", 0, 23);
        int menit = Waktu.bacaAngka(sc, "Menit", 0, 59);
        int detik = Waktu.bacaAngka(sc, "Detik", 0, 59);
        
        this.setJam(jam);
        this.setMenit(menit);
        this.setDetik(detik);
    }

    void outputDalam() {
        System.out.printf("%02d:%02d:%02d%n", this.jam, this.menit, this.detik);
    }

    private int keDetik() { 
        return this.jam * 3600 + this.menit * 60 + this.detik; 
    }

    Waktu selisihReturn(Waktu w) {
        int s = Math.abs(this.keDetik() - w.keDetik());
        return new Waktu(s / 3600, (s % 3600) / 60, s % 60);
    }

    void selisihVoid(Waktu w1, Waktu w2) {
        int s = Math.abs(w1.keDetik() - w2.keDetik());
        this.setJam(s / 3600);
        this.setMenit((s % 3600) / 60);
        this.setDetik(s % 60);
    }
}

public class Nomor2_063_078_090 {
    static void inputLuar(Waktu w, Scanner sc) {
        int jam = Waktu.bacaAngka(sc, "Jam  ", 0, 23);
        int menit = Waktu.bacaAngka(sc, "Menit", 0, 59);
        int detik = Waktu.bacaAngka(sc, "Detik", 0, 59);
        
        w.setJam(jam);
        w.setMenit(menit);
        w.setDetik(detik);
    }

    static void outputLuar(Waktu w) {
        System.out.printf("%02d:%02d:%02d%n", w.getJam(), w.getMenit(), w.getDetik());
    }

    static void uji(boolean modeVoid, int objek, Scanner sc) {
        Waktu w1 = new Waktu(), w2 = new Waktu();

        switch (objek) {
            case 1:
                System.out.println("\n[Objek 1 - Input dari Dalam / Setter]");
                System.out.println("Waktu 1:"); w1.inputDalam(sc);
                System.out.println("Waktu 2:"); w2.inputDalam(sc);
                break;
            case 2:
                System.out.println("\n[Objek 2 - Input dari Luar / Scanner]");
                System.out.println("Waktu 1:"); inputLuar(w1, sc);
                System.out.println("Waktu 2:"); inputLuar(w2, sc);
                break;
            case 3:
                System.out.println("\n[Objek 3 - Input dari Constructor]");
                w1 = new Waktu(8, 30, 15);
                w2 = new Waktu(10, 45, 50);
                break;
        }

        Waktu hasil;
        if (modeVoid) {
            hasil = new Waktu();
            hasil.selisihVoid(w1, w2);
        } else {
            hasil = w1.selisihReturn(w2);
        }

        System.out.print("\nWaktu 1                      : "); w1.outputDalam();
        System.out.print("Waktu 2                      : "); w2.outputDalam();
        System.out.print("Selisih (output dalam class) : "); hasil.outputDalam();
        System.out.print("Selisih (output luar class)  : "); outputLuar(hasil);
    }

    static void subMenu(boolean modeVoid, Scanner sc) {
        int p;
        do {
            System.out.println("\n--- SUB MENU: UJI " + (modeVoid ? "VOID" : "RETURN") + " ---");
            System.out.println("1. Objek 1 (Input dari Dalam / Setter)");
            System.out.println("2. Objek 2 (Input dari Luar / Scanner)");
            System.out.println("3. Objek 3 (Input dari Constructor)");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih objek (1-4): ");
            p = sc.nextInt();
            if (p >= 1 && p <= 3) uji(modeVoid, p, sc);
        } while (p != 4);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int p;
        do {
            System.out.println("\n==========================================");
            System.out.println("               MENU UTAMA");
            System.out.println("==========================================");
            System.out.println("1. Menguji dengan Fungsi Void");
            System.out.println("2. Menguji dengan Fungsi Return");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu (1-3): ");
            p = sc.nextInt();
            if (p == 1) subMenu(true, sc);
            else if (p == 2) subMenu(false, sc);
        } while (p != 3);
    }
}