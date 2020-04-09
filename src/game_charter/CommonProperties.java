package game_charter;

import java.util.ArrayList;
import java.util.HashMap;

public class CommonProperties {

	//generic paths
	protected String root;
	protected String imgDir;
	protected String company;
	protected String company_iframe;
	protected String logo;
	protected String background;
	protected String username;
	protected String userProfile;
	
	//application data paths
	protected String settingsDir;
	protected String settings;
	protected String profiles;
	protected String selector;
	protected String freqListFile;
	protected String lastUse;
	protected String completePic;
	protected String incompletePic;
	
	//universal application settings
	protected ArrayList<String> stdRegGameList;
	protected String[] profileList;
	
	//profile specific settings
	protected ArrayList<String> regGameList;
	protected HashMap<String, String> gamePaths;
	protected Schedule schedule;
	
}
