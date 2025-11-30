


run: compile
	export _JAVA_AWT_WM_NONREPARENTING=1; cd testgame; mvn exec:java -Dexec.mainClass="com.example.Main"
compile:
	mvn install;
