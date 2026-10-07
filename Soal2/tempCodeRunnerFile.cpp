
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

                    while (true) {
                        cout << "Masukkan Golongan (1/2/3/4): ";
                        if (cin >> golLuar && (golLuar >= 1 && golLuar <= 4)) break;
                        cin.clear(); cin.ignore(10000, '\n');
                        cout << "[Peringatan] Golongan hanya 1, 2, 3, atau 4!\n";
                    }
                    cin.ignore();
                    obj3.setGolongan(golLuar); 

                    cout << "Masukkan Waktu Datang:\n";
                    while (true) {
                        cout << "  Jam (0-23)   : "; 
                        if (cin >> jamLuar && datangLuar.setJam(jamLuar)) break;
                        cin.clear(); cin.ignore(10000, '\n');
                        cout << "  [Peringatan] Input jam tidak valid! Silakan ulangi.\n";
                    }
                    while (true) {
                        cout << "  Menit (0-59) : "; 
                        if (cin >> menitLuar && datangLuar.setMenit(menitLuar)) break;
                        cin.clear(); cin.ignore(10000, '\n');
                        cout << "  [Peringatan] Input menit tidak valid! Silakan ulangi.\n";
                    }
                    while (true) {
                        cout << "  Detik (0-59) : "; 
                        if (cin >> detikLuar && datangLuar.setDetik(detikLuar)) break;
                        cin.clear(); cin.ignore(10000, '\n');
                        cout << "  [Peringatan] Input detik tidak valid! Silakan ulangi.\n";
                    }
                    cin.ignore();
                    obj3.setDatang(datangLuar); 

                    cout << "Masukkan Waktu Pulang:\n";
                    while (true) {
                        cout << "  Jam (0-23)   : "; 
                        if (cin >> jamLuar && pulangLuar.setJam(jamLuar)) break;
                        cin.clear(); cin.ignore(10000, '\n');
                        cout << "  [Peringatan] Input jam tidak valid! Silakan ulangi.\n";
                    }
                    while (true) {
                        cout << "  Menit (0-59) : "; 
                        if (cin >> menitLuar && pulangLuar.setMenit(menitLuar)) break;
                        cin.clear(); cin.ignore(10000, '\n');
                        cout << "  [Peringatan] Input menit tidak valid! Silakan ulangi.\n";
                    }
                    while (true) {
                        cout << "  Detik (0-59) : "; 
                        if (cin >> detikLuar && pulangLuar.setDetik(detikLuar)) break;
                        cin.clear(); cin.ignore(10000, '\n');
                        cout << "  [Peringatan] Input detik tidak valid! Silakan ulangi.\n";
                    }
                    cin.ignore();
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