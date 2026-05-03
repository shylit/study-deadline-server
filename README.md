# Study Deadline Server

Серверная часть курсовой работы по теме:

> «Разработка клиент-серверного мобильного приложения для управления учебными заданиями и дедлайнами»

Сервер реализован на **Kotlin** с использованием **Ktor** и предоставляет REST API для Android-клиента.

---

# Технологии

- Kotlin
- Ktor
- PostgreSQL
- Neon.tech
- Exposed
- HikariCP
- Gradle Kotlin DSL

---

# Архитектура проекта

Проект построен с использованием принципов **Clean Architecture**.

Структура серверной части разделена на слои:

- `presentation` — routes, dto
- `domain` — models, repositories, usecases
- `data` — repositories, tables, database

---

# Переменные окружения

Для подключения к базе данных необходимо указать переменные окружения:

```env
DATABASE_URL=jdbc:postgresql://your-host/neondb
DATABASE_USER=your-user
DATABASE_PASSWORD=your-password
```

Пример структуры находится в файле `.env.example`.

---

# Запуск сервера

## Запуск через Gradle

```bash
./gradlew run
```

После запуска сервер будет доступен по адресу:

```text
http://localhost:8080
```

---

# Проверка работоспособности

```http
GET /health
```

Пример ответа:

```json
{
  "status": "OK"
}
```

---

# Основные эндпоинты

## Задания

### Получить список заданий

```http
GET /api/tasks
```

### Создать задание

```http
POST /api/tasks
```

### Обновить задание

```http
PUT /api/tasks/{id}
```

### Изменить статус задания

```http
PATCH /api/tasks/{id}/status
```

### Удалить задание

```http
DELETE /api/tasks/{id}
```

### Получить задания на сегодня

```http
GET /api/tasks/today
```

### Получить задания на неделю

```http
GET /api/tasks/week
```

### Получить задания по предмету

```http
GET /api/tasks/by-subject?subject=...
```

---

## Предметы

### Получить список предметов

```http
GET /api/subjects
```

### Создать предмет

```http
POST /api/subjects
```

### Получить предмет по id

```http
GET /api/subjects/{id}
```

### Обновить предмет

```http
PUT /api/subjects/{id}
```

### Удалить предмет

```http
DELETE /api/subjects/{id}
```

---

## Пользователь

### Получить текущего пользователя

```http
GET /api/users/me
```

---

# Пример JSON-запроса

## Создание задания

```json
{
  "title": "Сделать курсовую",
  "description": "Реализовать серверную часть",
  "subject": "Ktor",
  "deadline": "2026-06-10",
  "priority": "HIGH",
  "type": "COURSE_WORK"
}
```

---

# Формат ошибок

```json
{
  "message": "Описание ошибки"
}
```

---

# База данных

Сервер использует **PostgreSQL (Neon.tech)**.

## Основные таблицы

- `users`
- `subjects`
- `tasks`

## Связи

- пользователь → предметы
- пользователь → задания
- предмет → задания

---

# Автор

Курсовая работа студента направления **«Программная инженерия»**.

**Трисветов Ричард Вунгович**