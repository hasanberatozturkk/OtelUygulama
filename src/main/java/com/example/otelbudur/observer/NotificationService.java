package com.example.otelbudur.observer;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) { observers.add(o); }

    // Bu metot, Facade içinde bildirimci.tumunuBildir() olarak çağrılacaktır.
    public void notifyAll(String msg) {
        for (Observer o : observers) {
            o.update(msg);
        }
    }
}