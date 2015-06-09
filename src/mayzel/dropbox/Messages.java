package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class Messages {

	String line;

	public boolean matches(String msg) {
		return line.matches(msg);
	}

	abstract void perform(LinkedBlockingQueue<String> queue, String[] input);

}
