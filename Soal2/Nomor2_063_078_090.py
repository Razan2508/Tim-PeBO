# Nama Program  :
# Nama Kelompok : Tim PeBO
# Nama Anggota  : Razan Ibrahim Nabil, Muhammah Irsyad Azzarul Haq, Djeremy Rieldy Marchiano Panjaitan
# NPM  Anggota  : 140810250090, 250078, 250063
# Tanggal Buat  : 24/09/2026
# Deskripsi     :

from datetime import date, datetime, timedelta

def parse_waktu(teks):
    """Pengganti LocalTime.parse(..., "HH:mm"). Mengembalikan datetime.time,
    atau melempar ValueError jika formatnya salah (seperti exception di Java)."""
    if len(teks) != 5:
        raise ValueError("Format waktu tidak valid: " + teks)
    return datetime.strptime(teks, "%H:%M").time()


def format_rupiah(nilai):
    """Pengganti "%,.0f" di Java: 3360000 -> "3.360.000" """
    return f"{nilai:,.0f}".replace(",", ".")


def jam_menit(lama):
    """Mengubah timedelta menjadi (jam, menit). Pengganti toHours() dan toMinutesPart()."""
    total_menit = int(lama.total_seconds() // 60)
    return total_menit // 60, total_menit % 60


def baca_int(pesan):
    """Pengganti scanner.nextInt(): diulang sampai yang diketik benar-benar angka."""
    while True:
        try:
            return int(input(pesan))
        except ValueError:
            print("Input harus berupa angka.")


def baca_waktu(pesan):
    """Membaca waktu dari keyboard, diulang sampai formatnya valid."""
    while True:
        teks = input(pesan).strip()
        try:
            return parse_waktu(teks)
        except ValueError:
            print("Format waktu tidak valid. Contoh: 08:00 atau 17:30")


class Pegawai:
    GAPOK = {1: 1500000, 2: 2000000, 3: 3000000, 4: 5000000}
    PERSEN_TUNJANGAN = {1: 0.10, 2: 0.12, 3: 0.12, 4: 0.15}
    PERSEN_POTONGAN = {1: 0.01, 2: 0.02, 3: 0.02, 4: 0.04}

    def __init__(self, nip="", nama="", golongan=0):
        self._nip = nip
        self._nama = nama
        self._golongan = golongan
        self._waktu_datang = None 
        self._waktu_pulang = None

    def set_pegawai(self, nip, nama, golongan):
        self._nip = nip
        self._nama = nama
        self._golongan = golongan

    def set_nip(self, nip):
        self._nip = nip

    def set_nama(self, nama):
        self._nama = nama

    def set_golongan(self, golongan):
        self._golongan = golongan

    def set_waktu_datang(self, waktu):
        self._waktu_datang = parse_waktu(waktu)

    def set_waktu_pulang(self, waktu):
        self._waktu_pulang = parse_waktu(waktu)

    def set_waktu_kerja(self, datang, pulang):
        self.set_waktu_datang(datang)
        self.set_waktu_pulang(pulang)

    # ---------- Getter ----------
    def get_nip(self):
        return self._nip

    def get_nama(self):
        return self._nama

    def get_golongan(self):
        return self._golongan

    def get_waktu_datang(self):
        return self._waktu_datang

    def get_waktu_pulang(self):
        return self._waktu_pulang

    def get_waktu_datang_teks(self):
        return "-" if self._waktu_datang is None else self._waktu_datang.strftime("%H:%M")

    def get_waktu_pulang_teks(self):
        return "-" if self._waktu_pulang is None else self._waktu_pulang.strftime("%H:%M")

    # ---------- Input ----------
    def input_waktu(self):
        self._waktu_datang = baca_waktu("Masukkan Waktu Datang (HH:mm): ")
        self._waktu_pulang = baca_waktu("Masukkan Waktu Pulang (HH:mm): ")

    def input_pegawai(self):
        self._nip = input("Masukkan NIP: ")
        self._nama = input("Masukkan Nama Pegawai: ")
        self._golongan = baca_int("Masukkan Golongan (1/2/3/4): ")
        self.input_waktu()

    def hitung_lama_kerja_return(self):
        if self._waktu_datang is None or self._waktu_pulang is None:
            return timedelta(0)
        hari_ini = date.today()
        lama = (datetime.combine(hari_ini, self._waktu_pulang)
                - datetime.combine(hari_ini, self._waktu_datang))
        if lama < timedelta(0):
            lama += timedelta(hours=24)
        return lama

    def get_lama_kerja_teks(self):
        jam, menit = jam_menit(self.hitung_lama_kerja_return())
        return f"{jam} jam {menit} menit"

    def hitung_lama_kerja_void(self):
        if self._waktu_datang is None or self._waktu_pulang is None:
            print(" Lama Kerja = - (waktu belum diisi)")
            return
        hari_ini = date.today()
        lama = (datetime.combine(hari_ini, self._waktu_pulang)
                - datetime.combine(hari_ini, self._waktu_datang))
        if lama < timedelta(0):
            lama += timedelta(hours=24)
        jam, menit = jam_menit(lama)
        print(f" Lama Kerja = {jam} jam {menit} menit")

    def cari_gapok(self):
        return self.GAPOK.get(self._golongan, 0)

    def cari_tunjangan(self):
        return self.cari_gapok() * self.PERSEN_TUNJANGAN.get(self._golongan, 0)

    def cari_potongan(self):
        return self.cari_gapok() * self.PERSEN_POTONGAN.get(self._golongan, 0)

    def cari_gaji_total(self):
        return self.cari_gapok() + self.cari_tunjangan() - self.cari_potongan()

    def hitung_gaji_return(self):
        return self.cari_gapok() + self.cari_tunjangan() - self.cari_potongan()

    def hitung_gaji_void(self):
        gapok = self.cari_gapok()
        tunjangan = self.cari_tunjangan()
        potongan = self.cari_potongan()
        total = gapok + tunjangan - potongan
        print(f" Gapok      = Rp {format_rupiah(gapok)}")
        print(f" Tunjangan  = Rp {format_rupiah(tunjangan)}")
        print(f" Potongan   = Rp {format_rupiah(potongan)}")
        print(f" Total Gaji = Rp {format_rupiah(total)}")

    def cetak_waktu_kerja(self):
        if self._waktu_datang is None or self._waktu_pulang is None:
            lama = "-"
        else:
            lama = self.get_lama_kerja_teks()
        print(f" Waktu Datang (Clock In)  = {self.get_waktu_datang_teks()}")
        print(f" Waktu Pulang (Clock Out) = {self.get_waktu_pulang_teks()}")
        print(f" Lama Kerja               = {lama}")

    def cetak_pegawai(self):
        print("-" * 112)
        print(f"| {'NIP':<15} | {'Nama':<15} | {'Gol':<3} | {'Gapok':<13} | "
              f"{'Tunjangan':<13} | {'Potongan':<13} | {'Total Gaji':<15} |")
        print("-" * 112)
        print(f"| {self._nip:<15} | {self._nama:<15} | {self._golongan:<3} | "
              f"Rp {format_rupiah(self.cari_gapok()):>10} | "
              f"Rp {format_rupiah(self.cari_tunjangan()):>10} | "
              f"Rp {format_rupiah(self.cari_potongan()):>10} | "
              f"Rp {format_rupiah(self.cari_gaji_total()):>12} |")
        print("-" * 112)
        self.cetak_waktu_kerja()


def pilih_objek(daftar):
    """Memilih objek 1-3 sekaligus menyiapkan datanya.
    Mengembalikan objek yang dipilih, atau None jika pilihan tidak valid."""
    print("1. Obj. 1 (data diset lewat setter)")
    print("2. Obj. 2 (data diset lewat constructor)")
    print("3. Obj. 3 (data diinput dari keyboard)")
    no = baca_int("Pilih objek (1-3): ")

    if no == 1:
        daftar[0].set_pegawai("140810250078", "Irsyad", 1)
        daftar[0].set_waktu_kerja("07:45", "16:15")
    elif no == 2:
        pass 
    elif no == 3:
        daftar[2].input_pegawai()
    else:
        print("Objek tidak valid.")
        return None
    return daftar[no - 1]


def hitung_void(daftar):
    """Cara 1: method void, hasil dicetak di dalam method."""
    p = pilih_objek(daftar)
    if p is None:
        return
    print(f" NIP        = {p.get_nip()}")
    print(f" Nama       = {p.get_nama()}")
    print(f" Golongan   = {p.get_golongan()}")
    print(f" Clock In   = {p.get_waktu_datang_teks()}")
    print(f" Clock Out  = {p.get_waktu_pulang_teks()}")
    p.hitung_gaji_void()
    p.hitung_lama_kerja_void()


def hitung_return(daftar):
    """Cara 2: method return, hasil diterima lalu dicetak di sini."""
    p = pilih_objek(daftar)
    if p is None:
        return
    gapok = p.cari_gapok()
    tunjangan = p.cari_tunjangan()
    potongan = p.cari_potongan()
    total = p.hitung_gaji_return()
    jam, menit = jam_menit(p.hitung_lama_kerja_return())
    print(f" NIP        = {p.get_nip()}")
    print(f" Nama       = {p.get_nama()}")
    print(f" Golongan   = {p.get_golongan()}")
    print(f" Clock In   = {p.get_waktu_datang_teks()}")
    print(f" Clock Out  = {p.get_waktu_pulang_teks()}")
    print(f" Gapok      = Rp {format_rupiah(gapok)}")
    print(f" Tunjangan  = Rp {format_rupiah(tunjangan)}")
    print(f" Potongan   = Rp {format_rupiah(potongan)}")
    print(f" Total Gaji = Rp {format_rupiah(total)}")
    print(f" Lama Kerja = {jam} jam {menit} menit")


def main():
    daftar = [
        Pegawai(),
        Pegawai("140810250090", "Razan", 3),
        Pegawai(),
    ]

    daftar[1].set_waktu_kerja("08:00", "17:30")

    pilihan = 0
    while pilihan != 3: 
        print("\n===== MENU GajiPegawai =====")
        print("1. Hitung Gaji & Lama Kerja (void)")
        print("2. Hitung Gaji & Lama Kerja (return)")
        print("3. Keluar")
        pilihan = baca_int("Pilih menu: ")

        if pilihan == 1:
            print("Hitung (void)")
            hitung_void(daftar)
        elif pilihan == 2:
            print("Hitung (return)")
            hitung_return(daftar)
        elif pilihan == 3:
            print("Keluar dari program.")
        else:
            print("Pilihan tidak valid.")


if __name__ == "__main__":
    try:
        main()
    except (EOFError, KeyboardInterrupt): 
        print("\nProgram dihentikan.")