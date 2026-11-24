.PHONY: run compile clean

run: compile
	cd testgame && _JAVA_AWT_WM_NONREPARENTING=1 mvn exec:java -Dexec.mainClass="com.example.Main"

compile:
	mvn install

clean:
	mvn clean
