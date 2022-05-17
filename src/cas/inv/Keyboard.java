package cas.inv;

import java.util.List;

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
	public List<String> display() {
		List<String> list = super.display();
		list.add(5, type.toString());
		list.add(6, layout.toString());
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
