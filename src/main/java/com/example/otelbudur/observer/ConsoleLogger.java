package com.example.otelbudur.observer;

public class ConsoleLogger implements Observer {
    @Override
    public void update(String message) {
        System.out.println(">> [NOTIFICATION]: " + message);
    }
}