package com.atlne.freeskill.desktop;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.scenes.SceneManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public final class DesktopLauncher {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "FreeSkill";
		config.width = 1440;
		config.height = 900;
		config.backgroundFPS = 0;
		config.foregroundFPS = 0;
		new LwjglApplication(new Core(), config);
		SceneManager sceneManager = new SceneManager();
	}
}
