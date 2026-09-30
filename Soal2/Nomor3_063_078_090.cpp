/*
Nama Program  : Gaji Harian dan Lembur Pegawai (Full OOP)
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil, Muhammah Irsyad Azzarul Haq, Djeremy Rieldy Marchiano Panjaitan
NPM  Anggota  : 140810250090, 250078, 250063
Tanggal Buat  : 24/09/2026
Deskripsi     : Menghitung gaji harian + lembur dengan metode 3 Input (Dalam, Constructor, Luar/Setter).
*/

#include <iostream>
#include <iomanip>
#include <string>

using namespace std;

// --- FUNGSI BANTUAN FORMAT UANG ---
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
        return jam; 
    }
    int getMenit() const { 
        return menit; 
    }
    int getDetik() const { 
        return detik; 
    }

    void inputDalam(const string& pesan) {
        int jam, menit, detik;
        cout << pesan << "\n";
        cout << "  Jam (0-23)   : "; cin >> jam;
        cout << "  Menit (0-59) : "; cin >> menit;
        cout << "  Detik (0-59) : "; cin >> detik;
        cin.ignore();
        
        this->setJam(jam); 
        this->setMenit(menit); 
        this->setDetik(detik);
    }

    int keDetik() const { 
        return jam * 3600 + menit * 60 + detik; 
    }

    static Waktu dariDetik(int total) {
        return Waktu(total / 3600, (total % 3600) / 60, total % 60);
    }

    Waktu selisih(const Waktu& lain) const {
        int beda = lain.keDetik() - this->keDetik();
        if (beda < 0) beda += 24 * 3600;
        return dariDetik(beda);
    }

    string tampil() const {
        string str_jam = (jam < 10 ? "0" : "") + to_string(jam);
        string str_menit = (menit < 10 ? "0" : "") + to_string(menit);
        string str_detik = (detik < 10 ? "0" : "") + to_string(detik);
        return str_jam + ":" + str_menit + ":" + str_detik;
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
    
    // Atribut hasil proses
    Waktu lama;
    Waktu jamLembur;
    long long gajiHarian;
    long long lembur;
    long long total;
    string status;

    long long gapokGolongan(int gol) {
        switch (gol) {
            case 1: return 150000;
            case 2: return 200000;
            case 3: return 400000;
            case 4: return 500000;
            default: return 0;
        }
    }

    long long tarifLembur(int gol) {
        switch (gol) {
            case 1: return 50000;
            case 2: return 75000;
            case 3: return 150000;
            case 4: return 200000;
            default: return 0;
        }
    }

public:
    Pegawai() {
        this->nip = "";
        this->nama = "";
        this->golongan = 0;
        this->gajiHarian = 0;
        this->lembur = 0;
        this->total = 0;
        this->status = "-";
    }

    Pegawai(string nip, string nama, int golongan, Waktu datang, Waktu pulang) {
        this->setNip(nip);
        this->setNama(nama);
        this->setGolongan(golongan);
        this->setDatang(datang);
        this->setPulang(pulang);
        this->proses(); 
    }

    void setNip(string nip) { 
        this->nip = nip; 
    }
    void setNama(string nama) { 
        this->nama = nama; 
    }
    void setGolongan(int golongan) { 
        this->golongan = golongan; 
    }
    void setDatang(Waktu datang) { 
        this->datang = datang; 
    }
    void setPulang(Waktu pulang) { 
        this->pulang = pulang; 
    }

    string getNip() const { 
        return nip; 
    }
    string getNama() const { 
        return nama; 
    }
    int getGolongan() const { 
        return golongan; 
    }

    bool sudahDiisi() const { 
        return !nip.empty(); 
    }

    void inputDalam() {
        string nip, nama;
        int golongan;
        Waktu datang, pulang;

        cout << "Masukkan NIP: ";
        getline(cin, nip);
        this->setNip(nip); 
        
        cout << "Masukkan Nama: ";
        getline(cin, nama);
        this->setNama(nama); 
        
        cout << "Masukkan Golongan (1/2/3/4): ";
        cin >> golongan;
        cin.ignore();
        this->setGolongan(golongan); 
        
        datang.inputDalam("Masukkan Waktu Datang:");
        this->setDatang(datang);
        
        pulang.inputDalam("Masukkan Waktu Pulang:");
        this->setPulang(pulang);
        
        this->proses(); 
    }

    void proses() {
        this->lama = this->datang.selisih(this->pulang);
        int detikLama = this->lama.keDetik();
        int kelebihan = detikLama - BATAS_DETIK;

        this->gajiHarian = this->gapokGolongan(this->golongan);
        this->jamLembur = Waktu();
        this->lembur = 0;

        if (detikLama < BATAS_DETIK) {
            this->status = "peringatan";
        } else {
            this->status = "ok";
            if (kelebihan >= 3600) {
                this->jamLembur = Waktu::dariDetik(kelebihan);
                long long jamBulat = kelebihan / 3600;  
                this->lembur = jamBulat * this->tarifLembur(this->golongan);
            }
        }
        this->total = this->gajiHarian + this->lembur;
    }

    static void cetakHeader() {
        cout << string(122, '-') << "\n";
        cout << left << setw(4) << "No" << setw(7) << "NIP" << setw(15) << "Nama"
             << setw(4) << "Gol" << setw(10) << "Datang" << setw(10) << "Pulang"
             << setw(10) << "Lama" << setw(12) << "Jam Lembur"
             << right << setw(11) << "Gaji Harian" << setw(10) << "Lembur" << setw(10) << "Total"
             << "  " << left << "Status" << "\n";
        cout << string(122, '-') << "\n";
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

int main() {
    Pegawai obj1, obj2, obj3;
    int pilihan;

    do {
        cout << "\n===== MENU GAJI HARIAN PT INFORMATIKA =====\n";
        cout << "1. Input Objek 1 (Cara: Input Dalam Class)\n";
        cout << "2. Input Objek 2 (Cara: Konstruktor dari Luar)\n";
        cout << "3. Input Objek 3 (Cara: Input Luar -> Setter)\n";
        cout << "4. Tampilkan Tabel Gaji\n";
        cout << "5. Keluar\n";
        cout << "Pilih menu: ";
        cin >> pilihan;
        cin.ignore(); 

        switch (pilihan) {
            case 1:
                cout << "\n--- MENGISI OBJEK 1 (INPUT DALAM) ---\n";
                obj1.inputDalam(); 
                cout << "Data Objek 1 tersimpan!\n";
                break;

            case 2:
                cout << "\n--- MENGISI OBJEK 2 (KONSTRUKTOR) ---\n";
                cout << "Data disuntikkan secara otomatis dari luar melalui Konstruktor...\n";
                obj2 = Pegawai("002", "Djeremy", 3, Waktu(8, 0, 0), Waktu(17, 15, 10));
                cout << "Data Objek 2 tersimpan!\n";
                break;

            case 3:
                cout << "\n--- MENGISI OBJEK 3 (INPUT LUAR -> SETTER) ---\n";
                {
                    string nipLuar, namaLuar;
                    int golLuar, jamLuar, menitLuar, detikLuar;
                    Waktu datangLuar, pulangLuar;

                    cout << "Masukkan NIP: "; getline(cin, nipLuar);
                    obj3.setNip(nipLuar); 

                    cout << "Masukkan Nama: "; getline(cin, namaLuar);
                    obj3.setNama(namaLuar); 

                    cout << "Masukkan Golongan (1/2/3/4): "; cin >> golLuar; cin.ignore();
                    obj3.setGolongan(golLuar); 

                    cout << "Masukkan Waktu Datang:\n";
                    cout << "  Jam (0-23)   : "; cin >> jamLuar;
                    cout << "  Menit (0-59) : "; cin >> menitLuar;
                    cout << "  Detik (0-59) : "; cin >> detikLuar;
                    cin.ignore();
                    datangLuar = Waktu(jamLuar, menitLuar, detikLuar);
                    obj3.setDatang(datangLuar); 

                    cout << "Masukkan Waktu Pulang:\n";
                    cout << "  Jam (0-23)   : "; cin >> jamLuar;
                    cout << "  Menit (0-59) : "; cin >> menitLuar;
                    cout << "  Detik (0-59) : "; cin >> detikLuar;
                    cin.ignore();
                    pulangLuar = Waktu(jamLuar, menitLuar, detikLuar);
                    obj3.setPulang(pulangLuar); 

                    obj3.proses(); 
                    cout << "Data Objek 3 tersimpan!\n";
                }
                break;

            case 4:
                cout << "\n" << string(45, ' ') << "Daftar Gaji Harian PT Informatika\n";
                Pegawai::cetakHeader();
                
                if (obj1.sudahDiisi()) obj1.cetakBaris(1);
                if (obj2.sudahDiisi()) obj2.cetakBaris(2);
                if (obj3.sudahDiisi()) obj3.cetakBaris(3);
                
                cout << string(122, '-') << "\n";
                break;

            case 5:
                cout << "Keluar dari program. Terima Kasih!\n";
                break;

            default:
                cout << "Pilihan tidak valid.\n";
        }
    } while (pilihan != 5);

    return 0;
}