package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class SyncMessage extends Messages {

	public SyncMessage() {
		line = "SYNC";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input, FileCache fileCache) {
		System.out.println("sync");
		FileMessage file = new FileMessage();
		input[0] = "FILE";
		file.perform(queue, input, fileCache);
	}

}
