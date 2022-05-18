package cas.inv;

public class Mouse extends Item {
	public enum Type {
		standard,
		gaming
	}

	Type type;
	Integer buttons;

	public Mouse(String[] data) {
		super(data);
		type = Type.valueOf(data[2].toLowerCase());
		buttons = Integer.valueOf(data[9]);
	}

	@Override
	protected String variant_name() {
		return type.name();
	}

	@Override
	protected String extra() {
		return buttons.toString();
	}

	public Integer getButtons() {
		return buttons;
	}

}
