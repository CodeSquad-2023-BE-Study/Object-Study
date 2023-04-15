# Entity Component System (Java)
- Entity Component System를 검색해서 나온 블로그, Github, 강의에 나온 코드를 짜집기한 코드입니다. 제가 이해할 수 있는 부분만 간소화 하여 작성한 코드이므로 실제와 많이 다르고 틀린 부분이 있을 수 있습니다. 참고용으로 보세요.
- 코드의 출처는 발표자료 마지막 페이지에 있습니다.
- 발표 자료 : [Ape - Google Slide](https://docs.google.com/presentation/d/1qwcGarnXuT9_BG63Ppxc2usEvwIkiDrqqIdEV6vFJ9g/edit#slide=id.p)

# 코드
## Component
- Component는 데이터, 게임의 상태만 저장합니다.
```text
AgeComponent : 나이 속성
AutoMoveComponent : 스스로 움직이는 속성
ControlComponent : 사용자가 직접 조작할 수 있는 속성
PositionComponent : 위치 속성

ComponentType : component의 타입을 enum으로 정의함
```

## Entity
- Component의 집합. 하나의 GameObject가 됩니다.
```text
감자.txt
    - position
    - 위치 속성만 가집니다.
    - 필드에 존재하는 아이템, 맵을 꾸미는 요소 정도로 생각하시면 됩니다.
에이프.txt
    - Control,Position,Age
    - 사용자가 직접 컨트롤할 수 있습니다.
    - 위치와 나이를 가집니다.
    - 직접 플레이하는 케릭터라고 보시면 됩니다.
이린.txt, 지니.txt
    - Position,Age,AutoMove
    - 스스로 움직이는 Object입니다.
    - 위치와 나이를 가집니다.
    - NPC나 몬스터로 생각하시면 됩니다.
```

## System
- 기능 구현, 데이터를 변경하는 행동만 가집니다.
```text
AgeSystem
    - AgeComponent를 가진 Object들을 7초 마다 나이 먹게하는 시스템 입니다.
AISystem
    - AutoMoveComponent, PositionComponent를 가진 Object들을 4초 마다 자동으로 움직이게 하는 시스템입니다.
ControlSystem : 
    - ControlComponent, PositionComponent를 가진 Object들을 직접 조작할 수 있게 하는 시스템입니다.
```
## Entity text 파일을 GameObject 객체로 만드는 로직
- `package org.example.game.EntityGenerator`

### 1. text 파일 읽어오기
```java
public class EntityGenerator {
	// ... 생략
    private static GameObject loadObjectOrNull(String name) {
		String directory = "src/entity";
		String fileName = String.format("%s.txt", name);
		Path filePath = Paths.get(directory, fileName);

		File entityFile = new File(filePath.toString());

		if (!entityFile.isFile()) {
			return null; // 파일이 없는 경우
		}

		List<String> lines;

		try {
			lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		assert (lines.size() == 1) : "file is not in correct format!";
		// ... 생략
	}
	// ... 생략
}
```
- `private static GameObject loadObjectOrNull(String name)`
  - txt 파일의 이름을 매개변수로 받아와 GameObject를 반환합니다.
  - 파일이 없거나 읽어올 수 없으면 null을 반환합니다.(간소화 하기 위해 따로 처리하지 않았습니다.)
- `assert (lines.size() == 1) : "해당 파일은 올바른 포맷이 아닙니다.";`
  - 표현식(`lines.size() == 1`)을 인자로 받아 거짓인 경우 두번째 표현식(`"해당 파일은 올바른 포맷이 아닙니다."`)의 값을 예외 메시지로 던저줍니다.
  - assert는 디버깅 용으로 사용하며, 실제 실행시에는 동작하지 않습니다. (동작하게 하는 방법은 아래 링크에서 찾아보세요)
  - [Java에서 assert 사용하기](https://offbyone.tistory.com/294)
  - [[정보] Java에서 assert 사용하기](https://blueyikim.tistory.com/2452)
  - [[Java] - Assertion vs Exception](https://velog.io/@urtimeislimited/Java-Assertion-vs-Exception)

### 2. text 파일에서 읽어온 내용을 구분자(`,`)로 분류하여 Component 추가하기
```java
public class EntityGenerator {

	// ... 생략

	private static GameObject loadObjectOrNull(String name) {

		// ... 생략
        
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
```

- `String[] compoments = lines.get(0).split(",", -1);`
  - text 파일에서 읽어온 내용을 구분자(`,`)로 나누는 과정입니다.
  - `.split(",", -1)` 부분을 발표때 제가 잘 못 설명했는데요, split() 메서드의 두 번째 인자는 배열의 크기. 즉, 몇 개로 분리할 건지를 지정합니다.
  - 두 번째 인자에 음수(-1)를 넣어주면 구분자 사이에 값이 없어도 공백문자를 만들어 넣어 줍니다. 아래에 차이를 확인해 보세요
  ```java
  String line = "a,b,c,,,d,,";
  String[] arr1 = line.split(","); // 결과: ["a", "b", "c", "", "", "d"] - d 이후의 공백 문자는 취급하지 않습니다.
  String[] arr2 = line.split(",", 2); // 결과: ["a", "b,c,,,d,,"] - 두 개로 분리합니다.
  String[] arr3 = line.split(",", -1); // 결과:  ["a", "b", "c", "", "", "d", "", ""] - d 이후의 공백 문자까지 분리해 줍니다.
  
  System.out.println(arr1.length); // 6
  System.out.println(arr2.length); // 2
  System.out.println(arr3.length); // 8
  ```

## System 로직
- `package org.example.system.AgeSystem`로 설명하겠습니다.
```java
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
```
- `while (true) { ... Thread.sleep(7000); ... }`
  - 7초마다 한번씩 로직을 실행합니다.
- `for (GameObject obj : gameObjects)`
  - 현재 애플리케이션에 등록된 GameObject들을 순회합니다.
  - 현재 `감자`, `에이프`, `이린`, `지니`가 GameObject로 등록되어 있습니다.
- `AgeComponent c = (AgeComponent)obj.getComponent(ComponentType.AGE);`
  - 현재 GameObject에서 AgeComponent를 가져옵니다. AgeComponent가 없다면 null이 반환됩니다.
- `if (c != null) { ...`
  - 현재 GameObject에 AgeComponent가 있는 경우에만 System의 로직을 수행합니다.
  - 현재 GameObject에 AgeComponent가 없다면 다음 GameObject에서 같은 로직을 반복하게 됩니다.
- `c.age++;`
  - 7초마다 나이를 한 살씩 먹습니다. 
- `System.out.printf()`
  - 콘솔에 출력하기 위함입니다.

# World - GameObject와 System을 통합
```java
public class World {

	ArrayList<GameObject> gameObjects = new ArrayList<>();

	public World() throws ExecutionException, InterruptedException {
		initGame();
	}

	public void initGame() throws ExecutionException, InterruptedException {
		// System 배치
		AgeSystem ageSystem = new AgeSystem();
		ControlSystem controlSystem = new ControlSystem();
		AISystem aiSystem = new AISystem();

		// Entity 배치
		GameObject ape = EntityGenerator.generateEntity("에이프");
		GameObject jinny = EntityGenerator.generateEntity("지니");
		GameObject iireen = EntityGenerator.generateEntity("이린");
		GameObject gamja = EntityGenerator.generateEntity("감자");
		gameObjects.add(ape);
		gameObjects.add(jinny);
		gameObjects.add(iireen);
		gameObjects.add(gamja);

		CompletableFuture ageSystemLauncher = CompletableFuture.runAsync(() -> {
			// 시간 시스템 작동
			ageSystem.update(gameObjects);
		});

		CompletableFuture controlSystemLauncher = CompletableFuture.runAsync(() -> {
			// 컨트롤 시스템 작동
			controlSystem.update(gameObjects);
		});

		CompletableFuture aiSystemLauncher = CompletableFuture.runAsync(() -> {
			// AI 시스템 작동
			aiSystem.update(gameObjects);
		});

		CompletableFuture future = CompletableFuture.allOf(ageSystemLauncher, controlSystemLauncher, aiSystemLauncher);
		future.get();
	}
}
```

### Entity text 파일을 GameObject 객체로 만들어 애플리케이션에 등록합니다
```java
ArrayList<GameObject> gameObjects = new ArrayList<>();

GameObject ape = EntityGenerator.generateEntity("에이프");
GameObject jinny = EntityGenerator.generateEntity("지니");
GameObject iireen = EntityGenerator.generateEntity("이린");
GameObject gamja = EntityGenerator.generateEntity("감자");
gameObjects.add(ape);
gameObjects.add(jinny);
gameObjects.add(iireen);
gameObjects.add(gamja);
```

### 각 System을 쓰레드로 만들어 동시에 작동하도록 합니다
```java
CompletableFuture ageSystemLauncher = CompletableFuture.runAsync(() -> {
    // 시간 시스템 작동
    ageSystem.update(gameObjects);
});
```
```java
CompletableFuture controlSystemLauncher = CompletableFuture.runAsync(() -> {
    // 컨트롤 시스템 작동
    controlSystem.update(gameObjects);
});
```
```java
CompletableFuture aiSystemLauncher = CompletableFuture.runAsync(() -> {
    // AI 시스템 작동
    aiSystem.update(gameObjects);
});
```
```java
CompletableFuture future = CompletableFuture.allOf(ageSystemLauncher, controlSystemLauncher, aiSystemLauncher);
future.get();
```

# 실행 결과 Console
```text
⭐️ Update GameObject - 에이프
	🔸 Update Component - ControlComponent
	🔸 Update Component - PositionComponent
	🔸 Update Component - AgeComponent
⭐️ Update Complete - 에이프

⭐️ Update GameObject - 지니
	🔸 Update Component - PositionComponent
	🔸 Update Component - AgeComponent
	🔸 Update Component - AutoMoveComponent
⭐️ Update Complete - 지니

⭐️ Update GameObject - 이린
	🔸 Update Component - PositionComponent
	🔸 Update Component - AgeComponent
	🔸 Update Component - AutoMoveComponent
⭐️ Update Complete - 이린

⭐️ Update GameObject - 감자
	🔸 Update Component - PositionComponent
⭐️ Update Complete - 감자

💚 Launch System - ControlSystem
💚 Launch System - AgeSystem
💚 Launch System - AISystem
👻 지니 AI 이동 -> [0, 1] (Thread: ForkJoinPool.commonPool-worker-7)
👻 이린 AI 이동 -> [-1, 0] (Thread: ForkJoinPool.commonPool-worker-7)
⏳ 에이프 -> age: 1 (Thread: ForkJoinPool.commonPool-worker-3)
⏳ 지니 -> age: 1 (Thread: ForkJoinPool.commonPool-worker-3)
⏳ 이린 -> age: 1 (Thread: ForkJoinPool.commonPool-worker-3)
👻 지니 AI 이동 -> [0, 0] (Thread: ForkJoinPool.commonPool-worker-7)
👻 이린 AI 이동 -> [0, 0] (Thread: ForkJoinPool.commonPool-worker-7)
a
🏃 에이프 이동 -> [-1, 0] (Thread: ForkJoinPool.commonPool-worker-5)
w
🏃 에이프 이동 -> [-1, 1] (Thread: ForkJoinPool.commonPool-worker-5)
⏳ 에이프 -> age: 2 (Thread: ForkJoinPool.commonPool-worker-3)
⏳ 지니 -> age: 2 (Thread: ForkJoinPool.commonPool-worker-3)
⏳ 이린 -> age: 2 (Thread: ForkJoinPool.commonPool-worker-3)
👻 지니 AI 이동 -> [1, 0] (Thread: ForkJoinPool.commonPool-worker-7)
👻 이린 AI 이동 -> [-1, 0] (Thread: ForkJoinPool.commonPool-worker-7)
d
🏃 에이프 이동 -> [0, 1] (Thread: ForkJoinPool.commonPool-worker-5)
s
🏃 에이프 이동 -> [0, 0] (Thread: ForkJoinPool.commonPool-worker-5)
```
