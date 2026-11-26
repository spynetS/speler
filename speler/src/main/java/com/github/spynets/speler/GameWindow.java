package com.github.spynets.speler;

import com.github.spynets.speler.debug.*;

public interface GameWindow {
		void setSelectedScene(Scene scene);
		void renderWindow();
		void setBlankCursor();
    void renderDebug(DebugInfo info);
}
