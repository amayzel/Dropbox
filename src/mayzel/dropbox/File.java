package mayzel.dropbox;

public class File implements Messages{
	
	
	private String filename;
	private int lastModified;
	private byte fileSize;

	public File(String filename, int lastModified, byte fileSize) {
		this.filename = filename;
	}

	
	public void setLastModified(int lastModified) {
		this.lastModified = lastModified;
	}

	public int getLastModified() {
		return lastModified;
	}


	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}


	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}
	
	

}
