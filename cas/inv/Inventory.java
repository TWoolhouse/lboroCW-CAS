package cas.inv;

import java.io.IOException;
import java.util.HashMap;

public class Inventory {
	private String filename;
	private HashMap<String, Item> items;

	public Inventory(String filename) {
		this.filename = filename;
		items = new HashMap<String, Item>();

		var handler = new cas.io.Handler<Item>(filename, Item.class.getPackage(), 1);
		while (handler.open()) {
			add_item(handler.read());
		}
		handler.close();
	}

	private Boolean add_item(Item item) {
		if (!items.containsKey(item.getBarcode())) {
			items.put(item.getBarcode(), item);
			return true;
		}
		return false;
	}

	public void save() {
		var file = new cas.io.Writer(filename);
		try {
			for (var item : items.values()) {
				file.write(item);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		file.close();
	}

}
