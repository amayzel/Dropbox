package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class Files implements Messages {

	private FileCache fileCache;

	public Files(FileCache fileCache) {
		this.fileCache = fileCache;
	}

	@Override
	public void perform(LinkedBlockingQueue<String> queue) {
		String sendMessage = "FILES " + fileCache.getNumFiles();
		queue.add(sendMessage);
	}

}
