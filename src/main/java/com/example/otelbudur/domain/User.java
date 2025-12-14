package com.example.otelbudur.domain;

/**
 * Kullanıcı (Müşteri/Personel) için temel soyut sınıf.
 * ABSTRACT CLASS gereksinimini karşılar.
 */
public abstract class User {

    protected int id;
    protected String kullaniciAdi;
    protected String sifre;
    protected String adSoyad;
    protected String email;
    protected String telefon;

    public User(int id, String u, String p, String name) {
        this.id = id;
        this.kullaniciAdi = u;
        this.sifre = p;
        this.adSoyad = name;
        this.email = "";
        this.telefon = "";
    }

    public User(int id, String u, String p, String name, String email, String phone) {
        this.id = id;
        this.kullaniciAdi = u;
        this.sifre = p;
        this.adSoyad = name;
        this.email = email;
        this.telefon = phone;
    }


    // Kimlik Doğrulama
    public boolean login(String u, String p) { return this.kullaniciAdi.equals(u) && this.sifre.equals(p); }


    public int getId() { return id; }
    public String getUsername() { return kullaniciAdi; }
    public String getFullName() { return adSoyad; }
    public String getPassword() { return sifre; }
    public String getEmail() { return email; }
    public String getPhone() { return telefon; }


    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public void setFullName(String newFullName) { this.adSoyad = newFullName; }

    public void setPassword(String newPassword) { this.sifre = newPassword; }

    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.telefon = phone; }

    // ABSTRACT METHOD
    public abstract String getRole();
}