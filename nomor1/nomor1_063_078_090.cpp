/*
Nama Program  : Koordinat
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil , DJEREMY RIELDY MARCHIANO PANJAITAN , MUHAMMAD IRSYAD AZHARUL HAQ
NPM  Anggota  : 140810250090 , 140810250063 , 140810250078
Tanggal Buat  : 16/09/2026
Deskripsi     : Mnecari titik tengah , cermin terhadap x , dan cermin terhadap y
*/

#include <iostream>

using namespace std;

class Koordinat {
private:
    double absis;
    double ordinat;

public:
    // Constructor dengan parameter
    Koordinat(double absis, double ordinat) {
        this->absis = absis;
        this->ordinat = ordinat;
    }

    // Default Constructor
    Koordinat() {
        this->absis = 0.0;
        this->ordinat = 0.0;
    }

    void setAbsis(double absis) {
        this->absis = absis;
    }

    void setOrdinat(double ordinat) {
        this->ordinat = ordinat;
    }

    double getAbsis() {
        return this->absis;
    }

    double getOrdinat() {
        return this->ordinat;
    }

    void input() {
        cout << "Masukkan Nilai Absis = ";
        cin >> absis;
        setAbsis(absis);

        cout << "Masukkan Nilai Ordinat = ";
        cin >> ordinat;
        setOrdinat(ordinat);
    }

    // --- FUNGSI VOID ---
    void titikTengah(Koordinat a, Koordinat b) {
        this->absis = (a.absis + b.absis) / 2;
        this->ordinat = (a.ordinat + b.ordinat) / 2;
    }

    void cerminX(Koordinat a) {
        this->absis = a.absis;
        this->ordinat = -a.ordinat;
    }

    void cerminY(Koordinat a) {
        this->absis = -a.absis;
        this->ordinat = a.ordinat;
    }

    // --- FUNGSI RETURN ---
    Koordinat titikTengah(Koordinat b) {
        Koordinat temp(0, 0);
        temp.absis = (this->absis + b.absis) / 2;
        temp.ordinat = (this->ordinat + b.ordinat) / 2;
        return temp;
    }

    Koordinat cerminX() {
        Koordinat temp;
        temp.absis = this->absis;
        temp.ordinat = -this->ordinat;
        return temp;
    }

    Koordinat cerminY() {
        Koordinat temp;
        temp.absis = -this->absis;
        temp.ordinat = this->ordinat;
        return temp;
    }

    // --- FUNGSI CETAK ---
    void cetakVoid(Koordinat a, Koordinat b, Koordinat c, Koordinat d, Koordinat e) {
        cout << "HASIL AKHIR : (VOID)\n";
        cout << "TITIK A = (" << a.getAbsis() << "," << a.getOrdinat() << ")\n";
        cout << "TITIK B = (" << b.getAbsis() << "," << b.getOrdinat() << ")\n";
        cout << "TITIK TENGAH = (" << c.getAbsis() << "," << c.getOrdinat() << ")\n";
        cout << "CERMIN TERHADAP X = (" << d.getAbsis() << "," << d.getOrdinat() << ")\n";
        cout << "CERMIN TERHADAP Y = (" << e.getAbsis() << "," << e.getOrdinat() << ")\n";
    }

    void cetakReturn(Koordinat a, Koordinat b, Koordinat c, Koordinat d, Koordinat e) {
        cout << "HASIL AKHIR : (RETURN)\n";
        cout << "TITIK A = (" << a.getAbsis() << "," << a.getOrdinat() << ")\n";
        cout << "TITIK B = (" << b.getAbsis() << "," << b.getOrdinat() << ")\n";
        cout << "TITIK TENGAH = (" << c.getAbsis() << "," << c.getOrdinat() << ")\n";
        cout << "CERMIN TERHADAP X = (" << d.getAbsis() << "," << d.getOrdinat() << ")\n";
        cout << "CERMIN TERHADAP Y = (" << e.getAbsis() << "," << e.getOrdinat() << ")\n";
    }
};

int main() {
    Koordinat printer;
    bool menuUtamaAktif = true;
    int pilihanUtama;

    while (menuUtamaAktif) {
        cout << "\n=========================================\n";
        cout << "              MENU UTAMA\n";
        cout << "=========================================\n";
        cout << "1. Menguji dengan Fungsi Void\n";
        cout << "2. Menguji dengan Fungsi Return\n";
        cout << "3. Keluar\n";
        cout << "Pilih menu (1-3): ";
        cin >> pilihanUtama;

        if (pilihanUtama == 1) {
            bool subMenuVoidAktif = true;
            while (subMenuVoidAktif) {
                cout << "\n--- SUB MENU: UJI VOID ---\n";
                cout << "1. Objek 1 (Input dari Dalam / Setter)\n";
                cout << "2. Objek 2 (Input dari Luar / Scanner)\n";
                cout << "3. Objek 3 (Input dari Constructor)\n";
                cout << "4. Kembali ke Menu Utama\n";
                cout << "Pilih objek yang ingin dilihat (1-4): ";
                int pilihanVoid;
                cin >> pilihanVoid;

                switch (pilihanVoid) {
                    case 1: {
                        cout << "\n[ Menjalankan Objek 1 - Void (Setter) ]\n";
                        Koordinat a1, b1, tengah1, cx1, cy1;
                        a1.setAbsis(2.0); a1.setOrdinat(4.0);
                        b1.setAbsis(6.0); b1.setOrdinat(8.0);

                        tengah1.titikTengah(a1, b1); 
                        cx1.cerminX(a1); 
                        cy1.cerminY(a1); 
                        printer.cetakVoid(a1, b1, tengah1, cx1, cy1);
                        break;
                    }
                    case 2: {
                        cout << "\n[ Menjalankan Objek 2 - Void (Scanner) ]\n";
                        Koordinat a2, b2, tengah2, cx2, cy2;
                        cout << "Input Titik A:\n";
                        a2.input();
                        cout << "Input Titik B:\n";
                        b2.input();

                        tengah2.titikTengah(a2, b2);
                        cx2.cerminX(a2);
                        cy2.cerminY(a2);
                        printer.cetakVoid(a2, b2, tengah2, cx2, cy2);
                        break;
                    }
                    case 3: {
                        cout << "\n[ Menjalankan Objek 3 - Void (Constructor) ]\n";
                        Koordinat a3(-3.0, 5.0);
                        Koordinat b3(7.0, -1.0);
                        Koordinat tengah3, cx3, cy3;

                        tengah3.titikTengah(a3, b3);
                        cx3.cerminX(a3);
                        cy3.cerminY(a3);
                        printer.cetakVoid(a3, b3, tengah3, cx3, cy3);
                        break;
                    }
                    case 4:
                        subMenuVoidAktif = false;
                        break;
                    default:
                        cout << "Pilihan tidak valid!\n";
                }
            }
        } else if (pilihanUtama == 2) {
            bool subMenuReturnAktif = true;
            while (subMenuReturnAktif) {
                cout << "\n--- SUB MENU: UJI RETURN ---\n";
                cout << "1. Objek 1 (Input dari Dalam / Setter)\n";
                cout << "2. Objek 2 (Input dari Luar / Scanner)\n";
                cout << "3. Objek 3 (Input dari Constructor)\n";
                cout << "4. Kembali ke Menu Utama\n";
                cout << "Pilih objek yang ingin dilihat (1-4): ";
                int pilihanReturn;
                cin >> pilihanReturn;

                switch (pilihanReturn) {
                    case 1: {
                        cout << "\n[ Menjalankan Objek 1 - Return (Setter) ]\n";
                        Koordinat a1, b1;
                        a1.setAbsis(10.0); a1.setOrdinat(12.0);
                        b1.setAbsis(20.0); b1.setOrdinat(24.0);

                        Koordinat tengah1 = a1.titikTengah(b1);
                        Koordinat cx1 = a1.cerminX();
                        Koordinat cy1 = a1.cerminY();
                        printer.cetakReturn(a1, b1, tengah1, cx1, cy1);
                        break;
                    }
                    case 2: {
                        cout << "\n[ Menjalankan Objek 2 - Return (Scanner) ]\n";
                        Koordinat a2, b2;
                        cout << "Input Titik A:\n";
                        a2.input();
                        cout << "Input Titik B:\n";
                        b2.input();

                        Koordinat tengah2 = a2.titikTengah(b2);
                        Koordinat cx2 = a2.cerminX();
                        Koordinat cy2 = a2.cerminY();
                        printer.cetakReturn(a2, b2, tengah2, cx2, cy2);
                        break;
                    }
                    case 3: {
                        cout << "\n[ Menjalankan Objek 3 - Return (Constructor) ]\n";
                        Koordinat a3(4.0, -8.0);
                        Koordinat b3(-2.0, 6.0);

                        Koordinat tengah3 = a3.titikTengah(b3);
                        Koordinat cx3 = a3.cerminX();
                        Koordinat cy3 = a3.cerminY();
                        printer.cetakReturn(a3, b3, tengah3, cx3, cy3);
                        break;
                    }
                    case 4:
                        subMenuReturnAktif = false;
                        break;
                    default:
                        cout << "Pilihan tidak valid!\n";
                }
            }
        } else if (pilihanUtama == 3) {
            cout << "Keluar dari program. Terima kasih!\n";
            menuUtamaAktif = false;
        } else {
            cout << "Pilihan tidak valid! Silakan pilih 1, 2, atau 3.\n";
        }
    }
    return 0;
}