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

	public BasketBucket append(Item item, Integer count) {
		return append(new BasketBucket(item, count));
	}

	public void empty() {
		this.items.clear();
	}

	public Double price() {
		Double sum = 0.0;
		for (BasketBucket basketBucket : items) {
			sum += basketBucket.getItem().getPrice() * basketBucket.getCount();
		}
		return sum;
	}

	public void purchase() {
		for (BasketBucket bucket : items) {
			bucket.getItem().setQuantity(bucket.getItem().getQuantity() - bucket.getCount());
		}
		empty();
	}

}
