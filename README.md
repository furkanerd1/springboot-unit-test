## Unit Test Explanation for OrderService

This document explains the structure and key concepts used in the `OrderServiceImplTest` class, focusing on the **Given-When-Then** testing structure and the annotations utilized.

## 1. **Given-When-Then Structure**
The **Given-When-Then** structure is widely used to make tests more readable and understandable. Here's an explanation of each part:
- **Given**: Describes the initial context or setup for the test. This is where you prepare necessary objects or mock dependencies.
- **When**: Describes the action or event being tested. In this part, a method or function is invoked.
- **Then**: Describes the expected outcome after the action. This is where assertions are used to verify if the behavior is as expected.

## 2. Basic JUnit Annotations
#### **@Test**
- Marks a method as a test method
- Methods with this annotation will be executed as tests

#### **@BeforeEach**
- Runs before each test method
- Used for test setup and preparations

#### **@AfterEach**
- Runs after each test method
- Used for cleanup operations
- Example: Closing database connections

#### **@BeforeAll**
- Runs once before all tests in the class
- Must be a static method
- Example: Setting up database connection

#### **@AfterAll**
- Runs once after all tests are completed
- Must be a static method
- Example: General cleanup operations

#### **@DisplayName**
- Defines a custom name for the test
- Appears in test reports
- Example: @DisplayName("Customer registration should succeed")

#### **@Disabled**
- Temporarily disables a test

## 3. Mocking And Assertions
- ``Mocking:`` Mock objects are used to simulate the behavior of real objects in a controlled way. This ensures that the test focuses on a specific function without involving complex external dependencies like databases or network calls.
- ``Assertions:`` Assertions such as assertEquals, assertNotNull, and assertTrue are used to verify the expected outcomes. These assertions help in determining whether the test has passed or failed.

```java
assertEquals(expected, actual)      // Checks if two values are equal
assertTrue(condition)              // Checks if condition is true
assertFalse(condition)             // Checks if condition is false
assertNull(object)                 // Checks if object is null
assertNotNull(object)              // Checks if object is not null
assertThrows(Exception.class, () -> {}) // Checks if code throws expected exception
```
## TÜRKÇE
## OrderService için Birim Testi Açıklaması
Bu belge, `OrderServiceImplTest` sınıfında kullanılan yapıyı ve anahtar kavramları açıklamaktadır. Özellikle Given-When-Then test yapısına ve kullanılan anotasyonlara odaklanılacaktır.

## 1. **Given-When-Then Yapısı**
**Given-When-Then** yapısı, testleri daha okunabilir ve anlaşılır hale getirmek için yaygın olarak kullanılır. Her bir bölümün açıklaması şu şekildedir:
- **Given**: Testin başlangıç durumu veya kurulumu tanımlar. Bu kısımda gerekli nesneler veya mock bağımlılıklar hazırlanır.
- **When**: Test edilen eylemi veya olayı tanımlar. Bu kısımda bir metod veya fonksiyon çağrılır.
- **Then**:Eylem sonrasında beklenen sonucu tanımlar. Bu kısımda davranışın beklenen şekilde olup olmadığı doğrulamak için assertions (doğrulamalar) kullanılır.

## 2. Temel JUnit Anotasyonları
#### **@Test**
- Bir metodu test metodu olarak işaretler.
- Bu anotasyona sahip metotlar, testler olarak çalıştırılacaktır.

#### **@BeforeEach**
- Her test metodundan önce çalıştırılır.
- Testin kurulumu ve hazırlıkları için kullanılır.

#### **@AfterEach**
- Her test metodundan sonra çalıştırılır.
- Temizlik işlemleri için kullanılır.
- Örnek: Veritabanı bağlantılarının kapatılması.

#### **@BeforeAll**
- Sınıftaki tüm testlerden önce bir kez çalıştırılır.
- Statik bir metod olması gerekir.
- Örnek: Veritabanı bağlantısının kurulması.

#### **@AfterAll**
- Tüm testler tamamlandıktan sonra bir kez çalıştırılır.
- Statik bir metod olması gerekir.
- Örnek: Genel temizlik işlemleri.

#### **@DisplayName**
- Test için özel bir ad tanımlar.
- Test raporlarında görünür.
- Örnek: @DisplayName("Müşteri kaydı başarılı olmalıdır")

#### **@Disabled**
- Bir testi geçici olarak devre dışı bırakır.

## 3. Mocking VE Assertions
- ``Mocking:``  Mock nesneleri, gerçek nesnelerin davranışını kontrol edilen bir şekilde taklit etmek için kullanılır. Bu, testin dış bağımlılıklar olmadan belirli bir işlevi doğrulamasını sağlar. Örneğin, veritabanı veya ağ istekleri gibi karmaşık dış bağımlılıklar test dışında bırakılır.
- ``Assertions:`` Assertions (doğrulamalar) kullanılarak, beklenen sonuçların doğruluğu kontrol edilir. Bu doğrulamalar, testin geçip geçmediğini belirler.

```java
assertEquals(expected, actual)      //İki değerin eşit olup olmadığını kontrol eder.
assertTrue(condition)              // Şartın doğru olup olmadığını kontrol eder.
assertFalse(condition)             //Şartın yanlış olup olmadığını kontrol eder.
assertNull(object)                 // Nesnenin null olup olmadığını kontrol eder.
assertNotNull(object)              // Nesnenin null olmadığını kontrol eder.
assertThrows(Exception.class, () -> {}) // Kodun beklenen bir istisna (exception) atıp atmadığını kontrol eder.
```
