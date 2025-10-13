


run: compile
	export _JAVA_AWT_WM_NONREPARENTING=1;	java -Dawt.toolkit=sun.awt.X11.XToolkit -cp ./target/classes com.example.App

compile:
	mvn -B clean install;
