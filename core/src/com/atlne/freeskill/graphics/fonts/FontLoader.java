package com.atlne.freeskill.graphics.fonts;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

public class FontLoader implements Disposable {

    private transient FreeTypeFontGenerator fontGenerator;
    private transient FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

    @Override
    public void dispose() {

    }
}
