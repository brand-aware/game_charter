package game_charter;

import javax.swing.JInternalFrame;

public class ConfigOptionsFilter extends JInternalFrame{
	
	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = -6191990741782692723L;
	
	protected final int OFFSET_VERTICAL = 50;
	protected final int OFFSET_HORIZONTAL = 50;
	
	protected final int TOTAL_VERTICAL = 250;
	protected final int TOTAL_HORIZONTAL = 350;
	
	protected final String TITLE = "chart options";
	protected final int TITLE_HORIZONTAL = 200;
	protected final int TITLE_VERTICAL = 60;
	protected final int TITLE_FONT_SIZE = 20;
	
	protected final int SUBTITLE_HORIZONTAL = 200;
	protected final int SUBTITLE_VERTICAL = 30;
	
	protected final String SELECTOR_LABEL = "display";
	protected final int SELECTOR_HORIZONTAL = 100;
	protected final int SELECTOR_VERTICAL = 30;
	
	protected final String CHECK_LABEL = "display multiple games";
	protected final int CHECK_HORIZONTAL = 200;
	protected final int CHECK_VERTICAL = 30;
	
	protected final int BUTTON_HORIZONTAL = 100;
	protected final int BUTTON_VERTICAL = 30;
	
	protected final String DEFAULT_GAME = "<options not set>";
	
	public ConfigOptionsFilter() {
		super("filter options", false, true, false, false);
	}

}
