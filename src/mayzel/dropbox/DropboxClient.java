package mayzel.dropbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class DropboxClient implements ReaderListener {

	private String Message;
	private Socket socket;
	private OutputStream outputStream;
	private PrintWriter printWriter;
	private FileCache fileCache;
	private LinkedBlockingQueue<String> queue;

	public DropboxClient() throws UnknownHostException, IOException {
		this.socket = new Socket("localhost", 4444);
		this.outputStream = socket.getOutputStream();
		this.printWriter = new PrintWriter(outputStream);
		fileCache = new FileCache();
		queue = new LinkedBlockingQueue<String>();

		ReaderThread rt = new ReaderThread(socket, this);
		rt.start();
	}

	public void sendMessage(String message) {
		printWriter.println(message);
		printWriter.flush();
	}

	@Override
	public void onLineRead(String line) {
		Message = line;
		String[] inputs = line.split(" ");
		switch (inputs[0]) {
		case "FILES":
			System.out.println(Message);
			break;
		case "FILE":
			System.out.println(line);
			long lastModified = Long.parseLong(inputs[2]);
			byte fileSize = Byte.parseByte(inputs[3]);
			List<File> files = fileCache.getFiles();
			int offset = 0;
			byte[] data = new byte[512];
			for (File f : files) {
				if (!(inputs[1].equals(f.getName()) && lastModified == f.lastModified())) {
					RandomAccessFile rf;
					try {
						rf = new RandomAccessFile(f, "rw");
						rf.seek(offset);
						rf.read(data);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					String encoded = Base64.encode(data);
					Chunk chunk = new Chunk(inputs[1], lastModified, fileSize, offset, encoded);
					chunk.perform(queue);
				}
				offset += data.length;
			}

			break;
		}
	}

	@Override
	public void onCloseSocket(Socket socket) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		DropboxClient dc = new DropboxClient();
		dc.sendMessage("LIST");
	}

}
