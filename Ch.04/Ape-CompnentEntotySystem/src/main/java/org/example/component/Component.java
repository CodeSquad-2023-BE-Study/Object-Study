package org.example.component;

public abstract class Component {

	private ComponentType type;

	public Component(ComponentType type) {
		this.type = type;
	}

	public abstract void update();
	public boolean isMatchType(ComponentType type) {
		return this.type == type;
	}
}
