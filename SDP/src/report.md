# Отчёт — Assignment 1: Builder Pattern (Computer Configuration)

## 1. Домен и вариант

- **Домен:** Computer Configuration (сборка ПК)
- **Индивидуальное ограничение:** если выбрана топовая видеокарта (RTX 4080 / 4090 / 5080 / 5090),
  блок питания (`psuWattage`) должен быть не менее 750W, и охлаждение (`coolingType`)
  не должно быть `NONE`.
- **Пресеты:** `BUDGET`, `GAMING`, `WORKSTATION` (реализованы в классе `Director`)

## 2. Part A — Наивная реализация (проблема)

Класс `LegacyComputerConf` создаёт объект через один конструктор с 10 параметрами подряд:

```java
new LegacyComputerConf("Intel Core i7-14700K", 850, 32,
    new StorageConfig(StorageType.SSD, 1000),
    CoolingType.LIQUID, "RTX 4080", "Intel", true, true, 5);
```

### Три конкретные проблемы

1. **Непонятный вызов.** Глядя на `true, true` в конце списка, невозможно понять,
   что первое `true` — это Wi-Fi, а второе — Bluetooth, не открыв класс.
2. **Нет проверок.** Ничто не мешает передать отрицательный `psuWattage` или
   несуществующий `gpu` — объект создастся в невалидном состоянии.
3. **Нет значений по умолчанию.** Даже если нужен самый обычный компьютер без
   специальных опций, всё равно приходится явно указывать все 10 значений.

## 3. Part B — Builder

`ComputerConfig` — неизменяемый (immutable) объект: все поля `final`, единственный
способ его создать — через вложенный класс `ComputerConfig.Builder`.

```java
ComputerConfig pc = new ComputerConfig.Builder(
        "Intel Core i7-14700K", 32, new StorageConfig(StorageType.SSD, 1000), 850)
        .withGpu("RTX 4080")
        .withCooling(CoolingType.LIQUID)
        .withWarrantyYears(3)
        .build();
```

**Участники паттерна:**

| Роль | Класс |
|---|---|
| Product | `ComputerConfig` |
| Builder | `ComputerConfig.Builder` (вложенный статический класс) |
| Client | `Main` |
| Director | `Director` |
| Value Object | `StorageConfig` |

**Почему Builder вложенный, а не отдельный класс:** вложенный класс имеет доступ к
приватным полям друг друга в пределах одного внешнего класса — это позволяет
`ComputerConfig` читать поля Builder'а (`b.cpu`, `b.gpu` и т.д.) напрямую, без
дополнительных публичных геттеров у Builder'а.

## 4. Part C — Валидация

Реализована в приватном методе `validate()` внутри `Builder`, вызывается из `build()`
до создания объекта.

**Single-field правила (проверяют одно поле):**
1. `cpu` не должен быть пустым
2. `ramGb` должен быть в диапазоне 4–256
3. `psuWattage` должен быть не менее 300

**Cross-field правила (индивидуальное ограничение, проверяют зависимость между полями):**
1. Топовая GPU ⇒ `psuWattage >= 750`
2. Топовая GPU ⇒ `coolingType != NONE`

При нарушении любого правила выбрасывается `InvalidConfigException` с конкретным
сообщением, например: `"GPU 'RTX 4090' requires PSU >= 750W, got: 500"`.

## 5. Part D — Пресеты (Director)

`Director` определяет три готовые, переиспользуемые конфигурации:

| Пресет | CPU | RAM | GPU | PSU | Особенность |
|---|---|---|---|---|---|
| `budget()` | i3-13100 | 8GB | нет | 400W | Самая дешёвая валидная сборка |
| `gaming()` | i7-14700K | 32GB | RTX 4080 | 850W | Задействует индивидуальное ограничение |
| `workstation()` | Threadripper 7960X | 128GB | RTX 4000 Ada | 1000W | Много ядер и памяти |

**Зачем нужен Director:** без него каждый раз, когда нужна "gaming"-сборка, пришлось
бы заново писать всю цепочку `.withGpu(...).withCooling(...)...`. Director хранит
эту логику в одном месте — вызывающий код просто пишет `Director.gaming()`.

## 6. Part E — Clean Code: до и после

### Пример 1 — вынесение констант вместо "магических чисел"

**До:** число `750` и список видеокарт были бы разбросаны прямо внутри `if`.

**После:**
```java
private static final java.util.Set<String> HIGH_END_GPUS =
        java.util.Set.of("RTX 4080", "RTX 4090", "RTX 5080", "RTX 5090");
private static final int HIGH_END_MIN_PSU = 750;
```
**Почему лучше:** если порог когда-то изменится (например, до 800W), достаточно
поменять одну строку, а не искать все места, где стояло число `750`.

### Пример 2 — понятные названия методов вместо общих сеттеров

**До (гипотетически):** `setCooling(CoolingType type, boolean flag)` — непонятно,
что означает `flag` на месте вызова.

**После:**
```java
public Builder withCooling(CoolingType coolingType) { this.coolingType = coolingType; return this; }
```
**Почему лучше:** имя метода само объясняет, что он делает, вызывающему коду не
нужно помнить порядок и смысл параметров.

### Пример 3 — явное сообщение об ошибке вместо общего исключения

**До (гипотетически):** `throw new RuntimeException("error");` — непонятно, что
именно пошло не так.

**После:**
```java
throw new InvalidConfigException(
        "GPU '" + gpu + "' requires PSU >= " + HIGH_END_MIN_PSU + "W, got: " + psuWattage);
```
**Почему лучше:** сообщение сразу называет конкретное значение и конкретный порог,
разработчику не нужно лезть в код, чтобы понять причину ошибки.

## 7. Part F — Обоснование дизайн-решения

**Решение:** валидация происходит в `Builder.validate()`, а не в конструкторе
`ComputerConfig`.

**Альтернатива:** можно было бы проверять значения прямо в конструкторе `ComputerConfig`.

**Почему выбран текущий вариант:** если бы `ComputerConfig` сам себя проверял, его
конструктор отвечал бы одновременно за две задачи — присваивание полей и проверку
бизнес-правил. Держа валидацию в `Builder`, конструктор `ComputerConfig` остаётся
простым присваиванием полей, а вся логика "что считается допустимой сборкой" (включая
наше индивидуальное ограничение про GPU/PSU/охлаждение) находится там же, где
пошагово собираются значения.

## 8. Part G — UML-диаграмма

См. `docs/builder-uml.png`.

| Элемент диаграммы | Файл |
|---|---|
| Client (`Main`) | `Main.java` |
| Director (`Director`) | `Director.java` |
| Builder (`ComputerConfig.Builder`) | вложен в `ComputerConfig.java` |
| Product (`ComputerConfig`) | `ComputerConfig.java` |
| Value Object (`StorageConfig`) | `StorageConfig.java` |
| Enum'ы | `StorageType.java`, `CoolingType.java` |
| Исключение | `InvalidConfigException.java` |

## 9. Part H — Тесты

`ComputerConfigTest.java` содержит 10 проверок (простой вариант через `main()`,
без подключения JUnit): успешные сборки через пресеты, невалидные сборки (должны
выбрасывать `InvalidConfigException`), граничные значения (RAM = 4, PSU = 750),
проверка индивидуального ограничения, и проверка, что GPU — необязательное поле.

## 10. Ссылка на GitHub-репозиторий

`<ВСТАВИТЬ ССЫЛКУ НА СВОЙ РЕПОЗИТОРИЙ>`
