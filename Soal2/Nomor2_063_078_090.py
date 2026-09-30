# Nama Program  : Gaji Harian dan Lembur Pegawai
# Nama Kelompok : Tim PeBO
# Nama Anggota  : Razan Ibrahim Nabil, Muhammah Irsyad Azzarul Haq, Djeremy Rieldy Marchiano Panjaitan
# NPM  Anggota  : 140810250090, 250078, 250063
# Tanggal Buat  : 24/09/2026
# Deskripsi     : Menghitung gaji harian + lembur berdasarkan lama kerja (waktu datang - waktu pulang).
#                 Lembur berlaku jika kerja >= 8 jam, kelebihan dibulatkan ke bawah (minimal 1 jam).
#                 Pegawai yang kerja kurang dari 8 jam diberi status "peringatan".


def format_rupiah(nilai):
    """3360000 -> '3.360.000'"""
    return f"{nilai:,}".replace(",", ".")


def baca_int(pesan):
    while True:
        try:
            return int(input(pesan).strip())
        except ValueError:
            print("Input harus berupa angka.")


class Waktu:
    def __init__(self, jam=0, menit=0, detik=0):
        self._jam = jam
        self._menit = menit
        self._detik = detik

    @classmethod
    def dari_teks(cls, teks):
        """Membuat Waktu dari 'HH:mm:ss'. Melempar ValueError jika formatnya salah."""
        bagian = teks.split(":")
        if len(teks) != 8 or len(bagian) != 3 or not all(b.isdigit() for b in bagian):
            raise ValueError("Format waktu tidak valid: " + teks)
        jam, menit, detik = (int(b) for b in bagian)
        if jam > 23 or menit > 59 or detik > 59:
            raise ValueError("Nilai waktu di luar batas: " + teks)
        return cls(jam, menit, detik)

    @classmethod
    def dari_detik(cls, total):
        return cls(total // 3600, (total % 3600) // 60, total % 60)

    # Input
    def input(self, pesan):
        while True:
            try:
                w = Waktu.dari_teks(input(pesan).strip())
            except ValueError:
                print("Format waktu tidak valid. Contoh: 08:00:00 atau 17:15:10")
                continue
            self._jam, self._menit, self._detik = w._jam, w._menit, w._detik
            return

    # Proses
    def ke_detik(self):
        return self._jam * 3600 + self._menit * 60 + self._detik

    def selisih(self, lain):
        """Selisih dari waktu ini sampai waktu lain (menangani lewat tengah malam)."""
        beda = lain.ke_detik() - self.ke_detik()
        if beda < 0:
            beda += 24 * 3600
        return Waktu.dari_detik(beda)

    # Output
    def tampil(self):
        return f"{self._jam:02d}:{self._menit:02d}:{self._detik:02d}"


class Pegawai:
    GAJI_HARIAN = {1: 150000, 2: 200000, 3: 400000, 4: 500000}
    TARIF_LEMBUR = {1: 50000, 2: 75000, 3: 150000, 4: 200000}
    BATAS_DETIK = 8 * 3600
    LEBAR = 122

    def __init__(self, nip="", nama="", golongan=0, datang=None, pulang=None):
        self._nip = nip
        self._nama = nama
        self._golongan = golongan
        self._datang = datang if datang is not None else Waktu()
        self._pulang = pulang if pulang is not None else Waktu()
        self._lama = Waktu()
        self._jam_lembur = Waktu()
        self._gaji_harian = 0
        self._lembur = 0
        self._total = 0
        self._status = "-"
        if nip:
            self.proses()

    # Input
    def input(self):
        self._nip = input("Masukkan NIP: ").strip()
        self._nama = input("Masukkan Nama: ").strip()
        self._golongan = 0
        while self._golongan not in self.GAJI_HARIAN:
            self._golongan = baca_int("Masukkan Golongan (1/2/3/4): ")
            if self._golongan not in self.GAJI_HARIAN:
                print("Golongan harus 1, 2, 3, atau 4.")
        self._datang = Waktu()
        self._pulang = Waktu()
        self._datang.input("Masukkan Waktu Datang (HH:mm:ss): ")
        self._pulang.input("Masukkan Waktu Pulang (HH:mm:ss): ")
        self.proses()

    # Proses
    def proses(self):
        self._lama = self._datang.selisih(self._pulang)
        detik_lama = self._lama.ke_detik()
        kelebihan = detik_lama - self.BATAS_DETIK

        self._gaji_harian = self.GAJI_HARIAN.get(self._golongan, 0)
        self._jam_lembur = Waktu()
        self._lembur = 0

        if detik_lama < self.BATAS_DETIK:
            self._status = "peringatan"
        else:
            self._status = "ok"
            if kelebihan >= 3600:
                self._jam_lembur = Waktu.dari_detik(kelebihan)
                jam_bulat = kelebihan // 3600  # pembulatan ke bawah
                self._lembur = jam_bulat * self.TARIF_LEMBUR.get(self._golongan, 0)
        self._total = self._gaji_harian + self._lembur

    def sudah_diisi(self):
        return self._nip != ""

    # Output
    @classmethod
    def cetak_garis(cls):
        print("-" * cls.LEBAR)

    @classmethod
    def cetak_header(cls):
        print("\n" + " " * 45 + "Daftar Gaji Harian PT Informatika")
        cls.cetak_garis()
        print(f"{'No':<4}{'NIP':<7}{'Nama':<15}{'Gol':<4}{'Datang':<10}{'Pulang':<10}"
              f"{'Lama':<10}{'Jam Lembur':<12}{'Gaji Harian':>11}{'Lembur':>10}{'Total':>10}"
              f"  {'Status'}")
        cls.cetak_garis()

    def cetak_baris(self, no):
        print(f"{str(no) + '.':<4}{self._nip:<7}{self._nama:<15}{self._golongan:<4}"
              f"{self._datang.tampil():<10}{self._pulang.tampil():<10}"
              f"{self._lama.tampil():<10}{self._jam_lembur.tampil():<12}"
              f"{format_rupiah(self._gaji_harian):>11}{format_rupiah(self._lembur):>10}"
              f"{format_rupiah(self._total):>10}  {self._status}")


def input_objek(daftar):
    no = baca_int("Pilih objek yang diinput (1-3): ")
    if no < 1 or no > len(daftar):
        print("Objek tidak valid.")
        return
    daftar[no - 1].input()
    print(f"Data objek {no} tersimpan.")


def tampilkan_daftar(daftar):
    Pegawai.cetak_header()
    no = 0
    for p in daftar:
        if p.sudah_diisi():
            no += 1
            p.cetak_baris(no)
    Pegawai.cetak_garis()


def main():
    daftar = [
        Pegawai("001", "Ali", 3, Waktu(8, 0, 0), Waktu(17, 15, 10)),
        Pegawai("002", "Budi", 1, Waktu.dari_teks("08:00:00"), Waktu.dari_teks("15:30:00")),
        Pegawai(),  # diisi lewat keyboard
    ]

    pilihan = 0
    while pilihan != 3:
        print("\n===== MENU GAJI HARIAN PT INFORMATIKA =====")
        print("1. Input data pegawai (objek 1-3)")
        print("2. Tampilkan daftar gaji harian")
        print("3. Keluar")
        pilihan = baca_int("Pilih menu: ")

        if pilihan == 1:
            input_objek(daftar)
        elif pilihan == 2:
            tampilkan_daftar(daftar)
        elif pilihan == 3:
            print("Keluar dari program.")
        else:
            print("Pilihan tidak valid.")


if __name__ == "__main__":
    try:
        main()
    except (EOFError, KeyboardInterrupt):
        print("\nProgram dihentikan.")