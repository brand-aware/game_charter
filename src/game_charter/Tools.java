package game_charter;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.data.category.DefaultCategoryDataset;

import core.ProgressChart;
import game_charter.doc.ITools;

public class Tools extends CommonScreen implements ITools{
	
	public void initEnable() {
		desktopPane.moveToBack(empty);
		options.setEnabled(true);
		tools.setEnabled(true);
		mode.setEnabled(true);
		importProfile.setEnabled(true);
		exportProfile.setEnabled(true);
		config.setEnabled(true);
		suggestions.setEnabled(true);
		faq.setEnabled(true);
	}
	
	public boolean doSelectProfile() throws IOException {
		String[] profileList = properties.getProfileList();
		String selection = (String) JOptionPane.showInputDialog(
				chartFrame,
				"select user profile", 
				"load profile", 
				JOptionPane.PLAIN_MESSAGE, 
				new ImageIcon(properties.getCompany()), 
				profileList, 0);
		if(selection != null) {
			properties.setUsername(selection);
			return true;
		}
		return false;
	}
	
	public void prepData(String text) throws IOException {
		String path = properties.getGamePath(text);
		path += File.separator + "logs";
		if(!progressIO.isRead(text)) {
			progressIO.readLog(path, text);
		}
		if(!progressIO.isCondense(text)) {
			progressIO.condenseData(text);
		}
	}
	
	public void doLoadChart(String text) throws IOException {
		prepData(text);
		
		ArrayList<String> selectedGames = optionsData.getSelectedGames();
		DefaultCategoryDataset dataset = progressIO.createDataset(selectedGames, optionsData.getDisplay());
		ProgressChart chart = new ProgressChart();
		//chartPanel = null;
		if(chartPanel != null) {
			desktopPane.remove(chartPanel);
		}
		chartPanel = chart.createChartPannel(dataset, optionsData.getDisplay(), selectedGames.size(), optionsData.getDisplay());
		desktopPane.add(chartPanel);
		refreshChartArea();
	}
	
	public void refreshChartArea() {
		int currentY = headerY;
		int newY = headerY +  15 + 15 + 20 + 10 + ((5 + GAME_BUTTON_HEIGHT) * properties.getRegGames().size());
		if(newY < minY) {
			newY = minY;
			totalY = minY;
		}
		if(newY > totalY) {
			totalY = newY;
		}
		int chartY = headerY + 15 + 15 + 20 + 10 + CHART_HEIGHT;
		if(chartY > totalY) {
			newY = chartY;
			totalY = chartY;
		}
		
		int currentX = 75;
		for(int x = 0; x < regGames.size(); x++) {
			JButton button = regGames.get(x);
			button.setBounds(currentX, currentY, GAME_BUTTON_WIDTH, GAME_BUTTON_HEIGHT);
			currentY += 5 + GAME_BUTTON_HEIGHT;
			desktopPane.moveToFront(button);
		}
		
		currentX += 25 + GAME_BUTTON_WIDTH;
		chartPanel.setBounds(currentX, headerY, CHART_WIDTH, CHART_HEIGHT);
		desktopPane.moveToFront(chartPanel);
		
		chartFrame.setPreferredSize(new Dimension(BACKGROUND_WIDTH, totalY));
		chartFrame.pack();
	}
	
	public void doTextMode() throws IOException {
		prepData(lastSelected);
		if(chartPanel != null) {
			desktopPane.moveToBack(chartPanel);
			desktopPane.remove(chartPanel);
		}
		if(textScroll != null) {
			desktopPane.moveToBack(textScroll);
			desktopPane.remove(textScroll);
		}
		textArea = new JTextArea();
		textArea.setText(progressIO.createTextOutput(optionsData.getSelectedGames()));
		textArea.setEditable(false);
		textScroll = new JScrollPane(textArea);
		textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		int currentY = headerY;
		int newY = headerY +  15 + 15 + 20 + 10 + ((5 + GAME_BUTTON_HEIGHT) * properties.getRegGames().size());
		if(newY < minY) {
			newY = minY;
			totalY = minY;
		}
		if(newY > totalY) {
			totalY = newY;
		}
		int textY = headerY + 15 + 15 + 20 + 10 + TEXT_HEIGHT;
		if(textY > totalY) {
			newY = textY;
			totalY = textY;
		}
			
		int currentX = 75;
		for(int x = 0; x < regGames.size(); x++) {
			JButton button = regGames.get(x);
			button.setBounds(currentX, currentY, GAME_BUTTON_WIDTH, GAME_BUTTON_HEIGHT);
			currentY += 5 + GAME_BUTTON_HEIGHT;
			desktopPane.moveToFront(button);
		}
		currentX += 25 + GAME_BUTTON_WIDTH;
		textScroll.setBounds(currentX, headerY, TEXT_WIDTH, TEXT_HEIGHT);
		desktopPane.add(textScroll);
		desktopPane.moveToFront(textScroll);
		
		chartFrame.setPreferredSize(new Dimension(BACKGROUND_WIDTH, totalY));
		chartFrame.pack();
	}
	
	public void doCreateProfile() throws IOException {
		String username = "";
		boolean created = false;
		while(!created) {
			username = (String) JOptionPane.showInputDialog(chartFrame, 
					"enter username", 
					"create profile",
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon(properties.getCompany()),
					null, null);
			if(username != null) {
				created = properties.createUsername(username);
			}else {
				break;
			}
			if(!created) {
				JOptionPane.showMessageDialog(chartFrame, "invalid username", "warning!!!", JOptionPane.ERROR_MESSAGE, new ImageIcon(properties.getCompany()));
			}
		}
	}
	
	public void clearScreenCharts() {
		for(int x = 0; x < regGames.size(); x++) {
			desktopPane.moveToBack(regGames.get(x));
			desktopPane.remove(regGames.get(x));
		}
		if(chartPanel != null) {
			desktopPane.moveToBack(chartPanel);
			desktopPane.remove(chartPanel);
		}
		if(textScroll != null) {
			desktopPane.moveToBack(textScroll);
			desktopPane.remove(textScroll);
			desktopPane.remove(textArea);
		}
		regGames = new ArrayList<JButton>();
		gameList = new ArrayList<String>();
		optionsData.clearSelected();
		chartFrame.remove(desktopPane);
	}
	
	public void clearScreenLaunch() {
		for(int x = 0; x < quickLaunchButtons.size(); x++) {
			desktopPane.moveToBack(quickLaunchButtons.get(x));
			desktopPane.remove(quickLaunchButtons.get(x));
		}
		quickLaunchButtons = new ArrayList<JButton>();
		chartFrame.remove(desktopPane);
	}
	
	public void clearScreenSchedule() {
		for(int x = 0; x < schedButton.size(); x++) {
			desktopPane.moveToBack(schedButton.get(x));
			desktopPane.moveToBack(schedGoals.get(x));
			desktopPane.moveToBack(goalUnits.get(x));
			desktopPane.moveToBack(goalLabel.get(x));
			
			desktopPane.remove(schedButton.get(x));
			desktopPane.remove(schedGoals.get(x));
			desktopPane.remove(goalUnits.get(x));
			desktopPane.remove(goalLabel.get(x));
		}
		
		if(scheduleTitle != null) {
			desktopPane.moveToBack(scheduleTitle);
			desktopPane.moveToBack(addToSchedule);
			desktopPane.moveToBack(freqButton);
			desktopPane.moveToBack(completeLabel);
			
			desktopPane.remove(scheduleTitle);
			desktopPane.remove(addToSchedule);
			desktopPane.remove(freqButton);
			desktopPane.remove(completeLabel);
		}
		
		schedButton = new ArrayList<JButton>();
		schedGoals = new ArrayList<JTextField>();
		goalUnits = new ArrayList<JLabel>();
		goalLabel = new ArrayList<JLabel>();
		chartFrame.remove(desktopPane);
	}
	
	public void doDemoSchedule() {
		chartFrame.add(desktopPane);
		schedButton = new ArrayList<JButton>();
		schedGoals = new ArrayList<JTextField>();
		goalUnits = new ArrayList<JLabel>();
		goalLabel = new ArrayList<JLabel>();
		
		int currentX = SCHEDULE_MARGIN;
		int currentY = headerY - 45;
		
		scheduleTitle = new JLabel("Schedule of goals for user: DEMO");
		scheduleTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
		currentX = (BACKGROUND_WIDTH / 2) - (SCHEDULE_TITLE_WIDTH / 2) - 10;
		scheduleTitle.setBounds(currentX, currentY, SCHEDULE_TITLE_WIDTH, SCHEDULE_TITLE_HEIGHT);
		desktopPane.add(scheduleTitle);
		desktopPane.moveToFront(scheduleTitle);
		
		currentX = SCHEDULE_MARGIN;
		currentY += SCHEDULE_TITLE_HEIGHT + SCHEDULE_SPACE;
		
		String[] nameList = {"reflex_game", "identify_game", "typing_game", "pattern_game", "matching_game"};
		String[] optionList = {"timespent", "playcount", "totalscore", "score", "playcount"};
		String[] valueList = {"90", "3", "200", "400", "3"};
		String[] unitList = {"minutes", "times", "points", "points", "times"};
		
		for(int x = 0; x < nameList.length; x++) {
			String name = nameList[x];
			String option = optionList[x];
			String value = valueList[x];
			String units = unitList[x];
			
			JButton button = new JButton(name);
			button.setBounds(currentX, currentY, GAME_BUTTON_WIDTH, GAME_BUTTON_HEIGHT);
			button.setEnabled(false);
			desktopPane.add(button);
			desktopPane.moveToFront(button);
			schedButton.add(button);
			
			currentX += GAME_BUTTON_WIDTH + 5;
			JLabel label = new JLabel(option);
			label.setBounds(currentX, currentY, GOAL_LABEL_WIDTH, GOAL_LABEL_HEIGHT);
			desktopPane.add(label);
			desktopPane.moveToFront(label);
			goalLabel.add(label);
			
			currentX += GOAL_LABEL_WIDTH + 5;
			JTextField goal = new JTextField(value);
			goal.setEditable(false);
			goal.setEnabled(false);
			goal.setBounds(currentX, currentY, SCHEDULE_GOAL_WIDTH, SCHEDULE_GOAL_HEIGHT);
			desktopPane.add(goal);
			desktopPane.moveToFront(goal);
			schedGoals.add(goal);
			
			currentX += SCHEDULE_GOAL_WIDTH;
			JLabel unitsLabel = new JLabel(units);
			unitsLabel.setBounds(currentX, currentY, GOAL_UNIT_WIDTH, GOAL_UNIT_HEIGHT);
			desktopPane.add(unitsLabel);
			desktopPane.moveToFront(unitsLabel);
			goalUnits.add(unitsLabel);
			
			currentY += GAME_BUTTON_HEIGHT + 15;
			currentX = SCHEDULE_MARGIN;
		}
		
		currentX += 100;
		addToSchedule = new JButton("add");
		addToSchedule.setBounds(currentX, currentY, ADD_BUTTON_WIDTH, ADD_BUTTON_HEIGHT);
		addToSchedule.setEnabled(false);
		desktopPane.add(addToSchedule);
		desktopPane.moveToFront(addToSchedule);
		
		currentX = SCHEDULE_MARGIN;
		currentY += ADD_BUTTON_HEIGHT + SCHEDULE_SPACE;
		freqButton = new JButton("weekly");
		freqButton.setBounds(currentX, currentY, FREQ_LABEL_WIDTH, FREQ_LABEL_HEIGHT);
		desktopPane.add(freqButton);
		freqButton.setEnabled(false);
		desktopPane.moveToFront(freqButton);
		
		currentX += FREQ_LABEL_WIDTH + 10;
		completeLabel = new JLabel();
		completeLabel.setIcon(new ImageIcon(properties.getCompletePic()));
		completeLabel.setBounds(currentX, currentY, COMPLETE_WIDTH, COMPLETE_HEIGHT);
		desktopPane.add(completeLabel);
		desktopPane.moveToFront(completeLabel);
		
		totalY = getScheduleY(nameList.length);
		chartFrame.setPreferredSize(new Dimension(BACKGROUND_WIDTH, totalY));
		chartFrame.pack();
	}
	
	public int getScheduleY(int size) {
		int totalY = headerY + SCHEDULE_SPACE + 5;
		totalY += SCHEDULE_TITLE_HEIGHT + 5;
		totalY += (size * SCHEDULE_BUTTON_HEIGHT);
		totalY += (size * 15);
		totalY += ADD_BUTTON_HEIGHT + SCHEDULE_SPACE;
		totalY += SCHEDULE_SPACE;
		return totalY;
	}
	
	public void doSuggestion() {
		JOptionPane.showMessageDialog(chartFrame, 
				"quick launch and remember settings allow you to "
				+"use this app as a dashboard...\n\n"
				+"after playing around with basic charts, check out the demo package THEN the "
				+"schedule tools...\n\n", 
				"suggestions", 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(properties.getCompany()));
	}
	
	public void doFaq() {
		JOptionPane.showMessageDialog(chartFrame,
				"what are valid usernames?\na - z and A - Z characters ONLY\n\n"
				+"can I apply these tools to other apps/games I have?\nno\n\n"
				+"do I have any control over the detail/logging in this application?\nno\n\n",
				"faq",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(properties.getCompany()));
	}
	
	public void doAbout() {
		JOptionPane.showMessageDialog(chartFrame, 
				"game_charter\nproduct of ???\nbrand-aware 2019\n\ncontact:\nbrand-aware@outlook.com",
				"about",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(properties.getCompany()));
	}

}
