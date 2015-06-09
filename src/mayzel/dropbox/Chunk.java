package mayzel.dropbox;


public class Chunk {

	private String fileName;
	private long lastModified;
	private String encoded;
	private int offset;
	private byte filesize;

	public Chunk(String fileName, long lastModified, byte fileSize, int offset, String encoded) {
		this.fileName = fileName;
		this.encoded = encoded;
		this.offset = offset;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getEncoded() {
		return encoded;
	}

	public void setEncoded(String encoded) {
		this.encoded = encoded;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(int lastModified) {
		this.lastModified = lastModified;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public byte getFilesize() {
		return filesize;
	}

	public void setFilesize(byte filesize) {
		this.filesize = filesize;
	}

}
