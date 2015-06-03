package mayzel.dropbox;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class DropboxServer implements ReaderListener {

	private String Message;
	private Socket socket;
	private ArrayList<Socket> sockets;
	private ArrayList<String> messages;
	private WriterThread writer;

	public DropboxServer() {
		String sendMessage = "HI";
		sockets = new ArrayList<Socket>();
		
		writer = new WriterThread(sendMessage, sockets);
		writer.start();
		
		try{
			ServerSocket ss = new ServerSocket(4444);
			while(true){
				socket = ss.accept();
				sockets.add(socket);
				ReaderThread rt = new ReaderThread(socket, this);
				rt.start();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		
		/*messages = new ArrayList<String>();
			messages.add("LIST");
			messages.add("UPLOAD");
			messages.add("DOWNLOAD");*/
		
		
	}

	@Override
	public void onLineRead(String line) {
		Message = line;
		String [] inputs = Message.split(" ");
		switch (inputs[0]) {
		case "LIST":
			List list = new List(writer.getPrintWriter());
			list.perform();
			System.out.println("Goes Through!");
			break;
		case "DOWNLOAD":
			String dfilename = inputs[1];
			byte doffset = Byte.valueOf(inputs[2]);
			byte dchunksize = Byte.valueOf(inputs[3]);
			Download download = new Download(dfilename, doffset, dchunksize);
			download.perform();
			break;
		case "CHUNK":
			break;
		}
		System.out.println("Server's on line reader");
	}

	@Override
	public void onCloseSocket(Socket socket) {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args){
		DropboxServer server = new DropboxServer();
		System.out.println(server.Message);
	}

}
