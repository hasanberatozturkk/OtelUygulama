package com.example.otelbudur.state;

import com.example.otelbudur.factory.Room;

public interface RoomState {
    String getStatus();
    void book(Room oda);
    void checkIn(Room oda);
    void checkOut(Room oda);
}