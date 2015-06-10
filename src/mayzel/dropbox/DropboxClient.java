package mayzel.dropbox;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class DropboxClient implements ReaderListener {

	private Socket socket;
	private OutputStream outputStream;
	private PrintWriter printWriter;
	private FileCache fileCache;
	private LinkedBlockingQueue<String> queue;
	private ArrayList<Messages> messages;

	public DropboxClient() throws UnknownHostException, IOException {
		this.socket = new Socket("localhost", 8181);
		this.outputStream = socket.getOutputStream();
		this.printWriter = new PrintWriter(outputStream);
		fileCache = new FileCache("ClientDropbox/");
		queue = new LinkedBlockingQueue<String>();
		messages = new ArrayList<Messages>();
		messages.add(new ChunkMessage());
		messages.add(new SyncMessage());
		messages.add(new DownloadMessage());
		messages.add(new FileMessage());
		messages.add(new Files());
		messages.add(new ListMessage());

		ReaderThread rt = new ReaderThread(socket, this);
		rt.start();
	}

	public void sendMessage(String message) {
		printWriter.println(message);
		printWriter.flush();
	}

	@Override
	public void onLineRead(String line) {
		String[] inputs = line.split(" ");
		Messages m = null;
		for (Messages msg : messages) {
			if (msg.matches(inputs[0])) {
				m = msg;
				System.out.println(line);
				break;
			}
		}
		m.perform(queue, inputs, fileCache);
	}

	@Override
	public void onCloseSocket(Socket socket) {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		DropboxClient dc = new DropboxClient();
		dc.sendMessage("LIST");
	}

}
