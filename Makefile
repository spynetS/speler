


run: compile
	export _JAVA_AWT_WM_NONREPARENTING=1; mvn exec:java -Dexec.mainClass="com.example.App"
test:
	export _JAVA_AWT_WM_NONREPARENTING=1; mvn test
compile:
	mvn -B clean install;
