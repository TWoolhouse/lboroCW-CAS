package cas.inv;

import java.util.ArrayList;

public class Keyboard extends Item {
	enum Type {
		standard,
		flexible,
		gaming
	}

	enum Layout {
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
	ArrayList<String> display() {
		var list = super.display();
		return list;
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
