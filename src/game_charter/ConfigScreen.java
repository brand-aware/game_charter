package game_charter;

public class ConfigScreen {
	
	protected final int BACKGROUND_WIDTH = 800;
	protected final int BACKGROUND_HEIGHT = 900;
	
	protected final int LOGO_WIDTH = 550;
	protected final int LOGO_HEIGHT = 172;
	
	//chart area vars
	protected final int GAME_BUTTON_WIDTH = 150;
	protected final int GAME_BUTTON_HEIGHT = 30;
	
	protected final int GAME_DISPLAY_WIDTH = 200;
	protected final int GAME_DISPLAY_HEIGHT = 30;
	
	protected final int GAME_MENU_BAR = 25;
	
	protected final int CHART_HEIGHT = 250;
	protected final int CHART_WIDTH = 500;
	
	protected final int TEXT_HEIGHT = 250;
	protected final int TEXT_WIDTH = 500;
	
	//quicklaunch vars
	protected final int QUICK_HEIGHT = 110;
	protected final int QUICK_WIDTH = 130;
	
	protected final int QUICK_TOP = 150;
	protected final int QUICK_MARGIN = 100;
	protected final int QUICK_SPACE = 60;
	protected final int QUICK_ROW = 3;
	
	//schedule vars
	protected final int SCHEDULE_BUTTON_WIDTH = 100;
	protected final int SCHEDULE_BUTTON_HEIGHT = 30;;
	
	protected final int SCHEDULE_GOAL_WIDTH = 100;
	protected final int SCHEDULE_GOAL_HEIGHT = 30;
	
	protected final int GOAL_UNIT_WIDTH = 60;
	protected final int GOAL_UNIT_HEIGHT = 30;
	
	protected final int GOAL_LABEL_WIDTH = 60;
	protected final int GOAL_LABEL_HEIGHT = 30;
	
	protected final int SCHEDULE_TITLE_WIDTH = 450;
	protected final int SCHEDULE_TITLE_HEIGHT = 60;
	
	protected final int ADD_BUTTON_WIDTH = 100;
	protected final int ADD_BUTTON_HEIGHT = 30;
	
	protected final int FREQ_LABEL_WIDTH = 90;
	protected final int FREQ_LABEL_HEIGHT = 30;
	
	protected final int COMPLETE_WIDTH = 30;
	protected final int COMPLETE_HEIGHT = 30;
	
	protected final int SCHEDULE_MARGIN = 200;
	protected final int SCHEDULE_SPACE = 60;
	
	protected int chartX;
	protected int chartY;
	protected int totalY;
	protected int minY;
	protected int headerY;
	
	public ConfigScreen() {
		headerY = 15 + LOGO_HEIGHT + 15 + 15 + 20 + GAME_MENU_BAR;
	}

}
