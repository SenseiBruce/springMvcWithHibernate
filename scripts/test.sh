#!/usr/bin/env bash
# Runnable test entrypoint for fresh clones (no manual config copy required).
set -euo pipefail
cd "$(dirname "$0")/.."
make bootstrap
./mvnw -B test
