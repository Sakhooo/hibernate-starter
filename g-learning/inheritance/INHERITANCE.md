
# Hibernate Mapping Inheritance: @MappedSuperclass

## 📘 Что я изучаю

Сейчас изучаю тему **наследования в Hibernate**.  
Видео — про `@MappedSuperclass` ("помеченный суперкласс").

---

## 🧠 Что я понял:

- `@MappedSuperclass` — это аннотация, которая говорит Hibernate:  
  👉 "Этот класс содержит общие поля для других сущностей, **но сам не будет отображён как таблица в БД**"

- Поля этого класса **наследуются** другими `@Entity`, и **будут отображены в их таблицах**

---

## 📌 Пример:

```java
@MappedSuperclass
public class BaseEntity {
    @Id
    private Long id;

    private LocalDateTime createdAt;
}
```
# Hibernate Inheritance: SINGLE_TABLE

## 📘 Что я изучаю

Изучаю тему **наследования в Hibernate**, а именно стратегию `SINGLE_TABLE`.

---

## 🧠 Что понял:

- **`SINGLE_TABLE`** — это стратегия наследования в Hibernate, при которой все сущности (классы) из иерархии **отображаются в одну таблицу** в базе данных.
- Каждый класс в иерархии наследует все поля родительского класса, но для разных типов данных в таблице будут **дублироваться поля** (например, для одного класса будут `NULL` поля, не относящиеся к этому классу).

---

## 📌 Пример:

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public abstract class User {
    @Id
    private Long id;

    private String name;
}
```
## ⚖️ Плюсы и минусы стратегии SINGLE_TABLE

### ❌ Минусы:
1. **NOT NULL не получится на специфичные поля**  
   Например, `language` у `Manager` будет всегда `NULL`, и наоборот — `teamSize` у `Programmer`.  
   Поэтому нельзя сделать `NOT NULL`, иначе ошибка.

2. **Слабая селективность**  
   Когда ты ищешь только `Programmer`, Hibernate делает `SELECT * FROM user WHERE user_type = 'Programmer'` —  
   но таблица может быть большой и содержать данные всех типов.

---

### ✅ Плюсы:
1. **Один `SELECT` — одна таблица**  
   Очень просто: запрос — и все данные в одной таблице. Быстро и удобно.

2. **Можно использовать `IDENTITY`**  
   Одна таблица — один счётчик ID, можно использовать `GenerationType.IDENTITY`.

---

## 🧾 Итоги:

`SINGLE_TABLE`:
- ✅ Простая, быстрая, часто используемая
- ❌ Денормализованная структура — много `NULL`-полей
- ⚠️ Подходит, когда классы **похожи**, и различий немного





# Hibernate Inheritance: JOINED

## 📘 Что я изучаю

Продолжаю изучать **наследование в Hibernate**, и сейчас тема — стратегия `JOINED`.

---

## 🧠 Что понял:

- **`JOINED`** — это стратегия, при которой **у каждой сущности в иерархии будет своя таблица** в базе данных.
- Таблица родителя содержит общие поля, а таблицы наследников — только специфические.
- При запросе Hibernate делает **`JOIN` по `id`** между таблицами, чтобы собрать объект.

---

## 📌 Пример:

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    private Long id;

    private String name;
}

@Entity
public class Manager extends User {
    private String department;
}

@Entity
public class Programmer extends User {
    private String language;
}
```
⚖️ Плюсы и минусы стратегии JOINED
✅ Плюсы:
Нормализованная структура
Нет NULL-полей для неиспользуемых столбцов.

Гибкость в работе с БД
Можно накладывать ограничения, индексы, связи на таблицы наследников.

Подходит для сложных и разных сущностей
Когда наследники имеют много уникальных полей.

❌ Минусы:
Удаление требует две таблицы
Чтобы удалить, например, Programmer, Hibernate должен удалить строку и из таблицы programmer, и из user.

Сложнее делать SELECT для конкретного типа
Чтобы получить, например, Programmer, нужен INNER JOIN между user и programmer.

Получить всех User — нужен LEFT OUTER JOIN
Hibernate делает LEFT JOIN ко всем таблицам-наследникам, чтобы собрать полную выборку всех пользователей.

🧾 Итоги:
Если важна нормализация и строгая структура в БД — стоит выбирать JOINED.

Если такой необходимости нет и хочется простоты и быстродействия — подойдёт SINGLE_TABLE.

🧠 Запомни:
JOINED = нормализация и структура
SINGLE = простота и скорость
