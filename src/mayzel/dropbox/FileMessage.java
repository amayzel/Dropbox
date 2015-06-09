package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class FileMessage extends Messages {

	private FileCache fileCache;

	public FileMessage(FileCache fileCache) {
		this.fileCache = fileCache;
		line = "FILE";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input) {
		for (int i = 0; i < fileCache.getNumFiles(); i++) {
			java.io.File file = fileCache.getFiles().get(i);
			String msg = "FILE " + file.getName() + " " + file.lastModified() + " " + file.length();
			queue.add(msg);
		}
	}

}
