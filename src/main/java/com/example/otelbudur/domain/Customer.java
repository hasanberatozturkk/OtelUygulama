package com.example.otelbudur.domain;

public class Customer extends User {
    public Customer(int id, String u, String p, String name) {
        super(id, u, p, name);
    }
    @Override public String getRole() { return "MÜŞTERİ"; }
}