package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class File implements Messages {

	private String filename;
	private long lastModified;
	private byte fileSize;
	private FileCache fileCache;

	public File(String filename, long lastModified, byte fileSize, FileCache fileCache) {
		this.filename = filename;
		this.lastModified = lastModified;
		this.fileSize = fileSize;
		this.fileCache = fileCache;
	}

	public File(FileCache fileCache) {
		this.fileCache = fileCache;
	}

	public void setLastModified(int lastModified) {
		this.lastModified = lastModified;
	}

	public long getLastModified() {
		return lastModified;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public void perform(LinkedBlockingQueue<String> queue) {
		for (int i = 0; i < fileCache.getNumFiles(); i++) {
			java.io.File file = fileCache.getFiles().get(i);
			String msg = "FILE " + file.getName() + " " + file.lastModified() + " " + file.length();
			queue.add(msg);
		}
	}

}
