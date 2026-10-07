/*
Nama Program  : Koordinat
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil , DJEREMY RIELDY MARCHIANO PANJAITAN , MUHAMMAD IRSYAD AZHARUL HAQ
NPM  Anggota  : 140810250090 , 140810250063 , 140810250078
Tanggal Buat  : 26/09/2026
Deskripsi     : Mencari titik tengah , cermin terhadap x , dan cermin terhadap y
*/

import java.util.Scanner;
import java.lang.Math;

class Koordinat {
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

    public double jarakReturn (Koordinat a) {
        double jarak = Math.sqrt((this.absis - a.absis)*(this.absis - a.absis) + (this.ordinat - a.ordinat)*(this.ordinat - a.ordinat));

        return jarak;
    }

    public void jarakVoid (Koordinat a) {
        double jarak = Math.sqrt(((this.absis - a.absis) * (this.absis - a.absis)) + ((this.ordinat - a.ordinat) * (this.ordinat - a.ordinat)));
        
        System.out.println("JARAK ANTARA A DAN B = " + jarak);
    }

    public void cetakVoid (Koordinat a , Koordinat b , Koordinat c , Koordinat d , Koordinat e) {
        System.out.println("HASIL AKHIR : (VOID) (OUTPUT DALAM)" );
        System.out.println("TITIK A = (" + a.getAbsis() +"," + a.getOrdinat() + ")");
        System.out.println("TITIK B = (" + b.getAbsis() +"," + b.getOrdinat() + ")");
        System.out.println("TITIK TENGAH = (" + c.getAbsis() +"," + c.getOrdinat() + ")");
        System.out.println("CERMIN TERHADAP X = (" + d.getAbsis() +"," + d.getOrdinat() + ")");
        System.out.println("CERMIN TERHADAP Y = (" + e.getAbsis() +"," + e.getOrdinat() + ")");
        b.jarakVoid(a);
    }

    public void cetakReturn (Koordinat a , Koordinat b , Koordinat c , Koordinat d , Koordinat e) {
        System.out.println("HASIL AKHIR : (RETURN) (OUTPUT DALAM)" );
        System.out.println("TITIK A = (" + a.getAbsis() +"," + a.getOrdinat() + ")");
        System.out.println("TITIK B = (" + b.getAbsis() +"," + b.getOrdinat() + ")");
        System.out.println("TITIK TENGAH = (" + c.getAbsis() +"," + c.getOrdinat() + ")");
        System.out.println("CERMIN TERHADAP X = (" + d.getAbsis() +"," + d.getOrdinat() + ")");
        System.out.println("CERMIN TERHADAP Y = (" + e.getAbsis() +"," + e.getOrdinat() + ")");
        System.out.println("JARAK ANTARA A DAN B = " + b.jarakReturn(a));
    }
}

class menu {
    public static void jalankan() {
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

                            System.out.println("");
                            System.out.println("HASIL AKHIR : (VOID) (OUTPUT LUAR)" );
                            System.out.println("TITIK A = (" + a1.getAbsis() +"," + a1.getOrdinat() + ")");
                            System.out.println("TITIK B = (" + b1.getAbsis() +"," + b1.getOrdinat() + ")");
                            System.out.println("TITIK TENGAH = (" + tengah1.getAbsis() +"," + tengah1.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP X = (" + cx1.getAbsis() +"," + cx1.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP Y = (" + cy1.getAbsis() +"," + cy1.getOrdinat() + ")");
                            b1.jarakVoid(a1);

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

                            System.out.println("");
                            System.out.println("HASIL AKHIR : (VOID) (OUTPUT LUAR)" );
                            System.out.println("TITIK A = (" + a2.getAbsis() +"," + a2.getOrdinat() + ")");
                            System.out.println("TITIK B = (" + b2.getAbsis() +"," + b2.getOrdinat() + ")");
                            System.out.println("TITIK TENGAH = (" + tengah2.getAbsis() +"," + tengah2.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP X = (" + cx2.getAbsis() +"," + cx2.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP Y = (" + cy2.getAbsis() +"," + cy2.getOrdinat() + ")");
                            b2.jarakVoid(a2);

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

                            System.out.println("");
                            System.out.println("HASIL AKHIR : (VOID) (OUTPUT LUAR)" );
                            System.out.println("TITIK A = (" + a3.getAbsis() +"," + a3.getOrdinat() + ")");
                            System.out.println("TITIK B = (" + b3.getAbsis() +"," + b3.getOrdinat() + ")");
                            System.out.println("TITIK TENGAH = (" + tengah3.getAbsis() +"," + tengah3.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP X = (" + cx3.getAbsis() +"," + cx3.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP Y = (" + cy3.getAbsis() +"," + cy3.getOrdinat() + ")");
                            b3.jarakVoid(a3);

                            break;

                        case 4:
                            subMenuVoidAktif = false;
                            break;
                        default:
                            System.out.println("Pilihan tidak valid!");
                    }
                }

            } else if (pilihanUtama == 2) {
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

                            System.out.println("");
                            System.out.println("HASIL AKHIR : (RETURN) (OUTPUT LUAR)" );
                            System.out.println("TITIK A = (" + a1.getAbsis() +"," + a1.getOrdinat() + ")");
                            System.out.println("TITIK B = (" + b1.getAbsis() +"," + b1.getOrdinat() + ")");
                            System.out.println("TITIK TENGAH = (" + tengah1.getAbsis() +"," + tengah1.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP X = (" + cx1.getAbsis() +"," + cx1.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP Y = (" + cy1.getAbsis() +"," + cy1.getOrdinat() + ")");
                            System.out.println("JARAK ANTARA A DAN B = " + b1.jarakReturn(a1));

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

                            System.out.println("");
                            System.out.println("HASIL AKHIR : (RETURN) (OUTPUT LUAR)" );
                            System.out.println("TITIK A = (" + a2.getAbsis() +"," + a2.getOrdinat() + ")");
                            System.out.println("TITIK B = (" + b2.getAbsis() +"," + b2.getOrdinat() + ")");
                            System.out.println("TITIK TENGAH = (" + tengah2.getAbsis() +"," + tengah2.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP X = (" + cx2.getAbsis() +"," + cx2.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP Y = (" + cy2.getAbsis() +"," + cy2.getOrdinat() + ")");
                            System.out.println("JARAK ANTARA A DAN B = " + b2.jarakReturn(a2));

                            break;

                        case 3:
                            System.out.println("\n[ Menjalankan Objek 3 - Return (Constructor) ]");
                            Koordinat a3 = new Koordinat(4.0, -8.0);
                            Koordinat b3 = new Koordinat(-2.0, 6.0);

                            Koordinat tengah3 = a3.titikTengah(b3);
                            Koordinat cx3 = a3.cerminX();
                            Koordinat cy3 = a3.cerminY();

                            printer.cetakReturn(a3, b3, tengah3, cx3, cy3);

                            System.out.println("");
                            System.out.println("HASIL AKHIR : (RETURN) (OUTPUT LUAR)" );
                            System.out.println("TITIK A = (" + a3.getAbsis() +"," + a3.getOrdinat() + ")");
                            System.out.println("TITIK B = (" + b3.getAbsis() +"," + b3.getOrdinat() + ")");
                            System.out.println("TITIK TENGAH = (" + tengah3.getAbsis() +"," + tengah3.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP X = (" + cx3.getAbsis() +"," + cx3.getOrdinat() + ")");
                            System.out.println("CERMIN TERHADAP Y = (" + cy3.getAbsis() +"," + cy3.getOrdinat() + ")");
                            System.out.println("JARAK ANTARA A DAN B = " + b3.jarakReturn(a3));

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
        scanner.close();
    }
}

public class nomor1_063_078_090 {
    public static void main(String[] args) {
        menu.jalankan();
    }
}