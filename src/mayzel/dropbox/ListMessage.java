package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class ListMessage extends Messages {

	private FileCache fileCache;

	public ListMessage(FileCache fileCache) {
		this.fileCache = fileCache;
		line = "LIST";
	}

	@Override
	public void perform(LinkedBlockingQueue<String> queue, String[] input) {
		// return number of files
		// lists all file names last modified and file size

		Files files = new Files(fileCache);
		input[0] = "FILES";
		files.perform(queue, input);
	}

}
