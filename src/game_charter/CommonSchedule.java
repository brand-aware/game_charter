package game_charter;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2020
 * 
 */
public class CommonSchedule extends ConfigSchedule{
	
	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = -5057723134685047870L;
	protected Properties properties;
	protected JLayeredPane layeredPane;
	protected ProgressScreen pScreen;
	
	protected JLabel title, gameDisplay;
	protected JTextField input;
	protected JLabel inputLabel;
	protected JComboBox<String> gameCombo;
	protected JComboBox<String> selector;
	protected JButton ok, exit;
	protected ArrayList<String> scheduledGames;
	
	protected int choice = 0;
	
	public CommonSchedule() {
		super();
	}
}