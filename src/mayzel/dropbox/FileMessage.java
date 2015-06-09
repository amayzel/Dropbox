package mayzel.dropbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class FileMessage extends Messages {

	private FileCache fileCache;

	public FileMessage(FileCache fileCache) {
		this.fileCache = fileCache;
		line = "FILE";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input) {
		List<File> files = fileCache.getFiles();
		for (File f : files) {
			if (!(f.getName().equals(input[1])) && f.lastModified() != Long.valueOf(input[2])
					|| f.getName().equals(input[1]) && f.lastModified() != Long.valueOf(input[2])) {
				DownloadMessage msg = new DownloadMessage(fileCache);
				input[0] = "DOWNLOAD";
				msg.perform(queue, input);
				System.out.println("Download " + input[1]);
			}
		}
		String[] array = new String[6];
		array[0] = "CHUNK";
		array[1] = input[1];
		array[2] = input[2];
		array[3] = input[3];

		int offset = 0;
		array[4] = String.valueOf(offset);
		String filename = input[1];
		File file = new File(filename);
		byte[] data = new byte[(int) file.length()];
		RandomAccessFile rf;
		try {
			rf = new RandomAccessFile(file, "rw");
			while (offset < file.length()) {
				rf.seek(offset);
				rf.read(data);
				String encoded = Base64.encode(data);
				array[5] = encoded;
				Chunk chunk = new Chunk(filename, encoded, offset);
				String msg = "CHUNK " + filename + " " + file.lastModified() + " " + file.length() + " " + offset + " "
						+ encoded;
				ChunkMessage chunkMsg = new ChunkMessage(fileCache);
				input = msg.split(" ");
				queue.add(msg);
				chunkMsg.perform(queue, array);
				System.out.println("Upload " + msg);
				offset += 512;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
