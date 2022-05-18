package cas.inv;

import java.io.IOException;
import java.util.HashMap;

/**
 * In memory representation of the Stock file made up of a map of {@link Item}
 */
public class Inventory {
	private String filename;
	/**
	 * The Key is the {@link Item} barcode
	 */
	public HashMap<String, Item> items;

	/**
	 * Parses stock file and creates an in memory version
	 *
	 * @param filename Stock data file path
	 */
	public Inventory(String filename) {
		this.filename = filename;
		items = new HashMap<String, Item>();

		cas.io.Handler<Item> handler = new cas.io.Handler<Item>(filename, Item.class.getPackage(), 1);
		while (handler.open()) {
			add_item(handler.read());
		}
		handler.close();
	}

	/**
	 * Puts a new item into the shop {@link Inventory} as long as it doesn't exist
	 * already
	 *
	 * @param item The {@link Item} to add
	 * @return If the {@link Item} was added otherwise there was a barcode collision
	 */
	public Boolean add_item(Item item) {
		if (!items.containsKey(item.getBarcode())) {
			items.put(item.getBarcode(), item);
			return true;
		}
		return false;
	}

	/**
	 * Write the data back to the stock file.
	 */
	public void save() {
		cas.io.Writer file = new cas.io.Writer(filename);
		try {
			for (Item item : items.values()) {
				file.write(item);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.close();
	}

}
