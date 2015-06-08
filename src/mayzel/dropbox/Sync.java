package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class Sync implements Messages {

	private String filename;
	private byte filesize;
	private long lastModified;
	private FileCache fileCache;

	public Sync(String filename, long lastModified, byte filesize, FileCache fileCache) {
		this.filename = filename;
		this.filesize = filesize;
		this.lastModified = lastModified;
		this.fileCache = fileCache;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte getFilesize() {
		return filesize;
	}

	public void setFilesize(byte filesize) {
		this.filesize = filesize;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(int lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public void perform(LinkedBlockingQueue<String> queue) {
		File file = new File(filename, lastModified, filesize, fileCache);
		file.perform(queue);
	}

}
