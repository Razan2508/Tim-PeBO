/*
Nama Program  : Selisih Waktu
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil , DJEREMY RIELDY MARCHIANO PANJAITAN , MUHAMMAD IRSYAD AZHARUL HAQ
NPM  Anggota  : 140810250090 , 140810250063 , 140810250078
Tanggal Buat  : 16/09/2026
Deskripsi     : Mencari selisih waktu berdasarkan input jam , menit , detik
*/

#include <iostream>
#include <iomanip>
#include <cstdlib>
#include <string>
#include <cmath>
using namespace std;

// Baca angka dengan validasi rentang (diulang sampai valid)
int bacaAngka(const string &label, int lo, int hi) {
    int n;
    while (true) {
        cout << label << " (" << lo << "-" << hi << ") : ";
        if (cin >> n && n >= lo && n <= hi) return n;
        if (cin.eof()) exit(0);
        if (cin.fail()) { cin.clear(); cin.ignore(10000, '\n'); }
        cout << "Input tidak valid! Masukkan angka " << lo << " sampai " << hi << "." << endl;
    }
}

class Waktu {
private:
    int jam, menit, detik;
    
    int keDetik() const { 
        return this->jam * 3600 + this->menit * 60 + this->detik; 
    }

public:
    Waktu() {
        this->jam = 0;
        this->menit = 0;
        this->detik = 0;
    }
    
    Waktu(int jam, int menit, int detik) { 
        this->jam = jam;
        this->menit = menit;
        this->detik = detik;
    }

    void setJam(int jam) {
        this->jam = jam;
    }
    
    void setMenit(int menit) {
        this->menit = menit;
    }
    
    void setDetik(int detik) {
        this->detik = detik;
    }

    int getJam() const { 
        return this->jam; 
    }
    int getMenit() const { 
        return this->menit; 
    }
    int getDetik() const { return this->detik; }

    void inputDalam() {
        int jam, menit, detik;
        jam = bacaAngka("Jam  ", 0, 23);
        menit = bacaAngka("Menit", 0, 59);
        detik = bacaAngka("Detik", 0, 59);
        
        this->setJam(jam);
        this->setMenit(menit);
        this->setDetik(detik);
    }

    void outputDalam() const {
        cout << setfill('0') << setw(2) << this->jam << ":"
             << setw(2) << this->menit << ":" << setw(2) << this->detik << endl;
    }

    Waktu selisihReturn(const Waktu &w) const {
        int s = abs(this->keDetik() - w.keDetik());
        return Waktu(s / 3600, (s % 3600) / 60, s % 60);
    }

    void selisihVoid(const Waktu &w1, const Waktu &w2) {
        int s = abs(w1.keDetik() - w2.keDetik());
        this->setJam(s / 3600);
        this->setMenit((s % 3600) / 60);
        this->setDetik(s % 60);
    }
};

void inputLuar(Waktu &w) {
    int jam, menit, detik;
    jam = bacaAngka("Jam  ", 0, 23);
    menit = bacaAngka("Menit", 0, 59);
    detik = bacaAngka("Detik", 0, 59);
    
    w.setJam(jam);
    w.setMenit(menit);
    w.setDetik(detik);
}

void outputLuar(const Waktu &w) {
    cout << setfill('0') << setw(2) << w.getJam() << ":"
         << setw(2) << w.getMenit() << ":" << setw(2) << w.getDetik() << endl;
}

void uji(bool modeVoid, int objek) {
    Waktu w1, w2, hasil;

    switch (objek) {
        case 1: // Input dari Dalam / Setter
            cout << "\n[Objek 1 - Input dari Dalam / Setter]" << endl;
            cout << "Waktu 1:" << endl; w1.inputDalam();
            cout << "Waktu 2:" << endl; w2.inputDalam();
            break;
        case 2: // Input dari Luar / Scanner (cin)
            cout << "\n[Objek 2 - Input dari Luar / Scanner]" << endl;
            cout << "Waktu 1:" << endl; inputLuar(w1);
            cout << "Waktu 2:" << endl; inputLuar(w2);
            break;
        case 3: // Input dari Constructor
            cout << "\n[Objek 3 - Input dari Constructor]" << endl;
            w1 = Waktu(8, 30, 15);
            w2 = Waktu(10, 45, 50);
            break;
    }

    if (modeVoid) hasil.selisihVoid(w1, w2);
    else hasil = w1.selisihReturn(w2);

    cout << "\nWaktu 1                      : "; w1.outputDalam();
    cout << "Waktu 2                      : "; w2.outputDalam();
    cout << "Selisih (output dalam class) : "; hasil.outputDalam();
    cout << "Selisih (output luar class)  : "; outputLuar(hasil);
}

void subMenu(bool modeVoid) {
    int p;
    do {
        cout << "\n--- SUB MENU: UJI " << (modeVoid ? "VOID" : "RETURN") << " ---" << endl;
        cout << "1. Objek 1 (Input dari Dalam / Setter)" << endl;
        cout << "2. Objek 2 (Input dari Luar / Scanner)" << endl;
        cout << "3. Objek 3 (Input dari Constructor)" << endl;
        cout << "4. Kembali ke Menu Utama" << endl;
        cout << "Pilih objek (1-4): "; cin >> p;
        if (p >= 1 && p <= 3) uji(modeVoid, p);
    } while (p != 4);
}

int main() {
    int p;
    do {
        cout << "\n==========================================" << endl;
        cout << "               MENU UTAMA" << endl;
        cout << "==========================================" << endl;
        cout << "1. Menguji dengan Fungsi Void" << endl;
        cout << "2. Menguji dengan Fungsi Return" << endl;
        cout << "3. Keluar" << endl;
        cout << "Pilih menu (1-3): "; cin >> p;
        if (p == 1) subMenu(true);
        else if (p == 2) subMenu(false);
    } while (p != 3);
    return 0;
}