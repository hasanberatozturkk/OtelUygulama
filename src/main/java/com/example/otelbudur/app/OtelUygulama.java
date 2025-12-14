package com.example.otelbudur.app;

import com.example.otelbudur.facade.HotelSystemFacade;
import com.example.otelbudur.domain.User;
import com.example.otelbudur.ui.GirisEkrani;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OtelUygulama extends Application {

    public static final String BIRINCIL_RENGI = "#0077b6";
    public static final String VURGU_RENGI = "#00b4d8";
    public static final String ARKA_PLAN_GENEL = "#f0f2f5";
    public static final String ARKA_PLAN_MUSTERI = "#e3f5ff";
    public static final String ARKA_PLAN_PERSONEL = "#fff8e1";

    public static final int GENISLIK = 1000;
    public static final int YUKSEKLIK = 700;

    private static Stage anaSahne;
    private static HotelSystemFacade facade;
    public static User currentUser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        anaSahne = primaryStage;
        facade = new HotelSystemFacade();
        anaSahne.setTitle("Otel Yönetim Sistemi");
        giris_ekrani_goster();
        anaSahne.show();
    }

    public static HotelSystemFacade getFacade() { return facade; }

    public static void sahne_degistir(Scene scene, int w, int h) {
        anaSahne.setScene(scene);
        anaSahne.setWidth(w);
        anaSahne.setHeight(h);
        anaSahne.centerOnScreen();
    }

    public static void giris_ekrani_goster() {
        currentUser = null;
        sahne_degistir(GirisEkrani.olustur(), 550, 700);
    }
}