# Yookassa Java Library

Данная библиотека предоставляет удобный способ взаимодействия с API ЮKassa (YooKassa) для приема и обработки платежей в Java-приложениях.

## Возможности библиотеки

- **Создание платежей** (с разными способами оплаты, одноразовых или повторных).
- **Подтверждение платежей** (capture).
- **Получение информации о платежах** (как об отдельном платеже, так и о списке платежей).
- **Создание и получение возвратов (refund)**.
- **Создание, получение и удаление вебхуков** для получения уведомлений о статусе платежей.

## Структура проекта

В репозитории представлены следующие пакеты и классы:

- `yookassa.event`: Содержит `YookassaEvent` - enum для типов событий, на которые можно подписаться с помощью вебхуков.
- `yookassa.exception`: Определены классы исключений (`BadRequestException`, `UnspecifiedShopInformation`) для обработки ошибок при работе с API.
- `yookassa.implementation`: Содержит `RealYookassa` - реальную реализацию интерфейса `Yookassa` для взаимодействия с API YooKassa.
- `yookassa.model`: Модельные классы для платежей, возвратов, вебхуков и вспомогательных структур данных (таких как суммы платежа `Amount` или списки `PaymentList`, `RefundList`, `WebhookList`).
- `yookassa.model.request`: Классы для создания запросов к API (например, `PaymentRequest`, `RefundRequest`, `WebhookRequest`).
- `yookassa.utility`: Вспомогательный класс `JsonUtil` для (де)сериализации объектов в/из JSON.

Если вы не используете систему управления зависимостями, вы можете самостоятельно собрать JAR-файл проекта и подключить его к вашему приложению вручную (поместив файл в ваш classpath).

## Инициализация

```java
import com.anastasiaeverstova.myeduserver.yookassa.Yookassa;

int shopIdentifier = 123456;
String shopToken = "test_vXHHgh..."; // Ваш секретный токен из личного кабинета YooKassa

Yookassa yookassa = Yookassa.initialize(shopIdentifier, shopToken);
```
## Создание платежа

```java
import com.anastasiaeverstova.myeduserver.yookassa.model.Amount;
import com.anastasiaeverstova.myeduserver.yookassa.model.Payment;
import java.math.BigDecimal;

Amount amount = new Amount(new BigDecimal("100.00"), "RUB");
String description = "Оплата заказа №123";
String redirectUrl = "https://example.com/return";

try {
    Payment payment = yookassa.createPayment(amount, description, redirectUrl);
    System.out.println("ID платежа: " + payment.getId());
    System.out.println("Статус платежа: " + payment.status);
} catch (Exception e) {
    e.printStackTrace();
}
```
## Подтверждение (capture) платежа

```java
import java.util.UUID;

UUID paymentId = UUID.fromString("216749da-000f-50be-b000-096747fad91e");
Amount captureAmount = new Amount(new BigDecimal("100.00"), "RUB");
String idempotenceKey = UUID.randomUUID().toString();

try {
    Payment capturedPayment = yookassa.capturePayment(paymentId, captureAmount, idempotenceKey);
    System.out.println("Платеж подтвержден: " + capturedPayment.paid);
} catch (Exception e) {
    e.printStackTrace();
}
```
## Создание вебхука

```java
import com.anastasiaeverstova.myeduserver.yookassa.event.YookassaEvent;
import com.anastasiaeverstova.myeduserver.yookassa.model.Webhook;

try {
    Webhook webhook = yookassa.createWebhook(YookassaEvent.PAYMENT_SUCCESS_PAID, "https://example.com/webhook");
    System.out.println("Webhook создан с ID: " + webhook.getId());
} catch (Exception e) {
    e.printStackTrace();
}
```
## Обработка исключений

- **UnspecifiedShopInformation**: Отсутствует идентификатор магазина или токен.
- **BadRequestException**: Неверный запрос или ошибка, возвращенная API.
- **IOException**: Сетевые проблемы или ошибки ввода/вывода.

## Ссылки и документация

- [Документация YooKassa API](https://yookassa.ru/developers)
- [Примеры запросов](https://yookassa.ru/developers/api)
