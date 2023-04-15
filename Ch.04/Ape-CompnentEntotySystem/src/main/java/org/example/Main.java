package org.example;

import java.util.concurrent.ExecutionException;

import org.example.game.EntityGenerator;
import org.example.game.GameObject;
import org.example.game.World;

public class Main {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		World world = new World();
		world.initGame();
	}
}
