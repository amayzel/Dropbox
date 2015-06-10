package mayzel.dropbox;

import java.io.File;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class FileMessage extends Messages {

	public FileMessage() {
		line = "FILE";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input, FileCache fileCache) {
		List<File> files = fileCache.getFiles();
		// for (File f : files) {

		// if (!(f.getName().equals(input[1])) && f.lastModified() !=
		// Long.valueOf(input[2])
		// || f.getName().equals(input[1]) && f.lastModified() !=
		// Long.valueOf(input[2])) {
		DownloadMessage msg = new DownloadMessage();
		input[0] = "DOWNLOAD";
		msg.perform(queue, input, fileCache);
		System.out.println("Download " + input[1]);
		// }
		// }

	}
}
