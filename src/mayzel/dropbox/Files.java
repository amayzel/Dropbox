package mayzel.dropbox;

public class Files implements Messages{

	private int numberOfFiles;
	public Files() {
		this.numberOfFiles = 0;
	}
	
	

	public int getNumberOfFiles() {
		return numberOfFiles;
	}



	public void setNumberOfFiles(int numberOfFiles) {
		this.numberOfFiles = numberOfFiles;
	}



	@Override
	public void perform() {
		
	}

}
