package org.example.game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.example.system.AISystem;
import org.example.system.AgeSystem;
import org.example.system.ControlSystem;

public class World {

	ArrayList<GameObject> gameObjects = new ArrayList<>();

	public World() throws ExecutionException, InterruptedException {
		initGame();
	}

	public void initGame() throws ExecutionException, InterruptedException {
		// System 배치
		AgeSystem ageSystem = new AgeSystem();
		ControlSystem controlSystem = new ControlSystem();
		AISystem aiSystem = new AISystem();

		// Entity 배치
		GameObject ape = EntityGenerator.generateEntity("에이프");
		GameObject jinny = EntityGenerator.generateEntity("지니");
		GameObject iireen = EntityGenerator.generateEntity("이린");
		GameObject gamja = EntityGenerator.generateEntity("감자");
		gameObjects.add(ape);
		gameObjects.add(jinny);
		gameObjects.add(iireen);
		gameObjects.add(gamja);

		CompletableFuture ageSystemLauncher = CompletableFuture.runAsync(() -> {
			// 시간 시스템 작동
			ageSystem.update(gameObjects);
		});

		CompletableFuture controlSystemLauncher = CompletableFuture.runAsync(() -> {
			// 컨트롤 시스템 작동
			controlSystem.update(gameObjects);
		});

		CompletableFuture aiSystemLauncher = CompletableFuture.runAsync(() -> {
			// AI 시스템 작동
			aiSystem.update(gameObjects);
		});

		CompletableFuture future = CompletableFuture.allOf(ageSystemLauncher, controlSystemLauncher, aiSystemLauncher);
		future.get();
	}
}
