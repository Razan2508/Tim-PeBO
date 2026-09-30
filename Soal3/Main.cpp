#include <iostream>
#include <iomanip>
#include <cstdlib>
#include <string>
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
    int keDetik() const { return jam * 3600 + menit * 60 + detik; }

public:
    // Constructor
    Waktu() : jam(0), menit(0), detik(0) {}
    Waktu(int j, int m, int d) : jam(0), menit(0), detik(0) { setWaktu(j, m, d); }

    // Setter: menolak nilai di luar rentang
    bool setWaktu(int j, int m, int d) {
        if (j < 0 || j > 23 || m < 0 || m > 59 || d < 0 || d > 59) return false;
        jam = j; menit = m; detik = d;
        return true;
    }

    // Getter
    int getJam() const { return jam; }
    int getMenit() const { return menit; }
    int getDetik() const { return detik; }

    // Input (dalam class): baca di dalam class, isi lewat setter
    void inputDalam() {
        int j, m, d;
        j = bacaAngka("Jam  ", 0, 23);
        m = bacaAngka("Menit", 0, 59);
        d = bacaAngka("Detik", 0, 59);
        setWaktu(j, m, d);
    }

    // Output (dalam class)
    void outputDalam() const {
        cout << setfill('0') << setw(2) << jam << ":"
             << setw(2) << menit << ":" << setw(2) << detik << endl;
    }

    // Proses cara 1: fungsi return
    Waktu selisihReturn(const Waktu &w) const {
        int s = abs(keDetik() - w.keDetik());
        return Waktu(s / 3600, (s % 3600) / 60, s % 60);
    }

    // Proses cara 2: void (hasil disimpan di objek ini)
    void selisihVoid(const Waktu &w1, const Waktu &w2) {
        int s = abs(w1.keDetik() - w2.keDetik());
        setWaktu(s / 3600, (s % 3600) / 60, s % 60);
    }
};

// Input luar class: baca di luar class, isi lewat setter
void inputLuar(Waktu &w) {
    int j, m, d;
    j = bacaAngka("Jam  ", 0, 23);
    m = bacaAngka("Menit", 0, 59);
    d = bacaAngka("Detik", 0, 59);
    w.setWaktu(j, m, d);
}

// Output luar class
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