package cas;

import cas.inv.Basket;
import cas.inv.Inventory;
import cas.user.Users;

public class Shop {
	public Inventory inventory;
	public Basket basket;
	public Users users;

	Shop(String stock_file, String user_file) {
		inventory = new Inventory(stock_file);
		users = new Users(user_file);
		basket = new Basket();
		inventory.save();

	}

	public static void main(String[] args) {
		Shop shop = new Shop("Stock.txt", "UserAccounts.txt");
		cas.gui.Context.start(shop);
	}
}
