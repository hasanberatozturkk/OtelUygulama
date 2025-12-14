package com.example.otelbudur.state;

import com.example.otelbudur.factory.Room;

public class AvailableState implements RoomState {

    @Override
    public String getStatus() {
        return "MUSAIT";
    }

    @Override
    public void book(Room oda) {
        System.out.println("Oda " + oda.getRoomNumber() + " başarıyla rezerve edildi.");
        oda.setState(new ReservedState());
    }

    @Override
    public void checkIn(Room oda) {
        System.out.println("Doğrudan Misafir Girişi Yapıldı.");
        oda.setState(new OccupiedState());
    }

    @Override
    public void checkOut(Room oda) {
        System.out.println("Oda zaten boştur.");
    }
}