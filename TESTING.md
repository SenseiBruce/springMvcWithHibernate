# Testing

## Command

```bash
./test
# or
./scripts/test.sh
# or
./mvnw -B test
```

## Suite

- Framework: TestNG (Surefire)
- Suite file: `src/test/resources/testng.xml`
- Specs: `src/test/java/**/*Test.java`
- DAO integration tests use in-memory H2 only (`src/test/resources/application-test.properties`)

## Coverage gate

```bash
./mvnw -B verify
```

Fails if line coverage is below **70%** (JaCoCo `jacoco-check` on the `verify` phase).
XML report: `target/site/jacoco/jacoco.xml`
