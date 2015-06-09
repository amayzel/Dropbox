package mayzel.dropbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class DownloadMessage extends Messages {

	private FileCache fileCache;

	public DownloadMessage(FileCache fileCache) {
		this.fileCache = fileCache;
		line = "DOWNLOAD";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input) {
		String filename = input[1];
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
		byte[] data = new byte[(int) file.length()];
		RandomAccessFile rf;
		try {
			rf = new RandomAccessFile(file, "rw");
			while (offset < file.length()) {
				rf.seek(offset);
				rf.read(data);
				String encoded = Base64.encode(data);
				Chunk chunk = new Chunk(filename, encoded, offset);
				String msg = "CHUNK " + filename + " " + file.lastModified() + " " + file.length() + " " + offset + " "
						+ encoded;
				ChunkMessage chunkMsg = new ChunkMessage(fileCache);
				input = msg.split(" ");
				queue.add(msg);
				chunkMsg.perform(queue, input);
				System.out.println("Download " + msg);
				offset += 512;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
