package game_charter.doc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import game_charter.Schedule;

public interface IPropertiesTools {

	public String getProfiles();
	/**
	 * Creates directory structure for writing data to disk if
	 * none currently exists.
	 * 
	 * @throws IOException
	 */
	public void createSettingsDir() throws IOException;
	
	/**
	 * Loads user profile or creates an empty one if none exists.
	 * 
	 * @throws IOException
	 */
	public void createLoadProfile() throws IOException;
	
	/**
	 * Loads all games currently supported by game_charter brand
	 * logging extension.
	 * 
	 * @param File file
	 * @return ArrayList<String> list
	 * @throws IOException
	 */
	public ArrayList<String> loadRegGames(File file) throws IOException;
	
	/**
	 * Generates list from disk of all user profiles registered
	 * with application.
	 * 
	 * @param File file
	 * @return String[] list
	 * @throws IOException
	 */
	public String[] loadProfiles(File file) throws IOException;
	
	/**
	 * Saves user name and creates path to user profile on
	 * disk.
	 * 
	 * @param String name
	 */
	public void setUsername(String name);
	
	/**
	 * Checks validity of user name and prepares disk to save
	 * data.
	 * 
	 * @param String name
	 * @return boolean created
	 * @throws IOException
	 */
	public boolean createUsername(String name) throws IOException;
	
	/**
	 * Makes sure user has used fewer than 20 characters and only
	 * used alphabetic characters.
	 * 
	 * @param String name
	 * @return boolean valid
	 */
	public boolean validUsername(String name);
	
	/**
	 * Loads list of games user has registered for their profile.
	 * 
	 * @return ArrayList<String> list
	 * @throws IOException
	 */
	public void loadUserProfile() throws IOException;
	
	/**
	 * Adds selected game to user profile list
	 * 
	 * @param String name
	 * @throws IOException
	 */
	public boolean registerGame(Schedule schedule, String name) throws IOException;
}
