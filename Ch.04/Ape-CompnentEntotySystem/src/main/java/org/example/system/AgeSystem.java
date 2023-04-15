package org.example.system;

import java.util.ArrayList;

import org.example.component.AgeComponent;
import org.example.component.ComponentType;
import org.example.game.GameObject;

public class AgeSystem implements SystemInterface {
	@Override
	public void update(ArrayList<GameObject> gameObjects) {
		System.out.println("\uD83D\uDC9A Launch System - AgeSystem");

		while (true) {
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (GameObject obj : gameObjects) {
				AgeComponent c = (AgeComponent)obj.getComponent(ComponentType.AGE);
				if (c != null) {
					c.age++;
					System.out.printf("⏳ %s -> age: %d (Thread: %s)%n", obj.getName(), c.age,
						Thread.currentThread().getName());
				}
			}

		}
	}
}
