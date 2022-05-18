package cas.inv;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract Item which is also a {@link cas.io.RowCSV} so it can be written
 * to the stock file
 */
public abstract class Item implements cas.io.RowCSV {
	public enum Connection {
		wired,
		wireless,
	};

	private String barcode;
	private String brand;
	private String colour;
	private Connection connectivity;
	private Integer quantity;
	private Double original;
	private Double price;

	Item(String barcode,
			String brand,
			String colour,
			Connection connectivity,
			Integer quantity,
			Double original,
			Double price) {
		this.barcode = barcode;
		this.brand = brand;
		this.colour = colour;
		this.connectivity = connectivity;
		this.quantity = quantity;
		this.original = original;
		this.price = price;
	}

	/**
	 * Takes a split version of a csv row
	 *
	 * @param data the csv row
	 */
	Item(String[] data) {
		this(data[0],
				data[3],
				data[4],
				Connection.valueOf(data[5].toLowerCase()),
				Integer.valueOf(data[6]),
				Double.valueOf(data[7]),
				Double.valueOf(data[8]));
	}

	/**
	 *
	 * @return Basket display of an item
	 */
	public String getDisplayName() {
		return this.getBarcode() + " " + this.getBrand() + " " + this.getClass().getSimpleName();
	}

	/**
	 *
	 * @return Data back as an array for the JTable
	 */
	public final List<String> display() {
		List<String> list = new ArrayList<String>(10);
		list.add(this.getClass().getSimpleName());
		list.add(getBarcode());
		list.add(brand);
		list.add(colour);
		list.add(connectivity.toString());
		list.add(variant_name());
		list.add(extra());
		list.add(getQuantity().toString());
		list.add(getPrice().toString());
		list.add(original.toString());
		return list;
	}

	/**
	 *
	 * @return Keyboard type and Mouse Type enums name
	 */
	abstract protected String variant_name();

	/**
	 *
	 * @return The second extra field e.g mouse buttons
	 */
	abstract protected String extra();

	@Override
	public String[] csv() {
		return new String[] {
				getBarcode(),
				this.getClass().getSimpleName().toLowerCase(),
				variant_name().toLowerCase(),
				brand,
				colour,
				connectivity.name(),
				quantity.toString(),
				original.toString(),
				price.toString(),
				extra()
		};
	}

	public Double getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getBarcode() {
		return barcode;
	}

	public String getBrand() {
		return brand;
	}

	public String getColour() {
		return colour;
	}

	public Connection getConnectivity() {
		return connectivity;
	}

	public Double getOriginal() {
		return original;
	}

}
