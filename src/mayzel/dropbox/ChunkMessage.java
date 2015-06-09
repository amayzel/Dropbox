package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

public class ChunkMessage extends Messages {

	private FileCache fileCache;

	public ChunkMessage(FileCache fileCache) {
		this.fileCache = fileCache;
		line = "CHUNK";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input) {
		String fileName = input[1];
		try {
			fileCache.addChunk(new Chunk(fileName, input[5], Integer.valueOf(input[4])));
			System.out.println("Chunk");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
	}

}
