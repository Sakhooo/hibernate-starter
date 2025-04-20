# Hibernate Query Language (HQL)

## 📘 Что я изучаю

Изучаю тему **HQL-запросов в Hibernate**, а именно использование `JOIN` и **именованных параметров**.

---

## 🧠 Что я понял:

- HQL (Hibernate Query Language) — это язык запросов, похожий на SQL, но работает с **Java-сущностями**, а не с таблицами напрямую.
- Можно использовать `JOIN`, чтобы связывать сущности, и `WHERE`, чтобы фильтровать.
- Именованные параметры (`:param`) понятнее и безопаснее, чем позиционные (`?1`).

---

## 📌 Пример HQL-запроса

```hql
select u 
from User u 
join u.company c 
where u.personalInfo.firstname = :firstname 
  and c.name = :companyName
```


1) User — сущность с вложенным объектом personalInfo (@Embeddable) и связью с Company.
2) u.personalInfo.firstname — путь к полю внутри Embeddable-объекта.
3) join u.company c — связывает сущность User с Company.
4) .setParameter(...) — подставляет значения в запрос по имени параметра.





