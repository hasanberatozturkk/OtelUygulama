package com.example.otelbudur.ui;

import com.example.otelbudur.app.OtelUygulama;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class KayitEkrani {

    public static Scene olustur() {
        GridPane grid = new GridPane();
        grid.setHgap(15); // Aralıklar artırıldı
        grid.setVgap(15); // Aralıklar artırıldı
        grid.setPadding(new Insets(30));
        grid.setAlignment(Pos.CENTER);

        Label baslik = new Label("YENİ MÜŞTERİ KAYDI");
        baslik.setStyle("-fx-font: normal bold 20px 'Arial'; -fx-text-fill: " + OtelUygulama.VURGU_RENGI + ";");

        // Gerekli Müşteri Bilgileri
        final TextField adTxt = new TextField();
        final TextField soyadTxt = new TextField();
        final TextField tcTxt = new TextField();
        final TextField epostaTxt = new TextField();
        final TextField telefonTxt = new TextField();
        final PasswordField sifreTxt = new PasswordField();
        final PasswordField sifreTekrarTxt = new PasswordField();

        final Label kayitHataLbl = new Label();
        kayitHataLbl.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        kayitHataLbl.setVisible(false);

        Button btnKayit = new Button("KAYDI TAMAMLA");
        btnKayit.setMaxWidth(Double.MAX_VALUE);
        Button btnGeri = new Button("Giriş Ekranına Dön");

        // Form Elemanları
        grid.add(new Label("Adı:"), 0, 0); grid.add(adTxt, 1, 0);
        grid.add(new Label("Soyadı:"), 0, 1); grid.add(soyadTxt, 1, 1);
        grid.add(new Label("T.C. Kimlik No: *"), 0, 2); grid.add(tcTxt, 1, 2);
        grid.add(new Label("E-posta:"), 0, 3); grid.add(epostaTxt, 1, 3);
        grid.add(new Label("Telefon:"), 0, 4); grid.add(telefonTxt, 1, 4);
        grid.add(new Label("Şifre Oluştur: *"), 0, 5); grid.add(sifreTxt, 1, 5);
        grid.add(new Label("Şifre Tekrar: *"), 0, 6); grid.add(sifreTekrarTxt, 1, 6);

        VBox ana_duzen = new VBox(20);
        ana_duzen.setAlignment(Pos.CENTER);
        ana_duzen.setPadding(new Insets(30));
        ana_duzen.setStyle("-fx-background-color: " + OtelUygulama.ARKA_PLAN_GENEL + ";");

        ana_duzen.getChildren().addAll(baslik, grid, kayitHataLbl, btnKayit, btnGeri);
        btnKayit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 25; -fx-font-size: 14px;");
        btnGeri.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-padding: 10;");

        btnKayit.setOnAction(e -> {
            String ad = adTxt.getText();
            String soyad = soyadTxt.getText();
            String tc = tcTxt.getText();
            String sifre = sifreTxt.getText();
            String sifreTekrar = sifreTekrarTxt.getText();

            kayitHataLbl.setVisible(false);

            if (tc.isEmpty() || sifre.isEmpty() || sifreTekrar.isEmpty() || ad.isEmpty() || soyad.isEmpty()) {
                kayitHataLbl.setText("Ad, Soyad, T.C. No ve Şifre alanları boş bırakılamaz!");
                kayitHataLbl.setVisible(true);
            } else if (!sifre.equals(sifreTekrar)) {
                kayitHataLbl.setText("Şifreler birbiriyle eşleşmiyor.");
                kayitHataLbl.setVisible(true);
            } else {

                try {
                    OtelUygulama.getFacade().musteriKaydet(
                            ad + " " + soyad,
                            tc,
                            sifre
                    );
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Kayıt başarılı! Lütfen T.C. Kimlik numaranız ile giriş yapınız.");
                    alert.showAndWait();
                    OtelUygulama.giris_ekrani_goster();
                } catch (Exception ex) {
                    kayitHataLbl.setText("Kayıt sırasında bir hata oluştu: " + ex.getMessage());
                    kayitHataLbl.setVisible(true);
                }
            }
        });

        btnGeri.setOnAction(e -> OtelUygulama.giris_ekrani_goster());

        return new Scene(ana_duzen, 500, 650);
    }
}