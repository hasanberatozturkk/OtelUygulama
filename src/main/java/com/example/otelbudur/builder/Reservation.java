package com.example.otelbudur.builder;

import com.example.otelbudur.domain.Customer;
import com.example.otelbudur.domain.Staff;
import com.example.otelbudur.domain.User;
import com.example.otelbudur.factory.Room;
import com.example.otelbudur.factory.RoomFactory;

import java.util.ArrayList;
import java.util.List;

import com.example.otelbudur.builder.Reservation;
import com.example.otelbudur.domain.Customer;
import com.example.otelbudur.domain.Staff;
import com.example.otelbudur.domain.User;
import com.example.otelbudur.factory.Room;
import com.example.otelbudur.factory.RoomFactory;

import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private int id;
    private User kullanici;
    private Room oda;
    private String girisTarihi;
    private String cikisTarihi;

    private Reservation(RezervasyonKurucu kurucu) {
        this.id = kurucu.id;
        this.kullanici = kurucu.kullanici;
        this.oda = kurucu.oda;
        this.girisTarihi = kurucu.girisTarihi;
        this.cikisTarihi = kurucu.cikisTarihi;
    }

    public int getId() { return id; }
    public User getUser() { return kullanici; }
    public Room getRoom() { return oda; }
    public String getDateIn() { return girisTarihi; }
    public String getDateOut() { return cikisTarihi; }

    public static class RezervasyonKurucu {
        private int id;
        private User kullanici;
        private Room oda;
        private String girisTarihi;
        private String cikisTarihi;

        public RezervasyonKurucu(int id, User kullanici, Room oda) {
            this.id = id;
            this.kullanici = kullanici;
            this.oda = oda;
        }

        public RezervasyonKurucu setDates(String in, String out) {
            this.girisTarihi = in;
            this.cikisTarihi = out;
            return this;
        }

        public Reservation build() { return new Reservation(this); }
    }
}