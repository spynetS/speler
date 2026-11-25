.PHONY: build install test verify javadoc clean

# Build the engine jar (plus sources/javadoc jars).
build:
	mvn package

# Install into ~/.m2 so another local project can depend on the engine.
install:
	mvn install

test:
	mvn test

# Runs checkstyle as well.
verify:
	mvn verify

javadoc:
	mvn javadoc:javadoc

clean:
	mvn clean

# Once you add a demo module, give it its own run target, e.g.:
#
# .PHONY: run-platformer
# run-platformer: install
# 	cd demos/platformer && _JAVA_AWT_WM_NONREPARENTING=1 \
# 		mvn exec:java -Dexec.mainClass="com.github.spynets.speler.demo.platformer.Main"
