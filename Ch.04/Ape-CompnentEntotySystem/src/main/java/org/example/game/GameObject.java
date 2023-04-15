package org.example.game;

import java.util.ArrayList;

import org.example.component.Component;
import org.example.component.ComponentType;

public class GameObject {
	private String name;
	private final ArrayList<Component> components = new ArrayList<>();

	public GameObject(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addComponent(Component component) {
		components.add(component);
	}

	public Component getComponent(ComponentType type) {
		for(Component c : components) {
			if(c.isMatchType(type)) {
				return c;
			}
		}
		return null;
	}

	public void update() {
		System.out.printf("⭐️ Update GameObject - %s%n", this.name);
		for(Component component : this.components) {
			component.update();
		}
		System.out.printf("⭐️ Update Complete - %s%n%n", this.name);
	}
}
