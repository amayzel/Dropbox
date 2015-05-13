package mayzel.dropbox;

import java.io.File;
import java.util.List;

public class FileCache {
	
	private static final String ROOT = "dropbox/";
	
	public FileCache(){
		new File(ROOT).mkdir();
	}
	
	public List<File> getFiles(String username){
		//returns a list of files names
	}

	public void addChunk() {
		
	}
	
	public Chunk getChunk(String username, String filename, int start, int length){
		//is the actual bytes you need
	}

}