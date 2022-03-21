package cas.inv;

import java.util.ArrayList;

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
	ArrayList<String> display() {
		var list = super.display();
		list.add(type.toString());
		list.add(buttons.toString());
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
