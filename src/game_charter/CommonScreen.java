package game_charter;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;

import core.ProgressChart;
import core.ProgressIO;

public class CommonScreen extends ConfigScreen{
	
	protected ProgressChart progressChart;
	protected ProgressIO progressIO;
	protected Properties properties;
	protected OptionsData optionsData = null;
	protected Schedule schedule;
	
	protected JFrame chartFrame;
	protected JDesktopPane desktopPane;
	protected ChartPanel chartPanel;
	
	protected JLabel title;
	protected JLabel background;
	protected JLabel empty;
	
	protected JMenuBar menuBar;
	protected JMenu file, options, tools, mode, help, config;
	protected JMenuItem loadProfile, createProfile,
		importProfile, exportProfile, exit;
	protected JMenuItem registerGame, removeGame, checkLogs;
	protected JMenuItem chartFilters, textMode, scheduleTools,
		demoPackage;
	protected JCheckBoxMenuItem dispSchedule, quickLaunch, charts;
	protected JMenuItem suggestions, faq, about;
	protected JCheckBoxMenuItem normalStart, autoUpdate;
	
	//chart display vars
	protected ArrayList<JButton> regGames;
	protected ArrayList<String> gameList;
	
	protected String lastSelected;
	protected boolean initialized = false;
	
	protected JTextArea textArea;
	protected JScrollPane textScroll;
	
	protected boolean textModeFlag = false;
	protected boolean chartModeFlag = true;
	
	//quicklaunch display vars
	protected HashMap<JButton, String> launchLookup;
	protected ArrayList<JButton> quickLaunchButtons;
	
	//schedule display vars
	protected JLabel scheduleTitle;
	protected JButton addToSchedule;
	protected JButton freqButton;
	protected JLabel completeLabel;
	
	protected ArrayList<JButton> schedButton;
	protected ArrayList<JTextField> schedGoals;
	protected ArrayList<JLabel> goalUnits;
	protected ArrayList<JLabel> goalLabel;
	
	protected String frequency;
	protected boolean freqCompleted = false;
	
	protected boolean demoFlag = false;
	protected boolean liveFlag = false;

}