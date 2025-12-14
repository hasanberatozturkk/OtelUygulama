package com.example.otelbudur.state;

import com.example.otelbudur.factory.Room;

public class ReservedState implements RoomState {

    @Override
    public String getStatus() {
        return "REZERVE";
    }

    @Override
    public void book(Room oda) {
        System.out.println("Oda zaten rezerve edilmiş.");
    }

    @Override
    public void checkIn(Room oda) {
        System.out.println("Rezervasyon sahibi giriş yaptı.");
        oda.setState(new OccupiedState());
    }

    @Override
    public void checkOut(Room oda) {
        System.out.println("Giriş yapılmadan çıkış yapılamaz.");
    }
}