package mayzel.dropbox;

public class Download implements Messages{

	
	private String filename;
	private byte offset;
	private byte chunksize;
	public Download(String filename, byte offset, byte chunksize) {
		this.filename = filename;
		this.offset = offset;
		this.chunksize  = chunksize;
	}

	@Override
	public void perform() {
		// send files to cleint
		
	}

}
