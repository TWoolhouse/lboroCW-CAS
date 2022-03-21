package cas.inv;

import java.util.ArrayList;

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

	ArrayList<String> display() {
		var list = new ArrayList<String>(7);
		list.add(getBarcode());
		list.add(brand);
		// etc
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

}
