package mayzel.dropbox;

public class File {
	
	private String username;
	private String filename;

	public File(String username, String filename) {
		this.username = username;
		this.filename = filename;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	

}
