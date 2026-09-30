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

#include <iostream>
#include <iomanip>
#include <sstream>
#include <string>
#include <limits>
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

string trim(const string& teks) {
    size_t awal = teks.find_first_not_of(" \t\r\n");
    if (awal == string::npos) return "";
    size_t akhir = teks.find_last_not_of(" \t\r\n");
    return teks.substr(awal, akhir - awal + 1);
}

bool parseAngka(const string& teks, int& hasil) {
    if (teks.empty() || teks.size() > 9) return false;
    for (char c : teks) {
        if (!isdigit(static_cast<unsigned char>(c))) return false;
    }
    hasil = stoi(teks);
    return true;
}

int bacaInt(const string& pesan) {
    while (true) {
        int nilai;
        if (parseAngka(trim(bacaBaris(pesan)), nilai)) return nilai;
        cout << "Input harus berupa angka.\n";
    }
}

string formatRupiah(long long nilai) {
    string angka = to_string(nilai);
    for (int i = static_cast<int>(angka.size()) - 3; i > 0; i -= 3) {
        angka.insert(i, ".");
    }
    return angka;
}

class Waktu {
private:
    int jam;
    int menit;
    int detik;

public:
    // Constructor
    Waktu() : jam(0), menit(0), detik(0) {}
    Waktu(int jam, int menit, int detik) : jam(jam), menit(menit), detik(detik) {}

    // Membaca teks "HH:mm:ss", mengembalikan false jika format salah
    bool dariTeks(const string& teks) {
        if (teks.size() != 8 || teks[2] != ':' || teks[5] != ':') return false;
        for (int i : {0, 1, 3, 4, 6, 7}) {
            if (!isdigit(static_cast<unsigned char>(teks[i]))) return false;
        }
        int j = stoi(teks.substr(0, 2));
        int m = stoi(teks.substr(3, 2));
        int d = stoi(teks.substr(6, 2));
        if (j > 23 || m > 59 || d > 59) return false;
        jam = j;
        menit = m;
        detik = d;
        return true;
    }

    // Input
    void input(const string& pesan) {
        while (true) {
            if (dariTeks(trim(bacaBaris(pesan)))) return;
            cout << "Format waktu tidak valid. Contoh: 08:00:00 atau 17:15:10\n";
        }
    }

    // Proses
    int keDetik() const {
        return jam * 3600 + menit * 60 + detik;
    }

    static Waktu dariDetik(int total) {
        return Waktu(total / 3600, (total % 3600) / 60, total % 60);
    }

    // Selisih dari waktu ini sampai waktu lain (menangani lewat tengah malam)
    Waktu selisih(const Waktu& lain) const {
        int beda = lain.keDetik() - keDetik();
        if (beda < 0) beda += 24 * 3600;
        return dariDetik(beda);
    }

    // Output
    string tampil() const {
        ostringstream hasil;
        hasil << setfill('0') << setw(2) << jam << ":"
              << setw(2) << menit << ":" << setw(2) << detik;
        return hasil.str();
    }
};

class Pegawai {
private:
    static const int BATAS_DETIK = 8 * 3600;

    string nip;
    string nama;
    int golongan;
    Waktu datang;
    Waktu pulang;
    Waktu lama;
    Waktu jamLembur;
    long long gajiHarian;
    long long lembur;
    long long total;
    string status;

    static long long gapokGolongan(int gol) {
        switch (gol) {
            case 1: return 150000;
            case 2: return 200000;
            case 3: return 400000;
            case 4: return 500000;
            default: return 0;
        }
    }

    static long long tarifLembur(int gol) {
        switch (gol) {
            case 1: return 50000;
            case 2: return 75000;
            case 3: return 150000;
            case 4: return 200000;
            default: return 0;
        }
    }

public:
    // Constructor
    Pegawai() : nip(""), nama(""), golongan(0), gajiHarian(0), lembur(0), total(0), status("-") {}

    Pegawai(const string& nip, const string& nama, int golongan, const Waktu& datang, const Waktu& pulang)
        : nip(nip), nama(nama), golongan(golongan), datang(datang), pulang(pulang),
          gajiHarian(0), lembur(0), total(0) {
        proses();
    }

    // Input
    void input() {
        nip = trim(bacaBaris("Masukkan NIP: "));
        nama = trim(bacaBaris("Masukkan Nama: "));
        golongan = 0;
        while (golongan < 1 || golongan > 4) {
            golongan = bacaInt("Masukkan Golongan (1/2/3/4): ");
            if (golongan < 1 || golongan > 4) cout << "Golongan harus 1, 2, 3, atau 4.\n";
        }
        datang.input("Masukkan Waktu Datang (HH:mm:ss): ");
        pulang.input("Masukkan Waktu Pulang (HH:mm:ss): ");
        proses();
    }

    // Proses
    void proses() {
        lama = datang.selisih(pulang);
        int detikLama = lama.keDetik();
        int kelebihan = detikLama - BATAS_DETIK;

        gajiHarian = gapokGolongan(golongan);
        jamLembur = Waktu();
        lembur = 0;

        if (detikLama < BATAS_DETIK) {
            status = "peringatan";
        } else {
            status = "ok";
            if (kelebihan >= 3600) {
                jamLembur = Waktu::dariDetik(kelebihan);
                long long jamBulat = kelebihan / 3600;  // pembulatan ke bawah
                lembur = jamBulat * tarifLembur(golongan);
            }
        }
        total = gajiHarian + lembur;
    }

    bool sudahDiisi() const {
        return !nip.empty();
    }

    // Output
    static void cetakGaris() {
        cout << string(122, '-') << "\n";
    }

    static void cetakHeader() {
        cout << "\n" << string(45, ' ') << "Daftar Gaji Harian PT Informatika\n";
        cetakGaris();
        cout << left << setw(4) << "No" << setw(7) << "NIP" << setw(15) << "Nama"
             << setw(4) << "Gol" << setw(10) << "Datang" << setw(10) << "Pulang"
             << setw(10) << "Lama" << setw(12) << "Jam Lembur"
             << right << setw(11) << "Gaji Harian" << setw(10) << "Lembur" << setw(10) << "Total"
             << "  " << left << "Status" << "\n";
        cetakGaris();
    }

    void cetakBaris(int no) const {
        cout << left << setw(4) << (to_string(no) + ".") << setw(7) << nip << setw(15) << nama
             << setw(4) << golongan << setw(10) << datang.tampil() << setw(10) << pulang.tampil()
             << setw(10) << lama.tampil() << setw(12) << jamLembur.tampil()
             << right << setw(11) << formatRupiah(gajiHarian) << setw(10) << formatRupiah(lembur)
             << setw(10) << formatRupiah(total)
             << "  " << left << status << "\n";
    }
};

void inputObjek(Pegawai daftar[], int jumlah) {
    int no = bacaInt("Pilih objek yang diinput (1-3): ");
    if (no < 1 || no > jumlah) {
        cout << "Objek tidak valid.\n";
        return;
    }
    daftar[no - 1].input();
    cout << "Data objek " << no << " tersimpan.\n";
}

void tampilkanDaftar(const Pegawai daftar[], int jumlah) {
    Pegawai::cetakHeader();
    int no = 0;
    for (int i = 0; i < jumlah; i++) {
        if (daftar[i].sudahDiisi()) {
            no++;
            daftar[i].cetakBaris(no);
        }
    }
    Pegawai::cetakGaris();
}

int main() {
    Pegawai daftar[3] = {
        Pegawai("001", "Djeremy", 3, Waktu(8, 0, 0), Waktu(17, 15, 10)),
        Pegawai("002", "Irsyad", 1, Waktu(8, 0, 0), Waktu(15, 30, 0)),
        Pegawai()  // diisi lewat keyboard
    };

    int pilihan;
    do {
        cout << "\n===== MENU GAJI HARIAN PT INFORMATIKA =====\n";
        cout << "1. Input data pegawai (objek 1-3)\n";
        cout << "2. Tampilkan daftar gaji harian\n";
        cout << "3. Keluar\n";
        pilihan = bacaInt("Pilih menu: ");

        switch (pilihan) {
            case 1:
                inputObjek(daftar, 3);
                break;
            case 2:
                tampilkanDaftar(daftar, 3);
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