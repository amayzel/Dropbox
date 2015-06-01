package mayzel.dropbox;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class WriterThread extends Thread {

	private String Message;
	private Socket socket;

	public WriterThread(String message, Socket socket) {
		this.Message = message;
		this.socket = socket;
	}

	public void run() {
		while (true) {
			try{
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			pw.println(Message);
			pw.flush();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}