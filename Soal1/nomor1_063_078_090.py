"""
Nama Program  : Koordinat
Nama Kelompok : Tim PeBO
Nama Anggota  : Razan Ibrahim Nabil , DJEREMY RIELDY MARCHIANO PANJAITAN , MUHAMMAD IRSYAD AZHARUL HAQ
NPM  Anggota  : 140810250090 , 140810250063 , 140810250078
Tanggal Buat  : 16/09/2026
Deskripsi     : Mnecari titik tengah , cermin terhadap x , dan cermin terhadap y
"""

class Koordinat:
    # Memadukan Default dan Parameterized Constructor
    def __init__(self, absis=0.0, ordinat=0.0):
        self.absis = absis
        self.ordinat = ordinat

    def setAbsis(self, absis):
        self.absis = absis

    def setOrdinat(self, ordinat):
        self.ordinat = ordinat

    def getAbsis(self):
        return self.absis

    def getOrdinat(self):
        return self.ordinat

    def input_data(self):
        absis = float(input("Masukkan Nilai Absis = "))
        self.setAbsis(absis)
        ordinat = float(input("Masukkan Nilai Ordinat = "))
        self.setOrdinat(ordinat)

    # --- FUNGSI VOID ---
    def titikTengahVoid(self, a, b):
        self.absis = (a.absis + b.absis) / 2
        self.ordinat = (a.ordinat + b.ordinat) / 2

    def cerminXVoid(self, a):
        self.absis = a.absis
        self.ordinat = -a.ordinat

    def cerminYVoid(self, a):
        self.absis = -a.absis
        self.ordinat = a.ordinat

    # --- FUNGSI RETURN ---
    def titikTengahReturn(self, b):
        temp = Koordinat(0, 0)
        temp.absis = (self.absis + b.absis) / 2
        temp.ordinat = (self.ordinat + b.ordinat) / 2
        return temp

    def cerminXReturn(self):
        temp = Koordinat()
        temp.absis = self.absis
        temp.ordinat = -self.ordinat
        return temp

    def cerminYReturn(self):
        temp = Koordinat()
        temp.absis = -self.absis
        temp.ordinat = self.ordinat
        return temp

    # --- FUNGSI CETAK ---
    def cetakVoid(self, a, b, c, d, e):
        print("HASIL AKHIR : (VOID)")
        print(f"TITIK A = ({a.getAbsis()},{a.getOrdinat()})")
        print(f"TITIK B = ({b.getAbsis()},{b.getOrdinat()})")
        print(f"TITIK TENGAH = ({c.getAbsis()},{c.getOrdinat()})")
        print(f"CERMIN TERHADAP X = ({d.getAbsis()},{d.getOrdinat()})")
        print(f"CERMIN TERHADAP Y = ({e.getAbsis()},{e.getOrdinat()})")

    def cetakReturn(self, a, b, c, d, e):
        print("HASIL AKHIR : (RETURN)")
        print(f"TITIK A = ({a.getAbsis()},{a.getOrdinat()})")
        print(f"TITIK B = ({b.getAbsis()},{b.getOrdinat()})")
        print(f"TITIK TENGAH = ({c.getAbsis()},{c.getOrdinat()})")
        print(f"CERMIN TERHADAP X = ({d.getAbsis()},{d.getOrdinat()})")
        print(f"CERMIN TERHADAP Y = ({e.getAbsis()},{e.getOrdinat()})")

# --- MAIN METHOD ---
if __name__ == "__main__":
    printer = Koordinat()
    menuUtamaAktif = True

    while menuUtamaAktif:
        print("\n=========================================")
        print("              MENU UTAMA")
        print("=========================================")
        print("1. Menguji dengan Fungsi Void")
        print("2. Menguji dengan Fungsi Return")
        print("3. Keluar")
        
        try:
            pilihanUtama = int(input("Pilih menu (1-3): "))
        except ValueError:
            print("Input harus berupa angka.")
            continue

        if pilihanUtama == 1:
            subMenuVoidAktif = True
            while subMenuVoidAktif:
                print("\n--- SUB MENU: UJI VOID ---")
                print("1. Objek 1 (Input dari Dalam / Setter)")
                print("2. Objek 2 (Input dari Luar / Scanner)")
                print("3. Objek 3 (Input dari Constructor)")
                print("4. Kembali ke Menu Utama")
                pilihanVoid = int(input("Pilih objek yang ingin dilihat (1-4): "))

                if pilihanVoid == 1:
                    print("\n[ Menjalankan Objek 1 - Void (Setter) ]")
                    a1, b1 = Koordinat(), Koordinat()
                    a1.setAbsis(2.0); a1.setOrdinat(4.0)
                    b1.setAbsis(6.0); b1.setOrdinat(8.0)

                    tengah1, cx1, cy1 = Koordinat(), Koordinat(), Koordinat()
                    tengah1.titikTengahVoid(a1, b1)
                    cx1.cerminXVoid(a1)
                    cy1.cerminYVoid(a1)
                    printer.cetakVoid(a1, b1, tengah1, cx1, cy1)

                elif pilihanVoid == 2:
                    print("\n[ Menjalankan Objek 2 - Void (Scanner) ]")
                    a2, b2 = Koordinat(), Koordinat()
                    print("Input Titik A:")
                    a2.input_data()
                    print("Input Titik B:")
                    b2.input_data()

                    tengah2, cx2, cy2 = Koordinat(), Koordinat(), Koordinat()
                    tengah2.titikTengahVoid(a2, b2)
                    cx2.cerminXVoid(a2)
                    cy2.cerminYVoid(a2)
                    printer.cetakVoid(a2, b2, tengah2, cx2, cy2)

                elif pilihanVoid == 3:
                    print("\n[ Menjalankan Objek 3 - Void (Constructor) ]")
                    a3 = Koordinat(-3.0, 5.0)
                    b3 = Koordinat(7.0, -1.0)
                    tengah3, cx3, cy3 = Koordinat(), Koordinat(), Koordinat()

                    tengah3.titikTengahVoid(a3, b3)
                    cx3.cerminXVoid(a3)
                    cy3.cerminYVoid(a3)
                    printer.cetakVoid(a3, b3, tengah3, cx3, cy3)

                elif pilihanVoid == 4:
                    subMenuVoidAktif = False
                else:
                    print("Pilihan tidak valid!")

        elif pilihanUtama == 2:
            subMenuReturnAktif = True
            while subMenuReturnAktif:
                print("\n--- SUB MENU: UJI RETURN ---")
                print("1. Objek 1 (Input dari Dalam / Setter)")
                print("2. Objek 2 (Input dari Luar / Scanner)")
                print("3. Objek 3 (Input dari Constructor)")
                print("4. Kembali ke Menu Utama")
                pilihanReturn = int(input("Pilih objek yang ingin dilihat (1-4): "))

                if pilihanReturn == 1:
                    print("\n[ Menjalankan Objek 1 - Return (Setter) ]")
                    a1, b1 = Koordinat(), Koordinat()
                    a1.setAbsis(10.0); a1.setOrdinat(12.0)
                    b1.setAbsis(20.0); b1.setOrdinat(24.0)

                    tengah1 = a1.titikTengahReturn(b1)
                    cx1 = a1.cerminXReturn()
                    cy1 = a1.cerminYReturn()
                    printer.cetakReturn(a1, b1, tengah1, cx1, cy1)

                elif pilihanReturn == 2:
                    print("\n[ Menjalankan Objek 2 - Return (Scanner) ]")
                    a2, b2 = Koordinat(), Koordinat()
                    print("Input Titik A:")
                    a2.input_data()
                    print("Input Titik B:")
                    b2.input_data()

                    tengah2 = a2.titikTengahReturn(b2)
                    cx2 = a2.cerminXReturn()
                    cy2 = a2.cerminYReturn()
                    printer.cetakReturn(a2, b2, tengah2, cx2, cy2)

                elif pilihanReturn == 3:
                    print("\n[ Menjalankan Objek 3 - Return (Constructor) ]")
                    a3 = Koordinat(4.0, -8.0)
                    b3 = Koordinat(-2.0, 6.0)

                    tengah3 = a3.titikTengahReturn(b3)
                    cx3 = a3.cerminXReturn()
                    cy3 = a3.cerminYReturn()
                    printer.cetakReturn(a3, b3, tengah3, cx3, cy3)

                elif pilihanReturn == 4:
                    subMenuReturnAktif = False
                else:
                    print("Pilihan tidak valid!")

        elif pilihanUtama == 3:
            print("Keluar dari program. Terima kasih!")
            menuUtamaAktif = False
        else:
            print("Pilihan tidak valid! Silakan pilih 1, 2, atau 3.")