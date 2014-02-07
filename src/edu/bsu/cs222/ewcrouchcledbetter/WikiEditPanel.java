package edu.bsu.cs222.ewcrouchcledbetter;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class WikiEditPanel extends JPanel {

	private static final long serialVersionUID = -7688418722379781744L;

	private static final int PANE_HEIGHT = 50;
	private static final int ARTICLE_WIDTH = 150;
	private static final int TIME_WIDTH = 100;
	private static final int COMMENT_WIDTH = 400;
	private static final int PADDING_WIDTH = 2;

	JTextPane articlePane = new JTextPane();
	JTextPane timePane = new JTextPane();
	JTextPane commentPane = new JTextPane();

	public WikiEditPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		addTextPanes();
		resizeTextPanes();
		disableTextPanes();
		setLabelText();
		setLabelColor();
	}
	
	public WikiEditPanel(WikiEdit edit) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		resizeTextPanes();
		disableTextPanes();
		addTextPanes();
		setEditColor();
		update(edit);
	}
	
	private void addTextPanes() {
		add(articlePane);
		addPaddingCell();
		add(timePane);
		addPaddingCell();
		add(commentPane);
	}
	
	private void addPaddingCell() {
		JTextPane padding = new JTextPane();
		padding.setPreferredSize(new Dimension(PADDING_WIDTH, PANE_HEIGHT));
		padding.setBackground(Color.LIGHT_GRAY);
		padding.setEnabled(false);
		add(padding);
	}
	
	private void resizeTextPanes() {
		articlePane.setPreferredSize(new Dimension(ARTICLE_WIDTH, PANE_HEIGHT));
		timePane.setPreferredSize(new Dimension(TIME_WIDTH, PANE_HEIGHT));
		commentPane.setPreferredSize(new Dimension(COMMENT_WIDTH, PANE_HEIGHT));
	}
	
	private void disableTextPanes() {
		articlePane.setEnabled(false);
		timePane.setEnabled(false);
		commentPane.setEnabled(false);
	}
	
	private void setLabelText() {
		articlePane.setText("Article Name");
		timePane.setText("Date and Time");
		commentPane.setText("Comment");
	}
	
	private void setLabelColor() {
		articlePane.setBackground(Color.DARK_GRAY);
		timePane.setBackground(Color.DARK_GRAY);
		commentPane.setBackground(Color.DARK_GRAY);
	}
	
	private void setEditColor() {
		articlePane.setBackground(Color.BLACK);
		timePane.setBackground(Color.BLACK);
		commentPane.setBackground(Color.BLACK);
	}

	public void update(WikiEdit edit) {
		articlePane.setText(edit.getArticleName());
		timePane.setText(edit.getTime());
		commentPane.setText(edit.getComment());
	}
	
	
}
