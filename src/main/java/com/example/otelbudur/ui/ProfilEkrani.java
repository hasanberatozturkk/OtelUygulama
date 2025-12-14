package com.example.otelbudur.ui;

import com.example.otelbudur.app.OtelUygulama;
import com.example.otelbudur.domain.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class ProfilEkrani extends VBox {

    private final User currentUser;

    public ProfilEkrani() {
        this.currentUser = OtelUygulama.currentUser;

        // VBox düzeni, tüm içeriği tutar (Mevcut, İletişim, Şifre)
        VBox icerikVBox = new VBox(25);
        icerikVBox.setPadding(new Insets(30));
        icerikVBox.setStyle("-fx-background-color: " + OtelUygulama.ARKA_PLAN_GENEL + ";");

        Label title = new Label("👤 Hesap Bilgilerini Yönet");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + OtelUygulama.BIRINCIL_RENGI + ";");

        // Tüm formları VBox'a ekle
        icerikVBox.getChildren().addAll(
                title,
                olusturProfilGosterim(),
                new Separator(),
                olusturProfilGuncellemeFormu(),
                new Separator(),
                olusturSifreDegistirmeFormu()
        );

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(icerikVBox);
        scrollPane.setFitToWidth(true); // İçeriğin genişliğe sığmasını sağlar
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Yatay kaydırmayı kapat

        this.getChildren().add(scrollPane);

    }

    // Mevcut temel bilgileri gösterir
    private VBox olusturProfilGosterim() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 15; -fx-background-color: white;");

        Label ad = new Label("Ad Soyad: " + currentUser.getFullName());
        Label tc = new Label("T.C. Kimlik / Kullanıcı Adı: " + currentUser.getUsername());
        Label email = new Label("E-posta: " + currentUser.getEmail());
        Label phone = new Label("Telefon: " + currentUser.getPhone());
        Label rol = new Label("Rol: " + (currentUser.getRole().equals("CUSTOMER") ? "Müşteri" : "Personel"));

        ad.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tc.setStyle("-fx-font-size: 14px;");
        rol.setStyle("-fx-font-size: 14px;");

        layout.getChildren().addAll(new Label("Mevcut Bilgileriniz:"), ad, tc, email, phone, rol);
        return layout;
    }

    // E-posta ve Telefon Güncelleme Formu
    private Region olusturProfilGuncellemeFormu() {
        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(15);
        grid.setPadding(new Insets(15));
        grid.setStyle("-fx-border-color: #0077b6; -fx-border-width: 1; -fx-padding: 15; -fx-background-color: #f7f9fa;");

        Label formTitle = new Label("İletişim Bilgilerini Güncelle");
        formTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0077b6;");

        final TextField txtEmail = new TextField(currentUser.getEmail() != null ? currentUser.getEmail() : "");
        txtEmail.setPromptText("Yeni E-posta Adresi");

        final TextField txtTelefon = new TextField(currentUser.getPhone() != null ? currentUser.getPhone() : "");
        txtTelefon.setPromptText("Yeni Telefon Numarası");

        final TextField txtAdSoyad = new TextField(currentUser.getFullName());
        txtAdSoyad.setPromptText("Ad Soyad");

        Button btnGuncelle = new Button("Kaydet");
        btnGuncelle.setMaxWidth(Double.MAX_VALUE);
        btnGuncelle.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        grid.add(formTitle, 0, 0, 2, 1);
        grid.add(new Label("Ad Soyad:"), 0, 1); grid.add(txtAdSoyad, 1, 1);
        grid.add(new Label("E-posta:"), 0, 2); grid.add(txtEmail, 1, 2);
        grid.add(new Label("Telefon:"), 0, 3); grid.add(txtTelefon, 1, 3);
        grid.add(btnGuncelle, 1, 4);

        btnGuncelle.setOnAction(e -> {
            String newAdSoyad = txtAdSoyad.getText();
            String newEmail = txtEmail.getText();
            String newPhone = txtTelefon.getText();

            if (newAdSoyad.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Tüm alanlar boş bırakılamaz.").show();
                return;
            }

            OtelUygulama.getFacade().profilBilgisiGuncelle(currentUser, newAdSoyad, newEmail, newPhone);

            new Alert(Alert.AlertType.INFORMATION, "Profil bilgileri başarıyla güncellendi.").show();

        });

        return grid;
    }

    // Şifre Değiştirme Formu
    private Region olusturSifreDegistirmeFormu() {
        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(15);
        grid.setPadding(new Insets(15));
        grid.setStyle("-fx-border-color: #F44336; -fx-border-width: 1; -fx-padding: 15; -fx-background-color: #fffafa;");

        Label formTitle = new Label("Şifre Değiştirme");
        formTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #F44336;");

        final PasswordField txtCurrentSifre = new PasswordField();
        txtCurrentSifre.setPromptText("Mevcut Şifre");

        final PasswordField txtYeniSifre = new PasswordField();
        txtYeniSifre.setPromptText("Yeni Şifre");

        final PasswordField txtYeniSifreTekrar = new PasswordField();
        txtYeniSifreTekrar.setPromptText("Yeni Şifre Tekrar");

        Button btnSifreDegistir = new Button("Şifreyi Değiştir");
        btnSifreDegistir.setMaxWidth(Double.MAX_VALUE);
        btnSifreDegistir.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold;");

        grid.add(formTitle, 0, 0, 2, 1);
        grid.add(new Label("Mevcut Şifre:"), 0, 1); grid.add(txtCurrentSifre, 1, 1);
        grid.add(new Label("Yeni Şifre:"), 0, 2); grid.add(txtYeniSifre, 1, 2);
        grid.add(new Label("Tekrar:"), 0, 3); grid.add(txtYeniSifreTekrar, 1, 3);
        grid.add(btnSifreDegistir, 1, 4);

        btnSifreDegistir.setOnAction(e -> {
            String current = txtCurrentSifre.getText();
            String yeni = txtYeniSifre.getText();
            String yeniTekrar = txtYeniSifreTekrar.getText();

            if (current.isEmpty() || yeni.isEmpty() || yeniTekrar.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Tüm şifre alanları doldurulmalıdır.").show();
                return;
            }

            if (!yeni.equals(yeniTekrar)) {
                new Alert(Alert.AlertType.WARNING, "Yeni şifreler eşleşmiyor.").show();
                return;
            }

            if (OtelUygulama.getFacade().sifreDegistir(currentUser, current, yeni)) {
                new Alert(Alert.AlertType.INFORMATION, "Şifreniz başarıyla güncellendi.").show();
                txtCurrentSifre.clear();
                txtYeniSifre.clear();
                txtYeniSifreTekrar.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Mevcut şifreniz hatalı. Şifre değiştirilemedi.").show();
            }
        });

        return grid;
    }

    private void arayuzuYenile() {
        this.getChildren().clear();
        this.getChildren().add(new ProfilEkrani());
    }

    public static Region olustur() {
        return new ProfilEkrani();
    }
}