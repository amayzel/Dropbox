package mayzel.dropbox;

import java.io.File;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Files extends Messages {

	public Files() {
		line = "FILES";
	}

	@Override
	public void perform(LinkedBlockingQueue<String> queue, String[] input, FileCache fileCache) {
		List<File> files = fileCache.getFiles();
		for (File f : files) {
			Chunk c = fileCache.getChunk(f, 0, (int) f.length());
			ChunkMessage chunkMsg = new ChunkMessage();
			System.out.println("upload " + f.getName());
			String[] array = { "CHUNK", c.getFileName(), String.valueOf(f.lastModified()), String.valueOf(f.length()),
					String.valueOf(c.getOffset()), c.getEncoded() };
			chunkMsg.perform(queue, array, fileCache);
		}
	}
}
