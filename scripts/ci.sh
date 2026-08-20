#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
make bootstrap
./mvnw -B checkstyle:check
./mvnw -B test
./mvnw -B verify -DskipTests
