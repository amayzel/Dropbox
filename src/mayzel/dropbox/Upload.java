package mayzel.dropbox;

public class Upload implements Messages {

	private String filename;
	private byte offset;
	private byte filesize;
	public Upload(String filename,byte offset,byte filesize) {
		this.filename = filename;
		this.offset = offset;
		this.filesize = filesize;
	}
	

	public String getFilename() {
		return filename;
	}



	public void setFilename(String filename) {
		this.filename = filename;
	}



	public byte getOffset() {
		return offset;
	}



	public void setOffset(byte offset) {
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
		// cleint sends the files in chunks to the server
		
	}

}
