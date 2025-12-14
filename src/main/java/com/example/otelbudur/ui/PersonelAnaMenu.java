package com.example.otelbudur.ui;

import com.example.otelbudur.app.OtelUygulama;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class PersonelAnaMenu {
    private static StackPane icerikAlani;

    public static void goster() {
        Scene personelMenuScene = olustur();
        OtelUygulama.sahne_degistir(personelMenuScene, OtelUygulama.GENISLIK, OtelUygulama.YUKSEKLIK);
    }

    private static Scene olustur() {
        BorderPane anaYerlesim = new BorderPane();

        icerikAlani = new StackPane();
        icerikAlani.setPadding(new Insets(20));
        icerikAlani.setStyle("-fx-background-color: white;");
        anaYerlesim.setCenter(icerikAlani);

        HBox ustPanel = new HBox(10);
        ustPanel.setPadding(new Insets(15));
        ustPanel.setAlignment(Pos.CENTER_LEFT);
        ustPanel.setStyle("-fx-background-color: " + OtelUygulama.VURGU_RENGI+ ";");

        Label baslik = new Label("PERSONEL YÖNETİM PANELİ");
        baslik.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button cikisButon = new Button("Çıkış Yap");
        cikisButon.setOnAction(e -> OtelUygulama.giris_ekrani_goster());

        Region bosluk = new Region();
        HBox.setHgrow(bosluk, Priority.ALWAYS);
        ustPanel.getChildren().addAll(baslik, bosluk, cikisButon);
        anaYerlesim.setTop(ustPanel);

        // Sidebar
        VBox solMenu = new VBox(15);
        solMenu.setPadding(new Insets(20));
        solMenu.setPrefWidth(250);
        solMenu.setStyle("-fx-background-color: #e0e0e0;");

        Button btnOda = new Button("🚪 Oda Yönetimi");
        Button btnMusteriYonetim = new Button("👤 Müşteri Yönetimi");
        Button btnRezervasyonYonetim = new Button("📅 Rezervasyon Yönetimi");

        btnOda.setMaxWidth(Double.MAX_VALUE);
        btnMusteriYonetim.setMaxWidth(Double.MAX_VALUE);
        btnRezervasyonYonetim.setMaxWidth(Double.MAX_VALUE);

        solMenu.getChildren().addAll(
                new Label("MODÜLLER"), new Separator(),
                btnOda, btnMusteriYonetim, btnRezervasyonYonetim
        );
        anaYerlesim.setLeft(solMenu);

        icerikAlani.getChildren().add(OdaYonetimEkrani.olustur());

        // Buton Eylemleri
        btnOda.setOnAction(e -> guncelleIcerik(OdaYonetimEkrani.olustur()));
        btnMusteriYonetim.setOnAction(e -> guncelleIcerik(MusteriYonetimEkrani.olustur()));
        btnRezervasyonYonetim.setOnAction(e -> guncelleIcerik(RezervasyonYonetimEkrani.olustur()));

        return new Scene(anaYerlesim);
    }

    private static void guncelleIcerik(Region view) {
        icerikAlani.getChildren().clear();
        icerikAlani.getChildren().add(view);
    }
}