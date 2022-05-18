package cas.inv;

/**
 * A single element in a {@link Basket}
 */
public class BasketBucket {
	private Item item;
	private Integer count;

	/**
	 *
	 * @param item  The Item
	 * @param count The quantity in the basket
	 */
	BasketBucket(Item item, Integer count) {
		this.item = item;
		this.setCount(count);
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Item getItem() {
		return item;
	}

	/**
	 * Displayed on the GUI
	 */
	@Override
	public String toString() {
		return item.getDisplayName() + " x" + count;
	}
}
