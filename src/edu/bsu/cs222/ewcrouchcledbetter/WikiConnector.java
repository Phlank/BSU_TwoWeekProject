package edu.bsu.cs222.ewcrouchcledbetter;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class WikiConnector {

	private static final String LAST_THREE_EDITS_URL = "http://wikipedia.org/w/api.php?action=query&list=recentchanges&format=xml&rcnamespace=0&rcprop=comment|timestamp|title&rclimit=3&rctype=edit";
	private static final String URL_START = "http://wikipedia.org/w/api.php?action=query&list=recentchanges&format=xml&rcnamespace=0&rcprop=comment|timestamp|title&rclimit=";
	private static final String URL_END = "&rctype=edit";
	private static final String REQUEST_KEY = "User-Agent";
	private static final String REQUEST_VALUE = "CS222 2-week project (http://www.cs.bsu.edu; cledbetter@bsu.edu)";

	private URL url;
	private URLConnection connection;

	public void connect() throws IOException {
		url = new URL(LAST_THREE_EDITS_URL);
		connection = url.openConnection();
		connection.setRequestProperty(REQUEST_KEY, REQUEST_VALUE);
		connection.connect();
	}
	
	public void connect(int numberOfEdits) throws IOException {
		String editedUrl = URL_START+numberOfEdits+URL_END;
		url = new URL(editedUrl);
		connection = url.openConnection();
		connection.setRequestProperty(REQUEST_KEY, REQUEST_VALUE);
		connection.connect();
	}

	public String getData() throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(connection.getInputStream(), writer, "UTF-8");
		return writer.toString();
	}

}
