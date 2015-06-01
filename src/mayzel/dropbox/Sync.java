package mayzel.dropbox;

public class Sync implements Messages{

	private String filename;
	private byte filesize;
	private int lastModified;
	public Sync(String filename, byte filesize, int lastModified) {
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



	public int getLastModified() {
		return lastModified;
	}



	public void setLastModified(int lastModified) {
		this.lastModified = lastModified;
	}



	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}

}
