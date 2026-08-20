# Contributing

1. Run `make bootstrap` once (or just `make test` — bootstrap is automatic).
2. Use JDK 8; prefer `./mvnw` over a system Maven install.
3. Keep each change small: one feature or fix plus its TestNG test in the same commit.
4. Use conventional commit prefixes: `feat:`, `fix:`, `test:`, `chore:`, `ci:`, `docs:`.
5. Before opening a PR:

```bash
make lint
make test
make verify
```
