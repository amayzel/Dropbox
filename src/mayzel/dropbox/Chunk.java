package mayzel.dropbox;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Chunk {

	private String fileName;
	private String encoded;
	private int offset;
	private byte[] decoded;

	public Chunk(String fileName, String encoded, int offset) {
		this.fileName = fileName;
		this.encoded = encoded;
		this.offset = offset;
	}

	public byte[] decode() throws Base64DecodingException {
		byte[] data = Base64.decode(encoded);
		return data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getEncoded() {
		return encoded;
	}

	public void setEncoded(String encoded) {
		this.encoded = encoded;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
