package org.example.system;

import java.util.ArrayList;

import org.example.game.GameObject;

public interface SystemInterface {
	abstract void update(ArrayList<GameObject> gameObjects);
}
