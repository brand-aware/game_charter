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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2020
 * 
 */
public class ScheduleOptions extends CommonSchedule{

	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = -4664507689624490997L;
	
	private WindowListener windowListener;
	private ButtonHandler buttonListener;
	
	Schedule schedule;
	ScheduleData existingData;
	boolean previous = false;
	boolean full = false;
		
	public ScheduleOptions() {
		super();
		
		windowListener = new WindowListener();
		buttonListener = new ButtonHandler();
		
		layeredPane = new JLayeredPane();
		title = new JLabel(TITLE_LABEL);
		gameDisplay = new JLabel();
		gameCombo = new JComboBox<String>();
		input = new JTextField();
		inputLabel = new JLabel();
		ok = new JButton(OK_LABEL);
		exit = new JButton(EXIT_LABEL);
	}
	
	public void createPage() throws IOException {
		setLocation(LOCATION_X, LOCATION_Y);
		addInternalFrameListener(windowListener);
		setPreferredSize(new Dimension(TOTAL_X, TOTAL_Y));
		
		JLabel background = new JLabel();
		background.setIcon(new ImageIcon(properties.getBackground()));
		background.setBounds(0, 0, TOTAL_X, TOTAL_Y);
		int currentX = (TOTAL_X / 2) - (TITLE_WIDTH / 2);
		int currentY = 15;
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, TITLE_FONT_SIZE));
		title.setBounds(currentX, currentY, TITLE_WIDTH, TITLE_HEIGHT);
		
		currentY += TITLE_HEIGHT + 5;
		gameDisplay.setText(GAME_DISPLAY_TEXT);
		gameDisplay.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, SUB_FONT_SIZE));
		if(previous) {
			String text = gameDisplay.getText();
			text += existingData.getName();
			gameDisplay.setText(text);
			currentX = (TOTAL_X / 2) - (GDISPLAY_WIDTH / 2);
			gameDisplay.setBounds(currentX, currentY, GDISPLAY_WIDTH, GDISPLAY_HEIGHT);
		}
		if(!previous) {
			currentX += GDISPLAY_WIDTH + 5;
			loadGameSelector();
			currentX = (TOTAL_X / 2) - ((GDISPLAY_WIDTH - 100) / 2);
			gameDisplay.setBounds(currentX, currentY, GDISPLAY_WIDTH - 100, GDISPLAY_HEIGHT);
			gameCombo.setBounds(currentX, currentY, GAME_COMBO_WIDTH, GAME_COMBO_HEIGHT);
		}
		
		currentX = 15;
		currentY += GDISPLAY_HEIGHT + 5;
		String[] selectorList = loadSelectorOptions();
		selector = new JComboBox<String>(selectorList);
		if(previous) {
			int index = findSelectorOption(existingData.getOption(), selectorList);
			selector.setSelectedIndex(index);
		}
		selector.setBounds(currentX, currentY, SELECTOR_WIDTH, SELECTOR_HEIGHT);
		selector.addActionListener(buttonListener);
		
		currentX += SELECTOR_WIDTH + 5;
		input.setBounds(currentX, currentY, INPUT_WIDTH, INPUT_HEIGHT);
		if(previous) {
			input.setText(existingData.getValue());
		}
		
		currentX += INPUT_WIDTH + 5;
		String text = getInputText(choice);
		if(previous) {
			inputLabel.setText(existingData.getUnits());
		}else {
			inputLabel.setText(text);
		}
		inputLabel.setBounds(currentX, currentY, INPUT_LABEL_WIDTH, INPUT_LABEL_HEIGHT);
		
		currentX = (TOTAL_X / 2) - (OK_BUTTON_WIDTH);
		currentY += INPUT_HEIGHT + 5;
		ok.setBounds(currentX, currentY, OK_BUTTON_WIDTH, OK_BUTTON_HEIGHT);
		ok.addActionListener(buttonListener);
		
		currentX += OK_BUTTON_WIDTH + 5;
		exit.setBounds(currentX, currentY, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		exit.addActionListener(buttonListener);
		
		if(full) {
			ok.setEnabled(false);
			selector.setEnabled(false);
			input.setEnabled(false);
		}
		
		layeredPane.add(background);
		layeredPane.add(title);
		layeredPane.add(gameDisplay);
		layeredPane.add(gameCombo);
		layeredPane.add(selector);
		layeredPane.add(input);
		layeredPane.add(inputLabel);
		layeredPane.add(ok);
		layeredPane.add(exit);
		
		layeredPane.moveToFront(title);
		layeredPane.moveToFront(gameDisplay);
		layeredPane.moveToFront(gameCombo);
		layeredPane.moveToFront(selector);
		layeredPane.moveToFront(input);
		layeredPane.moveToFront(inputLabel);
		layeredPane.moveToFront(ok);
		layeredPane.moveToFront(exit);
		
		add(layeredPane);
		setVisible(true);
		pack();
	}
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == ok) {
				int selection = selector.getSelectedIndex();
				String text = input.getText();
				String name = "";
				if(previous) {
					name = gameDisplay.getText().replace(GAME_DISPLAY_TEXT, "");
				}else if(!previous) {
					name = gameCombo.getItemAt(gameCombo.getSelectedIndex());
				}
				String option = selector.getItemAt(selection);
				String units = getInputText(selector.getSelectedIndex());
				ScheduleData scheduleData = new ScheduleData(name, option, text, units);
				try {
					pScreen.saveScheduleOptions(scheduleData);
					pScreen.clearScreenSchedule();
					pScreen.doSchedule();
				} catch (IOException e) {
					e.printStackTrace();
				}
				dispose();
			}else if(event.getSource() == selector) {
				String units = getInputText(selector.getSelectedIndex());
				inputLabel.setText(units);
			}else if(event.getSource() == exit) {
				dispose();
			}
		}
	}
	
	private class WindowListener implements InternalFrameListener{

		@Override
		public void internalFrameOpened(InternalFrameEvent e) {}
		@Override
		public void internalFrameClosing(InternalFrameEvent e) {}
		@Override
		public void internalFrameClosed(InternalFrameEvent e) {
			pScreen.enable();			
		}
		@Override
		public void internalFrameIconified(InternalFrameEvent e) {}
		@Override
		public void internalFrameDeiconified(InternalFrameEvent e) {}
		@Override
		public void internalFrameActivated(InternalFrameEvent e) {}
		@Override
		public void internalFrameDeactivated(InternalFrameEvent e) {}
	}
	
	public String[] loadSelectorOptions() throws IOException{
		String path = properties.getSelectorList();
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		while(reader.ready()) {
			String line = reader.readLine();
			list.add(line);
		}
		reader.close();
		
		String[] data = new String[list.size()];
		for(int x = 0; x < list.size(); x++) {
			data[x] = list.get(x);
		}
		return data;
	}
	
	public int findSelectorOption(String name, String[] selector) {
		int index = 0;
		for(int x = 0; x < selector.length; x++) {
			String value = selector[x];
			if(name.compareTo(value) == 0) {
				index = x;
			}
		}
		
		return index;
	}
	
	public void loadGameSelector() {
		ArrayList<String> gameList = properties.getRegGames();
		ArrayList<String> nameList = schedule.getAllNames();
		for(int x = 0; x < gameList.size(); x++) {
			for(String name: nameList) {
				if(name.compareTo(gameList.get(x)) == 0) {
					gameList.remove(x);
					x--;
					break;
				}
			}
		}
		String[] list;
		if(gameList.size() == 0) {
			list = new String[] {"<full>"};
			full = true;
		}else {
			list = new String[gameList.size()];
			for(int x = 0; x < list.length; x++) {
				list[x] = gameList.get(x);
			}
		}
		gameCombo = new JComboBox<String>(list);
	}
	
	public String getInputText(int choice) {
		String text = "";
		if(choice == 0) {
			text = "points";
		}else if(choice == 1) {
			text = "mins";
		}else if(choice == 2) {
			text = "points";
		}else if(choice == 3) {
			text = "times";
		}
		return text;
	}
	
	public void init(ProgressScreen ps, ScheduleData data, Properties p) throws IOException {
		properties = p;
		pScreen = ps;
		if(data != null) {
			existingData = data;
			previous = true;			
		}
		createPage();
	}
	public void init(ProgressScreen ps, Schedule schd, Properties p) throws IOException {
		properties = p;
		pScreen = ps;
		if(schd != null) {
			schedule = schd;
			previous = false;			
		}
		createPage();
	}

}
