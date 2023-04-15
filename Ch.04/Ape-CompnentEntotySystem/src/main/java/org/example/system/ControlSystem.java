package org.example.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.example.component.ComponentType;
import org.example.component.ControlComponent;
import org.example.component.PositionComponent;
import org.example.game.GameObject;

public class ControlSystem implements SystemInterface {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(java.lang.System.in));

	@Override
	public void update(ArrayList<GameObject> gameObjects) {
		System.out.println("\uD83D\uDC9A Launch System - ControlSystem");
		while (true) {
			for (GameObject obj : gameObjects) {
				ControlComponent c = (ControlComponent)obj.getComponent(ComponentType.CONTROL);
				PositionComponent p = (PositionComponent)obj.getComponent(ComponentType.POSITION);
				if (c != null && p != null) {
					try {
						String command = br.readLine();
						switch (command) {
							case "a":
								p.x--;
								break;
							case "d":
								p.x++;
								break;
							case "w":
								p.y++;
								break;
							case "s":
								p.y--;
								break;
							default:
						}
						System.out.printf("\uD83C\uDFC3 %s 이동 -> [%d, %d] (Thread: %s)%n", obj.getName(), p.x, p.y,
							Thread.currentThread().getName());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
