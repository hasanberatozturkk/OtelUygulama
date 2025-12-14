package com.example.otelbudur.facade;

import com.example.otelbudur.singleton.DataStore;
import com.example.otelbudur.domain.Customer;
import com.example.otelbudur.domain.User;
import com.example.otelbudur.observer.ConsoleLogger;
import com.example.otelbudur.observer.NotificationService;
import com.example.otelbudur.builder.Reservation;
import com.example.otelbudur.factory.Room;
import com.example.otelbudur.factory.RoomFactory;
import com.example.otelbudur.state.AvailableState;
import com.example.otelbudur.state.OccupiedState;
import com.example.otelbudur.state.ReservedState;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FACADE Tasarım Deseni
 * Amç: Karmaşık alt sistemleri (DataStore, Notification, State, Builder)
 * tek bir basit arayüz arkasında toplamak.
 */
public class HotelSystemFacade {
    // Singleton DataStore erişimi
    private final DataStore veritabani = DataStore.getInstance();
    private final NotificationService bildirimci = new NotificationService();

    public HotelSystemFacade() {
        // Observer kaydı
        bildirimci.addObserver(new ConsoleLogger());
    }

    // --- KULLANICI VE PROFİL İŞLEMLERİ ---

    public User girisYap(String kullaniciAdi, String sifre) {
        return veritabani.getKullanicilar().stream()
                .filter(u -> u.login(kullaniciAdi, sifre))
                .findFirst()
                .orElse(null);
    }

    public void musteriKaydet(String adSoyad, String tc, String sifre) {
        int yeniId = veritabani.getKullanicilar().size() + 1;
        User yeniMusteri = new Customer(yeniId, tc, sifre, adSoyad);
        veritabani.getKullanicilar().add(yeniMusteri);
        bildirimci.notifyAll("Yeni Müşteri Kaydedildi: " + adSoyad);
    }

    public void profilBilgisiGuncelle(User kullanici, String yeniAd, String yeniEposta, String yeniTel) {
        if (kullanici instanceof Customer musteri) { // Java 16+ Pattern Matching
            musteri.setFullName(yeniAd);
            musteri.setEmail(yeniEposta);
            musteri.setPhone(yeniTel);
            bildirimci.notifyAll("Profil güncellendi: " + yeniAd);
        }
    }
    public List<Customer> tumMusterileriGetir() {
        // 1. Singleton DataStore üzerinden tüm kullanıcı listesini al
        // 2. Stream ile filtrele: Sadece Customer tipinde olanları seç
        // 3. Map ile cast et: User nesnelerini Customer nesnesine dönüştür
        return veritabani.getKullanicilar().stream()
                .filter(user -> user instanceof Customer) // Sadece müşteri olanları ayıkla
                .map(user -> (Customer) user)              // User tipini Customer'a dönüştür
                .collect(Collectors.toList());             // Liste olarak geri döndür
    }

    // --- ODA YÖNETİMİ ---

    public void odaEkle(String tip, int numara) {
        Room r = RoomFactory.createRoom(tip, numara);
        veritabani.getOdalar().add(r);
        bildirimci.notifyAll("Yeni Oda eklendi: #" + numara);
    }

    public void checkInYap(int odaNumarasi) {
        Room oda = odaBul(odaNumarasi);
        if (oda != null) {
            oda.getState().checkIn(oda); // STATE Pattern
            bildirimci.notifyAll("Oda #" + odaNumarasi + " girişi yapıldı.");
        }
    }

    public void checkOutYap(int odaNumarasi) {
        Room oda = odaBul(odaNumarasi);
        if (oda != null) {
            oda.getState().checkOut(oda); // STATE Pattern
            bildirimci.notifyAll("Oda #" + odaNumarasi + " boşaltıldı.");
        }
    }

    // --- REZERVASYON VE FİLTRELEME ---

    public List<Room> musaitOdalarıFiltrele(String tip, int kisi, LocalDate giris, LocalDate cikis) {
        return veritabani.getOdalar().stream()
                .filter(r -> r.getCapacity() >= kisi)
                .filter(r -> "TÜMÜ".equals(tip) || r.getType().equalsIgnoreCase(tip))
                .filter(r -> isOdaMusait(r, giris, cikis))
                .collect(Collectors.toList());
    }

    public void rezervasyonYap(User kullanici, int odaNo, String giris, String cikis) {
        Room oda = odaBul(odaNo);
        if (oda != null && oda.getState() instanceof AvailableState) {

            // BUILDER Pattern kullanımı
            Reservation yeniRez = new Reservation.RezervasyonKurucu(
                    veritabani.getRezervasyonlar().size() + 1, kullanici, oda)
                    .setDates(giris, cikis)
                    .build();

            veritabani.rezervasyonEkle(yeniRez);
            oda.getState().book(oda); // Durumu 'Reserved' yap
            bildirimci.notifyAll("Rezervasyon Onaylandı: " + kullanici.getUsername());
        } else {
            throw new RuntimeException("Oda uygun değil.");
        }
    }
    public List<Reservation> rezervasyonlariFiltrele(String aramaMetni, String durumFiltresi, User kullanici) {
        return veritabani.getRezervasyonlar().stream()
                // 1. Yetki Kontrolü: Personel her şeyi, Müşteri sadece kendisininkini görür
                .filter(r -> kullanici.getRole().equals("STAFF") || r.getUser().getId() == kullanici.getId())

                // 2. Durum Filtresi (Aktif / Geçmiş)
                .filter(r -> {
                    boolean isPast = LocalDate.parse(r.getDateOut()).isBefore(LocalDate.now());
                    if ("GEÇMİŞ".equals(durumFiltresi)) return isPast;
                    if ("AKTİF".equals(durumFiltresi)) return !isPast;
                    return true; // "TÜMÜ" ise hepsini al
                })

                // 3. Metin Arama (İsim veya Oda No içeriyor mu?)
                .filter(r -> {
                    if (aramaMetni == null || aramaMetni.trim().isEmpty()) return true;
                    String arama = aramaMetni.toLowerCase();
                    boolean isimMatch = r.getUser().getFullName().toLowerCase().contains(arama);
                    boolean odaMatch = String.valueOf(r.getRoom().getRoomNumber()).contains(arama);
                    return isimMatch || odaMatch;
                })
                .collect(Collectors.toList());
    }


    public boolean rezervasyonIptalEt(int rezId) {
        return veritabani.getRezervasyonlar().removeIf(r -> {
            if (r.getId() == rezId) {
                r.getRoom().setState(new AvailableState());
                bildirimci.notifyAll("Rezervasyon iptal edildi ID: " + rezId);
                return true;
            }
            return false;
        });
    }

    private Room odaBul(int numara) {
        return veritabani.getOdalar().stream()
                .filter(r -> r.getRoomNumber() == numara)
                .findFirst().orElse(null);
    }

    private boolean isOdaMusait(Room room, LocalDate giris, LocalDate cikis) {
        if (room.getState() instanceof OccupiedState) return false;
        if (room.getState() instanceof AvailableState) return true;

        // Eğer rezerve edilmişse tarih çakışmasına bak
        return veritabani.getRezervasyonlar().stream()
                .filter(res -> res.getRoom().getRoomNumber() == room.getRoomNumber())
                .noneMatch(res -> {
                    LocalDate exStart = LocalDate.parse(res.getDateIn());
                    LocalDate exEnd = LocalDate.parse(res.getDateOut());
                    return (giris.isBefore(exEnd) && exStart.isBefore(cikis));
                });
    }

    public boolean sifreDegistir(User kullanici, String eskiSifre, String yeniSifre) {
        // Mevcut şifre doğrulaması
        if (kullanici.login(kullanici.getUsername(), eskiSifre)) {
            kullanici.setPassword(yeniSifre); // Domain nesnesini güncelle

            // Observer bilgilendirme
            bildirimci.notifyAll(kullanici.getUsername() + " kullanıcısı şifresini başarıyla değiştirdi.");
            return true;
        }
        return false;
    }


}