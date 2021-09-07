package com.atlne.freeskill.desktop;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.scenes.SceneController;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public final class DesktopLauncher {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "FreeSkill";
		config.width = 1440;
		config.height = 900;
		config.allowSoftwareMode = true;
		config.vSyncEnabled = false;
		config.backgroundFPS = 0;
		config.foregroundFPS = 0;
		config.samples = 3;
		new LwjglApplication(new Core(), config);
		SceneController sceneManager = new SceneController();
	}
}
