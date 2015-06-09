package mayzel.dropbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class FileCache {

	private static final String ROOT = "dropbox/";

	public FileCache() {
		new File(ROOT).mkdir();
	}

	public List<File> getFiles() {
		// returns a list of files names
		File folder = new File(ROOT);
		File[] listOfFiles = folder.listFiles();
		ArrayList<File> returningFiles = new ArrayList<File>(Arrays.asList(listOfFiles));

		return returningFiles;
	}

	public void addChunk(Chunk chunk) throws Base64DecodingException {
		List<File> files = getFiles();
		File file = null;
		boolean found = false;
		for (File f : files) {
			if (!(f.getName().equals(chunk.getFileName()))) {
				file = f;
			} else {
				found = true;
			}
		}
		if (!found) {
			byte[] data = chunk.decode();
			RandomAccessFile rf;
			try {
				rf = new RandomAccessFile(file, "rw");
				rf.seek(chunk.getOffset());
				rf.write(data);
				rf.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public Chunk getChunk(String filename, int start, int length) {
		// is the actual bytes you need
		Chunk chunk = null;
		byte[] data = null;
		File f = new File(filename);
		RandomAccessFile rf;
		try {
			rf = new RandomAccessFile(f, "rw");
			rf.seek(start);
			rf.read(data, start, length);
			rf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String encoded = Base64.encode(data);
		chunk = new Chunk(filename, encoded, start);
		return chunk;
	}

	public int getNumFiles() {
		return getFiles().size();
	}
}