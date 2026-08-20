# Contributing

1. Copy `src/main/resources/application.properties.example` to `application.properties` (gitignored).
2. Use JDK 8 and Maven 3.6+.
3. Keep each change small: one feature or fix plus its TestNG test in the same commit.
4. Before opening a PR, run:

```bash
make lint
make test
make verify
```

5. Prefer commit messages like `feat(service): add findByIdRequired with test`.
