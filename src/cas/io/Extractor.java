package cas.io;

import java.util.Arrays;

/**
 * Extracts a CSV row by row, turning it into a Java object
 */
public class Extractor {
	private Package pck;
	private Integer pos;

	/**
	 *
	 * @param pck The package the classes of the objects are in
	 * @param pos Which csv column the class name is in
	 */
	Extractor(Package pck, Integer pos) {
		this.pck = pck;
		this.pos = pos;
	}

	/**
	 * Decode a single row
	 *
	 * @param data The row of a csv where column {@link Extractor.pos} is the class
	 *             name which is found in {@link Extractor.pck}
	 * @return The {@link Object} created from the row data
	 */
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
