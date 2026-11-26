package com.github.spynets.speler.debug;


public record DebugInfo(
    int fps,
    float frameTime,
    int entityCount,
    int componentCount,
    int systemCount,
    int drawCalls,
    int activeAnimations,
    long memoryUsed,
    long maxMemory
) {}
