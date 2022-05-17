package cas.inv;

public class BasketBucket {
	private Item item;
	private Integer count;

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

	@Override
	public String toString() {
		return item.getDisplayName() + " x" + count;
	}
}
