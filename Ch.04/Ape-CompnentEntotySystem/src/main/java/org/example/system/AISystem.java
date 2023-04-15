package org.example.system;

import java.util.ArrayList;
import java.util.Random;

import org.example.component.AutoMoveComponent;
import org.example.component.ComponentType;
import org.example.component.ControlComponent;
import org.example.component.PositionComponent;
import org.example.game.GameObject;

public class AISystem implements SystemInterface {
	private static final Random random = new Random();

	@Override
	public void update(ArrayList<GameObject> gameObjects) {
		System.out.println("\uD83D\uDC9A Launch System - AISystem");
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (GameObject obj : gameObjects) {
				AutoMoveComponent c = (AutoMoveComponent)obj.getComponent(ComponentType.AUTOMOVE);
				PositionComponent p = (PositionComponent)obj.getComponent(ComponentType.POSITION);
				if (c != null && p != null) {
					boolean randXY = random.nextBoolean();
					boolean randPlusMinus = random.nextBoolean();

					int num = (randPlusMinus)? 1 : -1;
					if(randXY) {
						p.x += num;
					} else {
						p.y += num;
					}
					System.out.printf("\uD83D\uDC7B %s AI 이동 -> [%d, %d] (Thread: %s)%n", obj.getName(), p.x, p.y,
						Thread.currentThread().getName());
				}
			}

		}
	}
}
