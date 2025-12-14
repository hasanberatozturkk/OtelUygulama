package com.example.otelbudur.ui;

import com.example.otelbudur.domain.Customer;
import com.example.otelbudur.app.OtelUygulama;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;
import java.util.stream.Collectors;

public class MusteriYonetimEkrani extends VBox {

    private final ListView<String> musteriListView = new ListView<>();

    public MusteriYonetimEkrani() {
        setPadding(new Insets(20));
        setSpacing(15);
        setStyle("-fx-background-color: white;");

        Label title = new Label("👤 Müşteri Yönetimi (Tüm Kayıtlı Müşteriler)");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + OtelUygulama.BIRINCIL_RENGI + ";");

        refreshMusteriListesi();

        Button btnMusteriDetay = new Button("👁️ Seçili Müşterinin Rezervasyon/Geçmiş Konaklamasını Görüntüle");
        btnMusteriDetay.setStyle("-fx-background-color: #0077b6; -fx-text-fill: white;");

        btnMusteriDetay.setOnAction(e -> {
            String selected = musteriListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                new Alert(Alert.AlertType.INFORMATION, "Detay Görüntüleme:\n" + selected + "\nRezervasyon/Geçmiş Konaklama Detayları Modülü henüz tamamlanmadı.").show();
            }
        });

        getChildren().addAll(title, musteriListView, btnMusteriDetay);
    }

    private void refreshMusteriListesi() {
        List<Customer> customers = OtelUygulama.getFacade().tumMusterileriGetir();

        List<String> customerStrings = customers.stream()
                .map(c -> "TC: " + c.getUsername() + " | Adı: " + c.getFullName() + " | Rol: " + c.getRole())
                .collect(Collectors.toList());

        musteriListView.getItems().setAll(customerStrings);
    }

    public static Region olustur() {
        return new MusteriYonetimEkrani();
    }
}