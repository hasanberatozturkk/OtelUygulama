package com.example.otelbudur.domain;

public class Staff extends User {
    public Staff(int id, String u, String p, String name) {
        super(id, u, p, name);
    }
    @Override public String getRole() { return "PERSONEL"; }
}