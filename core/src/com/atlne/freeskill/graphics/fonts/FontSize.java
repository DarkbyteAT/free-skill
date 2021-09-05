package com.atlne.freeskill.graphics.fonts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FontSize {
    TINY(4, 1),
    SMALLER(12, 1),
    SMALL(16, 2),
    DEFAULT(24, 2),
    MEDIUM(32, 3),
    LARGE(48, 4),
    LARGER(60, 6),
    HUGE(96, 8),
    HUGER(120, 12);

    private transient int size;
    private transient int borderWidth;
}