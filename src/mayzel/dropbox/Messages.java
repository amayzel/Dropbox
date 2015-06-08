package mayzel.dropbox;

import java.util.concurrent.LinkedBlockingQueue;

public interface Messages {

	abstract void perform(LinkedBlockingQueue<String> queue);

}
