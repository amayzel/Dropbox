package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

public class ChunkMessage extends Messages {

	public ChunkMessage() {
		line = "CHUNK";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input, FileCache fileCache) {
		String fileName = "./ServerDropbox/" + input[1];
		try {
			fileCache.addChunk(new Chunk(fileName, input[5], Integer.valueOf(input[4])));
			SyncMessage syncMsg = new SyncMessage();
			input[0] = "SYNC";
			syncMsg.perform(queue, input, fileCache);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
	}

}
