package com.example.otelbudur.ui;

import com.example.otelbudur.app.OtelUygulama;
import com.example.otelbudur.factory.Room;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;

public class OdaAramaEkrani {

    public static Region olustur() {
        VBox layout = new VBox(25);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0.0, 0, 0);");

        Label title = new Label("📅 Oda Arama ve Rezervasyon");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + OtelUygulama.BIRINCIL_RENGI + ";");

        // --- 1. ARAMA FORMU ---
        GridPane aramaFormu = new GridPane();
        aramaFormu.setHgap(20); aramaFormu.setVgap(15);
        aramaFormu.setPadding(new Insets(20));
        aramaFormu.setStyle("-fx-background-color: #f8f8f8; -fx-border-radius: 8; -fx-background-radius: 8;");

        // Input Alanları
        // Başlangıç tarihlerinin geçerli olması için varsayılan değerler atanır
        DatePicker checkInDate = new DatePicker(LocalDate.now());
        checkInDate.setPromptText("Giriş Tarihi");
        DatePicker checkOutDate = new DatePicker(LocalDate.now().plusDays(1));
        checkOutDate.setPromptText("Çıkış Tarihi");

        TextField txtKisiSayisi = new TextField("2");
        txtKisiSayisi.setPromptText("Kişi Sayısı");

        ComboBox<String> cmbOdaTipi = new ComboBox<>();
        cmbOdaTipi.getItems().addAll("TÜMÜ", "STANDART", "SÜİT", "AİLE");
        cmbOdaTipi.setValue("TÜMÜ");

        Button btnAra = new Button("🔍 Odaları Ara");
        btnAra.setStyle("-fx-background-color: " + OtelUygulama.VURGU_RENGI + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 5;");

        aramaFormu.add(new Label("Giriş:"), 0, 0); aramaFormu.add(checkInDate, 1, 0);
        aramaFormu.add(new Label("Çıkış:"), 2, 0); aramaFormu.add(checkOutDate, 3, 0);
        aramaFormu.add(new Label("Kişi Sayısı:"), 0, 1); aramaFormu.add(txtKisiSayisi, 1, 1);
        aramaFormu.add(new Label("Oda Tipi:"), 2, 1); aramaFormu.add(cmbOdaTipi, 3, 1);
        aramaFormu.add(btnAra, 3, 2);

        // LİSTELEME ALANI
        ListView<Room> odaListView = new ListView<>();
        odaListView.setPlaceholder(new Label("Lütfen arama yapın veya tüm odaları görmek için 'Ara' butonuna basın."));
        odaListView.setCellFactory(param -> new OdaListesiHucresi());

        // REZERVASYON BUTONU
        Button btnRezervasyonYap = new Button("✅ Seçili Odayı Rezerve Et");
        btnRezervasyonYap.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 16px; -fx-background-radius: 5;");
        btnRezervasyonYap.setDisable(true);

        // Filtreleme Fonksiyonu (CRUD R)
        btnAra.setOnAction(e -> {
            odaListView.getItems().clear();

            String secilenTip = cmbOdaTipi.getValue();
            int kisiSayisi = 0;
            LocalDate girisTarihi = checkInDate.getValue();
            LocalDate cikisTarihi = checkOutDate.getValue();

            if (girisTarihi == null || cikisTarihi == null || girisTarihi.isAfter(cikisTarihi)) {
                new Alert(Alert.AlertType.ERROR, "Lütfen geçerli bir giriş/çıkış tarihi aralığı seçiniz.").show();
                return;
            }

            try {
                kisiSayisi = Integer.parseInt(txtKisiSayisi.getText());
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Lütfen geçerli bir kişi sayısı giriniz.").show();
                return;
            }

            // Facade metodu çağrılıyor
            List<Room> filtrelenmisOdalar = OtelUygulama.getFacade().musaitOdalarıFiltrele(
                    secilenTip,
                    kisiSayisi,
                    girisTarihi,
                    cikisTarihi
            );

            odaListView.getItems().addAll(filtrelenmisOdalar);

            if (!odaListView.getItems().isEmpty()) {
                btnRezervasyonYap.setDisable(false);
            } else {
                btnRezervasyonYap.setDisable(true);
                new Alert(Alert.AlertType.INFORMATION, "Seçtiğiniz kriterlere uygun müsait oda bulunamadı.").show();
            }
        });

        odaListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            btnRezervasyonYap.setDisable(newV == null);
        });

        // Rezervasyon Fonksiyonu (CRUD C)
        btnRezervasyonYap.setOnAction(e -> {
            Room seciliOda = odaListView.getSelectionModel().getSelectedItem();
            LocalDate giris = checkInDate.getValue();
            LocalDate cikis = checkOutDate.getValue();

            if (seciliOda == null || giris == null || cikis == null || giris.isAfter(cikis) || giris.isBefore(LocalDate.now())) {
                new Alert(Alert.AlertType.WARNING, "Lütfen geçerli bir oda ve giriş/çıkış tarihi seçiniz.").show();
                return;
            }

            try {
                OtelUygulama.getFacade().rezervasyonYap(
                        OtelUygulama.currentUser,
                        seciliOda.getRoomNumber(),
                        giris.toString(),
                        cikis.toString()
                );
                new Alert(Alert.AlertType.INFORMATION, "Rezervasyonunuz başarıyla oluşturuldu!").show();
                btnAra.fire(); // Listeyi yenilemek için arama eylemini tekrar tetikle
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Rezervasyon Hatası: " + ex.getMessage()).show();
            }
        });

        // Başlangıçta tüm odaları listele
        // Bu fire() metodu, başlangıçtaki varsayılan tarih ve kişi sayısı değerleriyle arama yapacaktır.
        btnAra.fire();

        layout.getChildren().addAll(title, aramaFormu, new Separator(), new Label("Müsait Odalar (Seçim Yapın):"), odaListView, btnRezervasyonYap);
        return layout;
    }

    // --- Özel ListView Hücresi ---
    static class OdaListesiHucresi extends ListCell<Room> {
        private final VBox container = new VBox(5);
        private final Label lblNumara = new Label();
        private final Label lblTip = new Label();
        private final Label lblFiyat = new Label();
        private final Label lblKapasite = new Label();

        public OdaListesiHucresi() {
            container.setPadding(new Insets(10));
            container.setStyle("-fx-border-color: #ccc; -fx-border-width: 0 0 1 0; -fx-background-color: white;");

            lblNumara.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: " + OtelUygulama.BIRINCIL_RENGI + ";");
            lblTip.setStyle("-fx-font-weight: bold;");
            lblFiyat.setStyle("-fx-text-fill: #4CAF50;");

            container.getChildren().addAll(lblNumara, lblTip, lblKapasite, lblFiyat);
        }

        @Override
        protected void updateItem(Room item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                lblNumara.setText("Oda Numarası: #" + item.getRoomNumber() + " (Durum: " + item.getState().getStatus() + ")");
                lblTip.setText("Tipi: " + item.getType());
                lblKapasite.setText("Max Kişi Sayısı: " + item.getCapacity());
                lblFiyat.setText("Gecelik Fiyat: " + String.format("%.2f", item.getPrice()) + " TL");
                setGraphic(container);
            }
        }
    }
}