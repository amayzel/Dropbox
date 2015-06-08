package mayzel.dropbox;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class DropboxServer implements ReaderListener {

	private LinkedBlockingQueue<String> Message;
	private Socket socket;
	private ArrayList<Socket> sockets;
	private WriterThread writer;
	private FileCache fileCache;

	public DropboxServer() {
		Message = new LinkedBlockingQueue<String>();
		sockets = new ArrayList<Socket>();
		fileCache = new FileCache();

		writer = new WriterThread(Message, sockets);
		writer.start();

		try {
			ServerSocket ss = new ServerSocket(4444);
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
		switch (inputs[0]) {
		case "LIST":
			List list = new List(fileCache);
			list.perform(Message);
			System.out.println("Goes Through!");
			break;
		case "DOWNLOAD":
			String dfilename = inputs[1];
			byte doffset = Byte.valueOf(inputs[2]);
			byte dchunksize = Byte.valueOf(inputs[3]);
			Download download = new Download(dfilename, doffset, dchunksize);
			download.perform(Message);
			break;
		case "CHUNK":
			Chunk chunk = new Chunk(inputs[1], Long.valueOf(inputs[2]), Byte.valueOf(inputs[3]),
					Integer.valueOf(inputs[4]), null);
			chunk.perform(Message);
			break;
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
