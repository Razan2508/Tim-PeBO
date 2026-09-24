/*
Nama Program  : 
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil, Muhammah Irsyad Azzarul Haq, Djeremy Rieldy Marchiano Panjaitan
NPM  Anggota  : 140810250090, 250078, 250063
Tanggal Buat  : 24/09/2026
Deskripsi     : 
*/

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

class Pegawai{
    private static final DateTimeFormatter FORMAT_WAKTU = DateTimeFormatter.ofPattern("HH:mm");

    private String nip;
    private String nama;
    private int golongan;
    private LocalTime waktuDatang;
    private LocalTime waktuPulang;

    public Pegawai(){
        this.nip = "";
        this.nama = "";
        this.golongan = 0;
        this.waktuDatang = null;
        this.waktuPulang = null;
    }

    public Pegawai(String nip, String nama, int golongan){
        this.nip = nip;
        this.nama = nama;
        this.golongan = golongan;
        this.waktuDatang = null;
        this.waktuPulang = null;
    }

    public void setPegawai(String nip, String nama, int golongan){
        this.nip = nip;
        this.nama = nama;
        this.golongan = golongan;
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

    public void setWaktuDatang(String waktu) {
        this.waktuDatang = LocalTime.parse(waktu, FORMAT_WAKTU);
    }

    public void setWaktuPulang(String waktu) {
        this.waktuPulang = LocalTime.parse(waktu, FORMAT_WAKTU);
    }

    public void setWaktuKerja(String datang, String pulang) {
        setWaktuDatang(datang);
        setWaktuPulang(pulang);
    }

    public String getNip() {
        return this.nip;
    }

    public String getNama() {
        return this.nama;
    }

    public int getGolongan() {
        return this.golongan;
    }

    public LocalTime getWaktuDatang() {
        return this.waktuDatang;
    }

    public LocalTime getWaktuPulang() {
        return this.waktuPulang;
    }

    public String getWaktuDatangTeks() {
        return (this.waktuDatang == null) ? "-" : this.waktuDatang.format(FORMAT_WAKTU);
    }

    public String getWaktuPulangTeks() {
        return (this.waktuPulang == null) ? "-" : this.waktuPulang.format(FORMAT_WAKTU);
    }

    public void inputPegawai(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan NIP: ");
        this.nip = scanner.nextLine();
        System.out.print("Masukkan Nama Pegawai: ");
        this.nama = scanner.nextLine();
        System.out.print("Masukkan Golongan (1/2/3/4): ");
        this.golongan = scanner.nextInt();
        scanner.nextLine();
        inputWaktu(scanner);
    }

    public void inputWaktu(Scanner scanner){
        this.waktuDatang = bacaWaktu(scanner, "Masukkan Waktu Datang (HH:mm): ");
        this.waktuPulang = bacaWaktu(scanner, "Masukkan Waktu Pulang (HH:mm): ");
    }

    private LocalTime bacaWaktu(Scanner scanner, String pesan){
        while (true) {
            System.out.print(pesan);
            String teks = scanner.nextLine().trim();
            try {
                return LocalTime.parse(teks, FORMAT_WAKTU);
            } catch (DateTimeParseException e) {
                System.out.println("Format waktu tidak valid. Contoh: 08:00 atau 17:30");
            }
        }
    }

    public Duration hitungLamaKerjaReturn() {
        if (this.waktuDatang == null || this.waktuPulang == null) {
            return Duration.ZERO;
        }
        Duration lama = Duration.between(this.waktuDatang, this.waktuPulang);
        if (lama.isNegative()) {
            lama = lama.plusHours(24);
        }
        return lama;
    }

    public String getLamaKerjaTeks() {
        Duration lama = hitungLamaKerjaReturn();
        return lama.toHours() + " jam " + lama.toMinutesPart() + " menit";
    }

    public void hitungLamaKerjaVoid() {
        if (this.waktuDatang == null || this.waktuPulang == null) {
            System.out.println(" Lama Kerja = - (waktu belum diisi)");
            return;
        }
        Duration lama = Duration.between(this.waktuDatang, this.waktuPulang);
        if (lama.isNegative()) {
            lama = lama.plusHours(24);
        }
        System.out.printf(" Lama Kerja = %d jam %d menit\n", lama.toHours(), lama.toMinutesPart());
    }

    public double cariGapok() {
        switch (this.golongan) {
            case 1: return 1500000;
            case 2: return 2000000;
            case 3: return 3000000;
            case 4: return 5000000;
            default: return 0;
        }
    }

    public double cariTunjangan() {
        switch (this.golongan) {
            case 1: return cariGapok() * 0.10;
            case 2: return cariGapok() * 0.12;
            case 3: return cariGapok() * 0.12;
            case 4: return cariGapok() * 0.15;
            default: return 0;
        }
    }

    public double cariPotongan() {
        switch (this.golongan) {
            case 1: return cariGapok() * 0.01;
            case 2: return cariGapok() * 0.02;
            case 3: return cariGapok() * 0.02;
            case 4: return cariGapok() * 0.04;
            default: return 0;
        }
    }

    public double cariGajiTotal() {
        return cariGapok() + cariTunjangan() - cariPotongan();
    }

    public double hitungGajiReturn() {
        return cariGapok() + cariTunjangan() - cariPotongan();
    }

    public void hitungGajiVoid() {
        double gapok = cariGapok();
        double tunjangan = cariTunjangan();
        double potongan = cariPotongan();
        double total = gapok + tunjangan - potongan;
        System.out.printf(" Gapok      = Rp %,.0f\n", gapok);
        System.out.printf(" Tunjangan  = Rp %,.0f\n", tunjangan);
        System.out.printf(" Potongan   = Rp %,.0f\n", potongan);
        System.out.printf(" Total Gaji = Rp %,.0f\n", total);
    }

    public void cetakWaktuKerja() {
        String datang = (this.waktuDatang == null) ? "-" : this.waktuDatang.format(FORMAT_WAKTU);
        String pulang = (this.waktuPulang == null) ? "-" : this.waktuPulang.format(FORMAT_WAKTU);
        String lama = (this.waktuDatang == null || this.waktuPulang == null) ? "-" : getLamaKerjaTeks();

        System.out.printf(" Waktu Datang (Clock In)  = %s\n", datang);
        System.out.printf(" Waktu Pulang (Clock Out) = %s\n", pulang);
        System.out.printf(" Lama Kerja               = %s\n", lama);
    }

    public void cetakPegawai() {
        System.out.println("-".repeat(112));
        System.out.printf("| %-15s | %-15s | %-3s | %-13s | %-13s | %-13s | %-15s |\n", 
                "NIP", "Nama", "Gol", "Gapok", "Tunjangan", "Potongan", "Total Gaji");
        System.out.println("-".repeat(112));
        System.out.printf("| %-15s | %-15s | %-3d | Rp %,10.0f | Rp %,10.0f | Rp %,10.0f | Rp %,12.0f |\n", 
                this.nip, this.nama, this.golongan, cariGapok(), cariTunjangan(), cariPotongan(), cariGajiTotal());
        System.out.println("-".repeat(112));
        cetakWaktuKerja();
    }
}

public class Nomor2_063_078_090{
    public static void main(String[] args){
        Pegawai obj1 = new Pegawai();
        Pegawai obj2 = new Pegawai("140810250090", "Razan", 3);
        Pegawai obj3 = new Pegawai();

        obj2.setWaktuKerja("08:00", "17:30");

        Pegawai[] daftar = {obj1, obj2, obj3};

        Scanner scanner = new Scanner(System.in);
        int pilihan;

        do{
            System.out.println("\n===== MENU GajiPegawai =====");
            System.out.println("1. Hitung Gaji & Lama Kerja (void)");
            System.out.println("2. Hitung Gaji & Lama Kerja (return)");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan){
                case 1:
                    System.out.println("Hitung (void)");
                    HitungVoid(scanner, daftar);
                    break;
                case 2:
                    System.out.println("Hitung (return)");
                    HitungReturn(scanner, daftar);
                    break;
                case 3:
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }while(pilihan != 3);
    }


    static Pegawai pilihObjek(Scanner scanner, Pegawai[] daftar) {
        System.out.println("1. Obj. 1 (data diset lewat setter)");
        System.out.println("2. Obj. 2 (data diset lewat constructor)");
        System.out.println("3. Obj. 3 (data diinput dari keyboard)");
        System.out.print("Pilih objek (1-3): ");
        int no = scanner.nextInt();
        scanner.nextLine();

        switch (no) {
            case 1:
                daftar[0].setPegawai("140810250078", "Irsyad", 1);
                daftar[0].setWaktuKerja("07:45", "16:15");
                break;
            case 2:
                break;
            case 3:
                daftar[2].inputPegawai();
                break;
            default:
                System.out.println("Objek tidak valid.");
                return null;
        }
        return daftar[no - 1];
    }

    static void HitungVoid(Scanner scanner, Pegawai[] daftar) {
        Pegawai p = pilihObjek(scanner, daftar);
        if (p == null) return;
        System.out.printf(" NIP        = %s\n", p.getNip());
        System.out.printf(" Nama       = %s\n", p.getNama());
        System.out.printf(" Golongan   = %d\n", p.getGolongan());
        System.out.printf(" Clock In   = %s\n", p.getWaktuDatangTeks());
        System.out.printf(" Clock Out  = %s\n", p.getWaktuPulangTeks());
        p.hitungGajiVoid();
        p.hitungLamaKerjaVoid();
    }

    static void HitungReturn(Scanner scanner, Pegawai[] daftar) {
        Pegawai p = pilihObjek(scanner, daftar);
        if (p == null) return;
        double gapok = p.cariGapok();
        double tunjangan = p.cariTunjangan();
        double potongan = p.cariPotongan();
        double total = p.hitungGajiReturn();
        Duration lama = p.hitungLamaKerjaReturn();
        System.out.printf(" NIP        = %s\n", p.getNip());
        System.out.printf(" Nama       = %s\n", p.getNama());
        System.out.printf(" Golongan   = %d\n", p.getGolongan());
        System.out.printf(" Clock In   = %s\n", p.getWaktuDatangTeks());
        System.out.printf(" Clock Out  = %s\n", p.getWaktuPulangTeks());
        System.out.printf(" Gapok      = Rp %,.0f\n", gapok);
        System.out.printf(" Tunjangan  = Rp %,.0f\n", tunjangan);
        System.out.printf(" Potongan   = Rp %,.0f\n", potongan);
        System.out.printf(" Total Gaji = Rp %,.0f\n", total);
        System.out.printf(" Lama Kerja = %d jam %d menit\n", lama.toHours(), lama.toMinutesPart());
    }
}