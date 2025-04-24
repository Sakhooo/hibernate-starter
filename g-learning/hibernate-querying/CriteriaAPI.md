# Hibernate Criteria API — мини-конспект

> 📅 Начал изучение: апрель 2025  
> 🧠 Цель: научиться писать типобезопасные запросы через Criteria API

---

## 🧩 Основные компоненты

- **CriteriaBuilder** — строитель запросов, получаем через `entityManager.getCriteriaBuilder()`
- **CriteriaQuery<T>** — сам запрос, в котором указывается тип результата
- **Root<T>** — корень запроса, указывает таблицу (например, `User.class`)

---

## 🔍 Простой SELECT

```java
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<User> query = cb.createQuery(User.class);
Root<User> root = query.from(User.class);
query.select(root);
```

---

## 📌 WHERE-условия

```java
query.where(cb.equal(root.get("name"), "Sagadat"));
```

Другие условия:
- `cb.like(...)` — шаблон (например, `%gmail.com`)
- `cb.gt(...)`, `cb.lt(...)` — больше / меньше
- `cb.and(...)`, `cb.or(...)` — логика
- `cb.isNull(...)`, `cb.isNotNull(...)`

---

## 🔗 JOIN

```java
Join<User, Role> join = root.join("roles");
query.where(cb.equal(join.get("name"), "ADMIN"));
```

---

## 📊 ORDER BY

```java
query.orderBy(cb.asc(root.get("createdAt")));
```

---

## 🔄 Пример со сложными условиями

```java
Predicate p1 = cb.like(root.get("email"), "%gmail.com");
Predicate p2 = cb.greaterThan(root.get("age"), 18);
query.where(cb.and(p1, p2));
```

---

## 🧪 Получение результата

```java
List<User> result = em.createQuery(query).getResultList();
```

---

## 🧷 Tuple / Конструктор запрос

Если нужно получить DTO, можно использовать `cb.construct(...)`:

```java
criteria.select(
    cb.construct(CompanyDto.class,
        company.get(Company_.name),
        cb.avg(payment.get(Payment_.amount))
    )
);
```

Это позволяет напрямую маппить результат в нужный конструктор.

---

## 🧠 Subquery в Criteria API

```java
Subquery<Long> subquery = query.subquery(Long.class);
Root<Order> orderRoot = subquery.from(Order.class);
subquery.select(cb.count(orderRoot))
        .where(cb.equal(orderRoot.get("user"), root));

query.select(root)
     .where(cb.greaterThan(subquery, 5L));
```

---

## 📊 Группировка (GROUP BY, HAVING)

```java
query.multiselect(root.get("department"), cb.count(root))
     .groupBy(root.get("department"))
     .having(cb.gt(cb.count(root), 10));
```

---

## 📦 Pageable и Sort (Spring)

```java
TypedQuery<User> typedQuery = em.createQuery(query);
typedQuery.setFirstResult(page * size);
typedQuery.setMaxResults(size);

query.orderBy(cb.asc(root.get("createdAt")));
```

---

## 🔐 Типобезопасность с `@StaticMetamodel`

```java
query.select(userRoot.get(User_.email));
query.where(cb.equal(userRoot.get(User_.status), Status.ACTIVE));
```

Позволяет избежать ошибок в строковых названиях полей, компилятор сам подсказывает.

---

## 💡 Примечания

- Criteria API удобен для динамических фильтров (например, если параметры фильтрации приходят с фронта).
- Не забывай про `cb.conjunction()` и `cb.disjunction()` — полезно для динамических условий.
- Лучше использовать **метамодель** (сгенерированные `SingularAttribute`) для типобезопасности.

---

_Обновлено: апрель 2025_

