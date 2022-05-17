package cas.user;

import java.util.ArrayList;

public class Users {
	private ArrayList<User> _users;

	public Users(String filename) {
		_users = new ArrayList<User>();
		cas.io.Handler<User> handler = new cas.io.Handler<User>(filename, User.class.getPackage(), 6);
		while (handler.open()) {
			_users.add(handler.read());
		}
		handler.close();
	}

	public ArrayList<User> users() {
		return _users;
	}

}
