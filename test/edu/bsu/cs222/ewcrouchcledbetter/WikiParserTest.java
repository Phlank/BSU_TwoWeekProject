package edu.bsu.cs222.ewcrouchcledbetter;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class WikiParserTest {
	
	private WikiParser parser = new WikiParser();
	private ArrayList<WikiEdit> edits;
	private int numberOfEdits = 3;
	
	private String getFileData(String path) throws IOException {
		FileInputStream is = new FileInputStream(path);
		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer, "UTF-8");
		return writer.toString();
	}
	
	@Test
	public void testFile1() throws IOException {
		String data = getFileData("xml/test1.txt");
		edits = parser.parse(data);
		String[] expectedArticles = {"Zendaya", "Carlos Santana", "David Bailey"};
		String[] expectedComments = {"", "/* Amplifiers */ for readability and accuracy", "/* Early life */ tweaked"};
		for (int i = 0; i < numberOfEdits; i++) {
			Assert.assertEquals(expectedArticles[i], edits.get(i).getArticleName());
			Assert.assertEquals(expectedComments[i], edits.get(i).getComment());
		}
	}
	
	@Test
	public void testFile2() throws IOException {
		String data = getFileData("xml/test2.txt");
		edits = parser.parse(data);
		String[] expectedArticles = {"Chinguetti", "Jared Robinson", "Jonathan D. G. Jones"};
		String[] expectedComments = {"/* World heritage site */Fix [[Help:CS1_errors#deprecated_params|CS1 deprecated date parameter errors]]", "", ""};
		for (int i = 0; i < numberOfEdits; i++) {
			Assert.assertEquals(expectedArticles[i], edits.get(i).getArticleName());
			Assert.assertEquals(expectedComments[i], edits.get(i).getComment());
		}
	}

}
