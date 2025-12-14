package com.example.otelbudur.ui;

import com.example.otelbudur.app.OtelUygulama;
import com.example.otelbudur.singleton.DataStore; // Yeni DataStore paketini import ettik
import com.example.otelbudur.factory.Room;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class OdaYonetimEkrani {

    public static Region olustur() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        Label title = new Label("🚪 Oda Yönetim Paneli");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + OtelUygulama.BIRINCIL_RENGI + ";");

        // Oda Listesi
        ListView<String> odaListesi = new ListView<>();
        odaListesiniYenile(odaListesi);

        // Oda Ekleme Bölümü
        HBox eklemeBolumu = new HBox(10);
        TextField txtOdaNum = new TextField();
        txtOdaNum.setPromptText("Oda No (Örn: 501)");

        ComboBox<String> cmbTip = new ComboBox<>();
        cmbTip.getItems().addAll("STANDART", "SÜİT", "AİLE");
        cmbTip.setValue("STANDART");

        Button btnEkle = new Button("➕ Oda Ekle");
        btnEkle.setStyle("-fx-background-color: #0077b6; -fx-text-fill: white;");
        eklemeBolumu.getChildren().addAll(txtOdaNum, cmbTip, btnEkle);

        // İşlem Butonları (Check-In / Check-Out)
        HBox islemButonlari = new HBox(10);
        Button btnCheckIn = new Button("🔑 Check-In Yap");
        btnCheckIn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        Button btnCheckOut = new Button("🚪 Check-Out Yap");
        btnCheckOut.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        islemButonlari.getChildren().addAll(btnCheckIn, btnCheckOut);

        // --- OLAY YÖNETİCİLERİ ---

        btnEkle.setOnAction(e -> {
            try {
                int num = Integer.parseInt(txtOdaNum.getText());
                OtelUygulama.getFacade().odaEkle(cmbTip.getValue(), num);
                odaListesiniYenile(odaListesi);
                txtOdaNum.clear();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Geçersiz oda numarası!").show();
            }
        });

        btnCheckIn.setOnAction(e -> {
            int seciliNo = getSelectedRoomNumber(odaListesi);
            if (seciliNo != -1) {
                OtelUygulama.getFacade().checkInYap(seciliNo);
                odaListesiniYenile(odaListesi);
            }
        });

        btnCheckOut.setOnAction(e -> {
            int seciliNo = getSelectedRoomNumber(odaListesi);
            if (seciliNo != -1) {
                OtelUygulama.getFacade().checkOutYap(seciliNo);
                odaListesiniYenile(odaListesi);
            }
        });

        layout.getChildren().addAll(title, odaListesi, new Separator(),
                new Label("Yeni Oda Ekle:"), eklemeBolumu,
                new Separator(), new Label("Hızlı İşlemler:"), islemButonlari);

        return layout;
    }

    private static void odaListesiniYenile(ListView<String> list) {
        list.getItems().clear();
        List<Room> odalar = DataStore.getInstance().getOdalar();

        for (Room r : odalar) {
            String listItem = String.format("Oda #%d [%s] - Durum: %s (Kapasite: %d)",
                    r.getRoomNumber(),
                    r.getType(),
                    r.getState().getStatus(),
                    r.getCapacity());
            list.getItems().add(listItem);
        }
    }

    // Listeden seçilen oda numarasını parse eden yardımcı metod
    private static int getSelectedRoomNumber(ListView<String> list) {
        String selected = list.getSelectionModel().getSelectedItem();
        if (selected == null) return -1;
        try {
            return Integer.parseInt(selected.split("#")[1].split(" ")[0]);
        } catch (Exception e) {
            return -1;
        }
    }
}