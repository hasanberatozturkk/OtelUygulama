package com.example.otelbudur.factory;

public class FamilyRoom extends Room {
    public FamilyRoom(int num) {
        super(num, 200.0, 5);
    }

    @Override
    public String getType() {
        return "AİLE";
    }
}