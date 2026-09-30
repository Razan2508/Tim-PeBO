import java.util.Scanner;

class Waktu {
    private int jam, menit, detik;

    // Constructor
    Waktu() { }
    Waktu(int jam, int menit, int detik) { setWaktu(jam, menit, detik); }

    // Setter: menolak nilai di luar rentang waktu
    boolean setWaktu(int jam, int menit, int detik) {
        if (jam < 0 || jam > 23 || menit < 0 || menit > 59 || detik < 0 || detik > 59)
            return false;
        this.jam = jam; this.menit = menit; this.detik = detik;
        return true;
    }

    // Baca angka dengan validasi rentang (diulang sampai valid)
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

    // Getter
    int getJam() { return jam; }
    int getMenit() { return menit; }
    int getDetik() { return detik; }

    // Input (dalam class): baca di dalam class, isi lewat setter
    void inputDalam(Scanner sc) {
        int j = Waktu.bacaAngka(sc, "Jam  ", 0, 23);
        int m = Waktu.bacaAngka(sc, "Menit", 0, 59);
        int d = Waktu.bacaAngka(sc, "Detik", 0, 59);
        setWaktu(j, m, d);
    }

    // Output (dalam class)
    void outputDalam() {
        System.out.printf("%02d:%02d:%02d%n", jam, menit, detik);
    }

    private int keDetik() { return jam * 3600 + menit * 60 + detik; }

    // Proses cara 1: fungsi return
    Waktu selisihReturn(Waktu w) {
        int s = Math.abs(keDetik() - w.keDetik());
        return new Waktu(s / 3600, (s % 3600) / 60, s % 60);
    }

    // Proses cara 2: void (hasil disimpan di objek ini)
    void selisihVoid(Waktu w1, Waktu w2) {
        int s = Math.abs(w1.keDetik() - w2.keDetik());
        setWaktu(s / 3600, (s % 3600) / 60, s % 60);
    }
}

public class Main {
    // Input luar class: baca di luar class (Scanner di main), isi lewat setter
    static void inputLuar(Waktu w, Scanner sc) {
        int j = Waktu.bacaAngka(sc, "Jam  ", 0, 23);
        int m = Waktu.bacaAngka(sc, "Menit", 0, 59);
        int d = Waktu.bacaAngka(sc, "Detik", 0, 59);
        w.setWaktu(j, m, d);
    }

    // Output luar class
    static void outputLuar(Waktu w) {
        System.out.printf("%02d:%02d:%02d%n", w.getJam(), w.getMenit(), w.getDetik());
    }

    static void uji(boolean modeVoid, int objek, Scanner sc) {
        Waktu w1 = new Waktu(), w2 = new Waktu();

        switch (objek) {
            case 1: // Input dari Dalam / Setter
                System.out.println("\n[Objek 1 - Input dari Dalam / Setter]");
                System.out.println("Waktu 1:"); w1.inputDalam(sc);
                System.out.println("Waktu 2:"); w2.inputDalam(sc);
                break;
            case 2: // Input dari Luar / Scanner
                System.out.println("\n[Objek 2 - Input dari Luar / Scanner]");
                System.out.println("Waktu 1:"); inputLuar(w1, sc);
                System.out.println("Waktu 2:"); inputLuar(w2, sc);
                break;
            case 3: // Input dari Constructor
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