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
2. Copy the example config (real credentials stay gitignored):

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
# optional: cp .env.example .env
```

3. Edit `jdbc.url`, `jdbc.username`, and `jdbc.password` for your MySQL instance.

## Build

```bash
make package
# or: mvn clean package
```

Produces `target/SpringHibernateExample.war`.

## Run tests

Tests run against an in-memory H2 database — **no MySQL required**.

```bash
make test
# or: mvn test
```

This executes the TestNG suite in `src/test/resources/testng.xml` (controller, service, and DAO layers).

Lint only:

```bash
make lint
# or: mvn checkstyle:check
```

Full verify (tests + Checkstyle + JaCoCo coverage gate + package):

```bash
make verify
# or: mvn verify
```

## Health check

`GET /health` returns JSON `{ "status": "UP", "service": "SpringHibernateExample" }`.

## Run with Docker

```bash
docker compose up --build
```

App is available at `http://localhost:8080/` (health: `http://localhost:8080/health`).

## Dependency Management

Pinned versions live in `pom.xml`. A resolved tree snapshot is committed as `dependency-tree.txt`.

Regenerate after dependency changes:

```bash
make dependency-tree
# or: mvn dependency:tree -DoutputFile=dependency-tree.txt
```

Dependabot opens weekly PRs for Maven updates. CI also runs OWASP Dependency-Check.

## Project layout

```
src/main/java/...   application code
src/main/resources  application.properties.example, logback.xml, messages
src/main/webapp     JSP views
src/test/java/...   TestNG + Mockito + DBUnit tests
src/test/resources  testng.xml, H2 properties, DBUnit datasets
checkstyle.xml      lint rules enforced in CI
Makefile            make test | verify | lint | package
```
