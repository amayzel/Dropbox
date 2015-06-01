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
		String sendMessage = null;
		sockets = new ArrayList<Socket>();
		writer = new WriterThread(sendMessage, socket);
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
		String [] inputs = Message.split(" ");
		switch (inputs[0]) {
		case "LIST":
			List list = new List();
			Files files = new Files();
			sendMessage = "FILES " +  files.getNumberOfFiles();
			//list.perform();
			break;
		case "UPLOAD":
			String ufilename = inputs[1];
			byte uoffset = Byte.valueOf(inputs[2]);
			byte uchunksize = Byte.valueOf(inputs[3]);
			Upload upload = new Upload(ufilename, uoffset, uchunksize);
			upload.perform();
			break;
		case "DOWNLOAD":
			String dfilename = inputs[1];
			byte doffset = Byte.valueOf(inputs[2]);
			byte dchunksize = Byte.valueOf(inputs[3]);
			Download download = new Download(dfilename, doffset, dchunksize);
			download.perform();
			break;
		case "FILE":
			//File file = new File();
			break;
		case "FILES:":
			//Files files = new Files();
			break;
			
		}
		
	}

	@Override
	public void onLineRead(String line) {
		Message = line;
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
