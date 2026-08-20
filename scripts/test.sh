#!/usr/bin/env bash
# Runnable test entrypoint for fresh clones (no manual config copy required).
set -euo pipefail
cd "$(dirname "$0")/.."

make bootstrap

# Fail fast if the test profile is not H2 (suite must stay self-contained).
if ! grep -Eq '^jdbc\.driverClassName\s*=\s*org\.h2\.Driver' src/test/resources/application-test.properties; then
  echo "ERROR: application-test.properties must use org.h2.Driver" >&2
  exit 1
fi
if ! grep -Eq '^jdbc\.url\s*=\s*jdbc:h2:mem:' src/test/resources/application-test.properties; then
  echo "ERROR: application-test.properties must use an in-memory jdbc:h2:mem URL" >&2
  exit 1
fi

echo "Running TestNG suite via Maven Surefire (H2 in-memory, no external DB)"
./mvnw -B test
