package mayzel.dropbox;


public class Sync {

	private String filename;
	private byte filesize;
	private long lastModified;

	public Sync(String filename, long lastModified, byte filesize) {
		this.filename = filename;
		this.filesize = filesize;
		this.lastModified = lastModified;
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
}
