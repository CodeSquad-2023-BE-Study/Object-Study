package org.example.component;

public class AgeComponent extends Component {

	public int age;

	public AgeComponent() {
		super(ComponentType.AGE);
	}

	@Override
	public void update() {
		System.out.println("\t\uD83D\uDD38 Update Component - AgeComponent");
	}
}
