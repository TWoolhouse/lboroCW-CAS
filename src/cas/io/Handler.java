package cas.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Handler<T> implements Closeable {

	private Scanner scanner;
	private Extractor extractor;

	public Handler(String file_name, Package pack, Integer cls_index) {
		extractor = new Extractor(pack, cls_index);
		File file = new File(file_name);
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean open() {
		return scanner.hasNextLine();
	}

	@SuppressWarnings("unchecked")
	public T read() {
		return (T) extractor.decode(scanner.nextLine().split(", "));
	}

	public void close() {
		scanner.close();
	}
}
