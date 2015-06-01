package mayzel.dropbox;
import java.io.File;
import java.io.DataInput;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FileCache {

	private static final String ROOT = "dropbox/";
	private ArrayList<File> allFiles = new ArrayList<File>();
	public FileCache() {
		new File(ROOT).mkdir();
	}

	public List<File> getFiles(String username) {
		// returns a list of files names
		ArrayList<File> returningFiles = new ArrayList<File>();
		for(int i=0;i<allFiles.size();i++){
				returningFiles.add(allFiles.get(i));
		}
		return returningFiles;
	}

	public void addChunk() {

	}

	public Chunk getChunk(String filename, int start, int length) {
		// is the actual bytes you need
		Chunk chunk = null;
		File file;
		try {
			for(int i=0;i<allFiles.size();i++){
				if(allFiles.get(i).getFilename() == filename){
					file = allFiles.get(i);
					break;
				}
				else{
					file = new File(ROOT + "/filename", "rw");
				}
			}
			byte b[] = null;
			int counter = 0;
			while (b.length < length) {
				b[counter] = ((DataInput) file).readByte();
				counter++;
			}
			chunk = new Chunk(filename, b, start);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return chunk;
	}
}