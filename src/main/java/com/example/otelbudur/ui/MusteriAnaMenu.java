package com.example.otelbudur.ui;

import com.example.otelbudur.app.OtelUygulama;
import com.example.otelbudur.ui.OdaAramaEkrani;
import com.example.otelbudur.ui.RezervasyonYonetimEkrani;
import com.example.otelbudur.ui.ProfilEkrani;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.Priority;

public class MusteriAnaMenu {

    private static StackPane icerikAlani;
    private static Button sonTiklananButon = null;

    public static void goster() {
        Scene musteriMenuScene = olustur();
        OtelUygulama.sahne_degistir(musteriMenuScene, OtelUygulama.GENISLIK, 850);
    }

    private static Scene olustur() {
        BorderPane anaYerlesim = new BorderPane();

        icerikAlani = new StackPane();
        icerikAlani.setPadding(new Insets(20));
        icerikAlani.setStyle("-fx-background-color: " + OtelUygulama.ARKA_PLAN_GENEL + ";");
        anaYerlesim.setCenter(icerikAlani);

        // Header
        HBox ustPanel = new HBox(20);
        ustPanel.setPadding(new Insets(15));
        ustPanel.setAlignment(Pos.CENTER_LEFT);
        ustPanel.setStyle("-fx-background-color: " + OtelUygulama.BIRINCIL_RENGI + ";");

        Label baslik = new Label("🏠 MÜŞTERİ PANELİ | HOŞ GELDİNİZ, " + OtelUygulama.currentUser.getFullName().toUpperCase());
        baslik.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button cikisButon = new Button("❌ Çıkış Yap");
        cikisButon.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        cikisButon.setOnAction(e -> OtelUygulama.giris_ekrani_goster());

        Region genisletici = new Region();
        HBox.setHgrow(genisletici, Priority.ALWAYS);
        ustPanel.getChildren().addAll(baslik, genisletici, cikisButon);
        anaYerlesim.setTop(ustPanel);

        // Sidebar
        VBox solMenu = new VBox(20);
        solMenu.setPadding(new Insets(20));
        solMenu.setPrefWidth(280);
        solMenu.setStyle("-fx-background-color: #ffffff;");

        Label menuBaslik = new Label("İŞLEMLER");
        menuBaslik.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + OtelUygulama.BIRINCIL_RENGI + ";");

        Button btnOdaAra = new Button("🔍 Oda Ara ve Rezervasyon Yap");
        Button btnRezervasyonlarim = new Button("📅 Rezervasyonlarım ve Geçmişim");
        Button btnProfil = new Button("👤 Profil Bilgilerimi Yönet");

        String menuStyle = "-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14px; -fx-alignment: center-left; -fx-padding: 10 15;";
        btnOdaAra.setStyle(menuStyle);
        btnRezervasyonlarim.setStyle(menuStyle);
        btnProfil.setStyle(menuStyle);

        btnOdaAra.setMaxWidth(Double.MAX_VALUE);
        btnRezervasyonlarim.setMaxWidth(Double.MAX_VALUE);
        btnProfil.setMaxWidth(Double.MAX_VALUE);

        solMenu.getChildren().addAll(menuBaslik, new Separator(), btnOdaAra, btnRezervasyonlarim, new Separator(), btnProfil);
        anaYerlesim.setLeft(solMenu);

        // Buton Vurgulama Fonksiyonu
        btnOdaAra.setOnAction(e -> guncelleIcerik(btnOdaAra, OdaAramaEkrani.olustur()));

        btnRezervasyonlarim.setOnAction(e -> guncelleIcerik(btnRezervasyonlarim, RezervasyonYonetimEkrani.olustur()));

        btnProfil.setOnAction(e -> guncelleIcerik(btnProfil, ProfilEkrani.olustur()));

        btnOdaAra.fire();

        return new Scene(anaYerlesim);
    }


    private static void guncelleIcerik(Button aktifButon, Region view) {
        // Önceki butonu sıfırla
        if (sonTiklananButon != null) {
            sonTiklananButon.setStyle("-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14px; -fx-alignment: center-left; -fx-padding: 10 15;");
        }

        // Yeni butonu vurgula
        aktifButon.setStyle("-fx-background-color: " + OtelUygulama.VURGU_RENGI + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center-left; -fx-padding: 10 15; -fx-border-radius: 5;");
        sonTiklananButon = aktifButon;

        icerikAlani.getChildren().clear();
        icerikAlani.getChildren().add(view);
    }
}