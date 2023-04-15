package org.example.component;

public class ControlComponent extends Component {
	public ControlComponent() {
		super(ComponentType.CONTROL);
	}

	@Override
	public void update() {
		System.out.println("\t\uD83D\uDD38 Update Component - ControlComponent");
	}
}
