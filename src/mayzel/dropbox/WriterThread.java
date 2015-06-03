package mayzel.dropbox;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class WriterThread extends Thread {

	private String Message;
	private ArrayList<Socket> sockets;

	private PrintWriter pw;

	public WriterThread(String message, ArrayList<Socket> sockets) {
		this.Message = message;
		this.sockets = sockets;
	}

	public void run() {
		Iterator<Socket> iter = sockets.iterator();
		while(iter.hasNext()){
			Socket s = iter.next();
			try{
				pw = new PrintWriter(s.getOutputStream());
				pw.println(Message);
				pw.flush();
			}
			catch(IOException e){
				iter.remove();
				e.printStackTrace();
			}
		}
	}

	public PrintWriter getPrintWriter() {
		return pw;
	}
}