# Spring MVC + Hibernate Employee Manager

Spring MVC web application for employee CRUD, backed by Hibernate ORM and MySQL (H2 for tests).

## Quick start (fresh clone)

See also [TESTING.md](TESTING.md).

```bash
git clone https://github.com/SenseiBruce/springMvcWithHibernate.git
cd springMvcWithHibernate
./test
# equivalent: make test   OR   mvn -B test   OR   ./mvnw -B test
```

No MySQL and no manual config copy are required for tests. `make bootstrap` creates `application.properties` from the example when missing. The Maven Wrapper (`./mvnw`) installs Maven on first use.

## Architecture

| Layer | Package | Role |
|-------|---------|------|
| Controller | `com.websystique.springmvc.controller` | HTTP request handling |
| Service | `com.websystique.springmvc.service` | Business logic and transactions |
| DAO | `com.websystique.springmvc.dao` | Persistence via Hibernate `SessionFactory` |
| Model | `com.websystique.springmvc.model` | JPA entity (`Employee`) |

## Prerequisites

- JDK 8+ (Maven Wrapper included; system Maven optional)
- MySQL 8 only for running the WAR against a real database

## Setup (runtime / Docker)

```bash
make bootstrap
# edit src/main/resources/application.properties for your MySQL instance
# optional: cp .env.example .env
```

## Build

```bash
make package
```

Produces `target/SpringHibernateExample.war`.

## Run tests

```bash
make test
# equivalent: ./test
# equivalent: ./mvnw test
```

TestNG suite: `src/test/resources/testng.xml` (controller, service, DAO, util, request-id filter).

Lint:

```bash
make lint
```

Full verify (tests + Checkstyle + JaCoCo ≥ 70% line coverage + package):

```bash
make verify
```

## Observability

- `GET /health` — liveness JSON (`status=UP`)
- `GET /metrics` — counters: `health_checks_total`, `metrics_scrapes_total`, `errors_total`
- JSON logs via `logstash-logback-encoder` (`src/main/resources/logback.xml`)
- `X-Request-Id` correlation id on every request (`RequestIdFilter`); exceptions include the id in structured error logs
- Optional log shipping: set `LOGSTASH_HOST` in the runtime environment if your aggregator scrapes stdout JSON (tests never require an external collector)
- List pagination: `GET /list?page=0&size=10`

## Run with Docker

```bash
docker compose up --build
```

App: `http://localhost:8080/` · health: `/health` · metrics: `/metrics`

## Dependency Management

- Versions are pinned in `pom.xml` (Spring 5.3.x / Hibernate 5.6.x / javax namespace).
- **Spring 6 / Jakarta EE is explicitly out of scope** for this codebase; staying on javax keeps the WAR deployable on Servlet 3/4 containers with JDK 8.
- Resolved tree snapshot: `dependency-tree.txt` and runtime lock list `dependencies.lock` (regenerate with `make dependency-tree`).
- Dependabot opens weekly Maven update PRs; CI runs OWASP Dependency-Check.

## Project layout

```
./mvnw                 Maven Wrapper (preferred build entrypoint)
scripts/test.sh        zero-touch test runner for fresh clones
Makefile               bootstrap | test | lint | verify | package
config/checkstyle/     Checkstyle rules enforced in CI
src/test/resources/testng.xml
```

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md). Release notes live in [CHANGELOG.md](CHANGELOG.md).

## License

Released under the [MIT License](LICENSE).
