# OTEL REZERVASYON VE MÜŞTERİ TAKİP SİSTEMİ
## 🌟 PROJE ÖZETİ

Bu uygulama, bir otelin rezervasyon, müşteri takibi ve operasyonel süreçlerini yönetmek üzere tasarlanmıştır. Proje, temiz ve esnek bir mimari sağlamak amacıyla **altı temel Tasarım Deseni** kullanılarak katmanlı ve Nesne Yönelimli Programlama (OOP) prensiplerine uygun olarak geliştirilmiştir.
Sistem, Müşteri ve Personel rolleri için tam yetkilendirme, dinamik oda durum yönetimi (State Pattern), ve tüm işlemlerin merkezileştirilmiş bir cephe (Facade Pattern) arkasından yürütülmesini sunar.

**Geliştirme Ortamı:** Java 21, JavaFX, IntelliJ IDEA
**Proje Tipi:** GUI Uygulaması 

---

## 👥 EKİP ARKADAŞLARI / KATILIMCILAR

Bu proje, aşağıdaki ekip üyeleri tarafından gerçekleştirilmiştir.

| Ad Soyad              | GitHub Profili |
|:----------------------|  :--- |
| **Hasan Berat Öztürk**      |  [GitHub Profili](https://github.com/hasanberatozturkk) |
| **Mete Kar** |  [GitHub Profili](https://github.com/1220505053) |
| **Güven Susam**       |  [GitHub Profili](https://github.com/guvensusam) |

---

## 📦 PROJE MİMARİSİ VE PAKETLER

Projemizin katmanlı mimarisi, her paketin belirli ve net sorumluluklar taşıdığını gösterir.

| Paket Adı | Sorumluluk Alanı | Uygulanan Ana Tasarım Desenleri |
| :--- | :--- | :--- |
| `com.example.otelbudur.singleton` | **Merkezi Veri Yönetimi** | Singleton |
| `com.example.otelbudur.facade` | **İş Mantığı ve İşlem Yönetimi** | Facade |
| `com.example.otelbudur.factory` | **Dinamik Nesne Yaratımı** | Factory, Abstract Class (Room) |
| `com.example.otelbudur.state` | **Oda Durum Yönetimi** | State |
| `com.example.otelbudur.observer` | **Olay Bildirimi ve Loglama** | Observer |
| `com.example.otelbudur.builder` | **Karmaşık Nesne Yapılandırması** | Builder |
| `com.example.otelbudur.domain` | **Temel Veri Modelleri** | Abstract Class (User), Kalıtım (Customer, Staff) |
| `com.example.otelbudur.ui` | **Kullanıcı Arayüzü (Presentation)** | GUI (JavaFX) |

---
## 🚀 MİMARİ ANALİZ: 6 TASARIM DESENİNİN DETAYLI İNCELEMESİ

Proje, yazılım mühendisliği problemlerini çözmek ve kod kalitesini artırmak amacıyla 6 temel tasarım desenini stratejik olarak kullanmıştır.

### I. Yapısal ve Yönetim Desenleri

#### 1. FACADE (Cephe) Deseni

* **Uygulama Sınıfı:** HotelSystemFacade.java
* **Mimarideki Rolü:** Sorumlulukların Net Ayrımı. Facade, Kullanıcı Arayüzü (UI) ile İş Mantığı (Business Logic) arasında bir aracı katman görevi görür.
* **Faydası:** Bu desen sayesinde, arayüzdeki kodlar, veri yönetimi (DataStore), bildirimler (NotificationService) veya durum geçişleri (State) gibi karmaşık arka plan süreçlerini bilmek zorunda kalmaz. Tüm işlemler, Facade'e yapılan basit bir metot çağrısı arkasına gizlenir, bu da kodun okunurluğunu ve sürdürülebilirliğini artırır.

#### 2. SINGLETON (Tek Nesne) Deseni
   
* **Uygulama Sınıfı:** DataStore.java
* **Mimarideki Rolü:** Veri Merkezi. Projeniz gerçek bir veritabanı yerine bellek içi (In-Memory) veri yapısı kullandığından, DataStore sınıfı bu merkezin tek bir örneği olarak var olur.
* **Faydası:** Uygulamanın neresinde olursa olsun, tüm modüller DataStore.getInstance() metodu ile aynı kullanıcı, oda ve rezervasyon listelerine erişir. Bu, verinin bütünlüğünü sağlar ve farklı yerlerden veri setinin kopyalanmasını veya yanlış yönetilmesini engeller.

### II. Yaratımsal Desenler

#### 3. FACTORY (Fabrika) Deseni

* **Uygulama Sınıfı:** RoomFactory.java
* **Mimarideki Rolü:** Esnek Nesne Üretimi. Farklı oda tiplerinin (StandardRoom, SuiteRoom, FamilyRoom) oluşturulması görevini merkezileştirir.
* **Faydası:** Personel, sadece istediği oda tipini (STANDART veya SÜİT) Fabrikaya bildirir. Fabrika, hangi somut sınıfın yaratılacağına karar verir. Bu, sisteme gelecekte yeni bir oda tipi eklendiğinde, var olan kodun değiştirilmesini gerektirmez (Açık/Kapalı Prensibi).

#### 4. BUILDER (Kurucu) Deseni
   
* **Uygulama Sınıfı:** Reservation.RezervasyonKurucu
* **Mimarideki Rolü:** Karmaşık Nesnelerin Güvenli Yapılandırması. Rezervasyon nesnesi (Müşteri, Oda, Giriş Tarihi, Çıkış Tarihi) gibi çok sayıda zorunlu ve opsiyonel parametreye sahiptir.
* **Faydası:** Builder deseni, nesnenin adım adım (metot zincirleme) oluşturulmasını sağlar. Bu yöntem, büyük bir kurucu (constructor) kullanmak yerine, her bir parametrenin doğru sırada ve güvenli bir şekilde atanmasını zorlar, hata yapma riskini en aza indirir.

### III. Davranışsal Desenler

#### 5. STATE (Durum) Deseni

* **Uygulama Sınıfı:** RoomState Arayüzü ve Alt Sınıfları
* **Mimarideki Rolü:** Dinamik Davranış Yönetimi. Odaların (MÜSAİT, REZERVE, DOLU) davranışını, iç durumlarına göre otomatik olarak değiştirmeyi sağlar.
* **Faydası:** Oda sınıfı içinde, hangi durumda hangi işlemin yapılacağını kontrol eden uzun if/else koşullarını kullanmaktan kaçınılır. Davranışlar (checkIn(), checkOut()), ilgili durum sınıfının sorumluluğuna devredilir. Bu, kodun sade ve genişletilebilir olmasını sağlar.

#### 6. OBSERVER (Gözlemci) Deseni

* **Uygulama Sınıfı:** NotificationService.java ve Observer.java
* **Mimarideki Rolü:** Olay Takibi ve Bildirim Sistemi. Sistemde önemli bir olay (Rezervasyon, Şifre Değiştirme) gerçekleştiğinde, bu olaydan etkilenen tüm modülleri (ConsoleLogger gibi) otomatik olarak bilgilendirir.
* **Faydası:** Gevşek Bağlanım (Loose Coupling) sağlar. İş mantığı (Facade), kimin veya kaç kişinin loglama yaptığını bilmek zorunda kalmaz; sadece notifyAll() çağrısı yaparak görevi tamamlar. Bu, bildirim sistemini ana iş akışından ayırır.

---
# 🛠️ KURULUM VE TEST

### A. Teknik Gereksinimler
* **Abstract Class:** `User.java` ve `Room.java` sınıfları ile gereksinim karşılanmıştır.
* **Veri Yönetimi:** Kalıcı bir veritabanı bağlantısı **kullanılmamıştır**. Tüm veriler, Singleton deseni ile yönetilen `DataStore` sınıfının belleğinde (In-Memory) tutulur.

### B. Varsayılan Giriş Bilgileri (Test Senaryoları)

| Rol | Kullanıcı Adı / T.C. | Şifre |
| :--- | :--- | :--- |
| **Personel** | `admin` | `123` |
| **Müşteri** | `12345678901` | `123` |

### C. Başlatma
1. Proje dosyalarını indirin.
2. `Main.java` sınıfını (veya `OtelUygulama.java` sınıfını) çalıştırın.
3. Uygulama, `DataStore` üzerinden önceden yüklenmiş varsayılan verilerle (test kullanıcıları ve odalar) başlayacaktır.
