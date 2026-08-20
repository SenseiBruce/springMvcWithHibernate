# Testing

## Commands (any of these)

```bash
./test
make test
mvn -B test
./mvnw -B test
```

This is a **Maven** project (`pom.xml`). There is no Node/npm dependency graph.

## Suite

- Frameworks: TestNG (primary) + JUnit smoke (`JunitSmokeTest`)
- Suite file: `src/test/resources/testng.xml`
- Specs: `src/test/java/**/*Test.java`
- DAO integration tests use in-memory H2 only (`src/test/resources/application-test.properties`)
- `scripts/test.sh` fails fast if the test profile is not H2

## Coverage gate

```bash
./mvnw -B verify
```

Fails if line coverage is below **70%** (JaCoCo `jacoco-check` on the `verify` phase).
XML report: `target/site/jacoco/jacoco.xml`
