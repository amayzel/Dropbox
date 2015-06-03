package mayzel.dropbox;

import java.io.PrintWriter;

public class List implements Messages {
	
	private PrintWriter pw;
	public List(PrintWriter pw) {
		this.pw = pw;
	}

	@Override
	public void perform() {
		//return numebr of files
		//lists all file names last modified and file size
		Files files = new Files();
		String sendMessage = "FILES " +  files.getNumberOfFiles();
		pw.println(sendMessage);
		pw.flush();
	}

}
