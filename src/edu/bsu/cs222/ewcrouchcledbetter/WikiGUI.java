package edu.bsu.cs222.ewcrouchcledbetter;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WikiGUI extends JFrame {
	
	private static final long serialVersionUID = -4556317294147870948L;
	
	private static final int UPDATE_INTERVAL = 1;
	
	private static final String WINDOW_NAME = "Wikipedia Edit Viewer";
	private static final String WIKI_CONNECTION_ERROR = "WARNING: Could not connect to Wikipedia";
	private static final String LIVE_MODE_BUTTON_ENABLED_TEXT = "Disable Live Mode";
	private static final String LIVE_MODE_BUTTON_DISABLED_TEXT = "Enable Live Mode";
	private static final String UPDATE_BUTTON_TEXT = "Update";
	private static final String PLUS_BUTTON_TEXT = "+";
	private static final String MINUS_BUTTON_TEXT = "-";
	
	private int numberOfEdits = 3;
	
	private ArrayList<WikiEditPanel> editPanels = new ArrayList<WikiEditPanel>(numberOfEdits);
	private ArrayList<WikiEdit> edits = new ArrayList<WikiEdit>(numberOfEdits);
	
	private WikiConnector connector = new WikiConnector();
	private WikiParser parser = new WikiParser();
	
	private JPanel mainPanel = new JPanel();
	private JPanel editsPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private JButton updateButton = new JButton();
	private JButton liveModeButton = new JButton();
	private JButton plusButton = new JButton();
	private JButton minusButton = new JButton();
	
	private boolean liveMode = false;
	
	private ScheduledExecutorService ses;
	
	public WikiGUI() {
		super(WINDOW_NAME);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		configurePanels();
		update();
		pack();
	}
	
	private void configurePanels() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		editsPanel.setLayout(new BoxLayout(editsPanel, BoxLayout.Y_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		updateWikiInfo();
		addLabelPanel();
		addEditPanels();
		addButtons();
		mainPanel.add(editsPanel);
		mainPanel.add(buttonPanel);
		add(mainPanel);
	}
	
	private void addLabelPanel() {
		mainPanel.add(new WikiEditPanel());
	}
	
	private void updateWikiInfo() {
		try {
			connector.connect(numberOfEdits);
			edits = parser.parse(connector.getData());
		} catch (IOException e) {
			System.out.println(WIKI_CONNECTION_ERROR);
		}
	}
	
	private void addEditPanels() {
		for (int i = 0; i < numberOfEdits; i++) {
			editPanels.add(new WikiEditPanel(edits.get(i)));
			editsPanel.add(editPanels.get(i));
		}
	}
	
	private void addButtons() {
		addUpdateButton();
		addLiveModeButton();
		addPlusButton();
		addMinusButton();
	}
	
	private void addUpdateButton() {
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		updateButton.setText(UPDATE_BUTTON_TEXT);
		updateButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonPanel.add(updateButton);
	}
	
	private void addLiveModeButton() {
		liveModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (liveMode) {
					disableLiveMode();
				} else {
					enableLiveMode();
				}
			}
		});
		liveModeButton.setText(LIVE_MODE_BUTTON_DISABLED_TEXT);
		liveModeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonPanel.add(liveModeButton);
	}
	
	private void disableLiveMode() {
		liveMode = false;
		ses.shutdown();
		liveModeButton.setText(LIVE_MODE_BUTTON_DISABLED_TEXT);
	}
	
	private void enableLiveMode() {
		liveMode = true;
		ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				update();
			}
		}, 0, UPDATE_INTERVAL, TimeUnit.SECONDS);
		liveModeButton.setText(LIVE_MODE_BUTTON_ENABLED_TEXT);
	}
	
	private void addPlusButton() {
		plusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addEditPanel();
			}
		});
		plusButton.setText(PLUS_BUTTON_TEXT);
		buttonPanel.add(plusButton);
	}
	
	private void addEditPanel() {
		numberOfEdits++;
		editPanels.add(new WikiEditPanel(new WikiEdit()));
		editsPanel.add(editPanels.get(editPanels.size()-1));
		pack();
	}
	
	private void addMinusButton() {
		minusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeEditPanel();
			}
		});
		minusButton.setText(MINUS_BUTTON_TEXT);
		buttonPanel.add(minusButton);
	}
	
	private void removeEditPanel() {
		if (numberOfEdits > 1) {
			numberOfEdits--;
			editsPanel.remove(editPanels.get(editPanels.size()-1));
			editPanels.remove(editPanels.size()-1);
			pack();
		}
	}
	
	private void update() {
		updateWikiInfo();
		updateWikiEdits();
	}
	
	private void updateWikiEdits() {
		for (int i = 0; i < numberOfEdits; i++) {
			editPanels.get(i).update(edits.get(i));
		}
	}
	
	public static void main(String[] args) {
		WikiGUI gui = new WikiGUI();
		gui.setVisible(true);
	}

}
