package graphics.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface RawData {

	public void load(String filePath);

	public void accept(InterpretedData data);

	public default long getSize(String filePath) {
		String fileName = "src/main/resources/" + filePath;
		Path path = Paths.get(fileName);
		long fileSize = 0;
		try {
			fileSize = Files.size(path);
		} catch (IOException e) {
			System.out.println("Could not find file " + filePath);
			e.printStackTrace();
		}
		return fileSize;
	}

}
