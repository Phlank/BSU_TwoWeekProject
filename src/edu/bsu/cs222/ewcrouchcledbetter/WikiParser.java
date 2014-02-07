package edu.bsu.cs222.ewcrouchcledbetter;

import java.util.ArrayList;

public class WikiParser {

	public ArrayList<WikiEdit> parse(String data) {
		ArrayList<WikiEdit> edits = new ArrayList<WikiEdit>();
		ArrayList<String> rcLines = new ArrayList<String>();
		String[] tags = data.split(">");
		for (String tag : tags) {
			if (tag.substring(0, 3).equals("<rc"))
				rcLines.add(tag);
		}
		for (String s : rcLines) {
			String[] splitLine = s.split("\"");
			edits.add(new WikiEdit(splitLine[5], splitLine[7], splitLine[9]));
		}
		return edits;
	}

}
