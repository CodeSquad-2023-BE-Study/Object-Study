package org.example.component;

public class PositionComponent extends Component {
	public int x;
	public int y;

	public PositionComponent() {
		super(ComponentType.POSITION);
	}

	@Override
	public void update() {
		System.out.println("\t\uD83D\uDD38 Update Component - PositionComponent");
	}

}
