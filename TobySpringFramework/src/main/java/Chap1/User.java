package Chap1;

public class User {
	String id;
	String name;
	String password;
	Level level;
	int login;
	int recommend;
	String email;

	public User() {

	}

	public User(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public User(String id, String name, String password, Level level, int login, int recommend, String email) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.level = level;
		this.login = login;
		this.recommend = recommend;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void upgradeLevel() {
		Level nextLevel = this.level.nextLevel();
		if (nextLevel == null) {
			throw new IllegalStateException(this.level + "은 업그레이드가 불가능합니다.");
		} else {
			this.level = nextLevel;
		}
	}

	public enum Level {
		GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);
		private final int value;
		private final Level next;

		Level(int value, Level next) {
			this.value = value;
			this.next = next;
		}

		public int intValue() {
			return value;
		}

		public Level nextLevel() {
			return this.next;
		}

		public static Level valueOf(int value) {
			switch (value) {
				case 1:
					return BASIC;
				case 2:
					return SILVER;
				case 3:
					return GOLD;
				default:
					throw new AssertionError("Unknown value: " + value);
			}
		}
	}
}
