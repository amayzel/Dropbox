package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class Download implements Messages {

	private String filename;
	private byte offset;
	private byte chunksize;

	public Download(String filename, byte offset, byte chunksize) {
		this.filename = filename;
		this.offset = offset;
		this.chunksize = chunksize;
	}

	@Override
	public void perform(LinkedBlockingQueue<String> queue) {
		// send files to cleint

	}

}
