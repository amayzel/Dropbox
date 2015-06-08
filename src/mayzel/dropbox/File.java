package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class File implements Messages {

	private String filename;
	private long lastModified;
	private byte fileSize;
	private FileCache fileCache;

	public File(String filename, long lastModified, byte fileSize) {
		this.filename = filename;
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
			String msg = "FILE " + fileCache.getFiles().get(i).getName();
			queue.add(msg);
		}
	}

}
