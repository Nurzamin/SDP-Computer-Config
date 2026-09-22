# Assignment 1 — Builder Pattern: Computer Configuration

Индивидуальное задание по курсу *Software Design Patterns*.

- **Домен:** Computer Configuration (сборка ПК)
- **Индивидуальное ограничение:** топовая видеокарта (RTX 4080/4090/5080/5090)
  требует блок питания ≥750W и активное охлаждение (не `NONE`).
- **Пресеты:** `BUDGET`, `GAMING`, `WORKSTATION`

## Структура проекта

```
src/main/java/com/computerconfig/
├── LegacyComputerConf.java       — Part A: "плохой" конструктор (10 параметров)
├── ComputerConfig.java           — Part B/C: Product + вложенный Builder + валидация
├── Director.java                 — Part D: готовые пресеты (budget/gaming/workstation)
├── StorageConfig.java            — вложенный value object (тип диска + объём)
├── StorageType.java              — enum (SSD, HDD)
├── CoolingType.java               — enum (NONE, AIR, LIQUID)
├── InvalidConfigException.java   — своё исключение для невалидных конфигураций
├── Main.java                     — Client, демонстрация всех частей
└── ComputerConfigTest.java       — Part H: 10 тестов через простой main()

docs/
└── builder-uml.png               — Part G: UML-диаграмма классов

report.md                          — полный отчёт (проблема, решение, валидация,
                                      пресеты, Clean Code примеры, обоснование
                                      решения, тесты)
```

## Как запустить

Требуется JDK 17+.

```bash
javac -d out src/main/java/com/computerconfig/*.java
java -cp out com.computerconfig.Main
```

## Как запустить тесты

```bash
java -cp out com.computerconfig.ComputerConfigTest
```

Ожидаемый результат: `10 passed, 0 failed.`

Подробное объяснение всей логики — в [report.md](report.md).
