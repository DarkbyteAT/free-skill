package com.atlne.freeskill.graphics.fonts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FontSize {
    TINY(4, 1),
    SMALL(16, 2),
    MEDIUM(32, 4),
    LARGE(64, 6),
    HUGE(96, 8);

    private transient int size;
    private transient int borderWidth;
}