# Spring MVC + Hibernate Employee Manager

Spring MVC web application for employee CRUD, backed by Hibernate ORM and MySQL (H2 for tests).

## Architecture

| Layer | Package | Role |
|-------|---------|------|
| Controller | `com.websystique.springmvc.controller` | HTTP request handling |
| Service | `com.websystique.springmvc.service` | Business logic and transactions |
| DAO | `com.websystique.springmvc.dao` | Persistence via Hibernate `SessionFactory` |
| Model | `com.websystique.springmvc.model` | JPA entity (`Employee`) |

## Prerequisites

- JDK 8+
- Apache Maven 3.6+
- MySQL 8 (runtime only; tests use in-memory H2)

## Setup

1. Create a MySQL database named `websystique`.
2. Copy `application.properties.example` to `src/main/resources/application.properties` and set:

```properties
jdbc.driverClassName=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/websystique?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
jdbc.username=myuser
jdbc.password=mypassword
hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

3. Ensure the `EMPLOYEE` table exists (or enable schema generation if you prefer).

## Build

```bash
mvn clean package
```

Produces `target/SpringHibernateExample.war`.

## Run tests

Tests run against an in-memory H2 database — **no MySQL required**.

```bash
mvn test
```

This executes the TestNG suite covering controller, service, and DAO layers (`AppControllerTest`, `EmployeeServiceImplTest`, `EmployeeDaoImplTest`).

Full verify (tests + Checkstyle + package):

```bash
mvn verify
```

## Run with Docker

```bash
docker compose up --build
```

App is available at `http://localhost:8080/`.

## Project layout

```
src/main/java/...   application code
src/main/resources  application.properties, logback.xml, messages
src/main/webapp     JSP views
src/test/java/...   TestNG + Mockito + DBUnit tests
src/test/resources  H2 test properties and DBUnit datasets
```
