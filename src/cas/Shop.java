package cas;

import cas.inv.Inventory;
import cas.user.Users;

public class Shop {
	public Inventory inventory;
	public Users users;

	/**
	 *
	 * @param stock_file Stock data file path
	 * @param user_file  User data file path
	 */
	Shop(String stock_file, String user_file) {
		inventory = new Inventory(stock_file);
		users = new Users(user_file);
		inventory.save();

	}

	public static void main(String[] args) {
		Shop shop = new Shop("Stock.txt", "UserAccounts.txt");
		cas.gui.Context.start(shop);
	}
}
