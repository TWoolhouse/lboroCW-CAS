package cas.inv;

import java.util.List;

public class Mouse extends Item {
	enum Type {
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
	public List<String> display() {
		List<String> list = super.display();
		list.add(5, type.toString());
		list.add(6, buttons.toString());
		return list;
	}

	@Override
	protected String variant_name() {
		return type.name();
	}

	@Override
	protected String extra() {
		return buttons.toString();
	}
}
