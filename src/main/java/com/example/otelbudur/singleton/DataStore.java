package com.example.otelbudur.singleton;

import com.example.otelbudur.domain.Customer;
import com.example.otelbudur.domain.Staff;
import com.example.otelbudur.domain.User;
import com.example.otelbudur.factory.Room;
import com.example.otelbudur.factory.RoomFactory;
import com.example.otelbudur.builder.Reservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Tasarım Deseni: Singleton (Tek Nesne)
 * Amaç: Uygulama boyunca tek bir veri merkezi üzerinden tüm rezervasyon,
 * kullanıcı ve oda bilgilerini yönetmek.
 */
public class DataStore {
    // Sınıfın tek örneğini tutar.
    private static DataStore instance;

    // Bellek içi veritabanı görevi görür.
    private final List<User> kullanicilar;
    private final List<Room> odalar;
    private final List<Reservation> rezervasyonlar;

    // Dışarıdan 'new DataStore()' denmesini engeller.
    private DataStore() {
        kullanicilar = new ArrayList<>();
        odalar = new ArrayList<>();
        rezervasyonlar = new ArrayList<>();

        // Başlangıç verilerini yükle
        verileriHazirla();
    }

    // Singleton erişimi
    public static synchronized DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void verileriHazirla() {
        kullanicilar.add(new Staff(1, "admin", "123", "Sistem Yöneticisi"));
        kullanicilar.add(new Staff(2, "personel", "123", "Otel Personeli"));
        kullanicilar.add(new Customer(3, "12345678901", "123", "Ali Yılmaz"));

        // Varsayılan Odalar (RoomFactory kullanarak)
        odalar.add(RoomFactory.createRoom("STANDART", 101));
        odalar.add(RoomFactory.createRoom("SÜİT", 201));
        odalar.add(RoomFactory.createRoom("AİLE", 301));
    }


    public List<User> getKullanicilar() {
        return kullanicilar;
    }

    public List<Room> getOdalar() {
        return odalar;
    }

    public List<Reservation> getRezervasyonlar() {
        return rezervasyonlar;
    }

    public void rezervasyonEkle(Reservation r) {
        this.rezervasyonlar.add(r);
    }
}