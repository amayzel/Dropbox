package mayzel.dropbox;


public class Download {

	private String filename;
	private FileCache fileCache;

	public Download(String filename, FileCache fileCache) {
		this.filename = filename;
		this.fileCache = fileCache;
	}

}
