package mayzel.dropbox;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class WriterThread extends Thread {

	private LinkedBlockingQueue<String> Message;
	private ArrayList<Socket> sockets;

	private PrintWriter pw;

	public WriterThread(LinkedBlockingQueue<String> message, ArrayList<Socket> sockets) {
		this.Message = message;
		this.sockets = sockets;
	}

	public void run() {
		while (true) {
			try {
				String line = Message.take();
				send(line);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void send(String msg) {
		Iterator<Socket> iter = sockets.iterator();
		while (iter.hasNext()) {
			Socket s = iter.next();
			try {
				pw = new PrintWriter(s.getOutputStream());
				pw.println(msg);
				pw.flush();
			} catch (IOException e) {
				iter.remove();
				e.printStackTrace();
			}
		}
	}

	public PrintWriter getPrintWriter() {
		return pw;
	}
}