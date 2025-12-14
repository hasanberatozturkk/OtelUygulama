package com.example.otelbudur.ui;

import com.example.otelbudur.app.OtelUygulama;
import com.example.otelbudur.builder.Reservation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.List;

public class RezervasyonYonetimEkrani extends VBox {
    private final ListView<String> rezervasyonListView = new ListView<>();

    public RezervasyonYonetimEkrani() {
        setPadding(new Insets(20));
        setSpacing(15);
        setStyle("-fx-background-color: " + OtelUygulama.ARKA_PLAN_GENEL + ";");

        Label title = new Label("📅 Rezervasyon Yönetimi (Listeleme, Filtreleme, İptal)");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + OtelUygulama.BIRINCIL_RENGI + ";");

        // Filtreleme Alanı
        HBox filtrelemeBolumu = new HBox(15);
        filtrelemeBolumu.setAlignment(Pos.CENTER_LEFT);
        filtrelemeBolumu.setPadding(new Insets(10));
        filtrelemeBolumu.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 5;");

        TextField txtArama = new TextField();
        txtArama.setPromptText("Müşteri Adı/Oda No ile Ara");

        ComboBox<String> cmbDurum = new ComboBox<>();
        cmbDurum.getItems().addAll("TÜMÜ", "AKTİF", "GEÇMİŞ");
        cmbDurum.setValue("TÜMÜ");

        Button btnAra = new Button("🔍 Filtrele");
        btnAra.setStyle("-fx-background-color: " + OtelUygulama.VURGU_RENGI + "; -fx-text-fill: white;");

        filtrelemeBolumu.getChildren().addAll(
                new Label("Filtre:"), txtArama, cmbDurum, btnAra
        );

        Button btnIptalEt = new Button("❌ Seçili Rezervasyonu İptal Et");
        btnIptalEt.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold;");

        // Filtreleme
        btnAra.setOnAction(e -> refreshRezervasyonListesi(txtArama.getText(), cmbDurum.getValue()));

        // İptal Etme
        btnIptalEt.setOnAction(e -> {
            String selected = rezervasyonListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                // Rezervasyon ID'sini ayrıştırma
                int rezId = Integer.parseInt(selected.split("ID:")[1].split(" ")[0]);

                if (OtelUygulama.getFacade().rezervasyonIptalEt(rezId)) {
                    new Alert(Alert.AlertType.INFORMATION, "Rezervasyon ID: " + rezId + " başarıyla iptal edildi ve oda müsaitleştirildi.").show();
                    btnAra.fire(); // Listeyi yenilemek için filtreleme eylemini tetikle
                } else {
                    new Alert(Alert.AlertType.ERROR, "İptal sırasında hata oluştu.").show();
                }
            }
        });

        // Başlangıçta listeleme
        refreshRezervasyonListesi("", "TÜMÜ");

        getChildren().addAll(title, filtrelemeBolumu, rezervasyonListView, btnIptalEt);
    }

    private void refreshRezervasyonListesi(String aramaMetni, String durumFiltresi) {
        rezervasyonListView.getItems().clear();

        List<Reservation> rezervasyonlar;

        String filtreTipi;
        String filtreDegeri;

        // Arama Metnine Göre Filtre Tipi Belirleme
        if (aramaMetni != null && aramaMetni.matches("\\d+")) {
            filtreTipi = "ODA_NO";
            filtreDegeri = aramaMetni;
        } else {
            filtreTipi = "MUSTERI_ADI";
            filtreDegeri = aramaMetni;
        }

        // Durum Filtresi Uygulama
        if (durumFiltresi.equals("GEÇMİŞ")) {
            rezervasyonlar = OtelUygulama.getFacade().rezervasyonlariFiltrele("DURUM_GECMIS", filtreDegeri, OtelUygulama.currentUser);
        } else if (durumFiltresi.equals("AKTİF")) {
            // Aktif olanları filtrelemek için Facade'e yeni bir durum filtre tipi eklenmeli
            rezervasyonlar = OtelUygulama.getFacade().rezervasyonlariFiltrele("DURUM_AKTIF", filtreDegeri, OtelUygulama.currentUser);
        } else {
            // Tüm aktif/geçmiş durumları filtrelemeden getir
            rezervasyonlar = OtelUygulama.getFacade().rezervasyonlariFiltrele(filtreTipi, filtreDegeri, OtelUygulama.currentUser);
        }


        for (Reservation r : rezervasyonlar) {
            // Durum etiketi ve filtreleme mantığı
            String durumEtiketi = LocalDate.parse(r.getDateOut()).isBefore(LocalDate.now()) ? "[TAMAMLANDI]" : "[AKTİF]";

            // Liste elemanını oluşturma
            String bilgi = String.format("ID:%d %s | Oda #%d (%s) | Müşteri: %s | Giriş: %s | Çıkış: %s",
                    r.getId(),
                    durumEtiketi,
                    r.getRoom().getRoomNumber(),
                    r.getRoom().getType(),
                    r.getUser().getFullName(),
                    r.getDateIn(),
                    r.getDateOut());
            rezervasyonListView.getItems().add(bilgi);
        }
    }

    public static Region olustur() { return new RezervasyonYonetimEkrani(); }
}