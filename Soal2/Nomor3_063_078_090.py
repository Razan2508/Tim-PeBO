'''
Nama Program  : Gaji Harian dan Lembur Pegawai (Full OOP)
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil, Muhammah Irsyad Azzarul Haq, Djeremy Rieldy Marchiano Panjaitan
NPM  Anggota  : 140810250090, 250078, 250063
Tanggal Buat  : 26/09/2026
Deskripsi     : Menghitung gaji harian + lembur dengan metode 3 Input (Dalam, Constructor, Luar/Setter).
'''

# --- FUNGSI BANTUAN FORMAT UANG ---
def format_rupiah(nilai):
    angka = str(nilai)
    panjang = len(angka)
    for i in range(panjang - 3, 0, -3):
        angka = angka[:i] + "." + angka[i:]
    return angka

class Waktu:
    def __init__(self, jam=0, menit=0, detik=0):
        # Fallback ke 0 jika konstruktor menerima nilai yang tidak valid
        if not self.setJam(jam): self.jam = 0
        if not self.setMenit(menit): self.menit = 0
        if not self.setDetik(detik): self.detik = 0

    # Setter dengan Return Boolean
    def setJam(self, jam):
        if 0 <= jam <= 23:
            self.jam = jam
            return True
        return False

    def setMenit(self, menit):
        if 0 <= menit <= 59:
            self.menit = menit
            return True
        return False

    def setDetik(self, detik):
        if 0 <= detik <= 59:
            self.detik = detik
            return True
        return False

    def getJam(self):
        return self.jam

    def getMenit(self):
        return self.menit

    def getDetik(self):
        return self.detik

    def inputDalam(self, pesan):
        print(pesan)
        while True:
            try:
                j = int(input("  Jam (0-23)   : "))
                if self.setJam(j): break
            except ValueError:
                pass
            print("  [Peringatan] Input jam tidak valid! Silakan ulangi.")
            
        while True:
            try:
                m = int(input("  Menit (0-59) : "))
                if self.setMenit(m): break
            except ValueError:
                pass
            print("  [Peringatan] Input menit tidak valid! Silakan ulangi.")
            
        while True:
            try:
                d = int(input("  Detik (0-59) : "))
                if self.setDetik(d): break
            except ValueError:
                pass
            print("  [Peringatan] Input detik tidak valid! Silakan ulangi.")

    def keDetik(self):
        return self.jam * 3600 + self.menit * 60 + self.detik

    @staticmethod
    def dariDetik(total):
        return Waktu(total // 3600, (total % 3600) // 60, total % 60)

    def selisih(self, lain):
        beda = lain.keDetik() - self.keDetik()
        if beda < 0:
            beda += 24 * 3600
        return Waktu.dariDetik(beda)

    def tampil(self):
        str_jam = ("0" if self.jam < 10 else "") + str(self.jam)
        str_menit = ("0" if self.menit < 10 else "") + str(self.menit)
        str_detik = ("0" if self.detik < 10 else "") + str(self.detik)
        return f"{str_jam}:{str_menit}:{str_detik}"


class Pegawai:
    BATAS_DETIK = 8 * 3600

    def _gapokGolongan(self, gol):
        if gol == 1: return 150000
        elif gol == 2: return 200000
        elif gol == 3: return 400000
        elif gol == 4: return 500000
        return 0

    def _tarifLembur(self, gol):
        if gol == 1: return 50000
        elif gol == 2: return 75000
        elif gol == 3: return 150000
        elif gol == 4: return 200000
        return 0

    def __init__(self, nip="", nama="", golongan=0, datang=None, pulang=None):
        self.nip = nip
        self.nama = nama
        self.golongan = golongan
        self.datang = datang if datang else Waktu()
        self.pulang = pulang if pulang else Waktu()
        
        self.lama = Waktu()
        self.jamLembur = Waktu()
        self.gajiHarian = 0
        self.lembur = 0
        self.total = 0
        self.status = "-"
        
        if nip != "":
            self.proses()

    def setNip(self, nip): 
        self.nip = nip
    def setNama(self, nama): 
        self.nama = nama
    def setGolongan(self, golongan): 
        self.golongan = golongan
    def setDatang(self, datang): 
        self.datang = datang
    def setPulang(self, pulang): 
        self.pulang = pulang

    def getNip(self): 
        return self.nip
    def getNama(self): 
        return self.nama
    def getGolongan(self): 
        return self.golongan

    def sudahDiisi(self):
        return self.nip != ""

    def inputDalam(self):
        self.setNip(input("Masukkan NIP: "))
        self.setNama(input("Masukkan Nama: "))
        
        while True:
            try:
                gol = int(input("Masukkan Golongan (1/2/3/4): "))
                if 1 <= gol <= 4:
                    self.setGolongan(gol)
                    break
            except ValueError:
                pass
            print("[Peringatan] Golongan hanya 1, 2, 3, atau 4!")
        
        dtg = Waktu()
        dtg.inputDalam("Masukkan Waktu Datang:")
        self.setDatang(dtg)
        
        plg = Waktu()
        plg.inputDalam("Masukkan Waktu Pulang:")
        self.setPulang(plg)
        
        self.proses()

    def proses(self):
        self.lama = self.datang.selisih(self.pulang)
        detikLama = self.lama.keDetik()
        kelebihan = detikLama - self.BATAS_DETIK

        self.gajiHarian = self._gapokGolongan(self.golongan)
        self.jamLembur = Waktu()
        self.lembur = 0

        if detikLama < self.BATAS_DETIK:
            self.status = "peringatan"
        else:
            self.status = "ok"
            if kelebihan >= 3600:
                self.jamLembur = Waktu.dariDetik(kelebihan)
                jamBulat = kelebihan // 3600
                self.lembur = jamBulat * self._tarifLembur(self.golongan)
                
        self.total = self.gajiHarian + self.lembur

    @staticmethod
    def cetakHeader():
        print("-" * 122)
        print(f"{'No':<4}{'NIP':<7}{'Nama':<15}{'Gol':<4}{'Datang':<10}{'Pulang':<10}{'Lama':<10}{'Jam Lembur':<12}{'Gaji Harian':>11}{'Lembur':>10}{'Total':>10}  {'Status':<10}")
        print("-" * 122)

    def cetakBaris(self, no):
        print(f"{str(no) + '.':<4}{self.nip:<7}{self.nama:<15}{self.golongan:<4}{self.datang.tampil():<10}{self.pulang.tampil():<10}{self.lama.tampil():<10}{self.jamLembur.tampil():<12}{format_rupiah(self.gajiHarian):>11}{format_rupiah(self.lembur):>10}{format_rupiah(self.total):>10}  {self.status:<10}")


def main():
    obj1 = Pegawai()
    obj2 = Pegawai()
    obj3 = Pegawai()

    while True:
        print("\n===== MENU GAJI HARIAN PT INFORMATIKA =====")
        print("1. Input Objek 1 (Cara: Input Dalam Class)")
        print("2. Input Objek 2 (Cara: Konstruktor dari Luar)")
        print("3. Input Objek 3 (Cara: Input Luar -> Setter)")
        print("4. Tampilkan Tabel Gaji")
        print("5. Keluar")
        
        try:
            pilihan = int(input("Pilih menu: "))
        except ValueError:
            print("Pilihan tidak valid.")
            continue

        if pilihan == 1:
            print("\n--- MENGISI OBJEK 1 (INPUT DALAM) ---")
            obj1.inputDalam()
            print("Data Objek 1 tersimpan!")
            
        elif pilihan == 2:
            print("\n--- MENGISI OBJEK 2 (KONSTRUKTOR) ---")
            print("Data disuntikkan secara otomatis dari luar melalui Konstruktor...")
            obj2 = Pegawai("002", "Djeremy", 3, Waktu(8, 0, 0), Waktu(17, 15, 10))
            print("Data Objek 2 tersimpan!")
            
        elif pilihan == 3:
            print("\n--- MENGISI OBJEK 3 (INPUT LUAR -> SETTER) ---")
            obj3.setNip(input("Masukkan NIP: "))
            obj3.setNama(input("Masukkan Nama: "))
            
            while True:
                try:
                    gol = int(input("Masukkan Golongan (1/2/3/4): "))
                    if 1 <= gol <= 4:
                        obj3.setGolongan(gol)
                        break
                except ValueError:
                    pass
                print("[Peringatan] Golongan hanya 1, 2, 3, atau 4!")
            
            dtgLuar = Waktu()
            print("Masukkan Waktu Datang:")
            while True:
                try:
                    j = int(input("  Jam (0-23)   : "))
                    if dtgLuar.setJam(j): break
                except ValueError:
                    pass
                print("  [Peringatan] Input jam tidak valid! Silakan ulangi.")
            while True:
                try:
                    m = int(input("  Menit (0-59) : "))
                    if dtgLuar.setMenit(m): break
                except ValueError:
                    pass
                print("  [Peringatan] Input menit tidak valid! Silakan ulangi.")
            while True:
                try:
                    d = int(input("  Detik (0-59) : "))
                    if dtgLuar.setDetik(d): break
                except ValueError:
                    pass
                print("  [Peringatan] Input detik tidak valid! Silakan ulangi.")
            obj3.setDatang(dtgLuar)
            
            plgLuar = Waktu()
            print("Masukkan Waktu Pulang:")
            while True:
                try:
                    j = int(input("  Jam (0-23)   : "))
                    if plgLuar.setJam(j): break
                except ValueError:
                    pass
                print("  [Peringatan] Input jam tidak valid! Silakan ulangi.")
            while True:
                try:
                    m = int(input("  Menit (0-59) : "))
                    if plgLuar.setMenit(m): break
                except ValueError:
                    pass
                print("  [Peringatan] Input menit tidak valid! Silakan ulangi.")
            while True:
                try:
                    d = int(input("  Detik (0-59) : "))
                    if plgLuar.setDetik(d): break
                except ValueError:
                    pass
                print("  [Peringatan] Input detik tidak valid! Silakan ulangi.")
            obj3.setPulang(plgLuar)
            
            obj3.proses()
            print("Data Objek 3 tersimpan!")
            
        elif pilihan == 4:
            print("\n" + " " * 45 + "Daftar Gaji Harian PT Informatika")
            Pegawai.cetakHeader()
            
            if obj1.sudahDiisi(): obj1.cetakBaris(1)
            if obj2.sudahDiisi(): obj2.cetakBaris(2)
            if obj3.sudahDiisi(): obj3.cetakBaris(3)
            
            print("-" * 122)
            
        elif pilihan == 5:
            print("Keluar dari program. Terima Kasih!")
            break
            
        else:
            print("Pilihan tidak valid.")

if __name__ == "__main__":
    main()