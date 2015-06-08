package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class List implements Messages {

	private FileCache fileCache;

	public List(FileCache fileCache) {
		this.fileCache = fileCache;
	}

	@Override
	public void perform(LinkedBlockingQueue<String> queue) {
		// return numebr of files
		// lists all file names last modified and file size

		Files files = new Files(fileCache);
		files.perform(queue);
		File file = new File(fileCache);
		file.perform(queue);
	}

}
