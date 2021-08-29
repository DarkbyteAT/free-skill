package com.atlne.freeskill.graphics.fonts;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.utils.AssetHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;
import java.util.Map;

public class FontManager extends AssetHandler {

    public static final String FONTS_FOLDER_PATH = "/assets/fonts";
    public static final String TAG = "FontManager";

    private transient Map<String, FontLoader> fontLoaders = new HashMap<>();

    public FontManager(Core core) {
        super(core);
    }

    @Override
    public void create() {
        for(FileHandle file : Gdx.files.local(FONTS_FOLDER_PATH).list("ttf")) {
            String fontName = file.nameWithoutExtension();
            fontLoaders.put(fontName, new FontLoader(core, fontName));
        }
    }

    @Override
    public void dispose() {

    }

    public BitmapFont getFont(String name, FontSize fontSize) {
        return fontLoaders.get(name).getFont(fontSize);
    }
}
