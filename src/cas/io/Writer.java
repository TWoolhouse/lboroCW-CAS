package cas.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * CSV Writer
 */
public class Writer implements Closeable {
	private FileWriter file;

	/**
	 *
	 * @param filename File path to write the CSV to
	 */
	public Writer(String filename) {
		try {
			file = new FileWriter(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes a row to the CSV
	 *
	 * @param <T>    A {@link RowCSV} type
	 * @param object The object to serialise
	 * @throws IOException
	 */
	public <T extends RowCSV> void write(T object) throws IOException {
		for (String string : object.csv()) {
			file.write(string + ", ");
		}
		file.write('\n');
	}

	/**
	 * Closes the underlying file
	 */
	public void close() {
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
