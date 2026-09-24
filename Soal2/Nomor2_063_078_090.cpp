/*
Nama Program  : 
Nama Kelompok : Tim PeBO 
Nama Anggota  : Razan Ibrahim Nabil
NPM  Anggota  : 140810250090
Tanggal Buat  : 16/09/2026
Deskripsi     : 
*/

#include <iostream>
#include <iomanip>
#include <sstream>
#include <string>
#include <optional>
#include <stdexcept>
#include <limits>
#include <cmath>
#include <cctype>
#include <cstdlib>
using namespace std;

string bacaBaris(const string& pesan) {
    string teks;
    cout << pesan;
    if (!getline(cin, teks)) {
        cout << "\nInput berakhir. Program dihentikan.\n";
        exit(0);
    }
    return teks;
}

int bacaInt(const string& pesan) {
    int nilai;
    while (true) {
        cout << pesan;
        if (cin >> nilai) {
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            return nilai;
        }
        if (cin.eof()) {
            cout << "\nInput berakhir. Program dihentikan.\n";
            exit(0);
        }
        cout << "Input harus berupa angka.\n";
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
    }
}

string trim(const string& teks) {
    size_t awal = teks.find_first_not_of(" \t\r\n");
    if (awal == string::npos) return "";
    size_t akhir = teks.find_last_not_of(" \t\r\n");
    return teks.substr(awal, akhir - awal + 1);
}

bool parseWaktu(const string& teks, int& hasilMenit) {
    if (teks.size() != 5 || teks[2] != ':') return false;
    for (int i : {0, 1, 3, 4}) {
        if (!isdigit(static_cast<unsigned char>(teks[i]))) return false;
    }
    int jam = stoi(teks.substr(0, 2));
    int menit = stoi(teks.substr(3, 2));
    if (jam > 23 || menit > 59) return false;
    hasilMenit = jam * 60 + menit;
    return true;
}

string formatWaktu(int totalMenit) {
    ostringstream hasil;
    hasil << setfill('0') << setw(2) << totalMenit / 60 << ":"
          << setw(2) << totalMenit % 60;
    return hasil.str();
}

string formatRupiah(double nilai) {
    string angka = to_string(llround(nilai));
    int batas = (angka[0] == '-') ? 1 : 0;
    for (int i = static_cast<int>(angka.size()) - 3; i > batas; i -= 3) {
        angka.insert(i, ".");
    }
    return angka;
}

int bacaWaktu(const string& pesan) {
    while (true) {
        string teks = trim(bacaBaris(pesan));
        int menit;
        if (parseWaktu(teks, menit)) return menit;
        cout << "Format waktu tidak valid. Contoh: 08:00 atau 17:30\n";
    }
}


class Pegawai {
private:
    string nip;
    string nama;
    int golongan;
    optional<int> waktuDatang;   
    optional<int> waktuPulang;   

public:
    Pegawai() : nip(""), nama(""), golongan(0) {}

    Pegawai(const string& nip, const string& nama, int golongan)
        : nip(nip), nama(nama), golongan(golongan) {}

    // ---------- Setter ----------
    void setPegawai(const string& nip, const string& nama, int golongan) {
        this->nip = nip;
        this->nama = nama;
        this->golongan = golongan;
    }

    void setNip(const string& nip) {
        this->nip = nip;
    }

    void setNama(const string& nama) {
        this->nama = nama;
    }

    void setGolongan(int golongan) {
        this->golongan = golongan;
    }

    void setWaktuDatang(const string& waktu) {
        int menit;
        if (!parseWaktu(waktu, menit)) {
            throw invalid_argument("Format waktu tidak valid: " + waktu);
        }
        waktuDatang = menit;
    }

    void setWaktuPulang(const string& waktu) {
        int menit;
        if (!parseWaktu(waktu, menit)) {
            throw invalid_argument("Format waktu tidak valid: " + waktu);
        }
        waktuPulang = menit;
    }

    void setWaktuKerja(const string& datang, const string& pulang) {
        setWaktuDatang(datang);
        setWaktuPulang(pulang);
    }

    // ---------- Getter (const = tidak mengubah isi objek) ----------
    string getNip() const {
        return nip;
    }

    string getNama() const {
        return nama;
    }

    int getGolongan() const {
        return golongan;
    }

    optional<int> getWaktuDatang() const {
        return waktuDatang;
    }

    optional<int> getWaktuPulang() const {
        return waktuPulang;
    }

    string getWaktuDatangTeks() const {
        return waktuDatang.has_value() ? formatWaktu(*waktuDatang) : "-";
    }

    string getWaktuPulangTeks() const {
        return waktuPulang.has_value() ? formatWaktu(*waktuPulang) : "-";
    }

    // ---------- Input ----------
    void inputWaktu() {
        waktuDatang = bacaWaktu("Masukkan Waktu Datang (HH:mm): ");
        waktuPulang = bacaWaktu("Masukkan Waktu Pulang (HH:mm): ");
    }

    void inputPegawai() {
        nip = bacaBaris("Masukkan NIP: ");
        nama = bacaBaris("Masukkan Nama Pegawai: ");
        golongan = bacaInt("Masukkan Golongan (1/2/3/4): ");
        inputWaktu();
    }

    int hitungLamaKerjaReturn() const {
        if (!waktuDatang.has_value() || !waktuPulang.has_value()) {
            return 0;
        }
        int lama = *waktuPulang - *waktuDatang;
        if (lama < 0) {
            lama += 24 * 60;
        }
        return lama;
    }

    string getLamaKerjaTeks() const {
        int lama = hitungLamaKerjaReturn();
        return to_string(lama / 60) + " jam " + to_string(lama % 60) + " menit";
    }

    void hitungLamaKerjaVoid() const {
        if (!waktuDatang.has_value() || !waktuPulang.has_value()) {
            cout << " Lama Kerja = - (waktu belum diisi)\n";
            return;
        }
        int lama = *waktuPulang - *waktuDatang;
        if (lama < 0) {
            lama += 24 * 60;
        }
        cout << " Lama Kerja = " << lama / 60 << " jam " << lama % 60 << " menit\n";
    }

    // ---------- Gaji ----------
    double cariGapok() const {
        switch (golongan) {
            case 1: return 1500000;
            case 2: return 2000000;
            case 3: return 3000000;
            case 4: return 5000000;
            default: return 0;
        }
    }

    double cariTunjangan() const {
        switch (golongan) {
            case 1: return cariGapok() * 0.10;
            case 2: return cariGapok() * 0.12;
            case 3: return cariGapok() * 0.12;
            case 4: return cariGapok() * 0.15;
            default: return 0;
        }
    }

    double cariPotongan() const {
        switch (golongan) {
            case 1: return cariGapok() * 0.01;
            case 2: return cariGapok() * 0.02;
            case 3: return cariGapok() * 0.02;
            case 4: return cariGapok() * 0.04;
            default: return 0;
        }
    }

    double cariGajiTotal() const {
        return cariGapok() + cariTunjangan() - cariPotongan();
    }

    double hitungGajiReturn() const {
        return cariGapok() + cariTunjangan() - cariPotongan();
    }

    void hitungGajiVoid() const {
        double gapok = cariGapok();
        double tunjangan = cariTunjangan();
        double potongan = cariPotongan();
        double total = gapok + tunjangan - potongan;
        cout << " Gapok      = Rp " << formatRupiah(gapok) << "\n";
        cout << " Tunjangan  = Rp " << formatRupiah(tunjangan) << "\n";
        cout << " Potongan   = Rp " << formatRupiah(potongan) << "\n";
        cout << " Total Gaji = Rp " << formatRupiah(total) << "\n";
    }

    void cetakWaktuKerja() const {
        string datang = waktuDatang.has_value() ? formatWaktu(*waktuDatang) : "-";
        string pulang = waktuPulang.has_value() ? formatWaktu(*waktuPulang) : "-";
        string lama = (waktuDatang.has_value() && waktuPulang.has_value()) ? getLamaKerjaTeks() : "-";

        cout << " Waktu Datang (Clock In)  = " << datang << "\n";
        cout << " Waktu Pulang (Clock Out) = " << pulang << "\n";
        cout << " Lama Kerja               = " << lama << "\n";
    }

    void cetakPegawai() const {
        cout << string(112, '-') << "\n";
        cout << left
             << "| " << setw(15) << "NIP"
             << " | " << setw(15) << "Nama"
             << " | " << setw(3) << "Gol"
             << " | " << setw(13) << "Gapok"
             << " | " << setw(13) << "Tunjangan"
             << " | " << setw(13) << "Potongan"
             << " | " << setw(15) << "Total Gaji" << " |\n";
        cout << string(112, '-') << "\n";
        cout << left
             << "| " << setw(15) << nip
             << " | " << setw(15) << nama
             << " | " << setw(3) << golongan
             << right
             << " | Rp " << setw(10) << formatRupiah(cariGapok())
             << " | Rp " << setw(10) << formatRupiah(cariTunjangan())
             << " | Rp " << setw(10) << formatRupiah(cariPotongan())
             << " | Rp " << setw(12) << formatRupiah(cariGajiTotal()) << " |\n";
        cout << left;
        cout << string(112, '-') << "\n";
        cetakWaktuKerja();
    }
};

Pegawai* pilihObjek(Pegawai daftar[]) {
    cout << "1. Obj. 1 (data diset lewat setter)\n";
    cout << "2. Obj. 2 (data diset lewat constructor)\n";
    cout << "3. Obj. 3 (data diinput dari keyboard)\n";
    int no = bacaInt("Pilih objek (1-3): ");

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
            cout << "Objek tidak valid.\n";
            return nullptr;
    }
    return &daftar[no - 1];
}

void HitungVoid(Pegawai daftar[]) {
    Pegawai* p = pilihObjek(daftar);
    if (p == nullptr) return;
    cout << " NIP        = " << p->getNip() << "\n";
    cout << " Nama       = " << p->getNama() << "\n";
    cout << " Golongan   = " << p->getGolongan() << "\n";
    cout << " Clock In   = " << p->getWaktuDatangTeks() << "\n";
    cout << " Clock Out  = " << p->getWaktuPulangTeks() << "\n";
    p->hitungGajiVoid();
    p->hitungLamaKerjaVoid();
}

void HitungReturn(Pegawai daftar[]) {
    Pegawai* p = pilihObjek(daftar);
    if (p == nullptr) return;
    double gapok = p->cariGapok();
    double tunjangan = p->cariTunjangan();
    double potongan = p->cariPotongan();
    double total = p->hitungGajiReturn();
    int lama = p->hitungLamaKerjaReturn();  
    cout << " Nama       = " << p->getNama() << "\n";
    cout << " Golongan   = " << p->getGolongan() << "\n";
    cout << " Clock In   = " << p->getWaktuDatangTeks() << "\n";
    cout << " Clock Out  = " << p->getWaktuPulangTeks() << "\n";
    cout << " Gapok      = Rp " << formatRupiah(gapok) << "\n";
    cout << " Tunjangan  = Rp " << formatRupiah(tunjangan) << "\n";
    cout << " Potongan   = Rp " << formatRupiah(potongan) << "\n";
    cout << " Total Gaji = Rp " << formatRupiah(total) << "\n";
    cout << " Lama Kerja = " << lama / 60 << " jam " << lama % 60 << " menit\n";
}

int main() {
    Pegawai daftar[3] = {
        Pegawai(),
        Pegawai("140810250090", "Razan", 3),
        Pegawai()
    };

    daftar[1].setWaktuKerja("08:00", "17:30");

    int pilihan;

    do {
        cout << "\n===== MENU GajiPegawai =====\n";
        cout << "1. Hitung Gaji & Lama Kerja (void)\n";
        cout << "2. Hitung Gaji & Lama Kerja (return)\n";
        cout << "3. Keluar\n";
        pilihan = bacaInt("Pilih menu: ");

        switch (pilihan) {
            case 1:
                cout << "Hitung (void)\n";
                HitungVoid(daftar);
                break;
            case 2:
                cout << "Hitung (return)\n";
                HitungReturn(daftar);
                break;
            case 3:
                cout << "Keluar dari program.\n";
                break;
            default:
                cout << "Pilihan tidak valid.\n";
        }
    } while (pilihan != 3);

    return 0;
}