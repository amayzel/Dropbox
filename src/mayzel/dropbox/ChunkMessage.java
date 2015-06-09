package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class ChunkMessage extends Messages {

	private FileCache fileCache;

	public ChunkMessage(FileCache fileCache) {
		this.fileCache = fileCache;
		line = "CHUNK";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input) {
		String fileName = input[1];
		String msg = "CHUNK " + fileName + " " + input[2] + " " + input[3] + " " + input[4];
		queue.add(msg);
		fileCache.addChunk(fileName);
	}

}
