package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

public class ClientChunkMessage extends Messages {

	public ClientChunkMessage() {
		line = "CHUNK";
	}

	@Override
	void perform(LinkedBlockingQueue<String> queue, String[] input, FileCache fileCache) {
		String fileName = "./ClientDropbox/" + input[1];
		try {
			fileCache.addChunk(new Chunk(fileName, input[5], Integer.valueOf(input[4])));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
	}

}
