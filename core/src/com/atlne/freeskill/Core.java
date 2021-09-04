package com.atlne.freeskill;

import com.atlne.freeskill.audio.AudioPlayer;
import com.atlne.freeskill.graphics.GraphicsManager;
import com.atlne.freeskill.graphics.fonts.FontManager;
import com.atlne.freeskill.utils.Creatable;
import com.atlne.freeskill.utils.json.JsonHelper;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public final class Core extends ApplicationAdapter {

	private final transient List<Creatable> creatables = new ArrayList<>();
	private final transient List<Disposable> disposables = new ArrayList<>();

	@Getter private transient JsonHelper jsonHelper;
	@Getter private transient AudioPlayer audioPlayer;
	@Getter private transient FontManager fontManager;
	@Getter private transient GraphicsManager graphicsManager;

	@Override
	public void create() {
		loadCoreObjects();
		runCreatables();
		preloadNonCoreObjects();
	}

	@Override
	public void dispose() {
		Gdx.app.log("Cleanup", "Running asset disposal methods...");
		disposables.forEach(Disposable::dispose);
		Gdx.app.log("Cleanup", "Asset disposal methods complete!");
	}

	@Override
	public void render() {
		graphicsManager.update();
	}

	public void registerCreatable(Creatable creatable) {
		creatables.add(creatable);
	}

	public void registerDisposable(Disposable disposable) {
		disposables.add(disposable);
	}

	public void runCreatables() {
		creatables.forEach(Creatable::create);
		creatables.clear();
	}

	private void loadCoreObjects() {
		Gdx.app.log("Startup", "Initialising core objects...");
		jsonHelper = new JsonHelper();
		audioPlayer = new AudioPlayer(this);
		graphicsManager = new GraphicsManager(this);
		Gdx.app.log("Startup", "Core objects initialised!");
	}

	private void preloadNonCoreObjects() {
		Gdx.app.log("Startup", "Initialising objects for further loading...");
		fontManager = new FontManager(this);
		Gdx.app.log("Startup", "Further objects initialised!");
	}
}
