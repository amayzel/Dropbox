package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class Files extends Messages {

	private FileCache fileCache;

	public Files(FileCache fileCache) {
		this.fileCache = fileCache;
		line = "FILES";
	}

	@Override
	public void perform(LinkedBlockingQueue<String> queue, String[] input) {
		String sendMessage = "FILES " + fileCache.getNumFiles();
		queue.add(sendMessage);
	}
}
