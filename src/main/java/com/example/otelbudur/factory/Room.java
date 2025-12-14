package com.example.otelbudur.factory;

import com.example.otelbudur.state.RoomState;
import com.example.otelbudur.state.AvailableState;

public abstract class Room {

    protected int odaNumarasi;
    protected double fiyat;
    protected int kapasite;
    protected RoomState state;

    public Room(int num, double price, int cap) {
        this.odaNumarasi = num;
        this.fiyat = price;
        this.kapasite = cap;
        this.state = new AvailableState();
    }

    public void setState(RoomState state) { this.state = state; }
    public RoomState getState() { return state; }

    public int getRoomNumber() { return odaNumarasi; }
    public double getPrice() { return fiyat; }
    public int getCapacity() { return kapasite; }

    public abstract String getType();
}