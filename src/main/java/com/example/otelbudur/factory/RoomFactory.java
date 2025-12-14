package com.example.otelbudur.factory;

public class RoomFactory {
    public static Room createRoom(String type, int number) {
        switch (type.toUpperCase()) {
            case "STANDART": return new StandardRoom(number);
            case "AİLE": return new FamilyRoom(number);
            case "SÜİT": return new SuiteRoom(number);
            default: return new StandardRoom(number);
        }
    }
}