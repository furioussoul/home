package io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLResource implements Resource{

	private URL url;
	
	URLResource(URL url) {
		this.url = url;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		URLConnection conn = url.openConnection();
		conn.connect();
		return conn.getInputStream();
	}

}
