.PHONY: test verify lint package dependency-tree

test:
	mvn -B test

verify:
	mvn -B verify

lint:
	mvn -B checkstyle:check

package:
	mvn -B -DskipTests package

dependency-tree:
	mvn -B dependency:tree -DoutputFile=dependency-tree.txt
