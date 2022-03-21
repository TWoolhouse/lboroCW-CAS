package cas.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer implements Closeable {
	private FileWriter file;

	public Writer(String filename) {
		try {
			file = new FileWriter(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public <T extends RowCSV> void write(T object) throws IOException {
		for (var string : object.csv()) {
			file.write(string + ", ");
		}
		file.write('\n');
	}

	public void close() {
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
