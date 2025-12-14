package com.example.otelbudur.factory;

public class StandardRoom extends Room {
    public StandardRoom(int num) {
        super(num, 100.0, 2);
    }

    @Override
    public String getType() {
        return "STANDART";
    }
}