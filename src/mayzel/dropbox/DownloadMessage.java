package mayzel.dropbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.LinkedBlockingQueue;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class DownloadMessage extends Messages {

	public DownloadMessage() {
		line = "DOWNLOAD";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input, FileCache fileCache) {
		String filename = "./ServerDropbox/"  + input[1];
		File file = new File(filename);

		int offset = 0;
		byte[] data = new byte[(int) file.length()];
		RandomAccessFile rf;
		try {
			rf = new RandomAccessFile(file, "rw");
			// get file length of file copying
			while (offset < rf.length()) {
				rf.seek(offset);
				rf.read(data);
				String encoded = Base64.encode(data);
				Chunk chunk = new Chunk(filename, encoded, offset);
				String msg = "CHUNK " + filename + " " + file.lastModified() + " " + file.length() + " " + offset + " "
						+ encoded;
				ChunkMessage chunkMsg = new ChunkMessage();
				input = msg.split(" ");
				queue.add(msg);
				chunkMsg.perform(queue, input, fileCache);
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
