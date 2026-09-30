/*
Nama Program  : Gaji Harian dan Lembur Pegawai
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil, Muhammah Irsyad Azzarul Haq, Djeremy Rieldy Marchiano Panjaitan
NPM  Anggota  : 140810250090, 250078, 250063
Tanggal Buat  : 24/09/2026
Deskripsi     : Menghitung gaji harian + lembur berdasarkan lama kerja (waktu datang - waktu pulang).
                Lembur berlaku jika kerja >= 8 jam, kelebihan dibulatkan ke bawah (minimal 1 jam).
                Pegawai yang kerja kurang dari 8 jam diberi status "peringatan".
*/

import java.util.Locale;
import java.util.Scanner;

class Waktu {
    private int jam;
    private int menit;
    private int detik;

    // Constructor
    public Waktu() {
        this(0, 0, 0);
    }

    public Waktu(int jam, int menit, int detik) {
        this.jam = jam;
        this.menit = menit;
        this.detik = detik;
    }

    // Constructor dari teks "HH:mm:ss"
    public Waktu(String teks) {
        String[] p = teks.split(":");
        if (teks.length() != 8 || p.length != 3) {
            throw new IllegalArgumentException("Format waktu tidak valid: " + teks);
        }
        try {
            int j = Integer.parseInt(p[0]);
            int m = Integer.parseInt(p[1]);
            int d = Integer.parseInt(p[2]);
            if (j < 0 || j > 23 || m < 0 || m > 59 || d < 0 || d > 59) {
                throw new IllegalArgumentException("Nilai waktu di luar batas: " + teks);
            }
            this.jam = j;
            this.menit = m;
            this.detik = d;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Format waktu tidak valid: " + teks);
        }
    }

    // Input
    public void input(Scanner scanner, String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                Waktu w = new Waktu(scanner.nextLine().trim());
                this.jam = w.jam;
                this.menit = w.menit;
                this.detik = w.detik;
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Format waktu tidak valid. Contoh: 08:00:00 atau 17:15:10");
            }
        }
    }

    // Proses
    public int keDetik() {
        return jam * 3600 + menit * 60 + detik;
    }

    public static Waktu dariDetik(int total) {
        return new Waktu(total / 3600, (total % 3600) / 60, total % 60);
    }

    // Selisih dari waktu ini sampai waktu lain (menangani lewat tengah malam)
    public Waktu selisih(Waktu lain) {
        int beda = lain.keDetik() - this.keDetik();
        if (beda < 0) {
            beda += 24 * 3600;
        }
        return dariDetik(beda);
    }

    // Output
    public String tampil() {
        return String.format("%02d:%02d:%02d", jam, menit, detik);
    }
}

class Pegawai {
    private static final int[] GAJI_HARIAN = {150000, 200000, 400000, 500000};
    private static final int[] TARIF_LEMBUR = {50000, 75000, 150000, 200000};
    private static final int BATAS_DETIK = 8 * 3600;

    private String nip;
    private String nama;
    private int golongan;
    private Waktu datang;
    private Waktu pulang;
    private Waktu lama;
    private Waktu jamLembur;
    private long gajiHarian;
    private long lembur;
    private long total;
    private String status;

    // Constructor
    public Pegawai() {
        this("", "", 0, new Waktu(), new Waktu());
    }

    public Pegawai(String nip, String nama, int golongan, Waktu datang, Waktu pulang) {
        this.nip = nip;
        this.nama = nama;
        this.golongan = golongan;
        this.datang = datang;
        this.pulang = pulang;
        proses();
    }

    // Input
    public void input(Scanner scanner) {
        System.out.print("Masukkan NIP: ");
        this.nip = scanner.nextLine().trim();
        System.out.print("Masukkan Nama: ");
        this.nama = scanner.nextLine().trim();
        this.golongan = 0;
        while (this.golongan < 1 || this.golongan > 4) {
            System.out.print("Masukkan Golongan (1/2/3/4): ");
            try {
                this.golongan = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                this.golongan = 0;
            }
            if (this.golongan < 1 || this.golongan > 4) {
                System.out.println("Golongan harus 1, 2, 3, atau 4.");
            }
        }
        this.datang = new Waktu();
        this.pulang = new Waktu();
        this.datang.input(scanner, "Masukkan Waktu Datang (HH:mm:ss): ");
        this.pulang.input(scanner, "Masukkan Waktu Pulang (HH:mm:ss): ");
        proses();
    }

    // Proses
    public void proses() {
        this.lama = datang.selisih(pulang);
        int detikLama = lama.keDetik();
        int kelebihan = detikLama - BATAS_DETIK;

        this.gajiHarian = (golongan >= 1 && golongan <= 4) ? GAJI_HARIAN[golongan - 1] : 0;
        this.jamLembur = new Waktu();
        this.lembur = 0;

        if (detikLama < BATAS_DETIK) {
            this.status = "peringatan";
        } else {
            this.status = "ok";
            if (kelebihan >= 3600 && golongan >= 1 && golongan <= 4) {
                this.jamLembur = Waktu.dariDetik(kelebihan);
                long jamBulat = kelebihan / 3600; // pembulatan ke bawah
                this.lembur = jamBulat * TARIF_LEMBUR[golongan - 1];
            }
        }
        this.total = this.gajiHarian + this.lembur;
    }

    public boolean sudahDiisi() {
        return !nip.isEmpty();
    }

    private static String rupiah(long nilai) {
        return String.format(Locale.US, "%,d", nilai).replace(',', '.');
    }

    // Output
    public static void cetakHeader() {
        String garis = "-".repeat(122);
        System.out.println("\n" + " ".repeat(45) + "Daftar Gaji Harian PT Informatika");
        System.out.println(garis);
        System.out.printf("%-3s %-6s %-14s %-3s %-9s %-9s %-9s %-11s %11s %9s %9s  %-10s%n",
                "No", "NIP", "Nama", "Gol", "Datang", "Pulang", "Lama", "Jam Lembur",
                "Gaji Harian", "Lembur", "Total", "Status");
        System.out.println(garis);
    }

    public static void cetakGaris() {
        System.out.println("-".repeat(122));
    }

    public void cetakBaris(int no) {
        System.out.printf("%-3s %-6s %-14s %-3d %-9s %-9s %-9s %-11s %11s %9s %9s  %-10s%n",
                no + ".", nip, nama, golongan, datang.tampil(), pulang.tampil(), lama.tampil(),
                jamLembur.tampil(), rupiah(gajiHarian), rupiah(lembur), rupiah(total), status);
    }
}

public class Nomor3_063_078_090 {
    static int bacaInt(Scanner scanner, String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }

    static void inputObjek(Scanner scanner, Pegawai[] daftar) {
        int no = bacaInt(scanner, "Pilih objek yang diinput (1-3): ");
        if (no < 1 || no > daftar.length) {
            System.out.println("Objek tidak valid.");
            return;
        }
        daftar[no - 1].input(scanner);
        System.out.println("Data objek " + no + " tersimpan.");
    }

    static void tampilkanDaftar(Pegawai[] daftar) {
        Pegawai.cetakHeader();
        int no = 0;
        for (Pegawai p : daftar) {
            if (p.sudahDiisi()) {
                no++;
                p.cetakBaris(no);
            }
        }
        Pegawai.cetakGaris();
    }

    public static void main(String[] args) {
        Pegawai obj1 = new Pegawai("001", "Djeremy", 3, new Waktu(8, 0, 0), new Waktu(17, 15, 10));
        Pegawai obj2 = new Pegawai("002", "Irsyad", 1, new Waktu("08:00:00"), new Waktu("15:30:00"));
        Pegawai obj3 = new Pegawai(); // diisi lewat keyboard

        Pegawai[] daftar = {obj1, obj2, obj3};
        Scanner scanner = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n===== MENU GAJI HARIAN PT INFORMATIKA =====");
            System.out.println("1. Input data pegawai (objek 1-3)");
            System.out.println("2. Tampilkan daftar gaji harian");
            System.out.println("3. Keluar");
            pilihan = bacaInt(scanner, "Pilih menu: ");

            switch (pilihan) {
                case 1:
                    inputObjek(scanner, daftar);
                    break;
                case 2:
                    tampilkanDaftar(daftar);
                    break;
                case 3:
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 3);
    }
}