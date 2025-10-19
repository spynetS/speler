


run: compile
	mvn exec:java -Dexec.mainClass="com.example.App"
compile:
	mvn -B clean install;
