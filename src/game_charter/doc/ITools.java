package game_charter.doc;

import java.io.IOException;

public interface ITools {
	
	/**
	 * Profiles are used for storing schedules, registered games, settings,
	 * and other custom choices a user can make.  Triggered in the file menu under
	 * the create profile menu item.
	 * 
	 * @throws IOException
	 */
	public void doCreateProfile() throws IOException;
	
	/**
	 * Triggered by the help menu suggestion menu item.  Used
	 * to make helpful suggestions to the end user.
	 */
	public void doSuggestion();
	
	/**
	 * Triggered by the help menu faq menu item.  Used to
	 * answer any GUI usage concerns that may arise (or are
	 * not fully explained during normal program usage).
	 */
	public void doFaq();
	
	/**
	 * Found in the help menu, is used for brand, company,
	 * date, contact, and any other info the user might want
	 * for satisfactory experience.
	 */
	public void doAbout();

}
