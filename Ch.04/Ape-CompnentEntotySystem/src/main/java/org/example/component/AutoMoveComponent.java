package org.example.component;

public class AutoMoveComponent extends Component{

	public AutoMoveComponent() {
		super(ComponentType.AUTOMOVE);
	}

	@Override
	public void update() {
		System.out.println("\t\uD83D\uDD38 Update Component - AutoMoveComponent");
	}
}
