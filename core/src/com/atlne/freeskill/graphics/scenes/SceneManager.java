package com.atlne.freeskill.graphics.scenes;

import com.badlogic.gdx.Gdx;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SceneManager {

    private transient Stack<Scene> sceneStack = new Stack<>();
    private transient ArrayDeque<Scene> pushQueue = new ArrayDeque<>();
    private transient ArrayDeque<Scene> popQueue = new ArrayDeque<>();

    public void update() {
        sceneStack.addAll(pushQueue);
        sceneStack.removeAll(popQueue);
        displayScenes();
    }

    public Scene getScene() {
        return sceneStack.peek();
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
}
