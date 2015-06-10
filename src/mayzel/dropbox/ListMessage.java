package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class ListMessage extends Messages {

	public ListMessage() {
		line = "LIST";
	}

	@Override
	public void perform(LinkedBlockingQueue<String> queue, String[] input, FileCache fileCache) {
		// return number of files
		// lists all file names last modified and file size
		String sendMessage = "FILES " + fileCache.getNumFiles();
		queue.add(sendMessage);
		for (int i = 0; i < fileCache.getNumFiles(); i++) {
			java.io.File file = fileCache.getFiles().get(i);
			String msg = "FILE " + file.getName() + " " + file.lastModified() + " " + file.length();
			queue.add(msg);
		}

	}

}
