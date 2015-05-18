package mayzel.dropbox;

public class Chunk {

	private String fileName;
	private byte bytes[];
	private int start;
	
	public Chunk(String fileName, byte bytes[], int start) {
		this.fileName = fileName;
		this.bytes = bytes;;
		this.start = start;
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

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
	
	

}
