# RKSP Lab 2 — Gate Service (rksp_lab_2_gate)

Это **шлюз (gate-service)**. Он:
- поднимает внешний REST API по `openapi-gate.yaml`;
- проксирует вызовы во **внутренний data-service** через Feign-клиент, сгенерированный из `openapi-data.yaml`;
- отдает Swagger UI.

Порт по методичке: **8080**.  
Data-service должен быть доступен на: **http://localhost:8083**.

---

## Что нужно заранее

- **Java 17**
- **Запущенный data-service** (из репозитория rksp_lab_2_client)
- (опционально) Maven, но в проекте есть `./mvnw`

---

## Быстрый старт

### 1) Клонирование

```bash
git clone https://github.com/AntonShishkin11/rksp_lab_2_gate.git
cd rksp_lab_2_gate
```

### 2) Собрать проект (генерация OpenAPI + feign-клиента + компиляция)

```bash
./mvnw clean compile
```

### 3) Запустить gate-service

```bash
./mvnw spring-boot:run
```

---


## Swagger

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON (если нужен): обычно `http://localhost:8080/v3/api-docs`

---

## Проверка работоспособности

Важно: **сначала поднять data-service** (см. README в rksp_lab_2_client).

### 1) Создать студента через gate (POST /students)

Swagger gate → `POST /students` → **Try it out** → пример тела:

```json
{
  "fullName": "Петров Пётр Петрович",
  "passport": "4321 098765"
}
```

Ожидаемо: **201 Created** и ответ с `id`.

Фактически gate:
1) принимает запрос,
2) маппит в `StudentDataCreateRequest`,
3) вызывает data-service,
4) маппит ответ в `StudentGateResponse`.

### 2) Получить студента по id через gate (GET /students/{id})

Swagger gate → `GET /students/{id}` → подставь `id` из POST.

Ожидаемо: **200 OK** и данные студента.


## Структура проекта

- `src/main/resources/openapi-gate.yaml` — контракт API gate-service
- `src/main/resources/openapi-data.yaml` — контракт data-service (нужен для генерации клиента)
- `target/generated-sources/openapi/...` — сгенерированные API/модели для gate
- `target/generated-sources/openapi-feign/...` — сгенерированный Feign-клиент для data-service
- `src/main/java/.../controller` — `StudentGateController` (реализация API)
- `src/main/java/.../config` — бин-конфиг клиента (`ApiClient`)

