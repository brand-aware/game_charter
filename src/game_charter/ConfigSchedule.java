package game_charter;

import javax.swing.JInternalFrame;

/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2020
 * 
 */
public class ConfigSchedule extends JInternalFrame{
	
	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = -8291805522241796684L;
	
	protected final String OK_LABEL = "ok";
	protected final String EXIT_LABEL = "exit";
	protected final String TITLE_LABEL = "schedule options";
	protected final String GAME_DISPLAY_TEXT = "GAME: ";
	
	protected final int GAME_COMBO_WIDTH = 100;
	protected final int GAME_COMBO_HEIGHT = 30;
	
	protected final int LOCATION_X = 50;
	protected final int LOCATION_Y = 50;
	
	protected final int TOTAL_X = 425;
	protected final int TOTAL_Y = 300;
	
	protected final int OK_BUTTON_WIDTH = 100;
	protected final int OK_BUTTON_HEIGHT = 30;
	
	protected final int EXIT_BUTTON_WIDTH = 100;
	protected final int EXIT_BUTTON_HEIGHT = 30;
	
	protected final int TITLE_WIDTH = 250;
	protected final int TITLE_HEIGHT = 100;
	
	protected final int GDISPLAY_WIDTH = 170;
	protected final int GDISPLAY_HEIGHT = 30;
	
	protected final int INPUT_LABEL_WIDTH = 60;
	protected final int INPUT_LABEL_HEIGHT = 30;
	
	protected final int INPUT_WIDTH = 200;
	protected final int INPUT_HEIGHT = 30;
	
	protected final int SELECTOR_WIDTH = 100;
	protected final int SELECTOR_HEIGHT = 30;
	
	protected final int TITLE_FONT_SIZE = 26;
	protected final int SUB_FONT_SIZE = 16;
	
	public ConfigSchedule(){
		super("Schedule option config", false, true, false, false);
	}
}