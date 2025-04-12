
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
