.PHONY: bootstrap test verify lint package dependency-tree run

bootstrap:
	@test -f src/main/resources/application.properties \
		|| cp src/main/resources/application.properties.example src/main/resources/application.properties
	@echo "bootstrap: application.properties ready"

test: bootstrap
	./mvnw -B test

verify: bootstrap
	./mvnw -B verify

lint: bootstrap
	./mvnw -B checkstyle:check

package: bootstrap
	./mvnw -B -DskipTests package

dependency-tree: bootstrap
	./mvnw -B dependency:tree -DoutputFile=dependency-tree.txt
	./mvnw -B versions:display-dependency-updates >> dependency-tree.txt || true

run: package
	@echo "Deploy target/SpringHibernateExample.war to a Servlet 3+ container, then curl /health and /metrics"
