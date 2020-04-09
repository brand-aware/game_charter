package game_charter.doc;

import java.util.ArrayList;

import game_charter.Properties;

public interface IOptionsData {
	
	/**
	 * 
	 * @return Properties properties
	 */
	public Properties getProperties();
	
	/**
	 * 
	 * @return ArrayList<String> selectedGames
	 */
	public ArrayList<String> getSelectedGames();
	
	/**
	 * 
	 * @return boolean multiSet
	 */
	public boolean getMultiSet();
	
	/**
	 * 
	 * @return String display
	 */
	public String getDisplay();
	
	/**
	 * 
	 * @param boolean multi
	 */
	public void setMultiSet(boolean multi);
	
	
	/**
	 * 
	 * @param String selection
	 */
	public void setDisplay(String selection);
	
	/**
	 * 
	 * @param String text
	 */
	public void addSelectedGame(String text);

}
