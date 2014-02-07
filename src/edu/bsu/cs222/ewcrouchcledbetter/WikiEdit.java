package edu.bsu.cs222.ewcrouchcledbetter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

public class WikiEdit {

	private String articleName;
	private String time;
	private String comment;

	public WikiEdit() {
		articleName = "";
		time = "";
		comment = "";
	}
	
	public WikiEdit(String articleName, String time, String comment) {
		this.articleName = articleName;
		this.time = time;
		this.comment = comment;
		resolveTime();
	}

	public String getArticleName() {
		return articleName;
	}

	public String getTime() {
		return time;
	}

	public String getComment() {
		return comment;
	}

	public void resolveTime() {
		time = time.substring(0, time.length()-1).concat(".000");
		LocalDateTime dt = new LocalDateTime(time);
		DateTime finalDt = dt.toDateTime(DateTimeZone.getDefault());
		time = finalDt.toString().replaceAll("T", " ").substring(0, time.length()-4);
	}

}
