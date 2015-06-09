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
		System.out.println(sendMessage);
		for (int i = 0; i < fileCache.getNumFiles(); i++) {
			java.io.File file = fileCache.getFiles().get(i);
			String msg = "FILE dropbox\\" + file.getName() + " " + file.lastModified() + " " + file.length();
			queue.add(msg);
			System.out.println(msg);
		}
	}
}
