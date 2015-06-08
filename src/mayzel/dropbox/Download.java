package mayzel.dropbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Download implements Messages {

	private String filename;
	private FileCache fileCache;

	public Download(String filename, FileCache fileCache) {
		this.filename = filename;
		this.fileCache = fileCache;
	}

	@Override
	public void perform(LinkedBlockingQueue<String> queue) {
		// send files to cleint
		List<File> files = fileCache.getFiles();
		File file = null;
		for (File f : files) {
			if (f.getName().equals(filename)) {
				file = f;
				break;
			} else {

			}
		}
		int offset = 0;
		byte[] data = new byte[512];
		RandomAccessFile rf;
		try {
			rf = new RandomAccessFile(file, "rw");
			while (offset < file.length()) {
				rf.seek(offset);
				rf.read(data);
				String encoded = Base64.encode(data);
				Chunk chunk = new Chunk(filename, file.lastModified(), (byte) file.length(), offset, encoded, fileCache);
				String msg = "CHUNK " + filename + " " + file.lastModified() + " " + file.length() + " " + offset + " "
						+ encoded;
				System.out.println("Download " + msg);
				chunk.perform(queue);
				offset += 512;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
