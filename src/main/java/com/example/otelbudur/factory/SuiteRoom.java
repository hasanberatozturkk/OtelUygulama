package com.example.otelbudur.factory;

public class SuiteRoom extends Room {
    public SuiteRoom(int num) {
        super(num, 300.0, 4);
    }

    @Override
    public String getType() {
        return "SÜİT";
    }
}