package com.atlne.freeskill.graphics.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SceneController implements Disposable {

    public static final String TAG = "SceneManager";

    private transient Stack<Scene> sceneStack = new Stack<>();
    private transient ArrayDeque<Scene> pushQueue = new ArrayDeque<>();
    private transient ArrayDeque<Scene> popQueue = new ArrayDeque<>();

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "Disposing all scenes...");
        sceneStack.forEach(Scene::dispose);
        sceneStack.clear();
        pushQueue.forEach(Scene::dispose);
        pushQueue.clear();
        popQueue.forEach(Scene::dispose);
        popQueue.clear();
    }

    public void update() {
        pushQueue.forEach(Scene::create);
        sceneStack.addAll(pushQueue);
        pushQueue.clear();
        popQueue.forEach(Scene::dispose);
        sceneStack.removeAll(popQueue);
        popQueue.clear();
        displayScenes();
    }

    public void resize(int width, int height) {
        sceneStack.forEach(scene -> scene.resize(width, height));
    }

    public void pushScene(Scene scene) {
        pushQueue.push(scene);
    }

    public void popScene(Scene scene) {
        popQueue.push(scene);
    }

    private void displayScenes() {
        var scenesToDisplay = collectScenesToDisplay();

        if(scenesToDisplay.size() > 0) {
            Gdx.input.setInputProcessor(scenesToDisplay.get(scenesToDisplay.size() - 1));
            scenesToDisplay.forEach(Scene::display);
        }
    }

    private List<Scene> collectScenesToDisplay() {
        var scenesToDisplay = new ArrayList<Scene>();

        sceneStack.forEach(scene -> {
            if(!scene.isDisplayPrevious()) {
                scenesToDisplay.clear();
            }

            scenesToDisplay.add(scene);
        });

        return scenesToDisplay;
    }

    public Scene getScene() {
        return sceneStack.peek();
    }
}
