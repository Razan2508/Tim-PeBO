def baca_angka(label, lo, hi):
    # Baca angka dengan validasi rentang (diulang sampai valid)
    while True:
        try:
            n = int(input(f"{label} ({lo}-{hi}) : "))
            if lo <= n <= hi:
                return n
        except ValueError:
            pass
        print(f"Input tidak valid! Masukkan angka {lo} sampai {hi}.")


class Waktu:
    # Constructor
    def __init__(self, jam=0, menit=0, detik=0):
        self.jam = 0
        self.menit = 0
        self.detik = 0
        self.set_waktu(jam, menit, detik)

    # Setter: menolak nilai di luar rentang (jam 0-23, menit 0-59, detik 0-59)
    def set_waktu(self, jam, menit, detik):
        if not (0 <= jam <= 23 and 0 <= menit <= 59 and 0 <= detik <= 59):
            return False
        self.jam, self.menit, self.detik = jam, menit, detik
        return True

    # Input (dalam class): baca di dalam class, isi lewat setter
    def input_dalam(self):
        j = baca_angka("Jam  ", 0, 23)
        m = baca_angka("Menit", 0, 59)
        d = baca_angka("Detik", 0, 59)
        self.set_waktu(j, m, d)

    # Output (dalam class)
    def output_dalam(self):
        print(f"{self.jam:02d}:{self.menit:02d}:{self.detik:02d}")

    def ke_detik(self):
        return self.jam * 3600 + self.menit * 60 + self.detik

    # Proses cara 1: fungsi return
    def selisih_return(self, w):
        s = abs(self.ke_detik() - w.ke_detik())
        return Waktu(s // 3600, (s % 3600) // 60, s % 60)

    # Proses cara 2: void (hasil disimpan di objek ini)
    def selisih_void(self, w1, w2):
        s = abs(w1.ke_detik() - w2.ke_detik())
        self.set_waktu(s // 3600, (s % 3600) // 60, s % 60)


# Input luar class: baca di luar class (input di fungsi), isi lewat setter
def input_luar(w):
    j = baca_angka("Jam  ", 0, 23)
    m = baca_angka("Menit", 0, 59)
    d = baca_angka("Detik", 0, 59)
    w.set_waktu(j, m, d)


# Output luar class
def output_luar(w):
    print(f"{w.jam:02d}:{w.menit:02d}:{w.detik:02d}")


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