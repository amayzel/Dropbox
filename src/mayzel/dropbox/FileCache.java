package mayzel.dropbox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileCache {

	private static final String ROOT = "dropbox/";
	private ArrayList<File> allFiles = new ArrayList<File>();
	private File folder;

	public FileCache() {
		new File(ROOT).mkdir();
	}

	public List<File> getFiles() {
		// returns a list of files names
		ArrayList<File> returningFiles = new ArrayList<File>();
		for (int i = 0; i < allFiles.size(); i++) {
			returningFiles.add(allFiles.get(i));
		}
		return returningFiles;
	}

	public void addChunk() {
		folder = new File("./dropbox/");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			File f1 = listOfFiles[i];
			for (int j = 0; j < allFiles.size(); j++) {
				File f2 = allFiles.get(j);
				if (!(f1.getName().equals(f2.getName()) && f1.lastModified() == f2.lastModified())) {
					allFiles.add(f1);
				}
			}
		}
	}

	public Chunk getChunk(String filename, int start, int length) {
		// is the actual bytes you need
		Chunk chunk = null;
		File file;
		// try {
		for (int i = 0; i < allFiles.size(); i++) {
			if (allFiles.get(i).getName() == filename) {
				file = allFiles.get(i);
				break;
			} else {
				file = new File(ROOT + "/filename", "rw");
			}
		}
		byte b[] = null;
		int counter = 0;
		while (b.length < length) {
			// b[counter] = ((DataInput) file).readByte();
			counter++;
		}
		// chunk = new Chunk(filename, b, start);

		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		return chunk;
	}

	public int getNumFiles() {
		return allFiles.size();
	}
}