# Changelog

## [1.0.1] - 2026-08-20

### Added
- `/metrics` endpoint with request counters and tests
- JSON structured logging via logstash-logback-encoder
- `RequestIdFilter` correlating requests with `X-Request-Id`
- Maven Wrapper (`./mvnw`) and `scripts/test.sh` for zero-touch fresh clones
- `make bootstrap` to materialize `application.properties` from the example
- JaCoCo line-coverage gate at 70%
- Checkstyle config under `config/checkstyle/`

### Changed
- CI runs explicit `lint` and `test` jobs via Makefile / scripts
- Documented Spring 6 / Jakarta as out of scope

## [1.0.0] - 2026-08-20

### Added
- Spring MVC + Hibernate employee CRUD
- TestNG / Mockito / DBUnit suite on H2
- Docker Compose, Dependabot, OWASP audit, health endpoint
