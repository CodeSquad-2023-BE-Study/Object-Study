package org.example.game;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.example.component.AgeComponent;
import org.example.component.AutoMoveComponent;
import org.example.component.ComponentType;
import org.example.component.ControlComponent;
import org.example.component.PositionComponent;

public class EntityGenerator {
	private EntityGenerator() {

	}

	public static GameObject generateEntity(String entityFileName) {
		GameObject entity = loadObjectOrNull(entityFileName);
		entity.update();
		return entity;
	}

	private static GameObject loadObjectOrNull(String name) {
		String directory = "src/entity";
		String fileName = String.format("%s.txt", name);
		Path filePath = Paths.get(directory, fileName);

		File entityFile = new File(filePath.toString());

		if(!entityFile.isFile()) {
			return null; // 파일이 없는 경우
		}

		List<String> lines;

		try {
			lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		assert(lines.size() == 1) : "해당 파일은 올바른 포맷이 아닙니다.";

		String[] compoments = lines.get(0).split(",", -1);

		GameObject obj = new GameObject(name);

		for(String c : compoments) {
			ComponentType type;

			try {
				type = ComponentType.valueOf(c.toUpperCase());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			}

			addComponent(obj, type); // GameObject에 Component 추가
		}
		return obj;
	}

	private static void addComponent(GameObject obj, ComponentType type) {
		switch (type) {
			case AGE:
				obj.addComponent(new AgeComponent());
				break;
			case CONTROL:
				obj.addComponent(new ControlComponent());
				break;
			case POSITION:
				obj.addComponent(new PositionComponent());
				break;
			case AUTOMOVE:
				obj.addComponent(new AutoMoveComponent());
		}
	}
}
