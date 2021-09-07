package com.atlne.freeskill.graphics.fonts;

import com.atlne.freeskill.utils.Creatable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class FontLoader implements Creatable, Disposable {

    static {
        FreeTypeFontGenerator.setMaxTextureSize(FreeTypeFontGenerator.NO_MAXIMUM);
    }

    public static final String TAG = "FontLoader";

    private transient FreeTypeFontGenerator fontGenerator;
    private transient Map<FontSize, BitmapFont> generatedFonts = new HashMap<>();

    @Getter private String fontName;

    public FontLoader(String fontName) {
        this.fontName = fontName;
    }

    @Override
    public void create() {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.local(String.format("%s/%s.ttf",
                FontManager.FONTS_FOLDER_PATH, fontName)));

        for(var fontSize : FontSize.values()) {
            generatedFonts.put(fontSize, fontGenerator.generateFont(generateParameters(fontSize)));
            generatedFonts.get(fontSize).getData().markupEnabled = true;
        }
    }

    @Override
    public void dispose() {
        fontGenerator.dispose();
        generatedFonts.values().forEach(BitmapFont::dispose);
    }

    public BitmapFont getFont(FontSize fontSize) {
        return generatedFonts.get(fontSize);
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter generateParameters(FontSize fontSize) {
        var fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = fontSize.getSize();
        fontParameters.borderWidth = fontSize.getBorderWidth();
        fontParameters.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        fontParameters.color = Color.WHITE;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParameters.magFilter = Texture.TextureFilter.Linear;
        fontParameters.genMipMaps = true;
        fontParameters.kerning = true;
        return fontParameters;
    }
}
