# jUnit

Собственная реализация тестового раннера, имитирующего поведение JUnit.

## Описание

Проект содержит собственный `TestRunner`, который находит и запускает тестовые методы с помощью рефлексии, поддерживая аннотации:

- `@Test` — помечает метод как тестовый
- `@BeforeEach` — метод, запускаемый перед каждым тестом
- `@AfterEach` — метод, запускаемый после каждого теста
- `@Skip` — пропускает тест при запуске

## Запуск

Проект собран с помощью Maven (Java 17):

```bash
mvn compile
java -cp target/classes tasks.Main
```

## Структура

```
src/main/java/tasks/
├── Main.java              # Точка входа
├── TestRunner.java         # Раннер с рефлексией
├── DemoTest.java           # Демонстрационный тест
└── annotations/
    ├── Test.java
    ├── BeforeEach.java
    ├── AfterEach.java
    └── Skip.java
```
