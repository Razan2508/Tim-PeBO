'''
Nama Program  : Selisih Waktu
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil , DJEREMY RIELDY MARCHIANO PANJAITAN , MUHAMMAD IRSYAD AZHARUL HAQ
NPM  Anggota  : 140810250090 , 140810250063 , 140810250078
Tanggal Buat  : 26/09/2026
Deskripsi     : Mencari selisih waktu berdasarkan input jam , menit , detik
'''

def baca_angka(label, lo, hi):
    while True:
        try:
            n = int(input(f"{label} ({lo}-{hi}) : "))
            if lo <= n <= hi:
                return n
        except ValueError:
            pass
        print(f"Input tidak valid! Masukkan angka {lo} sampai {hi}.")


class Waktu:
    def __init__(self, jam=0, menit=0, detik=0):
        self.jam = jam
        self.menit = menit
        self.detik = detik

    def setJam(self, jam):
        self.jam = jam
        
    def setMenit(self, menit):
        self.menit = menit
        
    def setDetik(self, detik):
        self.detik = detik

    def getJam(self):
        return self.jam
        
    def getMenit(self):
        return self.menit
        
    def getDetik(self):
        return self.detik

    def input_dalam(self):
        jam = baca_angka("Jam  ", 0, 23)
        menit = baca_angka("Menit", 0, 59)
        detik = baca_angka("Detik", 0, 59)
        
        self.setJam(jam)
        self.setMenit(menit)
        self.setDetik(detik)

    def output_dalam(self):
        print(f"{self.jam:02d}:{self.menit:02d}:{self.detik:02d}")

    def ke_detik(self):
        return self.jam * 3600 + self.menit * 60 + self.detik

    def selisih_return(self, w):
        s = abs(self.ke_detik() - w.ke_detik())
        return Waktu(s // 3600, (s % 3600) // 60, s % 60)

    def selisih_void(self, w1, w2):
        s = abs(w1.ke_detik() - w2.ke_detik())
        self.setJam(s // 3600)
        self.setMenit((s % 3600) // 60)
        self.setDetik(s % 60)


def input_luar(w):
    jam = baca_angka("Jam  ", 0, 23)
    menit = baca_angka("Menit", 0, 59)
    detik = baca_angka("Detik", 0, 59)
    
    w.setJam(jam)
    w.setMenit(menit)
    w.setDetik(detik)


def output_luar(w):
    print(f"{w.getJam():02d}:{w.getMenit():02d}:{w.getDetik():02d}")


def uji(mode_void, objek):
    w1, w2 = Waktu(), Waktu()

    if objek == 1:  # Input dari Dalam / Setter
        print("\n[Objek 1 - Input dari Dalam / Setter]")
        print("Waktu 1:"); w1.input_dalam()
        print("Waktu 2:"); w2.input_dalam()
    elif objek == 2:  # Input dari Luar / Scanner
        print("\n[Objek 2 - Input dari Luar / Scanner]")
        print("Waktu 1:"); input_luar(w1)
        print("Waktu 2:"); input_luar(w2)
    elif objek == 3:  # Input dari Constructor
        print("\n[Objek 3 - Input dari Constructor]")
        w1 = Waktu(8, 30, 15)
        w2 = Waktu(10, 45, 50)

    if mode_void:
        hasil = Waktu()
        hasil.selisih_void(w1, w2)
    else:
        hasil = w1.selisih_return(w2)

    print("\nWaktu 1                      : ", end=""); w1.output_dalam()
    print("Waktu 2                      : ", end=""); w2.output_dalam()
    print("Selisih (output dalam class) : ", end=""); hasil.output_dalam()
    print("Selisih (output luar class)  : ", end=""); output_luar(hasil)


def sub_menu(mode_void):
    while True:
        print(f"\n--- SUB MENU: UJI {'VOID' if mode_void else 'RETURN'} ---")
        print("1. Objek 1 (Input dari Dalam / Setter)")
        print("2. Objek 2 (Input dari Luar / Scanner)")
        print("3. Objek 3 (Input dari Constructor)")
        print("4. Kembali ke Menu Utama")
        p = int(input("Pilih objek (1-4): "))
        if p in (1, 2, 3):
            uji(mode_void, p)
        elif p == 4:
            break


while True:
    print("\n==========================================")
    print("               MENU UTAMA")
    print("==========================================")
    print("1. Menguji dengan Fungsi Void")
    print("2. Menguji dengan Fungsi Return")
    print("3. Keluar")
    p = int(input("Pilih menu (1-3): "))
    if p == 1:
        sub_menu(True)
    elif p == 2:
        sub_menu(False)
    elif p == 3:
        break