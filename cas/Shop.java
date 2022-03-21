package cas;

import cas.gui.Context;
import cas.inv.Inventory;
import cas.user.Users;

public class Shop {
	Inventory inventory;
	Users users;

	Shop(String stock_file, String user_file) {
		inventory = new Inventory(stock_file);
		users = new Users(user_file);
		inventory.save();

	}

	static Context swing;

	public static void main(String[] args) {
		var shop = new Shop("Stock.txt", "UserAccounts.txt");
		swing = new Context(shop);
	}
}
