package mayzel.dropbox;

public class Chunk  implements Messages{

	private String fileName;
	private int lastModified;
	private byte bytes[];
	private int offset;
	private byte filesize;
	
	public Chunk(String fileName, int lastModified, byte fileSize, int offset,  byte bytes[]) {
		this.fileName = fileName;
		this.bytes = bytes;;
		this.offset = offset;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getLastModified() {
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
	public void perform() {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
