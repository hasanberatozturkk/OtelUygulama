package com.example.otelbudur.ui;

import com.example.otelbudur.domain.User; // Doğru User sınıfı import edildi
import com.example.otelbudur.app.OtelUygulama;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GirisEkrani {

    public static Scene olustur() {
        Label baslik = new Label("🔑 OTEL REZERVASYON SİSTEMİ");
        baslik.setStyle("-fx-font: normal bold 28px 'Arial'; -fx-text-fill: " + OtelUygulama.BIRINCIL_RENGI + ";");

        final VBox dinamikGirisAlani = new VBox(25);
        dinamikGirisAlani.setAlignment(Pos.TOP_CENTER);

        // Rol Seçim Butonları
        HBox rolButonlari = new HBox(20);
        rolButonlari.setAlignment(Pos.CENTER);

        // Personel butonu
        Button btnPersonelRol = new Button("👤 Personel Girişi");
        btnPersonelRol.setStyle(
                "-fx-background-color: " + OtelUygulama.BIRINCIL_RENGI + ";" +
                        "-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 5;"
        );

        // Müşteri butonu
        Button btnMusteriRol = new Button("🏨 Müşteri Girişi");
        btnMusteriRol.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 5;"
        );
        rolButonlari.getChildren().addAll(btnMusteriRol, btnPersonelRol);

        Button btnKayit = new Button("📝 Hemen Yeni Müşteri Kaydı Yap");
        btnKayit.setStyle("-fx-font-size: 14px; -fx-text-fill: " + OtelUygulama.VURGU_RENGI + "; -fx-underline: true;");
        // Kayıt ekranı çağrısı
        btnKayit.setOnAction(e -> OtelUygulama.sahne_degistir(KayitEkrani.olustur(), 500, 650));

        final VBox anaDuzen = new VBox(40);
        anaDuzen.setAlignment(Pos.CENTER);
        anaDuzen.setPadding(new Insets(60));
        anaDuzen.setStyle("-fx-background-color: " + OtelUygulama.ARKA_PLAN_GENEL + ";");

        StackPane kokDuzen = new StackPane(anaDuzen);

        anaDuzen.getChildren().addAll(baslik, new Separator(), rolButonlari, dinamikGirisAlani, btnKayit);

        btnMusteriRol.setOnAction(e -> {
            dinamikGirisAlani.getChildren().clear();
            dinamikGirisAlani.getChildren().add(olusturGirisFormu("Müşteri"));
            anaDuzen.setStyle("-fx-background-color: " + OtelUygulama.ARKA_PLAN_MUSTERI + ";");
        });

        btnPersonelRol.setOnAction(e -> {
            dinamikGirisAlani.getChildren().clear();
            dinamikGirisAlani.getChildren().add(olusturGirisFormu("Personel"));
            anaDuzen.setStyle("-fx-background-color: " + OtelUygulama.ARKA_PLAN_PERSONEL + ";");
        });

        //Müşteri Girişini göster
        btnMusteriRol.fire();

        return new Scene(kokDuzen, 550, 700);
    }

    private static VBox olusturGirisFormu(String rol) {
        VBox formKapsayici = new VBox(20);
        formKapsayici.setPadding(new Insets(30));
        formKapsayici.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-radius: 10; -fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0.5, 0, 0);"
        );
        formKapsayici.setMaxWidth(400);

        Label formBaslik = new Label(rol + " Girişi");
        formBaslik.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + OtelUygulama.BIRINCIL_RENGI + ";");
        formKapsayici.getChildren().add(formBaslik);

        GridPane grid= new GridPane();
        grid.setHgap(15); grid.setVgap(15);
        grid.setAlignment(Pos.CENTER_LEFT);

        final TextField kullaniciTxt = new TextField();
        kullaniciTxt.setPromptText("T.C. Kimlik / Kullanıcı Adı");
        kullaniciTxt.setPrefHeight(35);

        final PasswordField sifreTxt = new PasswordField();
        sifreTxt.setPromptText("Şifre");
        sifreTxt.setPrefHeight(35);

        Button btnGiris = new Button("➡️ " + rol + " Olarak Giriş Yap");
        btnGiris.setMaxWidth(Double.MAX_VALUE);
        String girisRenk = "Müşteri".equals(rol) ? "#4CAF50" : OtelUygulama.BIRINCIL_RENGI;
        btnGiris.setStyle(
                "-fx-background-color: " + girisRenk + ";" +
                        "-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 25; -fx-font-size: 16px; -fx-background-radius: 5;"
        );

        final Label hataMesajiLbl = new Label();
        hataMesajiLbl.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        hataMesajiLbl.setVisible(false);

        grid.add(new Label("Kullanıcı Adı/TC:"), 0, 0); grid.add(kullaniciTxt, 1, 0);
        grid.add(new Label("Şifre:"), 0, 1); grid.add(sifreTxt, 1, 1);
        grid.add(hataMesajiLbl, 0, 2, 2, 1);

        formKapsayici.getChildren().addAll(grid, btnGiris);

        btnGiris.setOnAction(e -> {
            String u = kullaniciTxt.getText();
            String p = sifreTxt.getText();
            hataMesajiLbl.setVisible(false);

            if (u.isEmpty() || p.isEmpty()) {
                hataMesajiLbl.setText("Tüm alanları doldurmanız zorunludur!");
                hataMesajiLbl.setVisible(true);
            } else {

                User user = OtelUygulama.getFacade().girisYap(u, p);

                if (user == null) {
                    hataMesajiLbl.setText("Hatalı kullanıcı adı veya şifre!");
                    hataMesajiLbl.setVisible(true);
                } else {
                    OtelUygulama.currentUser = user;
                    if ("Müşteri".equals(rol) && user.getRole().equals("MÜŞTERİ")) {
                        MusteriAnaMenu.goster();
                    } else if ("Personel".equals(rol) && user.getRole().equals("PERSONEL")) {
                        PersonelAnaMenu.goster();
                    } else {
                        hataMesajiLbl.setText("Seçtiğiniz rolden (" + rol + ") giriş yetkiniz bulunmamaktadır!");
                        hataMesajiLbl.setVisible(true);
                    }
                }
            }
        });

        return formKapsayici;
    }
}