package cas.inv;

import java.util.ArrayList;

/**
 * This class represents a shopping basket full of items
 */
public class Basket {
	private ArrayList<BasketBucket> items;

	public Basket() {
		this.items = new ArrayList<BasketBucket>();
	}

	public Basket(ArrayList<BasketBucket> items) {
		this.items = items;
	}

	/**
	 * Adds an Item to the basket which can be merged if they are the same item by
	 * increasing the count
	 *
	 * @param item The item to add to the basket
	 * @return {@link BasketBucket} of where the item is stored, not necessarily the
	 *         same as input, as they can be merged
	 */
	public BasketBucket append(BasketBucket item) {
		for (BasketBucket bucket : items) {
			if (bucket.getItem().getBarcode().equals(item.getItem().getBarcode())) {
				bucket.setCount(bucket.getCount() + item.getCount());
				return bucket;
			}
		}
		this.items.add(item);
		return item;
	}

	/**
	 * Adds an item to the basket
	 *
	 * @param item  {@link Item} to add
	 * @param count Amount to add
	 * @return {@link BasketBucket} it is held in
	 */
	public BasketBucket append(Item item, Integer count) {
		return append(new BasketBucket(item, count));
	}

	/**
	 * Empties the basket
	 */
	public void empty() {
		this.items.clear();
	}

	/**
	 * Computes the total price of the basket
	 *
	 * @return total cost of the basket
	 */
	public Double price() {
		Double sum = 0.0;
		for (BasketBucket basketBucket : items) {
			sum += basketBucket.getItem().getPrice() * basketBucket.getCount();
		}
		return sum;
	}

	/**
	 * Reduces the quantity of every item in the basket to simulate them being
	 * purchased.
	 * Then empties the basket.
	 */
	public void purchase() {
		for (BasketBucket bucket : items) {
			bucket.getItem().setQuantity(bucket.getItem().getQuantity() - bucket.getCount());
		}
		empty();
	}

}
