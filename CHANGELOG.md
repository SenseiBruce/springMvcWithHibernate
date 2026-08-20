# Changelog

## [1.0.4] - 2026-08-21

### Removed
- `package.json` npm wrapper (misclassified the Maven repo and hurt dependency health)

### Added
- SSN path-variable validation on edit/update/delete
- `PageResult` pagination for `/list?page=&size=`
- Unchecked-exception handling with MDC-tagged structured error logs

## [1.0.3] - 2026-08-21

### Added
- `package.json` `npm test` entrypoint for suite discovery
- Dedicated `.github/workflows/test.yml` and fresh-clone CI job
- JUnit smoke test alongside TestNG suite
- `errors_total` metric incremented from GlobalExceptionHandler
- Controller Bean Validation boundary tests

## [1.0.2] - 2026-08-21

### Added
- Typed `ApiError` and `EmployeeValidationService` with TestNG coverage
- Root `./test` entrypoint and `TESTING.md`
- `dependencies.lock` plus CI drift check against the committed lock/tree
- JaCoCo XML upload artifact and explicit CI `test` / `coverage` steps
- H2-only guard in `scripts/test.sh`

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
