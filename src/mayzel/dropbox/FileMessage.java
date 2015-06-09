package mayzel.dropbox;

import java.io.File;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class FileMessage extends Messages {

	private FileCache fileCache;

	public FileMessage(FileCache fileCache) {
		this.fileCache = fileCache;
		line = "FILE";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input) {
		List<File> files = fileCache.getFiles();
		for (File f : files) {
			Chunk c = fileCache.getChunk(f, 0, (int) f.length());
			ChunkMessage chunkMsg = new ChunkMessage(fileCache);
			System.out.println("upload");
			String[] array = { "CHUNK", c.getFileName(), String.valueOf(f.lastModified()), String.valueOf(f.length()),
					String.valueOf(c.getOffset()), c.getEncoded() };
			chunkMsg.perform(queue, array);
			if (!(f.getName().equals(input[1])) && f.lastModified() != Long.valueOf(input[2])
					|| f.getName().equals(input[1]) && f.lastModified() != Long.valueOf(input[2])) {
				DownloadMessage msg = new DownloadMessage(fileCache);
				input[0] = "DOWNLOAD";
				msg.perform(queue, input);
				System.out.println("Download " + input[1]);
			}
		}

	}
}
