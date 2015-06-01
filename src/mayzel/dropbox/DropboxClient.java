package mayzel.dropbox;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class DropboxClient implements ReaderListener {

	private Socket socket;
	private OutputStream outputStream;
	private PrintWriter printWriter;
	public DropboxClient() throws UnknownHostException, IOException {
		this.socket = new Socket("localhost", 4444);
		this.outputStream = socket.getOutputStream();
		this.printWriter = new PrintWriter(outputStream);
		
		ReaderThread rt = new ReaderThread(socket, this);
		rt.start();
	}
	
	public void sendMessage(String message){
		printWriter.println(message);
		printWriter.flush();
	}
	
	@Override
	public void onLineRead(String line) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onCloseSocket(Socket socket) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
