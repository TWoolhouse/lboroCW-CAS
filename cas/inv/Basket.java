package cas.inv;

import java.util.ArrayList;

public class Basket {
	private ArrayList<BasketBucket> items;

	public Basket() {
		this.items = new ArrayList<BasketBucket>();
	}

	public Basket(ArrayList<BasketBucket> items) {
		this.items = items;
	}

	BasketBucket append(BasketBucket item) {
		this.items.add(item);
		return item;
	}

	BasketBucket append(Item item, Integer count) {
		var it = new BasketBucket(item, count);
		this.items.add(it);
		return it;
	}

	public Double price() {
		Double sum = 0.0;
		for (var basketBucket : items) {
			var item = basketBucket.getItem();
			sum += item.getPrice() * item.getQuantity();
		}
		return sum;
	}

}
