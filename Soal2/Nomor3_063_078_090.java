/*
Nama Program  : Gaji Harian dan Lembur Pegawai (Full OOP)
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil, Muhammah Irsyad Azzarul Haq, Djeremy Rieldy Marchiano Panjaitan
NPM  Anggota  : 140810250090, 250078, 250063
Tanggal Buat  : 24/09/2026
Deskripsi     : Menghitung gaji harian + lembur dengan metode 3 Input (Dalam, Constructor, Luar/Setter).
*/

import java.util.Scanner;

class Waktu {
    static Scanner inputWaktu = new Scanner(System.in); 

    private int jam;
    private int menit;
    private int detik;

    public Waktu() {
        this.jam = 0;
        this.menit = 0;
        this.detik = 0;
    }

    public Waktu(int jam, int menit, int detik) {
        this.jam = jam;
        this.menit = menit;
        this.detik = detik;
    }

    public void setJam(int jam) { 
        this.jam = jam; 
    }
    public void setMenit(int menit) { 
        this.menit = menit; 
    }
    public void setDetik(int detik) { 
        this.detik = detik; 
    }

    public int getJam() { 
        return jam; 
    }
    public int getMenit() { 
        return menit; 
    }
    public int getDetik() { 
        return detik; 
    }

    public void inputDalam(String pesan) {
        System.out.println(pesan);
        System.out.print("  Jam (0-23)   : ");
        int j = inputWaktu.nextInt();
        System.out.print("  Menit (0-59) : ");
        int m = inputWaktu.nextInt();
        System.out.print("  Detik (0-59) : ");
        int d = inputWaktu.nextInt();
        inputWaktu.nextLine();

        this.setJam(j);
        this.setMenit(m);
        this.setDetik(d);
    }

    public int keDetik() {
        return jam * 3600 + menit * 60 + detik;
    }

    public static Waktu dariDetik(int total) {
        return new Waktu(total / 3600, (total % 3600) / 60, total % 60);
    }

    public Waktu selisih(Waktu lain) {
        int beda = lain.keDetik() - this.keDetik();
        if (beda < 0) beda += 24 * 3600;
        return dariDetik(beda);
    }

    public String tampil() {
        String strJam = (jam < 10 ? "0" : "") + jam;
        String strMenit = (menit < 10 ? "0" : "") + menit;
        String strDetik = (detik < 10 ? "0" : "") + detik;
        return strJam + ":" + strMenit + ":" + strDetik;
    }
}


class Pegawai {
    static Scanner inputPegawai = new Scanner(System.in);

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

    private String formatRupiah(long nilai) {
        StringBuilder angka = new StringBuilder(String.valueOf(nilai));
        for (int i = angka.length() - 3; i > 0; i -= 3) {
            angka.insert(i, ".");
        }
        return angka.toString();
    }

    private long gapokGolongan(int gol) {
        switch (gol) {
            case 1: return 150000;
            case 2: return 200000;
            case 3: return 400000;
            case 4: return 500000;
            default: return 0;
        }
    }

    private long tarifLembur(int gol) {
        switch (gol) {
            case 1: return 50000;
            case 2: return 75000;
            case 3: return 150000;
            case 4: return 200000;
            default: return 0;
        }
    }

    public Pegawai() {
        this.nip = "";
        this.nama = "";
        this.golongan = 0;
        this.datang = new Waktu();
        this.pulang = new Waktu();
        this.lama = new Waktu();
        this.jamLembur = new Waktu();
        this.gajiHarian = 0;
        this.lembur = 0;
        this.total = 0;
        this.status = "-";
    }

    public Pegawai(String nip, String nama, int golongan, Waktu datang, Waktu pulang) {
        this.setNip(nip);
        this.setNama(nama);
        this.setGolongan(golongan);
        this.setDatang(datang);
        this.setPulang(pulang);
        this.proses();
    }

    public void setNip(String nip) { 
        this.nip = nip; 
    }
    public void setNama(String nama) { 
        this.nama = nama; 
    }
    public void setGolongan(int golongan) { 
        this.golongan = golongan; 
    }
    public void setDatang(Waktu datang) { 
        this.datang = datang; 
    }
    public void setPulang(Waktu pulang) { 
        this.pulang = pulang; 
    }

    public String getNip() { 
        return nip; 
    }
    public String getNama() { 
        return nama; 
    }
    public int getGolongan() { 
        return golongan; 
    }

    public boolean sudahDiisi() {
        return !nip.isEmpty();
    }

    public void inputDalam() {
        System.out.print("Masukkan NIP: ");
        this.setNip(inputPegawai.nextLine());

        System.out.print("Masukkan Nama: ");
        this.setNama(inputPegawai.nextLine());

        System.out.print("Masukkan Golongan (1/2/3/4): ");
        this.setGolongan(inputPegawai.nextInt());
        inputPegawai.nextLine();

        Waktu dtg = new Waktu();
        dtg.inputDalam("Masukkan Waktu Datang:");
        this.setDatang(dtg);

        Waktu plg = new Waktu();
        plg.inputDalam("Masukkan Waktu Pulang:");
        this.setPulang(plg);

        this.proses();
    }

    public void proses() {
        this.lama = this.datang.selisih(this.pulang);
        int detikLama = this.lama.keDetik();
        int kelebihan = detikLama - BATAS_DETIK;

        this.gajiHarian = this.gapokGolongan(this.golongan);
        this.jamLembur = new Waktu();
        this.lembur = 0;

        if (detikLama < BATAS_DETIK) {
            this.status = "peringatan";
        } else {
            this.status = "ok";
            if (kelebihan >= 3600) {
                this.jamLembur = Waktu.dariDetik(kelebihan);
                long jamBulat = kelebihan / 3600;
                this.lembur = jamBulat * this.tarifLembur(this.golongan);
            }
        }
        this.total = this.gajiHarian + this.lembur;
    }

    public static void cetakHeader() {
        System.out.println("-".repeat(122));
        System.out.printf("%-4s%-7s%-15s%-4s%-10s%-10s%-10s%-12s%11s%10s%10s  %-10s\n",
                "No", "NIP", "Nama", "Gol", "Datang", "Pulang", "Lama", "Jam Lembur",
                "Gaji Harian", "Lembur", "Total", "Status");
        System.out.println("-".repeat(122));
    }

    public void cetakBaris(int no) {
        System.out.printf("%-4s%-7s%-15s%-4d%-10s%-10s%-10s%-12s%11s%10s%10s  %-10s\n",
                no + ".", nip, nama, golongan, datang.tampil(), pulang.tampil(),
                lama.tampil(), jamLembur.tampil(),
                formatRupiah(gajiHarian), formatRupiah(lembur), formatRupiah(total), status);
    }
}

public class Nomor3_063_078_090 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Pegawai obj1 = new Pegawai();
        Pegawai obj2 = new Pegawai();
        Pegawai obj3 = new Pegawai();
        int pilihan;

        do {
            System.out.println("\n===== MENU GAJI HARIAN PT INFORMATIKA =====");
            System.out.println("1. Input Objek 1 (Cara: Input Dalam Class)");
            System.out.println("2. Input Objek 2 (Cara: Konstruktor dari Luar)");
            System.out.println("3. Input Objek 3 (Cara: Input Luar -> Setter)");
            System.out.println("4. Tampilkan Tabel Gaji");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    System.out.println("\n--- MENGISI OBJEK 1 (INPUT DALAM) ---");
                    obj1.inputDalam();
                    System.out.println("Data Objek 1 tersimpan!");
                    break;

                case 2:
                    System.out.println("\n--- MENGISI OBJEK 2 (KONSTRUKTOR) ---");
                    System.out.println("Data disuntikkan secara otomatis dari luar melalui Konstruktor...");
                    obj2 = new Pegawai("002", "Djeremy", 3, new Waktu(8, 0, 0), new Waktu(17, 15, 10));
                    System.out.println("Data Objek 2 tersimpan!");
                    break;

                case 3:
                    System.out.println("\n--- MENGISI OBJEK 3 (INPUT LUAR -> SETTER) ---");
                    System.out.print("Masukkan NIP: ");
                    obj3.setNip(scanner.nextLine());

                    System.out.print("Masukkan Nama: ");
                    obj3.setNama(scanner.nextLine());

                    System.out.print("Masukkan Golongan (1/2/3/4): ");
                    obj3.setGolongan(scanner.nextInt());
                    scanner.nextLine();

                    System.out.println("Masukkan Waktu Datang:");
                    System.out.print("  Jam (0-23)   : "); int j1 = scanner.nextInt();
                    System.out.print("  Menit (0-59) : "); int m1 = scanner.nextInt();
                    System.out.print("  Detik (0-59) : "); int d1 = scanner.nextInt();
                    scanner.nextLine();
                    obj3.setDatang(new Waktu(j1, m1, d1));

                    System.out.println("Masukkan Waktu Pulang:");
                    System.out.print("  Jam (0-23)   : "); int j2 = scanner.nextInt();
                    System.out.print("  Menit (0-59) : "); int m2 = scanner.nextInt();
                    System.out.print("  Detik (0-59) : "); int d2 = scanner.nextInt();
                    scanner.nextLine();
                    obj3.setPulang(new Waktu(j2, m2, d2));

                    obj3.proses();
                    System.out.println("Data Objek 3 tersimpan!");
                    break;

                case 4:
                    System.out.println("\n" + " ".repeat(45) + "Daftar Gaji Harian PT Informatika");
                    Pegawai.cetakHeader();

                    if (obj1.sudahDiisi()) obj1.cetakBaris(1);
                    if (obj2.sudahDiisi()) obj2.cetakBaris(2);
                    if (obj3.sudahDiisi()) obj3.cetakBaris(3);

                    System.out.println("-".repeat(122));
                    break;

                case 5:
                    System.out.println("Keluar dari program. Terima Kasih!");
                    break;

                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 5);
        
        scanner.close();
    }
}