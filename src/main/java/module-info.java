module com.example.otelbudur {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;


    // 1. Uygulama Giriş Noktası
    exports com.example.otelbudur.app;
    opens com.example.otelbudur.app to javafx.fxml;

    // 2. UI ve Controller Katmanı
    exports com.example.otelbudur.ui;
    opens com.example.otelbudur.ui to javafx.fxml;

    // 3. Veri Katmanı (DataStore)
    exports com.example.otelbudur.singleton;

    // 4. Domain Modelleri (User, Customer, Room vb.)
    // TableView'lerin bu sınıflardaki getter'lara erişebilmesi için javafx.base'e 'opens' veriyoruz.
    exports com.example.otelbudur.domain;
    opens com.example.otelbudur.domain to javafx.base;

    // 5. Tasarım Desenleri Katmanları
    exports com.example.otelbudur.facade;
    exports com.example.otelbudur.factory;
    exports com.example.otelbudur.state;
    exports com.example.otelbudur.observer;
    exports com.example.otelbudur.builder;

    // 6. Builder ve Room sınıfları
    opens com.example.otelbudur.builder to javafx.base;
    opens com.example.otelbudur.factory to javafx.base;
}