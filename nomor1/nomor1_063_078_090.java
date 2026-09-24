/*
Nama Program  : Koordinat
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil , DJEREMY RIELDY MARCHIANO PANJAITAN , MUHAMMAD IRSYAD AZHARUL HAQ
NPM  Anggota  : 140810250090 , 140810250063 , 140810250078
Tanggal Buat  : 16/09/2026
Deskripsi     : Mnecari titik tengah , cermin terhadap x , dan cermin terhadap y
*/

import java.util.Scanner;

public class nomor1_063_078_090 {
    private double absis; 
    private double ordinat;

    public Koordinat (double absis , double ordinat) {
        this.absis = absis;
        this.ordinat = ordinat;
    }

    public Koordinat () {
        this.absis = 0.0;
        this.ordinat = 0.0;
    }

    public void setAbsis (double absis) {
        this.absis = absis;
    }

    public void setOrdinat (double ordinat) {
        this.ordinat = ordinat;
    }

    public double getAbsis () {
        return this.absis;
    }

    public double getOrdinat () {
        return this.ordinat;
    }

    public void input () {
        Scanner input = new Scanner (System.in);
        System.out.print("Masukkan Nilai Absis = ");
        double absis = input.nextDouble();
        setAbsis(absis);

        System.out.print("Masukkan Nilai Ordinat = ");
        double ordinat = input.nextDouble();
        setOrdinat(ordinat);
    }

    // --- FUNGSI VOID ---

    public void titikTengah (Koordinat a , Koordinat b) {
        this.absis = (a.absis + b.absis) / 2;
        this.ordinat = (a.ordinat + b.ordinat) / 2;
    }

    public void cerminX (Koordinat a) {
        this.absis = a.absis;
        this.ordinat = -a.ordinat;
    }

    public void cerminY (Koordinat a) {
        this.absis = -a.absis;
        this.ordinat = a.ordinat;
    }

    // --- FUNGSI RETURN ---

    public Koordinat titikTengah (Koordinat b) {
        Koordinat temp = new Koordinat(0, 0);
        temp.absis = (this.absis + b.absis) / 2;
        temp.ordinat = (this.ordinat + b.ordinat) / 2;
        return temp;
    }

    public Koordinat cerminX () {
        Koordinat temp = new Koordinat();
        temp.absis = this.absis;
        temp.ordinat = -this.ordinat;
        return temp;
    }

    public Koordinat cerminY () {
        Koordinat temp = new Koordinat();
        temp.absis = -this.absis;
        temp.ordinat = this.ordinat;
        return temp;
    }

    // --- FUNGSI CETAK ---

    public void cetakVoid (Koordinat a , Koordinat b , Koordinat c , Koordinat d , Koordinat e) {
        System.out.println("HASIL AKHIR : (VOID)" );
        System.out.println("TITIK A = (" + a.getAbsis() +"," + a.getOrdinat() + ")");
        System.out.println("TITIK B = (" + b.getAbsis() +"," + b.getOrdinat() + ")");
        System.out.println("TITIK TENGAH = (" + c.getAbsis() +"," + c.getOrdinat() + ")");
        System.out.println("CERMIN TERHADAP X = (" + d.getAbsis() +"," + d.getOrdinat() + ")");
        System.out.println("CERMIN TERHADAP Y = (" + e.getAbsis() +"," + e.getOrdinat() + ")");
    }

    public void cetakReturn (Koordinat a , Koordinat b , Koordinat c , Koordinat d , Koordinat e) {
        System.out.println("HASIL AKHIR : (RETURN)" );
        System.out.println("TITIK A = (" + a.getAbsis() +"," + a.getOrdinat() + ")");
        System.out.println("TITIK B = (" + b.getAbsis() +"," + b.getOrdinat() + ")");
        System.out.println("TITIK TENGAH = (" + c.getAbsis() +"," + c.getOrdinat() + ")");
        System.out.println("CERMIN TERHADAP X = (" + d.getAbsis() +"," + d.getOrdinat() + ")");
        System.out.println("CERMIN TERHADAP Y = (" + e.getAbsis() +"," + e.getOrdinat() + ")");
    }

    // --- MAIN METHOD DENGAN MENU ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Koordinat printer = new Koordinat();
        boolean menuUtamaAktif = true;

        while (menuUtamaAktif) {
            System.out.println("\n=========================================");
            System.out.println("              MENU UTAMA");
            System.out.println("=========================================");
            System.out.println("1. Menguji dengan Fungsi Void");
            System.out.println("2. Menguji dengan Fungsi Return");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu (1-3): ");
            int pilihanUtama = scanner.nextInt();

            if (pilihanUtama == 1) {
                // --- SUB MENU VOID ---
                boolean subMenuVoidAktif = true;
                while (subMenuVoidAktif) {
                    System.out.println("\n--- SUB MENU: UJI VOID ---");
                    System.out.println("1. Objek 1 (Input dari Dalam / Setter)");
                    System.out.println("2. Objek 2 (Input dari Luar / Scanner)");
                    System.out.println("3. Objek 3 (Input dari Constructor)");
                    System.out.println("4. Kembali ke Menu Utama");
                    System.out.print("Pilih objek yang ingin dilihat (1-4): ");
                    int pilihanVoid = scanner.nextInt();

                    switch (pilihanVoid) {
                        case 1:
                            System.out.println("\n[ Menjalankan Objek 1 - Void (Setter) ]");
                            Koordinat a1 = new Koordinat();
                            a1.setAbsis(2.0);
                            a1.setOrdinat(4.0);
                            
                            Koordinat b1 = new Koordinat();
                            b1.setAbsis(6.0);
                            b1.setOrdinat(8.0);

                            Koordinat tengah1 = new Koordinat();
                            tengah1.titikTengah(a1, b1); 
                            Koordinat cx1 = new Koordinat();
                            cx1.cerminX(a1); 
                            Koordinat cy1 = new Koordinat();
                            cy1.cerminY(a1); 

                            printer.cetakVoid(a1, b1, tengah1, cx1, cy1);
                            break;

                        case 2:
                            System.out.println("\n[ Menjalankan Objek 2 - Void (Scanner) ]");
                            Koordinat a2 = new Koordinat();
                            System.out.println("Input Titik A:");
                            a2.input();
                            
                            Koordinat b2 = new Koordinat();
                            System.out.println("Input Titik B:");
                            b2.input();

                            Koordinat tengah2 = new Koordinat();
                            tengah2.titikTengah(a2, b2);
                            Koordinat cx2 = new Koordinat();
                            cx2.cerminX(a2);
                            Koordinat cy2 = new Koordinat();
                            cy2.cerminY(a2);

                            printer.cetakVoid(a2, b2, tengah2, cx2, cy2);
                            break;

                        case 3:
                            System.out.println("\n[ Menjalankan Objek 3 - Void (Constructor) ]");
                            Koordinat a3 = new Koordinat(-3.0, 5.0);
                            Koordinat b3 = new Koordinat(7.0, -1.0);

                            Koordinat tengah3 = new Koordinat();
                            tengah3.titikTengah(a3, b3);
                            Koordinat cx3 = new Koordinat();
                            cx3.cerminX(a3);
                            Koordinat cy3 = new Koordinat();
                            cy3.cerminY(a3);

                            printer.cetakVoid(a3, b3, tengah3, cx3, cy3);
                            break;

                        case 4:
                            subMenuVoidAktif = false;
                            break;
                        default:
                            System.out.println("Pilihan tidak valid!");
                    }
                }

            } else if (pilihanUtama == 2) {
                // --- SUB MENU RETURN ---
                boolean subMenuReturnAktif = true;
                while (subMenuReturnAktif) {
                    System.out.println("\n--- SUB MENU: UJI RETURN ---");
                    System.out.println("1. Objek 1 (Input dari Dalam / Setter)");
                    System.out.println("2. Objek 2 (Input dari Luar / Scanner)");
                    System.out.println("3. Objek 3 (Input dari Constructor)");
                    System.out.println("4. Kembali ke Menu Utama");
                    System.out.print("Pilih objek yang ingin dilihat (1-4): ");
                    int pilihanReturn = scanner.nextInt();

                    switch (pilihanReturn) {
                        case 1:
                            System.out.println("\n[ Menjalankan Objek 1 - Return (Setter) ]");
                            Koordinat a1 = new Koordinat();
                            a1.setAbsis(10.0);
                            a1.setOrdinat(12.0);
                            
                            Koordinat b1 = new Koordinat();
                            b1.setAbsis(20.0);
                            b1.setOrdinat(24.0);

                            Koordinat tengah1 = a1.titikTengah(b1);
                            Koordinat cx1 = a1.cerminX();
                            Koordinat cy1 = a1.cerminY();

                            printer.cetakReturn(a1, b1, tengah1, cx1, cy1);
                            break;

                        case 2:
                            System.out.println("\n[ Menjalankan Objek 2 - Return (Scanner) ]");
                            Koordinat a2 = new Koordinat();
                            System.out.println("Input Titik A:");
                            a2.input();
                            
                            Koordinat b2 = new Koordinat();
                            System.out.println("Input Titik B:");
                            b2.input();

                            Koordinat tengah2 = a2.titikTengah(b2);
                            Koordinat cx2 = a2.cerminX();
                            Koordinat cy2 = a2.cerminY();

                            printer.cetakReturn(a2, b2, tengah2, cx2, cy2);
                            break;

                        case 3:
                            System.out.println("\n[ Menjalankan Objek 3 - Return (Constructor) ]");
                            Koordinat a3 = new Koordinat(4.0, -8.0);
                            Koordinat b3 = new Koordinat(-2.0, 6.0);

                            Koordinat tengah3 = a3.titikTengah(b3);
                            Koordinat cx3 = a3.cerminX();
                            Koordinat cy3 = a3.cerminY();

                            printer.cetakReturn(a3, b3, tengah3, cx3, cy3);
                            break;

                        case 4:
                            subMenuReturnAktif = false;
                            break;
                        default:
                            System.out.println("Pilihan tidak valid!");
                    }
                }

            } else if (pilihanUtama == 3) {
                System.out.println("Keluar dari program. Terima kasih!");
                menuUtamaAktif = false;
            } else {
                System.out.println("Pilihan tidak valid! Silakan pilih 1, 2, atau 3.");
            }
        }
    }
}