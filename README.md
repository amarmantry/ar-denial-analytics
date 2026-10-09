# AR Denial Analytics

This project implements the backend for analyzing denied healthcare claims using Java, Spring Boot, and PostgreSQL. It was built as part of the AR Denial Analytics mini-feature assignment.

## What I implemented

The application provides three API endpoints:

* **Denial summary by payer** (`GET /api/denials/by-payer`) — Returns the total denied amount and number of denied claims for each payer, sorted by total denied amount in descending order.
* **Denial summary by reason** (`GET /api/denials/by-reason`) — Returns the total denied amount grouped by denial reason, also sorted by amount in descending order.
* **Filtered claims** (`GET /api/claims`) — Returns denied claims with pagination and optional filters for payer, denial reason, and date range. Results are sorted by denial date, newest first.

The claims endpoint also validates pagination parameters and rejects invalid date ranges with a `400 Bad Request` response. Blank payer and denial-reason filters are treated as if no filter was provided.

I used Spring Data JPA for database access and PostgreSQL to store the claims. The project includes sample data through `data.sql` so the APIs can be tested locally.

## How to run

### Requirements

* Java 17 or later
* PostgreSQL
* Maven or the included Maven wrapper

### 1. Create the database

Create a PostgreSQL database named `ar_denials`:

```sql
CREATE DATABASE ar_denials;
```

### 2. Configure the database connection

Update `src/main/resources/application.properties` with your PostgreSQL username and password:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ar_denials
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

Replace the placeholders with your local credentials. The current configuration uses Hibernate to update the database schema and runs the SQL seed script during application startup.

### 3. Start the application

From the project root, run:

**Windows**

```bat
mvnw.cmd spring-boot:run
```

**macOS/Linux**

```bash
./mvnw spring-boot:run
```

The application runs at `http://localhost:8080`.

## Testing the APIs

The endpoints can be tested using Postman or a browser for GET requests.

**Payer summary**

```http
GET http://localhost:8080/api/denials/by-payer
```

**Denial reason summary**

```http
GET http://localhost:8080/api/denials/by-reason
```

**Claims with pagination**

```http
GET http://localhost:8080/api/claims?page=0&size=10
```

**Claims with filters**

```http
GET http://localhost:8080/api/claims?payer=Aetna&denialReason=Missing%20Documentation&fromDate=2026-08-01&toDate=2026-08-31&page=0&size=10
```

The claims endpoint supports these query parameters:

* `payer` — Filter by payer.
* `denialReason` — Filter by denial reason.
* `fromDate` and `toDate` — Inclusive date range in `YYYY-MM-DD` format.
* `page` — Zero-based page number; defaults to `0`.
* `size` — Number of records per page; defaults to `10` and must be between `1` and `100`.

## Shortcuts taken

Given the time available, I looked up how to generate seed data and used that approach to populate the database with sample claims. I also reused some boilerplate from my previous projects, mainly the PostgreSQL configuration and a few code patterns, adapting them to this project. I also used AI to help structure and refine the README for clarity and documentation.

The implementation focuses on the backend APIs and their filtering, aggregation, and pagination logic. The React dashboard was not implemented, so the frontend portion of the original requirements remains incomplete.
