package cas.user;

public class User {
	Integer uuid;
	String username;
	String name;
	Address address;

	User(Integer uuid, String username, String name, Address address) {
		this.uuid = uuid;
		this.username = username;
		this.name = name;
		this.address = address;
	}

	User(String[] data) {
		this(Integer.valueOf(data[0]), data[1], data[2], new Address(data[3], data[4], data[5]));
	}

}
