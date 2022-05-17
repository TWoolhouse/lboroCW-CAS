package cas.io;

import java.util.Arrays;

public class Extractor {
	private Package pck;
	private Integer pos;

	Extractor(Package pck, Integer pos) {
		this.pck = pck;
		this.pos = pos;
	}

	Object decode(String[] data) {
		try {
			Class<?> clazz = Class
					.forName(pck.getName() + "." + data[pos].substring(0, 1).toUpperCase() + data[pos].substring(1));
			Object obj = clazz.getConstructor(String[].class).newInstance(new Object[] { data });
			System.out.println(obj);
			return obj;
		} catch (ClassNotFoundException e) {
			System.err.println(Arrays.toString(data));
			e.printStackTrace(System.err);
		} catch (Exception e) {
			System.err.println(Arrays.toString(data));
			e.printStackTrace(System.err);
		}
		System.err.println(Arrays.toString(data));
		return null;
	}
}
