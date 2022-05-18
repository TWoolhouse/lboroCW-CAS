package cas.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * CSV Reader, converts the rows into an object of type T
 */
public class Handler<T> implements Closeable {

	private Scanner scanner;
	private Extractor extractor;

	/**
	 *
	 * @param file_name CSV file name
	 * @param pack      Package the subclasses are found in
	 * @param cls_index CSV column which contains the classes to convert to
	 */
	public Handler(String file_name, Package pack, Integer cls_index) {
		extractor = new Extractor(pack, cls_index);
		File file = new File(file_name);
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the file is still open
	 *
	 * @return
	 */
	public boolean open() {
		return scanner.hasNextLine();
	}

	/**
	 * Reads a single line of the CSV
	 *
	 * @return An object of Type T created from the data on the row
	 */
	@SuppressWarnings("unchecked")
	public T read() {
		return (T) extractor.decode(scanner.nextLine().split(", "));
	}

	/**
	 * Closes the underlying file
	 */
	public void close() {
		scanner.close();
	}
}
