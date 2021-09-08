package com.atlne.freeskill.graphics.scenes.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Alignment {
    TOP_LEFT(0, -1, 0),
    TOP_RIGHT(1, -1, 0),
    TOP_CENTER(-0.5f, -1, 0),
    BOTTOM_LEFT(0, 0, 1),
    BOTTOM_RIGHT(1, 0, 1),
    BOTTOM_CENTER(-0.5f, 0, 1),
    CENTER_LEFT(0, -0.5f, 0.5f),
    CENTER_RIGHT(1, -0.5f, 0.5f),
    CENTER(-0.5f, -0.5f, 0.5f);

    private float horizontalAdjustment;
    private float verticalAdjustment;
    private float textVerticalAdjustment;
}
