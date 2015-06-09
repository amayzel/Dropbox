package mayzel.dropbox;


public class File {

	private String filename;
	private long lastModified;
	private byte fileSize;

	public File(String filename, long lastModified, byte fileSize) {
		this.filename = filename;
		this.lastModified = lastModified;
		this.fileSize = fileSize;
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

}
