package cas.inv;

import java.util.ArrayList;
import java.util.List;

public abstract class Item implements cas.io.RowCSV {
	enum Connection {
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

	Item(String[] data) {
		this(data[0],
				data[3],
				data[4],
				Connection.valueOf(data[5].toLowerCase()),
				Integer.valueOf(data[6]),
				Double.valueOf(data[7]),
				Double.valueOf(data[8]));
	}

	public String getDisplayName() {
		return this.getBarcode() + " " + this.getBrand() + " " + this.getClass().getSimpleName();
	}

	public List<String> display() {
		List<String> list = new ArrayList<String>(8);
		list.add(this.getClass().getSimpleName());
		list.add(getBarcode());
		list.add(brand);
		list.add(colour);
		list.add(connectivity.toString());
		list.add(getQuantity().toString());
		list.add(getPrice().toString());
		list.add(original.toString());
		return list;
	}

	abstract protected String variant_name();

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
