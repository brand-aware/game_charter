package game_charter;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class OptionsFilter extends ConfigOptionsFilter{

	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = 9066231528390741083L;
	
	private ProgressScreen ps;
	private OptionsData optionsData;
	private ActionHandler handler;
	private WindowListener listener;
	private JLayeredPane layeredPane;
	private JLabel title, gameNameList, selectorLabel;
	private JComboBox<String> dataSelector;
	private JCheckBox allowMultipleGames;
	private JButton ok, cancel;
	
	private String[] optionsList;
	
	public OptionsFilter(ProgressScreen screen, OptionsData data, String selected) throws IOException {
		super();
		ps = screen;
		optionsData = data;
		
		handler = new ActionHandler();
		listener = new WindowListener();
		setPreferredSize(new Dimension(TOTAL_HORIZONTAL, TOTAL_VERTICAL));
		setLocation(OFFSET_HORIZONTAL, OFFSET_VERTICAL);
		setFrameIcon(new ImageIcon(
				optionsData.getProperties().getCompanyIframe()));
		setResizable(false);
		addInternalFrameListener(listener);
		
		readSelectorOptions();
		createPage(selected);
	}
	
	public void createPage(String selected) {
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, TOTAL_HORIZONTAL, TOTAL_VERTICAL);
		
		int currentX = 25;
		int currentY = 5;
		title = new JLabel("CHART OPTIONS");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, TITLE_FONT_SIZE));
		title.setBounds(currentX, currentY, TITLE_HORIZONTAL, TITLE_VERTICAL);
		
		currentY += TITLE_VERTICAL + 5;
		gameNameList = new JLabel();
		String text = "";
		ArrayList<String> games = optionsData.getSelectedGames();
		if(games.size() < 1) {
			text = DEFAULT_GAME;
		}else {
			for(int x = 0; x < games.size(); x++) {
				text += games.get(x) + ", ";
			}
			text = text.substring(0, text.lastIndexOf(","));
		}
		gameNameList.setText(text);
		gameNameList.setBounds(15, currentY, SUBTITLE_HORIZONTAL, SUBTITLE_VERTICAL);
		
		currentY += SUBTITLE_VERTICAL + 5;
		selectorLabel = new JLabel(SELECTOR_LABEL);
		selectorLabel.setBounds(15, currentY, 60, 30);
		
		dataSelector = new JComboBox<String>(optionsList);
		dataSelector.setBounds(60 + 5 + 15, currentY, SELECTOR_HORIZONTAL, SELECTOR_VERTICAL);
		dataSelector.setSelectedItem(selected);
		
		currentY += SELECTOR_VERTICAL + 5;
		allowMultipleGames = new JCheckBox(CHECK_LABEL);
		allowMultipleGames.setSelected(optionsData.getMultiSet());
		allowMultipleGames.setBounds(15, currentY, CHECK_HORIZONTAL, CHECK_VERTICAL);
		
		currentY += CHECK_VERTICAL + 5;
		currentX = (TOTAL_HORIZONTAL / 2) - ((BUTTON_HORIZONTAL *2) / 2) - 10;
		ok = new JButton("ok");
		ok.setBounds(currentX, currentY, BUTTON_HORIZONTAL, BUTTON_VERTICAL);
		ok.addActionListener(handler);
		
		currentX += BUTTON_HORIZONTAL + 5;
		cancel = new JButton("cancel");
		cancel.setBounds(currentX, currentY, BUTTON_HORIZONTAL, BUTTON_VERTICAL);
		cancel.addActionListener(handler);
		
		layeredPane.add(title);
		layeredPane.add(gameNameList);
		layeredPane.add(selectorLabel);
		layeredPane.add(dataSelector);
		layeredPane.add(allowMultipleGames);
		layeredPane.add(ok);
		layeredPane.add(cancel);
		
		add(layeredPane);
		pack();
		setVisible(true);		
	}
	
	private class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == ok) {
				String selection = optionsList[dataSelector.getSelectedIndex()];
				optionsData.setDisplay(selection);
				optionsData.setMultiSet(allowMultipleGames.isSelected());
				ps.saveOptions(optionsData);
				if(optionsData.getMultiSet()) {
					
				}else {
					try {
						if(ps.getChartFlag()) {
							String text = gameNameList.getText();
							if(text.compareTo(DEFAULT_GAME) != 0) {
								ps.doLoadChart(gameNameList.getText());
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				ps.enable();
				dispose();
			}else if(event.getSource() == cancel) {
				dispose();
			}
		}
	}
	
	public void readSelectorOptions() throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(
				optionsData.getProperties().getSelectorList()));
		while(reader.ready()) {
			String line = reader.readLine();
			list.add(line);
		}
		reader.close();
		optionsList = new String[list.size()];
		for(int x = 0; x < list.size(); x++) {
			optionsList[x] = list.get(x);
		}
	}
	
	private class WindowListener implements InternalFrameListener {

		@Override
		public void internalFrameActivated(InternalFrameEvent arg0) {	}

		@Override
		public void internalFrameClosed(InternalFrameEvent event) {
			ps.enable();
			
		}

		@Override
		public void internalFrameClosing(InternalFrameEvent arg0) {		}

		@Override
		public void internalFrameDeactivated(InternalFrameEvent arg0) {		}

		@Override
		public void internalFrameDeiconified(InternalFrameEvent arg0) {		}

		@Override
		public void internalFrameIconified(InternalFrameEvent arg0) {	}

		@Override
		public void internalFrameOpened(InternalFrameEvent arg0) {	}
		
	}
}
