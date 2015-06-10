package mayzel.dropbox;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class DropboxServer implements ReaderListener {

	private LinkedBlockingQueue<String> queue;
	private Socket socket;
	private ArrayList<Socket> sockets;
	private WriterThread writer;
	private FileCache fileCache;
	private ArrayList<Messages> messages;

	public DropboxServer() {
		queue = new LinkedBlockingQueue<String>();
		sockets = new ArrayList<Socket>();
		fileCache = new FileCache("ServerDropbox/");
		messages = new ArrayList<Messages>();
		messages.add(new ChunkMessage());
		messages.add(new DownloadMessage());
		messages.add(new FileMessage());
		messages.add(new SyncMessage());
		messages.add(new ListMessage());

		writer = new WriterThread(queue, sockets);
		writer.start();

		try {
			ServerSocket ss = new ServerSocket(8181);
			while (true) {
				socket = ss.accept();
				sockets.add(socket);
				ReaderThread rt = new ReaderThread(socket, this);
				rt.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onLineRead(String line) {
		String message = line;
		String[] inputs = message.split(" ");
		Messages m = null;
		for (Messages msg : messages) {
			if (msg.matches(inputs[0])) {
				m = msg;
				break;
			}
		}
		if (m != null) {
			m.perform(queue, inputs, fileCache);
			if (inputs[0].equals("CHUNK")) {
				SyncMessage sync = new SyncMessage();
				String[] array = { "SYNC", inputs[1], inputs[2], inputs[3] };
				sync.perform(queue, array, fileCache);
			}
		}
	}

	@Override
	public void onCloseSocket(Socket socket) {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DropboxServer server = new DropboxServer();
	}

}
