package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public class Chunk implements Messages {

	private String fileName;
	private long lastModified;
	private String encoded;
	private int offset;
	private byte filesize;
	private FileCache fileCache;

	public Chunk(String fileName, long lastModified, byte fileSize, int offset, String encoded) {
		this.fileName = fileName;
		this.encoded = encoded;
		this.offset = offset;
	}

	public Chunk(FileCache fileCache) {
		this.fileCache = fileCache;
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

	@Override
	public void perform(LinkedBlockingQueue<String> queue) {
		String msg = "CHUNK " + fileName + " " + lastModified + " " + offset + " " + encoded;
		queue.add(msg);
		fileCache.addChunk();
	}

}
