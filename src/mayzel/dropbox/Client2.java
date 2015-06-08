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

public class Client2 implements ReaderListener {

	private String Message;
	private Socket socket;
	private OutputStream outputStream;
	private PrintWriter printWriter;
	private FileCache fileCache;
	private LinkedBlockingQueue<String> queue;

	public Client2() throws UnknownHostException, IOException {
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
			System.out.println("List " + Message);
			int offset = 0;
			byte[] data = new byte[512];

			RandomAccessFile rf;
			try {
				rf = new RandomAccessFile("./goodbye.txt", "rw");
				rf.seek(offset);
				rf.read(data);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String encoded = Base64.encode(data);
			Long lastModified = System.currentTimeMillis();
			Chunk chunk = new Chunk("goodbye.txt", lastModified, (byte) 0, offset, encoded, fileCache);
			String msg = "CHUNK goodbye.txt " + lastModified + " " + 0 + " " + offset + " " + encoded;
			sendMessage(msg);
			chunk.perform(queue);

			break;
		case "FILE":
			System.out.println(line);
			lastModified = Long.parseLong(inputs[2]);
			byte fileSize = Byte.parseByte(inputs[3]);
			List<File> files = fileCache.getFiles();
			offset = 0;
			data = new byte[512];
			for (File f : files) {
				if (!(inputs[1].equals(f.getName()) && lastModified == f.lastModified())) {
					RandomAccessFile rf1;
					try {
						rf1 = new RandomAccessFile(f, "rw");
						rf1.seek(offset);
						rf1.read(data);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					encoded = Base64.encode(data);
					chunk = new Chunk(inputs[1], lastModified, fileSize, offset, encoded, fileCache);
					msg = "CHUNK " + inputs[1] + " " + lastModified + " " + offset + " " + encoded;
					sendMessage(msg);
					chunk.perform(queue);
				}
				offset += data.length;
			}

			break;
		case "SYNC":
			List<File> fileList = fileCache.getFiles();
			for (File f : fileList) {
				if (!(f.getName().equals(inputs[1]) && f.lastModified() == Long.valueOf(inputs[2]))) {
					Download d = new Download(inputs[1], fileCache);
					String dMessage = "DOWNLOAD dropbox/" + inputs[1];
					sendMessage(dMessage);
					d.perform(queue);
				}
			}
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

	public static void main(String[] args) throws UnknownHostException, IOException {
		DropboxClient dc = new DropboxClient();
		dc.sendMessage("LIST");
	}

}
