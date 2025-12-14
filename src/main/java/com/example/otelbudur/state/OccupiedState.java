package com.example.otelbudur.state;

import com.example.otelbudur.factory.Room;

public class OccupiedState implements RoomState {

    @Override
    public String getStatus() {
        return "DOLU";
    }

    @Override
    public void book(Room oda) {
        System.out.println("Hata: Oda dolu olduğu için rezerve edilemez.");
    }

    @Override
    public void checkIn(Room oda) {
        System.out.println("Hata: Oda zaten dolu.");
    }

    @Override
    public void checkOut(Room oda) {
        System.out.println("Misafir Çıkış Yaptı. Oda Temizleniyor.");
        oda.setState(new AvailableState()); // AvailableState sınıfı kullanılmaya devam etti
    }
}