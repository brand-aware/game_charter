package game_charter;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import core.DateCheck;
import core.ProgressChart;
import core.ProgressIO;
/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
public class ProgressScreen extends Tools{
	
	private ButtonHandler handler;
	private IconHandler iconHandler;
	private MenuHandler menuHandler;
	
	private DateCheck dateCheck;
	
	public ProgressScreen(Properties props) throws IOException {
		properties = props;
		schedule = new Schedule(properties);
		
		progressChart = new ProgressChart();
		progressIO = new ProgressIO(properties);
		optionsData = new OptionsData(properties);
		
		handler = new ButtonHandler();
		iconHandler = new IconHandler();
		menuHandler = new MenuHandler();
		
		schedButton = new ArrayList<JButton>();
		regGames = new ArrayList<JButton>();
		quickLaunchButtons = new ArrayList<JButton>();
		launchLookup = new HashMap<JButton, String>();
		gameList = properties.getStandardGameList();
		
		chartFrame = new JFrame("game_charter");
		chartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chartFrame.setResizable(false);
		Image company = Toolkit.getDefaultToolkit().getImage(properties.getCompany());
		chartFrame.setIconImage(company);
		chartFrame.setLocation(75, 75);
		desktopPane = new JDesktopPane();
		empty = new JLabel();
		
		totalY = 15 + LOGO_HEIGHT + 15 + 15 + 20 + GAME_MENU_BAR + 200;
		minY = totalY;
		chartFrame.setPreferredSize(new Dimension(BACKGROUND_WIDTH, totalY));
	}
	
	private void createView() throws IOException {
		
		//setup menu area
		menuBar = new JMenuBar();
		file = new JMenu("file");
		options = new JMenu("options");
		tools = new JMenu("tools");
		mode = new JMenu("mode");
		config = new JMenu("config");
		help = new JMenu("help");
		
		loadProfile = new JMenuItem("load profile");
		loadProfile.addActionListener(menuHandler);
		createProfile = new JMenuItem("create profile");
		createProfile.addActionListener(menuHandler);
		importProfile = new JMenuItem("import");
		importProfile.addActionListener(menuHandler);
		exportProfile = new JMenuItem("export");
		exportProfile.addActionListener(menuHandler);
		exit = new JMenuItem("exit");
		exit.addActionListener(menuHandler);
		
		registerGame = new JMenuItem("register game");
		registerGame.addActionListener(menuHandler);
		removeGame = new JMenuItem("remove game");
		removeGame.addActionListener(menuHandler);
		removeGame.setEnabled(false);
		checkLogs = new JMenuItem("check logs");
		checkLogs.addActionListener(menuHandler);
		
		chartFilters = new JMenuItem("chart filters");
		chartFilters.addActionListener(menuHandler);
		textMode = new JMenuItem("text mode");
		textMode.addActionListener(menuHandler);
		scheduleTools = new JMenuItem("schedule tools");
		scheduleTools.addActionListener(menuHandler);
		scheduleTools.setEnabled(false);
		demoPackage = new JMenuItem("demo package");
		demoPackage.addActionListener(menuHandler);
		demoPackage.setEnabled(false);
		
		dispSchedule = new JCheckBoxMenuItem("display schedule");
		dispSchedule.addActionListener(menuHandler);
		quickLaunch = new JCheckBoxMenuItem("quick launch");
		quickLaunch.addActionListener(menuHandler);
		charts = new JCheckBoxMenuItem("charts");
		charts.addActionListener(menuHandler);
		charts.setSelected(true);
		
		normalStart = new JCheckBoxMenuItem("open to quicklaunch");
		normalStart.addActionListener(menuHandler);
		normalStart.setSelected(false);
		autoUpdate = new JCheckBoxMenuItem("auto update");
		autoUpdate.addActionListener(menuHandler);
		autoUpdate.setSelected(true);
		
		suggestions = new JMenuItem("suggestions");
		suggestions.addActionListener(menuHandler);
		faq = new JMenuItem("faq");
		faq.addActionListener(menuHandler);
		about = new JMenuItem("about");
		about.addActionListener(menuHandler);
		
		file.add(loadProfile);
		file.add(createProfile);
		file.add(importProfile);
		file.add(exportProfile);
		file.add(exit);
		options.add(registerGame);
		options.add(removeGame);
		options.add(checkLogs);
		tools.add(chartFilters);
		tools.add(textMode);
		tools.add(scheduleTools);
		tools.add(demoPackage);
		mode.add(dispSchedule);
		mode.add(quickLaunch);
		mode.add(charts);
		config.add(normalStart);
		config.add(autoUpdate);
		help.add(suggestions);
		help.add(faq);
		help.add(about);
		menuBar.add(file);
		menuBar.add(options);
		menuBar.add(tools);
		menuBar.add(mode);
		menuBar.add(config);
		menuBar.add(help);
		
		chartFrame.setJMenuBar(menuBar);
		
		//setup title + background
		int currentX = (BACKGROUND_WIDTH / 2) - (LOGO_WIDTH / 2);
		title = new JLabel();
		ImageIcon titleIcon = new ImageIcon(properties.getLogo());
		title.setIcon(titleIcon);
		title.setBounds(currentX, 15, LOGO_WIDTH, LOGO_HEIGHT);
		
		background = new JLabel();
		ImageIcon backgroundIcon = new ImageIcon(properties.getBackground());
		background.setIcon(backgroundIcon);
		background.setBounds(0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		
		desktopPane.add(title);
		desktopPane.add(background);
		desktopPane.moveToFront(title);
		desktopPane.moveToBack(background);
		
		chartFrame.add(desktopPane);
		chartFrame.pack();
		chartFrame.setVisible(true);
		
		//initiate opening sequence
		boolean doneSelectProfile = doSelectProfile();
		if(!doneSelectProfile) {
			empty = new JLabel("Please create or load a profile now");
			empty.setBounds((BACKGROUND_WIDTH / 2) - (200 / 2), (totalY / 2) - (30 / 2), 200, 30);
			desktopPane.add(empty);
			desktopPane.moveToFront(empty);
			options.setEnabled(false);
			tools.setEnabled(false);
			mode.setEnabled(false);
			importProfile.setEnabled(false);
			exportProfile.setEnabled(false);
			config.setEnabled(false);
			suggestions.setEnabled(false);
			faq.setEnabled(false);
		}else {
			loadLastUse();
			doLoadProfile();
			if(normalStart.isSelected()) {
				quickLaunch.setSelected(true);
				charts.setSelected(false);
				demoPackage.setSelected(false);
				dispSchedule.setSelected(false);
				clearScreenCharts();
				doQuickLaunch();
			}
		}
	}
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			//charts functionality
			for(int x = 0; x < regGames.size(); x++) {
				JButton game = regGames.get(x);
				String text = game.getText();
				if(event.getSource() == game) {
					if(chartModeFlag) {
						try {
							removeGame.setEnabled(true);
							optionsData.addSelectedGame(text);
							lastSelected = text;
							doLoadChart(text);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(textModeFlag) {
						removeGame.setEnabled(true);
						optionsData.addSelectedGame(text);
						lastSelected = text;
						try {
							doTextMode();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			//schedule functionality
			for(int y = 0; y < schedButton.size(); y++) {
				JButton name = schedButton.get(y);
				if(event.getSource() == name) {
					try {
						ScheduleData sData = new ScheduleData(
								name.getText(), 
								goalLabel.get(y).getText(),
								schedGoals.get(y).getText(),
								goalUnits.get(y).getText());
						doLoadScheduleOptions(sData);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(event.getSource() == freqButton) {
				int selection = JOptionPane.showOptionDialog(chartFrame, 
						"select desired frequency for whole schedule", 
						"frequency", 
						JOptionPane.OK_CANCEL_OPTION, 
						JOptionPane.INFORMATION_MESSAGE, 
						new ImageIcon(properties.getCompany()), 
						schedule.getFrequencyList(), 0);
				if(selection != -1) {
					try {
						schedule.setFrequency(selection);
						freqButton.setText(schedule.getSelectedFrequency());
						dateCheck = new DateCheck(progressIO, properties);
						boolean complete = dateCheck.checkComplete(schedule);
						if(complete) {
							completeLabel.setIcon(new ImageIcon(properties.getCompletePic()));
						}else {
							completeLabel.setIcon(new ImageIcon(properties.getIncompletePic()));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else if(event.getSource() == addToSchedule) {
				try {
					doLoadScheduleOptions();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class IconHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			for(int x = 0; x < quickLaunchButtons.size(); x++) {
				JButton game = quickLaunchButtons.get(x);
				if(event.getSource() == game) {
					try {
						String name = launchLookup.get(game);
						doQuickLaunchExec(name);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	private class MenuHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == loadProfile) {
				try {
					clearScreenCharts();
					clearScreenLaunch();
					clearScreenSchedule();
					switchUser();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == createProfile) {
				try {
					doCreateProfile();
					doLoadProfile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == importProfile) {
				
			}else if(event.getSource() == exportProfile) {
				
			}else if(event.getSource() == exit) {
				System.exit(0);
			}else if(event.getSource() == registerGame) {
				try {
					doRegisterGame();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == removeGame) {
				doRemoveGame();				
			}else if(event.getSource() == checkLogs) {
				try {
					doCheckLogs();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == chartFilters) {
				try {
					doLoadFilterOptions();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == textMode) {
				if(textModeFlag) {
					textModeFlag = false;
					chartModeFlag = true;
					try {
						desktopPane.moveToBack(textScroll);
						desktopPane.remove(textScroll);
						doLoadChart(lastSelected);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					textModeFlag = true;
					chartModeFlag = false;
					//charts.setSelected(false);
					if(optionsData.getSelectedGames().get(0).compareTo(optionsData.getDefaultGame()) != 0) {
						try {
							doTextMode();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}else if(event.getSource() == scheduleTools) {
				
			}else if(event.getSource() == demoPackage) {
				clearScreenSchedule();
				if(!demoFlag) {
					demoFlag = true;
					liveFlag = false;
					clearScreenSchedule();
					doDemoSchedule();
				}else {
					demoFlag = false;
					liveFlag = true;
					try {
						doSchedule();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else if(event.getSource() == dispSchedule) {
				try {
					loadSchedule();
					demoFlag = false;
					liveFlag = true;
					
					quickLaunch.setSelected(false);
					charts.setSelected(false);
					dispSchedule.setSelected(true);
					
					tools.setEnabled(true);
					options.setEnabled(false);
					scheduleTools.setEnabled(true);
					demoPackage.setEnabled(true);
					chartFilters.setEnabled(false);
					textMode.setEnabled(false);
					
					clearScreenSchedule();
					clearScreenCharts();
					clearScreenLaunch();
					doSchedule();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == quickLaunch) {
				charts.setSelected(false);
				dispSchedule.setSelected(false);
				clearScreenSchedule();
				clearScreenCharts();
				doQuickLaunch();
			}else if(event.getSource() == charts) {
				tools.setEnabled(true);
				options.setEnabled(true);
				scheduleTools.setEnabled(false);
				demoPackage.setEnabled(false);
				chartFilters.setEnabled(true);
				textMode.setEnabled(true);
				
				quickLaunch.setSelected(false);
				demoPackage.setSelected(false);
				dispSchedule.setSelected(false);
				textModeFlag = false;
				chartModeFlag = true;
				try {
					clearScreenSchedule();
					clearScreenLaunch();
					clearScreenCharts();
					doLoadProfile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == normalStart) {
				try {
					writeConfig();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == autoUpdate) {
				try {
					writeConfig();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == suggestions) {
				doSuggestion();
			}else if(event.getSource() == faq) {
				doFaq();
			}else if(event.getSource() == about) {
				doAbout();
			}
		}
	}
	
	public void doQuickLaunchExec(String name) throws IOException, InterruptedException {
		String path = properties.getGamePath(name);
		String command = "\"" + properties.genLaunchPath(path) + "\"" +
				" " + "\"" + path + "\"";
		Runtime.getRuntime().exec(command);
		//Thread.sleep(3000);
		chartFrame.toBack();
	}
	
	public void doQuickLaunch() {
		chartFrame.add(desktopPane);
		tools.setEnabled(false);
		options.setEnabled(false);
		ArrayList<String> launchGames = properties.getRegGames();
		int currentY = LOGO_HEIGHT + QUICK_TOP;
		int currentX = QUICK_MARGIN;
		int totalY = currentY;
		
		for(int x = 0; x < launchGames.size(); x++) {
			String name = launchGames.get(x);
			String path = properties.getGamePath(name);
			ImageIcon icon = new ImageIcon(properties.genIconPath(path));
			JButton button = new JButton();
			button.setIcon(icon);
			button.addActionListener(iconHandler);
			button.setBounds(currentX, currentY, QUICK_WIDTH, QUICK_HEIGHT);
			launchLookup.put(button, name);
			quickLaunchButtons.add(button);
			desktopPane.add(button);
			desktopPane.moveToFront(button);
			if((x + 1) % QUICK_ROW == 0  && (x + 1) < launchGames.size()) {
				currentX = QUICK_MARGIN;
				currentY += QUICK_HEIGHT + QUICK_SPACE;
				totalY += QUICK_HEIGHT + QUICK_SPACE;
			}else {
				currentX += QUICK_SPACE + QUICK_WIDTH;
			}
		}
		totalY += QUICK_HEIGHT + QUICK_SPACE + QUICK_SPACE;
		chartFrame.setPreferredSize(new Dimension(BACKGROUND_WIDTH, totalY));
		chartFrame.pack();
	}
	
	public void doSchedule() throws IOException {
		chartFrame.add(desktopPane);
		schedButton = new ArrayList<JButton>();
		schedGoals = new ArrayList<JTextField>();
		goalUnits = new ArrayList<JLabel>();
		goalLabel = new ArrayList<JLabel>();
		
		int currentX = SCHEDULE_MARGIN;
		int currentY = headerY - 45;
		
		scheduleTitle = new JLabel("Schedule of goals for user: " + properties.getUsername());
		scheduleTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
		currentX = (BACKGROUND_WIDTH / 2) - (SCHEDULE_TITLE_WIDTH / 2) - 10;
		scheduleTitle.setBounds(currentX, currentY, SCHEDULE_TITLE_WIDTH, SCHEDULE_TITLE_HEIGHT);
		desktopPane.add(scheduleTitle);
		desktopPane.moveToFront(scheduleTitle);
		
		currentX = SCHEDULE_MARGIN;
		currentY += SCHEDULE_TITLE_HEIGHT + SCHEDULE_SPACE;
		ArrayList<String> nameList = schedule.getAllNames();
		for(String name : nameList) {
			String option = schedule.getOption(name);
			String value = schedule.getValue(name);
			String units = schedule.getUnits(name);
			
			JButton button = new JButton(name);
			button.addActionListener(handler);
			button.setBounds(currentX, currentY, GAME_BUTTON_WIDTH, GAME_BUTTON_HEIGHT);
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
		addToSchedule.addActionListener(handler);
		addToSchedule.setBounds(currentX, currentY, ADD_BUTTON_WIDTH, ADD_BUTTON_HEIGHT);
		desktopPane.add(addToSchedule);
		desktopPane.moveToFront(addToSchedule);
		
		currentX = SCHEDULE_MARGIN;
		currentY += ADD_BUTTON_HEIGHT + SCHEDULE_SPACE;
		freqButton = new JButton(schedule.getSelectedFrequency());
		freqButton.setBounds(currentX, currentY, FREQ_LABEL_WIDTH, FREQ_LABEL_HEIGHT);
		freqButton.addActionListener(handler);
		desktopPane.add(freqButton);
		desktopPane.moveToFront(freqButton);
		
		currentX += FREQ_LABEL_WIDTH + 10;
		completeLabel = new JLabel();
		dateCheck = new DateCheck(progressIO, properties);
		boolean complete = dateCheck.checkComplete(schedule);
		if(complete) {
			completeLabel.setIcon(new ImageIcon(properties.getCompletePic()));
		}else {
			completeLabel.setIcon(new ImageIcon(properties.getIncompletePic()));
		}
		completeLabel.setBounds(currentX, currentY, COMPLETE_WIDTH, COMPLETE_HEIGHT);
		desktopPane.add(completeLabel);
		desktopPane.moveToFront(completeLabel);
		
		totalY = getScheduleY(nameList.size());
		chartFrame.setPreferredSize(new Dimension(BACKGROUND_WIDTH, totalY));
		chartFrame.pack();
	}
	
	public void switchUser() throws IOException {
		if(doSelectProfile()) {
			clearScreenCharts();
			clearScreenLaunch();
			doLoadProfile();
		}
	}
	
	public void doRegisterGame() throws IOException {
		ArrayList<String> gameList = properties.getStandardGameList();
		gameList = properties.removeDuplicates(gameList);
		String[] list = new String[gameList.size()];
		for(int x = 0; x < gameList.size(); x++) {
			list[x] = gameList.get(x);
		}
		String selection = (String) JOptionPane.showInputDialog(
				chartFrame, 
				"select game to register", 
				"register game", 
				JOptionPane.QUESTION_MESSAGE, 
				new ImageIcon(properties.getCompany()), 
				list, 
				0);
		if(selection != null) {
			boolean success = properties.registerGame(schedule, selection);
			if(success) {
				clearScreenCharts();
				clearScreenLaunch();
				doLoadProfile();
			}
		}
	}
	
	public void doLoadProfile() throws IOException {
		properties.loadUserProfile();
		progressIO = new ProgressIO(properties);
		ArrayList<String> regGames = properties.getRegGames();
			
		loadChartScreen(regGames);
		initEnable();
		optionsData = new OptionsData(properties);
	}
	
	public void loadChartScreen(ArrayList<String> games) {
		chartFrame.add(desktopPane);
		int currentY = calcYHeader(games.size(), GAME_BUTTON_HEIGHT);
		for(int x = 0; x < games.size(); x++) {
			JButton button = new JButton(games.get(x));
			int currentX = (BACKGROUND_WIDTH / 2) - (GAME_BUTTON_WIDTH / 2);
			button.setBounds(currentX, currentY, GAME_BUTTON_WIDTH, GAME_BUTTON_HEIGHT);
			button.addActionListener(handler);
			regGames.add(button);
			currentY += 5 + GAME_BUTTON_HEIGHT;
		}
		
		for(int x = 0; x < regGames.size(); x++) {
			desktopPane.add(regGames.get(x));
			desktopPane.moveToFront(regGames.get(x));
		}
		chartFrame.setPreferredSize(new Dimension(BACKGROUND_WIDTH, totalY));
		chartFrame.pack();
	}
	
	public int calcYHeader(int size, int itemHeight) {
		int currentY = headerY;
		int newY = headerY +  15 + 15 + 20 + 10 + ((5 + itemHeight) * size);
		if(newY < minY) {
			newY = minY;
			totalY = minY;
		}
		totalY = newY;
		return currentY;
	}
	
	public void doScheduleDisplay() {
		ArrayList<String> games = properties.getRegGames();
		int currentY = calcYHeader(games.size(), GAME_DISPLAY_HEIGHT);
		for(int x = 0; x < games.size(); x++) {
			JTextField tField = new JTextField(games.get(x));
			int currentX = (BACKGROUND_WIDTH / 2) - (GAME_BUTTON_WIDTH / 2);
			tField.setBounds(currentX, currentY, GAME_DISPLAY_WIDTH, GAME_DISPLAY_HEIGHT);
			tField.setEditable(false);
		}
	}
	
	public void doLoadFilterOptions() throws IOException {
		if(optionsData == null) {
			optionsData = new OptionsData(properties);
		}
		OptionsFilter filter = new OptionsFilter(this, optionsData, optionsData.getDisplay());
		desktopPane.add(filter);
		desktopPane.moveToFront(filter);		
	}
	
	public void doLoadScheduleOptions() throws IOException {
		ScheduleOptions sOptions = new ScheduleOptions();
		sOptions.init(this,  schedule, properties);
		desktopPane.add(sOptions);
		desktopPane.moveToFront(sOptions);
	}
	public void doLoadScheduleOptions(ScheduleData data) throws IOException {
		ScheduleOptions sOptions = new ScheduleOptions();
		sOptions.init(this,  data, properties);
		dateCheck = new DateCheck(progressIO, properties);
		boolean complete = dateCheck.checkComplete(schedule);
		if(complete) {
			completeLabel.setIcon(new ImageIcon(properties.getCompletePic()));
		}else {
			completeLabel.setIcon(new ImageIcon(properties.getIncompletePic()));
		}
		desktopPane.add(sOptions);
		desktopPane.moveToFront(sOptions);
	}	
	
	public void doCheckLogs() throws IOException {
		ArrayList<String> selectedGames = optionsData.getSelectedGames();
		for(int x = 0; x < selectedGames.size(); x++) {
			String name = selectedGames.get(x);
			String path = properties.getGamePath(name);
			progressIO.readLog(path, name);
			progressIO.condenseData(name);
		}
	}
	
	public void doRemoveGame() {
		JOptionPane.showConfirmDialog(
				chartFrame, 
				"are you sure you want to remove last selected game: " + lastSelected, 
				"remove game", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(properties.getCompany()));
	}
	
	public void enable() {
		file.setEnabled(true);
		options.setEnabled(true);
		tools.setEnabled(true);
		mode.setEnabled(true);
		help.setEnabled(true);
		config.setEnabled(true);
	}
	
	public void disable() {
		file.setEnabled(false);
		options.setEnabled(false);
		tools.setEnabled(false);
		mode.setEnabled(false);
		help.setEnabled(false);
		config.setEnabled(false);
	}
	
	public void saveOptions(OptionsData options) {
		optionsData = options;
	}
	
	public void saveScheduleOptions(ScheduleData data) throws IOException {
		schedule.addEntry(data);
		
		String buffer = "";
		BufferedReader reader = new BufferedReader(new FileReader(properties.getUserProfile()));
		while(reader.ready()) {
			String line = reader.readLine();
			if(line.compareTo("###schedule") == 0) {
				break;
			}
			buffer += line + "\n";
		}
		reader.close();
		
		buffer += "###schedule\n";
		ArrayList<String> nameList = schedule.getAllNames();
		for (String name : nameList) {
			String option = schedule.getOption(name);
			String value = schedule.getValue(name);
			String units = schedule.getUnits(name);
			buffer += name + "," + option + "," + value + "," + units + "\n";
		}
		buffer += frequency + ",";
		if(freqCompleted) {
			buffer += "yes";
		}else {
			buffer += "no";
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getUserProfile()));
		writer.write(buffer);
		writer.close();
	}
	
	public void loadLastUse() throws IOException {
		boolean found = false;
		String username = properties.getUsername();
		String path = properties.getLastUseFile();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		while(reader.ready()) {
			String line = reader.readLine();
			if(line.compareTo("###user=" + username) == 0) {
				found = true;
			}
			else if(found) {
				String[] data = line.split("=");
				if(data[0].compareTo("normalStart") == 0) {
					boolean nLaunch = Boolean.parseBoolean(data[1]);
					normalStart.setSelected(nLaunch);
					if(nLaunch) {
						quickLaunch.setSelected(false);
						charts.setSelected(true);
						demoPackage.setSelected(false);
						dispSchedule.setSelected(false);
						clearScreenCharts();
						doQuickLaunch();
					}
				}else if(data[0].compareTo("auto-update") == 0) {
					autoUpdate.setSelected(Boolean.parseBoolean(data[1]));
				}else {
					break;
				}
			}
		}
		reader.close();
	}
	
	public void writeConfig() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(properties.getLastUseFile()));
		String buffer = "";
		String username = properties.getUsername();
		boolean currentUser = false;
		while(reader.ready()) {
			String line = reader.readLine();
			if(line.contains("###user")) {
				if(line.compareTo("###user=" + username) == 0) {
					currentUser=true;
				}else {
					currentUser=false;
				}
			}
			
			if(!currentUser) {
				buffer += line + "\n";
			}
		}
		buffer += "###user=" + username + "\n";
		buffer += "normalStart=" + normalStart.isSelected() + "\n";
		buffer += "auto-update=" + autoUpdate.isSelected();
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(properties.getLastUseFile()));
		writer.write(buffer);
		writer.close();
	}
	
	public void loadSchedule() throws IOException {
		schedule = new Schedule(properties);
		BufferedReader reader = new BufferedReader(new FileReader(properties.getUserProfile()));
		ArrayList<String> data = new ArrayList<String>();
		while(reader.ready()) {
			String line = reader.readLine();
			data.add(line);
		}
		reader.close();
		boolean started = false;
		for(int x = 0; x < data.size() - 1; x++) {
			if(started) {
				String[] dataSplit = data.get(x).split(",");
				ScheduleData scheduleData = new ScheduleData(
						dataSplit[0],
						dataSplit[1],
						dataSplit[2],
						dataSplit[3]);
				schedule.addEntry(scheduleData);
			}else {
				if(data.get(x).compareTo("###schedule") == 0) {
					started = true;
				}
			}
		}
		String[] dataSplit = data.get(data.size() - 1).split(",");
		frequency = dataSplit[0];
		if(dataSplit[1].compareTo("yes") == 0) {
			freqCompleted = true;
		}else {
			freqCompleted = false;
		}
	}
	
	public boolean getChartFlag() {
		return chartModeFlag;
	}
	
	public ProgressScreen getThis() {
		return this;
	}
	
	public void init() throws IOException {
		if(!initialized) {
			createView();
		}
	}
}