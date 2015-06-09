package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class SyncMessage extends Messages {

	private FileCache fileCache;

	public SyncMessage(FileCache fileCache) {
		this.fileCache = fileCache;
		line = "SYNC";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input) {
		System.out.println("sync");
		FileMessage file = new FileMessage(fileCache);
		input[0] = "FILE";
		file.perform(queue, input);
	}

}
