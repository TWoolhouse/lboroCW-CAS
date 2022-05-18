package cas.inv;

public class Keyboard extends Item {
	public enum Type {
		standard,
		flexible,
		gaming
	}

	public enum Layout {
		UK,
		US
	}

	Type type;
	Layout layout;

	public Keyboard(String[] data) {
		super(data);
		type = Type.valueOf(data[2].toLowerCase());
		layout = Layout.valueOf(data[9].toUpperCase());
	}

	@Override
	protected String variant_name() {
		return type.name();
	}

	@Override
	protected String extra() {
		return layout.name();
	}

}
